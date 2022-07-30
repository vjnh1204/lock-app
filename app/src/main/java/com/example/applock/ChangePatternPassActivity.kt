package com.example.applock

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import com.example.applock.model.PassPattern
import com.example.applock.utils.LocaleManager
import com.itsxtt.patternlock.PatternLockView
import com.shuhart.stepview.StepView
import java.util.ArrayList

class ChangePatternPassActivity : AppCompatActivity() {
    private var stepView:StepView? = null
    private var statusPassword:TextView? = null
    private var userPassword:String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_pattern_pass)
        PassPattern.setFirstStepChange(true)
        initLayout()
        initPassPattern()
    }
    private fun initPassPattern(){
        val patternLockView:PatternLockView = findViewById(R.id.pattern_view_change)
        patternLockView.setOnPatternListener(object : PatternLockView.OnPatternListener{
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                var password =""
                for(id in ids){
                    password+=id.toString()
                }
                if(password.length<4){
                    statusPassword?.text = resources.getString(R.string.schema_failed_pattern)
                    return false
                }
                if(PassPattern.isFirstStepChange()){
                    userPassword = password
                    PassPattern.setFirstStepChange(false)
                    statusPassword?.text = resources.getString(R.string.status_next_step_pattern)
                    stepView?.go(1,true)
                }
                else{
                    if(userPassword.equals(password)){
                        PassPattern.changePass(userPassword!!)
                        statusPassword?.text = resources.getString(R.string.status_pattern_correct)
                        stepView?.done(true)
                        startMain()
                    }
                    else{
                        statusPassword?.text = resources.getString(R.string.status_pattern_incorrect)
                        return false
                    }
                }
                return true
            }
        })
    }
    private fun startMain(){
        App.instance?.setLockStyle("pattern")
        startActivity(Intent(this,MainActivity::class.java))
        Toast.makeText(this,"Change success",Toast.LENGTH_SHORT).show()
        finish()
    }
    private fun initLayout(){
        stepView= findViewById(R.id.step_view_change)
        statusPassword = findViewById(R.id.status_password_change)
        statusPassword?.text = resources.getString(R.string.status_first_step_pattern)

        stepView?.setStepsNumber(2)
        stepView?.go(0,true)
    }

    override fun onBackPressed() {
        if(!PassPattern.isFirstStepChange()){
            PassPattern.setFirstStepChange(true)
            stepView?.go(0,true)
            statusPassword?.text = resources.getString(R.string.status_first_step_pattern)
        }
        else{
            finish()
            super.onBackPressed()
        }

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }
}