package com.ngedev.newsapplication.data.di

import androidx.room.Room
import com.ngedev.newsapplication.BuildConfig
import com.ngedev.newsapplication.data.repository.ArticleRepository
import com.ngedev.newsapplication.data.source.local.LocalDataSource
import com.ngedev.newsapplication.data.source.local.LocalDatabase
import com.ngedev.newsapplication.data.source.remote.ArticleRemote
import com.ngedev.newsapplication.data.source.remote.service.ApiService
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DataInjection {
    private val httpClient: OkHttpClient.Builder = OkHttpClient.Builder().apply {
        addInterceptor {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            val request: Request =
                it.request().newBuilder().addHeader("x-api-key", BuildConfig.API_KEY).build()
            it.proceed(request)
        }
    }

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create()).build()


    private fun retrofitModule() = module {
        single {
            retrofit.create(ApiService::class.java)
        }
    }

    private fun roomModule() = module {
        single {
            Room.databaseBuilder(
                androidContext(),
                LocalDatabase::class.java,
                "articles.db"
            ).fallbackToDestructiveMigration().build()
        }

        factory {
            get<LocalDatabase>().articleDao()
        }

        factory {
            get<LocalDatabase>().remoteKeysDao()
        }

        factory {
            get<LocalDatabase>().favoriteDao()
        }
    }

    fun injects() = module {
        includes(
            retrofitModule(),
            roomModule(),
            ArticleRepository.inject(),
            ArticleRemote.inject(),
            LocalDataSource.inject()
        )
    }

}