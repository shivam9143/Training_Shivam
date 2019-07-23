package com.example.training_shivam.utils


import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.training_shivam.R
import org.json.JSONObject

fun ImageView.loadImg(imageUrl: String?) =
        Glide.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.profilepic)
                .into(this)

fun readJSONFromAsset(applicationContext: Context): JSONObject {
    val jsonfile: String = applicationContext.assets.open("ela.json").bufferedReader().use {it.readText()}

    return JSONObject(jsonfile)
}