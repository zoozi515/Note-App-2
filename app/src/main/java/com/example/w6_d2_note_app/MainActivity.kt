package com.example.w6_d2_note_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

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
}