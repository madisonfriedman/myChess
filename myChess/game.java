package chess;
import java.io.Serializable;
public class game implements Serializable{
	String moveSequence;
	String whitePlayer;
	String blackPlayer;
	String venue;
	boolean whiteWins;
	
	public void addGame(String moveseq, String whitePlayer, String blackPlayer, String venue, boolean whiteWins){
		  this.moveSequence = moveseq;
		  this.whitePlayer = whitePlayer;
		  this.blackPlayer = blackPlayer;
		  this.venue = venue;
		  this.whiteWins = whiteWins;
	}
	
	
}
