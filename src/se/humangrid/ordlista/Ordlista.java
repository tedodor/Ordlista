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
	private Vector<String> getWords(String fileName) {
		Vector<String> wordsInFile = new Vector<String>();
		try {
			Scanner in = new Scanner(new File(fileName));
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
	private void writeToFile(Vector<String> wordsInFile, String fileName) {
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
		if (args.length > 3)
			o.writeToFile(o.getText(args[1]), args[2]);
		else
			o.writeToFile(o.getText("/Users/teodor/Documents/workspace/Ordlista/resources/text.txt"), "/Users/teodor/Documents/workspace/Ordlista/resources/ordlista.txt");

	}
}
	
