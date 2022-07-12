package com.example.applock.fragment

import android.annotation.SuppressLint
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.applock.R
import com.example.applock.adapters.AppsInstallerAdapter
import com.example.applock.model.AppInfo

class ApplicationsFragment : Fragment() {
    private lateinit var recyclerView:RecyclerView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_applications, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter= AppsInstallerAdapter(getApplications(activity),activity)
        recyclerView.adapter = adapter
    }
    @SuppressLint("QueryPermissionsNeeded")
    private fun getApplications(activity: FragmentActivity?):ArrayList<AppInfo> {
        val listApp = ArrayList<AppInfo>()
        val manager = activity?.packageManager
        val list:ArrayList<ApplicationInfo> = manager!!.getInstalledApplications(PackageManager.GET_META_DATA) as ArrayList<ApplicationInfo>
        for(appInfo in list){
            listApp.add(AppInfo(appInfo.loadIcon(manager),appInfo.packageName,appInfo.loadLabel(manager).toString()))
        }
        return listApp
    }

}

