package movieLibraryGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.swing.*;

import movieLibrary.Movie;
import movieLibrary.Movie.MovieGenre;
import movieLibrary.MovieLibrary;

@SuppressWarnings("javadoc")
public class MovieLibraryGUI extends JFrame {

	public static final long serialVersionUID = 1L;

	//GUI containers and components

	//_MainPanel__________//
	private JPanel mainPanel = new JPanel();

	//_HeaderPanel__________//
	private JPanel headerPanel = new JPanel();
	private JLabel headLine = new JLabel("MovieLibrary");

	//_ControlPanel__________//
	private JPanel controlPanel = new JPanel();
	private JButton[] buttons = new JButton[9];
	private String[] buttonInfo = {"Show","Add","Remove","Search","Store","Read","Clear","TimeSearch","Quit"};

	//__InputFieldPanel_________//
	private JPanel inputFieldPanel = new JPanel();
	private JLabel titleJL = new JLabel("Enter title:");
	private JLabel mainActorJL = new JLabel("Enter main actor");
	private JLabel productionYearJL = new JLabel("Enter production year");
	private JLabel lengthJL = new JLabel("Enter length");
	private JLabel typeJL = new JLabel("Enter type:");
	private JPanel consoleSplitPanel = new JPanel();


	private JTextField titleJTF = new JTextField();
	private JTextField mainActorJTF = new JTextField();
	private JTextField productionYearJTF = new JTextField();
	private JTextField lengthJTF = new JTextField();
	private JTextField typeJTF = new JTextField();

	//__OutputFieldPanel_________//

	Console console = new Console();
	private JPanel outputFieldPanel = new JPanel();
	private JLabel consoleJL = new JLabel("Console");
	private JLabel consoleJL1 = new JLabel("Console in");
	JTextField consoleIn = new JTextField("");
	JTextField consoleIn2 = new JTextField("");

	//GUI Model

	MovieLibrary model = new MovieLibrary();


	/**
	 * Initializes the library with 20 movies.
	 */
	@SuppressWarnings({ "static-access", "unchecked" })
	public void init() {
		model.populateLibrary(model);
	}

	/**
	 * Creates the GUI, uses a MovieLibrary model for handle.
	 * @param model
	 */

	@SuppressWarnings("static-access")
	public MovieLibraryGUI(MovieLibrary model) {
		this.init();
		this.setLayout(new BorderLayout());
		headerPanel.setLayout(new FlowLayout());
		headerPanel.add(headLine);
		headerPanel.setBackground(getBackground().GRAY);

		controlPanel.setBackground(getBackground().DARK_GRAY);
		controlPanel.setLayout(new FlowLayout());
		for(int i = 0; i<buttons.length;i++) {
			buttons[i] = new JButton(buttonInfo[i].trim());
			buttons[i].addActionListener(new AppActionListener());
			controlPanel.add(buttons[i]);
		}
		buttons[0].setToolTipText("Shows the contents of the Movielibrary");
		buttons[1].setToolTipText("Uses: Title,Actor,Year,Length and Type to add Movie to library.");
		buttons[2].setToolTipText("Uses: Contents in Console in AND the Item-ID input IF title has multiple versions");
		buttons[3].setToolTipText("Uses Console in. Free text search is permitted, OR use syntax: searchfield=criteria (ex: title=moonraker)");
		buttons[4].setToolTipText("Saves the current library to a file.");
		buttons[5].setToolTipText("Loads library from file.");
		buttons[6].setToolTipText("Clears the library.");
		buttons[7].setToolTipText("Uses: Item-ID/Minutes field to find movies within alotted timeslot of \"inserted\" minutes");
		buttons[8].setToolTipText("Exits application.");

		inputFieldPanel.setLayout(new GridLayout(6,2));
		inputFieldPanel.setBackground(getBackground().LIGHT_GRAY);

		consoleJL1.setHorizontalAlignment(JLabel.CENTER);
		consoleSplitPanel.setLayout(new GridLayout(1,2));
		consoleSplitPanel.add(consoleIn);
		consoleSplitPanel.add(consoleIn2);
		inputFieldPanel.add(consoleJL1);
		inputFieldPanel.add(consoleSplitPanel);

		titleJL.setHorizontalAlignment(JLabel.CENTER);
		inputFieldPanel.add(titleJL);
		inputFieldPanel.add(titleJTF);

		mainActorJL.setHorizontalAlignment(JLabel.CENTER);
		inputFieldPanel.add(mainActorJL);
		inputFieldPanel.add(mainActorJTF);


		productionYearJL.setHorizontalAlignment(JLabel.CENTER);
		inputFieldPanel.add(productionYearJL);
		inputFieldPanel.add(productionYearJTF);

		lengthJL.setHorizontalAlignment(JLabel.CENTER);
		inputFieldPanel.add(lengthJL);
		inputFieldPanel.add(lengthJTF);

		typeJL.setHorizontalAlignment(JLabel.CENTER);
		inputFieldPanel.add(typeJL);
		inputFieldPanel.add(typeJTF);

		outputFieldPanel.setLayout(new BorderLayout());
		outputFieldPanel.add(consoleJL, BorderLayout.NORTH);
		outputFieldPanel.add(console.getFrame(),BorderLayout.CENTER);


		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(getBackground());
		mainPanel.add(outputFieldPanel,BorderLayout.NORTH);
		mainPanel.add(inputFieldPanel,BorderLayout.CENTER);
		mainPanel.add(controlPanel,BorderLayout.SOUTH);

		this.add(headerPanel, BorderLayout.NORTH);
		this.add(mainPanel, BorderLayout.CENTER);
	}


	class AppActionListener implements ActionListener{

/**
 * Switch-case for AppActionListener.
 * Contains: Show, Add, Remove, Search, Store, Read, Clear, TimeSearch and Quit.
 */
		@Override
		public void actionPerformed(ActionEvent ae) {
			JButton trigger = (JButton) ae.getSource();
			switch(trigger.getText()) {
			case "Show":{
				model.showLibraryContents();
				break;
			}
			case "Add":{
				if(titleJTF.getText().isBlank() || mainActorJTF.getText().isBlank() || productionYearJTF.getText().isBlank() || lengthJTF.getText().isBlank() ||  typeJTF.getText().isBlank()) {
					System.out.println("\nPlease make sure all input-related slots have values:\nTitle, Main actor, Production year, Length and Type");
					System.out.println("Acceptable types are: Horror, Comedy, Scifi, Thriller, None");
				}
				else {
					try {
						int prodYear = Integer.parseInt(productionYearJTF.getText());
						int length = Integer.parseInt(lengthJTF.getText());
						MovieGenre type = null;
						switch(typeJTF.getText().toUpperCase().trim()) {
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
						Movie temp = new Movie(titleJTF.getText(),mainActorJTF.getText(), prodYear , length ,type);
						model.addItem(temp);
						System.out.println(String.format("Added: %s", temp.toString()));
					}catch(NumberFormatException e) {System.out.println("NumberformatException in Add"+e.toString());}
				}
				break;
			}
			case "Remove":{
				try {
					MovieLibrary lib = model;
					Set<String> keySet = lib.getMovieMap().keySet();

					if(lib.getMovieMap().size() <1) {
						System.out.println("\nCan not remove from empty library.");
						break;
					}

					String chosenKey = consoleIn.getText().toLowerCase().trim();
					if(chosenKey.isEmpty()) {
						System.out.println("\nConsole is empty, nothing to remove");
					}
					for(String key:keySet) {
						if(chosenKey.toLowerCase().trim().equals(key.toLowerCase())) {
							if(lib.getMovieMap().get(key).size()>1) {
								System.out.println("\nThis movie contains several versions.");
								for(Movie m:lib.getMovieMap().get(key)) {
									System.out.println(m.toString());
								}
								if(consoleIn2.getText().isBlank()) {
									System.out.println("\nPlease enter the ID into the second Console textfield and try again");
								}
								else {
									List<Movie> valueList = new ArrayList<Movie>(lib.getMovieMap().get(key));
									Integer choice = Integer.parseInt(consoleIn2.getText().trim());
									for(Movie temp:valueList) {
										if(choice == temp.getItemId()) {
											lib.removeItem(temp);
										}
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
				}catch(NumberFormatException e) {System.out.println("NumberformatException in remove" +e.toString());}
				break;
			}
			case "Search":{
				String criteria = null;
				if(consoleIn.getText().isBlank()) {
					System.out.println("\nConsole is blank, please enter a search parameter");
				}
				else {
					try {
						criteria = consoleIn.getText().toLowerCase().trim();
						model.searchItem(criteria);
					}catch(NumberFormatException e) {System.out.println("Numberformatexception in search"+e.toString());}

				}
				break;
			}
			case "Store":{
				model.storeItems("MovieLibraryFromGUI.ser");
				System.out.println("Library saved");
				break;
			}
			case "Read":{
				try {
					model.readItems("MovieLibraryFromGUI.ser");
				}catch(NoSuchElementException e) {init();
				System.out.println("Could not access empty library:"+e.toString());}
				System.out.println("Movie library loaded.");
				System.out.println("Press Show.");
				break;
			}
			case "Clear":{
				model.getMovieMap().clear();
				System.out.println("\nLibrary cleared");
				break;
			}

			case "TimeSearch":{
				if(consoleIn2.getText().isBlank()) {
					System.out.println("\nPlease enter a number of minutes in the second console textfield");
				}
				else {
					String criteria = consoleIn2.getText().trim();
					model.quickSearch(criteria);
				}
				break;
			}

			case "Quit":{
				System.exit(0);
				break;
			}
			default:{
				System.out.println("In Button default.");
				break;
			}
			}
		}
	}

	class Console {
		final JPanel frame = new JPanel();

		/**
		 * Creates a console in the GUI, setting System.out 
		 */
		public Console() {
			frame.setLayout(new BorderLayout());
			JTextArea textArea = new JTextArea(10,50);
			textArea.setFocusable(false);
			JScrollPane scroll = new JScrollPane(textArea);
			scroll.setAutoscrolls(true);

			System.setOut(new PrintStream(new OutputStream() {

				@Override
				public void write(int b) throws IOException {
					textArea.append(String.valueOf((char)b));
				}
			}));
			frame.add(scroll,BorderLayout.NORTH);
		}


		public JPanel getFrame() {
			return frame;
		}

	}

}
