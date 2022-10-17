package com.example.logindatastorefix.isi

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.asLiveData
import com.example.logindatastorefix.DataStoreLogin
import com.example.logindatastorefix.DataStoreProfile
import com.example.logindatastorefix.MainActivity
import com.example.logindatastorefix.databinding.ActivityProfileBinding
import com.example.logindatastorefix.view.LoginActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityProfileBinding
    lateinit var dataLogin : DataStoreLogin
    lateinit var dataProfile : DataStoreProfile
    private val REQUEST_CODE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dataLogin = DataStoreLogin(this)
        dataProfile = DataStoreProfile(this)

        dataLogin.userName.asLiveData().observe(this,{
            binding.profileUser.setText(it.toString())
        })
        dataProfile.userNama.asLiveData().observe(this,{
            binding.inputNama.setText(it.toString())
        })
        dataProfile.userTgl.asLiveData().observe(this,{
            binding.inputTgl.setText(it.toString())
        })
        dataProfile.userAlamat.asLiveData().observe(this,{
            binding.inputAlamat.setText(it.toString())
        })



        binding.btnUpdateProfile.setOnClickListener {
            val updateNama = binding.inputNama.text.toString()
            val updateTgl = binding.inputTgl.text.toString()
            val updateAlamat = binding.inputAlamat.text.toString()
            val updateGambar = binding.btnAddProfile.drawable.toString()
//            val updateUsername = binding.profileUser.text.toString()

            GlobalScope.launch {
                dataProfile.saveDataProfile(updateNama,updateTgl,updateAlamat)
                startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
            }
        }

        binding.btnLogout.setOnClickListener {
            GlobalScope.launch {
                DataStoreLogin(this@ProfileActivity).clearData()
                DataStoreProfile(this@ProfileActivity).clearDataProfile()
                startActivity(Intent(this@ProfileActivity, LoginActivity::class.java))
                finish()
            }
        }
        binding.btnAddProfile.setOnClickListener {
            checkingPermissions()
        }
    }
    private fun checkingPermissions() {
        if (isGranted(
                this,
                Manifest.permission.CAMERA,
                arrayOf(
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ),
                REQUEST_CODE_PERMISSION,
            )
        ) {
            chooseImageDialog()
        }
    }
    private fun isGranted(
        activity: Activity,
        permission: String,
        permissions: Array<String>,
        request: Int,
    ): Boolean {
        val permissionCheck = ActivityCompat.checkSelfPermission(activity, permission)
        return if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                showPermissionDeniedDialog()
            } else {
                ActivityCompat.requestPermissions(activity, permissions, request)
            }
            false
        } else {
            true
        }
    }
    private fun showPermissionDeniedDialog() {
        AlertDialog.Builder(this)
            .setTitle("Permission Denied")
            .setMessage("Permission is denied, Please allow permissions from App Settings.")
            .setPositiveButton(
                "App Settings"
            ) { _, _ ->
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
            }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
            .show()
    }
    private fun chooseImageDialog() {
        AlertDialog.Builder(this)
            .setMessage("Pilih Gambar")
            .setPositiveButton("Gallery") { _, _ -> openGallery() }
            .setNegativeButton("Camera") { _, _ -> openCamera() }
            .show()
    }
    private fun openGallery() {
        intent.type = "image/*"
        galleryResult.launch("image/*")
    }
    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraResult.launch(cameraIntent)
    }
    private fun handleCameraImage(intent: Intent?) {
        val bitmap = intent?.extras?.get("data") as Bitmap
        binding.btnAddProfile.setImageBitmap(bitmap)

    }
    private val cameraResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                handleCameraImage(result.data)
            }
        }
    private val galleryResult =
        registerForActivityResult(ActivityResultContracts.GetContent()) { result ->
            binding.btnAddProfile.setImageURI(result)
        }
}