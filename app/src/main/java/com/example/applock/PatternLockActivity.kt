package com.example.applock


import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.example.applock.model.PassPattern
import com.example.applock.services.BackgroundManager
import com.example.applock.utils.Utils
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
        BackgroundManager.getInstance()!!.init(this).startService()
        initLayout()
        initPatternListener()
    }
    private fun initPatternListener(){
        val patternLockView: PatternLockView = findViewById(R.id.pattern_view)
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
        if(intent.getStringExtra("broadcast_receiver") == null){
            startActivity(Intent(this,MainActivity::class.java))
        }
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
            startCurrentHomePackage()
            finish()
            super.onBackPressed()
        }
    }

    private fun startCurrentHomePackage() {
        val intent =Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val resolveInfo= packageManager.resolveActivity(intent,PackageManager.MATCH_DEFAULT_ONLY)
        val activityInfo = resolveInfo?.activityInfo
        val componentName = ComponentName(activityInfo?.applicationInfo!!.packageName,activityInfo.name)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        Utils(this).clearLastApp()
    }
}