package com.example.roamly

import com.example.roamly.data.remote.api.HotelApiService
import com.example.roamly.data.remote.dto.AvailabilityResponseDto
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HotelApiServiceTest {

    private lateinit var api: HotelApiService

    @Before
    fun setup() {
        val client = OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://13.39.162.212/") // URL real de la API
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        api = retrofit.create(HotelApiService::class.java)
    }

    @Test
    fun `getAvailability returns hotels from real API`() = runBlocking {
        // 🔸 Reemplaza por un group_id válido si lo tienes
        val groupId = "G04"
        val startDate = "2024-12-01"
        val endDate = "2024-12-10"

        val response: AvailabilityResponseDto = api.getAvailability(
            groupId = groupId,
            startDate = startDate,
            endDate = endDate,
            hotelId = null,
            city = null
        )

        println("Available hotels: ${response.available_hotels}")

        assertNotNull(response)
        assertTrue(response.available_hotels.isNotEmpty())
    }
}
