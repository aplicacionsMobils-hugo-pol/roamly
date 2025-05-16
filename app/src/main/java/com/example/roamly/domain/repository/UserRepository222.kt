package com.example.roamly.domain.repository

import com.example.roamly.domain.models.User

object UserRepository222 {
    val currentUser = User(
        id = 123,
        userId = "123",
        name = "John Doe",
        email = "johndoe@example.com",
        phoneNumber = "+123456789",
        profilePictureUrl = "https://randomuser.me/api/portraits/men/1.jpg",
        bio = "Explorador del mundo 🌍 | Aventura y naturaleza 🏕️",
        followers = 50,
        following = 20,
        totalLikes = 176,
        uploadedRoutes = 3,
        acceptEmails = true,
        birthdate = 2345,
        address = "123 Main St, Springfield, USA",
        country = "USA",
        username = "johndoe123"
    )
}
