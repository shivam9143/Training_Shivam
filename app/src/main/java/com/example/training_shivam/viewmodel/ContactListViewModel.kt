package com.example.training_shivam.viewmodel

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bumptech.glide.Glide
import com.example.training_shivam.R
import com.example.training_shivam.model.contactList

class ContactListViewModel : ViewModel
{
    var personName =""
    var personEmail = ""
    var contactNumber = ""
    var profileImageUrl = ""

    constructor() : super()

    constructor(contactList : contactList) : super() {
        this.personName = contactList.personName
        this.personEmail = contactList.personEmail
        this.contactNumber = contactList.contactNumber
        this.profileImageUrl = contactList.profileImageUrl
    }

    fun getImageUrl() : String
    {
        return profileImageUrl
    }

    var arrayListMutableLivedata = MutableLiveData<ArrayList<ContactListViewModel>>()

    var arrayList = ArrayList<ContactListViewModel>()

    fun getArrayList() : MutableLiveData<ArrayList<ContactListViewModel>>
    {
        val contactList1 = contactList("shivam", "skji72@gmail.com", "9044224967", "https://avatars1.githubusercontent.com/u/21219419?s=400&v=4")
        val contactList2 = contactList("shivam", "skji72@gmail.com", "9044224967", "https://avatars1.githubusercontent.com/u/21219419?s=400&v=4")

        val contactListViewModel1 : ContactListViewModel = ContactListViewModel(contactList1)
        val contactListViewModel2 : ContactListViewModel = ContactListViewModel(contactList1)

        arrayList!!.add(contactListViewModel1)
        arrayList!!.add(contactListViewModel2)

        arrayListMutableLivedata.value = arrayList



        return arrayListMutableLivedata
    }

}

object ImageBindingAdapter
{
    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view: ImageView, url : String)
    {
        Glide.with(view.context).load(url).placeholder(R.drawable.profilepic).into(view)
    }

}