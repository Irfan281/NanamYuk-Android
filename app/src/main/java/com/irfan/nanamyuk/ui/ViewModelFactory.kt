package com.irfan.nanamyuk.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.ui.daftar.DaftarViewModel
import com.irfan.nanamyuk.ui.dash.DashViewModel
import com.irfan.nanamyuk.ui.detail.DetailViewModel
import com.irfan.nanamyuk.ui.login.LoginViewModel
import com.irfan.nanamyuk.ui.pilih.PilihViewModel
import com.irfan.nanamyuk.ui.setting.SettingViewModel

class ViewModelFactory(private val pref: SessionPreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DaftarViewModel::class.java) -> {
                DaftarViewModel(pref) as T
            }
            modelClass.isAssignableFrom(PilihViewModel::class.java) -> {
                PilihViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DashViewModel::class.java) -> {
                DashViewModel(pref) as T
            }
            modelClass.isAssignableFrom(SettingViewModel::class.java) -> {
                SettingViewModel(pref) as T
            }
            modelClass.isAssignableFrom(DetailViewModel::class.java) -> {
                DetailViewModel(pref) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}