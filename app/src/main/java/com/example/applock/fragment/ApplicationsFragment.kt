package com.example.applock.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ResolveInfo
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
        val intent = Intent(Intent.ACTION_MAIN,null)
        intent.addCategory(Intent.CATEGORY_LAUNCHER)

        val infoApps:ArrayList<ResolveInfo> = manager!!.queryIntentActivities(intent,0) as ArrayList<ResolveInfo>
        for(infoApp in infoApps){
            val activityInfo = infoApp.activityInfo
            if(activityInfo.packageName.equals("com.example.applock")){
                continue
            }
            listApp.add(AppInfo(activityInfo.loadIcon(manager),
                activityInfo.packageName,activityInfo.loadLabel(manager).toString()))
        }
        return listApp
    }

}

