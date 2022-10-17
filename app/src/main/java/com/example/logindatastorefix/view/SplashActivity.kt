package com.example.logindatastorefix.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.lifecycle.asLiveData
import com.example.logindatastorefix.DataStoreLogin
import com.example.logindatastorefix.MainActivity
import com.example.logindatastorefix.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    lateinit var binding : ActivitySplashBinding
    lateinit var dataLogin : DataStoreLogin
    lateinit var usernama : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dataLogin = DataStoreLogin(this)
        dataLogin.userName.asLiveData().observe(this,{
            usernama = it.toString()
        })

        Handler().postDelayed({
            if (usernama.isNullOrEmpty()){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }


        },3000)




    }
}