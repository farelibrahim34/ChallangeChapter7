package com.example.logindatastorefix.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.logindatastorefix.DataStoreLogin
import com.example.logindatastorefix.databinding.ActivityRegisterBinding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegisterBinding
    lateinit var dataLogin : DataStoreLogin
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataLogin = DataStoreLogin(this)

//        binding.btnRegister.setOnClickListener {
//            val user = binding.registUsername.text.toString()
//            val pw = binding.registPw.text.toString()
//            GlobalScope.launch {
//                dataLogin.saveData(user,pw)
//                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
//
//            }
//        }
        binding.btnDaftar.setOnClickListener {
            var saveEmail = binding.registEmail.text.toString()
            var saveUser = binding.registUser.text.toString()
            var savePw = binding.inputRegistPw.text.toString()
            var getUpw = binding.inputRegistUpw.text.toString()
            GlobalScope.launch {
                dataLogin.saveData(saveUser,savePw,saveEmail)
                startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            }
        }


    }
}