import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SpellChecker {
	
	public static QuadraticProbingHashTable<String> dictionary = new QuadraticProbingHashTable<String>();
	@SuppressWarnings("unchecked")
	public static ArrayList<Word>[] misspelledWords = new ArrayList[52];

	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//adding blank arrayLists to array
		for (int i = 0; i < 52; i++) {
            misspelledWords[i] = new ArrayList<Word>();
        }
		
		Scanner scan = new Scanner(System.in);
		String choice = "a";
		
		//scan dictionary
		System.out.println("Reading Dictionary...");
		scanDictionary("Dictionary.txt");
		System.out.println("Dictionary read.");
		
		
		//prompt user for name of document
		//scan document
		System.out.println("Please enter a file to spellcheck >>");
		String filename = scan.nextLine();
		Scanner fr = new Scanner(new File(filename));
		
		
		while (!choice.equals("q")) {
		
			//go through misspelled words and ask user what to do about them
			System.out.println("Print words (p), enter new file (f), or quit (q) ?");
			choice = scan.nextLine();
			
			if (choice.equals("p")) {
				
				scanFile(filename);
				int lineNum = 1;
				
				while(fr.hasNextLine()) {					
					
					//creating line arrays
					String line = fr.nextLine();
					String[] lineArray= new String[line.length()];
					lineArray = line.split("\\s+");
					
					
					for (int i=0; i<lineArray.length; i++) {
						String word = lineArray[i];
						word = word.replaceAll("\\W", "");
						
						Word wordObject = findMisspelledWord(word);
					
						//if word not in dictionary call misspelled 
						if (!dictionary.contains(word)) { 
							
							
							if (wordObject.getIgnored() == false) { 
								if (wordObject.getReplaced() == false) {
			
									System.out.println("--" + word + " " + lineNum);
									System.out.println("ignore all (i), replace all (r), next(n), or quit (q)?");
									choice = scan.nextLine();
									
									//ignore this occurrence or ignore all
									if (choice.equals("i")) {
										ignoreAll(word); 
									}
									
									
									//replace this and all
									if (choice.equals("r")) {
										choice = replaceAll(word);
										
										if (choice.equals("q"))
											break;
										
										else if (!choice.equals("n")) {
											
											//using string to find word object in misspelled Words
											char letter = word.charAt(0);
											int index = 0;
											
											if (letter > 64 && letter < 90) //capital letters
												index = letter - 65;
											
											else if (letter > 96 && letter < 123) //lower case letters
												index = letter - 71;
											
											ArrayList<Word> tempList = misspelledWords[index];	
											
											for (int j = 0; j < tempList.size(); j++) {
												
												if (tempList.get(j).getWord().equals(word)) {//if the words match
													
													tempList.get(j).setReplacement(choice);
													tempList.get(j).setReplaced(true);
												}
											}
										}
			
									}
								
									if (choice.equals("q"))
										break;	
								}
							
							}
						
						}

					}
					
					lineNum++;
					
					if (choice.equals("q"))
						break;	
				}
				
				writeFiles(filename);
				
			}
			
			//enter a new file
			else if (choice.equals("f")) {
				System.out.println("Please enter a file to spellcheck >>");
				filename = scan.nextLine();
				
				//needed to reset file reader
				fr = new Scanner(new File(filename));
				
				//initializing new missspelledWords
				misspelledWords = new ArrayList[52];
				for (int i = 0; i < 52; i++) {
		            misspelledWords[i] = new ArrayList<Word>();
		        }
			}
		}
		
		System.out.println("Goodbye!");
		
	}
	
	
	
	//method to scan dictionary and put all the words into hash table
	public static void scanDictionary(String file) throws IOException {
		
		Scanner fr = new Scanner(new File(file));
		
		while(fr.hasNextLine()) {
			
			String word = fr.nextLine(); //individual word in dictionary is set as variable word
			dictionary.insert(word); //word inserted into dictionary hash table
			
		}
	}
	
	//method to scan document
	//parse through punctuation
	public static void scanFile(String file) throws IOException {
	
		Scanner fr = new Scanner(new File(file));
		
		while(fr.hasNext()) {
		
			String word = fr.next(); //creating a word after each space
			word = word.replaceAll("\\W", ""); //looking for any non-word/letter characters and replacing with a blank
			
			//if word not in dictionary call misspelled 
			if (!dictionary.contains(word) && containsMisspelledWords(word) == false) { 
				
				insertMisspelled(word);
			}
	
		}
	}
	
	//method to check for misspelled words and put into array
	public static void insertMisspelled(String str) {	
		
		Word word = new Word(str); //new word object - misspelled word		
		
		char letter = str.charAt(0); //getting the first letter of the string
		
		if (letter > 64 && letter < 90) { //capital letters
			
			int index = letter - 65;
			misspelledWords[index].add(word);
			
		}
		
		else if (letter > 96 && letter < 123) { //lower case letters
			
			int index = letter - 71;
			misspelledWords[index].add(word);
			
		}
		
	}
	
	//writing both corrected and order files
	public static void writeFiles(String filename) throws IOException {
		
		Scanner fr = new Scanner(new File(filename));
		
		filename = filename.replace(".txt", "");
		String outfile1 = filename + "_order" + ".txt"; //order file
		String outfile2 = filename + "_corrected" +".txt"; //corrected file
		BufferedWriter out1 = new BufferedWriter(new FileWriter(outfile1));
		BufferedWriter out2 = new BufferedWriter(new FileWriter(outfile2));
		
		int lineNum1 = 1;
		
		while(fr.hasNextLine()) {
			
			
			String line = fr.nextLine();
			String[] lineArray= new String[line.length()];
			lineArray = line.split("\\s+");
			
			
			for (int i=0; i<lineArray.length; i++) { //we don't want the program to call a word that's already been asked.
				String word = lineArray[i];
				String word2 = lineArray[i];
				word = word.replaceAll("\\W", ""); //stripping punctuation
			
				//if word not in dictionary call misspelled 
				if (!dictionary.contains(word)) { 
									
					//System.out.println("--" + word + " " + lineNum);
					out1.write(word + " " + lineNum1 + "\n"); //writing to the outfile
					
					//find word object
					Word wordObject = findMisspelledWord(word);
					
					//find word object in table		
					if (wordObject.getIgnored() == true) //if word is ignored
						out2.write(word2 + " ");
					
					else if (wordObject.getReplaced() == true) {  //if word has been replaced
						
					
						if (word2.substring(word2.length()-1, word2.length()).matches("\\p{Punct}") == true) {//if word contains punctuation
							out2.write(wordObject.getReplacement() + word2.substring(word2.length()-1, word2.length()) + " ");
						}
						
						else
							out2.write(wordObject.getReplacement() + " ");
					}
					
					else
						out2.write(word2 + " ");
				}
				
				else {
					out2.write(word2 + " ");
				}
			}
			
			lineNum1++;
			out2.newLine(); 
			
		}
		

		out1.close();
		out2.close();
	}
	
	
	//method to ignore all occurrences of that specific word
	public static void ignoreAll(String str) { 
		
		char letter = str.charAt(0);
		int index = 0;
		
		if (letter > 64 && letter < 90) //capital letters
			index = letter - 65;
		
		else if (letter > 96 && letter < 123) //lower case letters
			index = letter - 71;
		
		
		for (int i = 0; i < misspelledWords[index].size(); i++) {
			
			if (misspelledWords[index].get(i).getWord().equals(str)) { //if the words match
				misspelledWords[index].get(i).setIgnored(true);
			}
		}
	}
	
	
	//method to replace this and all
	public static String replaceAll(String str) {
		
		Scanner scan = new Scanner(System.in);
		
		ArrayList<String> suggestions = new ArrayList<String>();
		
		suggestions.addAll(replace(str));
		suggestions.addAll(insert(str));
		suggestions.addAll(delete(str));
		
		String temp = "";
		
		for (int i=0; i<suggestions.size(); i++) {
			
			temp = temp + "(" + (i+1) + ")" + suggestions.get(i) + ", ";
		}
		
		System.out.println("Replace with " + temp + "or next (n), or quit (q)?");
		String choice = scan.nextLine();
		int numChoice;
		String replacement;
		
		if (choice.equals("n"))
			return "n";
		else if (choice.equals("q"))
			return "q";
		else {
			numChoice = Integer.parseInt(choice);
			replacement = suggestions.get(numChoice-1);
			return replacement;
		}
		
		
	}
	
	//replace method
	public static ArrayList<String> replace(String str) {
		
		
		ArrayList<String> suggestions = new ArrayList<String>();
		String temp = str;
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		char letter;
		
		for (int i=0; i < str.length(); i++) { 		//loop to search through word
			for (int j=0; j<26; j++) {		//loop to search through alphabet
				
				letter = alphabet.charAt(j);
				temp = str.substring(0, i) + letter + str.substring(i +1, str.length()); //concatenating word together
				
				if (dictionary.contains(temp))
					suggestions.add(temp);
			}
		}
		
		return suggestions;
	}
	
	//insert method
	public static ArrayList<String> insert(String str) {
		
		ArrayList<String> suggestions = new ArrayList<String>();
		String temp = str;
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		char letter;
		
		
		//after the first character and before the last character
		for (int i=0; i < str.length(); i++) { 		//loop to search through word
			for (int j=0; j<26; j++) {		//loop to search through alphabet
			
				letter = alphabet.charAt(j);
				temp = str.substring(0, i) + letter + str.substring(i, str.length()); //concatenating word together
				
				if (dictionary.contains(temp)) 
					suggestions.add(temp);
	
				//inserting letter before the word
				temp = letter + str;
				
				if (dictionary.contains(temp)) 
					suggestions.add(temp);
				
				//inserting letter after the word
				temp = str + letter;
				
				if (dictionary.contains(temp)) 
					suggestions.add(temp);

			}
		}
		return suggestions;
	}
	
	//delete method
	public static ArrayList<String> delete(String str) {
		
		ArrayList<String> suggestions = new ArrayList<String>();
		String temp = str;
		
		for (int i=0; i < str.length(); i++) { 		//loop to search through word
			for (int j=0; j<26; j++) {		//loop to search through alphabet
				
				temp = str.substring(0, i) + str.substring(i +1, str.length()); //concatenating word together
				
				if (dictionary.contains(temp) && !suggestions.contains(temp))
					suggestions.add(temp);
			}
		}
		
		return suggestions;
	}
	
	//method to see if misspelledWords contains a word
	public static boolean containsMisspelledWords(String str) {
		
		char letter = str.charAt(0);
		int index = 0;
		boolean contain = false;
		
		if (letter > 64 && letter < 90) //capital letters
			index = letter - 65;
		
		else if (letter > 96 && letter < 123) //lower case letters
			index = letter - 71;
		
		for (int i=0; i< misspelledWords[index].size(); i++) {
			
			Word word = misspelledWords[index].get(i);
			
			if (word.toString().equals(str)) {
				contain = true;
				break;
			}
			else {
				contain = false;
			}
		}
		
		return contain;
		
	}
	
	//method for find wordObject(returns the word object)
	public static Word findMisspelledWord(String str) {
		
		char letter = str.charAt(0);
		int index = 0;
		Word word = null;
		
		if (letter > 64 && letter < 90) //capital letters
			index = letter - 65;
		
		else if (letter > 96 && letter < 123) //lower case letters
			index = letter - 71;
		
		for (int i=0; i< misspelledWords[index].size(); i++) {
			
			if (misspelledWords[index].get(i).getWord().equals(str))
				word = misspelledWords[index].get(i);
		}
		
		return word;
			
	}
	

}
