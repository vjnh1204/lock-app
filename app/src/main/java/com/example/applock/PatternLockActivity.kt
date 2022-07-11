package com.example.applock


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.applock.model.PassPattern
import com.itsxtt.patternlock.PatternLockView
import com.shuhart.stepview.StepView
import java.util.ArrayList


class PatternLockActivity : AppCompatActivity() {
    private var stepView: StepView?= null
    private var statusPassword: TextView?= null
    private var linearLayout: LinearLayout?= null
    private var userPassword: String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pattern_lock)
        initLayout()
        initPatternListener()
    }
    private fun initPatternListener(){
        var patternLockView: PatternLockView = findViewById(R.id.pattern_view)
        patternLockView.setOnPatternListener(object :PatternLockView.OnPatternListener {
            override fun onComplete(ids: ArrayList<Int>): Boolean {
                var password = ""
                for(id in ids){
                    password+=id.toString()
                }
                if(password.length<4){
                    statusPassword?.text= PassPattern.SCHEMA_FAILED
                    return false
                }
                if(PassPattern.getPassword() == null){
                    if(PassPattern.isFirstStep()){
                        userPassword= password
                        PassPattern.setFistStep(false)
                        statusPassword?.text = PassPattern.STATUS_NEXT_STEP
                        stepView?.go(1,true)
                    }
                    else{
                        if(userPassword.equals(password)){
                            PassPattern.setPassword(userPassword!!)
                            statusPassword?.text= PassPattern.STATUS_PASSWORD_CORRECT
                            stepView?.done(true)
                            startFirst()
                        }
                        else{
                            statusPassword?.text= PassPattern.STATUS_PASSWORD_INCORRECT
                            return false
                        }
                    }
                }
                else{
                    if (PassPattern.isCorrect(password)){
                        statusPassword?.text= PassPattern.STATUS_PASSWORD_CORRECT

                        startMain()
                    }
                    else{
                        statusPassword?.text= PassPattern.STATUS_PASSWORD_INCORRECT
                        return false
                    }
                }
                return true
            }
        })
    }
    private fun startFirst(){
        val intent = Intent()
        App.instance!!.setHaveCode("Y")
        setResult(Activity.RESULT_OK,intent)
        finish()
    }
    private fun startMain(){
        startActivity(Intent(this,MainActivity::class.java))
        finish()
    }
    private fun initLayout(){
        stepView = findViewById(R.id.step_view)
        statusPassword = findViewById(R.id.status_password)
        linearLayout = findViewById(R.id.ll_action_bar1)
        statusPassword?.text= PassPattern.STATUS_FIST_STEP

        if(PassPattern.getPassword() == null ){
            linearLayout?.visibility= View.GONE
            stepView?.visibility = View.VISIBLE
            stepView?.setStepsNumber(2)
            stepView?.go(0,true)
        }
        else {
            linearLayout?.visibility = View.VISIBLE
            stepView?.visibility = View.GONE
        }
    }

    override fun onBackPressed() {
        if(PassPattern.getPassword() == null && !PassPattern.isFirstStep()){
            stepView?.go(0,true)
            PassPattern.setFistStep(true)
            statusPassword?.text = PassPattern.STATUS_FIST_STEP
        }
        else{
            finish()
            super.onBackPressed()
        }
    }
}