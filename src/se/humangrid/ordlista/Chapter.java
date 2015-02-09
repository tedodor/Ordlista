package se.humangrid.ordlista;

import java.util.Vector;

public class Chapter {
	private Vector<String> ordLista;
	private boolean first;
	private Chapter previous;

	
	public Chapter(Chapter prev) {
		ordLista = new Vector<String>();
		previous = prev;
		first = (prev == null);
		}
	
	public void add(String line) {
		String[] words = line.split("\\s");
		for (String w : words) {
			w = removeSpecials(w);
			if (!inThisOrPrev(w)) {
				ordLista.add(w);
			}
		}
	}
	
	public boolean inThisOrPrev(String word) {
		if (first) 
			return ordLista.contains(word);
		return (ordLista.contains(word) || previous.inThisOrPrev(word));
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

	public Vector<String> getWords() {
		return ordLista;
	}
}
