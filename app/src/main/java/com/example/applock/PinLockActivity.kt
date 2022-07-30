package com.example.applock

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView
import com.example.applock.model.PassPin
import com.example.applock.utils.LocaleManager
import com.example.applock.utils.Utils

class PinLockActivity : AppCompatActivity() {
    private var statusPin: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pin_lock)
        initLayout()
        initPinLock()
    }

    private fun initPinLock() {
        val pinLockView: PinLockView = findViewById(R.id.pin_lock_view)
        pinLockView.setPinLockListener(object : PinLockListener {
            override fun onComplete(pin: String?) {

                if (PassPin.isCorrect(pin!!)) {
                    statusPin?.text = resources.getString(R.string.status_pin_correct)
                    startMain()
                } else {
                    statusPin?.text = resources.getString(R.string.status_pattern_incorrect)
                    pinLockView.resetPinLockView()
                }

            }

            override fun onEmpty() {

            }

            override fun onPinChange(pinLength: Int, intermediatePin: String?) {

            }

        })
        val mIndicatorDots: IndicatorDots = findViewById(R.id.indicator_dots)
        pinLockView.attachIndicatorDots(mIndicatorDots)
    }

    private fun initLayout() {
        statusPin = findViewById(R.id.status_pin)
        statusPin?.text = resources.getString(R.string.status_first_step_pin)
    }

    fun startMain() {
        if (intent.getStringExtra("broadcast_receiver") == null) {
            startActivity(Intent(this, MainActivity::class.java))
        }
        finish()
    }

    override fun onBackPressed() {
            startCurrentHomePackage()
            finish()
            super.onBackPressed()
    }

    private fun startCurrentHomePackage() {
        val intent = Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val resolveInfo = packageManager.resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY)
        val activityInfo = resolveInfo?.activityInfo
        val componentName =
            ComponentName(activityInfo?.applicationInfo!!.packageName, activityInfo.name)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Utils(this).clearLastApp()
    }
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }
}