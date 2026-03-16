package com.kforeach.pokedex.di

import com.kforeach.pokedex.data.ApiService
import com.kforeach.pokedex.data.Repository
import com.kforeach.pokedex.data.StoreManager
import com.kforeach.pokedex.vm.MainViewModel
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .connectTimeout(90, TimeUnit.SECONDS)
            .readTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    single { get<Retrofit>().create(ApiService::class.java) }
}


val dataModule = module {
    single { StoreManager(androidContext()) }
    single { Repository(context = androidContext(), network = get(), store = get()) }
}

val viewModelModule = module {
    viewModelOf(::MainViewModel)
}

val appModule = listOf(networkModule, dataModule, viewModelModule)
