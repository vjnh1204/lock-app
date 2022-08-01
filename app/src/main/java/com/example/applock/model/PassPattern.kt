package com.example.applock.model

import io.paperdb.Paper
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object PassPattern {

        private const val PASSWORD_KEY = "PASSWORD_KEY"
        private var isFirstStep:Boolean = true
        private var isFirstStepChange:Boolean= true
        fun setPassword(password: String){
            GlobalScope.launch {
                Paper.book().write(PASSWORD_KEY,password)
            }

        }
        fun getPassword():String?{
            return Paper.book().read(PASSWORD_KEY)
        }
        fun isFirstStep(): Boolean{
            return isFirstStep
        }
        fun setFistStep(firstStep : Boolean){
            isFirstStep = firstStep
        }
        fun isCorrect(password: String):Boolean{
            return password == getPassword()
        }
        fun isFirstStepChange():Boolean{
            return isFirstStepChange
        }
        fun setFirstStepChange(firstStepChange: Boolean){
            isFirstStepChange= firstStepChange
        }
        fun changePass(password: String){
            GlobalScope.launch {
                Paper.book().delete(PASSWORD_KEY)
                Paper.book().write(PASSWORD_KEY,password)
            }

        }
}
