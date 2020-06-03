package com.wildanpurnomo.timefighter.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.data.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_register.*

class RegisterFragment : Fragment() {

    private val mUserViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragRegisterBtn.setOnClickListener {
            val username = fragRegisterUsernameET.text.toString()
            val email = fragRegisterEmailET.text.toString()
            val password = fragRegisterPasswordET.text.toString()
            mUserViewModel.register(username, email, password)
            AuthenticatingDialog.show(
                requireActivity().supportFragmentManager,
                AuthenticatingDialog::class.java.simpleName
            )
        }
    }
}