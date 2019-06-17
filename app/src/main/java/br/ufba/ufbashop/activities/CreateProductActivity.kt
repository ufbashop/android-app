package br.ufba.ufbashop.activities

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_product.*
import android.widget.ArrayAdapter
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.Product
import br.ufba.ufbashop.util.FirebaseUtils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.hootsuite.nachos.terminator.ChipTerminatorHandler
import kotlinx.android.synthetic.main.activity_create_product.*


class CreateProductActivity : PickImageActivity() {
    private lateinit var mInstance: FirebaseDatabase
    private lateinit var mShopId: String
    private val mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var mProductImage : String? = null

    companion object {
        const val EXTRA_SHOP_ID = "br.ufba.ufbashop.product_shop_id"

        fun activityStart(context: Context, shopId: String){
            val i = Intent(context, CreateProductActivity::class.java)
            i.putExtra(EXTRA_SHOP_ID, shopId)
            context.startActivity(i)
        }
    }

    override fun onImageReceived(path: String?, data: Uri) {
        mProductImage = path
    }

    override fun getStorageReference(): StorageReference {
        return FirebaseStorage.getInstance()
            .reference
            .child(resources.getString(R.string.path_user_pics, mUser!!.uid))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)
        mInstance = FirebaseDatabase.getInstance()
        if(mUser == null) {
            FirebaseUtils.goBackToMain(this)
        }
        mShopId = intent.getStringExtra(EXTRA_SHOP_ID) ?: ""
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,
            resources.getStringArray(R.array.tags_suggestions))
        tag_chip_layout.setAdapter(adapter)
        tag_chip_layout.addChipTerminator(',', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR)

        product_image_view.setOnClickListener {requestImage(product_image_view)}
        product_image_button.setOnClickListener {requestImage(product_image_view)}

        send_fab.setOnClickListener {
            saveProduct()
        }
    }

    private fun validateProduct(): Boolean {
        var valid = true
        if(product_name.text.isNullOrEmpty()){
            product_name_layout.error = "Insira um nome para o produto"
            valid = false
        }
        if(product_price.text.isNullOrEmpty()) {
            product_price_layout.error = "Insira um preço"
        }
        return valid
    }

    private fun saveProduct(){
        if(validateProduct() && mShopId.isNotEmpty()) {
            val discount = if (product_discount.text.isNullOrEmpty()) 0.0
            else product_discount.text.toString().toDouble() / 100
            val product = Product(
                name = product_name.text.toString(),
                price = product_price.text.toString().toDouble(),
                discount = discount,
                description = product_description_form.text.toString(),
                tags = tag_chip_layout.chipValues,
                mainImage = mProductImage,
                used = product_used.isChecked
            )
            mInstance.reference
                .child("products")
                .child(mShopId)
                .push()
                .setValue(product).addOnSuccessListener {
                    finish()
                }
        }
    }
}