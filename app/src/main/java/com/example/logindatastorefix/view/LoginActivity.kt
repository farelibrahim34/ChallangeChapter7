package com.example.logindatastorefix.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.asLiveData
import com.example.logindatastorefix.DataStoreLogin
import com.example.logindatastorefix.MainActivity
import com.example.logindatastorefix.view.RegisterActivity
import com.example.logindatastorefix.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding : ActivityLoginBinding
    lateinit var dataLogin : DataStoreLogin
    lateinit var useremail : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataLogin = DataStoreLogin(this)
        dataLogin.userEmail.asLiveData().observe(this,{
            useremail = it.toString()
        })

        binding.btnLogin.setOnClickListener {
            val email = binding.inputEmail.text.toString()
            val password = binding.inputLogPw.text.toString()
            if(email.isEmpty()){
                Toast.makeText(this,"ISI PASSWORD DAN USERNAME ANDA", Toast.LENGTH_SHORT).show()
            }else if (password.isEmpty()){
                Toast.makeText(this,"ISI PASSWORD DAN USERNAME ANDA", Toast.LENGTH_SHORT).show()
            }else if (email == useremail){
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }else{
                Toast.makeText(this,"ISI PASSWORD DAN USERNAME ANDA", Toast.LENGTH_SHORT).show()
            }
        }
        binding.txtRegis.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}