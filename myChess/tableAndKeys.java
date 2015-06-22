package chess;
import java.io.Serializable;
import java.util.Hashtable;

import chess.AIv2Tree.Node;

public class tableAndKeys implements Serializable{

	public Hashtable<Long, Node> boards = null;
	 public long [] pieceRandoms = null;
	 
	 public void setObj(Hashtable<Long, Node> boards, long [] pieceRandoms){
		 this.pieceRandoms = pieceRandoms;
		 this.boards = boards;
	 }
}
