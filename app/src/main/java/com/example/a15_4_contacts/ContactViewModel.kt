package com.example.a15_4_contacts

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application: Application): AndroidViewModel(application) {
    private val repository: ContactRepository
    val contacts: LiveData<List<Contact>>

    init {
        val dao = AppDatabase.getDatabase(application).getContactDao()
        repository = ContactRepository(dao)
        contacts = repository.contacts
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contact)
    }

    fun insertContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }

    fun deleteAllContacts() = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }
}