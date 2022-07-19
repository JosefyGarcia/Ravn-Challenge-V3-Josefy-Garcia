package com.example.mystarwarsapplication.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.example.mystarwarsapplication.GetAllPeopleQuery
import com.example.mystarwarsapplication.GetPersonInfoQuery

interface PeopleRepository {
    suspend fun getAllPeople(cursor: String?): ApolloResponse<GetAllPeopleQuery.Data>
    suspend fun getPersonInfo(id: String): ApolloResponse<GetPersonInfoQuery.Data>
}