package PreProcessData;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import Classes.Path;

/**
 * This is for INFSCI 2140 in 2018
 *
 */
public class TrecwebCollection implements DocumentCollection {
	// Essential private methods or variables can be added.
	
	//reader for file
	private BufferedReader reader;
	//current read line
	private String cur_line;
	
	// YOU SHOULD IMPLEMENT THIS METHOD.
	public TrecwebCollection() throws IOException {
		// 1. Open the file in Path.DataWebDir.
		// 2. Make preparation for function nextDocument().
		// NT: you cannot load the whole corpus into memory!!
		
		//read docset.trecweb
		reader = new BufferedReader(new FileReader(Path.DataWebDir));
		System.out.println("Start reading \"trecweb\"");
		//get the first line
		cur_line = reader.readLine();
	}
	
	// YOU SHOULD IMPLEMENT THIS METHOD.
	public Map<String, Object> nextDocument() throws IOException {
		// 1. When called, this API processes one document from corpus, and returns its doc number and content.
		// 2. When no document left, return null, and close the file.
		// 3. the HTML tags should be removed in document content.
		
		if (cur_line == null) {
			reader.close();
			return null;
		}
		
		boolean doc_tag = true;//estimate if in the <doc> segment
		boolean text_tag = false;//estimate if in the <text> segment
		

		//document index
		Map<String, Object> doc_index = new HashMap<String, Object>();
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
			if (cur_line.startsWith("</DOCHDR>")) {
				text_tag = true;
				cur_line = reader.readLine();
				continue;
			}
			if (cur_line.startsWith("</DOC>")) {
				text_tag = false;
				cur_line = reader.readLine();
				continue;
			}
			
			//handle doc number
			if (cur_line.startsWith("<DOCNO>") && cur_line.endsWith("</DOCNO>")) {
				cur_line = cur_line.replace("<DOCNO>", "");
				cur_line = cur_line.replace("</DOCNO>", "");
				cur_line = cur_line.trim();
				doc_no = cur_line;
			}
			
			//handle context
			if (text_tag == true) {
				//StringBuilder can change String
				StringBuilder sb = new StringBuilder(cur_line);
				
				//delete the tag
				while (sb.toString().contains("<") && sb.toString().contains(">")) {
					if (sb.indexOf(">") <= sb.indexOf("<")) {
						sb = sb.delete(0, sb.indexOf(">") + 1);
						sb = sb.delete(sb.indexOf("<"), sb.length() + 1);
					}else {
						sb = sb.delete(sb.indexOf("<"), sb.indexOf(">") + 1);
					}
				}
				if (sb.toString().contains("<")) {
					sb = sb.delete(sb.indexOf("<"), sb.length() + 1);
				}
				if (sb.toString().contains(">")) {
					sb = sb.delete(0, sb.indexOf(">") + 1);
				}
				
				content = content +" " + sb.toString();
			}
			
			cur_line = reader.readLine();
		}
		
		content = content.toString().toCharArray();
		doc_index.put(doc_no, content);
		return doc_index;		
	}
	
}
