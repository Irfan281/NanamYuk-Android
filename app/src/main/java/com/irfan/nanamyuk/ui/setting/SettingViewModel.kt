package com.irfan.nanamyuk.ui.setting//package com.irfan.nanamyuk.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import kotlinx.coroutines.launch

class SettingViewModel(private val pref: SessionPreferences) : ViewModel() {
    fun logout() {
        viewModelScope.launch {
            pref.logout()
        }
    }
}