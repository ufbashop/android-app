package br.ufba.ufbashop.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ufba.ufbashop.R
import br.ufba.ufbashop.objects.Shop
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_shops.*
import com.firebase.ui.database.FirebaseRecyclerOptions
import br.ufba.ufbashop.adapters.ShopAdapter


class ShopsFragment : Fragment() {
    private lateinit var mInstance: FirebaseDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shops, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mInstance = FirebaseDatabase.getInstance()

        val query = mInstance.reference.child("shops")
        val options = FirebaseRecyclerOptions.Builder<Shop>()
            .setQuery(query, Shop::class.java)
            .build()
        val adapter = ShopAdapter(requireActivity(), options)
        adapter.startListening()
        shops_list.layoutManager = LinearLayoutManager(activity)
        shops_list.adapter = adapter
    }

}
