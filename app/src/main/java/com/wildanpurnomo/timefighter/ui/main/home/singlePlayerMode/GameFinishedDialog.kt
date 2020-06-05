package com.wildanpurnomo.timefighter.ui.main.home.singlePlayerMode

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.wildanpurnomo.timefighter.R
import java.lang.IllegalStateException

class GameFinishedDialog : DialogFragment() {

    companion object {
        const val EXTRA_FINAL_SCORE = "extra_final_score"

        fun newInstance(finalScore: Int): GameFinishedDialog {
            val fragment = GameFinishedDialog()
            val bundle = Bundle()
            bundle.putInt(EXTRA_FINAL_SCORE, finalScore)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let { fragmentActivity ->
            val finalScore = arguments?.getInt(EXTRA_FINAL_SCORE)
            val builder = AlertDialog.Builder(fragmentActivity)
            builder.setMessage(getString(R.string.end_game_dialog_message, finalScore ?: 0))
                .setPositiveButton(
                    R.string.dialog_ok
                ) { dialog, _ ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}