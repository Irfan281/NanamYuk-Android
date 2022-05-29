package com.irfan.nanamyuk.ui.login//package com.irfan.storyapp.view.login
//
//import android.telecom.Call
//import androidx.lifecycle.LiveData
//import androidx.lifecycle.MutableLiveData
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.irfan.storyapp.data.api.ConfigApi
//import com.irfan.storyapp.data.api.LoginResponse
//import com.irfan.storyapp.data.api.LoginResult
//import com.irfan.storyapp.data.datastore.SessionPreferences
//import kotlinx.coroutines.launch
//import org.json.JSONObject
//import retrofit2.Call
//import retrofit2.Callback
//import retrofit2.Response
//
//class LoginViewModel(private val pref: SessionPreferences) : ViewModel() {
//    private val _login = MutableLiveData<LoginResult?>()
//    val login: LiveData<LoginResult?> = _login
//
//    private val _isLoading = MutableLiveData<Boolean>()
//    val isLoading: LiveData<Boolean> = _isLoading
//
//    private val _responseStatus = MutableLiveData<String>()
//    val responseStatus: LiveData<String> = _responseStatus
//
//    private val _state = MutableLiveData<Boolean>()
//    val state: LiveData<Boolean> = _state
//
//    fun postLogin(email: String, password: String): LoginResponse? {
//        _isLoading.value = true
//        _state.value = false
//
//        val loginResponse: LoginResponse? = null
//
//        val client = ConfigApi.getApiService().postLogin(email, password)
//        client.enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                _isLoading.value = false
//                if (response.isSuccessful) {
//                    _state.value = true
//                    _login.value = response.body()?.loginResult
//                    _responseStatus.value = response.body()?.message
//
//                    viewModelScope.launch {
//                        response.body()?.loginResult?.token?.let { pref.login(it) }
//                    }
//                } else {
//                    val jsonError = response.errorBody()?.string()?.let { JSONObject(it) }
//                    _responseStatus.value = jsonError?.getString("message")
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                _isLoading.value = false
//                _responseStatus.value = t.message
//            }
//        })
//
//        return loginResponse
//    }
//}