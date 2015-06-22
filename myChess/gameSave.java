package chess;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Stack;

import chess.AIv2Tree.Node;

public class gameSave implements Serializable{
	 public AIv2Tree tree = null;
	 public int moveNum = 0;
	 public String [][] chessBoardConfig = null;
	 public Stack<String> whitePiecesTaken = null;
	 public Stack<String> blackPiecesTaken = null;
	 public int [][] moveHistory = null;
	 public boolean [] pieceTaken = null;
	 public long [] pieceRandoms = null;
	 public HashMap<Long, Node> boards = null;
	 public int [] personality = null;
	 public int piecesTaken = 0;
	 public int pointDifference = 0;
	 
	 public void setObj (AIv2Tree tree, int moveNum, String [][] chessBoardConfig, Stack<String> whitePiecesTaken,
			 Stack<String> blackPiecesTaken, int [][] moveHistory, boolean [] pieceTaken, long [] pieceRandoms,
			 HashMap<Long, Node> boards, int [] personality, int piecesTaken, int pointDifference){
		 
		this.tree = tree;
		this.moveNum = moveNum;
		this.chessBoardConfig = chessBoardConfig;
		this.whitePiecesTaken = whitePiecesTaken;
		this.blackPiecesTaken = blackPiecesTaken;
		this.moveHistory = moveHistory;
		this.pieceTaken = pieceTaken;
		this.pieceRandoms = pieceRandoms;
		this.boards = boards;
		this.personality = personality;
		this.piecesTaken = piecesTaken; 
		this.pointDifference = pointDifference;
		 
	 }

	
	 
	 
}
