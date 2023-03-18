package models

import java.time.LocalDateTime

data class Note(
    val noteTitle: String,
    val notePriority: Int,
    val noteCategory: String,
    val isNoteArchived:Boolean,
    val dateCreated: LocalDateTime?
) {
    override fun toString(): String {
        val status = if (isNoteArchived) "Archived" else "Active"
        return "Title: $noteTitle\n" +
                "Priority: $notePriority\n" +
                "Category: $noteCategory\n" +
                "Status: $status\n"
    }
}
