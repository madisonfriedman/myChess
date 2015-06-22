package chess;

import java.util.Arrays;

public class intBoardOperations {
	
	public static final int whiteQueen = 1, whiteKing = 2,
            whiteRook = 3, whiteKnight = 4, whiteBishop = 5, whitePawn = 6,
            blackQueen = -1, blackKing = -2,
            blackRook = -3, blackKnight = -4, blackBishop = -5, blackPawn = -6;
	
	public static final int []  pieceNames = {whiteQueen, whiteKing,
            whiteRook, whiteKnight, whiteBishop, whitePawn,
            blackQueen, blackKing,
            blackRook, blackKnight, blackBishop, blackPawn};
	
	public static int [] pieceValues = {0, 900, 100000, 500, 300, 325, 1, 1, 300, 325, 500, 100000, 900};

	public static int tacticalPositionValue (int [][] intBoardConfig, int [][] tacticalBoard, int [] personality){
    	int c = 0;
    	
    	for (int i = 0; i < 8; i++){
    		for (int j = 0; j < 8; j++){
    			if(intBoardConfig[i][j] > 0){
    				c = c + pieceValues[intBoardConfig[i][j]] + ((8 - i) + (8 - Math.abs((7 - j) - j)));
    				if (tacticalBoard[i][j] < 0){
    					c = c - personality[0]*pieceValues[intBoardConfig[i][j]];
    				}
    			}
    			
    			if(intBoardConfig[i][j] < 0){
    				c = c - pieceValues[-intBoardConfig[i][j]] - ((8 - i) + (8 - Math.abs((7 - j) - j)));
    				if (tacticalBoard[i][j] > 0){
    					c = c + personality[0]*pieceValues[-intBoardConfig[i][j]];
    				}
    			}
    			
    			if(intBoardConfig[i][j] == 0){
    				if (tacticalBoard[i][j] > 0){
    	    			c = c + personality[2]*((8 - i) + (8 - Math.abs((7 - j) - j)));
    	    		}
    				if (tacticalBoard[i][j] < 0){
    	    			c = c - personality[2]*(i + (8 - Math.abs((7 - j) - j)));
    	    		}
    			}
    		}
    	}
       	return c; 	
	}
    
    public static int [][] tacticalMapMaker(int [][] intBoardConfig){
    	int [][] tacticalBoard = new int [8][8];
    	
    	int [][] whiteMoves = completeMoveGen(intBoardConfig, 1, true);
    	int [][] blackMoves = completeMoveGen(intBoardConfig, 2, true);
    	
    	for (int i = 1; i < whiteMoves[0][0]; i++){
    		tacticalBoard[whiteMoves[i][2]][whiteMoves[i][3]]++;
    	}
    	for (int i = 1; i < blackMoves[0][0]; i++){
    		tacticalBoard[blackMoves[i][2]][blackMoves[i][3]]--;
    	}
    	
    	return tacticalBoard;
    }
	
	
    public static int[][] completeMoveGen (int [][] intBoardConfig, int moveNum, boolean tactical){
    	int [][] possibleMoves = new int [50][4];
    	int c = 1;
    	
    	int color = 1;
    	if (moveNum % 2 == 0){
    		color = -1;
    	}
    	
    	//long bal = 0b010010;
    	
    	for (int i = 0; i < 8; i++){
    		for (int j = 0; j < 8; j++){
	    		if (intBoardConfig[i][j] / color > 0){
	    			int [][] temp = null;
	    			if (intBoardConfig[i][j] == whitePawn*color){
	    				temp = AIpawnMoves(intBoardConfig, i, j, tactical);
	    			}
	    				
	    			if (intBoardConfig[i][j] == whiteKnight*color){
	    				temp = AIknightMoves(intBoardConfig, i, j, tactical);
	    			}
	    			
	    			if (intBoardConfig[i][j] == whiteBishop*color){
	    				temp = AIbishopMoves(intBoardConfig, i, j, tactical);
	    			}
	    				
	    			if (intBoardConfig[i][j] == whiteRook*color){
	    				temp = AIrookMoves(intBoardConfig, i, j, tactical);
	    			}
	    				
	    			if (intBoardConfig[i][j] == whiteQueen*color){
	    				temp = AIqueenMoves(intBoardConfig, i, j, tactical);
	    			}
	    				
	    			if (intBoardConfig[i][j] == whiteKing*color){
	    				temp = AIkingMoves(intBoardConfig, i, j, tactical);
	    			}
    				
	    			for (int k = 1; k < temp[0][0]; k++){
	    				possibleMoves[c][0] = i;
	    				possibleMoves[c][1] = j;
	    				possibleMoves[c][2] = temp[k][0];
	    				possibleMoves[c][3] = temp[k][1];
	    				c++;
	    			}
	    		}		
    		}
    	}
    	possibleMoves[0][0] = c;
    	return possibleMoves;
    }
    
    private static int [][] AIrookMoves(int [][] intBoardConfig, int startRow, int startCol, boolean tactical){
    	int temp = intBoardConfig[startRow][startCol];
    	
		int color = -1;
		if (temp > 0){
			color = 1;
		}
		
    	int [][] endCoordinates = new int [15][2];
    	
    	int c = 1;
    	
    	if (!tactical){
	    	//right
	    	for (int i = startCol + 1; i < 8; i++){
	    		if (intBoardConfig[startRow][i] / color < 0 || intBoardConfig[startRow][i] == 0) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
	    		if (intBoardConfig[startRow][i] / color < 0 || intBoardConfig[startRow][i] == 0) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
	    		if ( intBoardConfig[i][startCol] / color < 0 || intBoardConfig[i][startCol] == 0) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
	    		if ( intBoardConfig[i][startCol] / color < 0 || intBoardConfig[i][startCol] == 0) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
    	}
    	
    	if (tactical){
	    	//right
	    	for (int i = startCol + 1; i < 8; i++){
		    	endCoordinates[c][0] = startRow;
		    	endCoordinates[c][1] = i;
		    	c++;
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
		    	endCoordinates[c][0] = startRow;
		    	endCoordinates[c][1] = i;
		    	c++;
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
    	}
	    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    
    private static int [][] AIbishopMoves(int [][] intBoardConfig, int startRow, int startCol, boolean tactical){
    	
    	int temp = intBoardConfig[startRow][startCol];
    	
		int color = -1;
		if (temp > 0){
			color = 1;
		}
		
    	int [][] endCoordinates = new int [14][2];
    	
    	int c = 1;
    	
    	if (!tactical){
	    	//down right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (intBoardConfig[startRow + i][startCol + i] / color < 0 || intBoardConfig[startRow + i][startCol + i] == 0) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow + i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (intBoardConfig[startRow - i][startCol + i] / color < 0 || intBoardConfig[startRow - i][startCol + i] == 0) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow - i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol - i < 0)){
	    			break;
	    		}
	    		if (intBoardConfig[startRow + i][startCol - i] / color < 0 || intBoardConfig[startRow + i][startCol - i] == 0) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow + i][startCol - i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol - i < 0)){
	    			break;
	    		}
	    		
	    		if (intBoardConfig[startRow - i][startCol - i] / color < 0 || intBoardConfig[startRow - i][startCol - i] == 0) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow - i][startCol - i] != 0){
	    			break;
	    		}
	    	}
    	}
    	
    	if (tactical){
	    	//down right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol + i > 7)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow + i;
		    	endCoordinates[c][1] = startCol + i;
		    	c++;
	    		if (intBoardConfig[startRow + i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol + i > 7)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow - i;
		    	endCoordinates[c][1] = startCol + i;
		    	c++;
	    		if (intBoardConfig[startRow - i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol - i < 0)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow + i;
		    	endCoordinates[c][1] = startCol - i;
		    	c++;
	    		if (intBoardConfig[startRow + i][startCol - i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol - i < 0)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow - i;
		    	endCoordinates[c][1] = startCol - i;
		    	c++;
	    		if (intBoardConfig[startRow - i][startCol - i] != 0){
	    			break;
	    		}
	    	}
    	}
    	
	    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    
    private static int [][] AIknightMoves(int [][] intBoardConfig, int startRow, int startCol, boolean tactical){
    	int temp = intBoardConfig[startRow][startCol];
    	
		int color = -1;
		if (temp > 0){
			color = 1;
		}
    	int [][] endCoordinates = new int [9][2];
    	
    	int c = 1; 
    	
    	int [][] moves = {{1,2}, {2,1}, {-1,-2}, {-2, -1},
    						{-1, 2}, {1, -2}, {-2, 1}, {2, -1}};
    	int x = 0;
    	int y = 0;
    	
    	if (!tactical){
	    	for (int i = 0; i < 8; i++){
	    		x = startRow + moves[i][0];
	    		y = startCol + moves[i][1];
	    		if (((x > -1) && (x < 8)) && ((y > -1) && (y < 8))){
		    		if (intBoardConfig[x][y] / color < 0 || intBoardConfig[x][y] == 0){
		    			endCoordinates[c][1] = y;
		    			endCoordinates[c][0] = x;
		    			c++;
		    		}
	    		}
	    	}
    	}
    	
    	if (tactical){
	    	for (int i = 0; i < 8; i++){
	    		x = startRow + moves[i][0];
	    		y = startCol + moves[i][1];
	    		if (((x > -1) && (x < 8)) && ((y > -1) && (y < 8))){
		    		endCoordinates[c][1] = y;
		    		endCoordinates[c][0] = x;
		    		c++;
	    		}
	    	}
    	}
    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    
    private static int [][] AIkingMoves(int [][] intBoardConfig, int startRow, int startCol, boolean tactical){
    	int temp = intBoardConfig[startRow][startCol];
    	
		int color = -1;
		if (temp > 0){
			color = 1;
		}
		
    	int [][] endCoordinates = new int [11][2];
    	
    	int c = 1; 
    	
    	int [][] moves = {{1, 1}, {-1, -1}, {1, 0}, {-1, 0},
    						{0, 1}, {0, -1}, {1, -1}, {-1, 1}};
    	
    	int x = 0;
    	int y = 0;
    	
    	if (!tactical){
	    	for (int  i = 0; i < 8; i++){
	    		x = startRow + moves[i][1];
	    		y = startCol + moves[i][0];
	    		if (((x > -1) && (x < 8)) && ((y > -1) && (y < 8))){
		    		if (intBoardConfig[x][y] / color < 0 || intBoardConfig[x][y] == 0){
		    			endCoordinates[c][0] = x;
		    			endCoordinates[c][1] = y;
		    			c++;
		    		}
	    		}
	    	}
	    	
	    	if (intBoardConfig[0][5] == blackKing){
		    	if (temp == blackKing && ChessBoardWithColumnsAndRows.blackRightCastle == false){
		    		if (intBoardConfig[startRow][startCol + 1] == 0 && intBoardConfig[startRow][startCol + 2] == 0){
		    			endCoordinates[c][0] = 0;
		    			endCoordinates[c][1] = 6;
		    			c++;
		    		}
		    	}
		    	if (temp == blackKing && ChessBoardWithColumnsAndRows.blackLeftCastle == false){
		    		if (intBoardConfig[startRow][startCol - 1] == 0 && intBoardConfig[startRow][startCol - 2] == 0){
		    			endCoordinates[c][0] = 0;
		    			endCoordinates[c][1] = 2;
		    			c++;
		    		}
		    	}
	    	}
	    	
	    	if (intBoardConfig[7][5] == whiteKing){
		    	if (temp == whiteKing && ChessBoardWithColumnsAndRows.whiteRightCastle == false){
		    		if (intBoardConfig[startRow][startCol + 1] == 0 && intBoardConfig[startRow][startCol + 2] == 0){
		    			endCoordinates[c][0] = 7;
		    			endCoordinates[c][1] = 6;
		    			c++;
		    		}
		    	}
		    	if (temp == whiteKing && ChessBoardWithColumnsAndRows.whiteLeftCastle == false){
		    		if (intBoardConfig[startRow][startCol - 1] == 0 && intBoardConfig[startRow][startCol - 2] == 0){
		    			endCoordinates[c][0] = 7;
		    			endCoordinates[c][1] = 2;
		    			c++;
		    		}
		    	}
	    	}
	    	
    	}
    	
    	if (tactical){
    		for (int  i = 0; i < 8; i++){
	    		x = startRow + moves[i][1];
	    		y = startCol + moves[i][0];
	    		if (((x > -1) && (x < 8)) && ((y > -1) && (y < 8))){
		    		endCoordinates[c][0] = x;
		    		endCoordinates[c][1] = y;
		    		c++;
	    		}
	    	}
    	}
    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    
    private static int [][] AIpawnMoves(int [][] intBoardConfig, int startRow, int startCol, boolean tactical){
    	
    	int temp = intBoardConfig[startRow][startCol];
    	
		int color = -1;
		if (temp > 0){
			color = 1;
		}
		
    	int [][] endCoordinates = new int [5][2];
    	
    	int c = 1;
    	int x = 0;
    	int y = 0;
    	
    	int [][] whiteMoves = {{-2, 0}, {-1, 0}, {-1, -1}, {-1, 1}};
    	int [][] blackMoves = {{2, 0}, {1, 0}, {1, 1}, {1, -1}};
    	
    	int initialRow = 6;
    	int [][] moves = whiteMoves;
    	int diff = -1;
    	
    	if (color < 0){
    		moves = blackMoves;
    		initialRow = 1;
    		diff = 1;
    	}
    	
    	if (!tactical){
	    	if ((startRow == initialRow) && 
	    			(intBoardConfig[startRow + diff][startCol] == 0
	    			&& intBoardConfig[startRow + 2*diff][startCol] == 0)){
	    		endCoordinates[c][0] = startRow + moves[0][0];
	    		endCoordinates[c][1] = startCol;
	    		c++;
	    	}
    	
    	
	    	if (intBoardConfig[startRow + diff][startCol] == 0
	    			 && startRow < 7 && startRow > 0){
	    		endCoordinates[c][0] = startRow + moves[1][0];
	    		endCoordinates[c][1] = startCol;
	    		c++;
	    	}
    	}
    	
    	for (int i = 2; i < 4; i++){
    		x = startCol + moves[i][1];
    		y = startRow + moves[i][0];
    		if (((x > -1) && (x < 8)) && ((y > -1) && (y < 8))){
	    		if (intBoardConfig[y][x] / color < 0){
	    			endCoordinates[c][0] = y;
	        		endCoordinates[c][1] = x;
	        		c++;
	    		}
    		}
    	}
    	
    	
    	if (tactical){
    		for (int i = 2; i < 4; i++){
        		x = startCol + moves[i][1];
        		y = startRow + moves[i][0];
        		if (((x > -1) && (x < 8)) && ((y > -1) && (y < 8))){
        			endCoordinates[c][0] = y;
	        		endCoordinates[c][1] = x;
	        		c++;	
        		}
        	}
    	}
    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    
    
    private static int [][] AIqueenMoves (int [][] intBoardConfig, int startRow, int startCol, boolean tactical){
    	
    	int temp = intBoardConfig[startRow][startCol];
    	
		int color = -1;
		if (temp > 0){
			color = 1;
		}
		
    	int [][] endCoordinates = new int [28][2];
    	
    	int c = 1;
    	
    	if (!tactical){
	    	//right
	    	for (int i = startCol + 1; i < 8; i++){
	    		if (intBoardConfig[startRow][i] / color < 0 || intBoardConfig[startRow][i] == 0) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
	    		if (intBoardConfig[startRow][i] / color < 0 || intBoardConfig[startRow][i] == 0) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
	    		if  (intBoardConfig[i][startCol] / color < 0 || intBoardConfig[i][startCol] == 0) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
	    		if ( intBoardConfig[i][startCol] / color < 0 || intBoardConfig[i][startCol] == 0) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (intBoardConfig[startRow + i][startCol + i] / color < 0 || intBoardConfig[startRow + i][startCol + i] == 0) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow + i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (intBoardConfig[startRow - i][startCol + i] / color < 0 || intBoardConfig[startRow - i][startCol + i] == 0) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow - i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol - i < 0)){
	    			break;
	    		}
	    		if (intBoardConfig[startRow + i][startCol - i] / color < 0 || intBoardConfig[startRow + i][startCol - i] == 0) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow + i][startCol - i] != 0 ){
	    			break;
	    		}
	    	}
	    	
	    	//up left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol - i < 0)){
	    			break;
	    		}
	    		if (intBoardConfig[startRow - i][startCol - i] / color < 0 || intBoardConfig[startRow - i][startCol - i] == 0) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (intBoardConfig[startRow - i][startCol - i] != 0){
	    			break;
	    		}
	    	}
    	}
	    
    	if (tactical){
	    	//right
	    	for (int i = startCol + 1; i < 8; i++){
		    	endCoordinates[c][0] = startRow;
		    	endCoordinates[c][1] = i;
		    	c++;
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
		    	endCoordinates[c][0] = startRow;
		    	endCoordinates[c][1] = i;
		    	c++;
	    		if (intBoardConfig[startRow][i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (intBoardConfig[i][startCol] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol + i > 7)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow + i;
		    	endCoordinates[c][1] = startCol + i;
		    	c++;
	    		if (intBoardConfig[startRow + i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol + i > 7)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow - i;
		    	endCoordinates[c][1] = startCol + i;
		    	c++;
	    		if (intBoardConfig[startRow - i][startCol + i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//down left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol - i < 0)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow + i;
		    	endCoordinates[c][1] = startCol - i;
		    	c++;

	    		if (intBoardConfig[startRow + i][startCol - i] != 0){
	    			break;
	    		}
	    	}
	    	
	    	//up left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol - i < 0)){
	    			break;
	    		}
		    	endCoordinates[c][0] = startRow - i;
		    	endCoordinates[c][1] = startCol - i;
		    	c++;
	    		if (intBoardConfig[startRow - i][startCol - i] != 0){
	    			break;
	    		}
	    	}
    	}
	    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }

    
    
    public static int AICalculator(int pieceTaken){
    	int diff = 0;

		if (Math.abs(pieceTaken) == whitePawn){
			diff = 100;
		}
		if (Math.abs(pieceTaken) == whiteKnight || 
				Math.abs(pieceTaken) == whiteBishop){
			diff = 300;
		};
		if (Math.abs(pieceTaken) == whiteRook){
			diff = 500;
		}
		if (Math.abs(pieceTaken) == whiteQueen){
			diff = 900;
		}
		if (Math.abs(pieceTaken) == whiteKing){
			diff = 1000000;
		}
		
		if (pieceTaken > 0){
			diff = diff*(-1);
		}
		
		return diff;
    }
    
    
    public static int promotionCheck (int endRow, int tempPiece){
    	if (endRow == 7){
    		if (tempPiece == blackPawn){
    			return blackQueen;
    		}
    	}
    	
    	if (endRow == 0){
    		if (tempPiece == whitePawn){
    			return whiteQueen;
    		}
    	}
    	
    	return tempPiece;
    	
    }
    
    public static int differenceCalculator (int [][] board){
    	int difference = 0;
    	for (int i = 0; i < 8;i++){
    		for (int j = 0; j < 8; j++){
				if (board[i][j] > 0){
					difference = difference + pieceValues[board[i][j]];
				}
				if (board[i][j] < 0){
					difference = difference - pieceValues[(-1)*board[i][j]];
				}
    		}
    	}
    	return difference;
    }
    
    /*
    public static long [] bitBoardMaker(int [][] intBoard){
    	
    	long [] bitBoard = new long[13];
    	
    	for (int i = 0; i < 8;i++){
    		for (int j = 0; j < 8; j++){
    			if (intBoard[i][j] == whitePawn){
    				
    			}
    		}
    	}
    }
    */
    
    
    
}
