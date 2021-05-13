package movieLibrary;

import java.util.Collections;
import java.util.List;
/**
 * 
 * @author Kjell H Carlsson
 *This is the interface blueprint around which the rest of the application is constructed.
 * @param <T>
 */
@SuppressWarnings("javadoc")
public interface Library<T> {
	boolean addItem(T item);
	boolean removeItem(T item);
	int getNoOfItems(String filename);
	void showLibraryContents();
	void storeItems(String filename);
	void readItems(String filename);
	default List<T> searchItem(String searchPattern){return Collections.emptyList();}
	default T getItem(int itemId) {return null;}
}
