package com.example.training_shivam.view.mainActivity.ui

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log

import androidx.fragment.app.Fragment
import androidx.navigation.NavController

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.training_shivam.R
import com.example.training_shivam.model.User
import com.example.training_shivam.utils.DatabaseHelper
import com.example.training_shivam.utils.ImageConverter
import kotlinx.android.synthetic.main.fragment_fragment4.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Fragment3.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
//    private OnFragmentInteractionListener mListener;

class Fragment4 : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var lgnuser : User
    lateinit var f : File
    var imageChanged : Boolean = false
    internal var rootView: View? = null
    internal lateinit var mt1: Button
    internal lateinit var mt2: Button
    internal lateinit var mt3: Button
    internal var fragment: Fragment? = null
    internal var fl: FrameLayout? = null
    internal lateinit var navController: NavController
    lateinit var dbHelper : DatabaseHelper
    private var btn: Button? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2

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
        rootView = inflater.inflate(R.layout.fragment_fragment4, container, false)

        /*  Declaring References    */
        dbHelper = DatabaseHelper(activity as AppCompatActivity)
        val sharedPref: SharedPreferences = activity!!.getSharedPreferences("Login_State", Context.MODE_PRIVATE)
        val email = sharedPref.getString("Email", "0")
        lgnuser = dbHelper.getLoggedInUserDetails(email)

        requestMultiplePermissions()

        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            editPic.setOnClickListener { showPictureDialog() }
            imageview = iv
            displayEditName.text = lgnuser.name
            editName.setText(lgnuser.name)
            editMobile.setText(lgnuser.mobile)
            editAddress.setText(lgnuser.address)
            if (lgnuser.pic != "demoFilePath") {
                val bitmap = BitmapFactory.decodeFile(lgnuser.pic)
                val circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 50)
                iv.setImageBitmap(circularBitmap)
            }
            updateButton.setOnClickListener()
            {
                if(imageChanged)
                {
                    var user: User = User(lgnuser.id, editName.text.toString(), lgnuser.email, editMobile.text.toString(), editAddress.text.toString(),"*****",f.absolutePath)
                    dbHelper.updateUser(user)
                    Toast.makeText(activity, "Update done Successfully", Toast.LENGTH_SHORT).show()
                }
                else
                {
                    var user: User = User(lgnuser.id, editName.text.toString(), lgnuser.email, editMobile.text.toString(), editAddress.text.toString(),"*****","")
                    dbHelper.updateUser(user)
                    Toast.makeText(activity, "Update done Successfully", Toast.LENGTH_SHORT).show()
                }


            }
        }
        catch (e : Exception)
        {
            Toast.makeText(activity, e.message.toString(), Toast.LENGTH_SHORT).show()

        }

    }

    fun requestMultiplePermissions()
    {
        val camerapermission = ContextCompat.checkSelfPermission(activity as AppCompatActivity, Manifest.permission.READ_EXTERNAL_STORAGE)
        val writepermission = ContextCompat.checkSelfPermission(activity as AppCompatActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readpermission = ContextCompat.checkSelfPermission(activity as AppCompatActivity, Manifest.permission.CAMERA)

        val listPermissionsNeeded = ArrayList<String>()

        if (camerapermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.CAMERA)
        }
        if (writepermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
        if (readpermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(activity as AppCompatActivity, listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
           // return false
        }
       // return true
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Log.d(TAG, "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {
                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms[Manifest.permission.CAMERA] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.ACCESS_FINE_LOCATION] = PackageManager.PERMISSION_GRANTED
                perms[Manifest.permission.RECORD_AUDIO] = PackageManager.PERMISSION_GRANTED
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms[permissions[i]] = grantResults[i]
                    // Check for both permissions
                    if (perms[Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED
                            && perms[Manifest.permission.READ_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED)
                             {
                        Log.d(TAG, "CAmera & Storage  permission granted")
                        // process the normal flow
//                        val i = Intent(this@MainActivity, WelcomeActivity::class.java)
//                        startActivity(i)
//                        finish()
                        //else any one or both the permissions are not granted
                    } else {
                        Log.d(TAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity as AppCompatActivity, Manifest.permission.CAMERA)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity as AppCompatActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                                || ActivityCompat.shouldShowRequestPermissionRationale(activity as AppCompatActivity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            showDialogOK("Service Permissions are required for this app",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> requestMultiplePermissions()
//                                            DialogInterface.BUTTON_NEGATIVE -> finish()

                                            // proceed with logic by disabling the related features or quit the app.
                                        }
                                    })
                        } else {
                            explain("You need to give some mandatory permissions to continue. Do you want to go to app settings?")
                            //                            //proceed with logic by disabling the related features or quit the app.
                        }//permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                    }
                }
            }
        }

    }

    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(activity as AppCompatActivity)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
    }

    private fun explain(msg: String) {
        val dialog = AlertDialog.Builder(activity as AppCompatActivity)
        dialog.setMessage(msg)
                .setPositiveButton("Yes") { paramDialogInterface, paramInt ->
                    //  permissionsclass.requestPermission(type,code);
                    startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("com.example.training_shivam")))
                }
//                .setNegativeButton("Cancel") { paramDialogInterface, paramInt -> finish() }
        dialog.show()
    }


    private fun showPictureDialog() {
        val pictureDialog = AlertDialog.Builder(activity as AppCompatActivity)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from gallery", "Capture photo from camera")
        pictureDialog.setItems(pictureDialogItems
        ) { dialog, which ->
            when (which) {
                0 -> choosePhotoFromGallary()
                1 -> takePhotoFromCamera()
            }
        }
        pictureDialog.show()
    }

    fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI)

        startActivityForResult(galleryIntent, GALLERY)
    }
    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA)
    }


    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        if (requestCode == GALLERY)
        {
            if (data != null)
            {
                val contentURI = data!!.data
                try
                {
                    val bitmap = MediaStore.Images.Media.getBitmap((activity as AppCompatActivity).contentResolver, contentURI)
                    val path = saveImage(bitmap)
                    Toast.makeText(activity, "Image Saved!", Toast.LENGTH_SHORT).show()
                    imageview!!.setImageBitmap(bitmap)

                }
                catch (e: IOException) {
                    e.printStackTrace()
                    Toast.makeText(activity, "Failed!", Toast.LENGTH_SHORT).show()
                }

            }

        }
        else if (requestCode == CAMERA)
        {
            val thumbnail = data!!.extras!!.get("data") as Bitmap
            imageview!!.setImageBitmap(thumbnail)
            saveImage(thumbnail)
            Toast.makeText(activity, "Image Saved!", Toast.LENGTH_SHORT).show()
        }
    }
    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
                (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {
            wallpaperDirectory.mkdirs()
        }

        try
        {
            Log.d("heel",wallpaperDirectory.toString())
             f = File(wallpaperDirectory, ((Calendar.getInstance()
                    .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            f.absoluteFile
            f.absolutePath
            f.name
            f.canonicalPath
            imageChanged = true
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(activity,
                    arrayOf(f.getPath()),
                    arrayOf("image/jpeg"), null)
            fo.close()
            Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"
        val REQUEST_ID_MULTIPLE_PERMISSIONS = 1
        private val IMAGE_DIRECTORY = "/IM-Training"
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment3.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): Fragment4 {
            val fragment = Fragment4()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
