package com.example.applock.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import com.example.applock.App
import com.example.applock.R
import com.example.applock.utils.Constant
import com.example.applock.utils.Utils

class ProfilesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profiles, container, false)
        val lockAllCardView: CardView = view.findViewById(R.id.lock_all)
        lockAllCardView.setOnClickListener {
            dialogLock()
        }
        val unLockAllCardView:CardView = view.findViewById(R.id.open_all)
        unLockAllCardView.setOnClickListener {
            dialogUnLock()
        }
        return view
    }
    private fun dialogLock(){
        val utils = context?.let { Utils(it) }
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Xác nhận")
        alertDialog.setMessage("Bạn muốn kích hoạt cấu hình này không?")
        val listApps =  Constant.getApplications(requireActivity())
        alertDialog.setPositiveButton("Có"){_,_ ->
                for (app in listApps){
                    val pk= app.packageName
                    if (!utils!!.isLock(pk)){
                        utils.lock(pk)
                    }
            }

        }
        alertDialog.setNegativeButton("Không"){dialogInterface,_ ->
            dialogInterface.dismiss()
        }
        alertDialog.show()
    }
    private fun dialogUnLock(){
        val utils = context?.let { Utils(it) }
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setTitle("Xác nhận")
        alertDialog.setMessage("Bạn muốn kích hoạt cấu hình này không?")
        val listApps =  Constant.getApplications(requireActivity())
        alertDialog.setPositiveButton("Có"){_,_ ->
                for (app in listApps){
                    val pk= app.packageName
                    if (utils!!.isLock(pk)){
                        utils.unLock(pk)
                    }
            }
        }
        alertDialog.setNegativeButton("Không"){dialogInterface,_ ->
            dialogInterface.dismiss()
        }
        alertDialog.show()
    }
}