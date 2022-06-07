package com.irfan.nanamyuk.ui.pilih

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.irfan.nanamyuk.data.api.ConfigApi
import com.irfan.nanamyuk.data.api.PlantResponse
import com.irfan.nanamyuk.data.api.PlantResponseItem
import com.irfan.nanamyuk.data.datastore.SessionModel
import com.irfan.nanamyuk.data.datastore.SessionPreferences
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PilihViewModel(private val pref: SessionPreferences): ViewModel() {
    private val _plants = MutableLiveData<List<PlantResponseItem>>()
    val plants: LiveData<List<PlantResponseItem>> = _plants

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUserToken(): LiveData<SessionModel> {
        return pref.getToken().asLiveData()
    }

    fun getPlants(token: String){
        Log.e(TAG, "tes gais masuk gak 1")
        _isLoading.value = true

        val client = ConfigApi.getApiService().getPlant("Bearer $token")
        client.enqueue(object : Callback<List<PlantResponseItem>> {
            override fun onResponse(call: Call<List<PlantResponseItem>>, response: Response<List<PlantResponseItem>>) {
                _isLoading.value = false
                Log.e(TAG, "tes gais masuk gak 2")
                if (response.isSuccessful) {
                    _plants.value = response.body()
                    Log.e(TAG, "tes gais masuk gak 3")
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<PlantResponseItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure Throw: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "PilihViewModel"
    }
    
}