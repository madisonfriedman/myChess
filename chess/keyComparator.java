package chess;

import java.io.Serializable;
import java.util.Comparator;
import chess.AIv2Tree.Node;

public class keyComparator implements Comparator <chess.AIv2Tree.Node>, Serializable{
	public int compare (Node x, Node y){
		if (x.key <= y.key){
			return 1;		//-1
		}
		
		if (x.key > y.key){
			return -1;		//1
		}
		
		return 0;
	}

	
}
