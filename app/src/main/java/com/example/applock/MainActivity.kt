package com.example.applock

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SearchView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.applock.adapters.FragmentAdapter
import com.example.applock.model.AppInfo
import com.example.applock.utils.Constant
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class MainActivity : FragmentActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    var tabTitle = arrayOf("Applications","Profiles")
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var imgMenu:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.viewPager2)
        drawerLayout = findViewById(R.id.drawer_layout)
        imgMenu = findViewById(R.id.image_menu)
        viewPager.adapter = FragmentAdapter(this)
        TabLayoutMediator(tabLayout,viewPager){tab,index ->
            tab.text = tabTitle[index]
        }.attach()

        imgMenu.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }

//        val listApp     = Constant.getApplications(this)
        //search active

        val searchBtn   = findViewById<ImageView>(R.id.searchBtn)

            searchBtn.setOnClickListener{
                startActivity(Intent(this@MainActivity,SearchAcivity::class.java))
            }
//
//        val searchApp = findViewById<androidx.appcompat.widget.SearchView>(R.id.search)
//        val listAppNew = ArrayList<AppInfo>()
//        searchApp.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
//            androidx.appcompat.widget.SearchView.OnQueryTextListener {
//            override fun onQueryTextSubmit(query: String): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String): Boolean {
//                listAppNew.clear()
//                for (appInfo in listApp){
//                    if (appInfo.appName.lowercase().contains(newText) or appInfo.appName.uppercase().contains(newText)){
//                        listAppNew.add(appInfo)
//                        Log.d("aa",appInfo.appName)
//
//                    }
//                }
//                return true
//            }

//        })

    }




    }

