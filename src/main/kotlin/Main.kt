import controllers.NoteAPI
import models.Note
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit
import java.time.LocalDateTime

private val logger = KotlinLogging.logger {}
private val noteAPI = NoteAPI()

fun main(args: Array<String>) {
    runMenu()
}

fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        NOTE KEEPER APP         |
         > ----------------------------------
         > | NOTE MENU                      |
         > |   1) Add a note                |
         > |   2) List all notes            |
         > |   3) Update a note             |
         > |   4) Delete a note             |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}




fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            0  -> exitApp()
            else -> println("Invalid option entered: ${option}")
        }
    } while (true)
}

fun addNote(){
    //logger.info { "addNote() function invoked" }
    val noteTitle = readNextLine("Enter a title for the note: ")
    val notePriority = readNextInt("Enter a priority (1-low, 2, 3, 4, 5-high): ")
    val noteCategory = readNextLine("Enter a category for the note: ")
    val isAdded = noteAPI.add(Note(noteTitle, notePriority, noteCategory, false, LocalDateTime.now() ))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listNotes() {
    val option = ScannerInput.readNextInt("""
        > -----------------------------------
        > |        LIST NOTES MENU           |
        > -----------------------------------
        > | OPTIONS:                         |
        > |   1) List all notes              |
        > |   2) List active notes           |
        > |   3) List archived notes         |
        > |   4) Number of active notes      |
        > |   5) Number of archived notes    |
        > |   6) List notes by priority      |
        > |   7) Number of notes by priority |
        > |   8) List notes by date created  |
        > -----------------------------------
        > |   0) Back to main menu           |
        > -----------------------------------
        > ==>> """.trimMargin(">"))

    when (option) {
        1 -> println(noteAPI.listAllNotes())
        2 -> println(noteAPI.listActiveNotes())
        3 -> println(noteAPI.listArchivedNotes())
        4 -> println("Number of active notes: ${noteAPI.numberOfActiveNotes()}")
        5 -> println("Number of archived notes: ${noteAPI.numberOfArchivedNotes()}")
        6 -> {
            val priority = ScannerInput.readNextInt("Enter priority level (1-5): ")
            println(noteAPI.listNotesBySelectedPriority(priority))
        }
        7 -> {
            val priority = ScannerInput.readNextInt("Enter priority level (1-5): ")
            println("Number of notes with priority $priority: ${noteAPI.numberOfNotesByPriority(priority)}")
        }
        8 -> println(noteAPI.listNotesByDateCreated())
        0 -> return
        else -> println("Invalid option entered: $option")
    }
}



fun updateNote(){
    logger.info { "updateNote() function invoked" }

}

fun deleteNote(){
        //logger.info { "deleteNotes() function invoked" }
        listNotes()
        if (noteAPI.numberOfNotes() > 0) {
            //only ask the user to choose the note to delete if notes exist
            val indexToDelete = readNextInt("Enter the index of the note to delete: ")
            //pass the index of the note to NoteAPI for deleting and check for success.
            val noteToDelete = noteAPI.deleteNote(indexToDelete)
            if (noteToDelete != null) {
                println("Delete Successful! Deleted note: ${noteToDelete.noteTitle}")
            } else {
                println("Delete NOT Successful")
            }

    }


}

fun exitApp(){
    println("Exiting...bye")
    exit(0)
}

