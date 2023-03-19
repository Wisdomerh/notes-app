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
        return activeNotes.takeIf { it.isNotEmpty() }
            ?.joinToString(separator = "\n") { "${activeNotes.indexOf(it)}: $it" }
            ?: "No active notes stored"
    }


    fun listArchivedNotes(): String {
        val archivedNotes = notes.filter { it.isNoteArchived }
        return if (archivedNotes.isEmpty()) {
            "No archived notes stored"
        } else {
            archivedNotes.mapIndexed { index, note -> "$index: $note" }
                .joinToString("\n")
        }
    }


    fun numberOfArchivedNotes(): Int {
        return notes.count { it.isNoteArchived }
    }



    fun numberOfActiveNotes(): Int {
        return notes.count { !it.isNoteArchived }
    }




    fun listNotesBySelectedPriority(priority: Int): String {
        val notesWithPriority = notes.filter { it.notePriority == priority }

        return if (notesWithPriority.isEmpty()) {
            "No notes with priority $priority stored"
        } else {
            val listOfNotes = notesWithPriority.joinToString(separator = "\n") { "${notes.indexOf(it)}: $it" }
            "${numberOfNotesByPriority(priority)} notes with priority $priority: $listOfNotes"
        }
    }

    fun numberOfNotesByPriority(priority: Int): Int {
        return notes.count { it.notePriority == priority }
    }



    fun listNotesByDateCreated(): String {
        val sortedNotes = notes.sortedBy { it.dateCreated }
        return if (sortedNotes.isEmpty()) {
            "No notes stored"
        } else {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            sortedNotes.mapIndexed { i, note ->
                val formattedDate = note.dateCreated?.format(formatter)
                "$i: $note Created on $formattedDate \n"
            }.joinToString(separator = "")
        }
    }

    fun deleteNote(indexToDelete: Int): Note? {
        return if (isValidListIndex(indexToDelete, notes)) {
            notes.removeAt(indexToDelete)
        } else null
    }
    fun updateNote(indexToUpdate: Int, note: Note?): Boolean {

        val foundNote = findNote(indexToUpdate)


        if ((foundNote != null) && (note != null)) {
            foundNote.noteTitle = note.noteTitle
            foundNote.notePriority = note.notePriority
            foundNote.noteCategory = note.noteCategory
            return true
        }


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

    fun archiveNote(index: Int): Boolean = findNote(index)?.run {
        isNoteArchived = true
        true
    } ?: false

    fun searchByTitle(searchString : String) =
        notes.filter { note -> note.noteTitle.contains(searchString, ignoreCase = true)}
            .joinToString (separator = "\n") {
                    note ->  notes.indexOf(note).toString() + ": " + note.toString() }



}
