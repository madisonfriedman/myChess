package chess;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.*;
import javax.swing.border.*;

import java.net.URL;
import java.nio.file.Files;

import javax.imageio.ImageIO;

import chess.AIv2Tree.Node;
import chess.AIv2Tree.moveScoreTuple;



public class ChessBoardWithColumnsAndRows implements Serializable{
	
	private JLabel moveLabel;
	private JTextField moveField;
	private JButton button;
	private static JComboBox files = new JComboBox();
	
	
	public static int moveNum;
	private static int pointDifference = 0;
	
	//Toggle system checks on move validity:
	//"stupid" is a PvP game
	//"AItest" is a random AI v random AI game
	//"P1test" is toggles AI v P and AI v AI games
	
	public String PlayerType = "P1Test";
	public boolean P1test = true;
	
	public double knightDist = Math.sqrt((4.0-2.0)*(4.0-2.0) + (2.0-1.0)*(2.0-1.0));
	
	public static String[][] chessBoardConfig = new String[8][8];
	
	public static final String[] White_Starting_Row = {
		"white rook", "white knight", "white bishop", "white queen",
		"white king", "white bishop", "white knight", "white rook"
	};
	
	public static final String[] Black_Starting_Row = {
		"black rook", "black knight", "black bishop", "black queen",
		"black king", "black bishop", "black knight", "black rook"
	};
	
	public static final int whiteQueen = 1, whiteKing = 2,
            whiteRook = 3, whiteKnight = 4, whiteBishop = 5, whitePawn = 6,
            blackQueen = -1, blackKing = -2,
            blackRook = -3, blackKnight = -4, blackBishop = -5, blackPawn = -6;
	
	private final static JLabel score = new JLabel("Score is tied at " + pointDifference);

    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private static JButton[][] chessBoardSquares = new JButton[8][8];
    private static Image[][] chessPieceImages = new Image[2][6];
    private JPanel chessBoard;
    private final static JLabel message = new JLabel(
            "Chess Champ is ready to play!");
    private static final String COLS = "ABCDEFGH";
    public static final int QUEEN = 1, KING = 0,
            ROOK = 2, KNIGHT = 3, BISHOP = 4, PAWN = 5;
    public static final int[] STARTING_ROW = {
        ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK
    };
    
    public static final int BLACK = 0, WHITE = 1;
    
    public static String [] blackImages = {"black king", "black queen", "black rook", "black knight", "black bishop", "black pawn"};

    public static String [] whiteImages = {"white king", "white queen", "white rook", "white knight", "white bishop", "white pawn"};
    
    public static int [] pieceValues = {10000, 9, 5, 3, 3, 1};
    
    public static String [] pieceNames = {"king", "queen", "rook", "knight", "bishop", "pawn"};
    
    public static final int W = 0, WP = 1, WN = 2, WB = 3, WR = 4, WQ = 5, WK = 6,
			B = 7, BP = 8, BN = 9, BB = 10, BR = 11, BQ = 12, BK = 13;
    
    public static int [][] moveHistory = new int [150][4];
    
    public static boolean whiteLeftCastle = false;
    public static boolean whiteRightCastle = false;
    public static boolean blackLeftCastle = false;
    public static boolean blackRightCastle = false;
    
    static AIv2Tree tree = new AIv2Tree();
    
    public static Stack<String> whitePiecesTaken = new Stack<String>();
    public static Stack<String> blackPiecesTaken = new Stack<String>();
    
    public static boolean [] pieceTaken = new boolean[150];
    
    public static int treeSize = 0;
    
    public static int copiesCalled = 0;
     
	static long [] pieceRandoms = new long [769];
    
	static HashMap<Long, Node> boards = new HashMap<Long, Node>(100000000);
	
	static HashMap<Long, Integer> pawnStructuers = new HashMap<Long, Integer>(5000000);
	
	//static HashMap<Long, Node> boards2 = new HashMap<Long, Node>(50000000);
	
	
	
	//int centerDefendBonus = personality[0];
    
	//int ring1DefendBonus = personality[1];
	
	//int centerControlBonus = personality[2];
    
	//int ring1ControlBonus = personality[3];
	
	//int totalLandControl = personality[4];
	
	//int totalLandDefend = personality[5];

	//int blackBonus = personality[6];
	
	//int whiteBonus = personality[7];
	
	
	static int [] personality = {5, 5, 20, 10, 5, 10, 5, 2, 1};
	
	public static int hashNodesCalled = 0;
	
	public static int piecesTaken = 0;
	public static long [] keyTag = new long [32];
	
	public static Stack<Node> nodeBank = new Stack<Node>();
	
	static Comparator<Node> comparator1 = new scoreComparator1();
	static Comparator<Node> comparator2 = new scoreComparator2();
	static Comparator<Node> referenceComparator = new referenceComparator();
	static Comparator<Node> keyComparator = new keyComparator();
	
	//public static ArrayList<Node> references = new ArrayList<Node>(100000000);
	//public static ArrayList<Node> references2 = new ArrayList<Node>(50000000);
	
	public static int nodePop = 0;
	public static int nodeCreated = 0;
	public static int deletedN = 0;
	public static int tempNodeCreated = 0;
	
	public static int[]  nodesPerLevel = new int [20];
	public static int[]  nullsPerLevel = new int [10];
	public static int[]  tempNodesPerLevel = new int [30];
	public static int[]  newNodesPerLevel = new int [30];
	public static int[]  deletedNodesPerLevel = new int [30];
	
	public static long targetNodeKey = 0;
	
	public static Node [] targetNodePath = new Node[7];
	
	public static Hashtable<Long, ArrayList<ArrayList<Long[]>>> moveTable = new Hashtable<Long, ArrayList<ArrayList<Long[]>>>();
	
	static AIv2Tree tree2 = new AIv2Tree();
	
	public static long currentKey = 0;
	
	public static int difficulty = 0;
	
	public static boolean beginning = true;
	
	public static int referencedNodes = 0;
	
	public static boolean whiteMove = true;
	
	
	
    ChessBoardWithColumnsAndRows() {
        initializeGui();
    }

    public final void initializeGui() {
        // create the images for the chess pieces
        createImages();
        createMoveField();
        createFileList();
        moveButton();
        

        // set up the main GUI
        gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        Action newGameAction = new AbstractAction("New") {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
					setupNewGame();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
        };
        
        
        Action newGameAction1 = new AbstractAction("Save"){
        	
            public void actionPerformed(ActionEvent e) {
                try {
					save();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
            }
       };
        
        
        Action newGameAction2 = new AbstractAction("Load"){
        	
             public void actionPerformed(ActionEvent e) {
                 try {
					load();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
             }
        }; 
        
        tools.add(files);
        tools.add(newGameAction);
        tools.add(newGameAction1);
        tools.add(newGameAction2);
        
        /*
        tools.add(new JButton("Save")); // TODO - add functionality!
        tools.add(new JButton("Restore")); // TODO - add functionality!
        tools.addSeparator();
        tools.add(new JButton("Resign")); // TODO - add functionality!
        */
        
        tools.addSeparator();
        tools.add(message);
        tools.addSeparator();
        tools.add(score);
        tools.addSeparator();
        tools.add(moveLabel);
        tools.add(moveField);
       // tools.add(button);
        
       // JRootPane rootPane = tools.getRootPane();
        //rootPane.setDefaultButton(button);
    
      //  gui.add(new JLabel("?"), BorderLayout.LINE_START);

        chessBoard = new JPanel(new GridLayout(0, 9)) {

            /**
             * Override the preferred size to return the largest it can, in
             * a square shape.  Must (must, must) be added to a GridBagLayout
             * as the only component (it uses the parent as a guide to size)
             * with no GridBagConstaint (so it is centered).
             */
            @Override
            public final Dimension getPreferredSize() {
                Dimension d = super.getPreferredSize();
                Dimension prefSize = null;
                Component c = getParent();
                if (c == null) {
                    prefSize = new Dimension(
                            (int)d.getWidth(),(int)d.getHeight());
                } else if (c!=null &&
                        c.getWidth()>d.getWidth() &&
                        c.getHeight()>d.getHeight()) {
                    prefSize = c.getSize();
                } else {
                    prefSize = d;
                }
                int w = (int) prefSize.getWidth();
                int h = (int) prefSize.getHeight();
                // the smaller of the two sizes
                int s = (w>h ? h : w);
                return new Dimension(s,s);
            }
        };
        chessBoard.setBorder(new CompoundBorder(
                new EmptyBorder(8,8,8,8),
                new LineBorder(Color.BLACK)
                ));
        // Set the BG to be ochre
       // Color ochre = new Color(204,119,34);
      //  chessBoard.setBackground(ochre);
        JPanel boardConstrain = new JPanel(new GridBagLayout());
       // boardConstrain.setBackground(ochre);
        boardConstrain.add(chessBoard);
        gui.add(boardConstrain);

        // create the chess board squares
        Insets buttonMargin = new Insets(0, 0, 0, 0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                ImageIcon icon = new ImageIcon(
                        new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
                b.setIcon(icon);
                if ((jj % 2 == 1 && ii % 2 == 1)
                        //) {
                        || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                } else {
                    b.setBackground(Color.BLACK);
                }
                b.setOpaque(true);
                b.setBorderPainted(false);
                chessBoardSquares[jj][ii] = b;
            }
        }

        /*
         * fill the chess board
         */
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (9-(ii + 1)),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }
    
    //my methods
    
    private void createMoveField() {
    	moveLabel = new JLabel("Turn: " + moveNum + ", White's Move: ");
    	
    	final int FIELD_WIDTH = 5;
    	moveField = new JTextField(FIELD_WIDTH);
    	moveField.setText("");
    }    
    
    
    private static void createFileList(){
    	
    
    	/*
    	File f = null;
    	File[] paths;
    	files.removeAllItems();
    	f = new File("/Users/madison/Documents/workspace/chess/src/chess/saved_states");
    	paths = f.listFiles();
    	files.addItem("None");
    	for (int i = 0; i < paths.length; i++){
    		String s = paths[i].toString();
    		int index = s.lastIndexOf("/");
    		s = s.substring(index + 1);
    		
    		if (!s.contains(".DS_Store")){
    			files.addItem(s);
    		}
    	}
    	*/
    	
    }
    
    
    private void moveButton() {
    	button = new JButton("Execute");
    	class AddMoveListener implements ActionListener{
    		public void actionPerformed(ActionEvent event){
    			
    			long megaByte = 1024L * 1024L;
    			Runtime runtime = Runtime.getRuntime();
    			runtime.gc();
    		    long memory = runtime.totalMemory() - runtime.freeMemory();
    		   // System.out.println("Used memory in bytes: " + memory);
    		    System.out.println("Used memory in megabytes: " + memory/megaByte);
    		    System.out.println("Free memory in megabytes: " + (Runtime.getRuntime().maxMemory() - memory)/megaByte);
    			int lim = 150;
    		
    			
    			if (P1test == true){
    				lim = 1;
    				if (moveNum %2 == 0){
    					
    					//whiteMove = false;
    					PlayerType = "AIversion1";
    					difficulty = Integer.parseInt((String) files.getSelectedItem());
						//tree.add(chessBoardConfig, whiteMove, whiteRightCastle, whiteLeftCastle, blackRightCastle, blackLeftCastle);
						System.out.println("movenum: " + moveNum + ", whiteMove: " + (whiteMove));
    				}
    				if (whiteMove){
    					PlayerType = "stupid";
    					if (moveNum == 1){
    						
    						//difficulty = Integer.parseInt((String) files.getSelectedItem());
    						tree.add(chessBoardConfig, whiteMove, false, false, false, false);
    						
    						//tree.root.alphabeta(2, -1000000000, 1000000000, true, 0, whiteRightCastle, whiteLeftCastle, blackRightCastle, blackLeftCastle, false, true);
    						
    					}
    					else{
    						//whiteMove = true;
    						difficulty = Integer.parseInt((String) files.getSelectedItem());
    						//tree.add(chessBoardConfig, whiteMove, whiteRightCastle, whiteLeftCastle, blackRightCastle, blackLeftCastle);
    					}
    					System.out.println("movenum: " + moveNum + ", whiteMove: " + (whiteMove));
    				}
    			}
    			
    			
    			if (Long.bitCount((tree.root.board[0] | tree.root.board[7])) == 28){
    				beginning = false;
    			}
    			
    			if (PlayerType == "AIversion1"){
    				long start = System.currentTimeMillis();
    				
    				//System.gc();
    				
    				//int [] move = AIversion1(chessBoardConfig, moveNum, pointDifference, 4);
    				int[] move = null;
					try {
						move = AIversion2(chessBoardConfig, moveNum, 4);
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
    				moveHistory[moveNum - 1] = move;
    				if (chessBoardConfig[move[2]][move[3]].contains("black")){
    					String temp = chessBoardConfig[move[2]][move[3]];
    					blackPiecesTaken.push(temp);
    					piecesTaken++;
    					//deleteNodes(false);
    					System.out.println("deletedN: " + deletedN);
	    				deletedN = 0;
    					
    				}
    				if(chessBoardConfig[move[0]][move[1]].contains("king") && 
    						Math.abs(move[3] - move[1]) > 1){
    					if (whiteMove){
    						whiteRightCastle = true;
    						whiteLeftCastle = true;
    					}
    					if (!whiteMove){
    						blackRightCastle = true;
    						blackLeftCastle = true;
    					}	
    				}
    				System.out.println("total execution time = " + (System.currentTimeMillis() - start));
    				System.out.println();   
    				//System.out.println("chosen move2: " + Arrays.toString(move));
    				moveMaker(move, currentKey);	
    				
      
        			//System.out.println("d1: " + tree.getRoot().children[0].children.length);
        			//tree.getRoot().addNode(2 + moveNum, time, 0);
        			
        			
        			nodePop = 0;
        			nodeCreated = 0;
        			tempNodeCreated = 0;
        			//System.out.println("bank account: " + nodeBank.size());
        			//System.out.println("move: " + moveNum);
        			//System.out.println("round 1: " + tree.printNodeNumber(5));
        			/*
        			Collections.sort(queue, comparator1);
        			for (int i = 0; i < 5; i++){
        				queue.get(i).addNode(1, time, 0, 1, true);
        			}
        				
        			queue.clear();
        			
        			*/
        				/*
        			for(int i = 0; i < tree.getRoot().children.length; i++){
        				tree.getRoot().children[i].addNode(4 + moveNum, time, 0, 1, );
        			}
        			if (ChessBoardWithColumnsAndRows.moveNum == 2){
        				System.out.println("this size " + queue.size());
        			}
        			*/
        			//tree.getRoot().addNode(tree.getRoot(), 6, time, 0, 2, false);
        			
        		
    				long t = System.currentTimeMillis();
    			
        				
        			System.out.println("black move time: " + (System.currentTimeMillis() - t));
        			
        			System.out.println("HASH BOARD SIZE BLACK");
    				System.out.println(ChessBoardWithColumnsAndRows.boards.size());
    				
        			System.out.println("after black hash nodes called: " + hashNodesCalled);
        			hashNodesCalled = 0;
        			
        			long time = System.currentTimeMillis();
        			
        			//tree.extendTree(2, time, false, false, whiteRightCastle, whiteLeftCastle, blackRightCastle, blackLeftCastle);
        			//int in = lastTreeSize;
        			
        			
        			
        			
    			}
    			
    			if (PlayerType == "AItest"){
    				int index = 0;
    				int startRow = - 1;
    				int startCol = - 1;
    				int endRow = - 1;
    				int endCol = - 1;	
    				for (int v = 0; v < 10; v++){
	    				for (int i = 0; i < lim; i++){
	
	    					//moveList = randomMoveGen(completeMoveGen(chessBoardConfig, moveNum, false));
	    					ArrayList<Long[]>list = null;
	    					long [] bitBoard = bitBoardOperations.bitBoardGen(chessBoardConfig);
	    					if (whiteMove){
	    						list = bitBoardOperations.completeMoveGen(bitBoard, true);
	    					}
	    					else{
	    						list = bitBoardOperations.completeMoveGen(bitBoard, false);
	    					}
	    					
	    					Long [][] moveList = new Long[list.size()][2];
	    							
	    					for (int j = 0; j < list.size(); j++){
	    						moveList[j] = list.get(j);
	    						startRow = Long.numberOfLeadingZeros(moveList[j][0]) / 8;
	        					startCol = Long.numberOfLeadingZeros(moveList[j][0]) % 8;
	        					endRow = Long.numberOfLeadingZeros(moveList[j][1]) / 8;
	        					endCol = Long.numberOfLeadingZeros(moveList[j][1]) % 8;
	        					int [] m = {startRow, startCol, endRow, endCol};
	    						System.out.println(Arrays.toString(m));
	    					}
	    					
	    					
	    					Random r = new Random();
	    					int rand = r.nextInt(moveList.length);
	    					
	    					startRow = Long.numberOfLeadingZeros(moveList[rand][0]) / 8;
	    					startCol = Long.numberOfLeadingZeros(moveList[rand][0]) % 8;
	    					endRow = Long.numberOfLeadingZeros(moveList[rand][1]) / 8;
	    					endCol = Long.numberOfLeadingZeros(moveList[rand][1]) % 8;
	
	    					int [] m ={startRow, startCol, endRow, endCol};
	    					
	    					if (!whiteMove){
	            				if ((startRow == 0 && startCol == 0)){
	                				blackLeftCastle = true;
	                			}
	                			if ((startRow == 0 && startCol == 7)){
	                				blackLeftCastle = true;
	                			}
	                			if (chessBoardConfig[startRow][startCol].equals("black king")){
	                				blackLeftCastle = true;
	                				blackRightCastle = true;
	                			}
	            			}
	            			
	    					else{
	            				if ((startRow == 7 && startCol == 0)){
	                				whiteLeftCastle = true;
	                			}
	                			if ((startRow == 7 && startCol == 7)){
	                				whiteRightCastle = true;
	                			}
	                			if (chessBoardConfig[startRow][startCol].equals("white king")){
	                				whiteLeftCastle = true;
	                				whiteRightCastle = true;
	                			}
	            			}
	    					
	    					bitBoardOperations.printBoardLevel(bitBoard[0] | bitBoard[7]);
	    					System.out.println(Arrays.toString(m));
	    					
	    					String tempPiece = chessBoardConfig[startRow][startCol];
		        			String pieceTaken = chessBoardConfig[endRow][endCol];
		        			String[] split = tempPiece.split("\\s+");
		        			
		        			int color = -1;
		        			int windex = -1;
		        			
		        			if (tempPiece.contains("white")){
		        				color = 1;
		        				windex = Arrays.asList(whiteImages).indexOf(tempPiece);
		        			}
		        			
		        			if (tempPiece.contains("black")){
		        				color = 0;
		        				windex = Arrays.asList(blackImages).indexOf(tempPiece);
		        			}
	    					
	    					if (tempPiece.contains("empty") || !moveChecker(chessBoardConfig, moveNum, startRow, startCol, endRow, endCol) ){
	    						
	    						bitBoardOperations.printBoardLevel(bitBoard[0] | bitBoard[7]);
	    						
	    						for (int k = 0; k < moveList.length; k++){
	    							if (moveList[k][0]/8 == startRow && moveList[k][0] % 8 == startCol){
	    								System.out.println(Arrays.toString(moveList[k]));
	    							}
	    						}
	    						
	    						System.out.println("ho");
	    						System.out.println(chessBoardConfig[startRow][startCol]);
	    						System.out.println(startRow);
	    						System.out.println(startCol);
	    						System.out.println("fosho");
	    						System.out.println(chessBoardConfig[endRow][endCol]);
	    						System.out.println(endRow);
	    						System.out.println(endCol);
	    						System.out.println();
	    						
	    						System.out.println(index);
	    						
	    						System.out.println();
	    						
	    						
	    						/*
	    						int [][] temp = null;
	    	    				if (chessBoardConfig[startRow][startCol].contains("pawn")){
	    	    					temp = AIpawnMoves(chessBoardConfig, startRow, startCol, false);
	    	    				}
	    	    				
	    	    				if (chessBoardConfig[startRow][startCol].contains("knight")){
	    	    					temp = AIknightMoves(chessBoardConfig, startRow, startCol, false);
	    	    				}
	    	    				
	    	    				if (chessBoardConfig[startRow][startCol].contains("bishop")){
	    	    					temp = AIbishopMoves(chessBoardConfig, startRow, startCol, false);
	    	    				}
	    	    				
	    	    				if (chessBoardConfig[startRow][startCol].contains("rook")){
	    	    					temp = AIrookMoves(chessBoardConfig, startRow, startCol, false);
	    	    				}
	    	    				
	    	    				if (chessBoardConfig[startRow][startCol].contains("queen")){
	    	    					temp = AIqueenMoves(chessBoardConfig, startRow, startCol, false);
	    	    				}
	    	    				
	    	    				if (chessBoardConfig[startRow][startCol].contains("king")){
	    	    					temp = AIkingMoves(chessBoardConfig, startRow, startCol, false);
	    	    				}
	    						
	    				        for (int j = 1; j < temp[0][0]; j++){
	    				        	chessBoardConfig[temp[j][0]][temp[j][1]] = "MOVE";
	    				        }
	    				        
	    				        System.out.println();
	    				        for (int j = 0; j < 8; j++){
	    					        System.out.println(Arrays.toString(chessBoardConfig[j]));
	    					    }
	    				        System.out.println(Arrays.deepToString(temp));
	    						*/
	    						
	    						break;
	    					}
	    					
	    					if (moveChecker(chessBoardConfig, moveNum, startRow, startCol, endRow, endCol)){
	    						
	    						chessBoardConfig[endRow][endCol] = tempPiece;
	    	    				chessBoardConfig[startRow][startCol] = "empty";
	
	    	    				chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[color][windex]));
	    	    				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	    	    				chessBoardSquares[startCol][startRow].setIcon(icon);
	    	    				
	    	    				if ((endRow == 7) && chessBoardConfig[endRow][endCol].equals("black pawn")){
	    	    					chessBoardConfig[endRow][endCol] = "black queen";
	    	    					chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[BLACK][QUEEN]));
	    	    				}
	    	    				
	    	    				if ((endRow == 0) && chessBoardConfig[endRow][endCol].equals("white pawn")){
	    	    					chessBoardConfig[endRow][endCol] = "white queen";
	    	    					chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[WHITE][QUEEN]));
	    	    				}
	    	    				
	    	    				moveNum++;
	    	    				whiteMove = !whiteMove;
	    	    				if (whiteMove){
	    	    					moveLabel.setText("White's Move: ");
	    	    				}
	    	    				
	    	    				else{
	    	    					moveLabel.setText("Black's Move: ");
	    	    				}
	    	    				
	    	    				System.out.println();
	    	    				System.out.println(moveNum);
	    	    				System.out.println();
	    	    				
	    	    				for (int j = 0; j < 8; j++){
	    	    			        System.out.println(Arrays.toString(chessBoardConfig[j]));
	    	    			    }
	    	    				
	    	    				if (!pieceTaken.equals("empty")){
	    	    					scoreCalculator(pieceTaken);
	    	    					if (pointDifference < 0){
	    	    						score.setText("Black is winning by " + Math.abs(pointDifference));
	    	    					}
	    	    					if (pointDifference > 0){
	    	    						score.setText("White is winning by " + pointDifference);
	    	    					}
	    	    					if (pointDifference == 0){
	    	    						score.setText("Score is tied at " + pointDifference);
	    	    					}
	    	    				}
	    	    				
	    	    				if (pointDifference == -1000000){
	    	    					message.setText("BLACK WINS!");
	    	    				}
	    	    				
	    	    				if (pointDifference == 1000000){
	    	    					message.setText("WHITE WINS!");
	    	    				}
	    	    				moveField.setText("");
	    	    				
	    					}
	    					if (i != lim - 1 && v != 9){
		    					try {
									setupNewGame();
								} catch (ClassNotFoundException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
		    				}
	    				}
	    				
    				}
    			}
    			
    			if (PlayerType == "stupid"){
    				
    				String move = moveField.getText();
    				
        			String [] splitted = move.toLowerCase().split("\\s+");
        			String start = splitted[0];
        			String end = splitted[1];
        			
        			int startCol = 0;
        			int startRow = 0;
        			int endCol = 0;
        			int endRow = 0;
        			
        			if (!move.contains("castle")){
        				startCol = COLS.indexOf((start.substring( 0, 1)).toUpperCase());
	        			startRow = 8 - Integer.parseInt(start.substring( 1, 2));
	        			endCol = COLS.indexOf(end.substring( 0, 1).toUpperCase());
	        			endRow = 8 - Integer.parseInt(end.substring( 1, 2));
        			}
        			
        			if (move.contains("castle")){
        				if (move.contains("right")){
        					if (whiteMove){
        						startRow = 7;
        						startCol = 4;
        						endRow = 7;
        						endCol = 6;
        								
        					}
        					else{
        						startRow = 0;
        						startCol = 4;
        						endRow = 0;
        						endCol = 6;
        								
        					}
        				}
        				if (move.contains("left")){
        					if (whiteMove){
        						startRow = 7;
        						startCol = 4;
        						endRow = 7;
        						endCol = 2;
        								
        					}
        					else{
        						startRow = 0;
        						startCol = 4;
        						endRow = 0;
        						endCol = 2;
        								
        					}
        				}
        				if (whiteMove){
    						whiteRightCastle = true;
    						whiteLeftCastle = true;
    					}
    					if (!whiteMove){
    						blackRightCastle = true;
    						blackLeftCastle = true;
    					}
        			}
        			
        			
        	
        			
        			int [] moveArr = {startRow, startCol, endRow, endCol};
        			
        			long l = System.currentTimeMillis();
        			PrintWriter writer = null;
					try {
						writer = new PrintWriter(("/Users/madison/Documents/workspace/chess/src/chess/moveList/opponentMove"+l), "UTF-8");
					} catch (FileNotFoundException
							| UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
        			writer.println(startRow +""+ startCol + "" +  endRow + "" + endCol);
        			writer.close();
        			
        			
        			moveHistory[moveNum - 1] = moveArr;
        			
        			
        			
        			
        			if (!whiteMove){
        				if ((startRow == 0 && startCol == 0)){
            				blackLeftCastle = true;
            			}
            			if ((startRow == 0 && startCol == 7)){
            				blackLeftCastle = true;
            			}
            			if (chessBoardConfig[startRow][startCol].equals("black king")){
            				blackLeftCastle = true;
            				blackRightCastle = true;
            			}
        			}
        			
        			else{
        				if ((startRow == 7 && startCol == 0)){
            				whiteLeftCastle = true;
            			}
            			if ((startRow == 7 && startCol == 7)){
            				whiteRightCastle = true;
            			}
            			if (chessBoardConfig[startRow][startCol].equals("white king")){
            				whiteLeftCastle = true;
            				whiteRightCastle = true;
            			}
        			}
        			
        			
        			
        			
        			String tempPiece = chessBoardConfig[startRow][startCol];
        			String pieceTaken = chessBoardConfig[endRow][endCol];
        			
        			if (move.contains("en passant")){
        				if (whiteMove){
        					pieceTaken = chessBoardConfig[endRow + 1][endCol];
        				}
        				else{
        					pieceTaken = chessBoardConfig[endRow - 1][endCol];
        				}
        			}
        			
        			//String[] split = tempPiece.split("\\s+");
        			
        			int color = -1;
        			int index = -1;
        			
        			if (tempPiece.contains("white")){
        				color = 1;
        				index = Arrays.asList(whiteImages).indexOf(tempPiece);
        			}
        			
        			if (tempPiece.contains("black")){
        				color = 0; 
        				index = Arrays.asList(blackImages).indexOf(tempPiece);
        			}
    				
	    			if (moveChecker(chessBoardConfig, moveNum, startRow, startCol, endRow, endCol) || move.contains("castle")
	    					|| move.contains("en passant")){
	    				if (chessBoardConfig[moveArr[2]][moveArr[3]].contains("white")
	    						|| (move.contains("en passant") && chessBoardConfig[moveArr[2] + 1][moveArr[3]].contains("white"))){
	    					String temp = chessBoardConfig[moveArr[2]][moveArr[3]];
	    					whitePiecesTaken.push(temp);
	    					piecesTaken++;
	    					//deleteNodes(false);
	    					System.out.println("deletedN: " + deletedN);
		    				deletedN = 0;
	    				}
	    				
	    				
	    				
	    				bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
	    				//bitBoardOperations.printBoardLevel(tree.getRoot().board[bitBoardOperations.BP]);
	    				//bitBoardOperations.printBoardLevel(tree.getRoot().board[bitBoardOperations.B]);
	    				//bitBoardOperations.printBoardLevel(tree.getRoot().board[bitBoardOperations.W]);
	    				
	    				/*
	    				long [] tacticalBoard = bitBoardOperations.tacticalMapMaker(tree.getRoot().board);
	    				bitBoardOperations.printBoardLevel(tacticalBoard[0]);
	    				
	    				bitBoardOperations.printBoardLevel(tacticalBoard[1]);
						*/
	    				
	    				/*
	    				System.out.println((tree.getRoot().children == null));
	    				System.out.println((tree.getRoot() == null));
	    				Set<Long> keys = tree.getRoot().children.keySet();
	    				Iterator<Long> it = keys.iterator();
	    				//ArrayList<Node> children = new ArrayList<Node>();
	    				
	    				while(it.hasNext()){
	    					children.add(tree.getRoot().children.get(it.next()));
	    				}
	    				
	    				long [] nodeBoard = tree.getRoot().board;
	    				
	    				long s = 0;
	    				long e = 0;
	    				
	    				for (int i = 0; i < children.size(); i++){
	    					if (children.get(i) == null){
	    						break;
	    					}
	    					long [] childBoard = children.get(i).board;
	    					
	    					if (!whiteMove){
								s = nodeBoard[bitBoardOperations.B] & ~childBoard[bitBoardOperations.B];
								e = ~nodeBoard[bitBoardOperations.B] & childBoard[bitBoardOperations.B];
							}
							
	    					else{
								s = nodeBoard[bitBoardOperations.W] & ~childBoard[bitBoardOperations.W];
								e = ~nodeBoard[bitBoardOperations.W] & childBoard[bitBoardOperations.W];
							}
							
							//int [] a = {Long.numberOfLeadingZeros(s) / 8, Long.numberOfLeadingZeros(s)% 8,
							//		Long.numberOfLeadingZeros(e) / 8, Long.numberOfLeadingZeros(e) % 8};	
	    					//System.out.println("black prevMove: " + Arrays.toString(a) + ", score: " + tree.getRoot().children[i].temp);
	    				}
	    				*/
	        			System.out.println("# of black copied Nodes: " +  tree.printNodeNumber(7));
	        			System.out.println("copies: " + copiesCalled);
	    				chessBoardConfig[endRow][endCol] = tempPiece;
	    				chessBoardConfig[startRow][startCol] = "empty";

	    				chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[color][index]));
	    				ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	    				chessBoardSquares[startCol][startRow].setIcon(icon);
	    				
	    				if ((endRow == 7) && chessBoardConfig[endRow][endCol].equals("black pawn")){
	    					chessBoardConfig[endRow][endCol] = "black queen";
	    					chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[BLACK][QUEEN]));
	    				}
	    				
	    				if ((endRow == 0) && chessBoardConfig[endRow][endCol].equals("white pawn")){
	    					chessBoardConfig[endRow][endCol] = "white queen";
	    					chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[WHITE][QUEEN]));
	    				}
	    				
	    				if (move.contains("castle")){
	    					if (move.contains("right")){
	    						if (!whiteMove){
	    							chessBoardConfig[0][5] = "black rook";
	    							chessBoardConfig[0][7] = "empty";
	    							chessBoardSquares[5][0].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
	    							chessBoardSquares[7][0].setIcon(icon);
	    						}
	    						else{
	    							chessBoardConfig[7][5] = "white rook";
	    							chessBoardConfig[7][7] = "empty";
	    							chessBoardSquares[5][7].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
	    							chessBoardSquares[7][7].setIcon(icon);
	    						}
	    					}
	    					if (move.contains("left")){
	    						if (!whiteMove){
	    							chessBoardConfig[0][3] = "black rook";
	    							chessBoardConfig[0][0] = "empty";
	    							chessBoardSquares[3][0].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
	    							chessBoardSquares[0][0].setIcon(icon);
	    						}
	    						else{
	    							chessBoardConfig[7][3] = "white rook";
	    							chessBoardConfig[7][0] = "empty";
	    							chessBoardSquares[3][7].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
	    							chessBoardSquares[0][7].setIcon(icon);
	    						}
	    						
	    					}
	    				}
	    				if (move.contains("en passant")){
	    					if (whiteMove){
	    						chessBoardConfig[endRow + 1][startCol] = "empty";
	    						chessBoardSquares[startCol][endRow + 1].setIcon(icon);
	    					}
	    					else{
	    						chessBoardConfig[endRow - 1][startCol] = "empty";
	    						chessBoardSquares[startCol][endRow - 1].setIcon(icon);
	    					}
	    				}
	    				/*
	    	    		if (!Arrays.equals(tree.getRoot().board,bitBoardOperations.bitBoardGen(chessBoardConfig))){
	    	    			System.out.println("BOARDS DONT MATCH!");
	    	    			System.out.println("BOARDS DONT MATCH!");
	    	    			System.out.println("BOARDS DONT MATCH!");
	    	    		}
	    	    		
	    	    		
	    	    		*/
	    				
	    				//System.out.println("player after board -1 ");
	    				//bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
	    				
	    				System.out.println("player before board1");
	    				bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
	    				
	    				long [] bitBoardGend = bitBoardOperations.bitBoardGen(chessBoardConfig);
	    				long thisKey = bitBoardOperations.hasher(pieceRandoms, bitBoardGend, whiteMove);
	    				
	    				/*
	    				AIv2Tree tempTree = new AIv2Tree();
	       				
	       				Node tempRoot = tempTree.new Node();
	       				tempRoot.key = tree.root.key.longValue();
	       				tempRoot.board = Arrays.copyOf(tree.root.board, 14);
	       				tempRoot.children = new HashMap<Long, Node>();
	       				tempRoot.score = tree.root.score;
	       				tempRoot.references = 0;
	       				tempRoot.level = tree.root.level;
	       				tempRoot.boardScore = tree.root.boardScore;
	       				tempRoot.parent = tempTree.new Node();
	       				tempRoot.parent.level = tempRoot.level - 1;
	       				tempRoot.pawnKey = tree.root.pawnKey;

	       				tempTree.setRoot(tempRoot);
	       				*/
	    				
	       				long startTime = System.currentTimeMillis();
	       				for (int j = 1; j < difficulty + 1; j++){
	       					if (System.currentTimeMillis() - startTime < 500 || j <= difficulty){
	       						tree.root.alphabeta(j, -1000000000, 1000000000, whiteMove, 0, whiteRightCastle, whiteLeftCastle, blackRightCastle, blackLeftCastle, false, true);
	       				
	       					}
	       				}
	       				System.out.println("boards size: " + boards.size() );
	    				
						Set<Long> k = boards.keySet();
						Iterator<Long> ti = k.iterator();
						int count = 0;
						while (ti.hasNext()){
							if( boards.get(ti.next()) == null){
								count++;
							}
						}
						
						System.out.println("null nodes: " + count );
	       				
	       				System.out.println("created nodes: " + Arrays.toString(nodesPerLevel));
	       				Arrays.fill(nodesPerLevel, 0);
	       				
	       				

	       				System.out.println("traversed nodes: " + Arrays.toString(tempNodesPerLevel));
	       				Arrays.fill(tempNodesPerLevel, 0);
	       			
	       				
	       				System.out.println("black hashtable size: " + boards.size());
	       				
	       				long star = System.currentTimeMillis();
	       				tree.root.zeroReference();
	       				System.out.println("deleted nodes: " + Arrays.toString(deletedNodesPerLevel));
	       				Arrays.fill(deletedNodesPerLevel, 0);
	       				boards.get(thisKey).referenceCopy();
	       				System.out.println("remaining nodes: " + Arrays.toString(newNodesPerLevel));
	       				Arrays.fill(newNodesPerLevel, 0);
	       				long reftime = System.currentTimeMillis();
	       				System.out.println("black zero ref/ copy time: " + (reftime - star));
	       				tree.deleteSubTree(tree.root, !whiteMove);
	       				long delsub = System.currentTimeMillis();
	       				System.out.println("black deletesub time: " + (delsub - reftime));
	       				System.out.println("black nodes deleted: " + deletedN);
	       				System.out.println("black new hashtable size: " + boards.size());
	       				deletedN = 0;
	    				
	    				
	    				tree.copyTree(4 + moveNum, thisKey, bitBoardGend, (whiteMove));
	    				
	    				System.out.println("player before board2");
	    				bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
	 
	    				System.out.println("player after board");
	    				bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
	    		    	 
	    	    		long [] board = bitBoardOperations.bitBoardGen(chessBoardConfig);
	    	    		
	    	    		bitBoardOperations.printBoardLevel(board[0] | board[7]);
	    				
	    				moveNum++;
	    				whiteMove = !whiteMove;
	    				if (whiteMove){
	    					moveLabel.setText("White's Move: ");
	    				}
	    				
	    				else{
	    					moveLabel.setText("Black's Move: ");
	    				}
	    				
	    				System.out.println();
	    				System.out.println(moveNum);
	    				System.out.println();
	    				for (int i = 0; i < 8; i++){
	    			        System.out.println(Arrays.toString(chessBoardConfig[i]));
	    			    }
	    				
	    				if (!pieceTaken.equals("empty")){
	    					scoreCalculator(pieceTaken);
	    					if (pointDifference < 0){
	    						score.setText("Black is winning by " + Math.abs(pointDifference));
	    					}
	    					if (pointDifference > 0){
	    						score.setText("White is winning by " + pointDifference);
	    					}
	    					if (pointDifference == 0){
	    						score.setText("Score is tied at " + pointDifference);
	    					}
	    				}
	    				
	    				if (pointDifference == -1000000){
	    					message.setText("BLACK WINS!");
	    				}
	    				
	    				if (pointDifference == 1000000){
	    					message.setText("WHITE WINS!");
	    				}
	    				moveField.setText("");
	    			}
	    			
    			}
    		
    			/*
    			if (PlayerType == "AIversion1"){
       				difficulty = Integer.parseInt((String) files.getSelectedItem());
       				/*
        			Set<Long> keys = tree.root.children.keySet();
        			Iterator<Long> it = keys.iterator();
        			for (int i = 0; i < tree.root.children.size(); i++){
        				Node n = tree.root.children.get(it.next());
        				
        				
        			}
        			
       				
       				
       			}
    			*/
    		//	long start = tree.getRoot().prevMove[0];
    		//	long end = tree.getRoot().prevMove[1];
    		//	int s = Long.numberOfLeadingZeros(start);
    		//	int e = Long.numberOfLeadingZeros(end);
    			
    		//	System.out.println("prevmove: " + "[ "+s/8+", "+s%8+", "+e/8+", "+e%8+" ]");
    			
   			
    		}

    	}
    	ActionListener listener = new AddMoveListener();
    	button.addActionListener(listener);
    	moveField.addActionListener(listener);
    }
    
    
    public static int [][] intBoardGen (String [][] chessBoardConfig){
    	
    	int [][] intBoardConfig = new int[8][8];
    	
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				if (chessBoardConfig[i][j].contains("white")){
					if (chessBoardConfig[i][j].contains("queen")){
						intBoardConfig[i][j] = whiteQueen;
					}
					if (chessBoardConfig[i][j].contains("king")){
						intBoardConfig[i][j] = whiteKing;
					}
					if (chessBoardConfig[i][j].contains("rook")){
						intBoardConfig[i][j] = whiteRook;
					}
					if (chessBoardConfig[i][j].contains("knight")){
						intBoardConfig[i][j] = whiteKnight;
					}
					if (chessBoardConfig[i][j].contains("bishop")){
						intBoardConfig[i][j] = whiteBishop;
					}
					if (chessBoardConfig[i][j].contains("pawn")){
						intBoardConfig[i][j] = whitePawn;
					}
				}
				
				if (chessBoardConfig[i][j].contains("black")){
					if (chessBoardConfig[i][j].contains("queen")){
						intBoardConfig[i][j] = blackQueen;
					}
					if (chessBoardConfig[i][j].contains("king")){
						intBoardConfig[i][j] = blackKing;
					}
					if (chessBoardConfig[i][j].contains("rook")){
						intBoardConfig[i][j] = blackRook;
					}
					if (chessBoardConfig[i][j].contains("knight")){
						intBoardConfig[i][j] = blackKnight;
					}
					if (chessBoardConfig[i][j].contains("bishop")){
						intBoardConfig[i][j] = blackBishop;
					}
					if (chessBoardConfig[i][j].contains("pawn")){
						intBoardConfig[i][j] = blackPawn;
					}
				}
			}
		}
		return intBoardConfig;
		
    }
    

    
    public static int tacticalPositionValue (String [][] chessBoardConfig, int [][] tacticalBoard, int moveNum, int [] personality){
    	
    	int value =  attackedPieceCounter(chessBoardConfig, tacticalBoard, moveNum, personality[0]) ;
    			  //controlledTerritoryCounter (tacticalBoard, chessBoardConfig, moveNum + 1, personality[2]); 
    	/*
    	System.out.println(value);
    	
    	int [][] a = tacticalMapMaker(chessBoardConfig);
		
		for (int i = 0; i < 8; i++){
			System.out.println(Arrays.toString(a[i]));
		}
		System.out.println();
		*/
    	return value;
    	
    }
    
    public static int pieceValue(String piece){
    	int value = 0;
    	
	    for (int i = 0; i < 6; i++){
	    	if (piece.contains(pieceNames[i])){
	    		value = pieceValues[i];
	    	}
	    }
    	return value;
    }
    
    public static int defencelessWeightedPieceCounter(String [][] chessBoardConfig, int [][] tacticalBoard, int moveNum, int defendWeight){
    	int c = 0;
    	
    	if (whiteMove){
	    	for (int i = 0; i < 8; i++){
	    		for (int j = 0; j < 8; j++){
	    			if (tacticalBoard[i][j] < 0 && chessBoardConfig[i][j].contains("white")){
	    				c = c + defendWeight*pieceValue(chessBoardConfig[i][j]);
	    			}
	    		}
	    	}
    	}
    	
    	else{
	    	for (int i = 0; i < 8; i++){
	    		for (int j = 0; j < 8; j++){
	    			if (tacticalBoard[i][j] > 0 && chessBoardConfig[i][j].contains("black")){
	    				c = c + defendWeight*pieceValue(chessBoardConfig[i][j]);
	    			}
	    		}
	    	}
    	}
    	return c; 	
    }
    
    
    public static int attackedPieceCounter(String [][] chessBoardConfig, int [][] tacticalBoard, int moveNum, int attackWeight){
    	int c = 0;
    	
    	if (whiteMove){
	    	for (int i = 0; i < 8; i++){
	    		for (int j = 0; j < 8; j++){
	    			if (tacticalBoard[i][j] > 0 && chessBoardConfig[i][j].contains("black")){
	    				c = c + attackWeight*pieceValue(chessBoardConfig[i][j]);
	    			}
	    		}
	    	}
    	}
    	
    	else{
	    	for (int i = 0; i < 8; i++){
	    		for (int j = 0; j < 8; j++){
	    			if (tacticalBoard[i][j] < 0 && chessBoardConfig[i][j].contains("white")){
	    				c = c + attackWeight*pieceValue(chessBoardConfig[i][j]);
	    			}
	    		}
	    	}
    	}
    	return c; 	
    }
    
    /*
    public static int protectedPieceCounter(String [][] chessBoardConfig, int [][] tacticalBoard, int moveNum, int protectWeight){
    	int c = 0;
    	
    	if (whiteMove){
	    	for (int i = 0; i < 8; i++){
	    		for (int j = 0; j < 8; j++){
	    			if (tacticalBoard[i][j] > 0 && chessBoardConfig[i][j].contains("white")){
	    				c = c + protectWeight*pieceValue(chessBoardConfig[i][j], moveNum);
	    			}
	    		}
	    	}
    	}
    	
    	if (!whiteMove){
	    	for (int i = 0; i < 8; i++){
	    		for (int j = 0; j < 8; j++){
	    			if (tacticalBoard[i][j] < 0 && chessBoardConfig[i][j].contains("black")){
	    				c = c + protectWeight*pieceValue(chessBoardConfig[i][j], moveNum);
	    			}
	    		}
	    	}
    	}
    	return c; 	
    }
    
    */ 
    
    public static int controlledTerritoryCounter (int [][] tacticalBoard, String [][] chessBoardConfig, int moveNum, int spaceWeight){
    	
    	int c = 0;
    	
    	if (whiteMove){
	    	for (int i = 0; i < 8; i++){
		    	for (int j = 0; j < 8; j++){
		    		if (tacticalBoard[i][j] > 0 && chessBoardConfig[i][j].contains("empty")){
		    			c = c + spaceWeight;
		    		}
		    	}
	    	}
    	}
    	
    	if (!whiteMove){
	    	for (int i = 0; i < 8; i++){
		    	for (int j = 0; j < 8; j++){
		    		if (tacticalBoard[i][j] < 0 && chessBoardConfig[i][j].contains("empty")){
		    			c = c + spaceWeight;
		    		}
		    	}
	    	}
    	}
    	
		return c;
    }
    public static int [][] tacticalMapMaker(String [][] chessBoardConfig){
    	int [][] tacticalBoard = new int [8][8];
    	
    	int [][] whiteMoves = completeMoveGen(chessBoardConfig, 1, true);
    	int [][] blackMoves = completeMoveGen(chessBoardConfig, 2, true);
    	
    	for (int i = 1; i < whiteMoves[0][0]; i++){
    		tacticalBoard[whiteMoves[i][2]][whiteMoves[i][3]]++;
    	}
    	for (int i = 1; i < blackMoves[0][0]; i++){
    		tacticalBoard[blackMoves[i][2]][blackMoves[i][3]]--;
    	}
    	
    	return tacticalBoard;
    }
  
    public void moveMaker(int [] move, long newKey){
    	
		int startRow = - 1;
		int startCol = - 1;
		int endRow = - 1;			
		int endCol = - 1;	
		startRow = move[0];
		startCol = move[1];
		endRow = move[2];			
		endCol = move[3];
		
		int [][] castling = {{0, 4, 0, 6}, {0, 4, 0, 2}, {7, 4, 7, 6}, {7, 4, 7, 2}};
		
		if(!moveChecker(chessBoardConfig, moveNum, startRow, startCol, endRow, endCol) && !Arrays.equals(move, castling[0]) 
				&& !Arrays.equals(move, castling[1]) && !Arrays.equals(move, castling[2]) && !Arrays.equals(move, castling[3])){
			System.out.println("FAIL");
		}
			
		if (moveNum > 25){
			System.out.println("moveNum: " + moveNum + ", move: " + Arrays.toString(move));
			System.out.println(Arrays.equals(move, castling[0]));
		}
		
		if (moveChecker(chessBoardConfig, moveNum, startRow, startCol, endRow, endCol) || Arrays.equals(move, castling[0])
				|| Arrays.equals(move, castling[1]) || Arrays.equals(move, castling[2]) || Arrays.equals(move, castling[3])){
			
			
			if (Arrays.equals(move, castling[2])){
				
				startRow = 7;
				startCol = 4;
				endRow = 7;
				endCol = 6;		
			}
				
			if (Arrays.equals(move, castling[0])){
				
				startRow = 0;
				startCol = 4;
				endRow = 0;
				endCol = 6;
			}
								
			if (Arrays.equals(move, castling[3])){
				
				startRow = 7;
				startCol = 4;
				endRow = 7;
				endCol = 2;		
			}
			if (Arrays.equals(move, castling[1])){
				
				startRow = 0;
				startCol = 4;
				endRow = 0;
				endCol = 2;		
			}
			
			int [] moveArr = {startRow, startCol, endRow, endCol};
			moveHistory[moveNum - 1] = moveArr;

			
			if (!whiteMove){
				if ((startRow == 0 && startCol == 0)){
    				blackLeftCastle = true;
    			}
    			if ((startRow == 0 && startCol == 7)){
    				blackLeftCastle = true;
    			}
    			if (chessBoardConfig[startRow][startCol].equals("black king")){
    				blackLeftCastle = true;
    				blackRightCastle = true;
    			}
			}
			
			if (whiteMove){
				if ((startRow == 7 && startCol == 0)){
    				whiteLeftCastle = true;
    			}
    			if ((startRow == 7 && startCol == 7)){
    				whiteRightCastle = true;
    			}
    			if (chessBoardConfig[startRow][startCol].equals("white king")){
    				whiteLeftCastle = true;
    				whiteRightCastle = true;
    			}
			}

			String tempPiece = chessBoardConfig[startRow][startCol];
			String pieceTaken = chessBoardConfig[endRow][endCol];
			//String[] split = tempPiece.split("\\s+");
			
			int color = -1;
			int windex = -1;
			
			if (tempPiece.contains("white")){
				color = 1;
				windex = Arrays.asList(whiteImages).indexOf(tempPiece);
			}
			
			if (tempPiece.contains("black")){
				color = 0;
				windex = Arrays.asList(blackImages).indexOf(tempPiece);
			}
			
			chessBoardConfig[endRow][endCol] = tempPiece;
			tempPiece = promotionCheck(endRow, tempPiece);
			chessBoardConfig[startRow][startCol] = "empty";
	
			chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[color][windex]));
			ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
			chessBoardSquares[startCol][startRow].setIcon(icon);
			
			if ((endRow == 7) && chessBoardConfig[endRow][endCol].equals("black pawn")){
				chessBoardConfig[endRow][endCol] = "black queen";
				chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[BLACK][QUEEN]));
			}
			
			if ((endRow == 0) && chessBoardConfig[endRow][endCol].equals("white pawn")){
				chessBoardConfig[endRow][endCol] = "white queen";
				chessBoardSquares[endCol][endRow].setIcon(new ImageIcon(chessPieceImages[WHITE][QUEEN]));
			}
			
			System.out.println("array equals: " + (tempPiece.equals("white king")));
			System.out.println("array arrays ==: " + ("white king" == chessBoardConfig[startRow][startCol]));
			System.out.println("board piece " + chessBoardConfig[startRow][startCol]);
			
			if (Arrays.equals(move, castling[0]) && tempPiece.equals("black king")){
				chessBoardConfig[0][5] = "black rook";
				chessBoardConfig[0][7] = "empty";
				chessBoardSquares[5][0].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
				chessBoardSquares[7][0].setIcon(icon);
				
			}
			if (Arrays.equals(move, castling[2]) && tempPiece.equals("white king")){
				chessBoardConfig[7][5] = "white rook";
				chessBoardConfig[7][7] = "empty";
				chessBoardSquares[5][7].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
				chessBoardSquares[7][7].setIcon(icon);
				
			}

			if (Arrays.equals(move, castling[1])  && tempPiece.equals("black king")){
				chessBoardConfig[0][3] = "black rook";
				chessBoardConfig[0][0] = "empty";
				chessBoardSquares[3][0].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
				chessBoardSquares[0][0].setIcon(icon);
				
			}
			if (Arrays.equals(move, castling[3])  && tempPiece.equals("white king")){
				
				chessBoardConfig[7][3] = "white rook";
				chessBoardConfig[7][0] = "empty";
				chessBoardSquares[3][7].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
				chessBoardSquares[0][7].setIcon(icon);
				
			}

			/*
    		if (!Arrays.equals(tree.getRoot().board,bitBoardOperations.bitBoardGen(chessBoardConfig))){
    			System.out.println("BOARDS DONT MATCH!");
    			System.out.println("BOARDS DONT MATCH!");
    			System.out.println("BOARDS DONT MATCH!");
    		}
			*/			
			
			System.out.println("movemaker before board");
			bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
			long [] bitBoardGend = bitBoardOperations.bitBoardGen(chessBoardConfig);
			tree.copyTree(4 + moveNum, newKey, bitBoardGend, (whiteMove));
			System.out.println("movemaker before deleteNodes black");
			bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
			//tree.root.referenceCopy();
		
			//System.out.println("max depth2: " + AIv2Tree.maxDepth(tree));
		//	System.out.println(Arrays.toString(nodesPerLevel));
			
			//deleteNodes(true, referencedNodes);
			//System.out.println("deleted N 1 : " + deletedN);
			//deletedN = 0;
			//Arrays.fill(nodesPerLevel, 0);
			
		//	referencedNodes = 0;
			//System.out.println("SORTED: " + (sorted));
			
			/*
			Collection<Node> e = boards.values();
			Iterator iterator = e.iterator();
			
		    System.out.println("board size : " + boards.size());
		    
		    int x = 0;
		    int y = 0;
		    int z = 0;
		    int w = 0;
		    int v = 0;
		    int u = 0;
		    int t = 0;
		    
		    while (iterator.hasNext()){
		    	//System.out.println(y);
		    	Node g = (Node) iterator.next();
		    	if (g.references == 0){
		    		x++;
		    	}
		    	if (g.references < 0){
		    		w++;
		    	}
		    	if (g.references == 1 ){
		    		z++;
		    	}
		    	if (g.references == 2 ){
		    		v++;
		    	}
		    	if (g.references == 3){
		    		u++;
		    	}
		    	if (g.references == 4){
		    		t++;
		    	}
		    	
		    	//System.out.println("depth: " + y + ", tree score: " + g.score + ", tree key: " + g.key + "ref: " + g.references );
				y = y + g.references;
				int f = g.level - moveNum;
				
				if (f < 10 && f > -1){
					refPerLevel[f] = refPerLevel[f] + g.references;
				}
				
		    }
		    
		    Arrays.fill(nodesPerLevel, 0);
			AIv2Tree.maxDepth(tree);
			System.out.println("nodes: " + Arrays.toString(nodesPerLevel));
			
			System.out.println("reference level histogram69");
			System.out.println(Arrays.toString(refPerLevel));
			Arrays.fill(refPerLevel, 0);
			

			System.out.println("nulls level histogram1");
			System.out.println(Arrays.toString(nullsPerLevel));
			Arrays.fill(nullsPerLevel, 0);
		    
			System.out.println("x: " + x + ", w:" + w);
			System.out.println("v: " + v + ", u: " + u + ", z:" + z + ", t:" + t);
			System.out.println("total: " + y);
			
			*/
			
			//tree.root.zeroReference();
			
			System.out.println("movemaker after board");
			
			
			bitBoardOperations.printBoardLevel(tree.getRoot().board[0] | tree.getRoot().board[7]);
			moveNum++;
			whiteMove = !whiteMove;
			if (whiteMove){
				moveLabel.setText("White's Move: ");
			}
			
			else{
				moveLabel.setText("Black's Move: ");
			}
			
			System.out.println();
			System.out.println(moveNum);
			System.out.println();
			for (int i = 0; i < 8; i++){
		        System.out.println(Arrays.toString(chessBoardConfig[i]));
		    }
			
			if (!pieceTaken.equals("empty")){
				scoreCalculator(pieceTaken);
				if (pointDifference < 0){
					score.setText("Black is winning by " + Math.abs(pointDifference));
				}
				if (pointDifference > 0){
					score.setText("White is winning by " + pointDifference);
				}
				if (pointDifference == 0){
					score.setText("Score is tied at " + pointDifference);
				}
			}
			
			if (pointDifference == -1000000){
				message.setText("BLACK WINS!");
			}
			
			if (pointDifference == 1000000){
				message.setText("WHITE WINS!");
			}
			moveField.setText("");
		}
    }
    
    //#################################STOP#####################################
    
    
	private static int [] AIversion2 (String [][] chessBoardConfig, int moveNum, int depth) throws FileNotFoundException, UnsupportedEncodingException {
	
		long time = System.currentTimeMillis();
		
		long newKey = 0L;
		
		/*
		if(moveNum < 3){
	    
	    	if (whiteMove){
	    		tree.add(chessBoardConfig, true, false, false, false, false);
	    	}
	    	else{
	    		tree.add(chessBoardConfig, false, false, false, false, false);
	    	}
	    	
	    	
	   
			
			difficulty = Integer.parseInt((String) files.getSelectedItem());
			long startTime = System.currentTimeMillis();
			for (int i = 1; i < difficulty + 1; i++){
				if	(System.currentTimeMillis() - startTime < 500){
					AIv2Tree.scoreBoard sb = tree.root.alphabeta(i, -1000000000, 1000000000, false, 0, whiteRightCastle, whiteLeftCastle, blackRightCastle, blackLeftCastle, false, true);
					newKey = sb.key;
					currentKey = newKey;
				}
			}
			System.out.println("created nodes: " + Arrays.toString(nodesPerLevel));
			Arrays.fill(nodesPerLevel, 0);
			
			System.out.println("traversed nodes: " + Arrays.toString(tempNodesPerLevel));
			Arrays.fill(nodesPerLevel, 0);
			
			
			AIv2Tree.maxDepth(tree);
			System.out.println("maxdepth nodes: " + Arrays.toString(nodesPerLevel));
			Arrays.fill(nodesPerLevel, 0);
		
		}
		
		else{
			
			
			
					
		}
		*/
		
		System.out.println(boards.size());
		
		System.out.println("hashnode before" + hashNodesCalled);

		difficulty = Integer.parseInt((String) files.getSelectedItem());
		long startTime = System.currentTimeMillis();
		
		/*
		AIv2Tree tempTree = new AIv2Tree();
		
		Node tempRoot = tempTree.new Node();
		tempRoot.key = tree.root.key.longValue();
		tempRoot.board = Arrays.copyOf(tree.root.board, 14);
		tempRoot.children = new HashMap<Long, Node>(tree.root.children);
		tempRoot.score = tree.root.score;
		tempRoot.references = 0;
		tempRoot.level = tree.root.level;
		tempRoot.boardScore = tree.root.boardScore;
		tempRoot.parent = tempTree.new Node();
		tempRoot.parent.level = tempRoot.level - 1;
		tempRoot.pawnKey = tree.root.pawnKey;
		
		tempTree.setRoot(tempRoot);
		*/
		
		System.out.println("tempboard");

		bitBoardOperations.printBoardLevel(tree.root.board[0] | tree.root.board[7]);
		
		for (int i = 1; i < difficulty + 1; i++){
			if	(System.currentTimeMillis() - startTime < 500 || i <= difficulty){
				AIv2Tree.scoreBoard sb = tree.root.alphabeta(i, -1000000000, 1000000000, false, 0, whiteRightCastle, whiteLeftCastle, blackRightCastle, blackLeftCastle, false, true);
				newKey = sb.key;
				currentKey = newKey;
			}
		}
		

		System.out.println("boards size: " + boards.size() );
		System.out.println("tree root children null: " + (tree.root.children == null));
		
		Set<Long> keys = boards.keySet();
		Iterator<Long> it = keys.iterator();
		int count = 0;
		while (it.hasNext()){
			if( boards.get(it.next()) == null){
				count++;
			}
		}
		
		System.out.println("null nodes: " + count );
		
		//System.out.println("mark1: " + mark1);
		//System.out.println("mark2: " + mark2);

		System.out.println("tempboard");


		
		System.out.println("AB Time: " + (System.currentTimeMillis() - startTime));
		
		System.out.println("difficulty: " + difficulty);
	
		System.out.println("node created after ab3" + nodeCreated);
		System.out.println("hashnode after ab3" + hashNodesCalled);
		System.out.println("board size: " + boards.size());
		
	

		System.out.println("created nodes: " + Arrays.toString(nodesPerLevel));
		Arrays.fill(nodesPerLevel, 0);
		
		

		System.out.println("traversed nodes: " + Arrays.toString(tempNodesPerLevel));
		Arrays.fill(tempNodesPerLevel, 0);
		
		//
		
		
		System.out.println("after white hash nodes called: " + hashNodesCalled);
		hashNodesCalled = 0;
		
		
		//tree.print();
	
		long[] bitboard = bitBoardOperations.bitBoardGen(chessBoardConfig);
		//System.out.println("board score: " + bitBoardOperations.tacticalPositionValue(bitboard, bitBoardOperations.tacticalMapMaker(bitboard), personality));
	
		//System.out.println("key max depth: " + AIv2Tree.maxDepth(tree));
	//	System.out.println(Arrays.toString(nodesPerLevel));
		//Arrays.fill(nodesPerLevel, 0);
		time = System.currentTimeMillis();
		
		int [] move = tree.moveFinder2(depth, time, newKey);
		
		long l = System.currentTimeMillis();
		
		/*
		PrintWriter writer = new PrintWriter(("/Users/madison/Documents/workspace/chess/src/chess/moveList/AImove"+l), "UTF-8");
		writer.println(move[0] + "" + move[1] + "" + move[2] + "" + move[3]);
		writer.close();
		*/
		//tree.print();
		
		System.out.println("# of white copied Nodes: " + tree.printNodeNumber(depth));
		System.out.println();
		//System.out.println(Arrays.toString({}));
		
		/*
		if (whiteMove){
    		tree.add(chessBoardConfig, true, false, false, false, false);
    	}
    	else{
    		tree.add(chessBoardConfig, false, false, false, false, false);
    	}
		*/
		System.out.println("hashtable size: " + boards.size());
		
		long star = System.currentTimeMillis();
		tree.root.zeroReference();
		System.out.println("deleted nodes: " + Arrays.toString(deletedNodesPerLevel));
		Arrays.fill(deletedNodesPerLevel, 0);
		boards.get(currentKey).referenceCopy();
		System.out.println("remaining nodes: " + Arrays.toString(newNodesPerLevel));
		Arrays.fill(newNodesPerLevel, 0);
		long reftime = System.currentTimeMillis();
		System.out.println("zero ref/ copy time: " + (reftime - star));
		tree.deleteSubTree(tree.root, (whiteMove));
		long delsub = System.currentTimeMillis();
		System.out.println("deletesub time: " + (delsub - reftime));
		System.out.println("nodes deleted: " + deletedN);
		System.out.println("new hashtable size: " + boards.size());
		deletedN = 0;
	    return move; 
	}
    
    
    public static int AICalculator(String pieceTaken){
    	int diff = 0;

		if (pieceTaken.contains("pawn")){
			diff = 100;
		}
		if (pieceTaken.contains("knight") || 
				pieceTaken.contains("bishop")){
			diff = 300;
		};
		if (pieceTaken.contains("rook")){
			diff = 500;
		}
		if (pieceTaken.contains("queen")){
			diff = 900;
		}
		if (pieceTaken.contains("king")){
			diff = 1000000;
		}
		
		if (pieceTaken.contains("white")){
			diff = diff*(-1);
		}
		
		return diff;
    }
    
    public static String promotionCheck (int endRow, String tempPiece){
    	if (endRow == 7){
    		if (tempPiece.equals("black pawn")){
    			return "black queen";
    		}
    	}
    	
    	if (endRow == 0){
    		if (tempPiece.equals("white pawn")){
    			return "white queen";
    		}
    	}
    	
    	return tempPiece;
    	
    }
    
    
    public static int[][] completeMoveGen (String [][] chessBoardConfig, int moveNum, boolean tactical){
    	int [][] possibleMoves = new int [125][4];
    	int c = 1;
    	
    	String color = "white";
    	if (!whiteMove){
    		color = "black";
    	}
    	
    	for (int i = 0; i < 8; i++){
    		for (int j = 0; j < 8; j++){
    			if (chessBoardConfig[i][j].contains(color)){
    				int [][] temp = null;
    				if (chessBoardConfig[i][j].contains("pawn")){
    					temp = AIpawnMoves(chessBoardConfig, i, j, tactical);
    				}
    				
    				if (chessBoardConfig[i][j].contains("knight")){
    					temp = AIknightMoves(chessBoardConfig, i, j, tactical);
    				}
    				
    				if (chessBoardConfig[i][j].contains("bishop")){
    					temp = AIbishopMoves(chessBoardConfig, i, j, tactical);
    				}
    				
    				if (chessBoardConfig[i][j].contains("rook")){
    					temp = AIrookMoves(chessBoardConfig, i, j, tactical);
    				}
    				
    				if (chessBoardConfig[i][j].contains("queen")){
    					temp = AIqueenMoves(chessBoardConfig, i, j, tactical);
    				}
    				
    				if (chessBoardConfig[i][j].contains("king")){
    					temp = AIkingMoves(chessBoardConfig, i, j, tactical);
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
    
    private static int [] randomMoveGen(int [][] possibleMoves){
    	
    	Random randGen = new Random();
		int index = randGen.nextInt(possibleMoves[0][0] - 2) + 1;
    	
    	return possibleMoves[index];
    }
    
    
    private static int [][] AIrookMoves(String [][] chessBoardConfig, int startRow, int startCol, boolean tactical){
    	String temp = chessBoardConfig[startRow][startCol];
		String[] split = temp.split("\\s+");
		String color = split[0];
		
    	int [][] endCoordinates = new int [15][2];
    	
    	int c = 1;
    	
    	if (!tactical){
	    	//right
	    	for (int i = startCol + 1; i < 8; i++){
	    		if (!chessBoardConfig[startRow][i].contains(color)) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
	    		if (!chessBoardConfig[startRow][i].contains(color)) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
	    		if ( (!chessBoardConfig[i][startCol].contains(color))) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
	    		if ( (!chessBoardConfig[i][startCol].contains(color))) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
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
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
		    	endCoordinates[c][0] = startRow;
		    	endCoordinates[c][1] = i;
		    	c++;
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
	    			break;
	    		}
	    	}
    	}
	    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    
    private static int [][] AIbishopMoves(String [][] chessBoardConfig, int startRow, int startCol, boolean tactical){
    	String temp = chessBoardConfig[startRow][startCol];
		String[] split = temp.split("\\s+");
		String color = split[0];
		
    	int [][] endCoordinates = new int [14][2];
    	
    	int c = 1;
    	
    	if (!tactical){
	    	//down right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (!chessBoardConfig[startRow + i][startCol + i].contains(color)) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow + i][startCol + i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (!chessBoardConfig[startRow - i][startCol + i].contains(color)) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow - i][startCol + i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//down left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol - i < 0)){
	    			break;
	    		}
	    		if (!chessBoardConfig[startRow + i][startCol - i].contains(color)) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow + i][startCol - i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol - i < 0)){
	    			break;
	    		}
	    		
	    		if (!chessBoardConfig[startRow - i][startCol - i].contains(color)) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow - i][startCol - i].contains("empty")){
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
	    		if (!chessBoardConfig[startRow + i][startCol + i].contains("empty")){
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
	    		if (!chessBoardConfig[startRow - i][startCol + i].contains("empty")){
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
	    		if (!chessBoardConfig[startRow + i][startCol - i].contains("empty")){
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
	    		if (!chessBoardConfig[startRow - i][startCol - i].contains("empty")){
	    			break;
	    		}
	    	}
    	}
    	
	    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    
    private static int [][] AIknightMoves(String [][] chessBoardConfig, int startRow, int startCol, boolean tactical){
    	String temp = chessBoardConfig[startRow][startCol];
		String[] split = temp.split("\\s+");
		String color = split[0];
		
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
		    		if (!chessBoardConfig[x][y].contains(color)){
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
    
    private static int [][] AIkingMoves(String [][] chessBoardConfig, int startRow, int startCol, boolean tactical){
    	String temp = chessBoardConfig[startRow][startCol];
		String[] split = temp.split("\\s+");
		String color = split[0];
		
    	int [][] endCoordinates = new int [9][2];
    	
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
		    		if (!chessBoardConfig[x][y].contains(color)){
		    			endCoordinates[c][0] = x;
		    			endCoordinates[c][1] = y;
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
    
    private static int [][] AIpawnMoves(String [][] chessBoardConfig, int startRow, int startCol, boolean tactical){
    	String temp = chessBoardConfig[startRow][startCol];
		String[] split = temp.split("\\s+");
		String color = split[0];
		
    	int [][] endCoordinates = new int [5][2];
    	
    	int c = 1;
    	int x = 0;
    	int y = 0;
    	
    	int [][] whiteMoves = {{-2, 0}, {-1, 0}, {-1, -1}, {-1, 1}};
    	int [][] blackMoves = {{2, 0}, {1, 0}, {1, 1}, {1, -1}};
    	
    	int initialRow = 6;
    	int [][] moves = whiteMoves;
    	int diff = -1;
    	
    	if (color.equals("black")){
    		moves = blackMoves;
    		initialRow = 1;
    		diff = 1;
    	}
    	
    	if (!tactical){
	    	if ((startRow == initialRow) && 
	    			(chessBoardConfig[startRow + diff][startCol].contains("empty")
	    			&& chessBoardConfig[startRow + 2*diff][startCol].contains("empty"))){
	    		endCoordinates[c][0] = startRow + moves[0][0];
	    		endCoordinates[c][1] = startCol;
	    		c++;
	    	}
    	
    	
	    	if (chessBoardConfig[startRow + diff][startCol].contains("empty")
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
	    		if (!chessBoardConfig[y][x].contains("empty")
	    				&& !chessBoardConfig[y][x].contains(color)){
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
    
    
    private static int [][] AIqueenMoves (String [][] chessBoardConfig, int startRow, int startCol, boolean tactical){
    	String temp = chessBoardConfig[startRow][startCol];
		String[] split = temp.split("\\s+");
		String color = split[0];
		
    	int [][] endCoordinates = new int [28][2];
    	
    	int c = 1;
    	
    	if (!tactical){
	    	//right
	    	for (int i = startCol + 1; i < 8; i++){
	    		if (!chessBoardConfig[startRow][i].contains(color)) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
	    		if (!chessBoardConfig[startRow][i].contains(color)) {
		    		endCoordinates[c][0] = startRow;
		    		endCoordinates[c][1] = i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
	    		if ( (!chessBoardConfig[i][startCol].contains(color))) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
	    		if ( (!chessBoardConfig[i][startCol].contains(color))) {
		    		endCoordinates[c][0] = i;
		    		endCoordinates[c][1] = startCol;
		    		c++;
	    		}
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//down right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (!chessBoardConfig[startRow + i][startCol + i].contains(color)) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow + i][startCol + i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up right
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol + i > 7)){
	    			break;
	    		}
	    		if (!chessBoardConfig[startRow - i][startCol + i].contains(color)) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol + i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow - i][startCol + i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//down left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow + i > 7) || (startCol - i < 0)){
	    			break;
	    		}
	    		if (!chessBoardConfig[startRow + i][startCol - i].contains(color)) {
		    		endCoordinates[c][0] = startRow + i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow + i][startCol - i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up left
	    	for (int i = 1; i < 8; i++){
	    		if ((startRow - i < 0) || (startCol - i < 0)){
	    			break;
	    		}
	    		if (!chessBoardConfig[startRow - i][startCol - i].contains(color)) {
		    		endCoordinates[c][0] = startRow - i;
		    		endCoordinates[c][1] = startCol - i;
		    		c++;
		    	}
	    		if (!chessBoardConfig[startRow - i][startCol - i].contains("empty")){
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
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//left
	    	for (int i = startCol - 1; i > -1; i--){
		    	endCoordinates[c][0] = startRow;
		    	endCoordinates[c][1] = i;
		    	c++;
	    		if (!chessBoardConfig[startRow][i].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//up
	    	for (int i = startRow + 1; i < 8; i++){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
	    			break;
	    		}
	    	}
	    	
	    	//down
	    	for (int i = startRow - 1; i > -1; i--){
		    	endCoordinates[c][0] = i;
		    	endCoordinates[c][1] = startCol;
		    	c++;
	    		if (!chessBoardConfig[i][startCol].contains("empty")){
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
	    		if (!chessBoardConfig[startRow + i][startCol + i].contains("empty")){
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
	    		if (!chessBoardConfig[startRow - i][startCol + i].contains("empty")){
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

	    		if (!chessBoardConfig[startRow + i][startCol - i].contains("empty")){
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
	    		if (!chessBoardConfig[startRow - i][startCol - i].contains("empty")){
	    			break;
	    		}
	    	}
    	}
	    	
    	endCoordinates[0][0] = c;
    	return endCoordinates;
    }
    

    private void scoreCalculator(String pieceTaken){
    	if (pieceTaken.equals("white pawn")){
			pointDifference--;
		}
		if (pieceTaken.equals("black pawn")){
			pointDifference++;
		}
		if (pieceTaken.equals("white knight") || 
				pieceTaken.equals("white bishop")){
			pointDifference = pointDifference - 3;
		}
		if (pieceTaken.equals("black knight") || 
				pieceTaken.equals("black bishop")){
			pointDifference = pointDifference + 3;
		}
		if (pieceTaken.equals("white rook")){
			pointDifference = pointDifference - 5;
		}
		if (pieceTaken.equals("black rook")){
			pointDifference = pointDifference + 5;
		}
		if (pieceTaken.equals("white queen")){
			pointDifference = pointDifference - 9;
		}
		if (pieceTaken.equals("black queen")){
			pointDifference = pointDifference + 9;
		}
		if (pieceTaken.equals("white king")){
			pointDifference = -1000000;
		}
		if (pieceTaken.equals("black king")){
			pointDifference = 1000000;
		}
    }
    
    private boolean moveChecker(String [][] chessBoardConfig, int moveNum, int startRow, int startCol, int endRow, int endCol){
    	
    	String teamPiece = chessBoardConfig[startRow][startCol];
		String split[] = teamPiece.split("\\s+");
		System.out.println("Piece: " + teamPiece);
		String piece = split[1];
		
		int p = 0;
		for (int i = 0; i < bitBoardOperations.pieces.length; i++){
			if (piece.equals(bitBoardOperations.pieces[i])){
				p = i;
			}
			
		}
		
		long [] startBoard = bitBoardOperations.bitBoardGen(chessBoardConfig);
		long start = bitBoardOperations.bitConstants[8*startRow + startCol];
		long end = bitBoardOperations.bitConstants[8*endRow + endCol];
		long [] endBoard = Arrays.copyOf(startBoard, 14);
		
		if (whiteMove){
		
			endBoard[bitBoardOperations.W] = (endBoard[bitBoardOperations.W] ^ start) ^ end;
			endBoard[1 + p] = (endBoard[1 + p] ^ start) ^ end;
			
			for (int j = 7; j < 14; j++){
				endBoard[j] = endBoard[j] & ~end;
			}
			
			if (bitBoardOperations.whiteInCheck(endBoard)){
				message.setText("Can't move into check!");
				return false;
			}
			
		}
		if (!whiteMove){
	
			endBoard[bitBoardOperations.B] = (endBoard[bitBoardOperations.B] ^ start) ^ end;
			endBoard[8 + p] = (endBoard[8 + p] ^ start) ^ end;
			
			for (int j = 0; j < 7; j++){
				endBoard[j] = endBoard[j] & ~end;
			}

			if (bitBoardOperations.blackInCheck(endBoard)){
				message.setText("Can't move into check!");
				return false;
			}
		}

		
		if ((endRow > 7) || (endRow < 0) || (endCol > 7) || (endCol < 0 )){
			message.setText("Move Outside Table Limits");
			return false;
		}
		
		//System.out.println(chessBoardConfig[startRow][startCol]);
		
		if ((whiteMove) && chessBoardConfig[endRow][endCol].contains("white")){
			message.setText("Can't Take Own Piece");
			return false;
		}
		
		if ((!whiteMove) && chessBoardConfig[endRow][endCol].contains("black")){
			message.setText("Can't Take Own Piece");
			return false;
		}
		
    	if (piece.equals("pawn")){
			if (pawnMoves(startRow, startCol, endRow, endCol, chessBoardConfig, moveNum)){
				message.setText("Good Move!");
				return true;
			}
			else {
				message.setText("Invalid Pawn Move");
				return false;
			}
    	}
    	
		if (piece.equals("rook")){
			if (rookMoves(startRow, startCol, endRow, endCol)){
				message.setText("Good Move!");
				return true;
			}
			else {
				message.setText("Invalid Rook Move");
				return false;
			}
		}
		
		if (piece.equals("knight")){
			if (knightMoves(startRow, startCol, endRow, endCol)){
				message.setText("Good Move!");
				return true;
			}
			else{
				message.setText("Invalid Knight Move");
				return false;
			}
		}
			
		if (piece.equals("bishop")){
			if (bishopMoves(startRow, startCol, endRow, endCol)){
				message.setText("Good Move!");
				return true;
			}
			else {
				message.setText("Invalid Bishop Move");
				return false;
			}
		}
		
		if (piece.equals("queen")){
			if (queenMoves(startRow, startCol, endRow, endCol)){
				message.setText("Good Move!");
				return true;
			}
			else {
				message.setText("Invalid Queen Move");
				return false;
			}
		}
		
		if (piece.equals("king")){
			if (kingMoves(startRow, startCol, endRow, endCol)){
				message.setText("Good Move!");
				return true;
			}
			else {
				message.setText("Invalid King Move");
				return false;
			}
		}
		
		return false;	
    }
    
    
    
    
    private boolean knightMoves(int startRow, int startColumn, int endRow, int endColumn){
    	return distance(startRow, startColumn, endRow, endColumn) == knightDist;
    }
    
    private boolean rookMoves(int startRow, int startColumn, int endRow, int endColumn){
    	if ((startRow == endRow) || (startColumn == endColumn)){
	    	if (startRow != endRow){
	    		if (startRow > endRow){
		    		for (int i = endRow + 1; i < startRow; i++){
		    			if (!chessBoardConfig[i][startColumn].contains("empty")){
		    				return false;
		    			}
		    		}
	    		}
	    		if (startRow < endRow){
		    		for (int i = startRow + 1; i < endRow; i++){
		    			if (!chessBoardConfig[i][startColumn].contains("empty")){
		    				return false;
		    			}
		    		}
	    		}
	    	}
	    	if (startColumn != endColumn){
	    		if (startColumn > endColumn){
		    		for (int i = endColumn + 1; i < startColumn; i++){
		    			if (!chessBoardConfig[startRow][i].contains("empty")){
		    				return false;
		    			}
		    		}
	    		}
	    		if (startColumn < endColumn){
		    		for (int i = startColumn + 1; i < endColumn; i++){
		    			if (!chessBoardConfig[startRow][i].contains("empty")){
		    				return false;
		    			}
		    		}
	    		}
	    	}
    	}
    	return true;
    }
    
    private boolean bishopMoves(int startRow, int startColumn, int endRow, int endColumn){
    	double remainder = distance(startRow, startColumn, endRow, endColumn) % Math.sqrt(2);
    	if ((remainder == 0) || ((remainder > 1.41421356237) && (remainder < 1.41421356238))){
    		int units = (int) Math.round(distance(startRow, startColumn, endRow, endColumn) / Math.sqrt(2));
    	
    		if ((startRow > endRow) && (startColumn > endColumn)){
    			for (int i = 1; i < units; i++){
    				if (!chessBoardConfig[startRow - i][startColumn - i].contains("empty")){
    					return false;
    				}
    			}
    		}
    		if ((startRow < endRow) && (startColumn < endColumn)){
    			for (int i = 1; i < units; i++){
    				if (!chessBoardConfig[startRow + i][startColumn + i].contains("empty")){
    					return false;
    				}
    			}
    		}
    		if ((startRow > endRow) && (startColumn < endColumn)){
    			for (int i = 1; i < units; i++){
    				if (!chessBoardConfig[startRow - i][startColumn + i].contains("empty")){
    					return false;
    				}
    			}
    		}
    		if ((startRow < endRow) && (startColumn > endColumn)){
    			for (int i = 1; i < units; i++){
    				if (!chessBoardConfig[startRow + i][startColumn - i].contains("empty")){
    					return false;
    				}
    			}
    		}
    		return true;
    	}
    	return false;
    }
    
    private boolean queenMoves (int startRow, int startColumn, int endRow, int endColumn){
    	double remainder = distance(startRow, startColumn, endRow, endColumn) % Math.sqrt(2);
    	if ( ( ((startRow == endRow) || (startColumn == endColumn)) && 
    			(rookMoves(startRow, startColumn, endRow, endColumn)) ) || 
    			(( ( (remainder == 0) || ((remainder > 1.41421356237) && (remainder < 1.41421356238)) ) &&
    			bishopMoves(startRow, startColumn, endRow, endColumn)) )){
    		return true;
    	}
    	return false;
    }
    
    private boolean kingMoves (int startRow, int startColumn, int endRow, int endColumn){
    	return distance(startRow, startColumn, endRow, endColumn) <= 2;
    }
    
    private boolean pawnMoves (int startRow, int startColumn, int endRow, int endColumn, String [][] chessBoardConfig, int moveNum){
    	//for white pawns
    	if (whiteMove){
    		if ((((endRow-startRow == -1)&&(endColumn==startColumn))&&(!chessBoardConfig[endRow][endColumn].contains("black"))) || (((endRow-startRow == -1) && (Math.abs(endColumn-startColumn) == 1)) && 
    				chessBoardConfig[endRow][endColumn].contains("black"))){
    			return true;
    		}
    		if ((startRow == 6) && (endRow - startRow == -2) && (endColumn == startColumn) && (chessBoardConfig[endRow+1][endColumn].contains("empty"))){
    			return true;
    		}
    		
    		
    		boolean enP = ((moveHistory[moveNum -1][0] == 6) && ( moveHistory[moveNum -1][2] - moveHistory[moveNum -1][0] == 2));
    		
    		if (enP && (endColumn == moveHistory[moveNum -1][3]) ){
    			return true;
    		}
    	}
    	//black pawns
    	else {
    		if ((((endRow-startRow == 1)&&(endColumn==startColumn))&&(!chessBoardConfig[endRow][endColumn].contains("white"))) || (((endRow-startRow == 1) && (Math.abs(endColumn-startColumn) == 1)) && 
    				chessBoardConfig[endRow][endColumn].contains("white"))){
    			return true;
    		}
    		if ((startRow == 1) && (endRow - startRow == 2) && (endColumn == startColumn) && (chessBoardConfig[endRow-1][endColumn].contains("empty"))){
    			return true;
    		}
    	}
		return false;
    }
    
    private double distance (int a, int b, int c, int d){
    	return Math.sqrt((double)(a - c)*(a - c) + (b - d)*(b - d));
    }
    
    private final static void clearBoard(){
    	moveNum = 1;
    	for (int i = 0; i < 8; i++){
    		for (int j = 0; j < 8; j++){
    			chessBoardConfig[i][j] = "empty";
    		}
    	}
    	
    	ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
    	
    	for (int i = 0; i < 8; i++){
    		for (int j = 0; j < 8; j++){
    			chessBoardSquares[i][j].setIcon(icon);
    		}
    	}
    }
    
    //end of my methods

    public final JComponent getGui() {
        return gui;
    }

    private final void createImages() {
        try {
           // URL url = new URL("http://i.stack.imgur.com/memI0.png");
            //new File f = new File("chessPics.png");
            BufferedImage bi = ImageIO.read(new File("/Users/madison/Documents/workspace/chess/src/chess/chessPics.png"));
            for (int ii = 0; ii < 2; ii++) {
                for (int jj = 0; jj < 6; jj++) {
                    chessPieceImages[ii][jj] = bi.getSubimage(
                            jj * 64, ii * 64, 64, 64);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

   
    private final void setupNewGame() throws ClassNotFoundException, IOException {
    	createFileList();
    	boards.clear();
    	piecesTaken = 0;
    	//references.clear();
    	
    	for (int i = 1; i < 7; i++){
    		String s = i + "";
    		files.addItem(s);
    	}
    	
    	File file = new File("/Users/madison/Documents/workspace/chess/src/chess/moveList");      
        String[] myFiles;    
        if(file.isDirectory()){
            myFiles = file.list();
            for (int i=0; i<myFiles.length; i++) {
                File myFile = new File(file, myFiles[i]); 
                myFile.delete();
            }
         }   
    	
    	
    	/*
    	tableAndKeys t = hashTableMapper.load("whiteTableStart");
    	boards = t.boards;
    	
    	*/
    	System.out.println("board size: " + boards.size());
    	
    	/*
    	Enumeration<Long> e = boards.keys();
    	int y = 1;
	    while (e.hasMoreElements()){
	    	System.out.println(y);
	    	Node g = boards.get(e.nextElement());
	    	
	    	System.out.println("depth: " + y + ", tree score: " + g.score + ", tree key: " + g.key + "ref: " + g.references + "level: " + g.level );
			
	    	
	    	/*
	    	System.out.println((g == null));
	    	System.out.println((g.children == null));
	    	if (g.children != null){
		    	System.out.println((g.children[0] == null));
		    	System.out.println("Contains: " + (boards.containsKey(g.children[0].key)));
		    	System.out.println("equals: " + (boards.get(g.children[0].key) == g.children[0]));
		    	System.out.println((g.children[0].children == null));
		    	if (g.children[0].children != null){
		    		System.out.println((g.children[0].children[0] == null));
		    	}
	    	}
	    	
	    	y++;
	    	
	    }
    	*/
    	//pieceRandoms = t.pieceRandoms;
    	
    	
    	
    	Random randomno = new Random();
    	    
    	pieceRandoms = new long [768];
    	long l = ~(bitBoardOperations.bitConstants[0]);
  	    for (int i = 0; i < 768; i++){
  	    	Random randomno2 = new Random();
  	    	pieceRandoms[i] = randomno.nextLong() ^ randomno2.nextLong() << 1;
  	    	pieceRandoms[i] = pieceRandoms[i] & l;
  	    }   
  	    
  	    
  	    bitBoardOperations.moveTableMaker();
  	    
  	    /*
  	    for (int k = 0; k < pieceRandoms.length - 1; k++){
	  		ArrayList<ArrayList<Long[]>> g = moveTable.get(pieceRandoms[k]);
	  		
	  		long moves = 0L;
	  		System.out.println(g.size());
	  		for (int i = 0; i < g.size(); i++){
	  			ArrayList<Long[]> arr = g.get(i);
	  			//System.out.println("size" + arr.size());
	  			for (int j = 0; j < arr.size(); j++){
	  				if (k == 0){
	  					System.out.println("i:" + i + ", j: " + j);
	  					System.out.println("size" + arr.size());
	  					bitBoardOperations.printBoardLevel(arr.get(j)[2]);
	  				}
	  				moves = moves | arr.get(j)[2];
	  			}
	  		}
	  		System.out.println(k / 64);
	  		bitBoardOperations.printBoardLevel(moves);
  			
  	    }
  	    */
  	   
  	    keyTag[0] = 0;
  	    for (int i = 1; i < 32; i++){
  	    	keyTag[i] = keyTag[i - 1] + bitBoardOperations.bitConstants[4];
  	    }
  
  	    
    	whiteLeftCastle = false;
    	whiteRightCastle = false;
    	blackLeftCastle = false;
    	blackRightCastle = false;
    	
    	pointDifference = 0;
    	moveNum = 1;
    	score.setText("Score is tied at 0");
    	clearBoard();
    
        message.setText("Make your move!");
        // set up the black pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][0].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][STARTING_ROW[ii]]));
                    chessBoardConfig[0][ii] = Black_Starting_Row[ii];
        }
        
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][1].setIcon(new ImageIcon(
                    chessPieceImages[BLACK][PAWN]));
            chessBoardConfig[1][ii] = "black pawn";
        }
        // set up the white pieces
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][6].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][PAWN]));
            chessBoardConfig[7][ii] = White_Starting_Row[ii];
        }
        
        for (int ii = 0; ii < STARTING_ROW.length; ii++) {
            chessBoardSquares[ii][7].setIcon(new ImageIcon(
                    chessPieceImages[WHITE][STARTING_ROW[ii]]));
            		chessBoardConfig[6][ii] = "white pawn";
        }
        
        for (int i = 0; i < 8; i++){
        	System.out.println(Arrays.toString(chessBoardConfig[i]));
        }
        
        //long [] b = bitBoardOperations.bitBoardGen(chessBoardConfig);
  	    //bitBoardOperations.printBoardLevel(bitBoardOperations.hasher(b, 2));
        
    }
    
    public static void save() throws IOException{
    	gameSave s = new gameSave();
    	s.setObj(tree, moveNum, chessBoardConfig, whitePiecesTaken, blackPiecesTaken, 
    			moveHistory, pieceTaken, pieceRandoms, boards, personality, piecesTaken, pointDifference);
    	Date now = new Date();
    	String date = new SimpleDateFormat("ddMMyyyyHHmm").format(now);
    	FileOutputStream fileOut = 
    			new FileOutputStream("/Users/madison/Documents/workspace/chess/src/chess/saved_states/" + date + ".ser");
    	
    	ObjectOutputStream out = new ObjectOutputStream(fileOut);
        out.writeObject(s);
        out.close();
        fileOut.close();
       	createFileList();
       	s = null;
    }
    
    public static  void load() throws ClassNotFoundException, IOException{
    	clearBoard();
    	  
    	String file = (String) files.getSelectedItem();
    	
    	gameSave s = null;
    	
    	FileInputStream fileIn = new FileInputStream("/Users/madison/Documents/workspace/chess/src/chess/saved_states/" + file);
        ObjectInputStream in = new ObjectInputStream(fileIn);
        s = (gameSave) in.readObject();
        in.close();
        fileIn.close();
        
        
      
        
        tree = s.tree;
		moveNum = s.moveNum;
		chessBoardConfig = s.chessBoardConfig;
		whitePiecesTaken = s.whitePiecesTaken;
		blackPiecesTaken = s.blackPiecesTaken;
		moveHistory = s.moveHistory;
		pieceTaken = s.pieceTaken;
		pieceRandoms = s.pieceRandoms;
		boards = s.boards;
		personality = s.personality;
		piecesTaken = s.piecesTaken; 
		
		
		

		whiteLeftCastle = false;
		whiteRightCastle = false;
		blackLeftCastle = false;
		blackRightCastle = false;

		pointDifference = 0;

		score.setText("Loaded score = " + pointDifference);

		message.setText("Make your move!");
	        // set up the black pieces
			
		ImageIcon icon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		for (int i = 0; i < 8; i++){
			for (int j = 0; j < 8; j++){
				chessBoardSquares[j][i].setIcon(icon);
				if (chessBoardConfig[i][j].contains("black")){
					if (chessBoardConfig[i][j].contains("pawn")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[BLACK][PAWN]));
					}
					if (chessBoardConfig[i][j].contains("knight")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[BLACK][KNIGHT]));
					}
					if (chessBoardConfig[i][j].contains("bishop")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[BLACK][BISHOP]));
					}
					if (chessBoardConfig[i][j].contains("rook")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[BLACK][ROOK]));
					}
					if (chessBoardConfig[i][j].contains("queen")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[BLACK][QUEEN]));
					}
					if (chessBoardConfig[i][j].contains("king")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[BLACK][KING]));
					}
				}
				if (chessBoardConfig[i][j].contains("white")){
					if (chessBoardConfig[i][j].contains("pawn")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[WHITE][PAWN]));
					}
					if (chessBoardConfig[i][j].contains("knight")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[WHITE][KNIGHT]));
					}
					if (chessBoardConfig[i][j].contains("bishop")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[WHITE][BISHOP]));
					}
					if (chessBoardConfig[i][j].contains("rook")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[WHITE][ROOK]));
					}
					if (chessBoardConfig[i][j].contains("queen")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[WHITE][QUEEN]));
					}
					if (chessBoardConfig[i][j].contains("king")){
						chessBoardSquares[j][i].setIcon(new ImageIcon(chessPieceImages[WHITE][KING]));
					}
					
				}
				
			}
		
		}     
		//System.out.println("yes");
        for (int i = 0; i < 8; i++){
        	System.out.println(Arrays.toString(chessBoardConfig[i]));
        }			       	
    } 
    
    /*
    public static void deleteNodes(boolean b, int index){
    	System.out.println("references1: " + references.size());
    	if (b){
    		
    		Collections.sort(references, referenceComparator);
    		deletedN = 0;
    
    		//int limit = references.size() - index;
    		
    		for (int i = references.size() - 1; i > index -1 ; i--){
        		Node n = references.get(i);
        		//System.out.println("references " + n.references);
        		
        		/*
        		if (n.score == -10000){
        			System.out.println(i);
        			System.out.println("key " + n.key);
        			bitBoardOperations.printBoardLevel((n.board[0] | n.board[7]));
        		}
        		*/
        		
        		/*
        		if (n.references > 0){
        			System.out.println("ERROR!!");
        			break;
        		}
        		
        		*/
        		/*
        		if (n.references < 0){
        			//System.out.println(n.references);
        			//break;
        		}
        		
        		
        		//references.remove(i);
        		boards.remove(n.key);
        		pawnStructuers.remove(n.pawnKey);
        		if (n.parent.children != null){
        			n.parent.children.remove(n.key);
        		}
        		
        		//n.children.clear();
        		//n.children = null;
        		n = null;
        		
        		//i = i - 1;
        		//nodeBank.push(n);
        		deletedN++;
        	}	
    		
    	}
    	else{
    		deletedN = 0;
    		
    		AIv2Tree t = new AIv2Tree();
    		Node marker = t.new Node();
    		marker.key = keyTag[piecesTaken];
    		references.add(marker);
    		
    		tree.root.references++;
    		
        	Collections.sort(references, keyComparator);
        	
        	//int limit = references.indexOf(marker);
    		
        	for (int i = references.size() - 1; i > 0; i--){
        		Node n = references.get(i);
        		
        		//System.out.println("key" + Long.numberOfLeadingZeros(n.key));
        		
        		references.remove(i);
        		boards.remove(n.key);
        		//n.children = null;
        		//n = null;
        		//nodeBank.push(n);
        		deletedN++;
        	}	
    	}
    	//references.trimToSize();
    	System.out.println("references2: " + references.size());
    	
    }
    */
    
   
    
    public static void main(String[] args) {
    	
    	
        Runnable r = new Runnable() {

            @Override
            public void run() {
            	ChessBoardWithColumnsAndRows cg = new ChessBoardWithColumnsAndRows();

                JFrame f = new JFrame("ChessChamp");
                f.add(cg.getGui());
                // Ensures JVM closes after frame(s) closed and
                // all non-daemon threads are finished
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // See http://stackoverflow.com/a/7143398/418556 for demo.
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        // Swing GUIs should be created and updated on the EDT
        // http://docs.oracle.com/javase/tutorial/uiswing/concurrency
        SwingUtilities.invokeLater(r);
        
        
        
        /*
        String [][] board = new String [8][8];
        
        for (int i = 0; i < 8; i++){
    		for (int j = 0; j < 8; j++){
    			board[i][j] = "empty";
    		}
    	}
        
        board[1][4] = "black pawn";
        board[3][4] = "black queen";
        
        for (int i = 0; i < 8; i++){
	        System.out.println(Arrays.toString(board[i]));
	    }
        
        int [][] test = AIpawnMoves(board, 1, 4);
        for (int i = 1; i < test[0][0]; i++){
        	board[test[i][0]][test[i][1]] = "MOVE";
        }
        
        System.out.println();
        System.out.println();
        for (int i = 0; i < 8; i++){
	        System.out.println(Arrays.toString(board[i]));
	    }
        System.out.println(Arrays.deepToString(test));
        
        */
        
    }
}