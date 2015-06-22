package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.lang.Long;

import chess.AIv2Tree.Node;

public class test {

	//public static long[] bitConstants = bitBoardOperations.bitConstants();
	
	public static void main(String[] args) {
		
		bitBoardOperations.printBoardLevel(bitBoardOperations.circle);
		
		/*
		int moveNum = 1;
		
		long [] arr = new long [14];
		
		//arr[bitBoardOperations.BB] = bitBoardOperations.bitConstants[5];
		
		arr[bitBoardOperations.W] = bitBoardOperations.bitConstants[50];
				/*
				| bitBoardOperations.bitConstants[1]
				| bitBoardOperations.bitConstants[8];
	
		arr[bitBoardOperations.WP] =  bitBoardOperations.bitConstants[60];
		System.out.println("level");
		bitBoardOperations.printBoardLevel(arr[bitBoardOperations.WP] >> 8 );
		
		//arr[bitBoardOperations.BP] =  bitBoardOperations.bitConstants[9];
		//arr[bitBoardOperations.B] =  bitBoardOperations.bitConstants[9];
				
		//arr[bitBoardOperations.BQ] =  bitBoardOperations.bitConstants[63];
		
		//arr[bitBoardOperations.W] =  arr[bitBoardOperations.W] | bitBoardOperations.bitConstants[17];
		
		bitBoardOperations.printBoardLevel(arr[bitBoardOperations.WP]);
	//	bitBoardOperations.printBoardLevel(arr[bitBoardOperations.BR] >> 2);
	//	bitBoardOperations.printBoardLevel(arr[bitBoardOperations.BR] >>> 2);
		bitBoardOperations.printBoardLevel(arr[bitBoardOperations.W]);
		
		ArrayList<long[]> possibleMoves = new ArrayList<long[]>(65);
		//possibleMoves = bitBoardOperations.completeMoveGen(arr, moveNum, true);
		
		long brr = 0;
		
		System.out.println(possibleMoves.size());
		
		for (int i = 0; i < possibleMoves.size(); i++){
			long [] b = possibleMoves.get(i);
			brr = brr | b[1];
			//bitBoardOperations.printBoardLevel(brr);
		}
		
		bitBoardOperations.printBoardLevel(brr);
		
	
	    
	    System.out.println(Long.toBinaryString(temp));
	    System.out.println(Long.toBinaryString(temp << 1));
	    
	    System.out.println(Long.bitCount(whitepieces));
	    System.out.println(Long.numberOfLeadingZeros(whitepieces));
	    System.out.println(Long.numberOfTrailingZeros(whitepieces));
	    //System.out.println(Long.numberOfLeadingZeros(bitConstants[63]));
	    //System.out.println(Long.numberOfLeadingZeros(bitConstants[0]));
	    
	    
	   //String(whitepieces));
	    System.out.println();
	    
	   // bitBoardOperations.bitConstants();

		
		long l = ~(bitBoardOperations.bitConstants[0] 
	    			| bitBoardOperations.bitConstants[1] | bitBoardOperations.bitConstants[2] | bitBoardOperations.bitConstants[3]);
		
		bitBoardOperations.printBoardLevel(l);
		
		l = l + bitBoardOperations.bitConstants[2];
		
		bitBoardOperations.printBoardLevel(l);
		l = l + bitBoardOperations.bitConstants[2];
	
		bitBoardOperations.printBoardLevel(l);
		l = l + bitBoardOperations.bitConstants[2];
		bitBoardOperations.printBoardLevel(l);
		l = l + bitBoardOperations.bitConstants[2];
		
		//l = l >>> 1;
		l = l >> 1;
		bitBoardOperations.printBoardLevel(l);
		l = l + bitBoardOperations.bitConstants[2];
		bitBoardOperations.printBoardLevel(l);
		l = l + bitBoardOperations.bitConstants[2];
		bitBoardOperations.printBoardLevel(l);
		l = l + bitBoardOperations.bitConstants[2];
		bitBoardOperations.printBoardLevel(l);
		//l = l + bitBoardOperations.bitConstants[2];
		bitBoardOperations.printBoardLevel(bitBoardOperations.bitConstants[0]);
		bitBoardOperations.printBoardLevel(bitBoardOperations.bitConstants[0]>>1);
		bitBoardOperations.printBoardLevel(bitBoardOperations.bitConstants[0]>>>1);
		
		bitBoardOperations.printBoardLevel(bitBoardOperations.bitConstants[63]);
		bitBoardOperations.printBoardLevel(bitBoardOperations.bitConstants[63]<<11);
		bitBoardOperations.printBoardLevel(bitBoardOperations.bitConstants[63]<<11);
		
		int arr1[] = { 0, 1, 21, 3, 4, 5 , 8};
	     int arr2[] = { 0, 11, 20, 30, 40, 50, 80 };
	   
	     // copies an array from the specified source array
	     System.arraycopy(arr1, 0, arr2, 0, 1);
	     System.out.print("array2 = ");
	     System.out.print("arr2: " + Arrays.toString(arr2));
	     System.out.print("arr1: " + Arrays.toString(arr1));
	     
	     Node[] N = new Node[1];
	     System.out.println("null: " + (N == null));
	     System.out.println("null: " + (N[0] == null));
	     
		//System.out.println(s.lastIndexOf("i"));
		
	     int [] child = {1, 2};
	     int x = child[0];
	     child = null;
	     System.out.println(x);
	     //System.out.println(child[0]);
	     
	   //  List h = new ArrayList(5);
	     
	     int g = 1;
	     for (int i = 0; i < 6; i++){
	    	 if (i < 2){
	    		g = g * 20; 
	    		//b = g * 20
	    	 }
	    	 else{
		    	 if (i % 2 == 1){
		    		 g = g * 3;
		    	 }
		    	 else{
		    		 g = g * 30;
		    	 }
	    	 }
	    	 System.out.println("turn " + (i + 1));
	    	 System.out.println("i: " + g);
	    }
	     

	     boolean b = true;
	     System.out.println(b);
	     b = !b;
	     System.out.println(b);
	     b = !b;
	     System.out.println(b);
	     
	     
	    Integer r = new Integer(1);
	    r++;
	    System.out.println("R: " + r);
	    */
	    /* 
	    Random randomno = new Random();
	    
	    ArrayList<String> contestants =  new ArrayList(20);
	    
	    String[] input = {"Akachi Patricia Ukwu", "Isabel Bransky", "Hilary Mogul", "Michelle Snyder"};
	  
	    int n = randomno.nextInt(input.length);
	    
	    System.out.println(input[n]);
	    */
		
		/*
	    ArrayList<Integer> arr1 = new  ArrayList<Integer>(5);
	    arr1.add(-1);
	    arr1.add(0);
	    arr1.add(1);
	    arr1.add(2);
	    
	    System.out.println(Arrays.toString(arr1.toArray()));
	    
	    ArrayList arr2 = new  ArrayList(3);
	    
	    arr2.addAll(1, arr1);
	    System.out.println(Arrays.toString(arr2.toArray()));
	    */
		/*
		Long a = bitBoardOperations.bitConstants[5];
		
		long b = bitBoardOperations.bitConstants[5];
		
		System.out.println((a == b));
		
		long [] c = null;
		System.out.println(Arrays.toString(c));
		
		int[][] positions = new int[3][12];
		for (int i = 0; i < 3; i++){
			for (int j = 0; j < 12; j++){
				positions[i][j] = i + j;
			}
		}
		
		long t = System.currentTimeMillis();
		
		
		
		int[][] p = new int[3][12];
		
		System.arraycopy(positions[0], 0, p[0], 0, 12);
		System.arraycopy(positions[1], 0, p[1], 0, 12);
		System.arraycopy(positions[2], 0, p[2], 0, 12);
		
		System.out.println(System.currentTimeMillis() - t);
		
		System.out.println(Arrays.deepToString(p));
		
		/*
		Long g = null;
		int b = g.intValue();
		System.out.println(b);
		System.out.println(b == 0);
		
		
		int a = 101;
		a = 100*(a/100);
		System.out.println(a);
		*/
		/*
		long [] l1 = new long[14];
		
		for (int i = 0; i < 14; i++){
			l1[i] = System.currentTimeMillis() / (1 + i*i);
		}
		
		long l1start = System.currentTimeMillis();
		
		for (int i = 0; i < 100000; i++){
			
			long [] l2 = new long[14];
			
			System.arraycopy(l1, 0, l2, 0, 14);
		}
		long l1end = System.currentTimeMillis();
		
		System.out.println("time1 : " + (l1end - l1start));
	
		
		long l2start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++){
			long[] l3 = Arrays.copyOf(l1, 14);
		}
		long l2end = System.currentTimeMillis();
		
		System.out.println("time2 : " + (l2end - l2start));
		
		int in = 0;
		long start1 = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++){
			
			in =  + Long.bitCount((System.currentTimeMillis() & in));

		}
		long end1 = System.currentTimeMillis();
		
		System.out.println("time 89 : " + (end1- start1));
		
		int inr = 0;
		long start2 = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++){
			int r = Long.bitCount((System.currentTimeMillis() & inr));
			inr = inr + (r) ;
		}
		long end2 = System.currentTimeMillis();
		
		System.out.println("time1 : " + (end2- start2));
		
		
		long in2 = 0;
		long start3 = System.currentTimeMillis();
		for (int i = 0; i < 30000000; i++){
			
			in2 = in2 | Long.numberOfTrailingZeros(System.currentTimeMillis());
		}
		long end3 = System.currentTimeMillis();
		
		System.out.println(in2);
		System.out.println("time3 : " + (end3 - start3));
		
		
		int in3 = 0;
		long start4 = System.currentTimeMillis();
		for (int i = 0; i < 30000000; i++){
			
			in3 = in3 + i;
		}
		long end4 = System.currentTimeMillis();
		
		System.out.println(in3);
		System.out.println(in2);
		System.out.println("time3 : " + (end4 - start4));
		
		int [][] a = new int [2][2];
		a[0][0] = 1;
		a[0][1] = 2;
		a[1][0] = 3;
		a[1][1] = 4;
		
		System.out.println(Arrays.deepToString(a));
		
		int [][] b = Arrays.copyOf(a, 2);
		
		System.out.println(Arrays.deepToString(b));
		
		a[0][0] = 9;
		System.out.println(Arrays.deepToString(b));
		*/
		
	
		
		//long[] arr1 = new long[100];
		//long [][] arr2 = new long[10][10];
		
		
		//for (int i = 0; i < 100; i ++){
			
		//}
		
		
		long  in5 =0;
		
		long start5 = System.currentTimeMillis();
		for (int i = 0; i < 30000000; i++){
			int numWP = Long.bitCount(System.currentTimeMillis());
			int numWN = Long.bitCount(System.currentTimeMillis());
			int numWB = Long.bitCount(System.currentTimeMillis());
			int numWR = Long.bitCount(System.currentTimeMillis());
			int numWQ = Long.bitCount(System.currentTimeMillis());
			int numWK = Long.bitCount(System.currentTimeMillis());

			int numBP = Long.bitCount(System.currentTimeMillis());
			int numBN = Long.bitCount(System.currentTimeMillis());
			int numBB = Long.bitCount(System.currentTimeMillis());
			int numBR = Long.bitCount(System.currentTimeMillis());
			int numBQ = Long.bitCount(System.currentTimeMillis());
			int numBK = Long.bitCount(System.currentTimeMillis());

			in5 = (numWP * bitBoardOperations.pieceVals[bitBoardOperations.WP] + numWN * bitBoardOperations.pieceVals[bitBoardOperations.WN] + numWB
							* bitBoardOperations.pieceVals[bitBoardOperations.WB] + numWR * bitBoardOperations.pieceVals[bitBoardOperations.WR] + numWQ
							* bitBoardOperations.pieceVals[bitBoardOperations.WQ] + numWK * bitBoardOperations.pieceVals[bitBoardOperations.WK])
					+ (numBP * bitBoardOperations.pieceVals[bitBoardOperations.BP] + numBN * bitBoardOperations.pieceVals[bitBoardOperations.BN] + numBB
							* bitBoardOperations.pieceVals[bitBoardOperations.BB] + numBR * bitBoardOperations.pieceVals[bitBoardOperations.BR] + numBQ
							* bitBoardOperations.pieceVals[bitBoardOperations.BQ] + numBK * bitBoardOperations.pieceVals[bitBoardOperations.BK]);
		
		}
		long end5 = System.currentTimeMillis();
		
		System.out.println(in5);
		System.out.println("time5 : " + (end5 - start5));
		
		
		long in6 = 0;
		
		//int num = 8;
		//int sq = 0;
		long start6 = System.currentTimeMillis();
		for (int i = 0; i < 30000000; i++){
			in6 = in6 + bitBoardOperations.pieceVals[bitBoardOperations.BQ];
		}
		long end6 = System.currentTimeMillis();
		
		System.out.println(in5);
		System.out.println(in6);
		System.out.println("time6 : " + (end6 - start6));
		
		bitBoardOperations.printBoardLevel(bitBoardOperations.boardCenter);
		bitBoardOperations.printBoardLevel(bitBoardOperations.boardShellWhite);
		bitBoardOperations.printBoardLevel(bitBoardOperations.boardShellBlack);
		bitBoardOperations.printBoardLevel((bitBoardOperations.boardCenter | bitBoardOperations.boardShellWhite | bitBoardOperations.boardShellBlack));
	
		
		long b = bitBoardOperations.bitConstants[1];
		
		long a = b;
		
		a = a | bitBoardOperations.bitConstants[2];
		
		bitBoardOperations.printBoardLevel(a);
		bitBoardOperations.printBoardLevel(b);
		
	
	}
	

		
	
}
