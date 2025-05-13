package com.example.roamly.di


import HotelRepositoryImpl
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.roamly.BuildConfig
import com.example.roamly.data.local.AppDatabase
import com.example.roamly.data.local.dao.AccessLogDao
import com.example.roamly.data.local.dao.ItineraryDao
import com.example.roamly.data.local.dao.TripDao
import com.example.roamly.data.local.dao.UserDao
import com.example.roamly.data.remote.api.HotelApiService
import com.example.roamly.data.repository.AccessLogRepositoryImpl
import com.example.roamly.domain.repository.ItineraryRepository
import com.example.roamly.data.repository.ItineraryRepositoryImpl
import com.example.roamly.domain.repository.TripRepository
import com.example.roamly.data.repository.TripRepositoryImpl
import com.example.roamly.data.repository.UserRepositoryImpl
import com.example.roamly.domain.repository.AccessLogRepository
import com.example.roamly.domain.repository.HotelRepository
import com.example.roamly.domain.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

import com.example.roamly.ui.viewmodel.RegisterViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences =
        context.getSharedPreferences("${BuildConfig.APPLICATION_ID}_preferences", Context.MODE_PRIVATE)

    // context.getSharedPreferences("my_preferences", Context.MODE_PRIVATE) //bad implementation

    @Provides
    @Singleton
    fun provideFormValidationViewModel(
        @ApplicationContext context: Context
    ): RegisterViewModel = RegisterViewModel(context)

    @Provides
    @Singleton
    fun provideTripRepository(tripDao: TripDao, itineraryDao: ItineraryDao): TripRepository {
        return TripRepositoryImpl(tripDao, itineraryDao)
    }

    @Provides
    @Singleton
    fun provideItineraryRepository(itineraryDao: ItineraryDao): ItineraryRepository {
        return ItineraryRepositoryImpl(itineraryDao)
    }

    @Provides
    fun provideTripDao(db: AppDatabase): TripDao {
        return db.tripDao()
    }

    @Provides
    fun provideItineraryDao(db: AppDatabase): ItineraryDao {
        return db.itineraryDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "roamly_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    @Singleton
    fun provideAccessLogRepository(accessLogDao: AccessLogDao): AccessLogRepository {
        return AccessLogRepositoryImpl(accessLogDao)
    }

    @Provides
    fun provideUserDao(db: AppDatabase): UserDao {
        return db.userDao()
    }

    @Provides
    fun provideAccessLogDao(db: AppDatabase): AccessLogDao {
        return db.accessLogDao()
    }

    @Provides
    @Singleton
    fun provideHotelApi(): HotelApiService {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.HOTELS_API_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(HotelApiService::class.java)
    }

    /* --- Repository --- */
    @Provides
    @Singleton
    fun provideHotelRepository(api: HotelApiService, taskDao: TripDao): HotelRepository =
        HotelRepositoryImpl(api, taskDao)
}
