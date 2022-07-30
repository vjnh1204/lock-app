package com.example.applock

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.applock.utils.LocaleManager
import com.example.applock.utils.Utils
import kotlinx.coroutines.*

class FirstActivity : AppCompatActivity() {
    private var btnCreatePass: Button? = null
    private var imageCreatePass: ImageView? = null
    private var btnSettingPermission: Button? = null
    private var imageSettingPermission: ImageView? = null
    private var imageOverlayPermission: ImageView? = null
    private var btnOverlayPermission: Button? = null
    private var btnStart: Button? = null
    private var sharedPreferences: SharedPreferences? = null
    var checkButtonCreateClick: Boolean = false
    var checkButtonPermissionClick: Boolean = false
    var checkButtonOverlayClick: Boolean = false
    private var patternLockActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    checkButtonCreateClick = true
                    ImageViewCompat.setImageTintList(imageCreatePass!!, ColorStateList.valueOf(
                        ContextCompat.getColor(this, R.color.colorBlue)))
                    btnCreatePass?.background?.setTintList(ContextCompat.getColorStateList(this,
                        R.color.colorGray))
                    btnCreatePass?.isEnabled = false
                }
            } else if (result.resultCode == Activity.RESULT_CANCELED) {
                Log.e("Cancelled", "Cancelled")
            }

        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_acivity)
        sharedPreferences = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
        val firstTime = sharedPreferences?.getString("FirstTimeInstall", "")
        GlobalScope.launch {
            if (firstTime.equals("Yes")) {
                startActivity(Intent(this@FirstActivity, PatternLockActivity::class.java))
                finish()
            }
        }

        btnCreatePass = findViewById(R.id.btn_createPass)
        imageCreatePass = findViewById(R.id.image_done_createPass)
        imageSettingPermission = findViewById(R.id.image_setting_permission)
        btnSettingPermission = findViewById(R.id.btn_setting_permission)
        btnStart = findViewById(R.id.btn_start)
        btnOverlayPermission = findViewById(R.id.btn_orverlay_permission)
        imageOverlayPermission = findViewById(R.id.image_overlay_permission)
        if (App.instance!!.getHaveCode() == "Y") {
            setButtonCreate()
        }
        btnCreatePass?.setOnClickListener {
            patternLockActivityLauncher.launch(Intent(this, PatternLockActivity::class.java))
        }
        btnSettingPermission?.setOnClickListener {
            startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
        }
        btnStart?.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        btnOverlayPermission?.setOnClickListener {
            startActivity(Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION))
        }


    }

    private fun setButtonCreate() {
        checkButtonCreateClick = true
        ImageViewCompat.setImageTintList(imageCreatePass!!, ColorStateList.valueOf(
            ContextCompat.getColor(this, R.color.colorBlue)))
        btnCreatePass?.background?.setTintList(ContextCompat.getColorStateList(this,
            R.color.colorGray))
        btnCreatePass?.isEnabled = false
    }

    private fun showBtnStart() {
        GlobalScope.launch(newSingleThreadContext("FistThread")) {
            val editor = sharedPreferences?.edit()
            if (checkButtonOverlayClick && checkButtonPermissionClick && checkButtonCreateClick) {
                withContext(Dispatchers.Main){
                    btnStart?.visibility = View.VISIBLE
                }
                editor?.putString("FirstTimeInstall", "Yes")
            } else {
                withContext(Dispatchers.Main){
                    btnStart?.visibility = View.GONE
                }
                editor?.putString("FirstTimeInstall", "")
            }
            editor?.apply()
        }

    }

    override fun onResume() {
        GlobalScope.launch(newSingleThreadContext("SecondThread")) {
            if (Settings.canDrawOverlays(this@FirstActivity)) {
                checkButtonOverlayClick = true
                withContext(Dispatchers.Main) {
                    btnOverlayPermission?.isEnabled = false
                    ImageViewCompat.setImageTintList(imageOverlayPermission!!,
                        ColorStateList.valueOf(
                            ContextCompat.getColor(this@FirstActivity, R.color.colorBlue)))
                    btnOverlayPermission?.background?.setTintList(ContextCompat.getColorStateList(
                        this@FirstActivity,
                        R.color.colorGray))
                }

            } else {
                checkButtonOverlayClick = false
                withContext(Dispatchers.Main) {
                    btnOverlayPermission?.isEnabled = true
                    ImageViewCompat.setImageTintList(imageOverlayPermission!!,
                        ColorStateList.valueOf(
                            ContextCompat.getColor(this@FirstActivity, R.color.colorGray)))
                    btnOverlayPermission?.background?.setTintList(ContextCompat.getColorStateList(
                        this@FirstActivity,
                        R.color.colorButton))
                }

            }


            if (Utils.permissionCheck(this@FirstActivity)) {
                checkButtonPermissionClick = true
                withContext(Dispatchers.Main){
                    btnSettingPermission?.isEnabled = false
                    ImageViewCompat.setImageTintList(imageSettingPermission!!, ColorStateList.valueOf(
                        ContextCompat.getColor(this@FirstActivity, R.color.colorBlue)
                    ))
                    btnSettingPermission?.background?.setTintList(ContextCompat.getColorStateList(this@FirstActivity,
                        R.color.colorGray))
                }


            } else {
                checkButtonPermissionClick = false
                withContext(Dispatchers.Main){
                    btnSettingPermission?.isEnabled = true
                    ImageViewCompat.setImageTintList(imageSettingPermission!!, ColorStateList.valueOf(
                        ContextCompat.getColor(this@FirstActivity, R.color.colorGray)
                    ))
                    btnSettingPermission?.background?.setTintList(ContextCompat.getColorStateList(this@FirstActivity,
                        R.color.colorButton))
                }

            }
            showBtnStart()
        }

        super.onResume()
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }
}