package se.humangrid.ordlista;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;

/**
* Add word lists to texts att specified points.
* To be used in e.g. language text books.
*/

public class Ordlista {
	
	/**
	 * Gives a vector cointaning all the unique words in the text
	 * specified as fileName
	 * @param fileName 
	 * @return
	 */
	private Vector<String> getWords(File file) {
		Vector<String> wordsInFile = new Vector<String>();
		try {
			Scanner in = new Scanner(file);
			while (in.hasNextLine()) {
				String line = in.nextLine();
				String[] s = line.split("\\s");
				for (String w: s) {
					w = removeSpecials(w);	
					if (! wordsInFile.contains(w)) {
						wordsInFile.add(w);
					}
				}
			}
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return wordsInFile;
	}

	/**
	 * Removes all the special characters in a String.
	 * If the string contains a special character in the middle
	 * it removes it and everything after
	 * @param s
	 * @return
	 */
	private String removeSpecials(String s) {
		s = s.toLowerCase();
		if (s.matches("\\W\\w+.*")) {
			s = s.substring(1);
		}
		s = s.replaceAll("\\W\\w*", "");
		return s;
	}
	
	/**
	 * Writers all the words in the vector given as a parameter 
	 * to the file specified as a parameter.
	 * @param wordsInFile
	 * @param fileName
	 */
	private void writeToFile(Vector<String> wordsInFile, File fileName) {
		try {
			PrintWriter writer = new PrintWriter(fileName);
			for (String word : wordsInFile) {
				writer.println(word);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}
	
	/**
	 * Empty constructor for the class
	 */
	public Ordlista() {
	}

	/**
	 * Mainclass mainly for testing.
	 * @param args
	 */
	public static void main(String[] args) {
		Ordlista o = new Ordlista();
		File inputFile = null;
		File outputFile = null;
		if (args.length == 0){
			inputFile = new File("text.txt");
			outputFile = new File("output.txt");
		} else if (args.length == 1) {
			inputFile = new File(args[0]);
			outputFile = new File("output.txt");
		} else if (args.length == 2) {
			inputFile = new File(args[0]);
			outputFile = new File(args[1]);			
		}
		o.writeToFile(o.getWords(inputFile), outputFile);			

		
			/*
			o.writeToFile(
					o.getWords(
							new File(
									(o.getClass().getResource("/text.txt")).getPath())), new File(o.getClass().getResource("/ordlista.txt").getFile()));
*/
	}
}
	
