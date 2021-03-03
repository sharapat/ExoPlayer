package uz.usoft.test.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.usoft.test.data.ApiService
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class ApiModule {
    private val baseUrl = "https://api.vimeo.com/"
    @Provides
    @Singleton
    fun providesGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.setLenient().create()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient() : OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val interceptor = Interceptor {
            val original = it.request()
            val request = original.newBuilder()
                    .header("Accept", "application/json")
                    .method(original.method(), original.body())
                    .build()
            it.proceed(request)
        }
        return OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    fun providesRetrofit(gson: Gson): Retrofit = Retrofit.Builder().
        addConverterFactory(GsonConverterFactory.create(gson)).
        addCallAdapterFactory(RxJava3CallAdapterFactory.create()).
        client(provideOkHttpClient()).
        baseUrl(baseUrl).
        build()

    @Provides
    @Singleton
    fun providesApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}