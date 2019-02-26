package PreProcessData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Classes.Path;

/**
 * This is for INFSCI 2140 in 2019
 *
 */
public class TrectextCollection implements DocumentCollection {
	// Essential private methods or variables can be added.
	
	//reader for file
	private BufferedReader reader;
	//current read line
	private String cur_line;
	
	// YOU SHOULD IMPLEMENT THIS METHOD.
	public TrectextCollection() throws IOException {
		new Path();
		// 1. Open the file in Path.DataTextDir.
		// 2. Make preparation for function nextDocument().
		// NT: you cannot load the whole corpus into memory!!
		
		//read docset.trectext
		reader = new BufferedReader(new FileReader(Path.DataTextDir));
		System.out.println("Start reading \"trectext\"");
		//get the first line
		cur_line = reader.readLine();
	}
	
	// YOU SHOULD IMPLEMENT THIS METHOD.
	public Map<String, Object> nextDocument() throws IOException {
		// 1. When called, this API processes one document from corpus, and returns its doc number and content.
		// 2. When no document left, return null, and close the file.
		
		//if is the end of the file
		if (cur_line == null) {
			reader.close();
			return null;
		}
		
		//document index
		Map<String, Object> doc_index = new HashMap<String, Object>();
		boolean doc_tag = true;//estimate if in the <doc> segment
		boolean text_tag = false;//estimate if in the <text> segment
		
		String doc_no = null;
		Object content = "";
		while (doc_tag == true) {
			//set doc_tag
			if (cur_line.startsWith("<DOC>")) {
				doc_tag = true;
				cur_line = reader.readLine();
				continue;
			}
			if (cur_line.startsWith("</DOC>")) {
				doc_tag = false;
				cur_line = reader.readLine();
				continue;
			}
			
			//set text_tag
			if (cur_line.startsWith("<TEXT>")) {
				text_tag = true;
				cur_line = reader.readLine();
				continue;
			}
			if (cur_line.startsWith("</TEXT>")) {
				text_tag = false;
				cur_line = reader.readLine();
				continue;
			}
			
			//handle doc number
			if (cur_line.startsWith("<DOCNO>")) {
				cur_line = cur_line.replace("<DOCNO>", "");
				cur_line = cur_line.replace("</DOCNO>", "");
				cur_line = cur_line.trim();
				doc_no = cur_line;
			}
			
			//handle context
			if (text_tag == true) {
				content = content + " " + cur_line;
			}
			cur_line = reader.readLine();
		}
		
		content = content.toString().toCharArray();
		doc_index.put(doc_no, content);
		return doc_index;
	}
	
}
