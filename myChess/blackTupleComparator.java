package chess;

import java.io.Serializable;
import java.util.Comparator;
import chess.AIv2Tree.moveScoreTuple;

public class blackTupleComparator implements Comparator <moveScoreTuple>, Serializable {
	
	public int compare (moveScoreTuple x, moveScoreTuple y){
		/*
		if (x.references <= y.references){
			return -1;
		}
		
		if (x.references > y.references){
			return 1;
		}
		*/
		Integer b = x.score;
		Integer a = y.score;
		
		return b.compareTo(a);		//		return a.compareTo(b);
	}

}
