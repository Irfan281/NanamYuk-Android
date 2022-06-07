package com.irfan.nanamyuk.ui.dash

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.nanamyuk.data.api.ConfigApi
import com.irfan.nanamyuk.data.api.UserPlantsResponseItem
import com.irfan.nanamyuk.data.datastore.SessionModel
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import com.irfan.nanamyuk.ui.pilih.PilihViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashViewModel(private val pref: SessionPreferences) : ViewModel() {
    private val _userplants = MutableLiveData<List<UserPlantsResponseItem>>()
    val userplants: LiveData<List<UserPlantsResponseItem>> = _userplants

    fun getUserToken(): LiveData<SessionModel> {
        return pref.getToken().asLiveData()
    }

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading
    
    fun getUserPlants(token: String) {
        _isLoading.value = true

        val client = ConfigApi.getApiService().getUserPlants("Bearer $token")
        client.enqueue(object : Callback<List<UserPlantsResponseItem>> {
            override fun onResponse(call: Call<List<UserPlantsResponseItem>>, response: Response<List<UserPlantsResponseItem>>) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _userplants.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<UserPlantsResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure Throw: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "PilihViewModel"
    }

}