package br.ufba.ufbashop.activities

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_create_shop.*
import android.net.Uri
import android.widget.ArrayAdapter
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.Shop
import br.ufba.ufbashop.util.FirebaseUtils
import br.ufba.ufbashop.util.MaskWatcher
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class CreateShopActivity : PickImageActivity() {

    private lateinit var mInstance: FirebaseDatabase
    private val mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var mShopImage : String? = null

    override fun getStorageReference(): StorageReference {
         return FirebaseStorage.getInstance()
             .reference
             .child(resources.getString(R.string.path_user_pics, mUser!!.uid))
    }

    override fun onImageReceived(path: String?, data: Uri) {
        mShopImage = path
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shop)

        mInstance = FirebaseDatabase.getInstance()
        if(mUser == null) {
            FirebaseUtils.goBackToMain(this)
        }
        form_phone_number.addTextChangedListener(MaskWatcher.buildPhoneNumber())
        form_open_hour.addTextChangedListener(MaskWatcher.buildHour())
        form_close_hour.addTextChangedListener(MaskWatcher.buildHour())
        form_shop_image.setOnClickListener { requestImage(form_shop_image) }
        form_shop_image_button.setOnClickListener { requestImage(form_shop_image) }
        val adapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_item,
                                            resources.getStringArray(R.array.campus))
        form_shop_campus.threshold = 1
        form_shop_campus.keyListener = null
        form_shop_campus.setAdapter(adapter)

        form_no_fixed_location.setOnCheckedChangeListener { _, isChecked ->
            form_shop_location_layout.isEnabled = ! isChecked
        }

        form_send.setOnClickListener {
            saveShop()
        }
    }

    private fun validateForm(): Boolean {
        val open = form_open_hour.text.toString()
        val close = form_close_hour.text.toString()
        val phone = form_phone_number.text.toString()
        var valid = true
        if(open.length < 5 || open.split(":")[0] >= "23" ||
                 open.split(":")[1] >= "60") {
             form_open_hour_layout.error = resources.getString(R.string.hour_error)
             valid = false
        }
        if(close.length < 5 || close.split(":")[0] >= "23" ||
            close.split(":")[1] >= "60") {
            form_close_hour_layout.error = resources.getString(R.string.hour_error)
            valid = false
        }
        if(phone.length < 15){
            form_phone_number_layout.error = resources.getString(R.string.hour_error)
            valid = false
        }
        if(form_shop_name.text.isNullOrEmpty()) {
            form_shop_name_layout.error = resources.getString(R.string.shop_name_error)
            valid = false
        }

        return valid
    }
    private fun saveShop() {
        if(validateForm()) {
            val shopId = UUID.randomUUID().toString()
            val shop = Shop(
                name = form_shop_name.text.toString(),
                logoImage = mShopImage,
                mainSellingLocation = form_shop_location.text.toString(),
                starts = form_open_hour.text.toString(),
                ends = form_close_hour.text.toString(),
                campus = form_shop_campus.text.toString(),
                phoneNumber = form_phone_number.text.toString(),
                shopDescription = form_shop_description.text.toString(),
                userId = mUser!!.uid,
                shopId = shopId
            )
            mInstance.reference
                .child("shops").child(shopId)
                .setValue(shop).addOnSuccessListener {
                    finish()
                }
        }
    }

}