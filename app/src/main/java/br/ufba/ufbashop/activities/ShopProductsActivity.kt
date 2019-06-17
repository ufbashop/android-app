package br.ufba.ufbashop.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

import br.ufba.ufbashop.R
import br.ufba.ufbashop.adapters.ProductAdapter
import br.ufba.ufbashop.objects.Product
import br.ufba.ufbashop.util.FirebaseUtils
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit.*

class ShopProductsActivity: AppCompatActivity() {
    private lateinit var mInstance: FirebaseDatabase
    private val mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    companion object {
        private const val EXTRA_SHOP_ID = "br.ufba.ufbashop.Products.shopid"

        fun activityStart(context: Context, shopId: String) {
            val intent = Intent(context, ShopProductsActivity::class.java)
            intent.putExtra(EXTRA_SHOP_ID, shopId)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        mInstance = FirebaseDatabase.getInstance()
        val shopId = intent?.getStringExtra(EXTRA_SHOP_ID)
        if(mUser != null && shopId != null) {
            val query = mInstance.reference
                .child("products")
                .child(shopId)
            val options = FirebaseRecyclerOptions.Builder<Product>()
                .setQuery(query, Product::class.java)
                .build()
            val adapter = ProductAdapter(this, options)
            adapter.startListening()
            user_recycler_view.layoutManager =
                GridLayoutManager(applicationContext, 2)
            user_recycler_view.adapter = adapter
            add_shop_button.setOnClickListener {
                CreateProductActivity.activityStart(this, shopId)
            }
        } else {
            FirebaseUtils.goBackToMain(this)
        }
    }
}