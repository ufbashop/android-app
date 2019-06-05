package br.ufba.ufbashop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import br.ufba.ufbashop.fragments.HomeFragment
import br.ufba.ufbashop.objects.Comment
import br.ufba.ufbashop.objects.Shop
import br.ufba.ufbashop.objects.ShopRating
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class MainActivity : AppCompatActivity() {
    object RequestIds {
        const val RC_SIGN_IN = 0
    }

    //private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                openFragment(HomeFragment())
                //textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_desire_list -> {
                //textMessage.setText(R.string.desire_list)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //textMessage.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.navigation_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build())


        if(FirebaseAuth.getInstance().currentUser != null) {
            writeThisShit()
        } else {
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers)
                    .build(),
                RequestIds.RC_SIGN_IN
            )
        }
    }

    private fun writeThisShit() {
        val shop = Shop(
            name = "Loja de Teste",
            mainSellingLocation = "PAF I",
            score = 4.53,
            ratings = arrayListOf (
                ShopRating(
                    score = 5.0,
                    comment = Comment(
                        comment="Bom produto de fato",
                        timestamp = Date().time
                    )
                ),
                ShopRating(
                    score = 5.0,
                    comment = Comment(
                        comment="produto bonito",
                        timestamp = Date().time
                    )
                ),
                ShopRating(
                    score = 5.0,
                    comment = Comment(
                        comment="Praaaaaaaaa",
                        timestamp = Date().time
                    )
                ),
                ShopRating(
                    score = 5.0,
                    comment = Comment(
                        comment="Nem sei q to fznd",
                        timestamp = Date().time
                    )
                ),
                ShopRating(
                    score = 1.0,
                    comment = Comment(
                        comment="mds q lixo",
                        timestamp = Date().time
                    )
                ),
                ShopRating(
                    score = 2.0,
                    comment = Comment(
                        comment="se mate vendedor, que lixo de produto",
                        timestamp = Date().time
                    )
                )
            ),
            products = arrayListOf(),
            sellers = arrayListOf(),
            logo = ""
        )

        val dataRef  = FirebaseDatabase.getInstance().getReference("shops")
        val key = dataRef.push().key?: "penisu"
        shop.uuid = key
        dataRef.setValue(shop)
        Toast.makeText(this, "FUck this shit", Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RequestIds.RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

            } else {

            }
        }
    }


}
