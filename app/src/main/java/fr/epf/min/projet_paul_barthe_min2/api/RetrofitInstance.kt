package fr.epf.min.projet_paul_barthe_min2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val FOOD_FACTS_URL = "https://world.openfoodfacts.org/"

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
                .baseUrl(FOOD_FACTS_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    val api: ProductService by lazy {
        retrofit.create(ProductService::class.java)
    }

}