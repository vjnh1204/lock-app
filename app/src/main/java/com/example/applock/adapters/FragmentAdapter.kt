package com.example.applock.adapters

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.applock.fragment.ApplicationsFragment
import com.example.applock.fragment.ProfilesFragment


class FragmentAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {ApplicationsFragment()}
            1 -> {ProfilesFragment()}
            else ->{throw Resources.NotFoundException("Position not found")}
        }
    }
}