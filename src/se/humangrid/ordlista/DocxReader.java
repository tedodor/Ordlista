package se.humangrid.ordlista;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.Vector;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DocxReader {
	private Vector<String> complete;
	private Vector<String> words;
	private Vector<String> characters;
	
	public DocxReader(String fileName) {
		complete = new Vector<String>();
		words = new Vector<String>();
		characters = new Vector<String>();
		try {
			getString(fileName);
		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
	}
	
	public void getString(String fileName) throws IOException, SAXException, ParserConfigurationException {
	    ZipFile zipFile = new ZipFile(fileName);
	    Enumeration<? extends ZipEntry> entries = zipFile.entries();
        Scanner in = null;
        Document doc = null;
        InputStream stream = null;
        while (entries.hasMoreElements()){
	        ZipEntry entry = entries.nextElement();
	        if (entry.getName().equals("word/document.xml")) {
	        	stream = zipFile.getInputStream(entry);
	        	break;
	        }
	    }
        if (stream != null) {
        	in = new Scanner(stream);
          	doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(stream);
        	doc.normalize();
          	parseXML(doc);
        	zipFile.close();
        	in.close();
        }
	}
	
	public void parseXML(Document doc) {
		NodeList nodeList = (NodeList) doc.getElementsByTagName("w:p");
		for (int i=0;i<nodeList.getLength();i++) {
			Node thisNode = nodeList.item(i);
			Element styleElement = (Element) thisNode.getFirstChild().getFirstChild();
			String d = styleElement.getAttribute("w:val");
			switch(d) {
			case "Body":
				addText(thisNode, complete);
				break;
			case "Grammar":
				addText(thisNode, complete);
				break;
			case "Characterlist":
				addText(thisNode, characters);
				break;
			case "Wordlist":
				addText(thisNode, words);
				break;
			case "Heading":
			default:
				break;
			}
		}
	}
	
	public void addText(Node node, Vector<String> v) {
		Node wrNode = node.getFirstChild().getNextSibling();
		Element e = (Element) wrNode;
		while (e.getTagName().equals("w:r")) {
			String s = e.getTextContent();
			v.add(s);
			if (wrNode.getNextSibling() == null)
				break;
			wrNode = wrNode.getNextSibling();
			e = (Element) wrNode;
		}
	}
	
	public Vector<String> parseXML(Scanner in) {
		Pattern klammer = Pattern.compile("><|<|>");
		Vector<String> matches = new Vector<String>();
		String s = "";
		in.useDelimiter(klammer);
		while (in.hasNext()) {
			s = in.next();
			if (s.contains("pStyle")) 
				matches.add(s); 
			else if (s.equals("w:t")) {
				s = in.next();
				matches.add(s);
			}
		}	
		return matches;
	}
	
	public Vector<String> getComplete() {
		return complete;
	}

	public Vector<String> getWords() {
		return words;
	}

	public Vector<String> getCharacters() {
		return characters;
	}
}
