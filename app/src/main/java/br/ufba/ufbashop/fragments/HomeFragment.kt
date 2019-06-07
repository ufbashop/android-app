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
import br.ufba.ufbashop.holders.ProductsHolder
import br.ufba.ufbashop.objects.Product
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_product_overview.*
import kotlin.math.roundToInt


class HomeFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.home_fragment, container, false)
        var productsList = view.findViewById<RecyclerView>(R.id.products_list)

        return view
    }

}