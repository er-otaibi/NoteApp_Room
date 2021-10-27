package com.example.noteapp_room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Notes::class],version = 1,exportSchema = false)
abstract class NotesDatabase: RoomDatabase() {

    companion object{
        var instance:NotesDatabase?=null;
        fun getInstance(ctx: Context):NotesDatabase
        {
            if(instance!=null)
            {
                return  instance as NotesDatabase;
            }
            instance= Room.databaseBuilder(ctx,NotesDatabase::class.java,"MyNotes").run { allowMainThreadQueries() }.build();
            return instance as NotesDatabase;
        }
    }
    abstract fun NotesDao():NotesDao;
}