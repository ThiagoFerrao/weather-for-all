package thiagocruz.weatherforall.services

import retrofit2.Call
import retrofit2.Response

interface BaseCallback<T> {
    fun onResponse(call: Call<T>?, response: Response<T>?)
    fun onInternalError(call: Call<T>?, response: Response<T>?)
    fun onApiError(call: Call<T>?, t: Throwable?)
    fun onConnectionError(call: Call<T>?, t: Throwable?)
    fun onGeneralError(call: Call<T>?, t: Throwable?)
}
