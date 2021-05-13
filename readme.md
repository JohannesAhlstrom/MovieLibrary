Welcome to the MovieLibrary
----------------------------
Instructions:
Download the repository
Click the "MovieLibrary.sh" shell-script file in the "deploy" folder.
Choose either GUI (Graphical user interface) or CLI (Command Line Interface).
For documentation, click the "Index" file in JavaDoc.
Architecture can be found in "MovieLibrary.pdf"

----------------------------
MovieLibrary.
This program allows the user to create a library for their movies.

Current functionality include:
Configuration of paths or filenames for library output files, both text backup and serialized.

Show the contents of the library (Is initialized with 20 default movies).

Add movies using the Title, Main actor, Year of production, Length and Type.

Remove movies based on their names, or specific versions of the same movie if applicable.

Search for movies using either raw input, or syntaxed searches for specific
attributes, regular search: "Peter Stormare", Syntaxed search: "Title=Peter".

Save the current library using serialization.

Read the library contents from serialized file.

Clear the library.

TimeSearch: Filters movies based on a user specified time constraint.

Quit application.

----------------------------
Input: Configuration file
Output: Serialized Movielibrary / TextBackup of the MovieLibrary.

----------------------------

Next update:
The inclusion of a logging-system.

@Author: Johannes Ahlström


