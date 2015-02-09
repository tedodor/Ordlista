package se.humangrid.ordlista;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.Vector;

public class Book {
	
	private static final String CHAPTERBREAKDEFAULT = "Chapter";
	
	private Vector<Chapter> chapters;
	private Scanner in;
	private String chapterKeyWord = CHAPTERBREAKDEFAULT;
	
	public Book(File inputFile) throws FileNotFoundException {
		in = new Scanner(inputFile);
		chapters = new Vector<Chapter>();
	}
	
	public void setChapterKeyWord(String chapterKeyWord) {
		this.chapterKeyWord = chapterKeyWord;
	}

	/**
	 * Reads the text from a file and returns a vector with the words.
	 * @return
	 * @throws FileNotFoundException 
	 */
	private String getNext() {
		if (in.hasNext()) {
		    String nextLine = in.nextLine();
		    if (!nextLine.contains(chapterKeyWord)) {
			return nextLine;
		    }
		}
		return null;
	}
	
	
	public void addChapter() {
		Chapter newChapter;
		try {
			newChapter = new Chapter(chapters.lastElement());
		} catch (NoSuchElementException e) {
			newChapter = new Chapter(null);			
		}	
		String nextLine = getNext();
		while (nextLine != null) {
			newChapter.add(nextLine);
			nextLine = getNext();
		}
		chapters.add(newChapter);
	}
	
	
	public void makeWordList() {
	    while (in.hasNext()) {
		addChapter();
	    }
	}

	public static void main(String[] args) throws FileNotFoundException {
		Book book;
		PrintWriter writer;
		if (args.length >= 2) {			
			book = new Book(new File(args[0]));
			writer = new PrintWriter(args[1]);
		} else if (args.length == 1) {
			book = new Book(new File(args[0]));
			writer = new PrintWriter(System.out);	
		} else {
			throw new FileNotFoundException();
		}
		book.makeWordList();
		String ccc = CHAPTERBREAKDEFAULT;
		// somehow get a better value for ccc if such can be found
		book.setChapterKeyWord(ccc);
		for (Chapter ch : book.chapters) {
			for (String w : ch.getWords()) {
				writer.println(w);
			}
			writer.println("next chapter -----------------------------");
		}
		writer.close();
	}
	
}

