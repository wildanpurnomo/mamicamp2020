package com.wildanpurnomo.timefighter.ui.auth

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.wildanpurnomo.timefighter.R
import java.lang.IllegalStateException

object AuthenticatingDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { fragmentActivity ->
            val builder = AlertDialog.Builder(fragmentActivity)
            val root =
                requireActivity().layoutInflater.inflate(R.layout.dialog_authenticating, null)
            builder.setView(root)
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}