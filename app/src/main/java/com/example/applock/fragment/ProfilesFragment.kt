package com.example.applock.fragment


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
//import android.widget.CompoundButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
//import com.example.applock.MainActivity
import com.example.applock.R

class ProfilesFragment : Fragment() {
    private lateinit var switch:    SwitchCompat
    private lateinit var textView: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val view = inflater.inflate(R.layout.fragment_profiles,container,false)
            switch      =    view.findViewById(R.id.switchDarkMode)
            textView    =    view.findViewById(R.id.textview_id)
            if(AppCompatDelegate.getDefaultNightMode()== AppCompatDelegate.MODE_NIGHT_YES){
                context?.theme?.applyStyle(R.style.Theme_AppLock,false    )

            }else {
                context?.theme?.applyStyle(R.style.Theme_AppLock_Dark,true)
            }

            switch.setOnCheckedChangeListener{_,isChecked ->

                if(isChecked){
                    //Light Mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                    Log.d("aa",isChecked.toString())
                }else{
                    //Dark Mode
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    Log.d("bb",isChecked.toString())

                }
            }


        // Inflate the layout for this fragment
        return view
    }


}