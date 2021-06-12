package fr.epf.min.projet_paul_barthe_min2.api

import retrofit2.http.GET
import retrofit2.http.Path


interface ProductService {
    @GET("/api/v0/product/{code}.json")
    suspend fun getProductByCode(@Path("code") code: String) : GetProduct
}
data class GetProduct(val code: String, val product: Product)

data class Product(
        val _id: String ="",
        val image_url: String = "",
        val nutriscore_grade: String = "",
        val ingredients_text: String = "",
        val generic_name: String = "",
        val ecoscore_grade: String = ""
)