package chess;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class gameLibraryGen {

	
	  public static void save() throws IOException{
	    	tableAndKeys t = new tableAndKeys();
	    	t.setObj(boards, pieceRandoms);
	    	//Date now = new Date();
	    	String name = "whiteTableStart";
	    	FileOutputStream fileOut = 
	    			new FileOutputStream("/Users/madison/Documents/workspace/chess/src/chess/moveCache/" + name + ".ser");
	    	
	    	ObjectOutputStream out = new ObjectOutputStream(fileOut);
	        out.writeObject(t);
	        out.close();
	        fileOut.close();
	       	
	    }
	  
	  public static tableAndKeys load(String file) throws IOException, ClassNotFoundException{
		  tableAndKeys t;
		  FileInputStream fileIn = new FileInputStream("/Users/madison/Documents/workspace/chess/src/chess/moveCache/" + file + ".ser");
		  ObjectInputStream in = new ObjectInputStream(fileIn);
		  t = (tableAndKeys)in.readObject() ;
		  in.close();
		  fileIn.close();
			
		  boards = t.boards;
		  pieceRandoms = t.pieceRandoms;	
		  return t;
	  }

	  
	  
	  
	
	
}
