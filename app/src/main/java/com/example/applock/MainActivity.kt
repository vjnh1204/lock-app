package com.example.applock

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.applock.adapters.FragmentAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : FragmentActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager2
    private var tabTitle = arrayOf("Applications","Profiles")
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var imgMenu:ImageView
    private lateinit var changePassCardView:CardView
    private lateinit var changeStyleCardView:CardView
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
        changePassCardView = findViewById(R.id.change_pass)
        changePassCardView.setOnClickListener {
            if (App.instance?.getLockStyle().equals("pattern")){
                startActivity(Intent(this,ChangePatternPassActivity::class.java))
            }
            else if(App.instance?.getLockStyle().equals("pin")){
                startActivity(Intent(this,ChangePincodeActivity::class.java))
            }
        }
        changeStyleCardView = findViewById(R.id.change_lock_view)
        changeStyleCardView.setOnClickListener {
            val dialog = AlertDialog.Builder(this)
            val view = LayoutInflater.from(this).inflate(R.layout.dialog_style_lock,null)
            dialog.setView(view)
            val radioGroup: RadioGroup = view.findViewById(R.id.radio_group)
            val radioPatternLock: RadioButton = view.findViewById(R.id.radio_pattern_lock)
            val radioPinLock: RadioButton = view.findViewById(R.id.radio_pin_lock)
            if (App.instance?.getLockStyle().equals("pattern")){
                radioPatternLock.isChecked= true
            }
            else if(App.instance?.getLockStyle().equals("pin")){
                radioPinLock.isChecked = true
            }
            radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when(checkedId){
                    R.id.radio_pattern_lock->{
                        startActivity(Intent(this,ChangePatternPassActivity::class.java))
                        finish()
                    }
                    R.id.radio_pin_lock->{
                        startActivity(Intent(this,ChangePincodeActivity::class.java))
                        finish()
                    }
                }
            }
            dialog.show()
        }
    }

}