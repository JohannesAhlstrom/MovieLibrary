Welcome to the MovieLibrary
---------------------------------------------------------------------------------------------
Instructions:
Click the "MovieLibrary.sh" shell-script file.
Choose either GUI (Graphical user interface) or CLI (Command Line Interface).
For documentation, click the "Index" file in JavaDoc.
Architecture can be found in "MovieLibrary.pdf".

---------------------------------------------------------------------------------------------
MovieLibrary.
This program allows the user to create a library for their movies.

Current functionality include:
Configuration of paths/filenames for library output files, both text backup and serialized.

1 Show the contents of the library (Is initialized with 20 default movies).
2 Add movies using the Title, Main actor, Year of production, Length and Type.
3 Remove movies based on their names, or specific versions of the same movie if applicable.
4 Search for movies using either raw input, or syntaxed searches for specific
  attributes, regular search: "Peter Stormare", Syntaxed search: "Title=Peter".
5 Save the current library using serialization.
6 Read the library contents from serialized file.
7 Clear the library.
8 TimeSearch: Filters movies based on a user specified time constraint.
9 Quit application.
---------------------------------------------------------------------------------------------
Input: Configuration file
Output: Serialized Movielibrary / TextBackup of the MovieLibrary.
---------------------------------------------------------------------------------------------

Next update:
The inclusion of a logging-system.

@Author: Johannes Ahlström


