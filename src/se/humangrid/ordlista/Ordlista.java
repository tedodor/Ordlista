package se.humangrid.ordlista;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Vector;


public class Ordlista {

	
	private Vector<String> getText(String fileName) {
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

	private String removeSpecials(String s) {
		s = s.toLowerCase();
		if (s.matches("\\W\\w+.*")) {
			s = s.substring(1);
		}
		s = s.replaceAll("\\W\\w*", "");
		return s;
	}
	
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
	public Ordlista() {
	}

	public static void main(String[] args) {
		Ordlista o = new Ordlista();
		if (args.length > 3)
			o.writeToFile(o.getText(args[1]), args[2]);
		else
			o.writeToFile(o.getText("/Users/teodor/Documents/workspace/Ordlista/resources/text.txt"), "/Users/teodor/Documents/workspace/Ordlista/resources/ordlista.txt");

	}
}
	
