package br.ufba.ufbashop.holders

import android.app.Activity
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

abstract class BindHolder<T>(inflater: LayoutInflater, parent: ViewGroup, layout: Int) :
    RecyclerView.ViewHolder(inflater.inflate(layout, parent, false)) {

    abstract fun bind(item: T, context: Activity)

    fun getStorageReference(image: String?) : StorageReference? {
        return image?.let { FirebaseStorage.getInstance().getReference(it) }
    }
}

