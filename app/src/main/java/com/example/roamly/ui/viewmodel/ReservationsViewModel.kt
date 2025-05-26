package com.example.roamly.ui.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roamly.BuildConfig
import com.example.roamly.domain.models.Reservation
import com.example.roamly.domain.repository.HotelRepository
import com.google.firebase.auth.FirebaseAuth

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReservationsViewModel @Inject constructor(
    private val repo: HotelRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReservationsUiState())
    val uiState: StateFlow<ReservationsUiState> = _uiState

    private val auth = FirebaseAuth.getInstance()

    fun load() = viewModelScope.launch {
        _uiState.update { it.copy(loading = true) }
        val email = auth.currentUser?.email
        //IMPORTANTE USANDO EL GROUP ID!!!
        //AQUI tambien buscar el correo del usuario authenticado!!
        val res = if (email.isNullOrEmpty()) {
            repo.getGroupReservations(BuildConfig.GROUP_ID, null)
        } else {
            repo.getGroupReservations(BuildConfig.GROUP_ID, email)

        }
        _uiState.value = ReservationsUiState(false, res)
    }

    fun cancel(r: Reservation) = viewModelScope.launch {
        Log.d("viewmodel", "canceling: ${r.id}")
        repo.cancelById(r.id)
        _uiState.update { it.copy(reservations = it.reservations - r) } // quita de la lista
        load()
    }
}


data class ReservationsUiState(
    val loading: Boolean = true,
    val reservations:  List<Reservation> = emptyList()
)