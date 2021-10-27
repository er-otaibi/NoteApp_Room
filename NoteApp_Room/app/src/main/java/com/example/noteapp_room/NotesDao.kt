package com.example.noteapp_room

import androidx.room.*


@Dao
interface NotesDao{

    @Query("SELECT * FROM Notes")
    fun getAllMyNotes(): List<Notes>

    @Insert
    fun insertNote(notes: Notes)

    @Query("DELETE FROM Notes where id=:pk")
    fun delete(pk: Int)

    @Query("UPDATE Notes SET Note=:note where id=:pk")
    fun update(note: String,pk: Int)

    @Delete
    fun deleteOBJ(note: Notes)

    @Update
    fun updateOBJ(note: Notes)

}