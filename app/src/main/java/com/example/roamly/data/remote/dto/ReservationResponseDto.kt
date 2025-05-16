package com.example.roamly.data.remote.dto

/* ---------- reserva devuelta por POST /reserve ---------- */
data class ReservationResponseDto(
    val message: String,
    val nights: Int,
    val reservation: ReservationDto        // ya trae hotel + room
)

