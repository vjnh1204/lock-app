package com.example.applock

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.andrognito.pinlockview.IndicatorDots
import com.andrognito.pinlockview.PinLockListener
import com.andrognito.pinlockview.PinLockView
import com.example.applock.model.PassPin
import com.shuhart.stepview.StepView

class ChangePincodeActivity : AppCompatActivity() {
    private var stepView: StepView? = null
    private var statusPin: TextView? = null
    private var userPin: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pincode)
        PassPin.setFirstStepChange(true)
        initLayout()
        initPinLock()
    }

    private fun initPinLock() {
        val pinLockView: PinLockView = findViewById(R.id.pin_lock_view)
        pinLockView.setPinLockListener(object : PinLockListener {
            override fun onComplete(pin: String?) {

                if (PassPin.isFirstStepChange()) {
                    userPin = pin
                    PassPin.setFirstStepChange(false)
                    statusPin?.text = PassPin.STATUS_NEXT_STEP
                    stepView?.go(1, true)
                    pinLockView.resetPinLockView()
                } else {
                    if (userPin.equals(pin, true)) {

                        PassPin.changePin(userPin!!)
                        statusPin?.text = PassPin.STATUS_PIN_CORRECT
                        stepView?.done(true)
                        startMain()
                    } else {
                        statusPin?.text = PassPin.STATUS_PIN_INCORRECT
                        pinLockView.resetPinLockView()
                    }
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
    private fun startMain(){
        App.instance?.setLockStyle("pin")
        startActivity(Intent(this,MainActivity::class.java))
        Toast.makeText(this,"Change success",Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun initLayout() {
        stepView = findViewById(R.id.step_view_pin)
        statusPin = findViewById(R.id.status_pin)

        stepView?.visibility = View.VISIBLE
        statusPin?.text = PassPin.STATUS_FIST_STEP
        stepView?.setStepsNumber(2)
        stepView?.go(0, true)


    }

    override fun onBackPressed() {
        if (!PassPin.isFirstStepChange()) {
            stepView?.go(0, true)
            PassPin.setFirstStepChange(true)
            statusPin?.text = PassPin.STATUS_FIST_STEP
        } else {
            finish()
            super.onBackPressed()
        }
    }
}