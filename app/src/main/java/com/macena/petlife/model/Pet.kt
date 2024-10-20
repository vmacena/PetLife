package com.macena.petlife.model

data class Pet(
    val name: String,
    val birthDate: String,
    val type: String,
    val color: String,
    val size: String,
    val lastVetVisit: String,
    val lastVaccination: String,
    val lastPetShopVisit: String
)