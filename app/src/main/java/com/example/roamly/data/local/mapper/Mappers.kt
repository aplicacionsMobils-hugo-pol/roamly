package com.example.roamly.data.local.mapper

import com.example.roamly.data.local.entity.ItineraryItemEntity
import com.example.roamly.data.local.entity.TripEntity
import com.example.roamly.data.local.entity.UserEntity
import com.example.roamly.domain.models.ItineraryItem
import com.example.roamly.domain.models.Trip
import com.example.roamly.domain.models.User
import java.util.Date

fun Trip.toEntity(): TripEntity =
    TripEntity(
        id = id,
        userId = userId,
        destination = destination,
        startDate = startDate.time,
        endDate = endDate.time,
        budget = budget,
        notes = notes,
        isFavorite = isFavorite,
        coverImageUrl = coverImageUrl
    )

fun ItineraryItem.toEntity(tripId: Int = this.tripId): ItineraryItemEntity =
    ItineraryItemEntity(
        id = id,
        tripId = tripId,
        description = description,
        date = date.time,
        location = location,
    )

fun TripEntity.toDomain(itineraryItems: List<ItineraryItem>? = emptyList()): Trip =
    Trip(
        id = id,
        userId = userId,
        destination = destination,
        startDate = Date(startDate),
        endDate = Date(endDate),
        budget = budget,
        notes = notes,
        isFavorite = isFavorite,
        coverImageUrl = coverImageUrl,
        itineraryItems = itineraryItems ?: emptyList()
    )

fun ItineraryItemEntity.toDomain(): ItineraryItem =
    ItineraryItem(
        id = id,
        tripId = tripId,
        description = description,
        date = Date(date),
        location = location,
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = id,
        userId = userId,
        username = username,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        birthdate = birthdate,
        address = address,
        country = country,
        acceptEmails = acceptEmails,
        profilePictureUrl = profilePictureUrl,
        bio = bio,
        followers = followers,
        following = following,
        totalLikes = totalLikes,
        uploadedRoutes = uploadedRoutes
    )

fun UserEntity.toDomain(): User =
    User(
        id = id,
        userId = userId,
        username = username,
        name = name,
        email = email,
        phoneNumber = phoneNumber,
        birthdate = birthdate,
        address = address,
        country = country,
        acceptEmails = acceptEmails,
        profilePictureUrl = profilePictureUrl,
        bio = bio,
        followers = followers,
        following = following,
        totalLikes = totalLikes,
        uploadedRoutes = uploadedRoutes,
    )