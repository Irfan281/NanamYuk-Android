package com.irfan.nanamyuk.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.nanamyuk.data.datastore.SessionModel
import com.irfan.nanamyuk.data.datastore.SessionPreferences

class SplashViewModel(private val pref: SessionPreferences): ViewModel() {
    fun getUserToken(): LiveData<SessionModel> {
        return pref.getToken().asLiveData()
    }
}