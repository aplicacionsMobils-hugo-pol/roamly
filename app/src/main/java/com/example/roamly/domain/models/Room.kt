package com.example.roamly.domain.models

data class Room(
    val id: String,
    val roomType: String,
    val price: Float,
    val images: List<String>
)
