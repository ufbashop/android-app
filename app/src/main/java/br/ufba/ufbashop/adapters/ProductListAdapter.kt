package br.ufba.ufbashop.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import br.ufba.ufbashop.holders.BindHolder
import br.ufba.ufbashop.holders.ProductsHolder
import br.ufba.ufbashop.objects.Product

class ProductListAdapter(products: List<Product>, activity: Activity) :
    BindAdapter<Product>(products, activity) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindHolder<Product> {
        return ProductsHolder(LayoutInflater.from(mContext), parent)
    }

}