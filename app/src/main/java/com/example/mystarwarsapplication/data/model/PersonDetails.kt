package com.example.mystarwarsapplication.data.model

data class PersonDetails(
    val name: String,
    val eyeColor: String,
    val hairColor: String,
    val skinColor: String,
    val birthYear: String,
    val vehicles: List<String>?
)