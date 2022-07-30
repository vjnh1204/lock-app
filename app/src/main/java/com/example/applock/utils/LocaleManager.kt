package com.example.applock.utils

import android.content.Context
import android.content.res.Configuration
import com.example.applock.App
import java.util.*

object LocaleManager {
    fun setLocale(mContext: Context):Context{
        return if(App.instance?.getLocale()!= null){
            updateResources(mContext, App.instance?.getLocale()!!)
        }
        else mContext
    }
    fun setNewLocale(mContext: Context,language:String):Context{
        App.instance?.setLocale(language)
        return updateResources(mContext,language)
    }
    private fun updateResources(context: Context, language: String):Context{
        var localeContext= context
        var locale = Locale(language)
        Locale.setDefault(locale)
        val res = context.resources
        val config = Configuration(res.configuration)
        config.setLocale(locale)
        localeContext = context.createConfigurationContext(config)
        return localeContext
    }
}