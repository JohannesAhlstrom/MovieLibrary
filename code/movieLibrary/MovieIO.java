package movieLibrary;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import movieLibrary.Movie.MovieGenre;

@SuppressWarnings("javadoc")
public class MovieIO {

/**
 * Stores the movies currently in the library as a text file.
 * @param movieMap
 * @param filename
 */
	public static void storeMapMovies(Map<String,List<Movie>> movieMap, String filename) {
		try(PrintWriter pw = new PrintWriter(new FileWriter(filename))){
			for(List<Movie> temp:movieMap.values()) {
				for(Movie movieTemp:temp)
				pw.println(movieTemp);
			}
		}
		catch (IOException ioe) {System.out.println("Error in storeMovies" +ioe);
		}
	}
	
	
/**
 * Reads movies from text-based files, then parses.
 * @param filename
 * @return Map containing the library, based on the moviename as Keys.
 */
	public static Map<String,List<Movie>> readMapMovies(String filename){
		Map<Integer,Movie> movieMap = new HashMap<Integer,Movie>();
		try(BufferedReader br = new BufferedReader(new FileReader(filename))) {
			while(br.ready()) {
				String temp = br.readLine();
				Movie parsedMovie = parseMovie(temp);
				movieMap.putIfAbsent(parsedMovie.getItemId(), parsedMovie);
			}
		}
		catch(IOException ioe) {System.out.println("Error in readMovies" +ioe);}
		catch(NullPointerException e) {System.out.println("Error in MovieIO"+ e);
		}
			TreeSet<Integer> keys = new TreeSet<>(movieMap.keySet());
			Movie.counter = keys.last()+1;
			
			
		Map<String,List<Movie>> returnMap = new HashMap<>();
		List<Movie> movieList = new ArrayList<>();
		for(Movie temp: movieMap.values()) {
			String key = temp.getTitle();
			if(returnMap.containsKey(key)) {
				returnMap.get(key).add(temp);
			}
			else {
				movieList.add(temp);
				returnMap.putIfAbsent(key, movieList);
			}
		}
		return returnMap;
	}
	
	/**
	 * Parses a string into Movies as defined by this program.
	 * @param toBeParsed a String.
	 * @return An individual parsed movie converted from raw-string.
	 */
	public static Movie parseMovie(String toBeParsed) {
		String[] parts = toBeParsed.split(",");
		int itemId =Integer.parseInt(parts[0].trim());
		String title= parts[1].trim();
		String mainActor =parts[2].trim();
		int productionYear=Integer.parseInt(parts[3].trim());
		int length= Integer.parseInt(parts[4].trim());
		MovieGenre type = null;
		switch(parts[5].trim()) {
		case "HORROR":{
			type= Movie.MovieGenre.HORROR;
			break;
		}

		case "COMEDY":{
			type= Movie.MovieGenre.COMEDY;
			break;
		}

		case "SCIFI":{
			type= Movie.MovieGenre.SCIFI;
			break;
		}

		case "THRILLER":{
			type= Movie.MovieGenre.THRILLER;
			break;
		}

		case "NONE":{
			type= Movie.MovieGenre.NONE;
			break;
		}
		default:{System.out.println("In default parts[5] switch");}
		break;
		}
		
		Movie parsed = new Movie(itemId,title, mainActor,productionYear,length,type);
		return parsed;
	}

/**
 * Stores the data from the Map in a serialized form.
 * @param toBeStored
 * @param filename
 */
	public static void storeSearializedMapData(Map<String,List<Movie>> toBeStored, String filename) {
		try(ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)))){
			oos.writeObject(toBeStored);
		}
		catch(IOException ioe) {System.out.println("Error in storeSearlizedData"+ioe.toString());}
	}

	
	/**
	 * Reads a file containing serialized movies, transforming them into a Library.
	 * 
	 * @param filename
	 * @return Map containing the library, based on the movie name as Keys.
	 */
	@SuppressWarnings("unchecked")
	public static Map<String,List<Movie>> readSerializedMapData(String filename){
		Map<String,List<Movie>> movieMap = null;
		try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))){
		movieMap = (Map<String,List<Movie>>) ois.readObject();
		
		TreeSet<Integer> keys = new TreeSet<>();
		for(List<Movie> temp:movieMap.values()) {
			for(Movie movieTemp:temp) {
				keys.add(movieTemp.getItemId());
			}
		}
		Movie.counter = keys.last()+1;
	}
		catch(ClassNotFoundException ioe){System.out.println("Error in Serialized reader"+ioe.toString());}
		catch(IOException ioe) {System.out.println("Error in Serialized reader"+ioe.toString());}
		return movieMap;
	}
	

/**
 * 	Reads text based configuration-file called / found at the filename path.
	sends to parsing.
 * @param filename
 */
	public static void readConfigFile(String filename) {
		System.out.println("----------------------------------------------");
		System.out.println("Configuration started.");
		System.out.println("----------------------------------------------");
		System.out.println("Filenames are changed using Config.txt");
		System.out.println(String.format("Original filenames: \n\t%s \n\t%s",  Configuration.textmapfilename.toString(), Configuration.serializedmapfilename.toString()));
		
	
		String readString = null;
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			while(br.ready()) {
				readString = br.readLine();
				parseConfigData(readString);
			}
		}
		catch(IOException ioe) {System.out.println("IOE exception in ReadConfigFile");}
		System.out.println(String.format("\nNew filenames found and put: \n\t%s \n\t%s", Configuration.textmapfilename.toString(), Configuration.serializedmapfilename.toString()));
		System.out.println("----------------------------------------------");
	}

/**
 * Parses configuration data and configures system.
 * @param toBeParsed String from the configuration file
 */
	public static void parseConfigData(String toBeParsed) {
		String[] parts = toBeParsed.toLowerCase().split("=");
		switch(parts[0]) {
		case"textmapfilename":{
			Configuration.textmapfilename = parts[1];
			break;
		}
		case "serializedmapfilename":{
			Configuration.serializedmapfilename = parts[1];
			break;
		}
		default:{ 
			System.out.println("In Config parse Default.");
			break;
		}
		}
	}
}
