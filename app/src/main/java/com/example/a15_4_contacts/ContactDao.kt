package com.example.a15_4_contacts

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts ORDER BY id ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("DELETE FROM contacts")
    suspend fun deleteAll()
}
