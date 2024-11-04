package com.example.a15_4_contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val context: Context, private val listener: ContactClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    val contacts = ArrayList<Contact>()

    fun updateList(newList: List<Contact>){
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.nameTV)
        private val phoneTV: TextView = itemView.findViewById(R.id.phoneTV)
        private val timeTV: TextView = itemView.findViewById(R.id.timeTV)
        val imageButton: ImageButton = itemView.findViewById(R.id.imageButton)

        fun bind(contact: Contact) {
            nameTV.text = contact.name
            phoneTV.text = contact.phone
            timeTV.text = contact.time
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val viewHolder = ContactViewHolder(
            LayoutInflater.from(context).inflate(R.layout.contact_item, parent, false)
        )
        viewHolder.imageButton.setOnClickListener {
            listener.onItemClicked(contacts[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        var currentContact = contacts[position]
        holder.bind(currentContact)
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    interface ContactClickListener {
        fun onItemClicked(contact: Contact)
    }

}