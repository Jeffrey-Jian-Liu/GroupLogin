package com.liubase.grouplogin

import android.support.v4.app.*
import com.liubase.groupmain.*

/* Created by Jeffrey Liu on 2/04/2018. */
class LoginModule : BaseModule() {
    
    override fun entryFragment() : Fragment {
        return LoginFragment()
    }
}