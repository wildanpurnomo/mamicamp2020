package com.wildanpurnomo.timefighter.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.data.user.UserViewModel
import com.wildanpurnomo.timefighter.ui.main.MainActivity
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    private lateinit var mAppBarConfiguration: AppBarConfiguration

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        setSupportActionBar(authToolbar)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val navController = findNavController(R.id.nav_host_fragment_auth)
        mAppBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, mAppBarConfiguration)

        AuthenticatingDialog.show(
            supportFragmentManager,
            AuthenticatingDialog::class.java.simpleName
        )

        mUserViewModel.authenticate()

        mUserViewModel.getSuccessfulAuthSignal().observe(this, Observer {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        })

        mUserViewModel.getFailedAuthSignal().observe(this, Observer {
            AuthenticatingDialog.dismiss()
        })

        mUserViewModel.getToastMessage().observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_auth)
        return navController.navigateUp(mAppBarConfiguration) || super.onSupportNavigateUp()
    }
}