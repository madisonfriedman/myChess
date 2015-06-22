package chess;

import java.io.Serializable;
import java.util.Comparator;

import chess.AIv2Tree.Node;

public class scoreComparator2 implements Comparator <Node>, Serializable {
	public int compare (Node x, Node y){
		if (x.score < y.score){
			return -1;
		}
		
		if (x.score > y.score){
			return 1;
		}
		
		return 0;
	}
}