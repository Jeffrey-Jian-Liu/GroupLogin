package com.liubase.grouplogin

import android.os.*
import android.support.v4.app.*
import android.util.*
import android.view.*
import android.widget.*

/* Created by Jeffrey Liu on 3/04/2018. */
class LoginFragment : Fragment() {
    
    private val myTag = "Login Fragment"
    
    private lateinit var lView : LinearLayout
   
    private var checkemail : Boolean = false
    private var checkepwd : Boolean = false
    
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
            savedInstanceState : Bundle?) : View? {
        lView = inflater.inflate(R.layout.login_login, container, false) as LinearLayout
        
        return lView
    }
}