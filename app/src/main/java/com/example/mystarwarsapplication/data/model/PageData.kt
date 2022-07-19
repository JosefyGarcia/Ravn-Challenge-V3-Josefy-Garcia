package com.example.mystarwarsapplication.data.model

data class PageData(
    val hasNextPage: Boolean,
    val hasPreviousPage: Boolean,
    val startCursor: String,
    val endCursor: String,
)