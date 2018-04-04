package com.liubase.grouplogin

import android.content.*
import android.os.*
import android.support.v4.app.*
import android.text.*
import android.view.*
import android.widget.*
import java.util.regex.*

/* Created by Jeffrey Liu on 3/04/2018. */
class LoginFragment : Fragment() {
    
    private val myTag = "Login Fragment"
    
    private lateinit var lView : LinearLayout
    private lateinit var email : EditText
    private lateinit var password : EditText
    private lateinit var login : Button
    private lateinit var message : TextView
    
    private var checkemail : Boolean = false
    private var checkepwd : Boolean = false
    
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
            savedInstanceState : Bundle?) : View? {
        lView = inflater.inflate(R.layout.login_login, container, false) as LinearLayout
    
        email = lView.findViewById(R.id.email) as EditText
        password = lView.findViewById(R.id.password) as EditText
        login = lView.findViewById(R.id.login) as Button
        message = lView.findViewById(R.id.message) as TextView
        
        initFragment()
        return lView
    }
    
    private fun initFragment() {
        login.isEnabled = false
        val sp : SharedPreferences = this.activity!!.getSharedPreferences("config", Context.MODE_PRIVATE)
        val eString = sp.getString("login_email", "")
        if (eString.isEmpty()) {
            email.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int,
                        after : Int) {
                    
                }
                
                override fun onTextChanged(s : CharSequence?, start : Int, before : Int,
                        count : Int) {
                    
                }
                
                override fun afterTextChanged(s : Editable?) {
                    
                    val tmp = email.text.toString()
                    val reg = ".*@.+"
                    val pa = Pattern.compile(reg)
                    val ma = pa.matcher(tmp)
                    if (ma.matches()) {
                        checkemail = true
                        message.visibility = View.GONE
                        message.text = ""
                        if (checkepwd) {
                            login.isEnabled = true
                        }
                    } else {
                        login.isEnabled = false
                        message.visibility = View.VISIBLE
                        message.text = "邮箱地址格式不匹配!"
                    }
                }
            })
        } else {
            email.setText(eString)
            checkemail = true
            email.isFocusable = false
            email.isCursorVisible = false
        }
        
        
        password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s : CharSequence?, start : Int, count : Int,
                    after : Int) {
                
            }
            
            override fun onTextChanged(s : CharSequence?, start : Int, before : Int, count : Int) {
            
            }
            
            override fun afterTextChanged(s : Editable?) {
                
                val tmp = password.text.toString().length
                
                if (tmp > 7) {
                    checkepwd = true
                    message.visibility = View.GONE
                    message.text = ""
                    if (checkemail) {
                        login.isEnabled = true
                    }
                } else {
                    login.isEnabled = false
                    message.visibility = View.VISIBLE
                    message.text = "密码必须大于7位!"
                }
            }
        })
    
        login.setOnClickListener {
            login.isEnabled = false
            /*this.activity.mAuth = FirebaseAuth.getInstance()
            pa.mAuth.signInWithEmailAndPassword(email.text.toString(),
                    password.text.toString()).addOnCompleteListener {task ->
                if (task.isSuccessful) {
                    try {
                        pa.user = pa.mAuth.currentUser!!
                        val sp : SharedPreferences = pa.getSharedPreferences("config", Context.MODE_PRIVATE)
                        sp.edit().putString("email",email.text.toString()).commit()
                        val uFragment = UserFragment()
                        pa.swapFragment(fIndex, uFragment)
                    } catch (e : Exception) {
                        message.text = "用户错误!!!"
                    }
                } else {
                    message.visibility = View.VISIBLE
                    if (task.exception is FirebaseAuthInvalidUserException) {
                        message.text = "无效的用户名!!!"
                    } else if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        message.text = "密码错误!!!"
                    } else {
                        message.text = "网络错误!!!"
                    }
                }
            }*/
        
        
        }
    }
}