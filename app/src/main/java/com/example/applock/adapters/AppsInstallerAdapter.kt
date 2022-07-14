package com.example.applock.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.applock.App
import com.example.applock.R
import com.example.applock.model.AppInfo

class AppsInstallerAdapter(var items:ArrayList<AppInfo>, mContext: FragmentActivity?) : RecyclerView.Adapter<AppsInstallerAdapter.ViewHolder>() {
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        //Holder view
        val ivAppIcon:ImageView = view.findViewById(R.id.iv_icon_app)
        val tvAppName:TextView = view.findViewById(R.id.tv_app_name)
        val ivAppLock:ImageView = view.findViewById(R.id.iv_lock_app)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.apps_install_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvAppName.text= item.appName
        holder.ivAppIcon.setImageDrawable(item.iconApp)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    // function get listappNew
    fun setlistAppNew(listAppNew: ArrayList<AppInfo>){
        this.items = listAppNew
        notifyDataSetChanged()
    }
}