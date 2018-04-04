package com.liubase.grouplogin

import android.support.v4.app.*
import android.util.*
import com.liubase.groupmain.*

/* Created by Jeffrey Liu on 2/04/2018. */
class LoginModule : BaseModule() {
    
    override fun entryPoint() : Fragment {
        return LoginFragment()
    }
}