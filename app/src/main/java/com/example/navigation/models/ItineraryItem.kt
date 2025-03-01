package com.example.navigation.models

import java.util.Date

data class ItineraryItem(
    val id: String,
    val description: String,
    val date: Date,
    val location: String
) {
    // @TODO: Implement function to update itinerary item details
    fun updateItemDetails() {}
}
