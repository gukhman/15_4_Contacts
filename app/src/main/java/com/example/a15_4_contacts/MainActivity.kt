package com.example.a15_4_contacts

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity() {

    var db: AppDatabase? = null

    private lateinit var nameET: EditText
    private lateinit var phoneET: EditText
    private lateinit var saveBTN: Button
    private lateinit var delBTN: Button
    private lateinit var contactsTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWindowInsets(R.id.main)
        setupToolbar(R.id.toolbar, false)

        db = AppDatabase.getDatabase(this)
        refreshContacts(db!!)

        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        saveBTN = findViewById(R.id.saveBTN)
        delBTN = findViewById(R.id.delBTN)
        contactsTV = findViewById(R.id.contactsTV)
    }

    override fun onResume() {
        super.onResume()

        saveBTN.setOnClickListener {
            val contact =
                Contact(0, nameET.text.toString(), phoneET.text.toString())
            addContact(db!!, contact)
        }

        delBTN.setOnClickListener {
            deleteAllContacts(db!!)
        }
    }

    fun deleteAllContacts(db: AppDatabase) {
        lifecycleScope.launch {
            db.getContactDao().deleteAll()
            refreshContacts(db)
        }
    }

    fun addContact(db: AppDatabase, contact: Contact) {
        lifecycleScope.launch {
            if (contact.lastName.length > 1 && contact.phoneNumber.isNotEmpty()) {
                db.getContactDao().insert(contact)
                refreshContacts(db)
            }
        }
    }

    fun refreshContacts(db: AppDatabase) {
        lifecycleScope.launch {
            val contacts = db.getContactDao().getAllContacts()
            nameET.setText("")
            phoneET.setText("")
            contactsTV.text = ""
            contacts.forEach { i -> contactsTV.append("${i.lastName}    ${i.phoneNumber}\n") }
        }
    }
}
