package fr.epf.min.projet_paul_barthe_min2.data

import fr.epf.min.projet_paul_barthe_min2.api.Product

object data {
    private val favorites : ArrayList<Product> = arrayListOf()

    fun AddFavProduct(product: Product){
        if(!isFavProduct(product)){
            favorites.add(product)
        }
    }
    fun GetFavProducts() : ArrayList<Product>{
        return favorites
    }
    fun isFavProduct(product: Product): Boolean{
        return favorites.contains(product)
    }
    fun RemoveFavProduct(product: Product){
        if(isFavProduct(product)){
            favorites.remove(product)
        }
    }
}