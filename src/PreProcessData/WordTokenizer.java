package PreProcessData;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * This is for INFSCI 2140 in 2019
 * 
 * TextTokenizer can split a sequence of text into individual word tokens.
 */
public class WordTokenizer {
	// Essential private methods or variables can be added.
	//Tokenizer
	private List<String> tokenization = new ArrayList<String>();
	private Iterator<String> it;
	
	// YOU MUST IMPLEMENT THIS METHOD.
	public WordTokenizer( char[] texts ) {
		// Tokenize the input texts.
		
		boolean finish = false;//whether complish a word
		String word = "";
		for (int i = 0; i < texts.length; i++) {
			//if finish a word, push into queue
			if (finish == true) {
				tokenization.add(word);
				word = "";
				finish = false;
			}
			
			//if is a letter, give it to word
			if ((texts[i] >= 'A' && texts[i] <= 'Z') ||
				(texts[i] >= 'a' && texts[i] <= 'z') ||
				(texts[i] >= '0' && texts[i] <= '9')) {
				word += String.valueOf(texts[i]);
			}
			else if (!word.equals("")) {
				finish = true;
			}
		}
		it = tokenization.iterator();
	}
	
	// YOU MUST IMPLEMENT THIS METHOD.
	public char[] nextWord() {
		// Return the next word in the document.
		// Return null, if it is the end of the document.
		if (it.hasNext()) {
			return it.next().toString().toCharArray();
		}else {
			return null;
		}
	}
	
}
