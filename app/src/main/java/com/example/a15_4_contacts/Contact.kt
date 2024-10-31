package com.example.a15_4_contacts

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contacts")
data class Contact(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "lastName") val lastName: String,
    @ColumnInfo(name = "phoneNumber") val phoneNumber: String
)