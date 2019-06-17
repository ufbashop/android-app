package br.ufba.ufbashop.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import br.ufba.ufbashop.R
import br.ufba.ufbashop.adapters.ProductListAdapter
import br.ufba.ufbashop.objects.Product
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_shops.*


class HomeFragment : Fragment() {
    private lateinit var mInstance: FirebaseDatabase

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_shops, container, false)
    }

    private var products : ArrayList<Product> = arrayListOf()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mInstance = FirebaseDatabase.getInstance()


        mInstance.reference.child("products")
            .addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                (snapshot.children).forEach {
                    //println("IT 1: (((${it.value.toString()})))")
                    (it.children).forEach {it2 -> //println("IT 2: (((${it2.value.toString()})))")
                        val tags = ArrayList<String>()
                        (it2.child("tags").children).forEach { it3 -> //println("IT 3: (((${it3.value.toString()})))")
                            tags.add(it3.value.toString())
                        }

                        println("Adding products...")
                        val add = products.add(Product(
                            name = it2.child("name").value.toString(),
                            price = it2.child("price").value.toString().toDouble(),
                            discount = it2.child("discount").value.toString().toDouble(),
                            mainImage = it2.child("mainImage").value.toString(),
                            used = it2.child("used").value.toString() == "true",
                            available = it2.child("available").value.toString() == "true",
                            description = it2.child("description").value.toString(),
                            tags = tags))
                    }
                }
            }
        })
        println("Products size: ${products.size}")
        shops_list.adapter = ProductListAdapter(products, requireActivity())
        shops_list.layoutManager = GridLayoutManager(requireActivity(), 2)
    }
}