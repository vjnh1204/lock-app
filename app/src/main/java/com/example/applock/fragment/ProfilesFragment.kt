package com.example.applock.fragment
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment
import com.example.applock.MainActivity
import com.example.applock.R


class ProfilesFragment : Fragment() {

    private lateinit var switch:    SwitchCompat
    private lateinit var saveData: SaveData
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view            = inflater.inflate(R.layout.fragment_profiles,container,false)

        //SharePreferences states look
            saveData    =    SaveData(requireActivity())

        if(saveData.loadDarkModeState() == true){
            context?.theme?.applyStyle(R.style.Theme_AppLock,true)
        }else {
            context?.theme?.applyStyle(R.style.Theme_AppLock_Dark, true)
        }
        //Switch on/off
        switch          =    view.findViewById(R.id.switchDarkMode) as SwitchCompat
        if(saveData.loadDarkModeState() ==true){
            switch.isChecked =  true
        }
        //On Click ON OFF

        switch!!.setOnCheckedChangeListener{_,isChecked ->

            if(isChecked){
                //Light Mode
               saveData.setDarkModeState(true)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

                Log.d("aa",isChecked.toString())

            }else{
                //Dark Mode
                saveData.setDarkModeState(false)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.d("bb",isChecked.toString())

            }
        }

//        fun restartApplication(){
////            val application: Application = requireActivity().application
//            val i = Intent(requireActivity(),MainActivity::class.java)
//            startActivity(i)
//
//
//        }

        return view
    }


    }

