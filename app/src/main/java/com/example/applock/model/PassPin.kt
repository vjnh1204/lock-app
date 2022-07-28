package com.example.applock.model

import io.paperdb.Paper

object PassPin {
    private const val PIN_KEY = "PIN_KEY"
    const val STATUS_FIST_STEP = "Enter your pin"
    const val STATUS_NEXT_STEP = "Enter your pin again to confirm"
    const val STATUS_PIN_CORRECT= "Pin set"
    const val STATUS_PIN_INCORRECT= "Please try again"
    private var isFirstStepChange:Boolean= true
    private fun getPin():String?{
        return Paper.book().read(PIN_KEY)
    }
    fun isCorrect(pin: String):Boolean{
        return pin == getPin()
    }
    fun isFirstStepChange():Boolean{
        return isFirstStepChange
    }
    fun setFirstStepChange(firstStepChange: Boolean){
        isFirstStepChange= firstStepChange
    }
    fun changePin(pin: String){
        Paper.book().delete(PIN_KEY)
        Paper.book().write(PIN_KEY,pin)
    }
}