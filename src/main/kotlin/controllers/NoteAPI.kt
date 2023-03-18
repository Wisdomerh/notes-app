package controllers

import models.Note

class NoteAPI {
    private var notes = ArrayList<Note>()
    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String {
        return if (notes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            for (i in notes.indices) {
                listOfNotes += "${i}: ${notes[i]} \n"
            }
            listOfNotes
        }
    }
    fun numberOfNotes(): Int {
        return notes.size
    }

    fun findNote(index: Int): Note? {
        return if (isValidListIndex(index, notes)) {
            notes[index]
        } else null
    }

    //utility method to determine if an index is valid in a list.
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun listActiveNotes(): String {
        val activeNotes = notes.filter { !it.isNoteArchived }
        return if (activeNotes.isEmpty()) {
            "No active notes stored"
        } else {
            var listOfNotes = ""
            for (i in activeNotes.indices) {
                listOfNotes += "${i}: ${activeNotes[i]} \n"
            }
            listOfNotes
        }
    }

    fun listArchivedNotes(): String {
        val archivedNotes = notes.filter { it.isNoteArchived }
        return if (archivedNotes.isEmpty()) {
            "No archived notes stored"
        } else {
            var listOfNotes = ""
            for (i in archivedNotes.indices) {
                listOfNotes += "${i}: ${archivedNotes[i]} \n"
            }
            listOfNotes
        }
    }

    fun numberOfArchivedNotes(): Int {
        return notes.count { it.isNoteArchived }
    }

    fun numberOfActiveNotes(): Int {
        return notes.count { !it.isNoteArchived }
    }


}

