package thiagocruz.weatherforall.utils

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import thiagocruz.weatherforall.BuildConfig
import java.lang.reflect.Field
import java.util.concurrent.TimeUnit

object RetrofitUtil {

    fun buildRequest(): Retrofit {
        val client = client()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.OPEN_WEATHER_MAP_API_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder()))
                .client(client)
                .build()
    }

    private fun client(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)

        if (BuildConfig.DEBUG) {
            httpClient.addInterceptor(getLoggingInterceptor())
        }

        return httpClient.build()
    }

    private fun getLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY

        return logging
    }

    private fun gsonBuilder(): Gson {
        val customPolicy = { f: Field -> f.name.toLowerCase() }

        return GsonBuilder()
                .setFieldNamingStrategy(customPolicy)
                .create()
    }
}
