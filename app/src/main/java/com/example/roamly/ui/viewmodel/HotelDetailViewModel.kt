package com.example.roamly.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roamly.domain.models.Hotel
import com.example.roamly.domain.models.ReserveRequest
import com.example.roamly.domain.models.Room
import com.example.roamly.domain.models.Trip
import com.example.roamly.domain.repository.HotelRepository
import com.example.roamly.domain.repository.TripRepository
import com.example.roamly.domain.repository.UserRepository
import com.example.roamly.utils.ErrorUtils
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class HotelDetailViewModel @Inject constructor(
    private val repo: HotelRepository,
    private val tripRepo: TripRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(HotelDetailUiState())
    val uiState: StateFlow<HotelDetailUiState> = _uiState

    var showImageDialog by mutableStateOf(false)
    fun showImageDialog() { showImageDialog = true }
    fun hideImageDialog() { showImageDialog = false }
    private var currentUserId: String? = null

    init {
        val user = auth.currentUser
        currentUserId = user?.uid
    }
    fun selectRoom(room: Room) {
        _uiState.value = _uiState.value.copy(selectedRoom = room)
    }

    private lateinit var groupId: String
    private lateinit var start: String
    private lateinit var end: String

    /* -------- load hotel & free rooms -------- */
    fun load(hotelId: String, gid: String, s: String, e: String) {
        if (uiState.value.hotel != null) return   // already loaded
        groupId = gid; start = s; end = e
        viewModelScope.launch {
            val hotel = repo.getHotels(gid).first { it.id == hotelId }
            val freeRooms = repo.getAvailability(gid, s, e)
                .first { it.id == hotelId }.rooms
            _uiState.value = HotelDetailUiState(false, hotel, freeRooms)
        }
    }

//    fun reserveRoom(selectedRoom: Room) {
//        Log.d("hoteldetail", "room: $selectedRoom")
//        Log.i("hoteldetail", "room: $selectedRoom")
//        Log.d("hoteldetail", "room: $selectedRoom")
//    }

    /* -------- reserve selected room -------- */
    fun reserveRoom(room: Room) = viewModelScope.launch {

        if (currentUserId == null) {
            Log.e("HotelDetailViewModel", "No user authenticated")
            return@launch
        }

        val localUser = userRepository.getUserByFirebaseUid(currentUserId!!)
        if (localUser == null) {
            Log.e("HotelDetailViewModel", "Usuario no encontrado en la base de datos local")
            return@launch
        }

        Log.d("reserveRoom called", "room: $room")
        Log.d("HotelDetailViewModel", "Iniciando reserva para el usuario: ${localUser.name} (${localUser.email})")

        val hotel = uiState.value.hotel ?: return@launch
        val req = ReserveRequest(
            hotelId = uiState.value.hotel!!.id,
            roomId  = room.id,
            startDate = start,
            endDate   = end,
            guestName = localUser.name, //CAMBIAR AQUI PARA TU USUARIO
            guestEmail = localUser.email //CAMBIAR AQUI PARA TU USUARIO
        )
        Log.d("HotelDetailViewModel", "Request de reserva creado: $req")


        try {
            Log.d("HotelDetailViewModel", "Iniciando reserva...")
            val reservation = repo.reserve(groupId, req)

            Log.d("HotelDetailViewModel", "Respuesta de reserva: $reservation")

            // Reconstruir hotel y room si no vienen en la respuesta
            val hotelDto = uiState.value.hotel
            val roomDto = uiState.value.rooms?.find { it.id == reservation.roomId }

            if (hotelDto == null || roomDto == null) {
                Log.e("HotelDetailViewModel", "No se pudo obtener hotel o room tras reserva.")
                return@launch
            }


            val startDateMillis = java.time.LocalDate.parse(start).toEpochDay() * 86400000
            val endDateMillis = java.time.LocalDate.parse(end).toEpochDay() * 86400000
            Log.d("HotelDetailViewModel", "UserId: ${localUser.id}")
            val trip = Trip(
                id = 0,
                userId = localUser.userId.hashCode(),
                destination = hotelDto.name,
                startDate = Date(startDateMillis),
                endDate = Date(endDateMillis),
                budget = roomDto.price.toDouble(),
                notes = "",
                isFavorite = false,
                coverImageUrl = hotel.imageUrl,
                itineraryItems = emptyList()
            )
            Log.d("HotelDetailViewModel", "Trip creado localmente: $trip")

            val success = tripRepo.addTrip(trip)
            if (success) {
                Log.d("HotelDetailViewModel", "Reserva guardada localmente como Trip")
            } else {
                Log.e("HotelDetailViewModel", "Error al guardar el trip local")
            }
        } catch (e: HttpException) {
            val decodedError = ErrorUtils.extractErrorMessage(e)
            Log.e("BookViewModel", "HTTP error: ${decodedError}  $e")

        } catch (e: Exception) {
            Log.e("BookViewModel", "Error: ${e.localizedMessage}")
        }

    }
}


//try {
//    val hotels = repo.getAvailability(groupId, s.format(fmt), e.format(fmt), city = city)
//    _uiState.update { it.copy(loading = false, hotels = hotels) }
//} catch (e: HttpException) {
//
//    val decodedError = ErrorUtils.extractErrorMessage(e)
//
//    Log.e("BookViewModel", "HTTP error: ${decodedError}  $e")
//    _uiState.update { it.copy(loading = false, hotels = emptyList(), message = decodedError) }
//
//    _uiState.update {
//        it.copy(
//            loading = false,
//            hotels = emptyList(),
//            message = "Error: ${decodedError}}"
//        )
//    }
//
//} catch (e: Exception) {
//    Log.e("BookViewModel", "Error: ${e.localizedMessage}")
////            _uiState.update { it.copy(loading = false, hotels = emptyList()) }
//    _uiState.update {
//        it.copy(
//            loading = false,
//            hotels = emptyList(),
//            message = "Error: ${e.message}}"
//        )
//    }
//}

data class HotelDetailUiState(
    val loading: Boolean = true,
    val hotel: Hotel? = null,
    val rooms: List<Room>? = emptyList(),
    val selectedRoom: Room? = null,
    val showImageDialog: Boolean = false,
)