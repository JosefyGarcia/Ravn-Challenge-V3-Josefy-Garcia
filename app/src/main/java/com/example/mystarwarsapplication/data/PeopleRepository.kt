package com.example.mystarwarsapplication.data

import com.apollographql.apollo3.api.ApolloResponse
import com.example.mystarwarsapplication.GetAllPeopleQuery
import com.example.mystarwarsapplication.GetPersonInfoQuery

interface PeopleRepository {
    suspend fun getAllPeople(cursor: String): ApolloResponse<GetAllPeopleQuery.Data>
    suspend fun getPersonInfo(cursor: String): ApolloResponse<GetPersonInfoQuery.Data>
}