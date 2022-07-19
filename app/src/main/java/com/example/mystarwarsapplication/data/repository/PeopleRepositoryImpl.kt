package com.example.mystarwarsapplication.data.repository

import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Optional
import com.example.mystarwarsapplication.GetAllPeopleQuery
import com.example.mystarwarsapplication.GetPersonInfoQuery
import com.example.mystarwarsapplication.network.StarWarsApi
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(
    private val api: StarWarsApi
) : PeopleRepository {
    override suspend fun getAllPeople(cursor: String?): ApolloResponse<GetAllPeopleQuery.Data> {

        return api.apolloCLient()
            .query(GetAllPeopleQuery(Optional.presentIfNotNull(cursor))).execute()
    }

    override suspend fun getPersonInfo(id: String): ApolloResponse<GetPersonInfoQuery.Data> {

        return  api.apolloCLient()
            .query(GetPersonInfoQuery(Optional.presentIfNotNull(id))).execute()
    }

}
