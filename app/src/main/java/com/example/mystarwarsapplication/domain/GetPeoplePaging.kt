package com.example.mystarwarsapplication.domain

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mystarwarsapplication.data.repository.PeopleRepository
import com.example.mystarwarsapplication.data.model.PersonData
import javax.inject.Inject

class GetPeoplePaging @Inject constructor(
    private val peopleRepository: PeopleRepository,
) :
    PagingSource<String, PersonData>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, PersonData> {
        return try {
            var prevCursor: String? = null
            var nextCursor: String? = null
            val cursor = params.key
            var response = peopleRepository.getAllPeople(cursor)
            val pageInfo = response.data?.allPeople?.pageInfo

            if (pageInfo?.hasNextPage == true) {
                nextCursor = pageInfo?.endCursor
            }
            if (pageInfo?.hasPreviousPage == true) {
                prevCursor = pageInfo?.startCursor
            }
            val personModel = response.data?.allPeople?.edges?.map {
                PersonData(
                    it?.node?.id!!,
                    it?.node?.name,
                    it?.node?.species?.name ?: "Human",
                    it?.node?.homeworld?.name
                )
            }!!

            LoadResult.Page(
                data = personModel,
                prevKey = prevCursor,
                nextKey = nextCursor
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<String, PersonData>): String? {
        return null
    }


}