package com.example.training_shivam.view.mainActivity.ui
import android.content.Context
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.example.training_shivam.R
import com.example.training_shivam.model.User
import com.example.training_shivam.utils.DatabaseHelper
import com.example.training_shivam.utils.ImageConverter
import kotlinx.android.synthetic.main.fragment_fragment1.*


class Fragment1 : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    internal lateinit var rootView: View
    lateinit var dbHelper : DatabaseHelper
    lateinit var lgnuser : User
    lateinit var navController: NavController

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
        rootView = inflater.inflate(R.layout.fragment_fragment1, container, false)
        navController = findNavController(activity as AppCompatActivity, R.id.my_nav_host_fragment)



        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
       // val defaultValue = resources.getInteger(R.integer.shared)
        val sharedPref: SharedPreferences = activity!!.getSharedPreferences("Login_State", Context.MODE_PRIVATE)
        editProfile.setOnClickListener()
        {
            navController.navigate(R.id.frag4)
        }
        val email = sharedPref.getString("Email", "0")
try {
    if(sharedPref.getInt("LoggedIn", 0)==1) {
        dbHelper = DatabaseHelper(activity as AppCompatActivity)
        lgnuser = dbHelper.getLoggedInUserDetails(email)
        name.text = lgnuser.name
        mobile.text = lgnuser.mobile
        address.text = lgnuser.address
        emailProfile.text = lgnuser.email
        if (lgnuser.pic != "demoFilePath")
        {
            val bitmap = BitmapFactory.decodeFile(lgnuser.pic)
            val circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 50)
            main_backdrop.setImageBitmap(circularBitmap)

        }
}

//            Toast.makeText(activity, "Email=" + lgnuser.email + ", Name=" + lgnuser.name + ",mobile=" + lgnuser.mobile, Toast.LENGTH_SHORT).show()
        }
catch (e : Exception)
{
    Toast.makeText(activity, e.message.toString(), Toast.LENGTH_SHORT).show()
}
    }

    companion object {

        private val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): Fragment1 {
            val fragment = Fragment1()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor