package com.liubase.groups.grouplogin

import android.content.*
import android.os.*
import android.provider.*
import android.support.v4.app.*
import android.util.*
import android.view.*
import android.widget.*
import com.google.android.gms.tasks.*
import com.google.firebase.*
import com.google.firebase.auth.*
import com.liubase.groups.R
import com.liubase.groups.groupnetwork.*
import java.io.*

/* Created by Jeffrey Liu on 7/04/2018. */
class UserFragment : Fragment() {
    
    private val myTag = "User Fragment"
    
    lateinit var module : LoginModule
    private lateinit var lView : LinearLayout
    private lateinit var image : ImageView
    private lateinit var email : EditText
    private lateinit var emailCheck : CheckBox
    private lateinit var emailActivate : Button
    private lateinit var phone : EditText
    private lateinit var phoneCheck : CheckBox
    private lateinit var phoneActivate : Button
    private lateinit var cText : TextView
    private lateinit var code : EditText
    private lateinit var cButton : Button
    
    override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
            savedInstanceState : Bundle?) : View? {
        lView = inflater.inflate(R.layout.login_user, container, false) as LinearLayout
        
        image = lView.findViewById(R.id.image) as ImageView
        image.setOnClickListener {
            val filePath : String = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + System.currentTimeMillis() + ".png"
            val imageFile = File(filePath)
            val tPhoto = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            //tPhoto.putExtra(MediaStore.EXTRA_OUTPUT,
            //Uri.fromFile(imageFile))
            startActivityForResult(tPhoto, 2)
        }
        
        email = lView.findViewById(R.id.email) as EditText
        val se = NetworkAPI.firebaseAuthUser()?.email ?: ""
        email.setText(getString(R.string.login_user_info_email, se))
        email.isFocusable = false
        email.isCursorVisible = false
        
        emailCheck = lView.findViewById(R.id.email_check) as CheckBox
        emailCheck.isChecked = NetworkAPI.firebaseAuthUser()?.isEmailVerified ?: false
        emailCheck.isClickable = false
        
        emailActivate = lView.findViewById(R.id.email_activate) as Button
        
        if (!emailCheck.isChecked) {
            emailActivate.visibility = View.VISIBLE
        }
        emailActivate.setOnClickListener {
            NetworkAPI.firebaseAuthEmailVerification(OnCompleteListener {task ->
                if (task.isSuccessful) {
                    Log.d(myTag, "Email sent.")
                }
            })
        }
        
        phone = lView.findViewById(R.id.phone) as EditText
        var sp = NetworkAPI.firebaseAuthUser()?.phoneNumber ?: ""
        phone.setText(getString(R.string.login_user_info_phone, sp))
        
        cText = lView.findViewById(R.id.code_text) as TextView
        code = lView.findViewById(R.id.code) as EditText
        cButton = lView.findViewById(R.id.phone_ok) as Button
        
        
        phoneCheck = lView.findViewById(R.id.phone_check) as CheckBox
        phoneCheck.isChecked = !NetworkAPI.firebaseAuthUser()?.phoneNumber.isNullOrBlank()
        phoneCheck.isClickable = false
        
        phoneActivate = lView.findViewById(R.id.phone_activate) as Button
        if (!phoneCheck.isChecked) {
            phoneActivate.visibility = View.VISIBLE
            cText.visibility = View.VISIBLE
            code.visibility = View.VISIBLE
            cButton.visibility = View.VISIBLE
        } else {
            phone.isFocusable = false
            phone.isCursorVisible = false
            phoneActivate.visibility = View.GONE
            cText.visibility = View.GONE
            code.visibility = View.GONE
            cButton.visibility = View.GONE
        }
        
        var verificationId : String? = ""
        phoneActivate.setOnClickListener {
            val sn = "+61" + phone.text.substring(5)
            activity?.let {it1 ->
                NetworkAPI.firebaseAuthPhone(sn, it1,
                        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            override fun onVerificationFailed(p0 : FirebaseException?) {
                                Log.d(myTag, p0?.toString())
                            }
                            
                            override fun onVerificationCompleted(p0 : PhoneAuthCredential?) {
                            }
                            
                            override fun onCodeSent(p0 : String?,
                                    p1 : PhoneAuthProvider.ForceResendingToken?) {
                                verificationId = p0
                            }
                        })
            }
        }
        
        cButton.setOnClickListener {
            NetworkAPI.firebaseAuthCredential(verificationId!!, code.text.toString(),
                    OnCompleteListener {task ->
                        if (task.isSuccessful) {
                            Log.d(myTag, "Phone Verification.")
                        }
                    })
        }
        return lView
    }
}