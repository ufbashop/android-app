package br.ufba.ufbashop.activities

import android.net.Uri
import android.os.Bundle
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.User
import br.ufba.ufbashop.util.FirebaseUtils
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.fragment_header_profile.*

class EditProfileActivity: PickImageActivity() {
    private val mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser
    private var mUserImage : String? = null
    private lateinit var mInstance: FirebaseDatabase


    override fun onImageReceived(path: String?, data: Uri) {
        mUserImage = path
    }

    override fun getStorageReference(): StorageReference {
        return FirebaseStorage.getInstance()
            .reference
            .child(resources.getString(R.string.path_user_pics, mUser!!.uid))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(mUser != null) {
            FirebaseDatabase.getInstance().reference
                .child("users").child(mUser.uid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onDataChange(snapshot: DataSnapshot) {
                        edit_profile_username.setText(snapshot.child("name").value.toString())
                        val imgPath = snapshot.child("userImage").value.toString()
                        val img = imgPath.let { FirebaseStorage.getInstance().getReference(it) }
                        Glide.with(applicationContext)
                            .load(img)
                            .into(edit_profile_user_image)
                        mUserImage = imgPath
                    }
                }
             )
        } else {
            FirebaseUtils.goBackToMain(this)
        }
        setContentView(R.layout.activity_edit_profile)
        mInstance = FirebaseDatabase.getInstance()
        if(mUser == null) {
            FirebaseUtils.goBackToMain(this)
        }
        edit_profile_user_image.setOnClickListener {requestImage(edit_profile_user_image)}
        edit_profile_user_image_button.setOnClickListener {requestImage(edit_profile_user_image)}
        profile_send.setOnClickListener { saveUser() }
    }

    private fun validateUser() : Boolean {
        var valid = true
        if(edit_profile_username.text.isNullOrEmpty()) {
            edit_profile_username_layout.error = resources.getString(R.string.username_error)
            valid = false
        }
        return valid
    }
    private fun saveUser(){
        if(validateUser()) {
            val user = User(name=edit_profile_username.text.toString(),
                userImage = mUserImage)
            mInstance.reference
                .child("users").child(mUser!!.uid)
                .setValue(user).addOnSuccessListener {
                    finish()
                }
        }
    }

}