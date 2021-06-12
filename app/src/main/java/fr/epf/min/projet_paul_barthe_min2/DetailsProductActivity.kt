package fr.epf.min.projet_paul_barthe_min2

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.bumptech.glide.Glide
import fr.epf.min.projet_paul_barthe_min2.api.Product
import fr.epf.min.projet_paul_barthe_min2.api.RetrofitInstance
import fr.epf.min.projet_paul_barthe_min2.data.data
import fr.epf.min.projet_paul_barthe_min2.utils.transformEcocore
import fr.epf.min.projet_paul_barthe_min2.utils.transformNutriscore
import kotlinx.android.synthetic.main.activity_details_product.*
import kotlinx.coroutines.runBlocking

class DetailsProductActivity : AppCompatActivity() {

    private lateinit var currentProduct: Product
    private var myMenu: Menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_product)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val code = intent.getStringExtra("code")

        runBlocking {
            if (code != null){
                currentProduct = RetrofitInstance.api.getProductByCode(code).product

                product_details_name_textview.text =
                        "${currentProduct.generic_name}"

                product_details_ingredient_textview.text =
                        "Ingredients: ${currentProduct.ingredients_text}"

                displayNutriscore(currentProduct.nutriscore_grade)
                displayEcoscore(currentProduct.ecoscore_grade)

                Glide.with(this@DetailsProductActivity)
                        .load(currentProduct.image_url)
                        .into(product_details_imageview)
            }
        }
        this.title = "Product details"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.detail_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        if(data.isFavProduct(currentProduct)) {
            menu?.getItem(0)?.setIcon(R.drawable.ic_baseline_favorite_24)
        }else{
            menu?.getItem(0)?.setIcon(R.drawable.ic_baseline_favorite_border_24)
        }
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_product_action -> {
                if(!data.isFavProduct(currentProduct)) {
                    data.AddFavProduct(currentProduct)
                    item.setIcon(R.drawable.ic_baseline_favorite_24)
                }else{
                    data.RemoveFavProduct(currentProduct)
                    item.setIcon(R.drawable.ic_baseline_favorite_border_24)
                }
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun displayNutriscore(nutriscore: String){
        val scoreInfo = transformNutriscore(nutriscore)
        product_details_nutriscore_textview.text = scoreInfo[0]
        product_details_nutriscore_textview.setTextColor(Color.parseColor(scoreInfo[1]))
    }

    private fun displayEcoscore(ecoscore: String){
        val scoreInfo = transformEcocore(ecoscore)
        product_details_ecoscore_textview.text = scoreInfo[0]
        product_details_ecoscore_textview.setTextColor(Color.parseColor(scoreInfo[1]))
    }
}