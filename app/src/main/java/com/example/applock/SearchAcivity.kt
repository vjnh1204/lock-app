package com.example.applock

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applock.adapters.AppsInstallerAdapter
import com.example.applock.model.AppInfo
import com.example.applock.utils.Constant

class SearchAcivity:AppCompatActivity() {

    //global variable
    var recyclerView :RecyclerView ?= null
    private var listApp:ArrayList<AppInfo> ?= null
    private var adapter :AppsInstallerAdapter? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_app)
        //back activity from main
        val backView = findViewById<ImageView>(R.id.back)
        backView.setOnClickListener{
            startActivity(Intent(this@SearchAcivity,MainActivity::class.java))
        }
        //get data recyclerview
        recyclerView = findViewById(R.id.recyclerView)
        listApp= Constant.getApplications(this)
        recyclerView?.layoutManager = LinearLayoutManager(this)
        adapter = AppsInstallerAdapter(listApp!!,this)
        recyclerView?.adapter = adapter
        //Logic searchApp
        val searchApp   = findViewById<androidx.appcompat.widget.SearchView>(R.id.searchApp)
        searchApp.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                    filterListApp(newText) // call function ListApNew
                return true
            }

        })
    }

    private fun filterListApp(text: String) {

        val listAppNew  = ArrayList<AppInfo>()
        for(appInfo in listApp!!){

            if(appInfo.appName.lowercase().contains(text.toLowerCase())){
                listAppNew.add(appInfo)
//                Log.d("aa",appInfo.appName)
            }
            if(listAppNew == null){
            }
            else{
                adapter!!.setlistAppNew(listAppNew)
            }


        }



    }
}