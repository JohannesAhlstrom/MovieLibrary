package movieApp;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import movieLibrary.Configuration;
import movieLibrary.Movie;
import movieLibrary.MovieIO;
import movieLibrary.MovieLibrary;
import movieLibraryGUI.MovieLibraryGUI;
import movieLibraryGUI.MovieLibraryGUIAPP;
import movieLibrary.Movie.MovieGenre;

/**
 * @author Johannes Ahlström
 *
 */
public class MovieApp {
	/**
	 * @param args
	 */
	@SuppressWarnings({ "resource", "unused" })
	/**
	 * Allows the user to choose between a GUI or CLI version.
	 * @param args Reads the name of the configuration file if provided during runtime.
	 */
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		if(args.length>0) {
			MovieIO.readConfigFile(args[0]);
		}
		else {
			MovieIO.readConfigFile("Config.txt");
		}
		int promt;
		do {
		System.out.println("Program version choice initiated:");
		System.out.println("----------------------------------------------");
		System.out.println("Please choose version:\n\t1 GUI (Graphical User Interface) \n\t2 CLI (Command Line Interface)");
		promt = in.nextInt();
		}while(promt <1 || promt >2);
		if(promt == 1) {
			MovieLibrary lib = new MovieLibrary();
			MovieLibraryGUI GUI = new MovieLibraryGUI(lib);
			MovieLibraryGUIAPP.main(null);
		}
		else {
		MovieLibrary lib = new MovieLibrary();
		startup(lib);
		System.out.println(String.format("\nWelcome to your MovieLibrary."));
		do {
			try {
				mainMenu(lib);
			}
			catch(IllegalArgumentException e) {System.out.println(e.toString());}
			catch(InputMismatchException e) {System.out.println(e.toString());}
			catch(NullPointerException e) {System.out.println(e.toString());}
			catch (ClassCastException e) {System.out.println(e.toString());}
		}while(true);
	}
	}
	
	/**
	 * Starts the program by calling the populate library function, this provides
	 * 20 already created movies to the library to test functionality and usage. 
	 * @param lib the library in which the default library should be loaded.
	 * @return boolean true if default library was successfully loaded.
	 */
	@SuppressWarnings("unchecked")
	public static boolean startup(MovieLibrary lib) {
		boolean ok = true;
		System.out.println("Created default library.");
		MovieLibrary.populateLibrary(lib);
		System.out.println("----------------------------------------------");
		ok = true;
		return ok;
	}

/**
 * Allows for adding and removing movies, search for movies, saving and reading a library, clearing the library and quitting the application.
 * Handles user input and direction of actions in the program.
 * @param lib
 * @return boolean true if the mainMenu was run through successfully.
 */
	@SuppressWarnings("resource")
	public static boolean mainMenu(MovieLibrary lib){
		Scanner in = new Scanner(System.in);
		boolean ok = false;
		System.out.println("----------------------------------------------");
		System.out.println("Please choose an action.");
		System.out.println("----------------------------------------------");
		System.out.println(String.format("\t1 View current library\n\t2 Add movie \n\t3 Remove movie \n\t4 Search movie \n\t5 Store in file"));
		System.out.println(String.format("\t6 Read from file\n\t7 Clear library\n\t0 Quit"));
		System.out.println("----------------------------------------------");
		int input = in.nextInt();
		if(input <0 || input >7) {
			throw new IllegalArgumentException(String.format("Error:", input));
		}
		switch(input) {
		//VIEW LIBRARIES
		case 1:{
			System.out.println();
			lib.showLibraryContents();
			ok = true;
			break;
		}
		//ADD MOVIE
		case 2:{
			boolean added = false;
			Movie toBeAdded = createMovie();
			added = lib.addItem(toBeAdded);
			if(added == true) {
				System.out.println("Added to library");}
			else {
				System.out.println("Failed, could not add to library");
			}
			break;
		}
		//REMOVE MOVIE 
		case 3:{
			Set<String> keySet = lib.getMovieMap().keySet();
			Scanner in2 = new Scanner(System.in).useDelimiter("\n");
			if(lib.getMovieMap().size() <1) {
				System.out.println("Can not remove from empty library.");
				break;
			}
			System.out.println("Which Title do you want to remove?");
			for(String key:keySet) {
				System.out.println(key);
			}
			String chosenKey = in2.next();
			for(String key:keySet) {
				if(chosenKey.toLowerCase().trim().equals(key.toLowerCase())) {
					if(lib.getMovieMap().get(key).size()>1) {
						List<Movie> valueList = new ArrayList<Movie>(lib.getMovieMap().get(key));
						System.out.println("Remove which version?");

						for(Movie temp:valueList) {
							System.out.print(temp.getItemId()+" to remove: ");
							System.out.println(temp.toString());
						}
						Scanner in3 = new Scanner(System.in);
						int choice = in3.nextInt();
						for(Movie temp:valueList) {
							if(choice == temp.getItemId()) {
								lib.removeItem(temp);
							}
						}
					}
					else {
						keySet.remove(key);
						System.out.println("Removed the film");
						break;
					}
				}
			}
			break;
		}

		//SEARCH MOVIE
		case 4:{
			int searchChoice;
			Scanner in2 = new Scanner(System.in).useDelimiter("\n");
			do {
				System.out.println("Choose to present either: \n\t1 Full search\n\t2 Movies shorter than criteria");
				Scanner in3 = new Scanner(System.in);
				searchChoice = in3.nextInt();
			}while(searchChoice <1 || searchChoice >2);
			
			if(searchChoice == 1) {
			System.out.println("Free text is allowed");
			System.out.println("In order to narrow your search, please use the following keywords and syntax:");
			System.out.println("\ttitle=yoursearchcriteria\n\tmainactor=yoursearchcriteria\n\tyear=yoursearchcriteria\n\tlength=yoursearchcriteria\n\ttype=yoursearchcriteria");
			}
			else {
				System.out.println("Free text is allowed");
				System.out.println("Any number below 300 will be considered as valid times");
			}

			switch(searchChoice) {
			case 1:{
				System.out.println("Enter search criteria:");
				String criteria = in2.next().toLowerCase();
				lib.searchItem(criteria);
				break;
			}
			case 2:{
				System.out.println("Enter search criteria:");
				String criteria = in2.next().toLowerCase();
				lib.quickSearch(criteria);
				break;
			}
			default:{
				System.out.println("In searchChoice default.");
				break;
			}
			}

			ok = true;
			break;
		}

		//STORE IN FILES
		case 5:{
			lib.storeItems(Configuration.serializedmapfilename);
			lib.storeItems(Configuration.textmapfilename);
			System.out.println("Library successfully saved.");
			ok = true;
			break;
		}

		//READ FROM FILES
		case 6:{
			lib.readItems(Configuration.serializedmapfilename);
			System.out.println("Library read from serialized format.");
			ok = true;
			break;
		}
		//QUIT
		case 7:{
			lib.getMovieMap().clear();
			System.out.println("Library cleared");
			break;
		}
		case 0:{
			System.exit(1);
		}
		default:{
			System.out.println("In main default");
			ok = false;
			break;
		}
		}
		return ok;
	}


	/**
	 * Allows the user to create movies.
	 * Placed in this class because of the user input heavy nature of the functionality.
	 * Uses two scanners in order to take strings with multiple parts separated by blank-space if necessary.
	 * @throws IllegalArgumentException
	 * @return a user-defined movie.
	 */
	@SuppressWarnings("resource")
	public static Movie createMovie() {
		String movieTitle = null;
		String actorName = null;
		int productionYear;
		int length;
		boolean ok = false;
		Scanner in = new Scanner(System.in);
		Scanner in2 = new Scanner(System.in).useDelimiter("\n");
		do {
		System.out.println("Movie title:");
		movieTitle = in2.next();
		if(movieTitle.length()>Configuration.longestMovieAllowed) {
			System.out.println("Please limit the title to below 300 characters.");
		}
		}while(movieTitle.length()>Configuration.longestMovieAllowed);
		do {
		System.out.println("Main actor name: ");
		actorName = in2.next();
		if(actorName.length()>Configuration.longestMovieAllowed) {
			System.out.println("Please limit the name/names to a combined 300 characters.");
		}
		}while(actorName.length()>Configuration.longestMovieAllowed);
		do{
			System.out.println("Production year: ");
			productionYear = in.nextInt();
			if(productionYear<Configuration.firstMovieAppeared) {
				System.out.println("The oldest surviving movie is from 1888, please redo.");
			}
		}while(productionYear<Configuration.firstMovieAppeared);
		do {
		System.out.println("Length: ");
		length = in.nextInt();
		if(length > (Configuration.longestMovieAllowed+300) ) {
			System.out.println("Please keep the movie under 600 minutes (10h)");
		}
		}while(length >(Configuration.longestMovieAllowed+300));
		MovieGenre type = null;
		do {
			System.out.println("What genre is it?: ");
			for(int i = 0; i< MovieGenre.values().length; i++) {
				System.out.println(String.format("%d %s", (i+1),MovieGenre.values()[i].toString()));
			}

			int movieType = in.nextInt();
			if(movieType >MovieGenre.values().length || movieType <0) {
				throw new IllegalArgumentException(String.format("Error, illegal argument %d", movieType));
			}

			switch(movieType) {
			case 1:{
				type = MovieGenre.HORROR;
				ok = true;
				break;
			}
			case 2:{
				type = MovieGenre.COMEDY;
				ok = true;
				break;
			}
			case 3:{
				type = MovieGenre.SCIFI;
				ok = true;
				break;
			}
			case 4:{
				type = MovieGenre.THRILLER;
				ok = true;
				break;
			}
			case 5:{
				type = MovieGenre.NONE;
				ok = true;
				break;
			}
			default:{System.out.println("In Create-movie default");
			System.out.println("Please try again:");
			ok = false;
			break;
			}
			}
		}while(ok == false);
		Movie toBeCreated = new Movie(movieTitle.trim(),actorName.trim(),productionYear,length,type);
		System.out.println("Created number: ["+toBeCreated.toString()+ "]");
		return toBeCreated;
	}

}
