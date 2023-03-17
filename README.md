##Kotlin Notes App 
*Overview
This is a console-based Kotlin Notes App that provides basic CRUD (Create, Read, Update, Delete) functionalities for managing notes. This app is built on top of Kotlin programming language and provides a menu-driven user interface.

##Features
*Add Note: Add a new note to the app.
*List Notes: List all notes available in the app.
*Update Note: Update an existing note in the app.
*Delete Note: Delete a note from the app.
The note model has not been implemented yet, and this app only provides the menu structure. However, you can extend the app by implementing the Note model to store and manage notes in the app.

##Dependencies
The following dependencies are used in this app:

*MicroUtils Kotlin-Logging: Provides logging capabilities for the app.
*ScannerInput: A utility for providing robust user input/output functionality.
Usage
To use this app, simply run the main() function in the NotesApp.kt file. The app will start running and prompt you with a menu that you can use to manage your notes.

##Contributing
Contributions to this app are welcome. If you find a bug or want to suggest a new feature, please create an issue in the repository. If you want to contribute code, please fork the repository and create a pull request.
OpenNoteScanner
Build Status

This little application provides a way on scanning handwritten notes and printed documents.

It automatically detect the edge of the paper over a contrastant surface.

When using the printed special page template it automatically detects the QR Code printed on the bottom right corner and scans the page immediately.

After the page is detected, it compensates any perspective from the image adjusting it to a 90 degree top view and saves it on a folder on the device.

It is also possible to launch the application from any other application that asks for a picture, just make sure that there is no default application associated with this action.

Screenshots