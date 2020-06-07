package com.wildanpurnomo.timefighter.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.wildanpurnomo.timefighter.R
import com.wildanpurnomo.timefighter.data.user.UserViewModel
import com.wildanpurnomo.timefighter.ui.auth.AuthActivity
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val logoutItem = navView.menu.findItem(R.id.nav_sign_out)
        logoutItem.setOnMenuItemClickListener {
            mUserViewModel.signOut()
            return@setOnMenuItemClickListener true
        }
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,
                R.id.nav_leaderboard,
                R.id.nav_sign_out
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        mUserViewModel.setLoggedUserData()

        mUserViewModel.getLoggedUser().observe(this, Observer {
            navView.getHeaderView(0).findViewById<TextView>(R.id.headerUsername).text =
                it.username.toString()
            navView.getHeaderView(0).findViewById<TextView>(R.id.headerMaximumScore).text =
                String.format("Maximum Score: %d", it.maximumScore ?: 0)
        })

        mUserViewModel.getSuccessfulSignOutSignal().observe(this, Observer {
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        })

        // wss test
        val client = okhttp3.OkHttpClient.Builder()
            .readTimeout(3, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build()
        val request = okhttp3.Request.Builder()
            .url("ws://192.168.1.7:8000")
//            .url("ws://echo.websocket.org")
            .build()

        val wsListener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                webSocket.send("Hello World")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("tes", text)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.d("tes", t.message.toString())
            }
        }
        val webSocket = client.newWebSocket(request, wsListener)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}