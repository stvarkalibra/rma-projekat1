package com.example.movie.core.di

import com.example.movie.core.network.HttpClientFactory
import com.example.movie.core.network.MoviesApi
import com.example.movie.core.network.createMoviesApi
import de.jensklingenberg.ktorfit.Ktorfit
import io.ktor.client.HttpClient
import org.koin.dsl.module

private const val BASE_URL = "https://rma.finlab.rs/"

val networkModule = module {

    single {
        HttpClientFactory.create()
    }

    single {
        Ktorfit.Builder()
            .baseUrl(BASE_URL)
            .httpClient(get<HttpClient>())
            .build()
    }

    single<MoviesApi> {
        get<Ktorfit>().createMoviesApi()
    }
}