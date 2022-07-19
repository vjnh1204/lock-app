package com.example.applock

import android.app.Application
import android.content.Context
import com.example.applock.services.BackgroundManager
import io.paperdb.Paper

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)
        instance= this
    }
    fun setHaveCode(passwordKey: String){
        val pref = getSharedPreferences(PREFS, Context.MODE_PRIVATE).edit()
        pref.putString(HAVE_PASSWORD,passwordKey)
        pref.apply()
    }
    fun getHaveCode(): String?{
        val pref = getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        return pref.getString(HAVE_PASSWORD,"N")
    }
    companion object{
        var instance: App? = null
        const val PREFS: String = "SHARED_PREFS"
        const val HAVE_PASSWORD: String = "HAVE_PASSWORD"
    }
}