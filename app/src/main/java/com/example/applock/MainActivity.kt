package com.example.applock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.applock.adapter.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
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
        viewPager.adapter = FragmentAdapter(supportFragmentManager,lifecycle)
        TabLayoutMediator(tabLayout,viewPager){tab,index ->
            tab.text = tabTitle[index]
        }.attach()

        imgMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }
    }



}