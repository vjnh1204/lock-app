package com.example.applock

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applock.adapters.AppsFavoriteAdapter
import com.example.applock.model.AppInfo
import com.example.applock.utils.Constant
import com.example.applock.utils.Utils
import io.paperdb.Paper
import kotlinx.coroutines.*

class FavoriteActivity : AppCompatActivity() {
    private var nameProfile:String? = null
    private var listApp:ArrayList<AppInfo>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        val recyclerView:RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        listApp = Constant.getApplications(this)
        val adapter = AppsFavoriteAdapter(listApp!!,this)
        recyclerView.adapter = adapter
        val saveButton :Button = findViewById(R.id.btn_saveProfile)
        saveButton.setOnClickListener {
            dialogSave()
        }
    }
    private fun dialogSave(){
        val alertDialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_profiles,null)
        alertDialog.setView(view)
        alertDialog.create()
        val mAlertDialog = alertDialog.show()
        val editText:EditText = view.findViewById(R.id.edit_text)
        val btnSave :Button = view.findViewById(R.id.btn_save)
        btnSave.setOnClickListener {
            nameProfile= editText.text.toString()
            GlobalScope.launch(newSingleThreadContext("Favorite")) {
                val listAppFavorite = ArrayList<AppInfo>()
                for (app in listApp!!){
                    if (Utils(this@FavoriteActivity).isFavorite(app.packageName)){
                        listAppFavorite.add(app)
                    }

                }
                withContext(Dispatchers.Main){
                    val intent = Intent()
                    intent.putExtra("AAA",listAppFavorite)
                    intent.putExtra("nameProfile",nameProfile)
                    setResult(Activity.RESULT_OK,intent)
                    finish()
                }

            }
            mAlertDialog.dismiss()
        }
        val btnCancel:Button = view.findViewById(R.id.btn_cancel)
        btnCancel.setOnClickListener {
            mAlertDialog.dismiss()
        }

    }
}