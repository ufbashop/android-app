package br.ufba.ufbashop.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.ufba.ufbashop.MainActivity
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_overview.*
import kotlin.math.roundToInt


class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.home_fragment, container, false)
        var productsList = view.findViewById<RecyclerView>(R.id.products_list)

        return view
    }

    class ProductsAdapter(products: List<Product>, activity: Activity) : RecyclerView.Adapter<ProductsHolder>() {
        private var mProducts: List<Product> = products
        private var mActivity: Activity = activity

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsHolder {
            return ProductsHolder(LayoutInflater.from(mActivity), parent)
        }

        override fun getItemCount(): Int {
            return mProducts.size
        }

        override fun onBindViewHolder(holder: ProductsHolder, position: Int) {
            holder.bind(mProducts[position], mActivity)
        }

    }

    class ProductsHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_product_overview, parent, false)) {
        private var mProductImage : ImageView = itemView.findViewById(R.id.product_image)
        private var mProductPrice: TextView = itemView.findViewById(R.id.product_price)
        private var mProductDiscount: TextView = itemView.findViewById(R.id.product_discount)
        private var mProductDescription: TextView = itemView.findViewById(R.id.product_description)

        fun bind(product: Product, activity: Activity){
            Glide.with(activity)
                .load(product.mainImage)
                .into(mProductImage)
            mProductPrice.text = activity.resources.getString(R.string.price_module, product.price)
            val discount: Double = product.discount * 100
            mProductDiscount.text = activity.resources.getString(
                R.string.discount_module, discount.roundToInt())
            mProductDescription.text = product.description

        }
    }


}