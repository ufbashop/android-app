package br.ufba.ufbashop.dialogs

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TimePicker
import br.ufba.ufbashop.R

import java.util.Calendar
import java.util.Date
class TimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val time = arguments!!.getSerializable(ARG_TIME) as Date
        val calendar = Calendar.getInstance()
        calendar.time = time

        val hours = calendar.get(Calendar.HOUR_OF_DAY)
        val minutes = calendar.get(Calendar.MINUTE)

        val timePicker = TimePicker(activity)
        if (Build.VERSION.SDK_INT >= 23) {
            timePicker.hour = hours
            timePicker.minute = minutes
        } else {
            timePicker.currentHour = hours
            timePicker.currentMinute = minutes
        }

        return AlertDialog.Builder(activity!!)
            .setView(timePicker)
            .setTitle(R.string.time_picker_title)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val hour: Int
                val minute: Int
                if (Build.VERSION.SDK_INT >= 23) {
                    hour = timePicker.hour
                    minute = timePicker.minute
                } else {
                    hour = timePicker.currentHour
                    minute = timePicker.currentMinute
                }
                val newTime = Calendar.getInstance()
                newTime.set(Calendar.HOUR_OF_DAY, hour)
                newTime.set(Calendar.MINUTE, minute)
                val timeTime = newTime.time

                sendResult(Activity.RESULT_OK, timeTime)
            }
            .create()
    }

    private fun sendResult(resultCode: Int, time: Date) {
        if (targetFragment == null)
            return
        val c = Calendar.getInstance()
        c.time = time
        println(c.get(Calendar.HOUR))
        val intent = Intent()
        intent.putExtra(EXTRA_TIME, time)
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    companion object {
        private const val ARG_TIME = "time"
        const val EXTRA_TIME = "extra_time"

        fun newInstance(time: Date): TimePickerFragment {

            val args = Bundle()

            val fragment = TimePickerFragment()

            args.putSerializable(ARG_TIME, time)
            fragment.arguments = args
            return fragment
        }
    }
}
