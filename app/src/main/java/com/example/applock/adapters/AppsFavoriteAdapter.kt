package com.example.applock.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.applock.R
import com.example.applock.adapters.AppsFavoriteAdapter.ViewHolder
import com.example.applock.interfaces.AppOnClickListener
import com.example.applock.model.AppInfo
import com.example.applock.utils.Utils

class AppsFavoriteAdapter(private val items:ArrayList<AppInfo>,
                          private val mContext: Context, private val utils: Utils =Utils(mContext)) : RecyclerView.Adapter<ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView:ImageView = itemView.findViewById(R.id.iv_icon_app)
        val textView:TextView = itemView.findViewById(R.id.tv_app_name)
        val ivAppLock:ImageView = itemView.findViewById(R.id.iv_lock_app)
        init {
            itemView.setOnClickListener{
                listener?.selectApp(adapterPosition)
            }
        }
        private var listener: AppOnClickListener? = null
        fun setListener(listener: AppOnClickListener){
            this.listener = listener
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.apps_install_item,parent,false))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        utils.reset()
        val item = items[position]
        holder.imageView.setImageDrawable(mContext.packageManager.getApplicationIcon(item.packageName))
        holder.textView.text = item.appName
        val pk = item.packageName
        if(utils.isFavorite(pk)){
            holder.ivAppLock.setImageResource(R.drawable.lock)
        }
        else{
            holder.ivAppLock.setImageResource(R.drawable.unlock)
        }

        holder.setListener(object :AppOnClickListener{
            override fun selectApp(position: Int) {
                if (utils.isFavorite(pk)){
                    holder.ivAppLock.setImageResource(R.drawable.unlock)
                    utils.unFavorite(pk)
                }
                else{
                    holder.ivAppLock.setImageResource(R.drawable.lock)
                    utils.favorite(pk)
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

}