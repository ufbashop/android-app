package br.ufba.ufbashop.holders

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.Product
import com.bumptech.glide.Glide
import kotlin.math.roundToInt

class ProductsHolder(inflater: LayoutInflater, parent: ViewGroup) :
    BindHolder<Product>(inflater, parent, R.layout.item_product_overview) {
    private var mProductImage : ImageView = itemView.findViewById(R.id.product_image)
    private var mProductPrice: TextView = itemView.findViewById(R.id.product_price)
    private var mProductDiscount: TextView = itemView.findViewById(R.id.product_discount)
    private var mProductDescription: TextView = itemView.findViewById(R.id.product_description)

    override fun bind(item: Product, activity: Activity){
        Glide.with(activity)
            .load(item.mainImage)
            .into(mProductImage)
        mProductPrice.text = activity.resources.getString(
            R.string.price_module, item.price * (1 - item.discount))
        val discount: Double = item.discount * 100
        mProductDiscount.text = activity.resources.getString(
            R.string.discount_module, discount.roundToInt())
        mProductDescription.text = item.description

    }
}