package br.ufba.ufbashop.util

import android.app.Activity
import android.content.Intent
import br.ufba.ufbashop.activities.MainActivity

object FirebaseUtils {
    fun goBackToMain(activity: Activity) {
        val intent = Intent(activity, MainActivity::class.java)
        activity.startActivity(intent)
        activity.finish()
    }
}