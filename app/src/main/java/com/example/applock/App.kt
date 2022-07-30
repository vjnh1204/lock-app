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
    fun setLockStyle(lockStyle:String){
        val pref = getSharedPreferences(PREFS,Context.MODE_PRIVATE).edit()
        pref.putString(LOCK_STYLE,lockStyle)
        pref.apply()
    }
    fun getLockStyle():String?{
        val pref = getSharedPreferences(PREFS,Context.MODE_PRIVATE)
        return pref.getString(LOCK_STYLE,"pattern")
    }
    fun setLocale(locale:String){
        val pref = getSharedPreferences(PREFS,Context.MODE_PRIVATE).edit()
        pref.putString(LOCALE,locale)
        pref.apply()
    }
    fun getLocale():String?{
        val pref = getSharedPreferences(PREFS,Context.MODE_PRIVATE)
        return pref.getString(LOCALE,"en")
    }
    companion object{
        var instance: App? = null
        const val PREFS: String = "SHARED_PREFS"
        const val HAVE_PASSWORD: String = "HAVE_PASSWORD"
        const val LOCK_STYLE:String ="LOCK_STYLE"
        const val LOCALE:String ="LOCALE"
    }
}