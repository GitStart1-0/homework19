package com.example.homework19

import io.reactivex.Single
import retrofit2.http.GET

interface ApiInterface {
    @GET("all.json")
    fun getSuperheroes(): Single<List<Superhero>>
}
