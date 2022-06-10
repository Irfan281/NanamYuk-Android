package com.irfan.nanamyuk.ui.setting//package com.irfan.nanamyuk.ui.setting

import android.util.Log
import androidx.lifecycle.*
import com.irfan.nanamyuk.data.api.AuthResponse
import com.irfan.nanamyuk.data.api.ConfigApi
import com.irfan.nanamyuk.data.api.ConfigApi.Companion.BASE_URL
import com.irfan.nanamyuk.data.datastore.SessionModel
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.ui.pilih.PilihViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SettingViewModel(private val pref: SessionPreferences) : ViewModel() {
    fun getUserToken(): LiveData<SessionModel> {
        return pref.getToken().asLiveData()
    }

    private val _state = MutableLiveData<Boolean>()
    val state: LiveData<Boolean> = _state

    fun logout(token: String) {
        _state.value = false

        viewModelScope.launch {
            pref.logout()
        }

        val client = ConfigApi.getApiService(BASE_URL).logout("Bearer $token")
        client.enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) {
                    _state.value = true
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.e(TAG, "onFailure Throw: ${t.message}")
            }
         })
    }

    companion object {
        private const val TAG = "SettingViewModel"
    }
}