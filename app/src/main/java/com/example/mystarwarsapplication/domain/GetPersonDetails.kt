package com.example.mystarwarsapplication.domain

import com.example.mystarwarsapplication.data.repository.PeopleRepository
import com.example.mystarwarsapplication.data.model.PersonDetails
import javax.inject.Inject

class GetPersonDetails @Inject constructor(private val repository: PeopleRepository) {
    suspend operator fun invoke(id: String): PersonDetails {
        val query = repository.getPersonInfo(id)
        val response = query.data?.person

        return try {
            PersonDetails(
                response?.name ?: UNDEFINED,
                response?.eyeColor ?: UNDEFINED,
                response?.hairColor ?: UNDEFINED,
                response?.skinColor ?: UNDEFINED,
                response?.birthYear ?: UNDEFINED,
                response?.vehicleConnection?.edges?.map {
                    it?.node?.name?.toString() ?: UNDEFINED
                }
            )
        } catch (e: Exception) {
            PersonDetails(UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED, UNDEFINED, null)
        }
    }

    companion object {
        const val UNDEFINED: String = "undefined"
    }
}