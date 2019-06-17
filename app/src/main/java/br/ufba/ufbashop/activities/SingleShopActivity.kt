package br.ufba.ufbashop.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.Shop
import com.bumptech.glide.Glide
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_single_shop.*

class SingleShopActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_SHOP = "br.ufba.ufbashop.shop"
    }
    private lateinit var mShop: Shop

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_shop)
        val shopId = intent?.getStringExtra(EXTRA_SHOP)
        if(shopId != null) {
            val v = shop_nav_view.getHeaderView(0)
            FirebaseDatabase.getInstance().reference
                .child("shops")
                .child(shopId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {}

                    override fun onDataChange(snapshot: DataSnapshot) {
                        mShop = Shop(
                            name = snapshot.child("name").value.toString(),
                            campus = snapshot.child("campus").value.toString(),
                            shopDescription = snapshot.child("shopDescription").value.toString(),
                            starts = snapshot.child("starts").value.toString(),
                            ends = snapshot.child("ends").value.toString(),
                            mainSellingLocation = snapshot.child("mainSellingLocation").value.toString(),
                            logoImage = snapshot.child("logoImage").value.toString(),
                            shopId = snapshot.child("shopId").value.toString())
                        v.findViewById<TextView>(R.id.item_shop_name).text = mShop.name
                        v.findViewById<TextView>(R.id.item_shop_campus).text = mShop.campus
                        v.findViewById<TextView>(R.id.item_shop_description).text = mShop.shopDescription
                        v.findViewById<TextView>(R.id.item_shop_open_close).text = resources.getString(
                            R.string.item_open_close, mShop.starts, mShop.ends)
                        v.findViewById<TextView>(R.id.item_shop_location).text = mShop.mainSellingLocation
                        val img = mShop.logoImage?.let { FirebaseStorage.getInstance().getReference(it) }
                        val shopLogo = v.findViewById<ImageView>(R.id.shop_logo)
                        if(img == null)
                            shopLogo.visibility = View.GONE
                        else {
                            shopLogo.visibility = View.VISIBLE
                            Glide.with(applicationContext)
                                .load(img)
                                .into(shopLogo)
                        }
                    }
                }
                )
        }

        shop_nav_view.setNavigationItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.nav_shop_products -> {
                    ShopProductsActivity.activityStart(this, mShop.shopId)
                    //val intent = Intent(applicationContext, CreateProductActivity::class.java)
                    //startActivity(intent)
                    true
                }

                else -> false
            }
        }
    }


}