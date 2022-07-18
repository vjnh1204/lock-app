package com.example.applock.fragment

import android.content.Context
import android.content.SharedPreferences

class SaveData(context: Context){
    private var sharedPreferences:SharedPreferences =context.getSharedPreferences("file",Context.MODE_PRIVATE)

    //This METHOD will save the night Mode state:TRUE OR FALSE
    fun setDarkModeState(state:Boolean?){
        val editor  =   sharedPreferences.edit()
        editor.putBoolean("Dark",state!!)
        editor.apply()
    }

    //this method will load the night state
    fun loadDarkModeState(): Boolean {
        return sharedPreferences.getBoolean("Dark", false)
    }

}