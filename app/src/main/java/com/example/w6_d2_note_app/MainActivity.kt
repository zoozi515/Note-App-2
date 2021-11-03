package com.example.w6_d2_note_app

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
/*Notes App - insert update delete view*/
class MainActivity : AppCompatActivity() {

    lateinit var db : DatabaseHandler

    lateinit var edmsg : EditText
    lateinit var btnsav : Button
    lateinit var rvnote : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = DatabaseHandler(this)

        edmsg = findViewById(R.id.messageEditText)
        btnsav = findViewById(R.id.saveButton)
        btnsav.setOnClickListener { postNote() }

        rvnote = findViewById(R.id.recyclerView)
        updateRV()
    }

    private fun updateRV(){
        rvnote.adapter = NoteAdapter(this, getItemsList())
        rvnote.layoutManager = LinearLayoutManager(this)
    }

    private fun getItemsList(): ArrayList<NoteModel>{
        return db.viewNotes()
    }

    private fun postNote(){
        db.addNote(NoteModel(0, edmsg.text.toString()))
        edmsg.text.clear()
        Toast.makeText(this, "Note Added", Toast.LENGTH_LONG).show()
        updateRV()
    }

    private fun editNote(noteID: Int, noteText: String){
        db.updateNote(NoteModel(noteID, noteText))
        updateRV()
    }

    fun deleteNote(noteID: Int){
        db.deleteNote(NoteModel(noteID, ""))
        updateRV()
    }

    fun raiseDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val updatedNote = EditText(this)
        updatedNote.hint = "Enter new text"
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Save", DialogInterface.OnClickListener {
                    _, _ -> editNote(id, updatedNote.text.toString())
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Update Note")
        alert.setView(updatedNote)
        alert.show()
    }

    fun deleteDialog(id: Int){
        val dialogBuilder = AlertDialog.Builder(this)
        val deletNote = TextView(this)
        deletNote.text = "Are you sure that you want to delete this note? "
        dialogBuilder
            .setCancelable(false)
            .setPositiveButton("Delete", DialogInterface.OnClickListener {
                    _, _ -> deleteNote(id)
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener {
                    dialog, _ -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle("Delte Note")
        alert.setView(deletNote)
        alert.show()
    }
}