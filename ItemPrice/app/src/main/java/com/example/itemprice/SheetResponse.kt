package com.example.itemprice

data class SheetResponse(
    val range: String,
    val majorDimension: String,
    val values: List<List<String>> // 2D list, where each list is a row of data
)
