package com.example.applock.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.applock.fragment.ApplicationsFragment
import com.example.applock.fragment.ProfilesFragment

class FragmentAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount()=2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            1 -> {ApplicationsFragment()}
            2 -> {ProfilesFragment()}
            else ->{ApplicationsFragment()}
        }
    }

}