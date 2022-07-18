package com.example.mystarwarsapplication.network

import android.os.Looper
import com.apollographql.apollo3.ApolloClient

class StarWarsApi {
    fun apolloCLient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index").build()
    }
}