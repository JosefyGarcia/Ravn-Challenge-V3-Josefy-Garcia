package com.example.mystarwarsapplication.network

import com.apollographql.apollo3.ApolloClient

class StarWarsApi {
    fun apolloCLient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index").build()
    }
}