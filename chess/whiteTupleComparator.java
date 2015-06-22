package chess;

import java.io.Serializable;
import java.util.Comparator;

import chess.AIv2Tree.Node;
import chess.AIv2Tree.moveScoreTuple;

public class whiteTupleComparator implements Comparator <moveScoreTuple>, Serializable {
	
	public int compare (moveScoreTuple x, moveScoreTuple y){
		/*
		if (x.references <= y.references){
			return -1;
		}
		
		if (x.references > y.references){
			return 1;
		}
		*/
		Integer a = x.score;
		Integer b = y.score;
		
		return b.compareTo(a);		//		return a.compareTo(b);
	}

}
