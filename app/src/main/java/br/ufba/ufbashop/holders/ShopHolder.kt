package br.ufba.ufbashop.holders

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.Shop
import br.ufba.ufbashop.util.CanLog
import com.bumptech.glide.Glide
import com.google.firebase.storage.FirebaseStorage


class ShopHolder(inflater: LayoutInflater, parent: ViewGroup) :
    BindHolder<Shop>(inflater, parent, R.layout.item_shop_overview_design_material) {
    private val mShopImageView: ImageView = itemView.findViewById(R.id.shop_logo)
    private val mShopNameTextView: TextView = itemView.findViewById(R.id.item_shop_name)
    private val mShopOpenCloseHourTextView: TextView = itemView.findViewById(R.id.item_shop_open_close)
    private val mShopCampusTextView: TextView = itemView.findViewById(R.id.item_shop_campus)
    private val mShopDescriptionTextView: TextView = itemView.findViewById(R.id.item_shop_description)
    private val mShopLocationTextView: TextView = itemView.findViewById(R.id.item_shop_location)

    override fun bind(item: Shop, context: Context){
        val img = item.logoImage?.let { FirebaseStorage.getInstance().getReference(it) }
        if(img == null)
            mShopImageView.visibility = View.GONE
        else {
            mShopImageView.visibility = View.VISIBLE
            Glide.with(context)
                .load(img)
                .into(mShopImageView)
        }
        mShopNameTextView.text = item.name
        if (CanLog.CAN_LOG) {
            Log.i("VAI SE FUDER EU", "${item.starts}, ${item.ends}, ${item.shopDescription}")
        }
        mShopOpenCloseHourTextView.text = context.resources.getString(R.string.item_open_close,
            item.starts, item.ends)
        mShopCampusTextView.text = item.campus
        mShopDescriptionTextView.text = item.shopDescription
        mShopLocationTextView.text = item.mainSellingLocation
    }
}