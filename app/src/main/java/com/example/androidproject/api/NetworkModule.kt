package com.example.androidproject.api

import com.example.androidproject.BuildConfig
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonElement
import com.google.gson.JsonPrimitive
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Dagger module for providing network-related dependencies.
 */
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    private const val BASE_URL = BuildConfig.API_URL
    private const val TIMEOUT = 30L
    private const val SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    /**
     * Provides an OkHttpClient instance for making network requests.
     */
    @Provides
    @Singleton
    fun providesOkHttp(): OkHttpClient = OkHttpClient.Builder().apply {
        if (BuildConfig.DEBUG) {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                },
            )
        }
        connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        readTimeout(TIMEOUT, TimeUnit.SECONDS)
    }.build()

    /**
     * Provides a Gson instance for JSON serialization and deserialization.
     */
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setDateFormat(SERVER_DATE_FORMAT)
            .registerTypeAdapter(
                Date::class.java,
                object : JsonSerializer<Date> {
                    private val utcDateFormat = SimpleDateFormat(
                        SERVER_DATE_FORMAT,
                        Locale.ENGLISH,
                    ).apply {
                        timeZone = TimeZone.getTimeZone("UTC")
                    }

                    // Avoid using local date formatter (with timezone) to send UTC date
                    override fun serialize(src: Date?, typeOfSrc: Type?, context: JsonSerializationContext?): JsonElement {
                        return JsonPrimitive(src?.let { utcDateFormat.format(it) })
                    }
                },
            )
            .create()
    }

    /**
     * Provides a Retrofit instance for making API calls.
     */
    @Provides
    @Singleton
    fun providesRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder().apply {
        baseUrl(BASE_URL)
        client(
            okHttpClient.newBuilder().build(),
        )
        addConverterFactory(GsonConverterFactory.create(gson))
    }.build()
}
