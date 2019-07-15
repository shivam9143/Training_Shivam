package com.example.training_shivam

import android.net.Uri
import android.os.Bundle

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.NavController
import androidx.navigation.Navigation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout


/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Fragment3.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Fragment3.newInstance] factory method to
 * create an instance of this fragment.
 */
//    private OnFragmentInteractionListener mListener;

class Fragment4 : Fragment(), View.OnClickListener {

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    internal var rootView: View? = null
    internal lateinit var mt1: Button
    internal lateinit var mt2: Button
    internal lateinit var mt3: Button
    internal var fragmentManager: FragmentManager? = null
    internal var fragmentTransaction: FragmentTransaction? = null
    internal var fragment: Fragment? = null
    internal var fl: FrameLayout? = null
    internal lateinit var navController: NavController

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
        //        fragmentManager = getActivity().getSupportFragmentManager();
        //        fragmentTransaction = fragmentManager.beginTransaction();

        /*  Declaring References    */
        fl = rootView!!.findViewById(R.id.my_nav_host_fragment)
        mt1 = rootView!!.findViewById(R.id.mt1)
        mt2 = rootView!!.findViewById(R.id.mt2)
        mt3 = rootView!!.findViewById(R.id.mt3)
        navController = Navigation.findNavController(activity!!, R.id.my_nav_host_fragment)


        mt1.setOnClickListener { navController.navigate(R.id.frag1) }
        mt2.setOnClickListener { navController.navigate(R.id.frag2) }
        mt3.setOnClickListener {
            navController.popBackStack()

            navController.navigate(R.id.frag3)
        }


        return rootView
    }

    override fun onClick(view: View) {
        val fragment: Fragment
        when (view.id) {
            R.id.mt1 -> {
                fragment = Fragment1()
                fragmentTransaction!!.replace(R.id.my_nav_host_fragment, fragment)
            }
            R.id.mt2 -> {
                fragment = Fragment2()
                fragmentTransaction!!.replace(R.id.my_nav_host_fragment, fragment)
            }
            R.id.mt3 -> {
                fragment = Fragment3()
                fragmentTransaction!!.replace(R.id.my_nav_host_fragment, fragment)
            }
            else -> {
            }
        }
    }

    //    // TODO: Rename method, update argument and hook method into UI event
    //    public void onButtonPressed(Uri uri) {
    //        if (mListener != null) {
    //            mListener.onFragmentInteraction(uri);
    //        }
    //    }
    //
    //    @Override
    //    public void onAttach(Context context) {
    //        super.onAttach(context);
    //        if (context instanceof OnFragmentInteractionListener) {
    //            mListener = (OnFragmentInteractionListener) context;
    //        } else {
    //            throw new RuntimeException(context.toString()
    //                    + " must implement OnFragmentInteractionListener");
    //        }
    //    }
    //
    //    @Override
    //    public void onDetach() {
    //        super.onDetach();
    //        mListener = null;
    //    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
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
