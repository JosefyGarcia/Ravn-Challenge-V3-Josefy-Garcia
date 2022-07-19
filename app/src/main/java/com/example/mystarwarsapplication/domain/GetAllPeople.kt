package com.example.mystarwarsapplication.domain

import com.example.mystarwarsapplication.data.repository.PeopleRepository
import com.example.mystarwarsapplication.data.model.PageData
import com.example.mystarwarsapplication.data.model.PeopleData
import com.example.mystarwarsapplication.data.model.PersonData
import javax.inject.Inject

class GetAllPeople @Inject constructor(
    private val repository: PeopleRepository
    ) {
    suspend operator fun invoke(cursor: String?): PeopleData {
        val query = repository.getAllPeople(cursor)
        val pageInfo = query.data?.allPeople?.pageInfo

        return PeopleData(
            PageData(
                pageInfo?.hasNextPage ?: false,
                pageInfo?.hasPreviousPage ?: false,
                pageInfo?.startCursor!!,
                pageInfo.endCursor!!,
            ),
            query.data?.allPeople?.edges?.map {
                PersonData(
                    it?.node?.id!!,
                    it.node.name,
                    it.node.species?.name ?: "Human",
                    it.node.homeworld?.name
                )
            }!!
        )
    }
}