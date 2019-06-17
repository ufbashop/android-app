package br.ufba.ufbashop.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ufba.ufbashop.activities.EditShopsActivity
import br.ufba.ufbashop.R
import br.ufba.ufbashop.activities.EditProfileActivity
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_header_profile.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_header_profile.view.*


class ProfileFragment : Fragment() {
    private val mUser : FirebaseUser? = FirebaseAuth.getInstance().currentUser

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val nav = nav_view.getHeaderView(0)
        nav.profile_sign_out.setOnClickListener {
            AuthUI.getInstance()
                .signOut(requireActivity() as Context)
                .addOnCompleteListener {
                    activity?.recreate()
                }.addOnCanceledListener {

                }
        }
        nav_view.setNavigationItemSelectedListener{ item ->
            when(item.itemId) {
                R.id.nav_manage_shops -> {
                    val intent = Intent(requireActivity(), EditShopsActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.nav_edit_profile -> {
                    val intent = Intent(requireActivity(), EditProfileActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

        if(mUser != null) {
            FirebaseDatabase.getInstance().reference
                .child("users").child(mUser.uid)
                .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    profile_username.text = snapshot.child("name").value.toString()
                    val imgPath = snapshot.child("userImage").value.toString()
                    val img = imgPath.let { FirebaseStorage.getInstance().getReference(it) }
                    Glide.with(requireActivity())
                        .load(img)
                        .into(profile_image)
                }
            }
            )
        }
    }

}
