package PreProcessData;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import Classes.*;

public class StopWordRemover {
	// Essential private methods or variables can be added.
	//stop words list
	private Set<String> stop_list = new HashSet<String>();

	// YOU SHOULD IMPLEMENT THIS METHOD.
	public StopWordRemover( ) throws IOException {
		// Load and store the stop words from the fileinputstream with appropriate data structure.
		// NT: address of stopword.txt is Path.StopwordDir
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(Path.StopwordDir));
			String read_line = reader.readLine();
			
			while (read_line != null) {
				stop_list.add(read_line);
				read_line = reader.readLine();
			}
			
			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// YOU SHOULD IMPLEMENT THIS METHOD.
	public boolean isStopword( char[] word ) {
		// Return true if the input word is a stopword, or false if not.
		if (stop_list.contains(String.valueOf(word))) {
			return true;
		}
		else {
			return false;
		}
	}
}
