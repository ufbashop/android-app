package br.ufba.ufbashop.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ufba.ufbashop.CreateShopActivity
import br.ufba.ufbashop.R
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment_header.view.*


class ProfileFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.profile_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = nav_view.getHeaderView(0)
        nav.profile_sign_out.setOnClickListener {
            AuthUI.getInstance()
                .signOut(activity as Context)
                .addOnCompleteListener {
                    activity?.recreate()
                }.addOnCanceledListener {

                }
        }
        nav_view.setNavigationItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.nav_manage_shops -> {
                    val intent = Intent(activity, CreateShopActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

}
