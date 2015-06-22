package chess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

import javax.swing.ImageIcon;

import chess.AIv2Tree;
import chess.AIv2Tree.Node;
import chess.ChessBoardWithColumnsAndRows;

public class hashTableMapper {
	
	public static final int QUEEN = 1, KING = 0,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
	
	
	public static final String[] White_Starting_Row = {
		"white rook", "white knight", "white bishop", "white queen",
		"white king", "white bishop", "white knight", "white rook"
	};
	
	public static final String[] Black_Starting_Row = {
		"black rook", "black knight", "black bishop", "black queen",
		"black king", "black bishop", "black knight", "black rook"
	};
	
	public static Hashtable<Long, Node> boards = new Hashtable<Long, Node>(200000);
	public static long [] pieceRandoms = new long [769];

	
	public static final int[] STARTING_ROW = {
	        ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK
	};
	
	public static AIv2Tree tree = new AIv2Tree();
	
	
	public static void addToTable() throws IOException, ClassNotFoundException {
	
		//String [][] cashedGames = 
		
		boolean whiteWins = true;
		long [] keyTag = new long [32];
		tableAndKeys t = null;
		boolean hashTableStart = false;
		
		if (hashTableStart){
			t = load("whiteTableStart");
			boards = t.boards;
			pieceRandoms = t.pieceRandoms;
		}
		else{
			boards = new Hashtable<Long, Node>(200000);
			Random randomno = new Random();
    	    
	    	pieceRandoms = new long [769];
	    	
	    	
	    	long f = ~(bitBoardOperations.bitConstants[0] 
	  	    			| bitBoardOperations.bitConstants[1] | bitBoardOperations.bitConstants[2]
	  	    					| bitBoardOperations.bitConstants[3] | bitBoardOperations.bitConstants[4]);
	  	   	
	    	Random randomno2 = new Random();
	  	    for (int i = 0; i < 769; i++){
	  	    	pieceRandoms[i] = randomno.nextLong() ^ randomno2.nextLong() << 1;
	  	    	pieceRandoms[i] = pieceRandoms[i] & f;
	  	    }
	  	    
	  	    keyTag[0] = 0;
	  	    for (int i = 1; i < 32; i++){
	  	    	keyTag[i] = keyTag[i - 1] + bitBoardOperations.bitConstants[4];
	  	    }
	  	    
		}
		
		String[][] chessBoardConfig = new String[8][8];
		
		for (int i = 2; i < 6; i++){
			for (int j = 0; j < 8; j++){
				chessBoardConfig[i][j] = "empty";
			}
		}
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
			chessBoardConfig[0][ii] = Black_Starting_Row[ii];
		}
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
			chessBoardConfig[1][ii] = "black pawn";
	    }
		for (int ii = 0; ii < STARTING_ROW.length; ii++) {
	        chessBoardConfig[7][ii] = White_Starting_Row[ii];
	    }
	       
	    for (int ii = 0; ii < STARTING_ROW.length; ii++) {
	     		chessBoardConfig[6][ii] = "white pawn";
	    }	       
	    
	    for (int i = 0; i < 8; i++){
        	System.out.println(Arrays.toString(chessBoardConfig[i]));
        }
		
	    String pieceInitials = "NBRQKO";   
	    
	    String columnInitials = "abcdefgh";
	    
	    long[] board = bitBoardOperations.bitBoardGen(chessBoardConfig);
	    
	    bitBoardOperations.printBoardLevel(board[0] | board[7]);
	   

	    Node r = tree.new Node();
	    
	  
	    r.board = board.clone();
	   
	    r.score = 0;
	    r.children = new Node[1];
	    r.references = 1;
	    r.level = 0;
	   // System.out.println((r.references));
	    tree.root = r;
	    
	    System.out.println((tree.getRoot() == r));
	    System.out.println((tree.getRoot() == null));
	    boolean whiteMove = true;
	    
	    
	    Long key = new Long(bitBoardOperations.hasher(pieceRandoms, board , whiteMove));
	    tree.root.key = key;
	    System.out.println(tree.root.key);
	    
	    
	    
	    long p = 0;
	      
	    String moveList = "1. e4 c5 2. Nf3 d6 3. d4 Nf6 4. Nc3 cxd4 5. Nxd4 a6 6. Be3 Ng4 7. Bg5 h6 8. Bh4 g5 9. Bg3 Bg7 10. h3 Ne5 11. Nf5 Bxf5 12. exf5 Nbc6 13. Nd5 e6 14. fxe6 fxe6 15. Ne3 O-O 16. Be2 d5 17. O-O Ng6 18. c4 Nd4 19. cxd5 exd5 20. Bg4 Nf4 21. Rc1 Qa5 22. Nc2 Nxc2 23. Rxc2 Rae8 24. Bf3 Rd8 25. Bxf4 Rxf4 26. Re1 Bf6 27. Rce2 Kg7 28. Qb1 d4 29. Be4 Qc7 30. Qd3 Be7 31. a3 Bf8 32. Bf5 Rd5 33. Be6 Rd6 34. Ba2 Rdf6 35. Bb1 Kg8 36. Re7 Bxe7 37. Qh7+ Kf8 38. Qh8+ Kf7 39. Ba2+ Kg6 40. Qe8+ Rf7 41. Qg8+ Rg7 42. Bb1+ d3 43. Bxd3+ Kh5 44. Qxg7 Rxf2 45. Rxe7 Qc5 46. Qxh6+";
	    moveList = moveList.replace("+", "");
	    moveList = moveList.replace("x", "");
	   // moveList = moveList.replace("\\s+", "");
	    
	    String [] split = moveList.split("\\s+");
	    System.out.println(Arrays.toString(split));
	    System.out.println(split.length);
	    Node n = tree.root;
	    
	    int round = 0;
	    
	    for(int i = 1; i < split.length; i++){
	    	if (i % 3 != 0){
	    		System.out.println("i: " + (i - i/3));
		    	int color = 0;
		    	
		    	
		    	if ( i % 3 == 2){
		    		color = 7;
		    		whiteMove = false;
		    	}	   
		    	else{
		    		color = 0;
		    		whiteMove = true;
		    	}
		    	ArrayList<long[]> possibleMoves = null;
		    	
		    	possibleMoves = bitBoardOperations.completeMoveGen(board, whiteMove);
		    	
	    	
		    	
		    	if (p != 0){
					if (whiteMove){
						if (((board[bitBoardOperations.WP] & (p >>> 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 7)){
							long[] d = {(p >>> 1),  (p >> 8)};
							possibleMoves.add(d);
						}
						if (((board[bitBoardOperations.WP] & (p << 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 0)){
							long[] d = { (p << 1),  (p >> 8)};
							possibleMoves.add(d);
						}
					}
					else{
						if (((board[bitBoardOperations.BP] & (p >>> 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 7)){
							long[] d = {(p >>> 1),  (p << 8)};
							possibleMoves.add(d);
					}
						if (((board[bitBoardOperations.BP] & (p << 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 0)){
							long[] d = { (p << 1), (p << 8)};
							possibleMoves.add(d);
						}
	
					}
				}
				
				p = 0;
		    	
		    	//node to be inserted into table

				Node node = tree.new Node();
				if ((whiteMove && whiteWins) || (!whiteMove && !whiteWins)){
			    	node.board = board.clone();
			    	
			    	node.key = new Long( bitBoardOperations.hasher(pieceRandoms, node.board, whiteMove));
			    	int count = Long.bitCount((node.board[0] | node.board[7]));
					node.key = node.key | keyTag[32 - count];
			    	
			    	
			    	System.out.println("Parent KEY VAL");
					System.out.println(node.key);
					bitBoardOperations.printBoardLevel((node.key | keyTag[32 - count]));
					 
			    	
			    	node.children = new Node[1];
			    	node.children[0] = tree.new Node();
			    	node.references = 1;
			    	node.score = -10000; //bitBoardOperations.tacticalPositionValue(board, bitBoardOperations.tacticalMapMaker(board), ChessBoardWithColumnsAndRows.personality);
			    	node.level = round;
			    	node.children[0].level = round + 1;
				}
			    	
		    	//temporary node
		    	
		    	//n.board = board.clone();
				
	    		n.children = new Node[1];
	    		n.children[0] = tree.new Node();
	    		n.board = board.clone();
	    		n.key = new Long( bitBoardOperations.hasher(pieceRandoms, n.board, whiteMove));
	    		n.score = -10000;
	    		n.references = 1;
	    		//n.level = round;
	    		//n.children[0].level = round + 1;
	    		//System.out.println("BEFOREBOARD " + i);
	    		//bitBoardOperations.printBoardLevel(board[0] | board[7]);
	    		String str = split[i];
	    		int specifiedStartCol = 8;
	    		int l = 1;
	    		int endCol = 8;
	    		int endRow = 8;
	    		long end = 0;
	    		long start = 0;
	    		System.out.println(":" +str);
	    		if (pieceInitials.contains(Character.toString(str.charAt(0)))){
	    			l = l + pieceInitials.indexOf(str.charAt(0)) + 1;
	    			if (str.charAt(0) == 'O'){
	    				l = l - 1;
	    			}
	    			//System.out.println("color + L: " + (l + color));
	    			//System.out.println("L: " + l);
	    			//System.out.println(str.charAt(0));
	    			//System.out.println(pieceInitials.indexOf(str.charAt(0)));
	    			if (columnInitials.contains(Character.toString(str.charAt(2)))){
	    				specifiedStartCol = columnInitials.indexOf(str.charAt(1));
	    				endCol = columnInitials.indexOf(str.charAt(2));
	    				endRow = 8 - Character.getNumericValue(str.charAt(3));
	    				
	    				
	    				end = bitBoardOperations.bitConstants[8*(endRow) + endCol];
		    			
		    			for (int j = 0; j < possibleMoves.size(); j++){
		    				int a = Long.numberOfLeadingZeros(possibleMoves.get(j)[0] ) / 8;
	    					int b = Long.numberOfLeadingZeros(possibleMoves.get(j)[0] ) % 8;
	    					long pos = bitBoardOperations.bitConstants[8* a + b];
	    					
		    				if(possibleMoves.get(j)[1] == end && (pos & n.board[color + l]) != 0){
		    					if ((Long.numberOfLeadingZeros(possibleMoves.get(j)[0]) % 8) == specifiedStartCol ){
		    						start = possibleMoves.get(j)[0];
		    						/*
		    						if (end != possibleMoves.get(j)[1]){
		    							System.out.println("ERROR");
		    						}
		    						*/
		    					}	
		    				}
		    			}
		    			
	    			}	
	    			else{
	    				if (!str.contains("O-O")){
		    				endCol = columnInitials.indexOf(str.charAt(1));
		    				endRow = 8 - Character.getNumericValue(str.charAt(2));
		    				end = bitBoardOperations.bitConstants[8*(endRow) + endCol];
		    				
		    				for (int j = 0; j < possibleMoves.size(); j++){
		    					int a = Long.numberOfLeadingZeros(possibleMoves.get(j)[0] ) / 8;
		    					int b = Long.numberOfLeadingZeros(possibleMoves.get(j)[0] ) % 8;
		    					long pos = bitBoardOperations.bitConstants[8* a + b];
			    				if(possibleMoves.get(j)[1] == end && (pos & n.board[color + l]) != 0){
			    					start = possibleMoves.get(j)[0];
			    					/*
			    					if (end != possibleMoves.get(j)[1]){
		    							System.out.println("ERROR");
		    						}
		    						*/
			    				}
			    			}
	    				}
	    				else{
	    					//l = 6; 
	    					if (str.equals("O-O")){
	    						endCol = 6;
	    						int startRow = 8;
	    						int startCol = 4;
	    						if (whiteMove){
	    							startRow = endRow = 7;
	    						}
	    						else{
	    							endRow = startRow = 0;
	    						}
	    						
	    						start = bitBoardOperations.bitConstants[8*startRow + startCol];
	    						end = bitBoardOperations.bitConstants[8*endRow + endCol];
	    					}
	    					else{
	    						endCol = 2;
	    						int startRow = 8;
	    						int startCol = 4;
	    						if (whiteMove){
	    							endRow = 7;
	    							startRow = endRow;
	    						}
	    						else{
	    							endRow = 0;
	    							startRow = endRow;
	    						}
	    						
	    						start = bitBoardOperations.bitConstants[8*startRow + startCol];
	    						end = bitBoardOperations.bitConstants[8*endRow + endCol];
	    						
	    					}
	    				}
	    	
	    			}
	    			
	    		}	
	    		
	    		else{
	    			//System.out.println("color + L: " + (l + color));
	    			//System.out.println("L: " + l);
	    			//System.out.println(str.charAt(0));
	    			//System.out.println(pieceInitials.indexOf(str.charAt(0)));
	    			if (columnInitials.contains(Character.toString(str.charAt(0))) && columnInitials.contains(Character.toString(str.charAt(1)))){
	    				specifiedStartCol = columnInitials.indexOf(str.charAt(0));
	    				endCol = columnInitials.indexOf(str.charAt(1));
	    				endRow = 8 - Character.getNumericValue(str.charAt(2));
	    				
	    				end = bitBoardOperations.bitConstants[8*(endRow) + endCol];
		    			
		    			for (int j = 0; j < possibleMoves.size(); j++){
		    				int a = Long.numberOfLeadingZeros(possibleMoves.get(j)[0] ) / 8;
	    					int b = Long.numberOfLeadingZeros(possibleMoves.get(j)[0]) % 8;
	    					long pos = bitBoardOperations.bitConstants[8* a + b];
		    				if(possibleMoves.get(j)[1] == end && (pos & n.board[color + l]) != 0){
		    					if ((Long.numberOfLeadingZeros(possibleMoves.get(j)[0]) % 8) == specifiedStartCol ){
		    						start = possibleMoves.get(j)[0];
		    						/*
		    						if (end != possibleMoves.get(j)[1]){
		    							System.out.println("ERROR");
		    						}
		    						*/
		    					}	
		    				}
		    			}
		    	
	    			}
	    			else{
	    				//System.out.println(i);
	    				//System.out.println(str.charAt(0));
	    				endCol = columnInitials.indexOf(str.charAt(0));
	    				endRow = 8 - Character.getNumericValue(str.charAt(1));
	    				//System.out.println(endRow);
	    				end = bitBoardOperations.bitConstants[8*(endRow) + endCol];
	    				
	    				for (int j = 0; j < possibleMoves.size(); j++){
	    					int a = Long.numberOfLeadingZeros(possibleMoves.get(j)[0] ) / 8;
	    					int b = Long.numberOfLeadingZeros(possibleMoves.get(j)[0] ) % 8;
	    					long pos = bitBoardOperations.bitConstants[8* a + b];
		    				if(possibleMoves.get(j)[1] == end && (pos & n.board[color + l]) != 0){
		    					start = possibleMoves.get(j)[0];
		    					/*
		    					if (end != possibleMoves.get(j)[1]){
	    							System.out.println("ERROR");
	    						}
	    						*/
		    				}
		    			}
	    				
	    			}
	    		}
	    	//	System.out.println("STARTBOARD " + i);
	    		//bitBoardOperations.printBoardLevel(start);
	    		//System.out.println("ENDBOARD " + i);
	    		//bitBoardOperations.printBoardLevel(end);
	    		
	    		if (start == board[bitBoardOperations.WK]){
					if ((start >> 2) == end){
						board[bitBoardOperations.W] = (bitBoardOperations.bitConstants[63]
								^ (board[bitBoardOperations.W] ^ bitBoardOperations.bitConstants[61]));
						board[bitBoardOperations.WR] = (bitBoardOperations.bitConstants[63]
								^ (board[bitBoardOperations.WR] ^ bitBoardOperations.bitConstants[61]));
						key = (key ^ pieceRandoms[64*(bitBoardOperations.WR - 2) + 63]) 
								^ pieceRandoms[64*(bitBoardOperations.WR - 2) + 61];
					}
					if ((start << 2) == end){
						board[bitBoardOperations.W] =(bitBoardOperations.bitConstants[56]
								^ (board[bitBoardOperations.W] ^ bitBoardOperations.bitConstants[59]));
						board[bitBoardOperations.WR] = (bitBoardOperations.bitConstants[56]
								^ (board[bitBoardOperations.WR] ^ bitBoardOperations.bitConstants[59]));
						key = (key ^ pieceRandoms[64*(bitBoardOperations.WR - 2) + 56]) 
								^ pieceRandoms[64*(bitBoardOperations.WR - 2) + 59];
					}
				}
				if (start == board[bitBoardOperations.BK]){
					if ((start >> 2) == end){
						board[bitBoardOperations.B] = (bitBoardOperations.bitConstants[7]
								^ (board[bitBoardOperations.B] ^ bitBoardOperations.bitConstants[5]));
						board[bitBoardOperations.BR] = (bitBoardOperations.bitConstants[7]
								^ (board[bitBoardOperations.BR] ^ bitBoardOperations.bitConstants[5]));
						key = (key ^ pieceRandoms[64*(bitBoardOperations.BR - 2) + 7]) 
								^ pieceRandoms[64*(bitBoardOperations.BR - 2) + 5];
					}
					if ((start << 2) == end){
						board[bitBoardOperations.B] = (bitBoardOperations.bitConstants[0]
								^ (board[bitBoardOperations.B] ^ bitBoardOperations.bitConstants[3]));
						board[bitBoardOperations.BR] = (bitBoardOperations.bitConstants[0]
								^ (board[bitBoardOperations.BR] ^ bitBoardOperations.bitConstants[3]));
						key = (key ^ pieceRandoms[64*(bitBoardOperations.BR - 2) + 0]) 
								^ pieceRandoms[64*(bitBoardOperations.BR - 2) + 3];
					}
				}
	    		
	    		
				
				n = bitBoardOperations.promotionCheck(start, end, n, whiteMove);
				n = bitBoardOperations.bitPromotionCheck(start, end, n, n.board, whiteMove);
				
				int s = Long.numberOfLeadingZeros(start);
				int e = Long.numberOfLeadingZeros(end);
				
				if (whiteMove){
					board[bitBoardOperations.W] = (start ^ (board[bitBoardOperations.W] ^ end));
					for (int k = 1; k < 7; k++){
						if ((start & board[k]) != 0){
							board[k] = ((board[k] ^ end) ^ start);
							key = ((key ^ pieceRandoms[64*(k - 1)  + s]) ^ pieceRandoms[64*(k - 1) + e]);
							key = key ^ pieceRandoms[768];
						
						}
					}
					if ((board[bitBoardOperations.B] & end) != 0){
						board[bitBoardOperations.B] = (board[bitBoardOperations.B] ^ end);
						for (int k = 8; k < 14; k++){
							if ((end & board[k]) != 0){
								board[k] = (board[k] ^ end);
								key =  (key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 2) + e]) + bitBoardOperations.bitConstants[4];
								//score = score - bitBoardOperations.pieceVals[k];
							}
						}
					}					
				}
				else{
					board[bitBoardOperations.B] = (start ^ (board[bitBoardOperations.B] ^ end));
					for (int k = 8; k < 14; k++){
						if ((start & board[k]) != 0){
							board[k] = ((board[k] ^ end ) ^ start);
							key = ((key ^ pieceRandoms[64*(k - 2) + s]) ^ pieceRandoms[64*(k - 2) + e]);
							key = key ^ pieceRandoms[768];
						}
					}
					if ((board[bitBoardOperations.W] & end) != 0){
						board[bitBoardOperations.W] = (board[bitBoardOperations.W] ^ end);
						for (int k = 1; k < 7; k++){
							if ((end & board[k]) != 0){
								board[k] = (board[k] ^ end);
								key = (key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 1) + e]) + bitBoardOperations.bitConstants[4];
								//score = score - bitBoardOperations.pieceVals[k];
							}
						}
					}					
				}
			//	System.out.println("AFTERBOARD " + i);
				if(((board[0] | board[7]) & start) != 0){
					System.out.println("ERROR1");
				}
				if(((board[0] | board[7]) & end) == 0){
					System.out.println("ERROR2");
				}
				
			//	bitBoardOperations.printBoardLevel(board[0] | board[7]);
	    	//	System.out.println("er: " + endRow+ ", ec: " + endCol);
				n.children[0].board = board.clone();
				n.children[0].key = new Long( bitBoardOperations.hasher(pieceRandoms, n.children[0].board, !whiteMove));
				n.children[0].score = -10000;
				n.children[0].references = 1;
				n.children[0].level = round + 1;
				//n.children[0].key = key.longValue();
				
				if (whiteWins){	
					 if (whiteMove){
						 System.out.println("yes: " + i);
						
						 node.children[0].board = board.clone();
						 System.out.println("level: " + node.level);
						 bitBoardOperations.printBoardLevel(node.board[0] | node.board[7]);
						 System.out.println("level: " + node.children[0].level);
						 bitBoardOperations.printBoardLevel(node.children[0].board[0] | node.children[0].board[7]);
						 
						// System.out.println("SameBoard");
						 //System.out.println(Arrays.equals(node.children[0].board,node.board));
						 
						 
						 node.children[0].key = new Long(bitBoardOperations.hasher(pieceRandoms, node.children[0].board, false));
						 
						 int count = Long.bitCount((node.children[0].board[0] | node.children[0].board[7]));
						 node.children[0].key = node.children[0].key | keyTag[32 - count];
						 bitBoardOperations.printBoardLevel((node.children[0].key | keyTag[32 - count]));
						 System.out.println("CHILD KEY VAL");
						 System.out.println(node.children[0].key.longValue());
						 
						 
						 node.children[0].score = -10000;
						 node.children[0].references = 1;
						 node.children[0].level =  round + 1;
						 
						
						 Node c = boards.put(node.key, node);
						 if (c != null){
							 System.out.println("ERROR3");
						 }
						 Node x = boards.put(node.children[0].key, node.children[0]);
						 if (x != null){
							 System.out.println("ERROR4");
						 }
						 System.out.println("Ket Status");
						 System.out.println((boards.get(node.children[0].key) == boards.get(node.key)));
						 System.out.println("child children null");
						 System.out.println((boards.get(node.children[0].key).children == null));
						 
					 }
					 
				}
				 
				else{	
					 if (!whiteMove){
						node.children[0].board = board;
						node.children[0].key = bitBoardOperations.hasher(pieceRandoms, board, whiteMove);
						node.children[0].score = 10000;
						Node c = boards.put(key, node);
						if (c != null){
							System.out.println("ERROR3");
						}
					 }
				 }
				 
				n = n.children[0];
				
				round++;
				
				if ( (((start & board[bitBoardOperations.WP]) != 0) && (end == (start << 16)))
						|| ((start & board[bitBoardOperations.BP]) != 0) && (end == (start >> 16))){
					p = end; 
				}
				 
	    	}
	    	
	    }
	    tree.root.printNodes(tree.root, 0);
	    
	    Enumeration<Long> e = boards.keys();
	    System.out.println("board size : " + boards.size());
	    int y = 1;
	    while (e.hasMoreElements()){
	    	//System.out.println(y);
	    	Node g = boards.get(e.nextElement());
	    	System.out.println("depth: " + y + ", tree score: " + g.score + ", tree key: " + g.key + "ref: " + g.references+ "level: " + g.level  );
	    	 bitBoardOperations.printBoardLevel(g.key);
	    	/*
	    	System.out.println((g == null));
	    	System.out.println((g.children == null));
	    	if (g.children != null){
		    	System.out.println((g.children[0] == null));
		    	System.out.println((g.children[0].children == null));
		    	if (g.children[0].children != null){
		    		System.out.println((g.children[0].children[0] == null));
		    	}
	    	}
	    	*/
	    	y++;
	    }
	    
	   save();
    }

	
	
	
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
	    

	  public static void main(String[] args) throws ClassNotFoundException, IOException {
		  addToTable();
	  }
	  
	  
	  
}
