package br.ufba.ufbashop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import br.ufba.ufbashop.holders.ShopHolder
import br.ufba.ufbashop.objects.Shop
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class ShopAdapter(context: Context, options: FirebaseRecyclerOptions<Shop>)
    : FirebaseRecyclerAdapter<Shop, ShopHolder>(options) {
    private val mContext = context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHolder {
        return ShopHolder(LayoutInflater.from(mContext), parent)
    }
    override fun onBindViewHolder(holder: ShopHolder, position: Int, model: Shop) {
        holder.bind(model, mContext)
    }
}