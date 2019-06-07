package br.ufba.ufbashop.holders

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup


abstract class BindHolder<T>(inflater: LayoutInflater, parent: ViewGroup, layout: Int) :
    RecyclerView.ViewHolder(inflater.inflate(layout, parent, false)) {
    abstract fun bind(item: T, activity: Activity)
}

