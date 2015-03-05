package se.humangrid.ordlista;


import java.io.FileNotFoundException;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	DocxReader docxR;
	private static final Pattern NOTHANZI = Pattern.compile("[^\u4e00-\u9fcc]");

	/*
	 * Find some way of reading the docx or txt and import the text osv.
	 * Return the wordlist, characterlist, and list of complete text for
	 * each chapter. Or perhaps thats Books job. I don't know..
	 */
	
	public Parser(String fileName) {
		docxR = new DocxReader(fileName);
	}

	private String removeNonHanzi(String s) {
		Matcher m = NOTHANZI.matcher(s);
		return m.replaceAll("");
	}
	
	public Vector<String> getComplete() throws FileNotFoundException {
		Vector<String> complete = new Vector<String>();
		Vector<String> theseComplete = docxR.getComplete();
		for (String s : theseComplete) {
			s = removeNonHanzi(s);
			if (!s.equals(""))
				complete.add(s);
		}
		return complete;
	}

	public Vector<String> getCharacters() throws FileNotFoundException {
		Vector<String> characters = new Vector<String>();
		Vector<String> theseCharacters = docxR.getCharacters();
		for (String s : theseCharacters) {
			s = removeNonHanzi(s);
			if (!s.equals(""))
				characters.add(s);
		}
		return characters;
	}

	public Vector<String> getWords() throws FileNotFoundException {
		Vector<String> words = new Vector<String>();
		Vector<String> theseWords = docxR.getWords();
		for (String s : theseWords) {
			s = removeNonHanzi(s);
			if (!s.equals(""))
				words.add(s);
		}
		return words;
	}

}
