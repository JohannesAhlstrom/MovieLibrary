package movieLibrary;

import java.io.Serializable;


@SuppressWarnings("javadoc")
public class Movie implements Comparable<Movie>, Serializable {
	
	/**
	 * 
	 * Enumeration holding accepted genres.
	 * In current version: Horror, Comedy, SciFi, Thriller and "None".
	 *
	 */
	public enum MovieGenre {HORROR, COMEDY, SCIFI, THRILLER, NONE};
	
	//Attributes
	private static  final long serialVersionUID = 1L;
	static int counter=1;
	private int itemId=0;
	private String title;
	private String mainActor; 
	private int productionYear=0;
	private int length=0;
	private MovieGenre type;
	
	//Constructors
/**
 * Constructor
 * Creates a movie taking 5 inputs.
 * @param title
 * @param mainActor
 * @param productionYear
 * @param length
 * @param type
 */
	//new movie from user input (Adds ItemId).
	public Movie(String title, String mainActor, int productionYear, int length, MovieGenre type){
		this(counter,title,mainActor,productionYear,length,type);
		counter++;
		}
	
	/**
	 * Constructor
	 * Creates a movie taking 4 inputs.
	 * @param title
	 * @param mainActor
	 * @param productionYear
	 * @param length
	 */
	
	//new movie from user input (Adds ItemId and type).
	public Movie(String title, String mainActor, int productionYear, int length){
		this(counter,title,mainActor,productionYear,length,MovieGenre.NONE);
		counter++;
		}
	
	/**
	 * Constructor
	 * Creates a movie taking 6 inputs.
	 * This is used when the Movie is parsed and thus already contains an item ID.
	 * @param itemId
	 * @param title
	 * @param mainActor
	 * @param productionYear
	 * @param length
	 * @param type
	 */
	//restored parsed movie from file (Already contains ItemId).
	public Movie(int itemId, String title, String mainActor, int productionYear, int length, MovieGenre type) {
		if(title == null || mainActor == null) {			
			throw new NullPointerException(String.format("String null in Constructor"));
		}
		if(productionYear < 0 || length <0) {
			throw new IllegalArgumentException(String.format("0 found in Movie constructor %d, %d",productionYear, length));
		}
		else {
		this.itemId = itemId;
		this.title = title;
		this.mainActor = mainActor;
		this.productionYear = productionYear;
		this.length = length;
		this.type = type;
		}
	}
	//Methods
	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String x) {
		this.title = x;
	}
	
	public String getMainActor() {
		return this.mainActor;
	}
	
	public void setMainActor(String x) {
		this.mainActor = x;
	}
	
	public int getLength() {
		return this.length;
	}
	
	public void setLength(int x) {
		this.length = x;
	}
	
	public int getProductionYear() {
		return this.productionYear;
	}
	
	public void setProductionyear(int x) {
		this.productionYear = x;
	}
	
	public int getItemId() {
		return this.itemId;
	}
	
	public void setItemId(int x) {
		this.itemId = x;
	}
	
	public MovieGenre getMovieGenre() {	
		return this.type;
	}
	
	public void setMovieGenre(MovieGenre x) {
		this.type = x;
	}

	@Override 
	public String toString() {
		return String.format("%d, %s, %s, %d, %d, %s",this.itemId, this.title, this.mainActor,this.productionYear, this.length, this.type);
	}

	@Override
	public int compareTo(Movie that) {
		return (this.itemId - that.itemId);
	}
}
