package com.csgames.mixparadise.api

import retrofit2.Call
import retrofit2.http.GET



interface DrinkService {
    // TODO: get ingredients

    @GET("ingredients?key=asdasdsdsfds")
    fun getIngredients(): Call<IngredientsReturnModel>

    // TODO: serve call
    //fun
}

// result generated from /json

data class IngredientsReturnModel(val juices: List<Juices>?, val drinks: List<Drinks>?, val ingredients: List<Ingredients>?, val alcohols: List<Any>?)

data class Drinks(val id: String?, val label: String?, val type: String?, val color: String?, val opacity: Number?, val imageUrl: String?)

data class Ingredients(val id: String?, val label: String?, val type: String?, val weight: Number?, val imageUrl: String?, val sprites: List<String>?)

data class Juices(val id: String?, val label: String?, val type: String?, val color: String?, val opacity: Number?, val imageUrl: String?)
