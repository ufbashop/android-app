package br.ufba.ufbashop

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_create_shop.*
import br.ufba.ufbashop.util.MaskEditUtil
import android.content.Intent
import android.provider.MediaStore
import android.widget.ArrayAdapter
import br.ufba.ufbashop.objects.Shop
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.util.*


class CreateShopActivity : AppCompatActivity() {
    companion object RequestIds {
        const val PICK_IMAGE = 112
    }

    private lateinit var mInstance: FirebaseDatabase
    private lateinit var mStorageRef: StorageReference
    private val mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var mShopImage : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_shop)

        mInstance = FirebaseDatabase.getInstance()
        if(mUser == null) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
        mStorageRef = FirebaseStorage.getInstance()
            .reference
            .child("shops/${mUser!!.uid}/images")
        MaskEditUtil.makeMask(form_phone_number, MaskEditUtil.FORMAT_PHONE)
        MaskEditUtil.makeMask(form_open_hour, MaskEditUtil.FORMAT_HOUR)
        MaskEditUtil.makeMask(form_close_hour, MaskEditUtil.FORMAT_HOUR)
        form_shop_image_card.setOnClickListener { requestImage() }

        val adapter = ArrayAdapter<String>(this, android.R.layout.select_dialog_item,
                                            resources.getStringArray(R.array.campus))
        form_shop_campus.threshold = 1
        form_shop_campus.setAdapter(adapter)

        form_no_fixed_location.setOnCheckedChangeListener { _, isChecked ->
            form_shop_location.isEnabled = ! isChecked
        }

        form_send.setOnClickListener {
            saveShop()
        }
    }


    private fun saveShop() {
        val shop = Shop(
            name = form_shop_name.text.toString(),
            logoImage = mShopImage,
            mainSellingLocation = form_shop_location.text.toString(),
            starts = form_open_hour.text.toString(),
            ends = form_close_hour.text.toString(),
            campus = form_shop_campus.text.toString(),
            ratings = arrayListOf(),
            products = arrayListOf(),
            phoneNumber = form_phone_number.text.toString(),
            shopDescription = form_shop_description.text.toString()
        )
        if(mUser != null) {
            mInstance.reference.child("shops")
                .child(mUser.uid)
                .setValue(shop).addOnSuccessListener {
                    finish()
                }
        }

    }

    private fun requestImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Selecione a imagem"), PICK_IMAGE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null && data.data != null) {
                Glide.with(this)
                    .load(data)
                    .into(form_shop_image)
                mStorageRef.child(UUID.randomUUID().toString())
                    .putFile(data.data)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            mShopImage = task.result?.metadata?.path
                            Glide.with(this)
                                .load(data)
                                .into(form_shop_image)
                        }
                    }.addOnFailureListener {

                    }
            }
        }
    }

}