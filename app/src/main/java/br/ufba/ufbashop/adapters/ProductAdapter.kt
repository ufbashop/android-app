package br.ufba.ufbashop.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import br.ufba.ufbashop.holders.ProductsHolder
import br.ufba.ufbashop.objects.Product
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ProductAdapter(context: Activity, options: FirebaseRecyclerOptions<Product>)
    : FirebaseRecyclerAdapter<Product, ProductsHolder>(options) {
    private val mContext = context

    override fun onBindViewHolder(holder: ProductsHolder, p1: Int, model: Product) {
        holder.bind(model, mContext)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
        return ProductsHolder(LayoutInflater.from(mContext), parent)
    }

}