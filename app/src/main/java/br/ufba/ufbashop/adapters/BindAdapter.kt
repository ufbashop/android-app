package br.ufba.ufbashop.adapters

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import br.ufba.ufbashop.holders.BindHolder

abstract class BindAdapter<T>(items: List<T>, context: Context) : RecyclerView.Adapter<BindHolder<T>>() {
    private var mItems: List<T> = items
    protected var mContext: Context = context

    override fun getItemCount(): Int {
        return mItems.size
    }

    override fun onBindViewHolder(holder: BindHolder<T>, position: Int) {
        holder.bind(mItems[position], mContext)
    }


}