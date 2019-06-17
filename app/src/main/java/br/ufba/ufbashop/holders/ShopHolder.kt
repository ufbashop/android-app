package br.ufba.ufbashop.holders

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import br.ufba.ufbashop.R
import br.ufba.ufbashop.activities.SingleShopActivity
import br.ufba.ufbashop.objects.Shop
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

    override fun bind(item: Shop, context: Activity) {
        itemView.setOnClickListener {
            val intent = Intent(context, SingleShopActivity::class.java)
            intent.putExtra(SingleShopActivity.EXTRA_SHOP, item.shopId)
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                context, it, "shop_transition")
            context.startActivity(intent, options.toBundle())
        }

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
        mShopOpenCloseHourTextView.text = context.resources.getString(R.string.item_open_close,
            item.starts, item.ends)
        mShopCampusTextView.text = item.campus
        mShopDescriptionTextView.text = item.shopDescription
        mShopLocationTextView.text = item.mainSellingLocation
    }



}