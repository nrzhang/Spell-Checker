import java.util.ArrayList;

public class Word {
	
	private String word;
	private boolean ignored;
	private boolean replaced;
	private String replacement;
	private ArrayList<Integer> lines;
	
	//constructor - initializing the values
	public Word(String word) {	
		
		this.word = word;
		this.ignored = false;
		this.replaced = false;
		this.lines = new ArrayList<>();
	}
	
	//setter word
	public void setWord(String word){
		
		this.word = word;
	}
	
	//getter word
	public String getWord() {
		
		return word;
	}
	
	//word is ignored
	public void setIgnored(boolean bool) {
		
		this.ignored = bool;
	}
	
	public boolean getIgnored() {
		
		return this.ignored;
	}
	
	//setter replaced
	public void setReplaced(boolean bool) {
		
		this.replaced = bool;
	}
	
	public boolean getReplaced() {
		
		return this.replaced;
	}
	
	//set replacement - replacing the old word with new word
	public void setReplacement(String word) {
		
		this.replacement = word;
	}
	
	//get replacement - returning the new word
	public String getReplacement() {
		
		return this.replacement;
	}
	
	
	//toString  - converting word object to string
	public String toString() {
		
		return this.word;
	}
	
	//compareTo function
	public int compareTo(Word other) {
		
		int compareResult;
		compareResult = this.word.compareTo(other.word);
		
		return compareResult;
	}
	
	//add line to lines
	public void addLines(int line) {
		
		this.lines.add(line);
	}
	
	//return lines 
	public ArrayList<Integer> getLines() {
		
		return this.lines;
	}

}
