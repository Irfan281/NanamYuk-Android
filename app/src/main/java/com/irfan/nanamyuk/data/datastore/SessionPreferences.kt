package com.irfan.nanamyuk.data.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionPreferences private constructor(private val dataStore: DataStore<Preferences>) {
    fun getToken(): Flow<SessionModel> {
        return dataStore.data.map { preferences ->
            SessionModel(
                preferences[TOKEN] ?: ""
            )
        }
    }

    suspend fun login(token: String) {
        dataStore.edit { preferences ->
            preferences[TOKEN] = token
        }
    }

    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences.clear()
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: SessionPreferences? = null

        private val TOKEN = stringPreferencesKey("token")

        fun getInstance(dataStore: DataStore<Preferences>): SessionPreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = SessionPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}