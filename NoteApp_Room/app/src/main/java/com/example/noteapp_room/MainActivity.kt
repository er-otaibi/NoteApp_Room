package com.example.noteapp_room

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    lateinit var rvMain: RecyclerView
    lateinit var etnote: EditText
    lateinit var addBtn: Button
    var lv = arrayListOf<Notes>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvMain = findViewById(R.id.rvMain)
        etnote = findViewById(R.id.etnote)
        addBtn = findViewById(R.id.addBtn)

        NotesDatabase.getInstance(applicationContext)

        updateRV()

        addBtn.setOnClickListener {
            val note = etnote.text.toString()
            val noteOBJ = Notes(0, note)
            CoroutineScope(Dispatchers.IO).launch {
                NotesDatabase.getInstance(applicationContext).NotesDao().insertNote(noteOBJ)
                withContext(Main) {
                    update(NotesDatabase.getInstance(applicationContext).NotesDao().getAllMyNotes())
                }
            }
            etnote.setText("")
        }

    }

     private fun update(list: List<Notes>){
        rvMain.adapter = NoteAdapter(this,list)
        rvMain.layoutManager = LinearLayoutManager(this)
    }

    fun updateRV(){

        CoroutineScope(Dispatchers.IO).launch {

            var list = NotesDatabase.getInstance(applicationContext).NotesDao().getAllMyNotes()
            update(list)

        }
    }

    fun editAlert( idNote: Int, name: String){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)
        val input = EditText(this)
        var newNote = ""
        dialogBuilder.setMessage("")
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ ->  newNote = input.text.toString()
                Toast.makeText(applicationContext, "$idNote", Toast.LENGTH_SHORT)
                    .show()
                CoroutineScope(Dispatchers.IO).launch {
                    NotesDatabase.getInstance(applicationContext).NotesDao().updateOBJ(Notes(idNote,newNote))
                    withContext(Main) {
                        update(NotesDatabase.getInstance(applicationContext).NotesDao().getAllMyNotes())
                    }
                }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()

        alert.setTitle("Edit Alert")
        alert.setView(input)
        alert.show()


    }

    fun deleteAlert( idNote: Int , name: String){
        val dialogBuilder = androidx.appcompat.app.AlertDialog.Builder(this)


        dialogBuilder.setMessage("Confirm delete ?")
            .setPositiveButton("Delete", DialogInterface.OnClickListener {
                    dialog, id ->

                CoroutineScope(Dispatchers.IO).launch { NotesDatabase.getInstance(applicationContext).NotesDao()
                    .deleteOBJ(Notes(idNote,name))
                    withContext(Main) {
                        update(NotesDatabase.getInstance(applicationContext).NotesDao().getAllMyNotes())
                    }
            }
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()

        alert.setTitle("Delete Alert")
        alert.show()


    }
}