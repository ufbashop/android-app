package br.ufba.ufbashop.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.storage.StorageReference
import java.util.*

abstract class PickImageActivity: AppCompatActivity() {
    private lateinit var mImageView: ImageView

    companion object RequestIds {
        const val PICK_IMAGE = 112
    }

    protected abstract fun onImageReceived(path: String?, data: Uri)

    protected abstract fun getStorageReference(): StorageReference

    protected fun requestImage(imageView: ImageView) {
        mImageView = imageView
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(intent, "Selecione a imagem"),
            PICK_IMAGE
        )
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if(data != null && data.data != null) {
                getStorageReference().child(UUID.randomUUID().toString())
                    .putFile(data.data)
                    .addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            val path = task.result?.metadata?.path
                            onImageReceived(path, data.data)
                            Glide.with(applicationContext)
                                .load(data.data)
                                .into(mImageView)
                        }
                    }.addOnFailureListener {

                    }
            }
        }
    }

}