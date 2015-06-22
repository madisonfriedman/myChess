package chess;

import java.util.*;

import chess.AIv2Tree.Node;
import chess.ChessBoardWithColumnsAndRows;

import java.io.Serializable;
import java.lang.Long;
public class AIv2Tree implements Serializable{

	public Node root;
	
	private static int [] personality;
	
	private Node nextRoot;
	
	Comparator<Node> comparator1 = new scoreComparator1();
	Comparator<Node> comparator2 = new scoreComparator2();
	 
	Comparator<moveScoreTuple> whiteTupleComparator =  new whiteTupleComparator();
	Comparator<moveScoreTuple> blackTupleComparator =  new blackTupleComparator();
	
		
	 
	public AIv2Tree(){
		setRoot(null);
		personality = null;
	}
	
	
	
	public static final int whiteQueen = 1, whiteKing = 2,
            whiteRook = 3, whiteKnight = 4, whiteBishop = 5, whitePawn = 6,
            blackQueen = -1, blackKing = -2,
            blackRook = -3, blackKnight = -4, blackBishop = -5, blackPawn = -6;
	 
	
	
	
	public void add (String [][] chessBoardConfig, boolean whiteMove, boolean wcr, boolean wcl, boolean bcr, boolean bcl){

		Node tempParent = new Node();
		
		tempParent.level = ChessBoardWithColumnsAndRows.moveNum - 1;
		
		Node rootNode = new Node();

		rootNode.board = bitBoardOperations.bitBoardGen(chessBoardConfig);
		
		rootNode.parent = tempParent;
		
		Long key = new Long( bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, rootNode.board, whiteMove));
		rootNode.key =  key.longValue();
		
		long [] pawnBoard = new long[14];
		
		pawnBoard[1] = rootNode.board[1];
		pawnBoard[8] = rootNode.board[8];
		
		rootNode.pawnKey = new Long( bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, pawnBoard, whiteMove));
		
		
		rootNode.level = ChessBoardWithColumnsAndRows.moveNum;
		rootNode.boardScore = bitBoardOperations.boardScore(rootNode.board);
	
		ArrayList<moveScoreTuple> possibleMoves = bitBoardOperations.completeMoveGen2(rootNode.board, whiteMove, wcr, wcl, bcr, bcl).list;
		
		rootNode.children = new HashMap<Long, Node>((4*possibleMoves.size())/3);
		
		rootNode.score = 0;
		
		if (ChessBoardWithColumnsAndRows.boards.get(rootNode.key) != null){
			System.out.println("ROOT NODE FOUND");
			Node g = ChessBoardWithColumnsAndRows.boards.get(rootNode.key);
			rootNode.level = Math.min(g.level, rootNode.level);
			
			this.setRoot(rootNode);
		}
		else{
		
			this.setRoot(rootNode);
			root.references = 0;
			ChessBoardWithColumnsAndRows.boards.put(root.key, root);
		//	ChessBoardWithColumnsAndRows.references.add(root);
			
			//ChessBoardWithColumnsAndRows.boards2.put(root.key, root);
			//ChessBoardWithColumnsAndRows.references2.add(root);
	
			for (int i = 0; i < possibleMoves.size() ; i++){
				
				//int diff = intBoardOperations.AICalculator(intBoardConfig[possibleMoves[i][2]][possibleMoves[i][3]]);
			
				Node newNode = new Node();
				//newNode.parent = root;
				//newNode.data = score + diff;
				
				//System.out.println();
				//System.out.println();
				newNode.references = 0;
				newNode.score = 0;
				newNode.board = Arrays.copyOf(root.board, 14);
				newNode.key = root.key.longValue();
				newNode.level = ChessBoardWithColumnsAndRows.moveNum + 1;
				newNode.pawnKey = rootNode.pawnKey.longValue();
				
				long start = possibleMoves.get(i).move[1];
				long end = possibleMoves.get(i).move[2];
				
				
				if (whiteMove){
					newNode.board[bitBoardOperations.W] = ((newNode.board[bitBoardOperations.W] ^ end) ^ start);
					for (int k = 1; k < 7; k++){
						if ((start & newNode.board[k]) != 0){
							newNode.board[k] = ((newNode.board[k] ^ end) ^ start);
							newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 1)
											                                                          +(Long.numberOfLeadingZeros(start))])
											                                                          ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 1)
											                        						               						                 +(Long.numberOfLeadingZeros(end))]);
							
							if (k == 1){
								newNode.pawnKey = ((newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 1)
									                                                          +(Long.numberOfLeadingZeros(start))])
									                                                          ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 1)
									                        						               						                 +(Long.numberOfLeadingZeros(end))]);
							}
							
							newNode.key = newNode.key | bitBoardOperations.bitConstants[0];
						}
					}
					if ((newNode.board[bitBoardOperations.B] & end) != 0){
						newNode.board[bitBoardOperations.B] = (newNode.board[bitBoardOperations.B] ^ end);
						for (int k = 8; k < 14; k++){
							if ((end & newNode.board[k]) != 0){
								newNode.board[k] = (newNode.board[k] ^ end);
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 2) + ( (int) Long.numberOfLeadingZeros(end))]);
							}
						}
					}					
				}
				
				else{
					newNode.board[bitBoardOperations.B] = ((newNode.board[bitBoardOperations.B] ^ end) ^ start);
					for (int k = 8; k < 14; k++){
						if ((start & newNode.board[k]) != 0){
							newNode.board[k] = ((newNode.board[k] ^ end) ^ start);
							newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 2)
											                                                          +( (int) Long.numberOfLeadingZeros(start))])
											                                                          ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 2)
											                         						               						                 +( (int) Long.numberOfLeadingZeros(end))]);
							
							
							if (k == 8){
								newNode.pawnKey = ((newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 2)
													                                                          +( (int) Long.numberOfLeadingZeros(start))])
													                                                          ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 2)
													                         						               						                 +( (int) Long.numberOfLeadingZeros(end))]);
									
							}
							
							newNode.key = newNode.key & ~bitBoardOperations.bitConstants[0];
						}
					}
					if ((newNode.board[bitBoardOperations.W] & end) != 0){
						newNode.board[bitBoardOperations.W] = (newNode.board[bitBoardOperations.W] ^ end);
						for (int k = 1; k < 7; k++){
							if ((end & newNode.board[k]) != 0){
								newNode.board[k] = (newNode.board[k] ^ end);
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 1) + ( (int) Long.numberOfLeadingZeros(end))]);
							}
						}
					}					
				}
				
				newNode.boardScore = bitBoardOperations.boardScore(newNode.board);
				newNode.parent = rootNode;
				
				
				
				if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) != null){
					
					Node g = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
					newNode.level = Math.min(g.level, rootNode.level);
					
					newNode = g;
					
					/*
					System.out.println(a.score);
					bitBoardOperations.printBoardLevel(a.board[0] | a.board[7]);
					//bitBoardOperations.printBoardLevel(a.children.get(0).board[0] | a.children.get(0).board[7]);
	;				if (newNode != ChessBoardWithColumnsAndRows.boards.get(newNode.key)){
						newNode = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
						newNode.references = 0;
						ChessBoardWithColumnsAndRows.hashNodesCalled++;
					}
					*/
				}
				
				else{
					ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
					rootNode.children.put(newNode.key, newNode);
				//	ChessBoardWithColumnsAndRows.references.add(newNode);
				//	ChessBoardWithColumnsAndRows.boards2.put(newNode.key, newNode);
				//	ChessBoardWithColumnsAndRows.references2.add(newNode);
				}
			
				
				
			
				//System.out.println(newNode.board[8] != 0);
				/*
				System.out.println("first");
				bitBoardOperations.printBoardLevel(newNode.board[0] | newNode.board[7]);
				*/
				
			//	System.out.println("moveNum: " + moveNum + ", i: " + i);
				
				//int [][] tacboard = intBoardOperations.tacticalMapMaker(tempIntBoardConfig);
				//newNode.tacticalBoard = tacboard;
				
				//int tactical = intBoardOperations.tacticalPositionValue (intBoardConfig, tacboard, moveNum, personality);
				//System.out.println(Arrays.toString(newNode.prevMove));
				//System.out.println(tactical);
				//System.out.println();
				
				//newNode.data = score + diff + tactical;
				
				//getRoot().addNode(newNode, i, moveNum + 1, moveNum + roundsForecasted);
				//
				
				
				
				
				
				//bitBoardOperations.printBoardLevel(newNode.key);
				
			}
		}
	}
		
	
	public int[] moveFinder2(int depth, long time, long newKey){
		//for (int i = 0; i < scores.length; i++)
			
		
		/*
		ChessBoardWithColumnsAndRows.tree.getRoot().printTreePath();
		System.out.println("PATH");
		
		for(int i = 0; i < ChessBoardWithColumnsAndRows.targetNodePath.length; i++){
			System.out.println("level: " + i + ", node score: " + ChessBoardWithColumnsAndRows.targetNodePath[i].score);
		}
		ChessBoardWithColumnsAndRows.targetNodePath = new Node[7];
		
		*/
		long s = System.currentTimeMillis();
		//ChessBoardWithColumnsAndRows.tree.root.score = miniMax(ChessBoardWithColumnsAndRows.tree.root, depth, true, time );
		//ChessBoardWithColumnsAndRows.tree.root.score = ChessBoardWithColumnsAndRows.tree.root.alphabeta(5, -1000000000, 1000000000, (ChessBoardWithColumnsAndRows.moveNum % 2 == 1), 0, false, false, false, false);
		
		int score = ChessBoardWithColumnsAndRows.tree.root.score;
		/*
		if (ChessBoardWithColumnsAndRows.moveNum > 1){
			//ChessBoardWithColumnsAndRows.tree.root.printNodes(ChessBoardWithColumnsAndRows.tree.root, 1);
			System.out.println("tree 1 path");
			//ChessBoardWithColumnsAndRows.tree.root.printTreePath(4, s, true, null);
			//System.out.println("tree 2 path");
			//ChessBoardWithColumnsAndRows.tree2.root.printTreePath(4, s, true, null);
			
			
		}
		*/
		/*
		AIv2Tree tempTree = new AIv2Tree();
		
		Node tempRoot = new Node();
		
		tempRoot.board = Arrays.copyOf(ChessBoardWithColumnsAndRows.tree.root.board, 14);
		
		Long key = new Long( bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, tempRoot.board, true));
		tempRoot.key =  key.longValue();
		tempRoot.level = 0;
		
		tempRoot.score = 0;
		
		tempRoot.references = 0;
		
		tempTree.setRoot(tempRoot);
		
		tempTree.root.dynamicSearch(4, s, 0L, true);
		
		int g = dynamicMiniMax(tempTree.root, 4, -1000000000, 1000000000, true);
		*/
		/*
		
		ChessBoardWithColumnsAndRows.tree.getRoot().printTreePath();
		System.out.println("PATH");
		for(int i = 0; i < ChessBoardWithColumnsAndRows.targetNodePath.length; i++){
			System.out.println("level: " + i + ", node score: " + ChessBoardWithColumnsAndRows.targetNodePath[i].score);
		}
		ChessBoardWithColumnsAndRows.targetNodePath = new Node[7];
		
		*/
		
		
		
		//int score = alphabeta(root, depth, -1000000000, 1000000000, true);
		System.out.println("AB score " + score);
		System.out.println("alphabeta execution time = " + (System.currentTimeMillis() - s));
		
		bitBoardOperations.printBoardLevel(this.getRoot().board[0] | getRoot().board[7]);
		//bitBoardOperations.printBoardLevel(getRoot().board[bitBoardOperations.BP]);
		//bitBoardOperations.printBoardLevel(getRoot().board[bitBoardOperations.B]);
		//bitBoardOperations.printBoardLevel(getRoot().board[bitBoardOperations.W]);
		
		
		/*
		long [] tacticalBoard = bitBoardOperations.tacticalMapMaker(this.getRoot().board);
		bitBoardOperations.printBoardLevel(tacticalBoard[0]);
		
		bitBoardOperations.printBoardLevel(tacticalBoard[1]);
		*/
		
	//	System.out.println("prevMove: " + Arrays.toString(getRoot().prevMove));
		
		//ChessBoardWithColumnsAndRows.tree.print();
		
		int j = -1;
		boolean b = true;
		Set<Long> keys = this.getRoot().children.keySet();
		Iterator<Long> it = keys.iterator();
		ArrayList<Node> nodeList = new ArrayList<Node>();
		for (int i = 0; i < this.getRoot().children.size(); i++){
			nodeList.add(this.getRoot().children.get(it.next()));
		}
		
		for (int i = 0; i < this.getRoot().children.size(); i++){
			Node tempNode = nodeList.get(i);
			if (tempNode != null){
				long [] rootBoard = this.getRoot().board;
				long [] childBoard = tempNode.board;
				long start = 0;
				long end = 0;
				if (ChessBoardWithColumnsAndRows.moveNum %2 == 0){
					start = rootBoard[bitBoardOperations.B] & ~childBoard[bitBoardOperations.B];
					end = ~rootBoard[bitBoardOperations.B] & childBoard[bitBoardOperations.B];
				}
				
				if (ChessBoardWithColumnsAndRows.moveNum %2 == 1){
					start = rootBoard[bitBoardOperations.W] & ~childBoard[bitBoardOperations.W];
					end = ~rootBoard[bitBoardOperations.W] & childBoard[bitBoardOperations.W];
				}
				
			
				/*
				int [] a = {Long.numberOfLeadingZeros(start) / 8, Long.numberOfLeadingZeros(start)% 8,
						Long.numberOfLeadingZeros(end) / 8, Long.numberOfLeadingZeros(end) % 8};
				*/
				//System.out.println("prevMove: " + Arrays.toString(a)+ "score: " + root.children.get(i).score);
				//long [] points = bitBoardOperations.tacticalMapMaker(n.board);
				//bitBoardOperations.printBoardLevel(points[0]);
				//System.out.println("child score" + root.children.get(i).score + "does not equal " + score);
				/*
				if (score == tempNode.score && b){
					j = i;
					b = false;
				}
				*/
				if (ChessBoardWithColumnsAndRows.boards.get(newKey) == null){
					ChessBoardWithColumnsAndRows.boards.remove(newKey);
					this.getRoot().children.remove(newKey);
					
					System.out.println("tempnode is null: " + (tempNode == null));
					System.out.println("tempnode board is null: " + (tempNode.board == null));
					System.out.println("keyed node is null: " + (ChessBoardWithColumnsAndRows.boards.get(newKey) == null));
					//System.out.println("keyed node board is null: " + (ChessBoardWithColumnsAndRows.boards.get(newKey).board == null));
					
				}
				else{
					if (Arrays.equals(ChessBoardWithColumnsAndRows.boards.get(newKey).board, tempNode.board)){
						j = i;
						b = false;
					}
				}
			}
		}
		
		if (b == true){

			int tempScore = -1000000000;
			
			for (int i = 0; i < nodeList.size() - 1; i++){
				if (nodeList.get(i).score > tempScore){
					j = i;
					tempScore = nodeList.get(i).score;
				}
			}
			
			
			
			System.out.println("second white change made, c = " + j);
		}
		
		
		
		
		long [] rootBoard = this.getRoot().board;
		long [] childBoard = nodeList.get(j).board;
		
		long start = 0;
		long end = 0;
		
		if (ChessBoardWithColumnsAndRows.moveNum %2 == 0){
			start = rootBoard[bitBoardOperations.B] & ~childBoard[bitBoardOperations.B];
			end = ~rootBoard[bitBoardOperations.B] & childBoard[bitBoardOperations.B];
		}
		
		if (ChessBoardWithColumnsAndRows.moveNum %2 == 1){
			start = rootBoard[bitBoardOperations.W] & ~childBoard[bitBoardOperations.W];
			end = ~rootBoard[bitBoardOperations.W] & childBoard[bitBoardOperations.W];
		}
		
		
		int startRow = Long.numberOfLeadingZeros(start) / 8;
		int startCol = Long.numberOfLeadingZeros(start) % 8;
		int endRow = Long.numberOfLeadingZeros(end) / 8;
		int endCol = Long.numberOfLeadingZeros(end) % 8;
		
		if (Long.bitCount(start) > 1){
			if (endCol == 5){
				endCol = 6;
			}
			if (startCol == 0){
				startCol = 4;
			}
		}
		
		int [] move = {startRow, startCol, endRow, endCol};
		
		if (ChessBoardWithColumnsAndRows.moveNum == 1){
			move[1] = move[3] = 5;
			move[0] = 6;
			move[2] = 4;
		}
		
	    System.out.println("chosen move: " + Arrays.toString(move));
		System.out.println();   	
		
		//root.printTreePath(4);
		return move;
	}
	
	
	
	
	
	
	class Node implements Serializable {
		
		public Long key;
		public long[] board;
		//public int [][] tacticalBoard;
		public HashMap<Long, Node> children;
		public int score;
		public int references;
		public int level;
		public int boardScore;
		public Node parent;
		public Long pawnKey;
		
		
		public scoreBoard alphabeta(int depth, int alpha, int beta, boolean maximizingPlayer, long p, boolean wcr, boolean wcl, boolean bcr, boolean bcl, boolean tactical, boolean dSearch){
			
			if (board[bitBoardOperations.BK] == 0 && board[bitBoardOperations.WK] != 0){
				scoreBoard sb = new scoreBoard(1000000);
				sb.key = key.longValue();
				return sb;
			}
			else if (board[bitBoardOperations.WK] == 0 && board[bitBoardOperations.BK] != 0){
				scoreBoard sb = new scoreBoard(-1000000);
				sb.key = key.longValue();
			
				return sb;
			}
			else if(level != parent.level + 1){
				
				scoreBoard sb = new scoreBoard(score);
				sb.key = key.longValue();
				return sb;
			}
			else if(depth <= 0){
				
				/*
				listAndTactical list1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);
				ArrayList<moveScoreTuple>possibleMoves1 = list1.list;
				//ArrayList<moveScoreTuple>possibleMoves1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);							
				int size1 = possibleMoves1.size();
				
				listAndTactical list2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
				ArrayList<moveScoreTuple>possibleMoves2 =list2.list;
				
				//ArrayList<moveScoreTuple>possibleMoves2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
				int size2 = possibleMoves2.size();
				
				boardScore = boardScore + size1 - size2 + bitBoardOperations.tacticalPositionValue(board, list2.tacticalBoard, list1.tacticalBoard);
				*/
				
				scoreBoard sb = new scoreBoard(0);
				sb.score = this.dynamicSearch(ChessBoardWithColumnsAndRows.difficulty, alpha, beta, maximizingPlayer, p, wcr, wcl, bcr, bcl, tactical, dSearch).score;
				
				sb.key = key.longValue();
				
				return sb;
			}
			else{
			
				listAndTactical list1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);
				ArrayList<moveScoreTuple>possibleMoves1 = list1.list;
				//ArrayList<moveScoreTuple>possibleMoves1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);							
				int size1 = possibleMoves1.size();
				
				listAndTactical list2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
				ArrayList<moveScoreTuple>possibleMoves2 = list2.list;
				
				//ArrayList<moveScoreTuple>possibleMoves2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
				int size2 = possibleMoves2.size();
				
				/*
				
				boolean enPRight = false;
				boolean enPLeft = false;
				*/
				
				
				if (maximizingPlayer){
					
					scoreBoard bestKey = null;
					scoreBoard returnKey;
					
					/*
					if (p != 0L){
						//System.out.println("P");
						//bitBoardOperations.printBoardLevel(p);
						if (((board[bitBoardOperations.WP] & (p >>> 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 7)){
							Long[] l = {(long) bitBoardOperations.WP, (p >>> 1),  (p << 8), null};
							/*
							for (int t = 8; t < 14; t++){
								if ((l[2] & board[t]) != 0){
									l[3] = (long) t;
								}
							}
							
							possibleMoves.add(l);
							enPLeft = true;
						}
						if (((board[bitBoardOperations.WP] & (p << 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 0)){
							Long[] l = {(long) bitBoardOperations.WP, (p << 1),  (p << 8), null};
							/*
							for (int t = 8; t < 14; t++){
								if ((l[2] & board[t]) != 0){
									l[3] = (long) t;
								}
							}
							
							possibleMoves.add(l);
							enPRight = true;
						}
					}
					
					p = 0L;	
					*/
					
					ArrayList<moveScoreTuple> tuples = possibleMoves1;
					
					if (children == null){
						children = new HashMap<Long, Node>((4*size1)/3);
					}

					int lev = ChessBoardWithColumnsAndRows.difficulty - depth;
					
					for (int i = 0; i < size1; i++){
						
						Node newNode = new Node();
						ChessBoardWithColumnsAndRows.tempNodesPerLevel[lev]++;
						
						newNode.key = key.longValue();
						
						newNode.level = level + 1;
						
						int piece = tuples.get(i).move[0].intValue();
						long start = tuples.get(i).move[1];
						long end = tuples.get(i).move[2];
						int takenPiece = tuples.get(i).takenPiece;

						if (piece == 4){
							if (start == bitBoardOperations.bitConstants[63]){
								wcr = true;
							}
							else if (start == bitBoardOperations.bitConstants[56]){
								wcl = true;
							}
						}
						
						if (start == board[bitBoardOperations.WK]){
							wcr = true;
							wcl = true;
							if ((start >> 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[255]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[253];
								
								//64*(bitBoardOperations.WR - 1) + 63, 64*(bitBoardOperations.WR - 1) + 61
							}
							else if ((start << 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[248]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[251];
								//64*(bitBoardOperations.WR - 1) + 56, 64*(bitBoardOperations.WR - 1) + 59
							}
						}
						
						
						int s = tuples.get(i).move[3].intValue();
						int e = tuples.get(i).move[4].intValue();

						int index = 64*(piece - 1);
						
						newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + e]);
						newNode.key = newNode.key | bitBoardOperations.bitConstants[0];

						if ((board[bitBoardOperations.B] & end) != 0){
							/*
							if (takenPiece == null){
								l1:
								for (int t = 8; t < 14; t++){
    								if ((end & board[t]) != 0){
    									takenPiece = t;
    									break l1;
    								}
    							}
								System.out.println("tactical: " + (tactical));
								for (int g = 0; g < 14; g++){
									System.out.println("movenull: " + board == null);
									//System.out.println("piece: " + tuples.get(i).move[1].intValue());
									if ((board[g] & tuples.get(i).move[1]) != 0 ){
										System.out.println("start board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
									if ((board[g] & tuples.get(i).move[2]) != 0 ){
										System.out.println("end board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
								}
								
								System.out.println("whitemove: " + (maximizingPlayer));
								System.out.println(Arrays.toString(tuples.get(i).move));
								bitBoardOperations.printBoardLevel(tuples.get(i).move[1]);
								bitBoardOperations.printBoardLevel(tuples.get(i).move[2]);
								System.out.println(tuples.get(i).score);
								System.out.println("whi1");
								
								/*
								if(enPRight || enPLeft){
									if (i == size - 1){
										System.out.println("white enpassant error" );
									}
									
									if (i == size - 2){
										System.out.println("white enpassant error" );
									}
								}
								System.out.println("epr " + (enPRight));
								System.out.println("epr " + (enPLeft));
								
							
							}
							*/
							
							newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(takenPiece - 2) + e]);
							
						}					
						newNode = bitBoardOperations.bitPromotionCheck(end, newNode, board, true, e);
						
						/*
						if(enPRight || enPLeft){
							if (i == size - 1){
								int lead = Long.numberOfLeadingZeros(p);
								newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BP - 2) + lead]) ;
							}
							if (enPRight && enPLeft){
								if (i == size - 2){
									int lead = Long.numberOfLeadingZeros(p);
									newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BP - 2) + lead]) ;
								}
							}
						}
						*/
						
						
						if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) != null){
							Node g = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
							g.level = Math.min(g.level, newNode.level);
							tuples.get(i).score = g.score;
							
							newNode = g;
							
							if (children.get(newNode.key) == null){
								children.put(newNode.key, g);
							}
							
						}
						else{
							
							newNode.boardScore = boardScore - bitBoardOperations.pieceVals[takenPiece];
							newNode.board = Arrays.copyOf(board, 14);

							newNode.parent = this;

							newNode.pawnKey = pawnKey.longValue();
							
							tuples.get(i).score = newNode.boardScore;
							
						}
						tuples.get(i).node = newNode;
			
					}
						
					Collections.sort(tuples, whiteTupleComparator);
					
					int le = ChessBoardWithColumnsAndRows.difficulty - depth;
					
					for (int i = 0; i < tuples.size(); i++){
						
						Node newNode = tuples.get(i).node;
						if (!ChessBoardWithColumnsAndRows.boards.containsKey(newNode.key)){
							ChessBoardWithColumnsAndRows.nodesPerLevel[le]++;
							
							int piece = tuples.get(i).move[0].intValue();
							long start = tuples.get(i).move[1];
							long end = tuples.get(i).move[2];
							int s = tuples.get(i).move[3].intValue();
							int takenPiece = tuples.get(i).takenPiece;
							int e = tuples.get(i).move[4].intValue();
							
							
							if (start == board[bitBoardOperations.WK]){
								if ((start >> 2) == end){
									newNode.board[bitBoardOperations.W] = (bitBoardOperations.bitConstants[63]
											^ (newNode.board[bitBoardOperations.W] ^ bitBoardOperations.bitConstants[61]));
									newNode.board[bitBoardOperations.WR] = (bitBoardOperations.bitConstants[63]
											^ (newNode.board[bitBoardOperations.WR] ^ bitBoardOperations.bitConstants[61]));
								}
								else if ((start << 2) == end){
									newNode.board[bitBoardOperations.W] =(bitBoardOperations.bitConstants[56]
											^ (newNode.board[bitBoardOperations.W] ^ bitBoardOperations.bitConstants[59]));
									newNode.board[bitBoardOperations.WR] = (bitBoardOperations.bitConstants[56]
											^ (newNode.board[bitBoardOperations.WR] ^ bitBoardOperations.bitConstants[59]));
								}
							}
							
										
							newNode.board[bitBoardOperations.W] = (start ^ (newNode.board[bitBoardOperations.W] ^ end));
					
							newNode.board[piece] = ((newNode.board[piece] ^ end) ^ start);
							
							if (piece == 1){
								newNode.pawnKey = ((newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[e]);
							}
							if (takenPiece != 0){
								newNode.board[bitBoardOperations.B] = (newNode.board[bitBoardOperations.B] ^ end);
								newNode.board[takenPiece] = (newNode.board[takenPiece] ^ end);
								
								if (takenPiece == 8){
									newNode.pawnKey = (newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[384 + e]);
								}
							}	
							newNode = bitBoardOperations.promotionCheck(start, end, newNode, true, e);
							
							/*
							if(enPRight || enPLeft){
								if (i == size - 1){
									newNode.board[bitBoardOperations.B] = newNode.board[bitBoardOperations.B] ^ p;
									newNode.board[bitBoardOperations.BP] = newNode.board[bitBoardOperations.BP] ^ p;
									newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.BP];
								}
								if (enPRight && enPLeft){
									if (i == size - 2){
										newNode.board[bitBoardOperations.B] = newNode.board[bitBoardOperations.B] ^ p;
										newNode.board[bitBoardOperations.BP] = newNode.board[bitBoardOperations.BP] ^ p;
										newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.BP];
									}
								}
							}
							*/
							ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
							if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
								ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
								ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
								if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
									ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
									ChessBoardWithColumnsAndRows.boards.put(newNode.key,newNode);
									if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
										ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
										ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
									}
								}
							}		
							
						//	ChessBoardWithColumnsAndRows.references.add(newNode);
						
							/*
							if (((start & board[bitBoardOperations.WP]) != 0) && (end == (start << 16))){
								p = end; 
							}
							*/
							
							
							children.put(newNode.key, newNode);
							
						}
						
						returnKey = newNode.alphabeta(depth -1, alpha, beta, false, p, wcr, wcl, bcr, bcl, tactical, dSearch);
						
						if (returnKey.score >= beta){
							//returnKey.score = beta;
							return returnKey;
						}
						if (returnKey.score > alpha){
							alpha = returnKey.score.intValue();
							bestKey = returnKey;
							bestKey.key = newNode.key.longValue();
						}
						
						newNode.scoreSet(returnKey.score);	
					}
					
					if (bestKey == null){
		
						scoreBoard sb = new scoreBoard(0);
						sb.score = this.dynamicSearch(ChessBoardWithColumnsAndRows.difficulty, alpha, beta, !maximizingPlayer, p, wcr, wcl, bcr, bcl, tactical, dSearch).score;
						
						sb.key = key.longValue();
						return sb;
						
					}
					else{
						return bestKey;
					}
				
					
				}
				
				else {
					
					scoreBoard bestKey = null;
					scoreBoard returnKey;
					
					
					/*
					if (p != 0L){
						//System.out.println("P");
						//bitBoardOperations.printBoardLevel(p);
						if (((board[bitBoardOperations.BP] & (p >>> 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 7)){
							Long[] l = {(long) bitBoardOperations.BP, (p >>> 1),  (p >> 8), null};
							/*
							for (int t = 1; t < 7; t++){
								if ((l[2] & board[t]) != 0){
									l[3] = (long) t;
								}
							}
							
							possibleMoves.add(l);
							enPLeft = true;
							
						}
						if (((board[bitBoardOperations.BP] & (p << 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 0)){
							Long[] l = {(long) bitBoardOperations.BP, (p << 1), (p >> 8), null};
							/*
							for (int t = 1; t < 7; t++){
								if ((l[2] & board[t]) != 0){
									l[3] = (long) t;
								}
							}
							
							possibleMoves.add(l);
							enPRight = true;
						}
					}
					
					p = 0L;
					
					*/
					
					ArrayList<moveScoreTuple> tuples = possibleMoves2;
							
					
					if (children == null){
						children = new HashMap<Long, Node>((size2*4)/3);
					}
					
					int lev = ChessBoardWithColumnsAndRows.difficulty - depth;
					
					for (int i = 0; i < size2; i++){
						Node newNode = new Node();
						ChessBoardWithColumnsAndRows.tempNodesPerLevel[lev]++;
						
						newNode.key = key.longValue();
						
						newNode.level = level + 1;
						
						int piece = tuples.get(i).move[0].intValue();
						long start = tuples.get(i).move[1];
						long end = tuples.get(i).move[2];
						int takenPiece = tuples.get(i).takenPiece;
						
						if (piece == 11){
							if (start == bitBoardOperations.bitConstants[7]){
								bcr = true;
							}
							else if (start == bitBoardOperations.bitConstants[0]){
								bcl = true;
							}
						}
							
						if (start == board[bitBoardOperations.BK]){
							bcr = true;
							bcl = true;
							if ((start >> 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[583]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[581];
								//64*(bitBoardOperations.BR - 2) + 7, 64*(bitBoardOperations.BR - 2) + 5
								
							}
							else if ((start << 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[576]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[579];
								//64*(bitBoardOperations.BR - 2) + 0, 64*(bitBoardOperations.BR - 2) + 3
							}
						}
						
						int s = tuples.get(i).move[3].intValue();
						int e = tuples.get(i).move[4].intValue();
						
						int index = 64*(piece - 2);

						newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + e]);
						newNode.key = newNode.key & ~bitBoardOperations.bitConstants[0];
						
						if ((board[bitBoardOperations.W] & end) != 0){
							/*
							if (takenPiece == null){
								for (int t = 1; t < 7; t++){
    								if ((end & board[t]) != 0){
    									takenPiece = t;
    									break;
    								}
    							}
								System.out.println("tactical: " + (tactical));
								for (int g = 0; g < 14; g++){
									System.out.println("movenull: " + board == null);
									
									if ((board[g] & tuples.get(i).move[1]) != 0 ){
										System.out.println("start board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
									if ((board[g] & tuples.get(i).move[2]) != 0 ){
										System.out.println("end board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
								}
								
								System.out.println("whitemove: " + (maximizingPlayer));
								System.out.println(Arrays.toString(tuples.get(i).move));
								bitBoardOperations.printBoardLevel(tuples.get(i).move[1]);
								bitBoardOperations.printBoardLevel(tuples.get(i).move[2]);
								System.out.println(tuples.get(i).score);
								System.out.println("whi1");
								
								/*
								if(enPRight || enPLeft){
									if (i == size - 1){
										System.out.println("black enpassant error" );
										System.out.println("epr " + (enPRight));
										System.out.println("epr " + (enPLeft));
									}
									
									if (i == size - 2){
										System.out.println("black enpassant error" );
									}
								}
								
							}
							int val = takenPiece.intValue();
							*/
							
							newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(takenPiece - 1) + e]);
						
						}		
						
						
						newNode = bitBoardOperations.bitPromotionCheck(end, newNode, board, false, e);
						
						/*
						
						if(enPRight || enPLeft){
							if (i == size - 1){
								int lead = Long.numberOfLeadingZeros(p);
								newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WP - 1) + lead]) ;
							}
							if (enPRight && enPLeft){
								if (i == size - 2){
									int lead = Long.numberOfLeadingZeros(p);
									newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WP - 1) + lead]) ;
								}
							}
						}
						*/
						
						if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) != null){
							Node g = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
							
							g.level = Math.min(g.level, newNode.level);
							tuples.get(i).score = g.score;
							
							newNode = g;
							
							if (children.get(newNode.key) == null){
								children.put(newNode.key, g);
							}
							
						}
						else{
							
							newNode.boardScore = boardScore - bitBoardOperations.pieceVals[takenPiece];
							newNode.board = Arrays.copyOf(board, 14);

							newNode.parent = this;
							
							newNode.pawnKey = pawnKey.longValue();
							
							tuples.get(i).score = newNode.boardScore;
						}
						
						tuples.get(i).node = newNode;
						
					}
					
					
					Collections.sort(tuples, blackTupleComparator);
					
					int le = ChessBoardWithColumnsAndRows.difficulty - depth;
					
					for (int i = 0; i < tuples.size(); i++){
						
						Node newNode = tuples.get(i).node;
						if (!ChessBoardWithColumnsAndRows.boards.containsKey(newNode.key)){
							ChessBoardWithColumnsAndRows.nodesPerLevel[le]++;
							
							int piece = tuples.get(i).move[0].intValue();
							long start = tuples.get(i).move[1];
							long end = tuples.get(i).move[2];
							int takenPiece = tuples.get(i).takenPiece;
							int s = tuples.get(i).move[3].intValue();
							int e = tuples.get(i).move[4].intValue();
							
							if (start == board[bitBoardOperations.BK]){
								if ((start >> 2) == end){
									newNode.board[bitBoardOperations.B] = (bitBoardOperations.bitConstants[7]
											^ (newNode.board[bitBoardOperations.B] ^ bitBoardOperations.bitConstants[5]));
									newNode.board[bitBoardOperations.BR] = (bitBoardOperations.bitConstants[7]
											^ (newNode.board[bitBoardOperations.BR] ^ bitBoardOperations.bitConstants[5]));
								}
								else if ((start << 2) == end){
									newNode.board[bitBoardOperations.B] = (bitBoardOperations.bitConstants[0]
											^ (newNode.board[bitBoardOperations.B] ^ bitBoardOperations.bitConstants[3]));
									newNode.board[bitBoardOperations.BR] = (bitBoardOperations.bitConstants[0]
											^ (newNode.board[bitBoardOperations.BR] ^ bitBoardOperations.bitConstants[3]));
								}
							}
							
											
							newNode.board[bitBoardOperations.B] = (start ^ (newNode.board[bitBoardOperations.B] ^ end));
							
							newNode.board[piece] = ((newNode.board[piece] ^ end ) ^ start);
							
							if (piece == 8){
								newNode.pawnKey = ((newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[384 + s])
		                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[384 + e]);
							}
							
							if (takenPiece != 0){
								newNode.board[bitBoardOperations.W] = (newNode.board[bitBoardOperations.W] ^ end);
								newNode.board[takenPiece] = (newNode.board[takenPiece] ^ end);
								if (takenPiece == 1){
									newNode.pawnKey = (newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[e]);
								}
							}	
								
							newNode = bitBoardOperations.promotionCheck(start, end, newNode, false, e);
							/*
							if(enPRight || enPLeft){
								if (i == size - 1){
									newNode.board[bitBoardOperations.B] = newNode.board[bitBoardOperations.B] ^ p;
									newNode.board[bitBoardOperations.BP] = newNode.board[bitBoardOperations.BP] ^ p;
									//newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.BP];
								}
								if (enPRight && enPLeft){
									if (i == size - 2){
										newNode.board[bitBoardOperations.B] = newNode.board[bitBoardOperations.B] ^ p;
										newNode.board[bitBoardOperations.BP] = newNode.board[bitBoardOperations.BP] ^ p;
										//newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.BP];
									}
								}
							}
							*/
							ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
							if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
								ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
								ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
								if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
									ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
									ChessBoardWithColumnsAndRows.boards.put(newNode.key,newNode);
									if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
										ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
										ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
									}
								}
							}		
							
							//ChessBoardWithColumnsAndRows.references.add(newNode);
							
							/*
							if ((start & board[bitBoardOperations.BP]) != 0 && (end == (start >> 16))){
								p = end; 
							}
							*/
							
							children.put(newNode.key, newNode);
							
						}
					
						returnKey = newNode.alphabeta(depth -1, alpha, beta, true, p, wcr, wcl, bcr, bcl, tactical, dSearch);
						
						if (returnKey.score <= alpha){
							//returnKey.score = alpha;
							return returnKey;
						}
						if (returnKey.score < beta){
							beta = returnKey.score.intValue();
							bestKey = returnKey;
							bestKey.key = newNode.key.longValue();
							
						}
						
						newNode.scoreSet(returnKey.score);	
					}
					
					if (bestKey == null){
				
						scoreBoard sb = new scoreBoard(0);
						sb.score = this.dynamicSearch(ChessBoardWithColumnsAndRows.difficulty, alpha, beta, !maximizingPlayer, p, wcr, wcl, bcr, bcl, tactical, dSearch).score;
						
						sb.key = key.longValue();;
						return sb;
					}
					else{
						return bestKey;
					}
					
				}

			}
			
		}

		
		
		public void setReference(int ref){
			references = ref;
		}
		
		public int printNodeNumber (int depth){
			int newNumber = 1;
			if (children != null && depth > 0){
				//System.out.println("HI " + children != null);
				//System.out.println("[0]" + (children.get(0) != null));
				if (children.size() > 0){
					//System.out.println(children);
					Set<Long> keys = children.keySet();
					Iterator<Long> it = keys.iterator();
					for (int i = 0; i < children.size(); i++){
						Node tempNode = children.get(it.next());
						if (tempNode != null){
							newNumber = newNumber + tempNode.printNodeNumber(depth - 1);
						}
					}
				}
			//	System.out.println("Nulls");
				
			}
			return newNumber;
		}
		
		
		public void hashReferenceChecker (int depth){
			if (children != null && depth > 0){
				//System.out.println("HI " + children != null);
				//System.out.println("[0]" + (children.get(0) != null));
				if (children.size() > 0){
					//System.out.println(children);
					Set<Long> keys = children.keySet();
					Iterator<Long> it = keys.iterator();
					for (int i = 0; i < children.size(); i++){
						Node tempNode = children.get(it.next());
						if (tempNode != null){
							if (tempNode != ChessBoardWithColumnsAndRows.boards.get(tempNode.key)){
								System.out.println("HASH TABLE REFERENCE ERROR");
								break;
							}
							
						}
					}
				}	
			}
		}
	
		public void copyTree(int depth, long newKey, long[] bitBoardGend, boolean whiteMove){
			
			/*
			Node node = ChessBoardWithColumnsAndRows.tree.root;

			long [] gameBoard = bitBoardGend;
			System.out.println("GUI board");
			bitBoardOperations.printBoardLevel(gameBoard[0] | gameBoard[7]);
			//System.out.println("child len: "  + ChessBoardWithColumnsAndRows.tree.root.children.size());
			System.out.println("ROOT BOARD");
			
			long allPieces = 0;
			if (ChessBoardWithColumnsAndRows.moveNum % 2 == 0){
				allPieces = ChessBoardWithColumnsAndRows.tree.root.board[7];
			}
			else{
				allPieces = ChessBoardWithColumnsAndRows.tree.root.board[0];
			}
			
			long Pieces = (ChessBoardWithColumnsAndRows.tree.root.board[0] | ChessBoardWithColumnsAndRows.tree.root.board[7]);
			bitBoardOperations.printBoardLevel(Pieces);
			
			//System.out.println("ROOT CHILD BOARD");
			//bitBoardOperations.printBoardLevel(ChessBoardWithColumnsAndRows.tree.root.children.get(0).board[0] | ChessBoardWithColumnsAndRows.tree.root.children.get(0).board[7]);
			
			int c = -1;
			
			/*
			AIv2Tree t = new AIv2Tree();
			Node n = new Node();
			n.board = ChessBoardWithColumnsAndRows.tree.root.board;
			n.level = ChessBoardWithColumnsAndRows.tree.root.level;
			n.score = ChessBoardWithColumnsAndRows.tree.root.score;
			n.key = ChessBoardWithColumnsAndRows.tree.root.key;
			
			t.setRoot(n);
			*/
			
			
			/*
			ChessBoardWithColumnsAndRows.tree.root.extendTree(1, System.currentTimeMillis(), 0, (ChessBoardWithColumnsAndRows.moveNum % 2 == 1), false, ChessBoardWithColumnsAndRows.whiteRightCastle, 
					ChessBoardWithColumnsAndRows.whiteLeftCastle, ChessBoardWithColumnsAndRows.blackRightCastle, ChessBoardWithColumnsAndRows.blackLeftCastle);
			
			*/
			/*
			System.out.println("N length " + n.children.size());
			
			for (int i = 0; i < n.children.size(); i++){
				if (!ChessBoardWithColumnsAndRows.tree.root.children.contains(n.children.get(i))){
					ChessBoardWithColumnsAndRows.tree.root.children.add(n.children.get(i));
				}
			}
			
			
			
			Set<Long> keys = ChessBoardWithColumnsAndRows.tree.root.children.keySet();
			Iterator<Long> it = keys.iterator();
			
			System.out.println("children length " + ChessBoardWithColumnsAndRows.tree.root.children.size());
			
			ArrayList<Node> tempList = new ArrayList<Node>();
			
			for (int i = 0; i < ChessBoardWithColumnsAndRows.tree.root.children.size(); i++){
				
				Node tempNode = ChessBoardWithColumnsAndRows.tree.root.children.get(it.next());
				tempList.add(tempNode);
				if (tempNode != null){
				
					long nextPieces = 0;
					if (!whiteMove){
						nextPieces = tempNode.board[7];
					}
					else{
						nextPieces = tempNode.board[0];
					}
					
					
					//System.out.println("root.c");
				//	bitBoardOperations.printBoardLevel(allPieces);
					//System.out.println("next Pieces");
				//	bitBoardOperations.printBoardLevel(nextPieces);
					long start = allPieces & ~nextPieces;
					long end = ~allPieces & nextPieces;
					
					int sr = Long.numberOfLeadingZeros(start) / 8;
					int sc = Long.numberOfLeadingZeros(start) % 8;
					int er = Long.numberOfLeadingZeros(end) / 8;
					int ec = Long.numberOfLeadingZeros(end) % 8;
					
					
					
					if (Long.bitCount(start) > 1){
						if (ec == 5){
							ec = 6;
						}
						if (sc == 0){
							sc = 4;
						}
					}
					
					int[] m = {sr, sc, er, ec };
					if (!whiteMove){
						System.out.println("black move: " + Arrays.toString(m) + ", score: " + tempNode.score + ", boardscore: " + tempNode.boardScore);
						
					}
					else {
						System.out.println("white move: " + Arrays.toString(m)  + ", score: " + tempNode.score + ", boardscore: " + tempNode.boardScore);
						
					}
					System.out.println("boards equal: " + (Arrays.equals(gameBoard, tempNode.board)));
					
					bitBoardOperations.printBoardLevel(tempNode.board[0] | tempNode.board[7]);
				
					/*
					if (ChessBoardWithColumnsAndRows.moveNum == 7 && ChessBoardWithColumnsAndRows.tree.root.children.get(i).score == -1999){
						for (int b = 0; b < 14; b++){
							System.out.println(b);
							System.out.println(ChessBoardWithColumnsAndRows.tree.root.children.get(i).board[b] == gameBoard[b]);
							System.out.println(Long.valueOf(ChessBoardWithColumnsAndRows.tree.root.children.get(i).board[b]).equals(gameBoard[b]));
							System.out.println(Long.valueOf(gameBoard[b]).equals(ChessBoardWithColumnsAndRows.tree.root.children.get(i).board[b]));
						
							long l = ChessBoardWithColumnsAndRows.tree.root.children.get(i).board[b] ^ gameBoard[b];
							
							bitBoardOperations.printBoardLevel(l);
							if (l != 0){
								bitBoardOperations.printBoardLevel(ChessBoardWithColumnsAndRows.tree.root.children.get(i).board[b]);
								bitBoardOperations.printBoardLevel(gameBoard[b]);
								bitBoardOperations.printBoardLevel(ChessBoardWithColumnsAndRows.tree.root.board[b]);
							
							}
							
						}
					}
					
					
					//if (Arrays.equals(gameBoard, root.children.get(i).board)){
					
					//boolean b = false;
					
					if ((gameBoard[0] == tempNode.board[0] ) && (gameBoard[7] == tempNode.board[7])){
						if (!Arrays.equals(gameBoard, tempNode.board)){
							
							for (int k = 0; k < 13; k++){
								if (gameBoard[k] != tempNode.board[k]){
									System.out.println("depth: " + depth + ", k: " + k + ", moveNum: " + (ChessBoardWithColumnsAndRows.moveNum));
									bitBoardOperations.printBoardLevel(gameBoard[k]);
									bitBoardOperations.printBoardLevel(tempNode.board[k]);
								}
							}
						}
						c = i;
						
						if (!Arrays.equals(gameBoard, tempNode.board)){
							System.out.println("NOOOOOOOOOOOOOO");
						}
					}
					
					
					if (Arrays.equals(gameBoard, tempNode.board)){
						
						c = i;
										
						System.out.println("YAAAAAAAAAASSSS");
						System.out.println(i);
						
						/*
						if (ChessBoardWithColumnsAndRows.tree.root.children == null){
							ChessBoardWithColumnsAndRows.tree.root.children = new ArrayList<Node>();
						}
						
						
						if (tempNode.children != null){
							System.out.println(tempNode.children.size());
						}
						else{
							System.out.println("null children arraylist");
						}
						/*
						System.out.println("before :" + Arrays.toString(root.prevMove));
						
						//break;
						  
					}
					
				}
			}
			
			System.out.println("c: " + c);
			long l = ChessBoardWithColumnsAndRows.keyTag[ChessBoardWithColumnsAndRows.piecesTaken];
			
			
			if (c == -1){
				System.out.println("change made");
				Node thisNode = ChessBoardWithColumnsAndRows.boards.get(newKey);
				tempList.add(thisNode);
				c = tempList.size() - 1;
				
				if (tempList.get(c) == null){
					if (!whiteMove){
						int tempScore = 1000000000;
					
						for (int i = 0; i < tempList.size() - 1; i++){
							if (tempList.get(i).score < tempScore){
								c = i;
								tempScore = tempList.get(i).score;
							}
						}
						System.out.println("second white change made, c = " + c);
					}
					else{
						
						Node n = new Node();
						n.level = ChessBoardWithColumnsAndRows.tree.root.level + 1;
						n.board = Arrays.copyOf(gameBoard, 14);
						n.score = bitBoardOperations.boardScore(gameBoard);
						n.references = 0;
						n.key = bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, n.board, true);
						long t = System.currentTimeMillis();
						AIv2Tree.scoreBoard sb = n.alphabeta(1, -1000000000, 1000000000, true, 0L, false, false, false, false, false, true);
		    			tempList.add(n);
		    			c = tempList.size() - 1;
		    			System.out.println("black move change made, c = " + c);
					}
				}
				
			}
			/*
			long time = System.currentTimeMillis();
			node.zeroReference();
			tempList.get(c).referenceCopy();
			long reftime = System.currentTimeMillis();
			System.out.println("zero ref/ copy time: " + (reftime - time));
			
			System.out.println("boardSize after copy before deletion : " + ChessBoardWithColumnsAndRows.boards.size());
			ChessBoardWithColumnsAndRows.deletedN = 0;
			
			int size = root.children.size();
			Set<Long> ks = ChessBoardWithColumnsAndRows.tree.root.children.keySet();
			Iterator<Long> itt = ks.iterator();
			
			for (int k = 0; k < ks.size(); k++){
				itt.next();
				System.out.println("itt null: " + (itt == null) + "depth, " + depth);
				if (ChessBoardWithColumnsAndRows.tree.root.children.get(it) != null){
					if (!Arrays.equals(ChessBoardWithColumnsAndRows.tree.root.children.get(it).board, gameBoard) ){
						System.out.println("k: " + k);
						deleteSubTree(ChessBoardWithColumnsAndRows.tree.root.children.get(it));
					}
				}
				else {
					System.out.println("its Null!! " + k + "depth " + depth);
				}
			}
			
			System.out.println("deleted nodes: " + ChessBoardWithColumnsAndRows.deletedN);
			
			System.out.println("new board size: " + ChessBoardWithColumnsAndRows.boards.size());
			
			System.out.println("NEW ROOT to be BOARD");
			bitBoardOperations.printBoardLevel(tempList.get(c).board[0] | tempList.get(c).board[7]);
			
			bitBoardOperations.printBoardLevel(tempList.get(c).key);
			
			System.out.println("NEW ROOT BOARD len");
			System.out.println(tempList.size());
			
			//System.out.println("NEW ROOT child root BOARD len");
			/*System.out.println(ChessBoardWithColumnsAndRows.tree.root.children.get(0).children.size());
			bitBoardOperations.printBoardLevel(ChessBoardWithColumnsAndRows.tree.root.children.get(c).children[1].board[0] | 
					ChessBoardWithColumnsAndRows.tree.root.children.get(0).children[1].board[7]);
			
			
			
			ChessBoardWithColumnsAndRows.tree.setRoot(tempList.get(c));
			
			*/
			
			ChessBoardWithColumnsAndRows.tree.setRoot(ChessBoardWithColumnsAndRows.boards.get(newKey));
			
			ChessBoardWithColumnsAndRows.boards.remove(ChessBoardWithColumnsAndRows.tree.root.parent.key);
			ChessBoardWithColumnsAndRows.pawnStructuers.remove(ChessBoardWithColumnsAndRows.tree.root.parent.pawnKey);
    		deleteNode(ChessBoardWithColumnsAndRows.tree.root.parent);
    		ChessBoardWithColumnsAndRows.deletedN++;
				
			System.out.println("NEW ROOT child BOARD len2");
			if (ChessBoardWithColumnsAndRows.tree.root.children != null){
				System.out.println(ChessBoardWithColumnsAndRows.tree.root.children.size());
			}
			else{
				System.out.println("null children arraylist");
			}
			System.out.println("NEXT CHILD BOARD");
			
			/*
			if (ChessBoardWithColumnsAndRows.tree.root.children.size() == 1){
				bitBoardOperations.printBoardLevel(ChessBoardWithColumnsAndRows.tree.root.children.get(0).board[0] |
						ChessBoardWithColumnsAndRows.tree.root.children.get(0).board[7]);
			}
			*/
			
			/*
			if (Arrays.equals(node.board,ChessBoardWithColumnsAndRows.tree.root.board)){
				System.out.println("this error");
			}
			System.out.println("SCORES EQUAL?: " + (ChessBoardWithColumnsAndRows.tree.root.score == bitBoardOperations.boardScore(ChessBoardWithColumnsAndRows.tree.root.board)) );
			System.out.println("board score: " + bitBoardOperations.boardScore(ChessBoardWithColumnsAndRows.tree.root.board));
			System.out.println("root score: " + ChessBoardWithColumnsAndRows.tree.root.score);
			
			*/
			
			ChessBoardWithColumnsAndRows.tree.root.scoreSet(bitBoardOperations.boardScore(ChessBoardWithColumnsAndRows.tree.root.board));
			//bitBoardOperations.printBoardLevel(ChessBoardWithColumnsAndRows.tree.root.children.get(0).key);
			/*
			if (node == ChessBoardWithColumnsAndRows.tree.root){
				System.out.println("copy error");
				System.out.println("copy possibilities");
				System.out.println("UPDATE CODE IF NEEDED");
				/*
				long xorboard = 0;
				long andboard = 0;
				andboard = ~andboard;
				System.out.println("starting board");
				bitBoardOperations.printBoardLevel(allPieces);
				for (int ii = 0; ii < root.children.size(); ii++){
					
					long nextPieces = (ChessBoardWithColumnsAndRows.tree.root.children.get(ii).board[0] | ChessBoardWithColumnsAndRows.tree.root.children.get(ii).board[7]);
					
					long start = allPieces & ~nextPieces;
					long end = ~allPieces & nextPieces;
					
					int sr = Long.numberOfLeadingZeros(start) / 8;
					int sc = Long.numberOfLeadingZeros(start) % 8;
					int er = Long.numberOfLeadingZeros(end) / 8;
					int ec = Long.numberOfLeadingZeros(end) % 8;
					
					int[] m = {sr, sc, er, ec };
					
					System.out.println("black move: " + Arrays.toString(m));
					
					System.out.println(ii);
					System.out.println(Arrays.equals(gameBoard, ChessBoardWithColumnsAndRows.tree.root.children.get(ii).board));
					
					bitBoardOperations.printBoardLevel(nextPieces);
					
					xorboard = xorboard ^ allPieces;
					andboard = andboard & allPieces;
					
				}
				System.out.println("xorboard");
				bitBoardOperations.printBoardLevel(xorboard);
				System.out.println("andboard");
				bitBoardOperations.printBoardLevel(andboard);
				
				
				
			}
			*/
		}
		
		/*
		public void deleteSubTree(Node n, long l, int depth){
			
			//System.out.println("depth: " + depth);
				
			
			if (n.children != null && n.children.size() > 0 && depth > 0){
				for (int i = 0; i < n.children.size(); i++){
					if (n.children.get(i) != null ){
						deleteSubTree(n.children.get(i), l, depth - 1);
						
					}
				}
				/*
				if (children.size() == 1){
					System.out.println("bad BOard");
					bitBoardOperations.printBoardLevel(board[0] | board[7]);
				}
				
				
				
			}
			//n.references--;	
			//ChessBoardWithColumnsAndRows.nodeBank.push(n);
			//n.children = null;
    		//ChessBoardWithColumnsAndRows.boards.remove(n.key);
    		//ChessBoardWithColumnsAndRows.deletedN++;
				
		}
		*/
		
		//int duration
		public void scoreSet(int s){
			this.score = s;
		}
		
		
		public void referenceCopy(){
			references = 1;
			ChessBoardWithColumnsAndRows.newNodesPerLevel[level - ChessBoardWithColumnsAndRows.moveNum]++;
			if (children != null){
				Set<Long> keys = children.keySet();
				Iterator<Long> it = keys.iterator();
				for (int i = 0; i < children.size(); i++){
					Node tempNode = ChessBoardWithColumnsAndRows.boards.get(it.next());
					if (tempNode != null && tempNode.level == level + 1){
						tempNode.referenceCopy();
					}
				}
			}
		}
		
		public void deleteSubTree(Node n, boolean whiteMove){
		//	System.out.println("level: " + n.level);
			//System.out.println("references: " + n.references);
			if (n.children != null){
				Set<Long> keys = n.children.keySet();
				Iterator<Long> it = keys.iterator();
				while (it.hasNext()){
					Long l = it.next();
					Node thisNode = ChessBoardWithColumnsAndRows.boards.get(l);
					/*
					System.out.println("correct hash: " + (l == bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, thisNode.board, (level % 2 == 1))));
					System.out.println("l: " + (l ));
					System.out.println("hash: " + (bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, thisNode.board, (level % 2 == 1))));
					System.out.println("thisnodekey: " + (thisNode.key ));
					*/
					
					if (thisNode != null && thisNode.level == n.level + 1){
						deleteSubTree(thisNode, !whiteMove);
						/*
						if (n.references == 0){
							ChessBoardWithColumnsAndRows.boards.remove(n.key);
							ChessBoardWithColumnsAndRows.pawnStructuers.remove(n.pawnKey);
							parent.children.remove(n.key);
				    		
				    		deleteNode(children.get(it));
				    		ChessBoardWithColumnsAndRows.deletedN++;
						}
						*/
					}
					
					/*
					else{
						//System.out.println("ELSEEEEE ");
						System.out.println("null node?: " + (thisNode == null) + ",parent level " + n.level + ", child level: " + thisNode.level);
						//System.out.println("null node?: " + (l == null));
						System.out.println("l: " + (l ));
						
						System.out.println("hash: " + (bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, thisNode.board, (level % 2 == 1))));
						//System.out.println("parent level + 1: " + ( n.level + 1));
						//System.out.println("thisNode level: " + (thisNode.level));
						System.out.println("parent level + 1 == thisnodelevel:  " + ( thisNode.level == n.level + 1));
					}
					*/
				}
			}
			if (n.references == 0){
				ChessBoardWithColumnsAndRows.boards.remove(n.key);
				ChessBoardWithColumnsAndRows.pawnStructuers.remove(n.pawnKey);
				if (parent.children != null){
					parent.children.remove(n.key);
				}
	    		deleteNode(n);
	    		ChessBoardWithColumnsAndRows.deletedN++;
			}
			else{
				
			}
			
		}
		
		public void zeroReference(){
			references = 0;
			ChessBoardWithColumnsAndRows.deletedNodesPerLevel[level - ChessBoardWithColumnsAndRows.moveNum]++;
			//System.out.println("children Null!!: " + (children == null));
			if (children != null){
				Set<Long> keys = children.keySet();
				Iterator<Long> it = keys.iterator();
			//	System.out.println("children length: " + (children.size()));
				for (int i = 0; i < children.size(); i++){
					Long l = it.next();
					Node tempNode = ChessBoardWithColumnsAndRows.boards.get(l);
					//System.out.println("null key?: " + (l == null));
					//System.out.println("null node?: " + (tempNode == null));
					//System.out.println("tempNode level?: " + (tempNode.level));
					//System.out.println("parent level: " + level);
					if (tempNode != null && tempNode.level == level + 1){
						tempNode.zeroReference();
					}
					else{
						//System.out.println("ELSEEEEE ");
						//System.out.println("null node?: " + (tempNode == null) + ",parent level " + level + ", child level: " + tempNode.level);
						//System.out.println("null node?: " + (l == null));
						//System.out.println("l: " + (l ));
						
						//System.out.println("hash: " + (bitBoardOperations.hasher(ChessBoardWithColumnsAndRows.pieceRandoms, tempNode.board, (level % 2 == 1))));
						//System.out.println("parent level + 1: " + ( n.level + 1));
						//System.out.println("thisNode level: " + (thisNode.level));
						//System.out.println("parent level + 1 == thisnodelevel:  " + ( tempNode.level == level + 1));
					}
				}
			}
		}
		
		
		
		
		
		public void extendTree(int depth, long time, long p, boolean whiteMove, boolean dSearch, boolean wcr, boolean wcl, boolean bcr, boolean bcl) {
			
			/*
			if (children != null && depth > 1){
				Set<Long> keys = children.keySet();
				Iterator<Long> it = keys.iterator();
				for (int i = 0; i < children.size(); i++){
					Node tempNode = children.get(it.next());
					if (tempNode != null){ 
						tempNode.extendTree(depth - 1, time, p, !whiteMove, dSearch, wcr, wcl, bcr, bcl);
					}
				}
			}
			*/
			
			if ((true)){//(children == null) && (System.currentTimeMillis() - time < 60000)  ){
				
				ArrayList<moveScoreTuple> possibleMoves = bitBoardOperations.completeMoveGen2(board, whiteMove, wcr, wcl, bcr, bcl).list;
				
				int size = possibleMoves.size();
				
				/*
				boolean enPRight = false;
				boolean enPLeft = false;
				*/
				
				if (whiteMove){
					
					/*
					if (p != 0L){
						if (((board[bitBoardOperations.WP] & (p >>> 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 7)){
							Long[] l = {(long) bitBoardOperations.WP, (p >>> 1),  (p << 8), null};
							possibleMoves.add(l);
							enPLeft = true;
						}
						if (((board[bitBoardOperations.WP] & (p << 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 7)){
							Long[] l = {(long) bitBoardOperations.WP, (p << 1),  (p << 8), null};
							possibleMoves.add(l);
							enPRight = true;
						}
					}
					
					p = 0L;
					*/
				
					ArrayList<moveScoreTuple> tuples = possibleMoves;
				
					
					//Collections.sort(tuples, whiteTupleComparator);
					
					if (children == null){
						children = new HashMap<Long, Node>((4*size)/3);
					}
					
					for (int i = 0; i < size; i++){
						Node newNode = new Node();
						
						ChessBoardWithColumnsAndRows.nodeCreated++;
						
						newNode.key = key.longValue();
						
						newNode.level = level + 1;
						//System.out.println("Level: " + newNode.level);
						
						newNode.score = 0;
						
						int piece = tuples.get(i).move[0].intValue();
						long start = tuples.get(i).move[1];
						long end = tuples.get(i).move[2];
						Integer takenPiece = tuples.get(i).takenPiece;
						
						if (piece == 4){
							if (start == bitBoardOperations.bitConstants[63]){
								wcr = true;
							}
							else if (start == bitBoardOperations.bitConstants[56]){
								wcl = true;
							}
						}
						
						if (start == board[bitBoardOperations.WK]){
							wcr = true;
							wcl = true;
							if ((start >> 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WR - 1) + 63]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WR - 1) + 61];
							}
							if ((start << 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WR - 1) + 56]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WR - 1) + 59];
							}
						}
						
						int s = tuples.get(i).move[3].intValue();
						int e = tuples.get(i).move[4].intValue();
						
						newNode = bitBoardOperations.bitPromotionCheck(end, newNode, board, whiteMove, e);
						
						

						
						newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(piece - 1) + s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(piece - 1) + e]);
						newNode.key = newNode.key | bitBoardOperations.bitConstants[0];

						if ((board[bitBoardOperations.B] & end) != 0){
							newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(takenPiece.intValue() - 2) + e]);
							/*
							for (int k = 8; k < 14; k++){
								if ((end & board[k]) != 0){
									newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 2) + e]) ;
									break;
								}
							}
							*/
						}					

						/*
						if(enPRight || enPLeft){
							if (i == size - 1){
								int lead = Long.numberOfLeadingZeros(p);
								newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BP - 2) + lead]) ;
							}
							if (enPRight && enPLeft){
								if (i == size - 2){
									int lead = Long.numberOfLeadingZeros(p);
									newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BP - 2) + lead]) ;
								}
							}
						}
						*/
							
						if (!children.containsKey(newNode.key) && !ChessBoardWithColumnsAndRows.boards.containsKey(newNode.key)){
							
							newNode.board = Arrays.copyOf(board, 14);
							
							newNode.references = 0;
													
							if (start == board[bitBoardOperations.WK]){
								if ((start >> 2) == end){
									newNode.board[bitBoardOperations.W] = (bitBoardOperations.bitConstants[63]
											^ (newNode.board[bitBoardOperations.W] ^ bitBoardOperations.bitConstants[61]));
									newNode.board[bitBoardOperations.WR] = (bitBoardOperations.bitConstants[63]
											^ (newNode.board[bitBoardOperations.WR] ^ bitBoardOperations.bitConstants[61]));
								}
								if ((start << 2) == end){
									newNode.board[bitBoardOperations.W] =(bitBoardOperations.bitConstants[56]
											^ (newNode.board[bitBoardOperations.W] ^ bitBoardOperations.bitConstants[59]));
									newNode.board[bitBoardOperations.WR] = (bitBoardOperations.bitConstants[56]
											^ (newNode.board[bitBoardOperations.WR] ^ bitBoardOperations.bitConstants[59]));
								}
							}
							
							
							newNode = bitBoardOperations.promotionCheck(start, end, newNode, whiteMove, e);
											
							newNode.board[bitBoardOperations.W] = (start ^ (newNode.board[bitBoardOperations.W] | end));
							
							newNode.board[piece] = ((newNode.board[piece] ^ end) ^ start);
							
							if ((newNode.board[bitBoardOperations.B] & end) != 0){
								newNode.board[bitBoardOperations.B] = (newNode.board[bitBoardOperations.B] ^ end);
								newNode.board[takenPiece.intValue()] = (newNode.board[takenPiece.intValue()] ^ end);
								
								//newNode.score = newNode.score - bitBoardOperations.pieceVals[takenPiece.intValue()];
								/*
								for (int k = 8; k < 14; k++){
									if ((end & newNode.board[k]) != 0){
										newNode.board[k] = (newNode.board[k] ^ end);
										newNode.score = newNode.score - bitBoardOperations.pieceVals[k];
										break;
									}
								}
								*/
							}	
							
				
							/*
							if(enPRight || enPLeft){
								if (i == size - 1){
									newNode.board[bitBoardOperations.B] = newNode.board[bitBoardOperations.B] ^ p;
									newNode.board[bitBoardOperations.BP] = newNode.board[bitBoardOperations.BP] ^ p;
								//	newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.BP];
								}
								if (enPRight && enPLeft){
									if (i == size - 2){
										newNode.board[bitBoardOperations.B] = newNode.board[bitBoardOperations.B] ^ p;
										newNode.board[bitBoardOperations.BP] = newNode.board[bitBoardOperations.BP] ^ p;
										//newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.BP];
									}
								}
							}
							*/
							
							
							ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
							if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
								ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
								ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
								if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
									ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
									ChessBoardWithColumnsAndRows.boards.put(newNode.key,newNode);
									if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
										ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
										ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
									}
								}
							}		
							
							//ChessBoardWithColumnsAndRows.references.add(newNode);
							
							if (((start & board[bitBoardOperations.WP]) != 0) && (end == (start << 16))){
								p = end; 
							}
							children.put(newNode.key, newNode);
							
							
						}
						else{
							Node g;
							if (children.containsKey(newNode.key)){
								g = children.get(newNode.key);
								newNode = g;
								children.put(newNode.key, newNode);
							}
							else{
								g = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
								newNode = g;
								children.put(newNode.key, newNode);
							}
						}
						
						
						if (newNode != null){
							
							if (depth > 1 ){
								newNode.extendTree(depth - 1, time, p, !whiteMove, dSearch, wcr, wcl, bcr, bcl);
							}
							
							/*
							else if (depth == 1 && dSearch){
								//Integer m = newNode.score.intValue();
								
								newNode.dynamicSearch(1, time, !whiteMove);
								
								/*
								if (m != newNode.score.intValue()){
									System.out.println("prev score:" + m);
									System.out.println("current score:" + newNode.score.intValue());
									System.out.println();
								}
								
							}
						*/
							
							
						}
						
						
					}
				
				}
				else{
					/*
					if (p != 0L){
						if (((board[bitBoardOperations.BP] & (p >>> 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 7)){
							Long[] l = {(long) bitBoardOperations.BP, (p >>> 1),  (p >> 8), null};
							possibleMoves.add(l);
							enPLeft = true;
							
						}
						if (((board[bitBoardOperations.BP] & (p << 1)) != 0) && (Long.numberOfLeadingZeros(p) % 8 != 0)){
							Long[] l = {(long) bitBoardOperations.BP, (p << 1), (p >> 8), null};
							possibleMoves.add(l);
							enPRight = true;
						}
					}
					
					p = 0L;
					*/
					
					size = possibleMoves.size();
					
					
					ArrayList<moveScoreTuple> tuples = possibleMoves;
					
					if (children == null){
						children = new HashMap<Long, Node>((4*size)/3);
					}
					
					for (int i = 0; i < size; i++){
						Node newNode = new Node();
						ChessBoardWithColumnsAndRows.nodeCreated++;
						
						newNode.key = key.longValue();
						
						newNode.level = level + 1;
						//System.out.println("Level: " + newNode.level);
						
						newNode.score = 0;
						
						int piece = tuples.get(i).move[0].intValue();
						long start = tuples.get(i).move[1];
						long end = tuples.get(i).move[2];
						Integer takenPiece = tuples.get(i).takenPiece;
						
						if (piece == 11){
							if (start == bitBoardOperations.bitConstants[7]){
								bcr = true;
							}
							else if (start == bitBoardOperations.bitConstants[0]){
								bcl = true;
							}
						}
							
						if (start == board[bitBoardOperations.BK]){
							bcr = true;
							bcl = true;
							if ((start >> 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BR - 2) + 7]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BR - 2) + 5];
							}
							if ((start << 2) == end){
								newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BR - 2) + 0]) 
										^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.BR - 2) + 3];
							}
						}
						
						int s = tuples.get(i).move[3].intValue();
						int e = tuples.get(i).move[4].intValue();
						
						newNode = bitBoardOperations.bitPromotionCheck(end, newNode, board, whiteMove, e);
						

						newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(piece - 2) + s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(piece - 2) + e]);
						newNode.key = newNode.key & ~bitBoardOperations.bitConstants[0];
						
						if ((board[bitBoardOperations.W] & end) != 0){
							if (takenPiece == null){
								for (int t = 1; t < 7; t++){
    								if ((end & board[t]) != 0){
    									takenPiece = t;
    									break;
    								}
    							}
								
								for (int g = 0; g < 14; g++){
									System.out.println("movenull: " + board == null);
									//System.out.println("piece: " + tuples.get(i).move[1].intValue());
									if ((board[g] & tuples.get(i).move[1]) != 0 ){
										System.out.println("start board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
									if ((board[g] & tuples.get(i).move[2]) != 0 ){
										System.out.println("end board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
								}
								
								System.out.println("whitemove: " + (whiteMove));
								System.out.println(Arrays.toString(tuples.get(i).move));
								bitBoardOperations.printBoardLevel(tuples.get(i).move[1]);
								bitBoardOperations.printBoardLevel(tuples.get(i).move[2]);
								System.out.println(tuples.get(i).score);
								System.out.println("whi1");
								//newNode.score = newNode.score - bitBoardOperations.pieceVals[takenPiece.intValue()];
							}
							
							newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(takenPiece.intValue() - 1) + e]);
							
							/*
							for (int k = 1; k < 7; k++){	
								if ((end & board[k]) != 0){
									newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(k - 1) + e]) ;
									break;
								}
							}
							*/
						}		
						
						/*
						if(enPRight || enPLeft){
							if (i == size - 1){
								int lead = Long.numberOfLeadingZeros(p);
								newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WP - 1) + lead]) ;
							}
							if (enPRight && enPLeft){
								if (i == size - 2){
									int lead = Long.numberOfLeadingZeros(p);
									newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(bitBoardOperations.WP - 1) + lead]) ;
								}
							}
						}
						*/
						
						if (!children.containsKey(newNode.key) && !ChessBoardWithColumnsAndRows.boards.containsKey(newNode.key)){
							
							newNode.board = Arrays.copyOf(board, 14);
							
							newNode.references = 0;
							
							if (start == board[bitBoardOperations.BK]){
								if ((start >> 2) == end){
									newNode.board[bitBoardOperations.B] = (bitBoardOperations.bitConstants[7]
											^ (newNode.board[bitBoardOperations.B] ^ bitBoardOperations.bitConstants[5]));
									newNode.board[bitBoardOperations.BR] = (bitBoardOperations.bitConstants[7]
											^ (newNode.board[bitBoardOperations.BR] ^ bitBoardOperations.bitConstants[5]));
								}
								if ((start << 2) == end){
									newNode.board[bitBoardOperations.B] = (bitBoardOperations.bitConstants[0]
											^ (newNode.board[bitBoardOperations.B] ^ bitBoardOperations.bitConstants[3]));
									newNode.board[bitBoardOperations.BR] = (bitBoardOperations.bitConstants[0]
											^ (newNode.board[bitBoardOperations.BR] ^ bitBoardOperations.bitConstants[3]));
								}
							}
							
							newNode = bitBoardOperations.promotionCheck(start, end, newNode, whiteMove, e);
											
							newNode.board[bitBoardOperations.B] = (start ^ (newNode.board[bitBoardOperations.B] ^ end));
							
							newNode.board[piece] = ((newNode.board[piece] | end ) ^ start);
							
							if ((newNode.board[bitBoardOperations.W] & end) != 0){
								newNode.board[bitBoardOperations.W] = (newNode.board[bitBoardOperations.W] ^ end);
								newNode.board[takenPiece.intValue()] = (newNode.board[takenPiece.intValue()] ^ end);
								
								//newNode.score = newNode.score - bitBoardOperations.pieceVals[takenPiece.intValue()];
								/*
								for (int k = 1; k < 7; k++){
									if ((end & newNode.board[k]) != 0){
										newNode.board[k] = (newNode.board[k] ^ end);
										newNode.score = newNode.score - bitBoardOperations.pieceVals[k];
										break;
									}
								}
								*/
							}	
	
							/*
							if(enPRight || enPLeft){
								if (i == size - 1){
									newNode.board[bitBoardOperations.W] = newNode.board[bitBoardOperations.W] ^ p;
									newNode.board[bitBoardOperations.WP] = newNode.board[bitBoardOperations.WP] ^ p;
								//	newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.WP];
								}
								if (enPRight && enPLeft){
									if (i == size - 2){
										newNode.board[bitBoardOperations.W] = newNode.board[bitBoardOperations.W] ^ p;
										newNode.board[bitBoardOperations.WP] = newNode.board[bitBoardOperations.WP] ^ p;
								//		newNode.score = newNode.score - bitBoardOperations.pieceVals[bitBoardOperations.WP];
									}
								}
							}
							*/
							
							
							ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
							if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
								ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
								ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
								if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
									ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
									ChessBoardWithColumnsAndRows.boards.put(newNode.key,newNode);
									if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
										ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
										ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
									}
								}
							}		
							
							//ChessBoardWithColumnsAndRows.references.add(newNode);
							
							
							if ((start & board[bitBoardOperations.BP]) != 0 && (end == (start >> 16))){
								p = end; 
							}
							
							children.put(newNode.key, newNode);
							
						}
						else{
							Node g;
							if (children.containsKey(newNode.key)){
								g = children.get(newNode.key);
								newNode = g;
								children.put(newNode.key, newNode);
							}
							else{
								g = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
								newNode = g;
								children.put(newNode.key, newNode);
							}
						}

						if (newNode != null){
							
							if (depth > 1 ){
								newNode.extendTree(depth - 1, time, p, !whiteMove, dSearch, wcr, wcl, bcr, bcl);
							}
							
							/*
							else if (depth == 1 && dSearch){
								//Integer m = newNode.score.intValue();
								
							//	newNode.dynamicSearch(1, time, !whiteMove);
								
								/*
								if (m != newNode.score.intValue()){
									System.out.println("prev score:" + m);
									System.out.println("current score:" + newNode.score.intValue());
									System.out.println();
								}
								
							}
							*/
							
						}
					}
				}
			}
		}

		
	
		public int maxDepth(Node n, int level) {
			
			if(level < 12){
				if (n != null){
					//ChessBoardWithColumnsAndRows.nodesPerLevel[level]++;
					int d = level;
					if (n.children != null){
						Set<Long> keys = n.children.keySet();
						Iterator<Long> it = keys.iterator();
						for(int i = 0; i < n.children.size(); i++){
							Node tempNode = n.children.get(it.next());
							if (tempNode != null && level == parent.level + 1){
								d = Math.max(d, maxDepth(tempNode, level + 1));
							}
							
						}
					}
					return d;
				}
				else{
					//ChessBoardWithColumnsAndRows.nullsPerLevel[level]++;
					return 0;
				}
			}
			else{
				return 0;
			}
			
		}

		
		public Node[] nodeArrayConcat(Node[] a, Node[] b) {
			int aLen = a.length;
			int bLen = b.length;
			Node[] c= new Node[aLen+bLen];
			System.arraycopy(a, 0, c, 0, aLen);
			System.arraycopy(b, 0, c, aLen, bLen);
			return c;   
		}
		
	
		/*
		
		public void printTreePath(int depth, long time, boolean whiteMove, long [] tacticalBoard) {
			System.out.println("depth: " + depth);
			System.out.println((children == null));
			if (children != null && depth > 1){
				int nextNode = 0;
				Set<Long> keys = children.keySet();
				Iterator<Long> it = keys.iterator();
				ArrayList<Node> tempList = new ArrayList<Node>();
				for (int i = 0; i < children.size(); i++){
					Node tempNode = children.get(it.next());
					if (tempNode != null){
						System.out.println("child score: " + tempNode.score + ", root score: " + (root.score) + ", level: " + tempNode.level ); //", chldnull: " + (children.get(0) == null));// - bitBoardOperations.tacticalPositionValue(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board, bitBoardOperations.tacticalMapMaker(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board), null)));
						if (tempNode.score == root.score){
							nextNode = i;
						}
					}
				}
				System.out.println("Path Depth: " + depth);
				System.out.println("whiteMove: " + (whiteMove));
				System.out.println("board score^: " + bitBoardOperations.boardScore(tempList.get(nextNode).board));
				bitBoardOperations.printBoardLevel(tempList.get(nextNode).board[0] | tempList.get(nextNode).board[7]);
				if (depth == 2){
					tempList.get(nextNode).printTreePath(depth - 1, time, !whiteMove, tempList.get(nextNode).board);
				}
				if (depth != 1){
					tempList.get(nextNode).printTreePath(depth - 1, time, !whiteMove, null);
				}
			}
			
			
			if (children != null && depth == 1){
				Set<Long> keys = children.keySet();
				Iterator<Long> it = keys.iterator();
				ArrayList<Node> tempList = new ArrayList<Node>();
				loop:
				for (int i = 0; i < children.size(); i++){
					Node tempNode = children.get(it.next());
					if (tempNode != null){
						System.out.println("child score: " + tempNode.score + ", root score: " + (root.score) + ", level: " + tempNode.level + ", chldnull: ");// + (children.get(0) == null));// - bitBoardOperations.tacticalPositionValue(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board, bitBoardOperations.tacticalMapMaker(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board), null)));
						if (tempNode.score == root.score  - bitBoardOperations.tacticalPositionValue(board, bitBoardOperations.tacticalMapMaker(this, whiteMove), null)){
							System.out.println("Path Depth: " + depth);
							System.out.println(tempNode.key);
							System.out.println("whiteMove: " + (whiteMove));
							System.out.println("board score^: " + bitBoardOperations.boardScore(tempNode.board));
							bitBoardOperations.printBoardLevel(tempNode.board[0] | tempNode.board[7]);	
							
							/*
							
							AIv2Tree tempTree = new AIv2Tree();
							Node node = new Node();
							
							node.board = children.get(i).board;
							node.level = children.get(i).level;
							node.score = children.get(i).score;
							node.key = children.get(i).key;
 					                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             

							tempTree.setRoot(node);
							
							
							tempTree.root.dynamicSearch(2, time, !whiteMove, 0L);
							dynamicMiniMax(tempTree.root, 2, -1000000000, 1000000000, !whiteMove);
							
							tempTree.root.printTreePath(depth - 1, time, !whiteMove, tacticalBoard);
							
							break loop;
							
							
							for (int k = 0; k < tempTree.root.children.size(); k++){
								if (tempTree.root.children[k] != null){
									System.out.println("child score: " + tempTree.root.children[k].score + ", root score: " + (ChessBoardWithColumnsAndRows.tree.root.score) + ", level: " + tempTree.root.children[k].level + ", chldnull: " + (tempTree.root.children[k] == null));// - bitBoardOperations.tacticalPositionValue(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board, bitBoardOperations.tacticalMapMaker(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board), null)));
									if (children[k].score.intValue() == ChessBoardWithColumnsAndRows.tree.root.score.intValue()  - bitBoardOperations.tacticalPositionValue(board, bitBoardOperations.tacticalMapMaker(board), null)){
										System.out.println("Path Depth: " + 0);
										System.out.println("board score^: " + bitBoardOperations.boardScore(children[k].board));
										bitBoardOperations.printBoardLevel(children[k].board[0] | children[k].board[7]);
										if (tempTree.root.children[k].children != null){
											for (int j = 0; j < tempTree.root.children[k].children.size(); j++){
												if (tempTree.root.children[k].children.get(j) != null){
													System.out.println("child score: " + tempTree.root.children[k].children.get(j).score + ", root score: " + (ChessBoardWithColumnsAndRows.tree.root.score) + ", level: " + tempTree.root.children[k].children.get(j).level + ", chldnull: " + (tempTree.root.children[k].children.get(j) == null));// - bitBoardOperations.tacticalPositionValue(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board, bitBoardOperations.tacticalMapMaker(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board), null)));
													if (children[k].children.get(j) != null){
														if (children[k].children.get(j).score.intValue() == ChessBoardWithColumnsAndRows.tree.root.score.intValue()  - bitBoardOperations.tacticalPositionValue(board, bitBoardOperations.tacticalMapMaker(board), null)){
															System.out.println("Path Depth: " + -1);
															System.out.println("board score^: " + bitBoardOperations.boardScore(children[k].children.get(j).board));
															bitBoardOperations.printBoardLevel(children[k].children.get(j).board[0] | children[k].children.get(j).board[7]);
															//break;
														}
													}
												}
											}
											//break;
										}
									}
								}
							}			
					
							
							//break;
						}
					}
				}
			}
			
			/*
			if (children != null && depth > -2 && depth < 1){
				loop:
				for (int k = 0; k < children.size(); k++){
					if (children[k] != null){
						System.out.println("child score: " + children[k].score + ", root score: " + (ChessBoardWithColumnsAndRows.tree.root.score) + ", level: " + children[k].level + ", chldnull: " + (children[k] == null));// - bitBoardOperations.tacticalPositionValue(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board, bitBoardOperations.tacticalMapMaker(ChessBoardWithColumnsAndRows.tree.root.children.get(j).board), null)));
						
						if (children[k].score.intValue() == ChessBoardWithColumnsAndRows.tree.root.score.intValue()  - bitBoardOperations.tacticalPositionValue(tacticalBoard, bitBoardOperations.tacticalMapMaker(tacticalBoard), null)){
							System.out.println((tacticalBoard == null));
							
							/*
							if (ChessBoardWithColumnsAndRows.boards.containsKey(children.get(i).key)){
								Node g = ChessBoardWithColumnsAndRows.boards.get(children.get(i).key);
							
								if (g.level != children.get(i).level){
									children.get(i) = null;
								}
								else{
									
									children.get(i) = g;
									ChessBoardWithColumnsAndRows.hashNodesCalled++;
								
								}	
							}
							
							
							
							System.out.println("Path Depth: " + (depth));
							System.out.println(children[k].key);
							System.out.println("whiteMove: " + (whiteMove));
							System.out.println("board score^: " + bitBoardOperations.boardScore(children[k].board));
							bitBoardOperations.printBoardLevel(children[k].board[0] | children[k].board[7]);
							children[k].printTreePath(depth - 1, time, !whiteMove, tacticalBoard);
							break loop;
						}
					}
				}
				
			}
			
	
			
		}
		
	*/
		
		public void deleteNode(Node n){
			n = null;
		}
		
		public void printNodes(Node n, int level){
			if (level < 3){
				System.out.println("depth: " + level + ", tree score: " + n.score + ", board score: " + bitBoardOperations.boardScore(n.board));
				//System.out.println(Long.bitCount(board[0] | board[7]));
				//bitBoardOperations.printBoardLevel(n.board[0] | n.board[7]);
				//System.out.println((ChessBoardWithColumnsAndRows.boards.containsKey(key)));
				Set<Long> keys = n.children.keySet();
				Iterator<Long> it = keys.iterator();
				if (n != null && n.children != null){
					Node tempNode = n.children.get(it.next());
					for (int i = 0; i < n.children.size(); i++){
						tempNode.printNodes(tempNode, level + 1);
					}
				}
			}
		}
		
		
		
		
		public scoreBoard dynamicSearch(int depth, int alpha, int beta, boolean maximizingPlayer, long p, boolean wcr, boolean wcl, boolean bcr, boolean bcl, boolean tactical, boolean dSearch){
			
			listAndTactical list1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);
			ArrayList<moveScoreTuple>possibleMoves1 = list1.list;
			//ArrayList<moveScoreTuple>possibleMoves1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);							
			int size1 = possibleMoves1.size();
			
			listAndTactical list2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
			ArrayList<moveScoreTuple>possibleMoves2 = list2.list;
			
			//ArrayList<moveScoreTuple>possibleMoves2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
			int size2 = possibleMoves2.size();
			
			int thisScore = boardScore + size1 - size2;// + bitBoardOperations.tacticalPositionValue(board, list2.tacticalBoard, list1.tacticalBoard);
			
			
			//int thisScore = boardScore + size1 - size2;
		
			scoreBoard sb = new scoreBoard(0);
			if (depth <= 0 || (level != parent.level + 1)){
				//System.out.println("this dynamic");
				sb.score = thisScore;
				sb.key = key.longValue();;
				return sb;
			}
			if (board[bitBoardOperations.BK] == 0 && board[bitBoardOperations.WK] != 0){
				sb.score =  1000000;
				sb.key = key.longValue();;
				return sb;
			}
			else if (board[bitBoardOperations.WK] == 0 && board[bitBoardOperations.BK] != 0){
				sb.score =  -1000000;
				sb.key = key.longValue();
				return sb;
			}
			else{
			
				ArrayList<moveScoreTuple> possibleMoves = null;
				
				if (maximizingPlayer){

					scoreBoard bestKey = null;
					scoreBoard returnKey;
					
					if (thisScore >= beta){
						//sb = new scoreBoard(beta);
						sb = new scoreBoard(thisScore);
						sb.key = key.longValue();
						return sb;
					}
					if (alpha < thisScore){
						alpha = thisScore;
						//bestKey = new scoreBoard(thisScore);
						//bestKey.key = key;
						//bestKey.score = thisScore;
					}
					possibleMoves = possibleMoves1;
					ArrayList<moveScoreTuple> tuples = new ArrayList<moveScoreTuple>();
					
					
					int size = possibleMoves.size();
					for (int i = 0; i < size; i++){
						if ((possibleMoves.get(i).move[2] & board[7]) != 0){
							tuples.add(possibleMoves.get(i));
						}
					}
				
					if (children == null){
						children = new HashMap<Long, Node>((4*size)/3);
					}

					int le = 2*ChessBoardWithColumnsAndRows.difficulty - depth;
					
					for (int i = 0; i < tuples.size(); i++){
						
						Node newNode = new Node();
						ChessBoardWithColumnsAndRows.tempNodesPerLevel[le]++;
						
						newNode.key = key.longValue();
						
						newNode.level = level + 1;
						
						int piece = tuples.get(i).move[0].intValue();
						long end = tuples.get(i).move[2];
						int takenPiece = tuples.get(i).takenPiece;
					
						int s = tuples.get(i).move[3].intValue();
						int e = tuples.get(i).move[4].intValue();

						int index = 64*(piece - 1);
						
						newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + e]);
						newNode.key = newNode.key | bitBoardOperations.bitConstants[0];

						
						
						if ((board[bitBoardOperations.B] & end) != 0){
							/*
							if (takenPiece == null){
								l1:
								for (int t = 8; t < 14; t++){
    								if ((end & board[t]) != 0){
    									takenPiece = t;
    									break l1;
    								}
    							}
								System.out.println("tactical: " + (tactical));
								for (int g = 0; g < 14; g++){
									System.out.println("movenull: " + board == null);
									//System.out.println("piece: " + tuples.get(i).move[1].intValue());
									if ((board[g] & tuples.get(i).move[1]) != 0 ){
										System.out.println("start board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
									if ((board[g] & tuples.get(i).move[2]) != 0 ){
										System.out.println("end board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
								}
								
								System.out.println("whitemove: " + (maximizingPlayer));
								System.out.println(Arrays.toString(tuples.get(i).move));
								bitBoardOperations.printBoardLevel(tuples.get(i).move[1]);
								bitBoardOperations.printBoardLevel(tuples.get(i).move[2]);
								System.out.println(tuples.get(i).score);
								System.out.println("whi1");
								
							}
							*/
							newNode.key =  (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(takenPiece - 2) + e]);
						
						}					
						newNode = bitBoardOperations.bitPromotionCheck(end, newNode, board, true, e);
						
						
						
						if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) != null){
							Node g = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
							
							tuples.get(i).score = g.score;
							g.level = Math.min(g.level, newNode.level);
							newNode = g;
							
							if (children.get(newNode.key) == null){
								children.put(newNode.key, g);
							}
							
						}
						else{
							
							
							
							newNode.boardScore = boardScore - bitBoardOperations.pieceVals[takenPiece];
							newNode.board = Arrays.copyOf(board, 14);
							
							newNode.parent = this;			
							
							newNode.pawnKey = pawnKey.longValue();
							
							tuples.get(i).score = newNode.boardScore;
						}
						tuples.get(i).node = newNode;
					}
						
					Collections.sort(tuples, whiteTupleComparator);
					
					int lev = 2*ChessBoardWithColumnsAndRows.difficulty - depth;
					
					for (int i = 0; i < tuples.size(); i++){
						
						Node newNode = tuples.get(i).node;
						if (!ChessBoardWithColumnsAndRows.boards.containsKey(newNode.key)){
							ChessBoardWithColumnsAndRows.nodesPerLevel[lev]++;
							
							int piece = tuples.get(i).move[0].intValue();
							long start = tuples.get(i).move[1];
							long end = tuples.get(i).move[2];
							int takenPiece = tuples.get(i).takenPiece;
							int s = tuples.get(i).move[3].intValue();
							int e = tuples.get(i).move[4].intValue();
							
							newNode.board[bitBoardOperations.W] = (start ^ (newNode.board[bitBoardOperations.W] ^ end));
					
							newNode.board[piece] = ((newNode.board[piece] ^ end) ^ start);
						
							if (piece == 1){
								newNode.pawnKey = ((newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[e]);
							}
							if (takenPiece != 0){
								newNode.board[bitBoardOperations.B] = (newNode.board[bitBoardOperations.B] ^ end);
								newNode.board[takenPiece] = (newNode.board[takenPiece] ^ end);
								
								if (takenPiece == 8){
									newNode.pawnKey = (newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[384 + e]);
								}
							}	
							newNode = bitBoardOperations.promotionCheck(start, end, newNode, true, e);
						
							ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
							if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
								ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
								ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
								if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
									ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
									ChessBoardWithColumnsAndRows.boards.put(newNode.key,newNode);
									if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
										ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
										ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
									}
								}
							}		
							
						//	ChessBoardWithColumnsAndRows.references.add(newNode);
						
							/*
							if (((start & board[bitBoardOperations.WP]) != 0) && (end == (start << 16))){
								p = end; 
							}
							*/
							
							children.put(newNode.key, newNode);
							
						}
						
						returnKey = newNode.dynamicSearch(depth -1, alpha, beta, false, p, wcr, wcl, bcr, bcl, tactical, dSearch);
						
						if (returnKey.score >= beta){
							//returnKey.score = beta;
							sb = new scoreBoard(returnKey.score);
							sb.key = newNode.key.longValue();
							return sb;
						}
						if (returnKey.score > alpha){
							alpha = returnKey.score.intValue();
							bestKey = returnKey;
							bestKey.key = newNode.key;
						}
						
						newNode.scoreSet(returnKey.score);	
			
					}
					
					if (bestKey == null){
					
						scoreBoard s = new scoreBoard(thisScore); 
						s.key = key.longValue();
						
						/*
						ArrayList<moveScoreTuple>p1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);
						int s1 = p1.size();
						
						ArrayList<moveScoreTuple>p2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
						int s2 = p2.size();
						
						s.score = bitBoardOperations.boardScore(board) + s1 - s2;
						
						*/
						
						/*
						if (tuples.size() == 0 || children.size() == 0){
							
							
							
						}
						/*
						else{
							//System.out.println("dynamic keynull depth: " + depth);
							s.score = beta;
						}
					 */
						return s;
					}
					else{
						return bestKey;
					}
					
				}
				
				else {
					scoreBoard bestKey = null;
					scoreBoard returnKey;
					
					if (thisScore <= alpha){
						//sb = new scoreBoard(alpha);
						sb = new scoreBoard(thisScore);
						sb.key = key.longValue();
						return sb;
					}
					if (thisScore < beta){
						beta = thisScore;
						//bestKey = new scoreBoard(thisScore);
						//bestKey.key = key;
					}
					
					possibleMoves = possibleMoves2;
					
					
					int size = possibleMoves.size();
					
					ArrayList<moveScoreTuple> tuples = new ArrayList<moveScoreTuple>();

					
					for (int i = 0; i < size; i++){
						if ((possibleMoves.get(i).move[2] & board[0]) != 0){
							tuples.add(possibleMoves.get(i));
							
						}
					}		
			
					if (children == null){
						children = new HashMap<Long, Node>((possibleMoves.size()*4)/3);
					}
					
					int le = 2*ChessBoardWithColumnsAndRows.difficulty - depth;
					for (int i = 0; i < tuples.size(); i++){
						Node newNode = new Node();
						ChessBoardWithColumnsAndRows.tempNodesPerLevel[le]++;
						
						newNode.key = key.longValue();
						
						newNode.level = level + 1;
						
						int piece = tuples.get(i).move[0].intValue();
						long end = tuples.get(i).move[2];
						int takenPiece = tuples.get(i).takenPiece;
						
						int s = tuples.get(i).move[3].intValue();
						int e = tuples.get(i).move[4].intValue();

						int index = 64*(piece - 2);
						
						newNode.key = ((newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + s])
                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[index + e]);
						newNode.key = newNode.key & ~bitBoardOperations.bitConstants[0];
						
						if ((board[bitBoardOperations.W] & end) != 0){
							/*
							if (takenPiece == null){
								for (int t = 1; t < 7; t++){
    								if ((end & board[t]) != 0){
    									takenPiece = t;
    									break;
    								}
    							}
								System.out.println("tactical: " + (tactical));
								for (int g = 0; g < 14; g++){
									System.out.println("movenull: " + board == null);
									//System.out.println("piece: " + tuples.get(i).move[1].intValue());
									if ((board[g] & tuples.get(i).move[1]) != 0 ){
										System.out.println("start board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
									if ((board[g] & tuples.get(i).move[2]) != 0 ){
										System.out.println("end board: " + g);
										bitBoardOperations.printBoardLevel(board[g]);
									}
								}
								
								System.out.println("whitemove: " + (maximizingPlayer));
								System.out.println(Arrays.toString(tuples.get(i).move));
								bitBoardOperations.printBoardLevel(tuples.get(i).move[1]);
								bitBoardOperations.printBoardLevel(tuples.get(i).move[2]);
								System.out.println(tuples.get(i).score);
								System.out.println("whi1");
								
								}
							int val = takenPiece.intValue();
							*/
							
							newNode.key = (newNode.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64*(takenPiece - 1) + e]) ;
							
						}		
						
						
						newNode = bitBoardOperations.bitPromotionCheck(end, newNode, board, false, e);
					
						
						if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) != null){
							Node g = ChessBoardWithColumnsAndRows.boards.get(newNode.key);
							
							tuples.get(i).score = g.score;
							g.level = Math.min(g.level, newNode.level);
							newNode = g;
							
							if (children.get(newNode.key) == null){
								children.put(newNode.key, g);
							}
							
						}
						else{
							
							newNode.boardScore = boardScore - bitBoardOperations.pieceVals[takenPiece];
							newNode.board = Arrays.copyOf(board, 14);
							
							newNode.parent = this;

							newNode.pawnKey = pawnKey.longValue();
							
							tuples.get(i).score = newNode.boardScore;
						}
						tuples.get(i).node = newNode;
						
					}
					
					
					Collections.sort(tuples, blackTupleComparator);
					
					int lev = 2*ChessBoardWithColumnsAndRows.difficulty - depth;
					
					for (int i = 0; i < tuples.size(); i++){
						
						Node newNode = tuples.get(i).node;
					
						if (!ChessBoardWithColumnsAndRows.boards.containsKey(newNode.key)){
							ChessBoardWithColumnsAndRows.nodesPerLevel[lev]++;
							
							int piece = tuples.get(i).move[0].intValue();
							long start = tuples.get(i).move[1];
							long end = tuples.get(i).move[2];
							int takenPiece = tuples.get(i).takenPiece;
							int s = tuples.get(i).move[3].intValue();
							int e = tuples.get(i).move[4].intValue();
							
						
							newNode.board[bitBoardOperations.B] = (start ^ (newNode.board[bitBoardOperations.B] ^ end));
							
							newNode.board[piece] = ((newNode.board[piece] ^ end ) ^ start);
							
							if (piece == 8){
								newNode.pawnKey = ((newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[384 + s])
		                                ^ ChessBoardWithColumnsAndRows.pieceRandoms[384 + e]);
							}
							
							if (takenPiece != 0){
								newNode.board[bitBoardOperations.W] = (newNode.board[bitBoardOperations.W] ^ end);
								newNode.board[takenPiece] = (newNode.board[takenPiece] ^ end);
								if (takenPiece == 1){
									newNode.pawnKey = (newNode.pawnKey ^ ChessBoardWithColumnsAndRows.pieceRandoms[e]);
								}
							}	
								
							newNode = bitBoardOperations.promotionCheck(start, end, newNode, false, e);
							
							ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
							if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
								ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
								ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
								if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
									ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
									ChessBoardWithColumnsAndRows.boards.put(newNode.key,newNode);
									if (ChessBoardWithColumnsAndRows.boards.get(newNode.key) !=  newNode){
										ChessBoardWithColumnsAndRows.boards.remove(newNode.key);
										ChessBoardWithColumnsAndRows.boards.put(newNode.key, newNode);
									}
								}
							}		
							
							//ChessBoardWithColumnsAndRows.references.add(newNode);
							
							/*
							if ((start & board[bitBoardOperations.BP]) != 0 && (end == (start >> 16))){
								p = end; 
							}
							*/
							
							children.put(newNode.key, newNode);
							
						}
						
						returnKey = newNode.dynamicSearch(depth -1, alpha, beta, true, p, wcr, wcl, bcr, bcl, tactical, dSearch);
						
						newNode.scoreSet(returnKey.score);
						
						if (returnKey.score <= alpha){
							//sb = new scoreBoard(alpha);
							sb = new scoreBoard(returnKey.score);
							sb.key = newNode.key.longValue();
							return sb;
						}
						if (returnKey.score < beta){
							beta = returnKey.score.intValue();
							bestKey = returnKey;
							bestKey.key = newNode.key.longValue();
							
						}
					}
				
					if (bestKey == null){
						
						scoreBoard s = new scoreBoard(thisScore); 
						s.key = key.longValue();
						/*
						ArrayList<moveScoreTuple>p1 = bitBoardOperations.completeMoveGen2(board, true, wcr, wcl, bcr, bcl);
						int s1 = p1.size();
						
						
						ArrayList<moveScoreTuple>p2 = bitBoardOperations.completeMoveGen2(board, false, wcr, wcl, bcr, bcl);
						int s2 = p2.size();
						*/
					
						/*
						
						if (tuples.size() == 0 || children.size() == 0){
							
							
							
						}
						
						/*
						else{
							//System.out.println("null not expected white black: " + depth);
							s.score = alpha;
						}
						*/
						return s;
					}
					else{
						return bestKey;
					}
					
			
				}

			}
		}
	}

		
	public int printNodeNumber(int depth) {
		int num = 0;
		if (this.getRoot() != null){
			num = this.getRoot().printNodeNumber(depth);
		}
		return num;
	}
	
	public void copyTree(int depth, long newKey, long[] bitBoardGend, boolean whiteMove){
		if (this.getRoot() != null){
			this.root.copyTree(depth, newKey, bitBoardGend, whiteMove);
			ChessBoardWithColumnsAndRows.copiesCalled++;
		}
	}
	
	public void rootSet(Node n){
		this.setRoot(n);
	}


	public Node getRoot() {
		return this.root;
	}
	
	public void deleteSubTree(Node n, boolean whiteMove){
		this.root.deleteSubTree(n, whiteMove);
	}
	
	public Node getNextRoot() {
		return ChessBoardWithColumnsAndRows.tree.nextRoot;
	}

	public void setRoot(Node root) {
		this.root = root;
	}
	
	public void setNextRoot(Node nextRoot) {
		ChessBoardWithColumnsAndRows.tree.nextRoot = nextRoot;
	}
	
	/*
	public void printTreePath(int depth, boolean whiteMove, long[] tacticalBoard){
		if (this.getRoot() != null){
			this.root.printTreePath(depth, System.currentTimeMillis(),  whiteMove, tacticalBoard);
		}
	}
	*/
	
	public void extendTree(int depth, long time, boolean whiteMove, boolean dSearch, boolean wcr, boolean wcl, boolean bcr, boolean bcl){
		if (this.getRoot() != null){
			long l = 0L;
			this.root.extendTree(depth, time, l, whiteMove, dSearch, wcr, wcl, bcr, bcl);
		}
	}

	public static int maxDepth(AIv2Tree t){
	    	return t.getRoot().maxDepth(t.getRoot(), 0);
	}
	
  
	public static class listAndTactical{
		ArrayList<moveScoreTuple> list;
		long tacticalBoard;
	}
	
	public class scoreBoard{
		
		public scoreBoard (Integer i){
			this.score = i;
		}
		
		Long key;
		Integer score;
		
	}
    
    public static class moveScoreTuple {
    	Long [] move;
    	int score;
    	
    	Node node;
    	
    	int takenPiece;
    	
    	int getScore(){
    		return score;
    	}
    	Long [] getMove(){
    		return move;
    	}
    	void setScore(int scr){
    		score = scr;
    	}
    	void setMove(Long[] m){
    		move = m;
    	}
    	
    }


}
