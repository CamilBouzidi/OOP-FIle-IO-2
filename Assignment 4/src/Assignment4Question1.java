import java.util.ArrayList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * This is the Assignment4Question1 class created for the Assignment 4.
 * The user inputs the name of a txt file, and the program will create a txt dictionary of the words in alphabetical order,
 * excluding punctuation and numbers.
 * @author Morin-Laberge, William (ID #40097269), and Bouzidi, Camil (ID #40099611)
 * @version 4.0
 * COMP 249 
 * Assignment #4
 * April 8th 2019
 */
public class Assignment4Question1 {
	//I think I should create a method that check that the next word is a word, not a \!, \., etc.
	public static void main(String[] args) {
		Scanner kb = new Scanner(System.in);
		Scanner sc = null;
		PrintWriter pw = null;
		String nameIn=null; //Name of the author
		System.out.println("Welcome to the Sub-dictionary Creator!"
				+ "\n Please enter the name of the .txt file you would like to go through: ");
		nameIn = kb.next();
		ArrayList<String> wordList = new ArrayList<String>(); //arraylist that will contain the words in the dictionnary
		ArrayList<String> illegal = new ArrayList<String>(); //arraylist of illegal characters
		boolean canWrite = true;
		illegal.add(".");
		illegal.add(",");illegal.add("!");
		illegal.add("?");illegal.add(";");
		illegal.add(":");illegal.add("=");
		
		try { //Opening the Scanner and PrintWriter
			sc = new Scanner(new FileInputStream(nameIn+".txt"));
			pw = new PrintWriter(new FileOutputStream("SubDictionary.txt"));
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			System.out.println("Program will terminate.");
			sc.close();
			pw.close();
			System.exit(0);
		} finally {
			while (sc.hasNext()) {
				canWrite = true; //initialize it to true because there is minority of cases where you cannot write.
				String word = sc.next();
				word = word.toUpperCase();
				for (String p : illegal) { //iterates to see if word ends with illegal character. If it does, illegal is removed
					if (word.contains(p)) {
					System.out.println("Error: "+p+" found in "+word);
					word = word.substring(0, word.length()-1);
					System.out.println("The word is now: "+word);
					}
				}
				if (word.contains("�")) {
					word = word.substring(0,word.indexOf("�"));
				}
				if ((word.length()==1)&&(!word.equals("I"))&&(!word.equals("A"))) //eliminates the words that only have 1 letter (except I and A)
				canWrite=false;
				for (int i=0; i<10; i++) { //Checks if word contains number. If it does, it's not added to the list.
					if (word.contains(i+"")) {
						canWrite=false;
						break;
					}
				}
				if ((!wordList.contains(word))&&(canWrite)) { //if the word is writeable and doesn't already exist in the list, add it.
						wordList.add(word);
				}
			}
			wordList.sort(null); //sort in alphabetical order
			if (wordList.get(0).isEmpty()) {
				wordList.remove(wordList.get(0));
			}
			wordList.trimToSize(); //reduce the size to its real size
			System.out.println("The character at the beginning is: "+wordList.get(0).charAt(0));
			char begin = wordList.get(0).charAt(0); //index of the dictionary.
			boolean indexPrinted=false;
			for (String a: wordList) {//goes through the arraylist and checks if the first letter of the word is equal to the current index of the dictionary
				//If it is, the index is written in the dictionary and is changed to the next letter. 
				//It won't change until a word that starts with the next letter is reached.
				//It also writes the words from the arraylist into the dictionary.
				if (a.charAt(0)==begin) {
					if (!indexPrinted) {
						System.out.println(begin + " is equal to "+a.charAt(0));
						pw.println("\n"+begin+"\n====");
						indexPrinted=true;
					}
				}else if (a.charAt(0)>begin){
					begin=a.charAt(0);
					System.out.println(begin + " is equal to "+a.charAt(0));
					pw.println("\n"+begin+"\n====");
					indexPrinted=true;
				}
				pw.print(a+"\n");
				System.out.println(a);
			}
			pw.close();
		}
	}
}
