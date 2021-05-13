package movieLibraryGUI;

import java.util.NoSuchElementException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import movieLibrary.MovieLibrary;

@SuppressWarnings("javadoc")
public class MovieLibraryGUIAPP {

	/**
	 * Handles the GUI application.
	 * Sticks the MovieLibrary to the GUI.
	 * creates JFrame and adds the GUI to it.
	 * @param args
	 */
	public static void main(String[] args) {
		Runnable guiRun = new Runnable(){
		public void run(){
			try {
			MovieLibrary model = new MovieLibrary();
			JFrame jf = new MovieLibraryGUI(model);
			jf.setTitle("Johannes MovieLibrary GUI");
			jf.pack();
			jf.setVisible(true);
		}catch(NullPointerException e) {System.out.println("NullpointerCatch in GUI app" + e.toString());}
		catch(NoSuchElementException e) {System.out.println("No such element in GUI APP" +e.toString());}
		}
	};
	SwingUtilities.invokeLater(guiRun);
}
}
