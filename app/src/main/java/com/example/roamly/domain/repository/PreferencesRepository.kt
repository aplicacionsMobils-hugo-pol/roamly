package com.example.roamly.domain.repository

import com.example.roamly.domain.models.Preferences

interface PreferencesRepository {
    fun addPreferences(preferences: Preferences): Boolean
    fun getPreferences(id: String): Preferences?
    fun updatePreferences(id: String, notificationsEnabled: Boolean?, preferredLanguage: String?, theme: String?): Boolean
    fun deletePreferences(id: String): Boolean
}