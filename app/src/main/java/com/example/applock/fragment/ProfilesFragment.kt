package com.example.applock.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.example.applock.R

class ProfilesFragment : Fragment() {
    private lateinit var switch: SwitchCompat
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?


    ): View? {
        val view = inflater.inflate(R.layout.fragment_profiles,container,false)
        switch   = view.findViewById(R.id.switchDarkMode)
        if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_NO){
            getContext()?.getTheme()?.applyStyle(R.style.Theme_AppLock, true);
        }else{
            getContext()?.getTheme()?.applyStyle(R.style.Theme_AppLock_Dark, true);
        }
        switch.setOnCheckedChangeListener { compoundButton, isChecked ->
            if(isChecked){
                //Light Mode

                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO )
                Log.d("aa", isChecked.toString())
            }else{
                //Dark Mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.d("bb", isChecked.toString())

            }

        }
        // Inflate the layout for this fragment
        return view
    }

}