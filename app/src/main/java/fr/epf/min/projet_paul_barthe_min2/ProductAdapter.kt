package fr.epf.min.projet_paul_barthe_min2

import android.content.Intent
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.epf.min.projet_paul_barthe_min2.api.Product
import fr.epf.min.projet_paul_barthe_min2.utils.transformNutriscore
import kotlinx.android.synthetic.main.product_view.view.*


class ProductAdapter(private val products : ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(), Filterable{

    private val productList: ArrayList<Product> = ArrayList(products)

    class ProductViewHolder(val productView: View) : RecyclerView.ViewHolder(productView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            val layoutInflater : LayoutInflater = LayoutInflater.from(parent.context)
            val view : View = layoutInflater.inflate(R.layout.product_view, parent, false)
            return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        if (product.generic_name.isNotEmpty()){
            holder.productView.product_name_textview.text =
                    "${product.generic_name}"
        }else{
            holder.productView.product_name_textview.text = "Name not found"
        }


        val ingredients = "Ingredients: ${reduceIngredientsList(product.ingredients_text)}"
        val nutriscore = "Nutriscore: ${product.nutriscore_grade.toUpperCase()}"
        val color = transformNutriscore(product.nutriscore_grade)[1]

        val spannableIngredientsString = SpannableString(ingredients)
        val spannableScoreString = SpannableString(nutriscore)

        spannableIngredientsString.setSpan(ForegroundColorSpan(Color.BLACK),
                0, 12, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableScoreString.setSpan(ForegroundColorSpan(Color.parseColor(color)),
                12, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        holder.productView.product_ingredient_textview.text = spannableIngredientsString

        holder.productView.product_score_textview.text = spannableScoreString

        Glide.with(holder.productView)
            .load(product.image_url)
            .into(holder.productView.product_imageview)

        holder.productView.setOnClickListener {
            with(it.context){
                val intent = Intent(this, DetailsProductActivity::class.java)
                intent.putExtra("code", products[position]._id)
                startActivity(intent)
            }
        }
    }

    override fun getItemCount() =  products.size

    override fun getFilter(): Filter {
        return productFilter
    }

    private fun reduceIngredientsList(ingredients_text: String): String{
        if (ingredients_text.count() > 100){
            return ingredients_text.slice(0..100) + " ..."
        }
        return ingredients_text
    }

    private val productFilter: Filter = object : Filter() {

        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList: ArrayList<Product> = ArrayList()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(productList)
            } else {
                val filterPattern = constraint.toString().toLowerCase().trim { it <= ' ' }
                for (product in productList) {
                    if (product.generic_name.toLowerCase().contains(filterPattern)) {
                        filteredList.add(product)
                    }
                }
            }

            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            products.clear()
            products.addAll(results.values as List<Product>)
            Log.d("EPF_", "Changement")
            notifyDataSetChanged()
        }
    }
}
