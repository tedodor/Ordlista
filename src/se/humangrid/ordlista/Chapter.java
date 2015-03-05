package se.humangrid.ordlista;

import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Vector;

public class Chapter {
	private Vector<Word> wordList;
	private Vector<String> completeList;
	private Vector<String> characterList;
	private boolean first;
	private Chapter previous;

	private class Word implements Comparable<Word>{
		public String word;
		public Word(String s) {
			word = s;
		}
		public int compareTo(Word w) {
			return w.word.length()-this.word.length();
		}
		public String toString() {
			return word;
		}
	}
	
	public Chapter(Chapter prev) {
		wordList = new Vector<Word>();
		characterList = new Vector<String>();
		completeList = new Vector<String>();
		previous = prev;
		first = (prev == null);
	}
	
	public Chapter() {
		this(null);
	}
	
	public void add(String line) {
		String[] words = line.split("\\s");
		for (String w : words) {
			w = removeSpecials(w);
			completeList.add(w);
			if (!inThisOrPrev(w)) {
				wordList.add(new Word(w));
			}
		}
	}
	
	public void makeWordList() {
		for (String word : completeList) {
			if (!inThisOrPrev(word))
				wordList.add(new Word(word));
		}
	}
	
	/**
	 * Recursive function that checks if the word specified as a parameter is in this chapter
	 * or in any previous one.
	 * @param word
	 * @return
	 */
	public boolean inThisOrPrev(String word) {
		if (first) 
			return wordList.contains(word);
		return (wordList.contains(word) || previous.inThisOrPrev(word));
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

	public Vector<Word> getWords() {
		return wordList;
	}

	/**
	 * Returns a list words in the text but not in the word list.  
	 * @return
	 */
	public Vector<String> compareCompleteToWords() {
		Vector<String> diff = completeList;
		Vector<String> words = new Vector<String>();
		for (Word word : wordList) {
			words.add(word.word);
		}
		compare(diff, words);
		return diff;
	}
	
	/**
	 * Returns a list of words in the word list but not in the text.
	 * @return
	 */
	public Vector<String> compareWordsToComplete() {
		Vector<String> diff = completeList;
		Vector<String> words = new Vector<String>();
		for (Word word : wordList) {
			words.add(word.word);
		}
		return compare(diff, words);
	}
	
	private String removeSubString(String string, String subString) {
		if (string.equals(subString)) 
			return "";
		while (string.contains(subString)) {
			string = string.subSequence(0, string.indexOf(subString)) + string.substring(string.indexOf(subString)+subString.length(), string.length());
		}
		return string;
	}
	
	/**
	 * Returns a list of characters in the text but not in the character list.
	 * @return
	 */
	public Vector<String> compareCompleteToCharacters() {
		Vector<String> diff = new Vector<String>();
		Vector<String> chars = characterList;
		for (String line : completeList) {
			for (char character : line.toCharArray()) {
				diff.addElement(String.valueOf(character));
			}
		}	
		compare(diff, chars);
		return diff;
	}

	private void removeEmpty(Vector<String> v) {
		for (int i=(v.size()-1); i >= 0; i--) {
			if (v.get(i).equals("")) {
				v.remove(i);
			}
		}
	}
	
	/**
	 * Compares the vectors. Iterates over wordList, if text contains the word all occurrences get removed
	 * and the word gets removed from wordList, if not the word stays in wordList.
	 * @param text
	 * @param wordList
	 */
	public Vector<String> compare(Vector<String> text, Vector<String> wordList) {
		Vector<String> newWordList = new Vector<String>();
		for (int i=0; i<wordList.size(); i++) {
			int m = 0;
			for (int j=0; j<text.size(); j++) {
				if (text.get(j).contains(wordList.get(i)))
					m++;
				text.set(j, removeSubString(text.get(j), wordList.get(i)));
			}
			if (m == 0)
				newWordList.add(wordList.get(i));
		}
		removeEmpty(text);
		return newWordList;
	}
	
	/**
	 * Returns a list of characters in the character list but not the text. 
	 * @return
	 */
	public Vector<String> compareCharactersToComplete() {
		Vector<String> diff = new Vector<String>();
		Vector<String> chars = characterList;
		for (String line : completeList) {
			for (char character : line.toCharArray()) {
				diff.addElement(String.valueOf(character));
			}
		}	
		return compare(diff, chars);
	}
	
	public void setComplete(Vector<String> words) {
		completeList = words;
	}
	
	public void setWords(Vector<String> words) {
		for (String s : words) {
			wordList.addElement(new Word(s));
		}
		Collections.sort(wordList);
	}
	
	public void setCharacter(Vector<String> words) {
		characterList = words;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1) {
			System.out.println("Usage: java -jar Ordlista.jar [inputfile.docx]");
			return;
		}
		Parser p = new Parser(args[0]);
		Chapter c = new Chapter();
		c.setCharacter(p.getCharacters());
		c.setWords(p.getWords());
		c.setComplete(p.getComplete());
		Vector<String> v;
		v = c.compareCharactersToComplete();
		System.out.println("Characters in character list but not in the text:");
		for (String s : v) {
			System.out.println(s);
		}
		v = c.compareCompleteToCharacters();
		System.out.println("-----------------------");
		System.out.println("Characters in text but not in the character list:");
		for (String s : v) {
			System.out.println(s);
		}
		v = c.compareWordsToComplete();
		System.out.println("-----------------------");
		System.out.println("Words in word list but not in the text:");
		for (String s : v) {
			System.out.println(s);
		}
		v = c.compareCompleteToWords();
		System.out.println("-----------------------");
		System.out.println("Words in text but not in the word list:");
		for (String s : v) {
			System.out.println(s);
		}
	}
}
