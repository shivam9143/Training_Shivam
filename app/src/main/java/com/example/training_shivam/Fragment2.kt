package com.example.training_shivam


import android.annotation.SuppressLint
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class Fragment2 : Fragment() {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null
    lateinit var progressBar: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var rootView: View
    var myDataset : ArrayList<contactList> = ArrayList()

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
        rootView = inflater.inflate(R.layout.fragment_fragment2, container, false)
        viewManager = LinearLayoutManager(activity)
        progressBar =rootView.findViewById(R.id.MyprogressBar)
        recyclerView = rootView.findViewById<RecyclerView>(R.id.my_recycler_view)
        recyclerView.layoutManager=viewManager
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = MyAdapter(myDataset)
        return rootView
    }

    override fun onResume() {
        super.onResume()
        AsyncTaskExample().execute()
    }

    inner class AsyncTaskExample : AsyncTask<String, String, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        }

        override fun doInBackground(vararg p0: String?): String {

            var result = ""
            try {
                val url = URL("http://5d2d694443c343001498cfbd.mockapi.io/contactList")
                val httpURLConnection = url.openConnection() as HttpURLConnection
                httpURLConnection.readTimeout = 8000
                httpURLConnection.connectTimeout = 8000
                httpURLConnection.doOutput = true
                httpURLConnection.connect()
                myDataset.clear()
                Log.e("In Backgroung", url.toString())
                val responseCode: Int = httpURLConnection.responseCode
                Log.e("Response Code", "responseCode - " + responseCode)
                if (responseCode == 201) {
                    val inStream: InputStream = httpURLConnection.inputStream
                    val isReader = InputStreamReader(inStream)
                    val bReader = BufferedReader(isReader)
                    var tempStr: String?
                    try {
                        while (true) {
                            tempStr = bReader.readLine()
                            if (tempStr == null) {
                                break
                            }
                            result += tempStr
                        }
                        Log.e("Result", result.toString())
                    } catch (Ex: Exception) {
                        Log.e("Async ERROR", "Error in convertToString " + Ex.printStackTrace())
                    }
                }
            } catch (ex: Exception) {
                Log.d("", "Error in doInBackground " + ex.message)
            }
            return result
        }

        override fun onPostExecute(result: String?) {

            super.onPostExecute(result)
           progressBar.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
            if (result == "") {
                Log.e("Result Blank", result.toString())
            } else {
                var jsonObject: JSONObject = JSONObject(result)
                var jsonArray =  JSONArray()
                jsonArray = jsonObject.getJSONArray("key")
                var length : Int = jsonArray.length()
                var len1 : Int = length
                var jsonOb :JSONObject
                while(len1-->0)
                {
                    jsonOb = jsonArray[len1] as JSONObject
                    Log.e("row"+len1,jsonOb.toString())
                    myDataset.add(contactList(jsonOb.getString("name"),
                            jsonOb.getString("email"),
                            jsonOb.getString("phone"),
                            jsonOb.getString("picurl")))
                }
                viewAdapter = MyAdapter(myDataset)
                recyclerView.adapter = viewAdapter
            }
        }
    }



    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Fragment2.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): Fragment2 {
            val fragment = Fragment2()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }
}// Required empty public constructor
