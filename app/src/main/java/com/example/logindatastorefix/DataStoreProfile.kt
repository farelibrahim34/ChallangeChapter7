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

val Context.dataStoreProfile : DataStore<Preferences> by preferencesDataStore(name = "dataprofile")

class DataStoreProfile(private val context: Context) {
    val NAMA = stringPreferencesKey("nama")
    val TGL = stringPreferencesKey("tgl")
    val ALAMAT = stringPreferencesKey("alamat")


    suspend fun saveDataProfile(nama : String, tgl : String, alamat: String){
        context.dataStore.edit {
            it[NAMA] = nama
            it[TGL] = tgl
            it[ALAMAT] = alamat
        }

    }
    val userNama: Flow<String> = context.dataStore.data
        .map {
            it[NAMA] ?: ""
        }
    val userTgl: Flow<String> = context.dataStore.data
        .map {
            it[TGL] ?: ""
        }
    val userAlamat: Flow<String> = context.dataStore.data
        .map {
            it[ALAMAT] ?: ""
        }
    suspend fun clearDataProfile(){
        context.dataStore.edit {
            it.clear()
        }
    }



}