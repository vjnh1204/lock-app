package com.example.applock.model

import io.paperdb.Paper

object PassPattern {

        private const val PASSWORD_KEY = "PASSWORD_KEY"
        const val STATUS_FIST_STEP = "Draw an unlock pattern"
        const val STATUS_NEXT_STEP = "Draw pattern again to confirm"
        const val STATUS_PASSWORD_CORRECT= "Pattern set"
        const val STATUS_PASSWORD_INCORRECT= "Try again"
        const val SCHEMA_FAILED = "Connect at least 4 dots"
        private var isFirstStep:Boolean = true
        private var isFirstStepChange:Boolean= true
        fun setPassword(password: String){
            Paper.book().write(PASSWORD_KEY,password)
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
            Paper.book().delete(PASSWORD_KEY)
            Paper.book().write(PASSWORD_KEY,password)
        }
}
