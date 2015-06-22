package chess;

import java.io.Serializable;
import java.util.Comparator;
import chess.AIv2Tree.Node;

public class referenceComparator implements Comparator <Node>, Serializable {
	public int compare (Node x, Node y){
		/*
		if (x.references <= y.references){
			return -1;
		}
		
		if (x.references > y.references){
			return 1;
		}
		*/
		Integer a = x.references;
		Integer b = y.references;
		
		return b.compareTo(a);		//		return a.compareTo(b);
	}
}
