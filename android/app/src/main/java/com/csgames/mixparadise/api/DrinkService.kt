package com.csgames.mixparadise.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface DrinkService {
    //TODO remove key from here

    @GET("ingredients?key=asdasdsdsfds")
    fun getIngredients(): Call<IngredientsReturnModel>

    @GET("serve?key=asdasdsdsfds")
    fun serve(@Query("id") id: String, @Query("qty") quantity: Int) : ServingReview
}

// result generated from /json

data class IngredientsReturnModel(val juices: List<Juices>?, val drinks: List<Drinks>?, val ingredients: List<Ingredients>?, val alcohols: List<Any>?)

data class Drinks(val id: String?, val label: String?, val type: String?, val color: String?, val opacity: Number?, val imageUrl: String?)

data class Ingredients(val id: String?, val label: String?, val type: String?, val weight: Number?, val imageUrl: String?, val sprites: List<String>?)

data class Juices(val id: String?, val label: String?, val type: String?, val color: String?, val opacity: Number?, val imageUrl: String?)



data class ServingReview(val rating: Rating, val review: Review)

data class Rating(val note: Integer, val comment: String)
data class Review(val taste: Int, val volume: Int, val strength: Int)
