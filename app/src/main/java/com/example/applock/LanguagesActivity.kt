package com.example.applock

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.example.applock.utils.LocaleManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LanguagesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_languages)
        val vietNamCardView:CardView = findViewById(R.id.VietNam)
        val englishCardView:CardView = findViewById(R.id.English)
        vietNamCardView.setOnClickListener {
            GlobalScope.launch {
                LocaleManager.setNewLocale(this@LanguagesActivity,"vi")
                startActivity(Intent(this@LanguagesActivity,MainActivity::class.java))
                finish()
            }

        }
        englishCardView.setOnClickListener {
            GlobalScope.launch {
                LocaleManager.setNewLocale(this@LanguagesActivity,"en")
                startActivity(Intent(this@LanguagesActivity,MainActivity::class.java))
                finish()
            }

        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleManager.setLocale(newBase!!))
    }
}