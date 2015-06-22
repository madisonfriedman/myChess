package chess;

public class newHugh {

	//#include "stdafx.h"
	//#include "0x88_math.h"
	//#include "eval.h"
	//#include "transposition.h"
	 
	/* adjustements of piece value based on the number of own pawns */
	
	
	
	int[] knight_adj = { -20, -16, -12, -8, -4,  0,  4,  8, 12};
	
	int[]  rook_adj = {  15,  12,   9,  6,  3,  0, -3, -6, -9};
	 
	public static int[] SafetyTable = {
	    0,  0,   1,   2,   3,   5,   7,   9,  12,  15,
	  18,  22,  26,  30,  35,  39,  44,  50,  56,  62,
	  68,  75,  82,  85,  89,  97, 105, 113, 122, 131,
	 140, 150, 169, 180, 191, 202, 213, 225, 237, 248,
	 260, 272, 283, 295, 307, 319, 330, 342, 354, 366,
	 377, 389, 401, 412, 424, 436, 448, 459, 471, 483,
	 494, 500, 500, 500, 500, 500, 500, 500, 500, 500,
	 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
	 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
	 500, 500, 500, 500, 500, 500, 500, 500, 500, 500
	};
	 
	/*******************************************************************
	*  This struct holds data about certain aspects of evaluation,     *
	*  which allows program to print them if desired.                  *
	*******************************************************************/
	
	int WHITE = 0;
	int BLACK = 1;
	
	class eval_vector {
	int gamePhase;
	int mgMob[];
	int egMob[];
	int attCnt[];
	int attWeight[];
	int kingShield[];
	int MaterialAdjustement[];
	int Blockages[];
	int PositionalThemes[];
	} ;
	 
	int eval( int alpha, int beta, int use_hash ) {
	    int result = 0, mgScore = 0, egScore = 0;
	    int stronger, weaker;
	 
	    /***********************************************************
	    /  Probe the evaluatinon hashtable, unless we call eval()  /
	    /  only in order to display detailed result                /
	    ***********************************************************/
	 
	    int probeval = tteval_probe();
	    if (probeval != INVALID && use_hash)
	        return probeval;
	 
	    /***********************************************************
	    /  Clear all eval data                                     /
	    ***********************************************************/
	 
	    eval_vector v = new eval_vector();
	    
	    /*
	    v.gamePhase = 0;
	    v.mgMob[WHITE] = 0;      v.mgMob[BLACK] = 0;
	    v.egMob[WHITE] = 0;      v.egMob[BLACK] = 0;
	    v.attCnt[WHITE] = 0;     v.attCnt[BLACK] = 0;
	    v.attWeight[WHITE] = 0;  v.attWeight[BLACK] = 0;
	    v.MaterialAdjustement[WHITE] = 0; v.MaterialAdjustement[BLACK] = 0;
	    v.Blockages[WHITE] = 0; v.Blockages[BLACK] = 0;
	    v.PositionalThemes[WHITE] = 0; v.PositionalThemes[BLACK] = 0;
	    v.kingShield[WHITE] = 0; v.kingShield[BLACK] = 0;
	 	*/
	    
	    
	    /* sum the incrementally counted material and pcsq values */
	    mgScore = b.PieceMaterial[WHITE] + b.PawnMaterial[WHITE] + b.PcsqMg[WHITE]
	            - b.PieceMaterial[BLACK] - b.PawnMaterial[BLACK] - b.PcsqMg[BLACK];
	    egScore = b.PieceMaterial[WHITE] + b.PawnMaterial[WHITE] + b.PcsqEg[WHITE]
	            - b.PieceMaterial[BLACK] - b.PawnMaterial[BLACK] - b.PcsqEg[BLACK];
	 
	    /* add king's pawn shield score and evaluate part of piece blockage score
	    (the rest of the latter will be done via piece eval) */
	    v.kingShield[WHITE] = wKingShield();
	    v.kingShield[BLACK] = bKingShield();
	    blockedPieces();
	    mgScore += (v.kingShield[WHITE] - v.kingShield[BLACK]);
	 
	    /* tempo bonus */
	    if ( b.stm == WHITE ) result += e.TEMPO;
	    else                  result -= e.TEMPO;
	 
	    /*******************************************************************
	    * Adjusting material value for the various combinations of pieces. *
	    * Currently it scores bishop, knight and rook pairs. The first one *
	    * gets a bonus, the latter two - a penalty. Please also note that  *
	    * adjustements of knight and rook value based on the number of own *
	    * pawns on the board are done within the piece-specific routines.  *
	    *******************************************************************/
	 
	     if ( b.PieceCount[WHITE][BISHOP] > 1 ) result += e.BISHOP_PAIR;
	     if ( b.PieceCount[BLACK][BISHOP] > 1 ) result -= e.BISHOP_PAIR;
	     if ( b.PieceCount[WHITE][KNIGHT] > 1 ) result -= e.P_KNIGHT_PAIR;
	     if ( b.PieceCount[BLACK][KNIGHT] > 1 ) result += e.P_KNIGHT_PAIR;
	     if ( b.PieceCount[WHITE]  [ROOK] > 1 ) result -= e.P_ROOK_PAIR;
	     if ( b.PieceCount[BLACK]  [ROOK] > 1 ) result += e.P_ROOK_PAIR;
	 
	     result += getPawnScore();
	 
	    /*******************************************************************
	    *  Evaluate pieces                                                 *
	    *******************************************************************/
	 
	   for (U8 row=0; row < 8; row++)
	   for (U8 col=0; col < 8; col++) {
	 
	       S8 sq = SET_SQ(row, col);
	 
	       if ( b.color[sq] != COLOR_EMPTY ) {
	           switch (b.pieces[sq]) {
	           case PAWN: // pawns are evaluated separately
	           break;
	           case KNIGHT:
	              EvalKnight(sq, b.color[sq]);
	              break;
	           case BISHOP:
	              EvalBishop(sq, b.color[sq]);
	              break;
	           case ROOK:
	               EvalRook(sq, b.color[sq]);
	               break;
	           case QUEEN:
	               EvalQueen(sq, b.color[sq]);
	               break;
	           case KING:
	               break;
	       }
	     }
	   }
	 
	   /********************************************************************
	   *  Merge midgame and endgame score. We interpolate between these    *
	   *  two values, using a gamePhase value, based on remaining piece    *
	   *  material on both sides. With less pieces, endgame score beco-    *
	   *  mes more influential.                                            *
	   ********************************************************************/
	 
	   mgScore += (v.mgMob[WHITE] - v.mgMob[BLACK]);
	   egScore += (v.egMob[WHITE] - v.egMob[BLACK]);
	   if (v.gamePhase > 24) v.gamePhase = 24;
	   int mgWeight = v.gamePhase;
	   int egWeight = 24 - mgWeight;
	   result += ( (mgScore * mgWeight) + (egScore * egWeight) ) / 24;
	 
	   /********************************************************************
	   *  Add phase-independent score components.                          *
	   ********************************************************************/
	 
	   result += (v.Blockages[WHITE] - v.Blockages[BLACK]);
	   result += (v.PositionalThemes[WHITE] - v.PositionalThemes[BLACK]);
	   result += (v.MaterialAdjustement[WHITE] - v.MaterialAdjustement[BLACK]);
	 
	   /********************************************************************
	    *  Merge king attack score. We don't apply this value if there are *
	    *  less than two attackers or if the attacker has no queen.        *
	    *******************************************************************/
	 
	   if (v.attCnt[WHITE] < 2 || b.PieceCount[WHITE][QUEEN] == 0) v.attWeight[WHITE] = 0;
	   if (v.attCnt[BLACK] < 2 || b.PieceCount[BLACK][QUEEN] == 0) v.attWeight[BLACK] = 0;
	   result += SafetyTable[v.attWeight[WHITE]];
	   result -= SafetyTable[v.attWeight[BLACK]];
	 
	   /********************************************************************
	   *  Low material correction - guarding against an illusory material  *
	   *  advantage. Full blown program should have more such rules,  but  *
	   *  the current set ought to be useful enough. Please note that our  *
	   *  code  assumes different material values for bishop and  knight.  *
	   *                                                                   *
	   *  - a single minor piece cannot win                                *
	   *  - two knights cannot checkmate bare king                         *
	   *  - bare rook vs minor piece is drawish                            *
	   *  - rook and minor vs rook is drawish                              *
	   ********************************************************************/
	 
	   if (result > 0) { stronger = WHITE; weaker = BLACK; }
	   else               { stronger = BLACK; weaker = WHITE; }
	 
	   if (b.PawnMaterial[stronger] == 0) {
	 
	       if (b.PieceMaterial[stronger] < 400) return 0;
	 
	       if (b.PawnMaterial[weaker] == 0
	           && (b.PieceMaterial[stronger] == 2 * e.PIECE_VALUE[KNIGHT]))
	           return 0;
	 
	       if (b.PieceMaterial[stronger] == e.PIECE_VALUE[ROOK]
	           && b.PieceMaterial[weaker] == e.PIECE_VALUE[BISHOP]) result /= 2;
	 
	       if (b.PieceMaterial[stronger] == e.PIECE_VALUE[ROOK]
	           && b.PieceMaterial[weaker] == e.PIECE_VALUE[BISHOP]) result /= 2;
	 
	       if (b.PieceMaterial[stronger] == e.PIECE_VALUE[ROOK] + e.PIECE_VALUE[BISHOP]
	           && b.PieceMaterial[stronger] == e.PIECE_VALUE[ROOK]) result /= 2;
	 
	       if (b.PieceMaterial[stronger] == e.PIECE_VALUE[ROOK] + e.PIECE_VALUE[KNIGHT]
	           && b.PieceMaterial[stronger] == e.PIECE_VALUE[ROOK]) result /= 2;
	   }
	 
	    /*******************************************************************
	    *  Finally return the score relative to the side to move.          *
	    *******************************************************************/
	 
	    if ( b.stm == BLACK ) result = -result;
	 
	    tteval_save(result);
	 
	    return result;
	}
	 
	void EvalKnight(S8 sq, S8 side) {
	     int att = 0;
	     int mob = 0;
	     int pos;
	      v.gamePhase += 1;
	 
	     if (side == WHITE) {
	         switch (sq) {
	         case A8: if (isPiece(BLACK, PAWN, A7) || isPiece(BLACK, PAWN, C7)) v.Blockages[WHITE] -= e.P_KNIGHT_TRAPPED_A8; break;
	         case H8: if (isPiece(BLACK, PAWN, H7) || isPiece(BLACK, PAWN, F7)) v.Blockages[WHITE] -= e.P_KNIGHT_TRAPPED_A8; break;
	         case A7: if (isPiece(BLACK, PAWN, A6) && isPiece(BLACK, PAWN, B7)) v.Blockages[WHITE] -= e.P_KNIGHT_TRAPPED_A7; break;
	         case H7: if (isPiece(BLACK, PAWN, H6) && isPiece(BLACK, PAWN, G7)) v.Blockages[WHITE] -= e.P_KNIGHT_TRAPPED_A7; break;
	         case C3: if (isPiece(WHITE, PAWN, C2) && isPiece(WHITE, PAWN, D4) && !isPiece(WHITE, PAWN, E4)) v.Blockages[WHITE] -= e.P_C3_KNIGHT; break;
	         }
	     }
	     else
	     {
	         switch (sq) {
	         case A1: if (isPiece(WHITE, PAWN, A2) || isPiece(WHITE, PAWN, C2)) v.Blockages[BLACK] -= e.P_KNIGHT_TRAPPED_A8; break;
	         case H1: if (isPiece(WHITE, PAWN, H2) || isPiece(WHITE, PAWN, F2)) v.Blockages[BLACK] -= e.P_KNIGHT_TRAPPED_A8; break;
	         case A2: if (isPiece(WHITE, PAWN, A3) && isPiece(WHITE, PAWN, B2)) v.Blockages[BLACK] -= e.P_KNIGHT_TRAPPED_A7; break;
	         case H2: if (isPiece(WHITE, PAWN, H3) && isPiece(WHITE, PAWN, G2)) v.Blockages[BLACK] -= e.P_KNIGHT_TRAPPED_A7; break;
	         case C6: if (isPiece(BLACK, PAWN, C7) && isPiece(BLACK, PAWN, D5) && !isPiece(BLACK, PAWN, E5)) v.Blockages[BLACK] -= e.P_C3_KNIGHT; break;
	         }
	     }
	 
	     /***************************************************************
	     *  Material value adjustement based on the no. of own pawns.   *
	     *  Knights lose value as pawns disappear.                      *
	     ***************************************************************/
	 
	     v.MaterialAdjustement[side] += knight_adj[b.PieceCount[side][PAWN]];
	 
	    /****************************************************************
	    *  Collect data about mobility and king attacks. This resembles *
	    *  move generation code, except that we are just incrementing   *
	    *  the counters instead of adding actual moves.                 *
	    ****************************************************************/
	 
	    for (U8 dir=0;dir<8;dir++) {
	        pos = sq + vector[KNIGHT][dir];
	        if ( IS_SQ(pos) && b.color[pos] != side ) {
	           ++mob;
	           if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] )
	               ++att; // this knight is attacking zone around enemy king
	        }
	    }
	 
	    /****************************************************************
	    *  Evaluate mobility. We try to do it in such a way             *
	    *  that  zero represents average mobility, but  our             *
	    *  formula of doing so is a puer guess.                         *
	    ****************************************************************/
	 
	    v.mgMob[side] += 4 * (mob-4);
	    v.egMob[side] += 4 * (mob-4);
	 
	    /****************************************************************
	    *  Save data about king attacks                                 *
	    ****************************************************************/
	 
	    if (att) {
	       v.attCnt[side]++;
	       v.attWeight[side] += 2 * att;
	    }
	}
	 
	void EvalBishop(S8 sq, S8 side) {
	     int att = 0;
	     int mob = 0;
	     v.gamePhase += 1;
	 
	     if (side == WHITE) {
	         switch (sq) {
	         case A7: if (isPiece(BLACK, PAWN, B6)) v.Blockages[WHITE] -= e.P_BISHOP_TRAPPED_A7; break;
	         case H7: if (isPiece(BLACK, PAWN, G6)) v.Blockages[WHITE] -= e.P_BISHOP_TRAPPED_A7; break;
	         case B8: if (isPiece(BLACK, PAWN, C7)) v.Blockages[WHITE] -= e.P_BISHOP_TRAPPED_A7; break;
	         case G8: if (isPiece(BLACK, PAWN, F7)) v.Blockages[WHITE] -= e.P_BISHOP_TRAPPED_A7; break;
	         case A6: if (isPiece(BLACK, PAWN, B5)) v.Blockages[WHITE] -= e.P_BISHOP_TRAPPED_A6; break;
	         case H6: if (isPiece(BLACK, PAWN, G5)) v.Blockages[WHITE] -= e.P_BISHOP_TRAPPED_A6; break;
	         case F1: if (isPiece(WHITE, KING, G1)) v.PositionalThemes[WHITE] += e.RETURNING_BISHOP; break;
	         case C1: if (isPiece(WHITE, KING, B1)) v.PositionalThemes[WHITE] += e.RETURNING_BISHOP; break;
	         }
	     }
	     else {
	         switch (sq) {
	         case A2: if (isPiece(WHITE, PAWN, B3)) v.Blockages[BLACK] -= e.P_BISHOP_TRAPPED_A7; break;
	         case H2: if (isPiece(WHITE, PAWN, G3)) v.Blockages[BLACK] -= e.P_BISHOP_TRAPPED_A7; break;
	         case B1: if (isPiece(WHITE, PAWN, C2)) v.Blockages[BLACK] -= e.P_BISHOP_TRAPPED_A7; break;
	         case G1: if (isPiece(WHITE, PAWN, F2)) v.Blockages[BLACK] -= e.P_BISHOP_TRAPPED_A7; break;
	         case A3: if (isPiece(WHITE, PAWN, B4)) v.Blockages[BLACK] -= e.P_BISHOP_TRAPPED_A6; break;
	         case H3: if (isPiece(WHITE, PAWN, G4)) v.Blockages[BLACK] -= e.P_BISHOP_TRAPPED_A6; break;
	         case F8: if (isPiece(BLACK, KING, G8)) v.PositionalThemes[BLACK] += e.RETURNING_BISHOP; break;
	         case C8: if (isPiece(BLACK, KING, B8)) v.PositionalThemes[BLACK] += e.RETURNING_BISHOP; break;
	         }
	     }
	 
	    /****************************************************************
	    *  Collect data about mobility and king attacks                 *
	    ****************************************************************/
	 
	    for (char dir=0;dir<vectors[BISHOP];dir++) {
	 
	         for (char pos = sq;;) {
	 
	              pos = pos + vector[BISHOP][dir];
	              if (! IS_SQ(pos)) break;
	 
	              if (b.pieces[pos] == PIECE_EMPTY) {
	                 mob++;
	                 if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] ) ++att;
	              }
	              else if (b.color[pos] != side) {
	                   mob++;
	                   if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] ) ++att;
	                   break;
	              }
	              else {
	                   break;
	              }
	 
	         }
	    }
	 
	    v.mgMob[side] += 3 * (mob-7);
	    v.egMob[side] += 3 * (mob-7);
	 
	    if (att) {
	       v.attCnt[side]++;
	       v.attWeight[side] += 2*att;
	    }
	 
	 
	}
	 
	void EvalRook(S8 sq, S8 side) {
	    int att = 0;
	    int mob = 0;
	    int ownBlockingPawns = 0;
	    int oppBlockingPawns = 0;
	    int stepFwd;
	    int nextSq;
	 
	    v.gamePhase += 2;
	 
	    /***************************************************************
	    *  Material value adjustement based on the no. of own pawns.   *
	    *  Rooks gain value as pawns disappear.                        *
	    ***************************************************************/
	 
	    v.MaterialAdjustement[side] += rook_adj[b.PieceCount[side][PAWN]];
	 
	    /***************************************************************
	    *  This is an ugly hack to detect open files. Merging it with  *
	    *  mobility  eval would have been better, but less  readable,  *
	    *  and this is educational program fter all.                   *
	    /**************************************************************/
	 
	    if (side == WHITE) stepFwd = NORTH; else stepFwd = SOUTH;
	    nextSq = sq + stepFwd;
	 
	    while (IS_SQ(nextSq)) {
	        if (b.pieces[nextSq] == PAWN) {
	            if (b.color[nextSq] == side) {
	               ownBlockingPawns++;
	               break;
	            }
	            else
	               oppBlockingPawns++;
	        }
	        nextSq += stepFwd;
	    }
	 
	    /****************************************************************
	    *  Evaluate open and half-open files. We merge this bonus with  *
	    *  mobility  score.                                             *
	    /***************************************************************/
	 
	    if ( !ownBlockingPawns ) {
	 
	       if ( !oppBlockingPawns ) {
	           v.mgMob[side] += e.ROOK_OPEN;
	           v.egMob[side] += e.ROOK_OPEN;
	       } else {
	           v.mgMob[side] += e.ROOK_HALF;
	           v.egMob[side] += e.ROOK_HALF;
	       }
	    }
	 
	    /****************************************************************
	    *  Collect data about mobility and king attacks                 *
	    ****************************************************************/
	 
	    for (char dir=0; dir<vectors[ROOK]; dir++) {
	 
	         for (char pos = sq;;) {
	 
	              pos = pos + vector[ROOK][dir];
	              if (! IS_SQ(pos)) break;
	 
	              if (b.pieces[pos] == PIECE_EMPTY) {
	                 mob++;
	                 if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] ) ++att;
	              }
	              else if (b.color[pos] != side) {
	                   mob++;
	                   if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] ) ++att;
	                   break;
	              }
	              else {
	                   break;
	              }
	 
	         }
	    }
	 
	    v.mgMob[side] += 2 * (mob-7);
	    v.egMob[side] += 4 * (mob-7);
	 
	    if (att) {
	       v.attCnt[side]++;
	       v.attWeight[side] += 3*att;
	    }
	 
	}
	 
	void EvalQueen(S8 sq, S8 side) {
	    v.gamePhase += 4;
	    int att = 0;
	    int mob = 0;
	 
	    /****************************************************************
	    *  A queen should not be developed too early                    *
	    ****************************************************************/
	 
	    if (side == WHITE && ROW(sq) > ROW_2) {
	        if (isPiece(WHITE, KNIGHT, B1)) v.PositionalThemes[WHITE] -= 2;
	        if (isPiece(WHITE, BISHOP, C1)) v.PositionalThemes[WHITE] -= 2;
	        if (isPiece(WHITE, BISHOP, F1)) v.PositionalThemes[WHITE] -= 2;
	        if (isPiece(WHITE, KNIGHT, G1)) v.PositionalThemes[WHITE] -= 2;
	    }
	 
	    if (side == BLACK && ROW(sq) < ROW_7) {
	        if (isPiece(BLACK, KNIGHT, B8)) v.PositionalThemes[BLACK] -= 2;
	        if (isPiece(BLACK, BISHOP, C8)) v.PositionalThemes[BLACK] -= 2;
	        if (isPiece(BLACK, BISHOP, F8)) v.PositionalThemes[BLACK] -= 2;
	        if (isPiece(BLACK, KNIGHT, G8)) v.PositionalThemes[BLACK] -= 2;
	    }
	 
	    /****************************************************************
	    *  Collect data about mobility and king attacks                 *
	    ****************************************************************/
	 
	    for (char dir=0;dir<vectors[QUEEN];dir++) {
	 
	         for (char pos = sq;;) {
	 
	              pos = pos + vector[QUEEN][dir];
	              if (! IS_SQ(pos)) break;
	 
	              if (b.pieces[pos] == PIECE_EMPTY) {
	                 mob++;
	                 if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] ) ++att;
	              }
	              else if (b.color[pos] != side) {
	                   mob++;
	                   if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] ) ++att;
	                   break;
	              }
	              else {
	                   break;
	              }
	 
	         }
	    }
	 
	    v.mgMob[side] += 1 * (mob-14);
	    v.egMob[side] += 2 * (mob-14);
	 
	    if (att) {
	       v.attCnt[side]++;
	       v.attWeight[side] += 4*att;
	    }
	 
	}
	 
	int wKingShield() {
	 
	    int result = 0;
	 
	    /* king on the kingside */
	    if ( COL(b.KingLoc[WHITE]) > COL_E ) {
	 
	       if ( isPiece(WHITE, PAWN, F2) )  result += e.SHIELD_1;
	       else if ( isPiece(WHITE, PAWN, F3) )  result += e.SHIELD_2;
	 
	       if ( isPiece(WHITE, PAWN, G2) )  result += e.SHIELD_1;
	       else if ( isPiece(WHITE, PAWN, G3) )  result += e.SHIELD_2;
	 
	       if ( isPiece(WHITE, PAWN, H2) )  result += e.SHIELD_1;
	       else if ( isPiece(WHITE, PAWN, H3) )  result += e.SHIELD_2;
	   }
	 
	   /* king on the queenside */
	   else if ( COL(b.KingLoc[WHITE]) < COL_D ) {
	 
	       if ( isPiece(WHITE, PAWN, A2) )  result += e.SHIELD_1;
	       else if ( isPiece(WHITE, PAWN, A3) )  result += e.SHIELD_2;
	 
	       if ( isPiece(WHITE, PAWN, B2) )  result += e.SHIELD_1;
	       else if ( isPiece(WHITE, PAWN, B3) )  result += e.SHIELD_2;
	 
	       if ( isPiece(WHITE, PAWN, C2) )  result += e.SHIELD_1;
	       else if ( isPiece(WHITE, PAWN, C3) )  result += e.SHIELD_2;
	   }
	 
	return result;
	}
	 
	int bKingShield() {
	    int result = 0;
	 
	  /* king on the kingside */
	  if ( COL(b.KingLoc[BLACK]) > COL_E ) {
	        if ( isPiece(BLACK, PAWN, F7) )  result += e.SHIELD_1;
	        else if ( isPiece(BLACK, PAWN, F6) )  result += e.SHIELD_2;
	 
	        if ( isPiece(BLACK, PAWN, G7) )  result += e.SHIELD_1;
	        else if ( isPiece(BLACK, PAWN, G6) )  result += e.SHIELD_2;
	 
	        if ( isPiece(BLACK, PAWN, H7) )  result += e.SHIELD_1;
	        else if ( isPiece(BLACK, PAWN, H6) )  result += e.SHIELD_2;
	   }
	 
	   /* king on the queenside */
	   else if ( COL(b.KingLoc[BLACK]) < COL_D ) {
	       if ( isPiece(BLACK, PAWN, A7) )  result += e.SHIELD_1;
	       else if ( isPiece(BLACK, PAWN, A6) )  result += e.SHIELD_2;
	 
	       if ( isPiece(BLACK, PAWN, B7) )  result += e.SHIELD_1;
	       else if ( isPiece(BLACK, PAWN, B6) )  result += e.SHIELD_2;
	 
	       if ( isPiece(BLACK, PAWN, C7) )  result += e.SHIELD_1;
	       else if ( isPiece(BLACK, PAWN, C6) )  result += e.SHIELD_2;
	  }
	  return result;
	}
	 
	/******************************************************************************
	*                            Pawn structure evaluaton                         *
	******************************************************************************/
	 
	int getPawnScore() {
	    int result;
	 
	    /**************************************************************************
	    *  This function wraps hashing mechanism around evalPawnStructure().      *
	    *  Please note  that since we use the pawn hashtable, evalPawnStructure() *
	    *  must not take into account the piece position.  In a more elaborate    *
	    *  program, pawn hashtable would contain only the characteristics of pawn *
	    *  structure,  and scoring them in conjunction with the piece position    *
	    *  would have been done elsewhere.                                        *
	    **************************************************************************/
	 
	    int probeval = ttpawn_probe();
	    if (probeval != INVALID)
	        return probeval;
	 
	    result = evalPawnStructure();
	 
	    ttpawn_save(result);
	 
	    return result;
	}
	 
	int evalPawnStructure() {
	    int result = 0;
	 
	    for (U8 row = 0; row < 8; row++)
	    for (U8 col = 0; col < 8; col++) {
	 
	       S8 sq = SET_SQ(row, col);
	 
	       if (b.pieces[sq] == PAWN) {
	           if (b.color[sq] == WHITE) result += EvalPawn(sq, WHITE);
	           else                      result -= EvalPawn(sq, BLACK);
	       }
	   }
	 
	   return result;
	}
	 
	int EvalPawn(S8 sq, S8 side) {
	    int result = 0;
	    int flagIsPassed = 1; // we will be trying to disprove that
	    int flagIsWeak = 1;   // we will be trying to disprove that
	    int flagIsOpposed = 0;
	 
	    int stepFwd, stepBck;
	    if (side == WHITE) stepFwd = NORTH; else stepFwd = SOUTH;
	    if (side == WHITE) stepBck = SOUTH; else stepBck = NORTH;
	    S8 nextSq = sq + stepFwd;
	 
	    /*************************************************************************
	    *   We have only very basic data structures that do not update informa-  *
	    *   tion about pawns incrementally, so we have to calculate everything   *
	    *   here.  The loop below detects doubled pawns, passed pawns and sets   *
	    *   a flag on finding that our pawn is opposed by enemy pawn.            *
	    *************************************************************************/
	 
	    while (IS_SQ(nextSq)) {
	 
	        if (b.pieces[nextSq] == PAWN) { // either opposed by enemy pawn or doubled
	            flagIsPassed = 0;
	            if (b.color[nextSq] == side)
	                result -= 20;       // doubled pawn penalty
	            else
	                flagIsOpposed = 1;  // flag our pawn as opposed
	        }
	 
	        if (IS_SQ(nextSq + WEST) && isPiece(!side, PAWN, nextSq + WEST))
	            flagIsPassed = 0;
	 
	        if (IS_SQ(nextSq + EAST) && isPiece(!side, PAWN, nextSq + EAST))
	            flagIsPassed = 0;
	 
	        nextSq += stepFwd;
	    }
	 
	    /*************************************************************************
	    *   Another loop, going backwards and checking whether pawn has support. *
	    *   Here we can at least break out of it for speed optimization.         *
	    *************************************************************************/
	 
	    nextSq = sq;
	    while (IS_SQ(nextSq)) {
	 
	        if (IS_SQ(nextSq + WEST) && isPiece(side, PAWN, nextSq + WEST)) {
	            flagIsWeak = 0;
	            break;
	        }
	 
	        if (IS_SQ(nextSq + EAST) && isPiece(side, PAWN, nextSq + EAST)) {
	            flagIsWeak = 0;
	            break;
	        }
	 
	        nextSq += stepBck;
	    }
	 
	    /*************************************************************************
	    *  Evaluate passed pawns, scoring them higher if they are protected      *
	    *  or if their advance is supported by friendly pawns                    *
	    *************************************************************************/
	 
	    if ( flagIsPassed ) {
	        if ( isPawnSupported(sq, side) ) result += e.protected_passer[side][sq];
	        else                             result += e.passed_pawn[side][sq];
	    }
	 
	    /*************************************************************************
	    *  Evaluate weak pawns, increasing the penalty if they are situated      *
	    *  on a half-open file                                                   *
	    *************************************************************************/
	 
	    if ( flagIsWeak ) {
	        result += e.weak_pawn[side][sq];
	        if (!flagIsOpposed)
	            result -= 4;
	    }
	 
	    return result;
	}
	 
	int isPawnSupported(S8 sq, S8 side) {
	    int step;
	    if (side == WHITE) step = SOUTH; else step = NORTH;
	 
	    if ( IS_SQ(sq+WEST) && isPiece(side,PAWN, sq + WEST) ) return 1;
	    if ( IS_SQ(sq+EAST) && isPiece(side,PAWN, sq + EAST) ) return 1;
	    if ( IS_SQ(sq+step+WEST) && isPiece(side,PAWN, sq + step+WEST ) ) return 1;
	    if ( IS_SQ(sq+step+EAST) && isPiece(side,PAWN, sq + step+EAST ) ) return 1;
	 
	    return 0;
	}
	 
	/******************************************************************************
	*                             Pattern detection                               *
	******************************************************************************/
	 
	void blockedPieces() {
	 
	    // central pawn blocked, bishop hard to develop
	    if (isPiece(WHITE, BISHOP, C1) && isPiece(WHITE, PAWN, D2) && b.color[D3] != COLOR_EMPTY)
	        v.Blockages[WHITE] -= e.P_BLOCK_CENTRAL_PAWN;
	    if (isPiece(WHITE, BISHOP, F1) &&  isPiece(WHITE, PAWN, E2) && b.color[E3] != COLOR_EMPTY)
	        v.Blockages[WHITE] -= e.P_BLOCK_CENTRAL_PAWN;
	    if (isPiece(BLACK, BISHOP, C8) &&  isPiece(BLACK, PAWN, D7) && b.color[D6] != COLOR_EMPTY)
	        v.Blockages[BLACK] -= e.P_BLOCK_CENTRAL_PAWN;
	    if (isPiece(BLACK, BISHOP, F8) && isPiece(BLACK, PAWN, E7) && b.color[E6] != COLOR_EMPTY)
	        v.Blockages[BLACK] -= e.P_BLOCK_CENTRAL_PAWN;
	 
	    // uncastled king blocking own rook
	    if ( ( isPiece(WHITE, KING, F1) || isPiece(WHITE, KING, G1 ) )&&
	         ( isPiece(WHITE, ROOK, H1) || isPiece(WHITE, ROOK, G1 ) )
	       )
	       v.Blockages[WHITE] -= e.P_KING_BLOCKS_ROOK;
	 
	    if ( ( isPiece(WHITE, KING, C1) || isPiece(WHITE, KING, B1 ) )&&
	         ( isPiece(WHITE, ROOK, A1) || isPiece(WHITE, ROOK, B1 ) )
	       )
	       v.Blockages[WHITE] -= e.P_KING_BLOCKS_ROOK;
	 
	    if ( ( isPiece(BLACK, KING, F8) || isPiece(BLACK, KING, G8 ) )&&
	         ( isPiece(BLACK, ROOK, H8) || isPiece(BLACK, ROOK, G8 ) )
	       )
	       v.Blockages[BLACK] -= e.P_KING_BLOCKS_ROOK;
	 
	    if ( ( isPiece(BLACK, KING, C8) || isPiece(BLACK, KING, B8 ) )&&
	         ( isPiece(BLACK, ROOK, A8) || isPiece(BLACK, ROOK, B8 ) )
	       )
	       v.Blockages[BLACK] -= e.P_KING_BLOCKS_ROOK;
	}
	 
	int isPiece(U8 color, U8 piece, S8 sq) {
	    return ( (b.pieces[sq] == piece) && (b.color[sq] == color) );
	}
	 
	/***************************************************************************************
	*                             Printing eval results                                    *
	***************************************************************************************/
	 
	void printEval() {
	    printf("------------------------------------------\n");
	    printf("Total value (for side to move): %d \n", eval(-INF,INF, 0) );
	    printf("Material balance    : %d \n", b.PieceMaterial[WHITE] + b.PawnMaterial[WHITE] - b.PieceMaterial[BLACK] - b.PawnMaterial[BLACK] );
	    printf("Material adjustement: "); printEvalFactor(v.MaterialAdjustement[WHITE], v.MaterialAdjustement[BLACK]);
	    printf("Mg Piece/square tables : "); printEvalFactor(b.PcsqMg[WHITE], b.PcsqMg[BLACK]);
	    printf("Eg Piece/square tables : "); printEvalFactor(b.PcsqEg[WHITE], b.PcsqEg[BLACK]);
	    printf("Mg Mobility            : "); printEvalFactor(v.mgMob[WHITE], v.mgMob[BLACK]);
	    printf("Eg Mobility            : "); printEvalFactor(v.mgMob[WHITE], v.egMob[BLACK]);
	    printf("Pawn structure      : %d \n", evalPawnStructure() );
	    printf("Blockages           : "); printEvalFactor(v.Blockages[WHITE], v.Blockages[BLACK]);
	    printf("Positional themes   : "); printEvalFactor(v.PositionalThemes[WHITE], v.PositionalThemes[BLACK]);
	    printf("King Shield         : "); printEvalFactor(v.kingShield[WHITE], v.kingShield[BLACK]);
	    printf("Tempo: ");
	    if ( b.stm == WHITE ) printf("%d", e.TEMPO); else printf("%d", -e.TEMPO);
	    printf("\n");
	    printf("------------------------------------------\n");
	}
	 
	/*
	void printEvalFactor(int wh, int bl) {
	     printf("white %4d, black %4d, total: %4d \n", wh, bl, wh - bl);
	}
	*/
	
	
	int index_white[64] = {
		    A8, B8, C8, D8, E8, F8, G8, H8,
		    A7, B7, C7, D7, E7, F7, G7, H7,
		    A6, B6, C6, D6, E6, F6, G6, H6,
		    A5, B5, C5, D5, E5, F5, G5, H5,
		    A4, B4, C4, D4, E4, F4, G4, H4,
		    A3, B3, C3, D3, E3, F3, G3, H3,
		    A2, B2, C2, D2, E2, F2, G2, H2,
		    A1, B1, C1, D1, E1, F1, G1, H1
		};
		 
		int index_black[64] = {
		    A1, B1, C1, D1, E1, F1, G1, H1,
		    A2, B2, C2, D2, E2, F2, G2, H2,
		    A3, B3, C3, D3, E3, F3, G3, H3,
		    A4, B4, C4, D4, E4, F4, G4, H4,
		    A5, B5, C5, D5, E5, F5, G5, H5,
		    A6, B6, C6, D6, E6, F6, G6, H6,
		    A7, B7, C7, D7, E7, F7, G7, H7,
		    A8, B8, C8, D8, E8, F8, G8, H8
		};
		 
		int dist_bonus[64][64];
		 
		/*****************************************************************
		*                           PAWN PCSQ                            *
		*                                                                *
		*  Unlike TSCP, CPW generally doesn't want to advance its pawns  *
		*  just for the fun of it. It takes into account the following:  *
		*                                                                *
		*  - file-dependent component, encouraging program to capture    *
		*    towards the center                                          *
		*  - small bonus for staying on the 2nd rank                     *
		*  - small bonus for standing on a3/h3                           *
		*  - penalty for d/e pawns on their initial squares              *
		*  - bonus for occupying the center                              *
		*****************************************************************/
		 
		int pawn_pcsq_mg[64] = {
		     0,   0,   0,   0,   0,   0,   0,   0,
		    -6,  -4,   1,   1,   1,   1,  -4,  -6,
		    -6,  -4,   1,   2,   2,   1,  -4,  -6,
		    -6,  -4,   2,   8,   8,   2,  -4,  -6,
		    -6,  -4,   5,  10,  10,   5,  -4,  -6,
		    -4,  -4,   1,   5,   5,   1,  -4,  -4,
		    -6,  -4,   1, -24,  -24,  1,  -4,  -6,
		     0,   0,   0,   0,   0,   0,   0,   0
		};
		 
		int pawn_pcsq_eg[64] = {
		     0,   0,   0,   0,   0,   0,   0,   0,
		    -6,  -4,   1,   1,   1,   1,  -4,  -6,
		    -6,  -4,   1,   2,   2,   1,  -4,  -6,
		    -6,  -4,   2,   8,   8,   2,  -4,  -6,
		    -6,  -4,   5,  10,  10,   5,  -4,  -6,
		    -4,  -4,   1,   5,   5,   1,  -4,  -4,
		    -6,  -4,   1, -24,  -24,  1,  -4,  -6,
		     0,   0,   0,   0,   0,   0,   0,   0
		};
		 
		/****************************************************************
		*    KNIGHT PCSQ                                                *
		*                                                               *
		*   - centralization bonus                                      *
		*   - penalty for not being developed                           *
		****************************************************************/
		 
		int knight_pcsq_mg[64] = {
		    -8,  -8,  -8,  -8,  -8,  -8,  -8,  -8,
		    -8,   0,   0,   0,   0,   0,   0,  -8,
		    -8,   0,   4,   4,   4,   4,   0,  -8,
		    -8,   0,   4,   8,   8,   4,   0,  -8,
		    -8,   0,   4,   8,   8,   4,   0,  -8,
		    -8,   0,   4,   4,   4,   4,   0,  -8,
		    -8,   0,   1,   2,   2,   1,   0,  -8,
		    -8, -12,  -8,  -8,  -8,  -8, -12,  -8
		};
		 
		int knight_pcsq_eg[64] = {
		    -8,  -8,  -8,  -8,  -8,  -8,  -8,  -8,
		    -8,   0,   0,   0,   0,   0,   0,  -8,
		    -8,   0,   4,   4,   4,   4,   0,  -8,
		    -8,   0,   4,   8,   8,   4,   0,  -8,
		    -8,   0,   4,   8,   8,   4,   0,  -8,
		    -8,   0,   4,   4,   4,   4,   0,  -8,
		    -8,   0,   1,   2,   2,   1,   0,  -8,
		    -8, -12,  -8,  -8,  -8,  -8, -12,  -8
		};
		 
		 
		/****************************************************************
		*                BISHOP PCSQ                                    *
		*                                                               *
		*   - centralization bonus, smaller than for knight             *
		*   - penalty for not being developed                           *
		*   - good squares on the own half of the board                 *
		****************************************************************/
		 
		int bishop_pcsq_mg[64] = {
		    -4,  -4,  -4,  -4,  -4,  -4,  -4,  -4,
		    -4,   0,   0,   0,   0,   0,   0,  -4,
		    -4,   0,   2,   4,   4,   2,   0,  -4,
		    -4,   0,   4,   6,   6,   4,   0,  -4,
		    -4,   0,   4,   6,   6,   4,   0,  -4,
		    -4,   1,   2,   4,   4,   2,   1,  -4,
		    -4,   2,   1,   1,   1,   1,   2,  -4,
		    -4,  -4, -12,  -4,  -4, -12,  -4,  -4
		};
		 
		int bishop_pcsq_eg[64] = {
		    -4,  -4,  -4,  -4,  -4,  -4,  -4,  -4,
		    -4,   0,   0,   0,   0,   0,   0,  -4,
		    -4,   0,   2,   4,   4,   2,   0,  -4,
		    -4,   0,   4,   6,   6,   4,   0,  -4,
		    -4,   0,   4,   6,   6,   4,   0,  -4,
		    -4,   1,   2,   4,   4,   2,   1,  -4,
		    -4,   2,   1,   1,   1,   1,   2,  -4,
		    -4,  -4, -12,  -4,  -4, -12,  -4,  -4
		};
		 
		/****************************************************************
		*                        ROOK PCSQ                              *
		*                                                               *
		*    - bonus for 7th and 8th ranks                              *
		*    - penalty for a/h columns                                  *
		*    - small centralization bonus                               *
		*****************************************************************/
		 
		int rook_pcsq_mg[64] = {
		     5,   5,   5,   5,   5,   5,   5,   5,
		    20,  20,  20,  20,  20,  20,  20,  20,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		     0,   0,   0,   2,   2,   0,   0,   0
		};
		 
		int rook_pcsq_eg[64] = {
		     5,   5,   5,   5,   5,   5,   5,   5,
		    20,  20,  20,  20,  20,  20,  20,  20,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		    -5,   0,   0,   0,   0,   0,   0,  -5,
		     0,   0,   0,   2,   2,   0,   0,   0
		};
		 
		/***************************************************************************
		*                     QUEEN PCSQ                                           *
		*                                                                          *
		* - small bonus for centralization in the endgame                          *
		* - penalty for staying on the 1st rank, between rooks in the midgame      *
		***************************************************************************/
		 
		int queen_pcsq_mg[64] = {
		    0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 1, 1, 1, 1, 0, 0,
		    0, 0, 1, 2, 2, 1, 0, 0,
		    0, 0, 2, 3, 3, 2, 0, 0,
		    0, 0, 2, 3, 3, 2, 0, 0,
		    0, 0, 1, 2, 2, 1, 0, 0,
		    0, 0, 1, 1, 1, 1, 0, 0,
		    -5, -5, -5, -5, -5, -5, -5, -5
		};
		 
		int queen_pcsq_eg[64] = {
		    0, 0, 0, 0, 0, 0, 0, 0,
		    0, 0, 1, 1, 1, 1, 0, 0,
		    0, 0, 1, 2, 2, 1, 0, 0,
		    0, 0, 2, 3, 3, 2, 0, 0,
		    0, 0, 2, 3, 3, 2, 0, 0,
		    0, 0, 1, 2, 2, 1, 0, 0,
		    0, 0, 1, 1, 1, 1, 0, 0,
		    -5, -5, -5, -5, -5, -5, -5, -5
		};
		 
		int king_pcsq_mg[64] = {
		    -40, -30, -50, -70, -70, -50, -30, -40,
		    -30, -20, -40, -60, -60, -40, -20, -30,
		    -20, -10, -30, -50, -50, -30, -10, -20,
		    -10,   0, -20, -40, -40, -20,   0, -10,
		      0,  10, -10, -30, -30, -10,  10,   0,
		     10,  20,   0, -20, -20,   0,  20,  10,
		     30,  40,  20,   0,   0,  20,  40,  30,
		     40,  50,  30,  10,  10,  30,  50,  40
		};
		 
		int king_pcsq_eg[64] = {
		    -72, -48, -36, -24, -24, -36, -48, -72,
		    -48, -24, -12,   0,   0, -12, -24, -48,
		    -36, -12,   0,  12,  12,   0, -12, -36,
		    -24,   0,  12,  24,  24,  12,   0, -24,
		    -24,   0,  12,  24,  24,  12,   0, -24,
		    -36, -12,   0,  12,  12,   0, -12, -36,
		    -48, -24, -12,   0,   0, -12, -24, -48,
		    -72, -48, -36, -24, -24, -36, -48, -72
		};
		 
		/*****************************************************************
		*                     WEAK PAWNS PCSQ                            *
		*                                                                *
		*  Current version of CPW-engine does not differentiate between  *
		*  isolated  and  backward pawns, using one  generic  cathegory  *
		*  of  weak pawns. The penalty is bigger in the center, on  the  *
		*  assumption  that  weak  central pawns can be  attacked  from  *
		*  many  directions. If the penalty seems too low, please  note  *
		*  that being on a semi-open file will come into equation, too.  *
		*****************************************************************/
		 
		int weak_pawn_pcsq[64] = {
		      0,   0,   0,   0,   0,   0,   0,   0,
		    -10, -12, -14, -16, -16, -14, -12, -10,
		    -10, -12, -14, -16, -16, -14, -12, -10,
		    -10, -12, -14, -16, -16, -14, -12, -10,
		    -10, -12, -14, -16, -16, -14, -12, -10,
		     -8, -12, -14, -16, -16, -14, -12, -10,
		     -8, -12, -14, -16, -16, -14, -12, -10,
		      0,   0,   0,   0,   0,   0,   0,   0
		};
		 
		int passed_pawn_pcsq[64] = {
		      0,   0,   0,   0,   0,   0,   0,   0,
		    100, 100, 100, 100, 100, 100, 100, 100,
		     80,  80,  80,  80,  80,  80,  80,  80,
		     60,  60,  60,  60,  60,  60,  60,  60,
		     40,  40,  40,  40,  40,  40,  40,  40,
		     20,  20,  20,  20,  20,  20,  20,  20,
		     20,  20,  20,  20,  20,  20,  20,  20,
		      0,   0,   0,   0,   0,   0,   0,   0
		};
		 
		 
		void setDefaultEval() {
		 
		    setBasicValues();
		    setSquaresNearKing();
		    setPcsq();
		    readIniFile();
		    correctValues();
		}
		 
		void setBasicValues() {
		 
		    /********************************************************************************
		    *  We use material values by IM Larry Kaufman with additional + 10 for a Bishop *
		    *  and only +30 for a Bishop pair                                                 *
		    ********************************************************************************/
		 
		    e.PIECE_VALUE[KING]   = 0;
		    e.PIECE_VALUE[QUEEN]  = 975;
		    e.PIECE_VALUE[ROOK]   = 500;
		    e.PIECE_VALUE[BISHOP] = 335;
		    e.PIECE_VALUE[KNIGHT] = 325;
		    e.PIECE_VALUE[PAWN]   = 100;
		 
		    e.BISHOP_PAIR   = 30;
		    e.P_KNIGHT_PAIR = 8;
		    e.P_ROOK_PAIR   = 16;
		 
		    /*************************************************
		    *  Calculate  the  value  of  piece  material    *
		    *  at  the  beginning of  the  game,  which  is  *
		    *  used for scaling the king tropism evaluation. *
		    *  This  way we don't have to update a constant  *
		    *  every time we change material values.         *
		    *************************************************/
		 
		    e.START_MATERIAL = e.PIECE_VALUE[QUEEN]
		        + 2 * e.PIECE_VALUE[ROOK]
		        + 2 * e.PIECE_VALUE[BISHOP]
		        + 2 * e.PIECE_VALUE[KNIGHT];
		 
		    /*************************************************
		    * Values used for sorting captures are the same  *
		    * as normal piece values, except for a king.     *
		    *************************************************/
		 
		    for (int i = 0; i < 6; ++i) {
		        e.SORT_VALUE[i] = e.PIECE_VALUE[i];
		    }
		    e.SORT_VALUE[KING] = SORT_KING;
		 
		    /* trapped and blocked pieces */
		 
		    e.P_KING_BLOCKS_ROOK   = 24;
		    e.P_BLOCK_CENTRAL_PAWN = 24;
		    e.P_BISHOP_TRAPPED_A7  = 150;
		    e.P_BISHOP_TRAPPED_A6  = 50;
		    e.P_KNIGHT_TRAPPED_A8  = 150;
		    e.P_KNIGHT_TRAPPED_A7  = 100;
		 
		    /* minor penalties */
		 
		    e.P_C3_KNIGHT = 5;
		    e.P_NO_FIANCHETTO = 4;
		 
		    /* king's defence */
		    e.SHIELD_1 = 10;
		    e.SHIELD_2 = 5;
		    e.P_NO_SHIELD = 10;
		 
		    /* minor bonuses */
		 
		    e.ROOK_OPEN = 10;
		    e.ROOK_HALF = 5;
		    e.RETURNING_BISHOP = 20;
		    e.FIANCHETTO = 4;
		    e.TEMPO = 10;
		 
		    e.ENDGAME_MAT = 1300;
		}
		 
		void setSquaresNearKing() {
		    for (int i = 0; i < 128; ++i)
		        for (int j = 0; j < 128; ++j)
		        {
		 
		            e.sqNearK[WHITE][i][j] = 0;
		            e.sqNearK[BLACK][i][j] = 0;
		 
		            if (IS_SQ(i) &&
		                IS_SQ(j)) {
		 
		                // squares constituting the ring around both kings
		 
		                if (j == i + NORTH || j == i + SOUTH ||
		                    j == i + EAST || j == i + WEST ||
		                    j == i + NW || j == i + NE ||
		                    j == i + SW || j == i + SE) {
		 
		                    e.sqNearK[WHITE][i][j] = 1;
		                    e.sqNearK[BLACK][i][j] = 1;
		                }
		 
		                /* squares in front of the white king ring */
		 
		                if (j == i + NORTH + NORTH ||
		                    j == i + NORTH + NE ||
		                    j == i + NORTH + NW)
		                    e.sqNearK[WHITE][i][j] = 1;
		 
		                // squares in front og the black king ring
		 
		                if (j == i + SOUTH + SOUTH ||
		                    j == i + SOUTH + SE ||
		                    j == i + SOUTH + SW)
		                    e.sqNearK[WHITE][i][j] = 1;
		            }
		 
		        }
		}
		 
		 
		void setPcsq() {
		 
		    for (int i = 0; i < 64; ++i) {
		 
		        e.weak_pawn[WHITE][index_white[i]] = weak_pawn_pcsq[i];
		        e.weak_pawn[BLACK][index_black[i]] = weak_pawn_pcsq[i];
		        e.passed_pawn[WHITE][index_white[i]] = passed_pawn_pcsq[i];
		        e.passed_pawn[BLACK][index_black[i]] = passed_pawn_pcsq[i];
		 
		        /* protected passers are considered slightly stronger
		        than ordinary passed pawns */
		 
		        e.protected_passer[WHITE][index_white[i]] = (passed_pawn_pcsq[i] * 10) / 8;
		        e.protected_passer[BLACK][index_black[i]] = (passed_pawn_pcsq[i] * 10) / 8;
		 
		        /* now set the piece/square tables for each color and piece type */
		 
		        e.mgPst[PAWN][WHITE][index_white[i]] = pawn_pcsq_mg[i];
		        e.mgPst[PAWN][BLACK][index_black[i]] = pawn_pcsq_mg[i];
		        e.mgPst[KNIGHT][WHITE][index_white[i]] = knight_pcsq_mg[i];
		        e.mgPst[KNIGHT][BLACK][index_black[i]] = knight_pcsq_mg[i];
		        e.mgPst[BISHOP][WHITE][index_white[i]] = bishop_pcsq_mg[i];
		        e.mgPst[BISHOP][BLACK][index_black[i]] = bishop_pcsq_mg[i];
		        e.mgPst[ROOK][WHITE][index_white[i]] = rook_pcsq_mg[i];
		        e.mgPst[ROOK][BLACK][index_black[i]] = rook_pcsq_mg[i];
		        e.mgPst[QUEEN][WHITE][index_white[i]] = queen_pcsq_mg[i];
		        e.mgPst[QUEEN][BLACK][index_black[i]] = queen_pcsq_mg[i];
		        e.mgPst[KING][WHITE][index_white[i]] = king_pcsq_mg[i];
		        e.mgPst[KING][BLACK][index_black[i]] = king_pcsq_mg[i];
		 
		        e.egPst[PAWN][WHITE][index_white[i]] = pawn_pcsq_eg[i];
		        e.egPst[PAWN][BLACK][index_black[i]] = pawn_pcsq_eg[i];
		        e.egPst[KNIGHT][WHITE][index_white[i]] = knight_pcsq_eg[i];
		        e.egPst[KNIGHT][BLACK][index_black[i]] = knight_pcsq_eg[i];
		        e.egPst[BISHOP][WHITE][index_white[i]] = bishop_pcsq_eg[i];
		        e.egPst[BISHOP][BLACK][index_black[i]] = bishop_pcsq_eg[i];
		        e.egPst[ROOK][WHITE][index_white[i]] = rook_pcsq_eg[i];
		        e.egPst[ROOK][BLACK][index_black[i]] = rook_pcsq_eg[i];
		        e.egPst[QUEEN][WHITE][index_white[i]] = queen_pcsq_eg[i];
		        e.egPst[QUEEN][BLACK][index_black[i]] = queen_pcsq_eg[i];
		        e.egPst[KING][WHITE][index_white[i]] = king_pcsq_eg[i];
		        e.egPst[KING][BLACK][index_black[i]] = king_pcsq_eg[i];
		    }
		}
		 
		/* This function is meant to be used in conjunction with the *.ini file.
		Its aim is to make sure that all the assumptions made within the program
		are met.  */
		 
		void correctValues() {
		    if (e.PIECE_VALUE[BISHOP] == e.PIECE_VALUE[KNIGHT])
		        ++e.PIECE_VALUE[BISHOP];
		}
		 
		void readIniFile() {
		    FILE *cpw_init;
		    char line[256];
		 
		    /* if the cpw.ini file does not exist, then exit */
		 
		    if ((cpw_init = fopen("cpw.ini", "r")) == NULL) {
		        printf("Cannot open cpw.ini, default settings will be used \n");
		        return;
		    }
		 
		    /* process cpw.ini file line by line */
		 
		    while (fgets(line, 250, cpw_init)) {
		        if (line[0] == ';') continue; // don't process comment lines
		        processIniString(line);
		    }
		}
		 
		void processIniString(char line[250]) {
		 
		    /* piece values */
		    if (!strncmp(line, "PAWN_VALUE", 10))
		        sscanf(line, "PAWN_VALUE %d", &e.PIECE_VALUE[PAWN]);
		    else if (!strncmp(line, "KNIGHT_VALUE", 12))
		        sscanf(line, "KNIGHT_VALUE %d", &e.PIECE_VALUE[KNIGHT]);
		    else if (!strncmp(line, "BISHOP_VALUE", 12))
		        sscanf(line, "BISHOP_VALUE %d", &e.PIECE_VALUE[BISHOP]);
		    else if (!strncmp(line, "ROOK_VALUE", 10))
		        sscanf(line, "ROOK_VALUE %d", &e.PIECE_VALUE[ROOK]);
		    else if (!strncmp(line, "QUEEN_VALUE", 11))
		        sscanf(line, "QUEEN_VALUE %d", &e.PIECE_VALUE[QUEEN]);
		 
		    /* piece pairs */
		    else if (!strncmp(line, "BISHOP_PAIR", 11))
		        sscanf(line, "BISHOP_PAIR %d", &e.BISHOP_PAIR);
		    else if (!strncmp(line, "PENALTY_KNIGHT_PAIR", 19))
		        sscanf(line, "PENALTY_KNIGHT_PAIR %d", &e.P_KNIGHT_PAIR);
		    else if (!strncmp(line, "PENALTY_ROOK_PAIR", 17))
		        sscanf(line, "PENALTY_ROOK_PAIR %d", &e.P_KNIGHT_PAIR);
		 
		    /* pawn shield*/
		    else if (!strncmp(line, "SHIELD_1", 8))
		        sscanf(line, "SHIELD_1 %d", &e.SHIELD_1);
		    else if (!strncmp(line, "SHIELD_2", 8))
		        sscanf(line, "SHIELD_2 %d", &e.SHIELD_2);
		    else if (!strncmp(line, "PENALTY_NO_SHIELD", 17))
		        sscanf(line, "PENALTY_NO_SHIELD %d", &e.P_NO_SHIELD);
		 
		    /* major penalties */
		 
		    else if (!strncmp(line, "PENALTY_BISHOP_TRAPPED_A7", 25))
		        sscanf(line, "PENALTY_BISHOP_TRAPPED_A7 %d", &e.P_BISHOP_TRAPPED_A7);
		    else if (!strncmp(line, "PENALTY_BISHOP_TRAPPED_A6", 25))
		        sscanf(line, "PENALTY_BISHOP_TRAPPED_A6 %d", &e.P_BISHOP_TRAPPED_A6);
		    else if (!strncmp(line, "PENALTY_KNIGHT_TRAPPED_A8", 25))
		        sscanf(line, "PENALTY_KNIGHT_TRAPPED_A8 %d", &e.P_KNIGHT_TRAPPED_A8);
		    else if (!strncmp(line, "PENALTY_KNIGHT_TRAPPED_A7", 25))
		        sscanf(line, "PENALTY_KNIGHT_TRAPPED_A7 %d", &e.P_KNIGHT_TRAPPED_A7);
		    else if (!strncmp(line, "PENALTY_KING_BLOCKS_ROOK", 24))
		        sscanf(line, "PENALTY_KNIGHT_TRAPPED_A7 %d", &e.P_KING_BLOCKS_ROOK);
		    else if (!strncmp(line, "PENALTY_BLOCKED_CENTRAL_PAWN", 28))
		        sscanf(line, "PENALTY_BLOCKED_CENTRAL_PAWN %d", &e.P_BLOCK_CENTRAL_PAWN);
		 
		    /* minor penalties */
		    else if (!strncmp(line, "PENALTY_KNIGHT_BLOCKS_C", 23))
		        sscanf(line, "PENALTY_KNIGHT_BLOCKS_C %d", &e.P_C3_KNIGHT);
		    else if (!strncmp(line, "PENALTY_NO_FIANCHETTO", 21))
		        sscanf(line, "PENALTY_NO_FIANCHETTO %d", &e.P_NO_FIANCHETTO);
		 
		    /* minor positional bonuses */
		 
		    else if (!strncmp(line, "ROOK_OPEN", 9))
		        sscanf(line, "ROOK_OPEN %d", &e.ROOK_OPEN);
		    else if (!strncmp(line, "ROOK_HALF_OPEN", 14))
		        sscanf(line, "ROOK_HALF_OPEN %d", &e.ROOK_HALF);
		    else if (!strncmp(line, "FIANCHETTO", 10))
		        sscanf(line, "FIANCHETTO %d", &e.FIANCHETTO);
		    else if (!strncmp(line, "RETURNING_BISHOP", 16))
		        sscanf(line, "RETURNING_BISHOP %d", &e.RETURNING_BISHOP);
		    else if (!strncmp(line, "TEMPO", 5))
		        sscanf(line, "TEMPO %d", &e.TEMPO);
		 
		    /* variables deciding about inner workings of evaluation function */
		 
		    else if (!strncmp(line, "ENDGAME_MATERIAL", 16))
		        sscanf(line, "ENDGAME_MATERIAL %d", &e.ENDGAME_MAT);
		}
	
	
}
