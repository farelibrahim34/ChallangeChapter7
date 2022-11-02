package com.example.logindatastorefix.isi

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.asLiveData
import com.example.logindatastorefix.DataStoreLogin
import com.example.logindatastorefix.DataStoreProfile
import com.example.logindatastorefix.MainActivity
import com.example.logindatastorefix.R
import com.example.logindatastorefix.databinding.ActivityProfileBinding
import com.example.logindatastorefix.view.LoginActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {
    lateinit var binding : ActivityProfileBinding
    lateinit var dataLogin : DataStoreLogin
    lateinit var dataProfile : DataStoreProfile
    private val REQUEST_CODE_PERMISSION = 100
    private var imageUri: Uri? = Uri.EMPTY

    lateinit var mGoogleSignInClient: GoogleSignInClient
    val Req_Code:Int=123
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        FirebaseApp.initializeApp(this)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        firebaseAuth= FirebaseAuth.getInstance()

        firebaseAuth= FirebaseAuth.getInstance()
        binding.akunGoogle.setOnClickListener{ view: View? ->
            Toast.makeText(this,"Logging In", Toast.LENGTH_SHORT).show()
            signInGoogle()


        }
        binding.logoutGoogle.setOnClickListener {
            mGoogleSignInClient.signOut().addOnCompleteListener {
                val intent= Intent(this, LoginActivity::class.java)
                Toast.makeText(this,"Logging Out", Toast.LENGTH_SHORT).show()
                startActivity(intent)
                binding.logoutGoogle.setText("")
                binding.akunGoogle.setText("sambungkan ke akun google?")
                finish()
            }
        }





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
        var image = BitmapFactory.decodeFile(this.applicationContext.filesDir.path + File.separator +"dataFoto"+ File.separator +"fotoProfile.png")
        binding.btnAddProfile.setImageBitmap(image)




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

            val resolver = this.applicationContext.contentResolver
            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(imageUri.toString())))
            writeBitmapToFile(this,picture)
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
    private  fun signInGoogle(){


        val signInIntent: Intent =mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,Req_Code)

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==Req_Code){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }
    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                UpdateUI(account)
            }
        } catch (e: ApiException){
            Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show()
        }
    }
    private fun UpdateUI(account: GoogleSignInAccount){
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
            startActivity(Intent(this, ProfileActivity::class.java))
            Log.e("sigingoogle",signInGoogle().toString())


        }
    }
    override fun onStart() {
        super.onStart()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, ProfileActivity::class.java))

        }
    }

    override fun onResume() {
        super.onResume()
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, ProfileActivity::class.java))
            binding.akunGoogle.text = "terhubung ke akun google"
            binding.logoutGoogle.text = "logout"
        }
    }





    fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = "fotoProfile.png"
        val outputDir = File(applicationContext.filesDir, "dataFoto")
        if (!outputDir.exists()) {
            outputDir.mkdirs() // should succeed
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0 /* ignored for PNG */, out)
        } finally {
            out?.let {
                try {
                    it.close()
                } catch (ignore: IOException) {
                }

            }
        }
        return Uri.fromFile(outputFile)
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
            imageUri = result
            binding.btnAddProfile.setImageURI(result)
        }
}