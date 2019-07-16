package com.example.training_shivam

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.training_shivam.model.User
import com.example.training_shivam.utils.DatabaseHelper
import com.example.training_shivam.utils.InputValidation
import kotlinx.android.synthetic.main.fragment_fragment3.*
import com.google.android.material.snackbar.Snackbar


class Fragment3 : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var  rootView : View
    lateinit var userName  : String
    lateinit var userEmail  : String
    lateinit var userMobile  : String
    lateinit var userAddress  : String
    lateinit var userPassword  : String
    lateinit var userCPassword  : String
    private lateinit var inputValidation: InputValidation
    private lateinit var databaseHelper: DatabaseHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment3, container, false)
        (activity as AppCompatActivity).supportActionBar!!.hide()
        inputValidation = InputValidation(activity as AppCompatActivity)
        databaseHelper = DatabaseHelper(activity as AppCompatActivity)

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        signUpBtn.setOnClickListener {
            postDataToSQLite()
        }

        already_user.setOnClickListener()
        {
            val accountsIntent = Intent(activity, loginActivity::class.java)
            startActivity(accountsIntent)
        }

    }

    private fun postDataToSQLite() {
        if (!inputValidation!!.isInputEditTextFilled(fullName, "Name field cannot be blank")) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(userEmailId, "Email field cannot be blank")) {
            return
        }
        if (!inputValidation!!.isInputEditTextEmail(userEmailId, "Invalid Email")) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(password, "Password field cannot be blank!")) {
            return
        }
        if (!inputValidation!!.isInputEditTextMatches(password, confirmPassword,"Passwords do not match")) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(mobileNumber, "Password field cannot be blank!")) {
            return
        }
        if (!inputValidation!!.isInputEditTextFilled(location, "Password field cannot be blank!")) {
            return
        }


        if (!databaseHelper!!.checkUser(userEmailId!!.text.toString().trim())) {

            var user = User(name = fullName!!.text.toString().trim(),
                    email = userEmailId!!.text.toString().trim(),
                    password = password!!.text.toString().trim(),
                    mobile = mobileNumber!!.text.toString().trim(),
                    address = location!!.text.toString().trim(),
                    pic = "demoFilePath")

            databaseHelper!!.addUser(user)

            // Snack Bar to show success message that record saved successfully
            Snackbar.make(signupForm!!, "Registration Successful", Snackbar.LENGTH_LONG).show()
            emptyInputEditText()


        } else {
            // Snack Bar to show error message that record already exists
            Snackbar.make(signupForm!!, "Entered Email already exists", Snackbar.LENGTH_LONG).show()
        }


    }

    /**
     * This method is to empty all input edit text
     */
    private fun emptyInputEditText() {
        fullName!!.text = null
        userEmailId!!.text = null
        mobileNumber!!.text = null
        password!!.text = null
        confirmPassword!!.text = null
        location!!.text =null
    }




    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        fun newInstance(param1: String, param2: String): Fragment3 {
            val fragment = Fragment3()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}
