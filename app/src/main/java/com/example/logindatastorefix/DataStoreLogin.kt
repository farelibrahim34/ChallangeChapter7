package com.example.logindatastorefix

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "datauser")
class DataStoreLogin(private val context: Context) {
    val USERNAME = stringPreferencesKey("username")
    val EMAIL = stringPreferencesKey("email")
    val PW = stringPreferencesKey("password")


    suspend fun saveData(username : String, pw : String, email: String){
        context.dataStore.edit {
            it[USERNAME] = username
            it[PW] = pw
            it[EMAIL] = email
        }

    }
    suspend fun clearData(){
        context.dataStore.edit {
            it.clear()
        }
    }
    val userName: Flow<String> = context.dataStore.data
        .map {
            it[USERNAME] ?: ""
        }
    val userEmail: Flow<String> = context.dataStore.data
        .map {
            it[EMAIL] ?: ""
        }
    val userPw: Flow<String> = context.dataStore.data
        .map {
            it[PW] ?: ""
        }

}




