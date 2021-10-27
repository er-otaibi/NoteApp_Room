package com.example.noteapp_room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.note_row.view.*

class NoteAdapter (private val activity: MainActivity, private val notes: List<Notes>):  RecyclerView.Adapter<NoteAdapter.ItemViewHolder>(){
    class ItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        var list1 = notes[position]

        holder.itemView.apply {
            textView.text = list1.name
            editBtn.setOnClickListener {
                var id = list1.id
                var name = list1.name
                activity.editAlert(id,name)

            }
            deleteBtn.setOnClickListener {
                var note = list1.id
                var noteName = list1.name
                activity.deleteAlert(note,noteName)
            }
        }
    }

    override fun getItemCount() = notes.size
}