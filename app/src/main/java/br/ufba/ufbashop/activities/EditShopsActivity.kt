package br.ufba.ufbashop.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import br.ufba.ufbashop.R
import br.ufba.ufbashop.adapters.ShopAdapter
import br.ufba.ufbashop.objects.Shop
import br.ufba.ufbashop.util.FirebaseUtils
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_edit.*

class EditShopsActivity : AppCompatActivity() {
    private lateinit var mInstance: FirebaseDatabase
    private val mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)
        mInstance = FirebaseDatabase.getInstance()

        if(mUser != null) {
            val query = mInstance.reference.child("shops")
                .orderByChild("userId")
                .startAt(mUser.uid).endAt(mUser.uid)
            val options = FirebaseRecyclerOptions.Builder<Shop>()
                .setQuery(query, Shop::class.java)
                .build()
            val adapter = ShopAdapter(this, options)
            adapter.startListening()
            user_recycler_view.layoutManager =
                LinearLayoutManager(applicationContext)
            user_recycler_view.adapter = adapter
            add_shop_button.setOnClickListener {
                val intent = Intent(applicationContext, CreateShopActivity::class.java)
                startActivity(intent)
            }
        } else {
            FirebaseUtils.goBackToMain(this)
        }
    }
}