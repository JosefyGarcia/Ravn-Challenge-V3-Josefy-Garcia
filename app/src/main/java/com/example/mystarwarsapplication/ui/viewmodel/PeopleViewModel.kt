package com.example.mystarwarsapplication.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.mystarwarsapplication.data.model.PersonDetails
import com.example.mystarwarsapplication.domain.GetPeoplePaging
import com.example.mystarwarsapplication.domain.GetPersonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.launch


@HiltViewModel
class PeopleViewModel @Inject constructor(
    private val getPersonDetails: GetPersonDetails, private val getPeoplePaging: GetPeoplePaging) : ViewModel() {

    companion object {
        const val SH_NAME = "personName"
        const val SH_BIRTH = "personBirth"
        const val SH_EYE = "personEye"
        const val SH_SKIN = "personSkin"
        const val SH_HAIR = "personHair"
        const val SH_VEHICLES = "personVehicles"
        const val SHARE_PRE_NAME = "shpFavorite"

    }
    val pager = Pager(
        PagingConfig(pageSize = 5)
    ) {
        getPeoplePaging
    }.flow.cachedIn(viewModelScope)

    val personDetails = MutableLiveData<PersonDetails>()
    var isDetailsViewActive = MutableLiveData<Boolean>()

    fun initViewModel() {
        isDetailsViewActive.postValue(false)
    }

    fun loadPersonDetails(id: String) {
        viewModelScope.launch {
            personDetails.postValue(getPersonDetails(id)!!)
        }
    }
}

