package com.example.training_shivam

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import com.example.training_shivam.utils.DatabaseHelper
import com.example.training_shivam.utils.InputValidation
import kotlinx.android.synthetic.main.fragment_fragment3.*
import kotlinx.android.synthetic.main.activity_login.*

class loginActivity : AppCompatActivity() {



    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        databaseHelper = DatabaseHelper(this)
        inputValidation = InputValidation(this)

        loginBtn.setOnClickListener(View.OnClickListener {
            verifyFromSQLite()
        })


    }

    private fun verifyFromSQLite() {

        if (!inputValidation!!.isInputEditTextFilled(login_emailid!!, "E-mail field cannot be blank")) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(login_emailid!! , "Please enter valid email")) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(login_password!!, "Password field cannot be blank")) {
            return
        }

        if (databaseHelper!!.checkUser(login_emailid!!.text.toString().trim { it <= ' ' }, login_password!!.text.toString().trim { it <= ' ' })) {


            val accountsIntent = Intent(this, Activity1::class.java)
            val sharedPref: SharedPreferences = getSharedPreferences("Login_State", Context.MODE_PRIVATE)
            with (sharedPref.edit()) {
                putInt("LoggedIn", 1)
                Log.e("Email===",login_emailid.text.toString())
                putString("Email", login_emailid.text.toString())
                commit()
            }
            Log.e("Email===",sharedPref.getString("Email","0"))

            accountsIntent.putExtra("EMAIL", login_emailid!!.text.toString().trim { it <= ' ' })
            emptyInputEditText()
            startActivity(accountsIntent)

        } else {

            // Snack Bar to show success message that record is wrong
            Snackbar.make(login!!, "No matching record found!", Snackbar.LENGTH_LONG).show()
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        login_emailid!!.text = null
        login_password!!.text = null
    }

}
