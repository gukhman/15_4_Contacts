package com.example.a15_4_contacts

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity : BaseActivity(), ContactAdapter.ContactClickListener {

    private lateinit var viewModel: ContactViewModel

    private lateinit var nameET: EditText
    private lateinit var phoneET: EditText
    private lateinit var saveBTN: Button
    private lateinit var delBTN: Button
    private lateinit var contactsRV: RecyclerView

    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupWindowInsets(R.id.main)
        setupToolbar(R.id.toolbar, false)

        nameET = findViewById(R.id.nameET)
        phoneET = findViewById(R.id.phoneET)
        saveBTN = findViewById(R.id.saveBTN)
        delBTN = findViewById(R.id.delBTN)
        contactsRV = findViewById(R.id.contactsRV)

        rootView = findViewById<View>(android.R.id.content)

        contactsRV.layoutManager = LinearLayoutManager(this)
        val adapter = ContactAdapter(this, this)
        contactsRV.adapter = adapter

        viewModel = ViewModelProvider(
            this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(application)
        )[ContactViewModel::class.java]
        viewModel.contacts.observe(this) { list ->
            list?.let {
                adapter.updateList(it)
            }
        }

        saveBTN.setOnClickListener {
            saveData()
        }

        delBTN.setOnClickListener {
            viewModel.deleteAllContacts()
        }
    }

    override fun onItemClicked(contact: Contact) {
        viewModel.deleteContact(contact)
        Snackbar.make(rootView, "Контакт ${contact.name} удален", Snackbar.LENGTH_LONG).show()
    }

    fun saveData() {
        val contactName = nameET.text.toString()
        val contactPhone = phoneET.text.toString()
        if (contactName.length > 1 && isValidPhoneNumber(contactPhone)) {
            viewModel.insertContact(Contact(0, contactName, contactPhone))
            Snackbar.make(rootView, "Контакт $contactName сохранен", Snackbar.LENGTH_LONG).show()

            nameET.text.clear()
            phoneET.text.clear()
        } else Snackbar.make(rootView, "Заполните поля корректно. Номер телефона должен начинаться с +7 или 8 и далее идет 10 цифр", Snackbar.LENGTH_LONG).show()
    }

    fun isValidPhoneNumber(phoneNumber: String): Boolean {
        val phoneRegex = Regex("^(\\+7|8)\\d{10}$")
        return phoneRegex.matches(phoneNumber)
    }
}
