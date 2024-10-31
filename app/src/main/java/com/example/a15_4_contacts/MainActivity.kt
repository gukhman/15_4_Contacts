package com.example.a15_4_contacts

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : BaseActivity() {

    var db: AppDatabase? = null

    private lateinit var editTextLastName: EditText
    private lateinit var editTextPhoneNumber: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonDelete: Button
    private lateinit var textViewContacts: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWindowInsets(R.id.main)
        setupToolbar(R.id.toolbar, false)

        db = AppDatabase.getDatabase(this)
        readDatabase(db!!)

        editTextLastName = findViewById(R.id.editTextLastName)
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber)
        buttonSave = findViewById(R.id.buttonSave)
        buttonDelete = findViewById(R.id.buttonDelete)
        textViewContacts = findViewById(R.id.textViewContacts)
    }

    override fun onResume() {
        super.onResume()

        buttonSave.setOnClickListener {
            val contact =
                Contact(0, editTextLastName.text.toString(), editTextPhoneNumber.text.toString())
            addContact(db!!, contact)
            readDatabase(db!!)
        }

        buttonDelete.setOnClickListener {
            delete(db!!)
            textViewContacts.text = ""
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun delete(db: AppDatabase) = GlobalScope.async() {
        db.getContactDao().deleteAll()
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun addContact(db: AppDatabase, contact: Contact) = GlobalScope.async() {
        db.getContactDao().insert(contact)
    }

    @OptIn(DelicateCoroutinesApi::class)
    fun readDatabase(db: AppDatabase) = GlobalScope.async() {
        textViewContacts.text = ""
        val list = db.getContactDao().getAllContacts()
        list.forEach { i -> textViewContacts.append("${i.lastName} ${i.phoneNumber}\n") }
    }
}