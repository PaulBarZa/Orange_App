package fr.epf.min.projet_paul_barthe_min2

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import fr.epf.min.projet_paul_barthe_min2.api.Product
import fr.epf.min.projet_paul_barthe_min2.api.RetrofitInstance
import fr.epf.min.projet_paul_barthe_min2.data.data
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.coroutines.runBlocking


class HomeActivity : AppCompatActivity() {
    private val ZBAR_CAMERA_PERMISSION = 1
    lateinit var adapter: ProductAdapter
    lateinit var products: ArrayList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)
        this.title = "Favorites"

        list_product_recyclerview.layoutManager =
                LinearLayoutManager(
                        this,
                        LinearLayoutManager.VERTICAL,
                        false
                )

        products = data.GetFavProducts()
        adapter = ProductAdapter(products)
        list_product_recyclerview.adapter = adapter

        image_button_scanne.setOnClickListener {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), ZBAR_CAMERA_PERMISSION)
            } else {
                val intent = Intent(this, ScannerActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            ZBAR_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] === PackageManager.PERMISSION_GRANTED) {
                    val intent = Intent(this, ScannerActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Please accept camera permission to use the QR Scanner", Toast.LENGTH_SHORT).show()
                }
                return
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.home_menu, menu)

        val searchItem = menu?.findItem(R.id.action_search)
        var searchView: SearchView = searchItem?.actionView as SearchView
        searchView.imeOptions = EditorInfo.IME_ACTION_DONE

        searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })

        return true
    }

}