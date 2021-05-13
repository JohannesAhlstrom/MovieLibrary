package movieLibrary;

import java.util.*;

@SuppressWarnings({ "rawtypes", "javadoc" })
public class MovieLibrary implements Library {

	//Attributes
	private Map<String,List<Movie>> movieMap = null;

	//Constructor
	public MovieLibrary() {
		this.movieMap = new HashMap<String,List<Movie>>();
	}

	//Methods

	public void setMovieMap(Map<String,List<Movie>> insert) {
		this.movieMap = insert;
	}

	public Map<String,List<Movie>> getMovieMap(){
		return this.movieMap;
	}

	
	/**
	 * 	
	 * Allows for free-text search of the contents of any movie, ALSO allows for specific searches according to syntax
	 * Example:   title=Golden Eye
	 * Example:   length=109   
	 * @return resultList, a list of movies answering to the description of the search parameter.
	 * @param searchPattern
	 */
	public List<Movie> searchItem(String searchPattern){
		List<Movie> resultList = new ArrayList<>();
		int parsedInt =0;
		int secondParsedInt = 0;
		boolean allDigits = true;
		boolean equalFound = false;
		for(Character c:searchPattern.trim().toCharArray()) {
			if(Character.isAlphabetic(c)) {
				allDigits = false;
			}
			if(c.equals('=')) {
				equalFound = true;
			}
		}

		if(allDigits == true) {
			parsedInt = Integer.parseInt(searchPattern.trim());
		}

		if(equalFound == true) {
			String[] parts = searchPattern.toLowerCase().split("=");

			boolean allDigitsInSearchPattern = true;
			for(Character c:parts[1].trim().toCharArray()) {
				if(Character.isAlphabetic(c)) {
					allDigitsInSearchPattern = false;
				}
			}
			if(allDigitsInSearchPattern == true) {
				secondParsedInt = Integer.parseInt(parts[1].toString().trim());
			}

			Iterator<List<Movie>> iter = movieMap.values().iterator();
			switch(parts[0].trim().toLowerCase()) {
			case "title":{
				while(iter.hasNext()) {
					List<Movie> temp = iter.next();
					Iterator<Movie> iter2 = temp.iterator();
					while(iter2.hasNext()) {
						Movie tempMovie = iter2.next();
						if(tempMovie.getTitle().toLowerCase().contains(parts[1].trim())) {
							if(!(resultList.contains(tempMovie))) {
								resultList.add(tempMovie);
							}
						}
					}

				}
				break;
			}

			case "mainactor":{
				while(iter.hasNext()) {
					List<Movie> temp = iter.next();
					Iterator<Movie> iter2 = temp.iterator();
					while(iter2.hasNext()) {
						Movie tempMovie = iter2.next();
						if(tempMovie.getMainActor().toLowerCase().contains(parts[1].trim())) {
							if(!(resultList.contains(tempMovie))) {
								resultList.add(tempMovie);
							}
						}
					}

				}
				break;
			}

			case "year":{
				while(iter.hasNext()) {
					List<Movie> temp = iter.next();
					Iterator<Movie> iter2 = temp.iterator();
					while(iter2.hasNext()) {
						Movie tempMovie = iter2.next();
						if(tempMovie.getProductionYear()==secondParsedInt) {
							if(!(resultList.contains(tempMovie))) {
								resultList.add(tempMovie);
							}
						}
					}
				}
				break;
			}

			case "length":{
				while(iter.hasNext()) {
					List<Movie> temp = iter.next();
					Iterator<Movie> iter2 = temp.iterator();
					while(iter2.hasNext()) {
						Movie tempMovie = iter2.next();
						if(tempMovie.getLength()==secondParsedInt) {
							if(!(resultList.contains(tempMovie))) {
								resultList.add(tempMovie);
							}
						}
					}

				}
				break;
			}

			case "type":{
				while(iter.hasNext()) {
					List<Movie> temp = iter.next();
					Iterator<Movie> iter2 = temp.iterator();
					while(iter2.hasNext()) {
						Movie tempMovie = iter2.next();
						if(tempMovie.getMovieGenre().toString().toLowerCase().contains(parts[1].trim())) {
							if(!(resultList.contains(tempMovie))) {
								resultList.add(tempMovie);
							}
						}
					}

				}
				break;
			}

			default:{ System.out.println("In default, could not match before equal-sign in search criteria");
			break;
			}
			}
		}

		for(List<Movie> temp:this.movieMap.values()) {
			Iterator<Movie> iter = temp.iterator();
			while(iter.hasNext()) {
				Movie movieTemp = iter.next();
				if((movieTemp.toString().toLowerCase().contains(searchPattern.trim()))) {
					if(!(resultList.contains(movieTemp))) {
						resultList.add(movieTemp);
					}
				}
			}
		}
		if(resultList.size() <1) {
			System.out.println("No movie matching the criteria found in the library");
			return resultList;
		}
		else {
			System.out.println("Found the following matches:");
			for(Movie result:resultList) {
				System.out.println(result.toString());
				if(result.getLength()<parsedInt && parsedInt <=Configuration.longestMovieAllowed) {
					System.out.println("\t[Included since movie length: "+result.getLength()+" < critera: "+parsedInt+"]\n");
				}
			}
			return resultList;
		}
	}


	/**
	 * Adds the possibility to search for items AND show which movies are shorter than the searchPattern
	 * @param searchPattern
	 * @return list of movies.
	 */

	public List<Movie> quickSearch(String searchPattern){

		List<Movie> resultList = new ArrayList<>();
		int parsedInt =0;
		boolean allDigits = true;
		for(Character c:searchPattern.trim().toCharArray()) {
			if(Character.isAlphabetic(c)) {
				allDigits = false;
			}
			if(c.equals('=')) {
			}
		}

		if(allDigits == true) {
			parsedInt = Integer.parseInt(searchPattern.trim());
		}
		for(List<Movie> temp:this.movieMap.values()) {
			Iterator<Movie> iter = temp.iterator();
			while(iter.hasNext()) {
				Movie movieTemp = iter.next();
				if((movieTemp.toString().toLowerCase().contains(searchPattern.trim())||movieTemp.getLength()<=parsedInt)) {
					if(!(resultList.contains(movieTemp))) {
						resultList.add(movieTemp);
					}
				}
			}
		}
		if(resultList.size() <1) {
			System.out.println("No movie matching the criteria found in the library");
			return resultList;
		}
		else {
			System.out.println("Found the following matches:");
			for(Movie result:resultList) {
				System.out.println(result.toString());
				if(result.getLength()<=parsedInt) {
					System.out.println("\t[Included since movie length: "+result.getLength()+" < critera: "+parsedInt+"]\n");
				}
			}
			return resultList;
		}
	}



	/**
	 * Adds objects to the Library hashMap if the item is a Movie.
	 * @param item
	 * @return boolean true if found in Map, false if  not found and thus not added.
	 */
	public boolean addItem(Object item) {
		if(item instanceof Movie) {
			String key = ((Movie) item).getTitle();
			if(movieMap.keySet().contains(key)) {
				movieMap.get(key).add((Movie)item);
			}
			else {
				List<Movie> movieList = new ArrayList<>();
				movieList.add((Movie)item);
				movieMap.putIfAbsent(key, movieList);
			}
		}
		if(movieMap.containsKey(((Movie)item).getTitle())) {
			return true;
		}
		else {
			System.out.println("Could not add the Movie to the library");
			return false;
		}
	}

	/**
	 * Removes the specific object form the map, using the itemId as key.
	 * @param item
	 * @return boolean true if successful, false with explanation if object is not a movie or object could not be removed. 
	 */
	public boolean removeItem(Object item) {
		if(item instanceof Movie) {
			Set<String> keyset = movieMap.keySet();
			for(String key:keyset) {
				if(movieMap.get(key).contains(item))
					movieMap.get(key).remove(item);
			}
			for(String key:keyset) {
				if(movieMap.get(key).contains(item)) {
					System.out.println("Could not remove the movie");
					return false;
				}
				else {
					System.out.println("The movie was removed");
					return true;
				}
			}
		}
		return true;
	}


	/**
	 * Counts the number of items in the library by calling an IO and counting.
	 * @param filename
	 * @return number of items found in a specified filename.
	 */
	@Override
	public int getNoOfItems(String filename) {
		Map<String, List<Movie>> countMap = new HashMap<>();
		countMap = MovieIO.readSerializedMapData(filename);
		return countMap.size();
	}


	/**
	 * Shows contents in library, printed to console.
	 * Versions of movies are shown one tab below their title
	 */
	@Override
	public void showLibraryContents() {
		if(movieMap.size()<1) {
			System.out.println("The library is empty.");
		}
		else {
			Set<String> keyring = movieMap.keySet();
			System.out.println("\t\tLIBRARY CONTENTS:");
			for(String key:keyring) {
				System.out.println("["+key.toString()+"]"+" contains:");
				List<Movie> tempList = movieMap.get(key);
				for(Movie temp:tempList) {
					System.out.println("\t"+temp.toString());
				}
				System.out.println("---");
			}
			System.out.println("Individual titles found in the library: "+ movieMap.size());
		}
	}

	/**
	 * Stores movies depending on the ending of the filename. Text or serialized.
	 */
	@Override
	public void storeItems(String filename) {
		if(filename.contains(".ser")) {
			MovieIO.storeSearializedMapData(this.movieMap, filename);
		}
		else {
			MovieIO.storeMapMovies(movieMap, filename);
		}
	}

	/**
	 * Reads and stores items from a serialized file.
	 * @param filename
	 */
	@Override
	public void readItems(String filename) {
		Map<String, List<Movie>> storedMap = new HashMap<>();
		if(filename.contains(".ser")) {
			storedMap = MovieIO.readSerializedMapData(filename);	
		}
		this.movieMap = storedMap;
	}

	/**
	 * @author Kjell H Carlsson
	 * This method sets up a test library
	 * @param lib a reference to the Library implementation to use in application
	 */
	public static void populateLibrary(Library<Movie> lib) {

		Movie[] myMovies = new Movie[20];

		myMovies[0] = new Movie("Dr No", "Sean Connory", 1962, 109);
		myMovies[1] = new Movie("From Russia with Love", "Sean Connory", 1963, 115);
		myMovies[2] = new Movie("Goldfinger", "Sean Connory", 1964, 110);
		myMovies[3] = new Movie("Thunderball", "Sean Connory", 1965, 130);
		myMovies[4] = new Movie("Live and Let Die", "Roger Moore", 1973, 121);
		myMovies[5] = new Movie("Moonraker", "Roger Moore", 1979, 126);
		myMovies[6] = new Movie("Octopussy", "Roger Moore", 1983, 131);
		myMovies[7] = new Movie("Golden Eye", "Pierce Brosnan", 1995, 130);	
		myMovies[8] = new Movie("Tomorrow Never Dies", "Pierce Brosnan", 1997, 119);
		myMovies[9] = new Movie("Casino Royale", "Daniel Craig", 2006, 144);		
		myMovies[10] = new Movie("Skyfall", "Daniel Craig", 2012, 143);
		myMovies[11] = new Movie("Spectre", "Daniel Craig", 2015, 148);	

		myMovies[12] = new Movie("King Kong", "Jeff Bridges", 1976, 134);
		myMovies[13] = new Movie("King Kong", "Naomi Watts", 2005, 180);
		myMovies[14] = new Movie("King Kong", "Fay Wray", 1933, 105);

		myMovies[15] = new Movie("Das Boot", "Jürgen Prochnow", 1981, 145);
		myMovies[16] = new Movie("Das Boot", "Jürgen Prochnow", 1981, 199);

		myMovies[17] = new Movie("The Good, The Bad, The Ugly", "Clint Eastwood", 1966, 171);
		myMovies[18] = new Movie("For a Fistful of Dollars", "Clint Eastwood", 1964, 96);
		myMovies[19] = new Movie("For a Few Dollars More", "Clint Eastwood", 1965, 126);

		for(Movie m: myMovies){
			lib.addItem(m);
		}
	}
}