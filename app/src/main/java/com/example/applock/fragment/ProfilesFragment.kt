package com.example.applock.fragment

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import com.example.applock.App
import com.example.applock.FavoriteActivity
import com.example.applock.R
import com.example.applock.model.AppInfo
import com.example.applock.utils.Constant
import com.example.applock.utils.Utils
import io.paperdb.Paper

class ProfilesFragment : Fragment() {
    private var favoriteActivityLauncher : ActivityResultLauncher<Intent> =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK){
                val data:Intent? = result.data
                if (data!= null){
                    val list = data.getSerializableExtra("AAA") as ArrayList<*>
                    val name = data.getStringExtra("nameProfile")
                }
            }

        }
    private var linearLayout:LinearLayout? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profiles, container, false)
        linearLayout= view.findViewById(R.id.ll_root)
        val lockAllCardView: CardView = view.findViewById(R.id.lock_all)
        lockAllCardView.setOnClickListener {
            dialogLock()
        }
        val unLockAllCardView:CardView = view.findViewById(R.id.open_all)
        unLockAllCardView.setOnClickListener {
            dialogUnLock()
        }
        val btnCreateProfile:Button = view.findViewById(R.id.btn_createProfile)
        btnCreateProfile.setOnClickListener {
            favoriteActivityLauncher.launch(Intent(activity,FavoriteActivity::class.java))
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