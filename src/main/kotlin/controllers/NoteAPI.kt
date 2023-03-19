package controllers

import models.Note
import persistence.Serializer
import java.time.format.DateTimeFormatter

class NoteAPI(serializerType: Serializer){

    private var serializer: Serializer = serializerType

    private var notes = ArrayList<Note>()

    fun add(note: Note): Boolean {
        return notes.add(note)
    }

    fun listAllNotes(): String =
        if  (notes.isEmpty()) "No notes stored"
        else notes.joinToString (separator = "\n") { note ->
            notes.indexOf(note).toString() + ": " + note.toString() }


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
        var counter = 0
        for (note in notes) {
            if (note.isNoteArchived) {
                counter++
            }
        }
        return counter
    }


    fun numberOfActiveNotes(): Int {
        return notes.stream()
            .filter{note: Note -> !note.isNoteArchived}
            .count()
            .toInt()
    }



    fun listNotesBySelectedPriority(priority: Int): String {

            return if (notes.isEmpty()) {
                "No notes stored"
            } else {
                var listOfNotes = ""
                for (i in notes.indices) {
                    if (notes[i].notePriority == priority) {
                        listOfNotes +=
                            """$i: ${notes[i]}
                        """.trimIndent()
                    }
                }
                if (listOfNotes.equals("")) {
                    "No notes with priority $priority stored"
                } else {
                    "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
                }
            }
    }

    fun numberOfNotesByPriority(priority: Int): Int {
        var counter = 0
        for (note in notes) {
            if (note.notePriority == priority) {
                counter++
            }
        }
        return counter
    }


    fun listNotesByDateCreated(): String {
        val sortedNotes = notes.sortedBy { it.dateCreated }
        return if (sortedNotes.isEmpty()) {
            "No notes stored"
        } else {
            var listOfNotes = ""
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            for (i in sortedNotes.indices) {
                val note = sortedNotes[i]
                val formattedDate = note.dateCreated?.format(formatter)
                listOfNotes += "${i}:$note Created on $formattedDate \n"
            }
            listOfNotes
        }
    }
    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }
    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {
        //find the note object by the index number
        val foundNote = findNote(indexToUpdate)

        //if the note exists, use the note details passed as parameters to update the found note in the ArrayList.
        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }

        //if the note was not found, return false, indicating that the update was not successful
        return false
    }
    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, notes);
    }
    @Throws(Exception::class)
    fun load() {
        notes = serializer.read() as ArrayList<Note>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(notes)
    }

    fun archiveNote(index: Int): Boolean {
        val note = findNote(index)
        return if (note != null) {
            note.isNoteArchived = true
            true
        } else {
            false
        }
    }

}
