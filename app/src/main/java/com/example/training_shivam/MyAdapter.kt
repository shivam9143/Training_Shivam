package com.example.training_shivam

import android.Manifest.permission.CALL_PHONE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.RecyclerView



class MyAdapter(private val myDataset: ArrayList<contactList>) :
        RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        var personImage: ImageView
        var personName: TextView
        var personEmail: TextView
        var call: ImageView

        init {
            personImage = itemView.findViewById(R.id.personImageView)
            personName = itemView.findViewById(R.id.personName)
            personEmail = itemView.findViewById(R.id.personEmail)
            call = itemView.findViewById(R.id.call)
        }

        fun bind(contact: contactList) {
            personName.text = contact.personName
            personEmail.text = contact.personEmail
            personImage.loadImg(contact.profileImageUrl)
            call.setOnClickListener {
                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.contactNumber))
                view.context.startActivity(intent)
            }
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyAdapter.MyViewHolder {
        // create a new view
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_item, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return MyViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
       holder.bind(myDataset[position])
        }
    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}
