package com.example.applock.model

import io.paperdb.Paper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object PassPin {
    private const val PIN_KEY = "PIN_KEY"
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
        GlobalScope.launch {
            Paper.book().delete(PIN_KEY)
            Paper.book().write(PIN_KEY,pin)
        }

    }
}