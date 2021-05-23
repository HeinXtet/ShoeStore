package com.deevvdd.shoestore.model

data class ShoeModel(
    val title: String = "",
    val prize: Double = 0.0,
    val company: String = "",
    val quantity: Int = 0,
    val imageUrl: String = ""
)