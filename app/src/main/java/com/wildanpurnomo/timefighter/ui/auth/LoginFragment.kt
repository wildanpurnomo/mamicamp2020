package com.wildanpurnomo.timefighter.ui.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.data.user.UserViewModel
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private val mUserViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragLoginTVRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginPage_to_registerPage)
        }

        fragLoginBtn.setOnClickListener {
            val email = fragLoginEmailET.text.toString()
            val password = fragLoginPasswordET.text.toString()
            mUserViewModel.login(email, password)
            AuthenticatingDialog.show(
                requireActivity().supportFragmentManager,
                AuthenticatingDialog::class.java.simpleName
            )
        }
    }
}