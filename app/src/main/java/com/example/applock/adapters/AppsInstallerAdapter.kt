package com.example.applock.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applock.R
import com.example.applock.interfaces.AppOnClickListener
import com.example.applock.model.AppInfo
import com.example.applock.utils.Utils

class AppsInstallerAdapter(
    private val items:ArrayList<AppInfo>, private val mContext: Context,
    private val utils: Utils= Utils(mContext)
        ) : RecyclerView.Adapter<AppsInstallerAdapter.ViewHolder>() {
    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        //Holder view

        val ivAppIcon:ImageView = view.findViewById(R.id.iv_icon_app)
        val tvAppName:TextView = view.findViewById(R.id.tv_app_name)
        val ivAppLock:ImageView = view.findViewById(R.id.iv_lock_app)
        init {
            view.setOnClickListener {
                listener?.selectApp(adapterPosition)
            }
        }
        private var listener:AppOnClickListener?= null
        fun setListener(listener: AppOnClickListener){
            this.listener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.apps_install_item,parent,false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvAppName.text= item.appName
        holder.ivAppIcon.setImageDrawable(item.iconApp)
        val pk = item.packageName
        if (utils.isLock(pk)){
            holder.ivAppLock.setImageResource(R.drawable.lock)
        }
        else{
            holder.ivAppLock.setImageResource(R.drawable.unlock)
        }
        holder.setListener(object : AppOnClickListener{
            override fun selectApp(position: Int) {
                if (utils.isLock(pk)){
                    holder.ivAppLock.setImageResource(R.drawable.unlock)
                    utils.unLock(pk)
                }
                else{
                    holder.ivAppLock.setImageResource(R.drawable.lock)
                    utils.lock(pk)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }
}