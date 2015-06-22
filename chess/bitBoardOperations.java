package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import chess.AIv2Tree.Node;
import chess.AIv2Tree.listAndTactical;
import chess.AIv2Tree.moveScoreTuple;

public class bitBoardOperations {

	// eval city

	int[] pawn_pcsq_mg = { 0, 0, 0, 0, 0, 0, 0, 0, -6, -4, 1, 1, 1, 1, -4, -6,
			-6, -4, 1, 2, 2, 1, -4, -6, -6, -4, 2, 8, 8, 2, -4, -6, -6, -4, 5,
			10, 10, 5, -4, -6, -4, -4, 1, 5, 5, 1, -4, -4, -6, -4, 1, -24, -24,
			1, -4, -6, 0, 0, 0, 0, 0, 0, 0, 0 };

	int[] pawn_pcsq_eg = { 0, 0, 0, 0, 0, 0, 0, 0, -6, -4, 1, 1, 1, 1, -4, -6,
			-6, -4, 1, 2, 2, 1, -4, -6, -6, -4, 2, 8, 8, 2, -4, -6, -6, -4, 5,
			10, 10, 5, -4, -6, -4, -4, 1, 5, 5, 1, -4, -4, -6, -4, 1, -24, -24,
			1, -4, -6, 0, 0, 0, 0, 0, 0, 0, 0 };

	int[] knight_pcsq_mg = { -8, -8, -8, -8, -8, -8, -8, -8, -8, 0, 0, 0, 0, 0,
			0, -8, -8, 0, 4, 4, 4, 4, 0, -8, -8, 0, 4, 8, 8, 4, 0, -8, -8, 0,
			4, 8, 8, 4, 0, -8, -8, 0, 4, 4, 4, 4, 0, -8, -8, 0, 1, 2, 2, 1, 0,
			-8, -8, -12, -8, -8, -8, -8, -12, -8 };

	int[] knight_pcsq_eg = { -8, -8, -8, -8, -8, -8, -8, -8, -8, 0, 0, 0, 0, 0,
			0, -8, -8, 0, 4, 4, 4, 4, 0, -8, -8, 0, 4, 8, 8, 4, 0, -8, -8, 0,
			4, 8, 8, 4, 0, -8, -8, 0, 4, 4, 4, 4, 0, -8, -8, 0, 1, 2, 2, 1, 0,
			-8, -8, -12, -8, -8, -8, -8, -12, -8 };

	int[] bishop_pcsq_mg = { -4, -4, -4, -4, -4, -4, -4, -4, -4, 0, 0, 0, 0, 0,
			0, -4, -4, 0, 2, 4, 4, 2, 0, -4, -4, 0, 4, 6, 6, 4, 0, -4, -4, 0,
			4, 6, 6, 4, 0, -4, -4, 1, 2, 4, 4, 2, 1, -4, -4, 2, 1, 1, 1, 1, 2,
			-4, -4, -4, -12, -4, -4, -12, -4, -4 };

	int[] bishop_pcsq_eg = { -4, -4, -4, -4, -4, -4, -4, -4, -4, 0, 0, 0, 0, 0,
			0, -4, -4, 0, 2, 4, 4, 2, 0, -4, -4, 0, 4, 6, 6, 4, 0, -4, -4, 0,
			4, 6, 6, 4, 0, -4, -4, 1, 2, 4, 4, 2, 1, -4, -4, 2, 1, 1, 1, 1, 2,
			-4, -4, -4, -12, -4, -4, -12, -4, -4 };

	int[] rook_pcsq_mg = { 5, 5, 5, 5, 5, 5, 5, 5, 20, 20, 20, 20, 20, 20, 20,
			20, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0,
			0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5,
			0, 0, 0, 2, 2, 0, 0, 0 };

	int[] rook_pcsq_eg = { 5, 5, 5, 5, 5, 5, 5, 5, 20, 20, 20, 20, 20, 20, 20,
			20, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0,
			0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5, -5, 0, 0, 0, 0, 0, 0, -5,
			0, 0, 0, 2, 2, 0, 0, 0 };

	int[] queen_pcsq_mg = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0,
			0, 1, 2, 2, 1, 0, 0, 0, 0, 2, 3, 3, 2, 0, 0, 0, 0, 2, 3, 3, 2, 0,
			0, 0, 0, 1, 2, 2, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, -5, -5, -5, -5,
			-5, -5, -5, -5 };
	int[] queen_pcsq_eg = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, 0,
			0, 1, 2, 2, 1, 0, 0, 0, 0, 2, 3, 3, 2, 0, 0, 0, 0, 2, 3, 3, 2, 0,
			0, 0, 0, 1, 2, 2, 1, 0, 0, 0, 0, 1, 1, 1, 1, 0, 0, -5, -5, -5, -5,
			-5, -5, -5, -5 };

	int[] king_pcsq_mg = { -40, -30, -50, -70, -70, -50, -30, -40, -30, -20,
			-40, -60, -60, -40, -20, -30, -20, -10, -30, -50, -50, -30, -10,
			-20, -10, 0, -20, -40, -40, -20, 0, -10, 0, 10, -10, -30, -30, -10,
			10, 0, 10, 20, 0, -20, -20, 0, 20, 10, 30, 40, 20, 0, 0, 20, 40,
			30, 40, 50, 30, 10, 10, 30, 50, 40 };

	int[] king_pcsq_eg = { -72, -48, -36, -24, -24, -36, -48, -72, -48, -24,
			-12, 0, 0, -12, -24, -48, -36, -12, 0, 12, 12, 0, -12, -36, -24, 0,
			12, 24, 24, 12, 0, -24, -24, 0, 12, 24, 24, 12, 0, -24, -36, -12,
			0, 12, 12, 0, -12, -36, -48, -24, -12, 0, 0, -12, -24, -48, -72,
			-48, -36, -24, -24, -36, -48, -72 };

	int[] weak_pawn_pcsq = { 0, 0, 0, 0, 0, 0, 0, 0, -10, -12, -14, -16, -16,
			-14, -12, -10, -10, -12, -14, -16, -16, -14, -12, -10, -10, -12,
			-14, -16, -16, -14, -12, -10, -10, -12, -14, -16, -16, -14, -12,
			-10, -8, -12, -14, -16, -16, -14, -12, -10, -8, -12, -14, -16, -16,
			-14, -12, -10, 0, 0, 0, 0, 0, 0, 0, 0,

			0, 0, 0, 0, 0, 0, 0, 0, 10, 12, 14, 16, 16, 14, 12, 10, 10, 12, 14,
			16, 16, 14, 12, 10, 10, 12, 14, 16, 16, 14, 12, 10, 10, 12, 14, 16,
			16, 14, 12, 10, 8, 12, 14, 16, 16, 14, 12, 10, 8, 12, 14, 16, 16,
			14, 12, 10, 0, 0, 0, 0, 0, 0, 0, 0 };

	int[] passed_pawn_pcsq = { 0, 0, 0, 0, 0, 0, 0, 0, 100, 100, 100, 100, 100,
			100, 100, 100, 80, 80, 80, 80, 80, 80, 80, 80, 60, 60, 60, 60, 60,
			60, 60, 60, 40, 40, 40, 40, 40, 40, 40, 40, 20, 20, 20, 20, 20, 20,
			20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 0, 0, 0, 0, 0, 0, 0, 0,

			0, 0, 0, 0, 0, 0, 0, 0, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20, 20,
			20, 20, 20, 20, 20, 40, 40, 40, 40, 40, 40, 40, 40, 80, 80, 80, 80,
			80, 80, 80, 80, 60, 60, 60, 60, 60, 60, 60, 60, 100, 100, 100, 100,
			100, 100, 100, 100, 0, 0, 0, 0, 0, 0, 0, 0 };

	int[] protected_passer = { 0, 0, 0, 0, 0, 0, 0, 0, 125, 125, 125, 125, 125,
			125, 125, 125, 100, 100, 100, 100, 100, 100, 100, 100, 75, 75, 75,
			75, 75, 75, 75, 75, 50, 50, 50, 50, 50, 50, 50, 50, 25, 25, 25, 25,
			25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 0, 0, 0, 0, 0, 0,
			0, 0,

			0, 0, 0, 0, 0, 0, 0, 0, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25,
			25, 25, 25, 25, 25, 50, 50, 50, 50, 50, 50, 50, 50, 75, 75, 75, 75,
			75, 75, 75, 75, 100, 100, 100, 100, 100, 100, 100, 100, 125, 125,
			125, 125, 125, 125, 125, 125, 0, 0, 0, 0, 0, 0, 0, 0 };

	int P_KING_BLOCKS_ROOK = 24;
	int P_BLOCK_CENTRAL_PAWN = 24;
	int P_BISHOP_TRAPPED_A7 = 150;
	int P_BISHOP_TRAPPED_A6 = 50;
	int P_KNIGHT_TRAPPED_A8 = 150;
	int P_KNIGHT_TRAPPED_A7 = 100;

	/* minor penalties */

	int P_C3_KNIGHT = 5;
	int P_NO_FIANCHETTO = 4;

	/* king's defence */
	int SHIELD_1 = 10;
	int SHIELD_2 = 5;
	int P_NO_SHIELD = 10;

	/* minor bonuses */

	int ROOK_OPEN = 10;
	int ROOK_HALF = 5;
	int RETURNING_BISHOP = 20;
	int FIANCHETTO = 4;
	int TEMPO = 10;

	int ENDGAME_MAT = 1300;

	int[] e = pieceVals;
	int BISHOP_PAIR = 30;
	int P_KNIGHT_PAIR = 8;
	int P_ROOK_PAIR = 16;

	long setSquaresNearKing(long kingBoard) {

		int num = Long.numberOfLeadingZeros(kingBoard);

		int diff = -9;
		if (diff > 0) {
			if (num % 8 == 0) {
				return (circle >> diff) & ~fileH;
			} else if (num % 8 == 7) {
				return (circle >> diff) & ~fileA;
			}
		} else {
			if (num % 8 == 0) {
				return (circle << diff) & ~fileH;
			} else if (num % 8 == 7) {
				return (circle << diff) & ~fileA;
			}
		}
		return 0;

	}

	// newHugh

	int[] knight_adj = { -20, -16, -12, -8, -4, 0, 4, 8, 12 };

	int[] rook_adj = { 15, 12, 9, 6, 3, 0, -3, -6, -9 };

	public static int[] SafetyTable = { 0, 0, 1, 2, 3, 5, 7, 9, 12, 15, 18, 22,
			26, 30, 35, 39, 44, 50, 56, 62, 68, 75, 82, 85, 89, 97, 105, 113,
			122, 131, 140, 150, 169, 180, 191, 202, 213, 225, 237, 248, 260,
			272, 283, 295, 307, 319, 330, 342, 354, 366, 377, 389, 401, 412,
			424, 436, 448, 459, 471, 483, 494, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500, 500,
			500, 500, 500, 500, 500, 500, 500 };

	int blockedBishop(long[] bitBoardConfig, long allPieces) {

		int score = 0;

		// central pawn blocked, bishop hard to develop
		/*
		 * if (isPiece(WHITE, BISHOP, C1) && isPiece(WHITE, PAWN, D2) &&
		 * b.color[D3] != COLOR_EMPTY) v.Blockages[WHITE] -=
		 * e.P_BLOCK_CENTRAL_PAWN; if (isPiece(WHITE, BISHOP, F1) &&
		 * isPiece(WHITE, PAWN, E2) && b.color[E3] != COLOR_EMPTY)
		 * v.Blockages[WHITE] -= e.P_BLOCK_CENTRAL_PAWN; if (isPiece(BLACK,
		 * BISHOP, C8) && isPiece(BLACK, PAWN, D7) && b.color[D6] !=
		 * COLOR_EMPTY) v.Blockages[BLACK] -= e.P_BLOCK_CENTRAL_PAWN; if
		 * (isPiece(BLACK, BISHOP, F8) && isPiece(BLACK, PAWN, E7) &&
		 * b.color[E6] != COLOR_EMPTY) v.Blockages[BLACK] -=
		 * e.P_BLOCK_CENTRAL_PAWN;
		 */

		if (((bitBoardConfig[WB] & bitConstants[58]) != 0)
				&& ((bitBoardConfig[WP] & bitConstants[51]) != 0)
				&& ((allPieces & bitConstants[43]) != 0)) {
			score = score - P_BLOCK_CENTRAL_PAWN;
		}

		if (((bitBoardConfig[WB] & bitConstants[61]) != 0)
				&& ((bitBoardConfig[WP] & bitConstants[52]) != 0)
				&& ((allPieces & bitConstants[44]) != 0)) {
			score = score - P_BLOCK_CENTRAL_PAWN;
		}

		if (((bitBoardConfig[BB] & bitConstants[2]) != 0)
				&& ((bitBoardConfig[BP] & bitConstants[11]) != 0)
				&& ((allPieces & bitConstants[19]) != 0)) {
			score = score + P_BLOCK_CENTRAL_PAWN;
		}

		if (((bitBoardConfig[BB] & bitConstants[5]) != 0)
				&& ((bitBoardConfig[BP] & bitConstants[12]) != 0)
				&& ((allPieces & bitConstants[20]) != 0)) {
			score = score + P_BLOCK_CENTRAL_PAWN;
		}

		return score;
	}

	long rightWKBlock = bitConstants[61] | bitConstants[62];
	long leftWKBlock = bitConstants[57] | bitConstants[58];
	long rightBKBlock = bitConstants[5] | bitConstants[6];
	long leftBKBlock = bitConstants[1] | bitConstants[2];

	long rightWRBlock = bitConstants[63] | bitConstants[62];
	long leftWRBlock = bitConstants[57] | bitConstants[57];
	long rightBRBlock = bitConstants[7] | bitConstants[6];
	long leftBRBlock = bitConstants[1] | bitConstants[0];

	int blockedRook(long[] bitBoardConfig, long allPieces) {

		int score = 0;

		// uncastled king blocking own rook

		/*
		 * if ( ( isPiece(WHITE, KING, F1) || isPiece(WHITE, KING, G1 ) )&& (
		 * isPiece(WHITE, ROOK, H1) || isPiece(WHITE, ROOK, G1 ) ) )
		 * v.Blockages[WHITE] -= e.P_KING_BLOCKS_ROOK;
		 * 
		 * if ( ( isPiece(WHITE, KING, C1) || isPiece(WHITE, KING, B1 ) )&& (
		 * isPiece(WHITE, ROOK, A1) || isPiece(WHITE, ROOK, B1 ) ) )
		 * v.Blockages[WHITE] -= e.P_KING_BLOCKS_ROOK;
		 * 
		 * if ( ( isPiece(BLACK, KING, F8) || isPiece(BLACK, KING, G8 ) )&& (
		 * isPiece(BLACK, ROOK, H8) || isPiece(BLACK, ROOK, G8 ) ) )
		 * v.Blockages[BLACK] -= e.P_KING_BLOCKS_ROOK;
		 * 
		 * if ( ( isPiece(BLACK, KING, C8) || isPiece(BLACK, KING, B8 ) )&& (
		 * isPiece(BLACK, ROOK, A8) || isPiece(BLACK, ROOK, B8 ) ) )
		 * v.Blockages[BLACK] -= e.P_KING_BLOCKS_ROOK;
		 */

		if (((bitBoardConfig[WK] & rightWKBlock) != 0)
				&& (bitBoardConfig[WR] & rightWRBlock) != 0) {
			score = score - P_KING_BLOCKS_ROOK;
		}
		if (((bitBoardConfig[WK] & leftWKBlock) != 0)
				&& (bitBoardConfig[WR] & leftWRBlock) != 0) {
			score = score - P_KING_BLOCKS_ROOK;
		}

		if (((bitBoardConfig[BK] & rightBKBlock) != 0)
				&& (bitBoardConfig[BR] & rightBRBlock) != 0) {
			score = score - P_KING_BLOCKS_ROOK;
		}
		if (((bitBoardConfig[BK] & leftBKBlock) != 0)
				&& (bitBoardConfig[BR] & leftBRBlock) != 0) {
			score = score - P_KING_BLOCKS_ROOK;
		}

		return score;

	}

	int WEST = 1;
	int EAST = -1;

	long pawnSupport = bitConstants[0] | bitConstants[8];

	boolean isPawnSupported(long[] bitBoardConfig, int sq, boolean whiteMove) {

		/*
		 * if (side == WHITE) step = SOUTH; else step = NORTH;
		 * 
		 * if ( IS_SQ(sq+WEST) && isPiece(side,PAWN, sq + WEST) ) return 1; if (
		 * IS_SQ(sq+EAST) && isPiece(side,PAWN, sq + EAST) ) return 1; if (
		 * IS_SQ(sq+step+WEST) && isPiece(side,PAWN, sq + step+WEST ) ) return
		 * 1; if ( IS_SQ(sq+step+EAST) && isPiece(side,PAWN, sq + step+EAST ) )
		 * return 1;
		 */
		if (whiteMove) {
			if (sq % 8 != 7) {
				if ((bitBoardConfig[WP] & (pawnSupport >> (sq - 1))) != 0) {
					return true;
				}
			}
			if (sq % 8 != 0) {
				if ((bitBoardConfig[WP] & (pawnSupport >> (sq + 1))) != 0) {
					return true;
				}
			}
		} else {
			if (sq % 8 != 7) {
				if ((bitBoardConfig[BP] & (pawnSupport >> (sq - 9))) != 0) {
					return true;
				}
			}
			if (sq % 8 != 0) {
				if ((bitBoardConfig[BP] & (pawnSupport >> (sq - 7))) != 0) {
					return true;
				}
			}
		}

		return false;
	}

	int EvalPawn(int sq, long[] bitBoardConfig, boolean whiteMove, long allPawns) {
		int result = 0;
		/*
		 * int flagIsPassed = 1; // we will be trying to disprove that int
		 * flagIsWeak = 1; // we will be trying to disprove that int
		 * flagIsOpposed = 0;
		 */

		boolean flagIsPassed = true; // we will be trying to disprove that
		boolean flagIsWeak = true; // we will be trying to disprove that
		boolean flagIsOpposed = false;

		int stepFwd = -8;
		int stepBck = 8;

		/*
		 * if (side == WHITE) stepFwd = NORTH; else stepFwd = SOUTH; if (side ==
		 * WHITE) stepBck = SOUTH; else stepBck = NORTH; S8 nextSq = sq +
		 * stepFwd;
		 */
		int color = 0;
		int limit = sq / 8;
		int side = 0;
		int boardShift = 0;

		if (!whiteMove) {
			stepFwd = 8;
			stepBck = -8;
			limit = 7 - limit;
			color = 7;
			side = 1;
			boardShift = 64;
		}

		int nextSq = sq + stepFwd;

		/*************************************************************************
		 * We have only very basic data structures that do not update informa- *
		 * tion about pawns incrementally, so we have to calculate everything *
		 * here. The loop below detects doubled pawns, passed pawns and sets * a
		 * flag on finding that our pawn is opposed by enemy pawn. *
		 *************************************************************************/

		/*
		 * while (IS_SQ(nextSq)) {
		 * 
		 * if (b.pieces[nextSq] == PAWN) { // either opposed by enemy pawn or
		 * doubled flagIsPassed = 0; if (b.color[nextSq] == side) result -= 20;
		 * // doubled pawn penalty else flagIsOpposed = 1; // flag our pawn as
		 * opposed }
		 * 
		 * if (IS_SQ(nextSq + WEST) && isPiece(!side, PAWN, nextSq + WEST))
		 * flagIsPassed = 0;
		 * 
		 * if (IS_SQ(nextSq + EAST) && isPiece(!side, PAWN, nextSq + EAST))
		 * flagIsPassed = 0;
		 * 
		 * nextSq += stepFwd; }
		 */

		int playerPawn = WP + color;
		int opponentPawn = BP - color;

		for (int i = 0; i < limit; i++) {

			if ((bitConstants[nextSq] & allPawns) != 0) { // either opposed by
															// enemy pawn or
															// doubled
				flagIsPassed = false;
				if ((bitConstants[nextSq] & bitBoardConfig[playerPawn]) != 0) {
					result -= 20; // doubled pawn penalty
				} else {
					flagIsOpposed = true; // flag our pawn as opposed
				}
			}

			if (sq % 8 != 7) {
				if ((bitBoardConfig[opponentPawn] & (bitConstants[nextSq] >> 1)) != 0) {
					flagIsPassed = false;
				}
			}
			if (sq % 8 != 0) {
				if ((bitBoardConfig[opponentPawn] & (bitConstants[nextSq] << 1)) != 0) {
					flagIsPassed = false;
				}
			}

			nextSq = nextSq + stepFwd;
		}

		/*************************************************************************
		 * Another loop, going backwards and checking whether pawn has support.
		 * * Here we can at least break out of it for speed optimization. *
		 *************************************************************************/

		/*
		 * nextSq = sq;
		 * 
		 * while (IS_SQ(nextSq)) {
		 * 
		 * if (IS_SQ(nextSq + WEST) && isPiece(side, PAWN, nextSq + WEST)) {
		 * flagIsWeak = 0; break; }
		 * 
		 * if (IS_SQ(nextSq + EAST) && isPiece(side, PAWN, nextSq + EAST)) {
		 * flagIsWeak = 0; break; }
		 * 
		 * nextSq += stepBck; }
		 */

		nextSq = sq;
		limit = 7 - limit;

		for (int i = 0; i < limit; i++) {
			if (sq % 8 != 7) {
				if ((bitBoardConfig[playerPawn] & (bitConstants[nextSq] >> 1)) != 0) {
					flagIsWeak = false;
					break;
				}
			}
			if (sq % 8 != 0) {
				if ((bitBoardConfig[playerPawn] & (bitConstants[nextSq] << 1)) != 0) {
					flagIsWeak = false;
					break;
				}
			}

			nextSq = nextSq + stepFwd;
		}

		/*************************************************************************
		 * Evaluate passed pawns, scoring them higher if they are protected * or
		 * if their advance is supported by friendly pawns *
		 *************************************************************************/

		int shiftedPiece = sq + boardShift;

		if (flagIsPassed) {
			/*
			 * if ( isPawnSupported(sq, side) ) result +=
			 * e.protected_passer[side][sq]; else result +=
			 * e.passed_pawn[side][sq];
			 */

			if (isPawnSupported(bitBoardConfig, sq, whiteMove)) {
				result = result + protected_passer[shiftedPiece];
			} else {
				result = result + passed_pawn_pcsq[shiftedPiece];
			}
		}

		/*************************************************************************
		 * Evaluate weak pawns, increasing the penalty if they are situated * on
		 * a half-open file *
		 *************************************************************************/

		if (flagIsWeak) {
			result = result + weak_pawn_pcsq[shiftedPiece];
			if (!flagIsOpposed)
				result = result - 4;
		}

		return result;
	}

	int evalPawnStructure(int sq, long[] bitBoardConfig, boolean whiteMove,
			long allPawns) {
		int result = 0;

		/*
		 * for (U8 row = 0; row < 8; row++) for (U8 col = 0; col < 8; col++) {
		 * 
		 * S8 sq = SET_SQ(row, col);
		 * 
		 * if (b.pieces[sq] == PAWN) { if (b.color[sq] == WHITE) result +=
		 * EvalPawn(sq, WHITE); else result -= EvalPawn(sq, BLACK); } }
		 */

		int count = Long.bitCount(bitBoardConfig[WP]);
		long pieces = bitBoardConfig[WP];
		int position = 0;

		for (int i = 0; i < count; i++) {
			position = Long.numberOfLeadingZeros(pieces);
			result = result + EvalPawn(sq, bitBoardConfig, true, allPawns);
			pieces = pieces ^ bitConstants[position];
		}

		count = Long.bitCount(bitBoardConfig[BP]);
		pieces = bitBoardConfig[BP];
		position = Long.numberOfLeadingZeros(pieces);

		for (int i = 0; i < count; i++) {
			position = Long.numberOfLeadingZeros(pieces);
			result = result + EvalPawn(sq, bitBoardConfig, false, allPawns);
			pieces = pieces ^ bitConstants[position];
		}

		return result;
	}

	int getPawnScore(Long pawnKey, int sq, long[] bitBoardConfig,
			boolean whiteMove, long allPawns) {
		int result;

		/*
		 * int probeval = ttpawn_probe(); if (probeval != INVALID) return
		 * probeval;
		 * 
		 * result = evalPawnStructure();
		 * 
		 * ttpawn_save(result);
		 */

		Integer probeval = ChessBoardWithColumnsAndRows.pawnStructuers
				.get(pawnKey);

		if (probeval != null) {
			return probeval;
		}

		result = evalPawnStructure(sq, bitBoardConfig, whiteMove, allPawns);

		return result;
	}

	long bKingShieldKS7 = bitConstants[13] | bitConstants[14]
			| bitConstants[15];
	long bKingShieldKS6 = bitConstants[21] | bitConstants[22]
			| bitConstants[23];
	long bKingShieldQS7 = bitConstants[8] | bitConstants[9] | bitConstants[10];
	long bKingShieldQS6 = bitConstants[16] | bitConstants[17]
			| bitConstants[18];

	int bKingShield(long[] bitBoardConfig, int blackKingNum) {
		int result = 0;

		/* king on the kingside */

		/*
		 * if ( COL(b.KingLoc[BLACK]) > COL_E ) { if ( isPiece(BLACK, PAWN, F7)
		 * ) result += e.SHIELD_1; else if ( isPiece(BLACK, PAWN, F6) ) result
		 * += e.SHIELD_2;
		 * 
		 * if ( isPiece(BLACK, PAWN, G7) ) result += e.SHIELD_1; else if (
		 * isPiece(BLACK, PAWN, G6) ) result += e.SHIELD_2;
		 * 
		 * if ( isPiece(BLACK, PAWN, H7) ) result += e.SHIELD_1; else if (
		 * isPiece(BLACK, PAWN, H6) ) result += e.SHIELD_2; }
		 * 
		 * /* king on the queenside else if ( COL(b.KingLoc[BLACK]) < COL_D ) {
		 * if ( isPiece(BLACK, PAWN, A7) ) result += e.SHIELD_1; else if (
		 * isPiece(BLACK, PAWN, A6) ) result += e.SHIELD_2;
		 * 
		 * if ( isPiece(BLACK, PAWN, B7) ) result += e.SHIELD_1; else if (
		 * isPiece(BLACK, PAWN, B6) ) result += e.SHIELD_2;
		 * 
		 * if ( isPiece(BLACK, PAWN, C7) ) result += e.SHIELD_1; else if (
		 * isPiece(BLACK, PAWN, C6) ) result += e.SHIELD_2; }
		 */

		if (blackKingNum % 8 > 3) {
			result = result + SHIELD_1
					* Long.bitCount(bitBoardConfig[BP] & bKingShieldKS7)
					+ SHIELD_2
					* Long.bitCount(bitBoardConfig[BP] & bKingShieldKS6);
		} else {
			result = result + SHIELD_1
					* Long.bitCount(bitBoardConfig[BP] & bKingShieldQS7)
					+ SHIELD_2
					* Long.bitCount(bitBoardConfig[BP] & bKingShieldQS6);
		}

		return result;
	}

	long wKingShieldKS2 = bitConstants[55] | bitConstants[54]
			| bitConstants[53];
	long wKingShieldKS3 = bitConstants[47] | bitConstants[46]
			| bitConstants[45];
	long wKingShieldQS2 = bitConstants[48] | bitConstants[49]
			| bitConstants[50];
	long wKingShieldQS3 = bitConstants[40] | bitConstants[41]
			| bitConstants[42];

	int wKingShield(long[] bitBoardConfig, int whiteKingNum) {

		int result = 0;

		/*
		 * king on the kingside
		 * 
		 * if ( COL(b.KingLoc[WHITE]) > COL_E ) {
		 * 
		 * if ( isPiece(WHITE, PAWN, F2) ) result += e.SHIELD_1; else if (
		 * isPiece(WHITE, PAWN, F3) ) result += e.SHIELD_2;
		 * 
		 * if ( isPiece(WHITE, PAWN, G2) ) result += e.SHIELD_1; else if (
		 * isPiece(WHITE, PAWN, G3) ) result += e.SHIELD_2;
		 * 
		 * if ( isPiece(WHITE, PAWN, H2) ) result += e.SHIELD_1; else if (
		 * isPiece(WHITE, PAWN, H3) ) result += e.SHIELD_2; }
		 * 
		 * /* king on the queenside else if ( COL(b.KingLoc[WHITE]) < COL_D ) {
		 * 
		 * if ( isPiece(WHITE, PAWN, A2) ) result += e.SHIELD_1; else if (
		 * isPiece(WHITE, PAWN, A3) ) result += e.SHIELD_2;
		 * 
		 * if ( isPiece(WHITE, PAWN, B2) ) result += e.SHIELD_1; else if (
		 * isPiece(WHITE, PAWN, B3) ) result += e.SHIELD_2;
		 * 
		 * if ( isPiece(WHITE, PAWN, C2) ) result += e.SHIELD_1; else if (
		 * isPiece(WHITE, PAWN, C3) ) result += e.SHIELD_2; }
		 */

		if (whiteKingNum % 8 > 3) {
			result = result + SHIELD_1
					* Long.bitCount(bitBoardConfig[BP] & wKingShieldKS2)
					+ SHIELD_2
					* Long.bitCount(bitBoardConfig[BP] & wKingShieldKS3);
		} else {
			result = result + SHIELD_1
					* Long.bitCount(bitBoardConfig[BP] & wKingShieldQS2)
					+ SHIELD_2
					* Long.bitCount(bitBoardConfig[BP] & wKingShieldQS3);
		}

		return result;
	}

	long whiteBDevelop = bitConstants[58] | bitConstants[61];
	long whiteNDevelop = bitConstants[57] | bitConstants[62];

	long blackBDevelop = bitConstants[2] | bitConstants[5];
	long blackNDevelop = bitConstants[1] | bitConstants[6];

	int EvalQueen(int sq, long[] bitBoardConfig, boolean whiteMove) {

		int result = 0;

		// v.gamePhase += 4;
		int att = 0;
		int mob = 0;

		/****************************************************************
		 * A queen should not be developed too early *
		 ****************************************************************/

		/*
		 * if (side == WHITE && ROW(sq) > ROW_2) { if (isPiece(WHITE, KNIGHT,
		 * B1)) v.PositionalThemes[WHITE] -= 2; if (isPiece(WHITE, BISHOP, C1))
		 * v.PositionalThemes[WHITE] -= 2; if (isPiece(WHITE, BISHOP, F1))
		 * v.PositionalThemes[WHITE] -= 2; if (isPiece(WHITE, KNIGHT, G1))
		 * v.PositionalThemes[WHITE] -= 2; }
		 * 
		 * if (side == BLACK && ROW(sq) < ROW_7) { if (isPiece(BLACK, KNIGHT,
		 * B8)) v.PositionalThemes[BLACK] -= 2; if (isPiece(BLACK, BISHOP, C8))
		 * v.PositionalThemes[BLACK] -= 2; if (isPiece(BLACK, BISHOP, F8))
		 * v.PositionalThemes[BLACK] -= 2; if (isPiece(BLACK, KNIGHT, G8))
		 * v.PositionalThemes[BLACK] -= 2; }
		 */

		if (whiteMove) {
			result = result
					- 2
					* Long.bitCount(((bitBoardConfig[WN] & whiteNDevelop) | (bitBoardConfig[WB] & whiteBDevelop)));
		} else {
			result = result
					+ 2
					* Long.bitCount(((bitBoardConfig[BN] & blackNDevelop) | (bitBoardConfig[BB] & blackBDevelop)));

		}

		/****************************************************************
		 * Collect data about mobility and king attacks *
		 ****************************************************************/

		/*
		 * for (char dir=0;dir<vectors[QUEEN];dir++) {
		 * 
		 * for (char pos = sq;;) {
		 * 
		 * pos = pos + vector[QUEEN][dir]; if (! IS_SQ(pos)) break;
		 * 
		 * if (b.pieces[pos] == PIECE_EMPTY) { mob++; if ( e.sqNearK[!side]
		 * [b.KingLoc[!side] ] [pos] ) ++att; } else if (b.color[pos] != side) {
		 * mob++; if ( e.sqNearK[!side] [b.KingLoc[!side] ] [pos] ) ++att;
		 * break; } else { break; }
		 * 
		 * } }
		 */
		
		ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
				.get(ChessBoardWithColumnsAndRows.pieceRandoms[64 * (WQ - 1)
						+ sq]);
		for (int i = 0; i < list.size(); i++) {
			ArrayList<Long[]> l = list.get(i);
			l2: for (int j = 0; j < l.size(); j++) {
				Long[] arr = l.get(j);
				if ((arr[2] & bitBoardConfig[W]) == 0) {
					if ((arr[2] & bitBoardConfig[B]) == 0) {
						moveScoreTuple tuple = new moveScoreTuple();
						tuple.move = arr;
						possibleMoves.add(tuple);
					} else {
						for (int t = 8; t < 14; t++) {
							if ((arr[2] & bitBoardConfig[t]) != 0) {
								moveScoreTuple tuple = new moveScoreTuple();
								tuple.takenPiece = t;

								tuple.move = arr;
								possibleMoves.add(tuple);
								break l2;
							}
						}
					}
				} else {
					break l2;
				}
			}
		}

		v.mgMob[side] += 1 * (mob - 14);
		v.egMob[side] += 2 * (mob - 14);

		if (att) {
			v.attCnt[side]++;
			v.attWeight[side] += 4 * att;
		}

	}

	// ////////////////////////////////////////////////////////////////////////////////////////

	public static final int W = 0, WP = 1, WN = 2, WB = 3, WR = 4, WQ = 5,
			WK = 6, B = 7, BP = 8, BN = 9, BB = 10, BR = 11, BQ = 12, BK = 13;

	public static final String[] pieceNames = { "W", "WP", "WN", "WB", "WR",
			"WQ", "WK", "B", "BP", "BN", "BB", "BR", "BQ", "BK" };

	public static final String[] pieces = { "pawn", "knight", "bishop", "rook",
			"queen", "king" };

	/*
	public static int[] pieceVals = { 0, 100, 325, 335, 500, 975, 0, 0, -100,
			-325, -335, -500, -975, 0 };
	*/
	
	public static int[] pieceVals= { 0, 1000, 3000, 3250, 5000, 9000,
		0, 0, -1000, -3000, -3250, -5000, -9000, 0};


	
	public static int[] pieceValues = { 0, 9000, 100000, 5000, 3000, 3250,
			1000, 1000, 3000, 3250, 5000, 100000, 9000 };

	public static long[] bitConstants = bitConstants();

	public static long circle = (bitConstants[0] | bitConstants[1]
			| bitConstants[2] | bitConstants[10] | bitConstants[18]
			| bitConstants[17] | bitConstants[16] | bitConstants[8]);

	public static long fileH = (bitConstants[7] | bitConstants[15]
			| bitConstants[23] | bitConstants[31] | bitConstants[39]
			| bitConstants[47] | bitConstants[55] | bitConstants[63]);

	public static long fileA = (bitConstants[0] | bitConstants[8]
			| bitConstants[16] | bitConstants[24] | bitConstants[32]
			| bitConstants[40] | bitConstants[48] | bitConstants[56]);

	public static long row2 = (bitConstants[48] | bitConstants[49]
			| bitConstants[50] | bitConstants[51] | bitConstants[52]
			| bitConstants[53] | bitConstants[54] | bitConstants[55]);

	public static long row7 = (bitConstants[8] | bitConstants[9]
			| bitConstants[10] | bitConstants[11] | bitConstants[12]
			| bitConstants[13] | bitConstants[14] | bitConstants[15]);

	public static long boardCenter = (bitConstants[36] | bitConstants[35]
			| bitConstants[28] | bitConstants[27]);
	public static long boardShellWhite = (bitConstants[45] | bitConstants[44]
			| bitConstants[43] | bitConstants[42] | bitConstants[37] | bitConstants[34]);

	public static long boardShellBlack = (bitConstants[29] | bitConstants[21]
			| bitConstants[20] | bitConstants[19] | bitConstants[18] | bitConstants[26]);

	public static long rank1 = row7 << 8;

	public static long[] bitConstants() {
		long[] bitConstants = new long[64];

		for (int i = 0; i < bitConstants.length; i++) {
			bitConstants[63 - i] = (1l << i);

		}
		/*
		 * for (int i = 0; i < bitConstants.length; i++){
		 * printBoardLevel(bitConstants[i]); }
		 */
		return bitConstants;

	}

	public static long[] bitBoardGen(String[][] chessBoardConfig) {
		long[] bitConstants = bitConstants();
		long[] bitBoardConfig = new long[14];

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (chessBoardConfig[i][j].contains("white")) {
					bitBoardConfig[W] = bitBoardConfig[W]
							| bitConstants[i * 8 + j];
					if (chessBoardConfig[i][j].contains("pawn")) {
						bitBoardConfig[WP] = bitBoardConfig[WP]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("knight")) {
						bitBoardConfig[WN] = bitBoardConfig[WN]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("bishop")) {
						bitBoardConfig[WB] = bitBoardConfig[WB]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("rook")) {
						bitBoardConfig[WR] = bitBoardConfig[WR]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("queen")) {
						bitBoardConfig[WQ] = bitBoardConfig[WQ]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("king")) {
						bitBoardConfig[WK] = bitBoardConfig[WK]
								| bitConstants[i * 8 + j];
					}

				}

				if (chessBoardConfig[i][j].contains("black")) {
					bitBoardConfig[B] = bitBoardConfig[B]
							| bitConstants[i * 8 + j];
					if (chessBoardConfig[i][j].contains("pawn")) {
						bitBoardConfig[BP] = bitBoardConfig[BP]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("knight")) {
						bitBoardConfig[BN] = bitBoardConfig[BN]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("bishop")) {
						bitBoardConfig[BB] = bitBoardConfig[BB]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("rook")) {
						bitBoardConfig[BR] = bitBoardConfig[BR]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("queen")) {
						bitBoardConfig[BQ] = bitBoardConfig[BQ]
								| bitConstants[i * 8 + j];
					}

					if (chessBoardConfig[i][j].contains("king")) {
						bitBoardConfig[BK] = bitBoardConfig[BK]
								| bitConstants[i * 8 + j];
					}
				}
			}
		}
		return bitBoardConfig;
	}

	public static int boardScore(long[] bitBoardConfig) {
		int c = 0;
		int numWP = Long.bitCount(bitBoardConfig[WP]);
		int numWN = Long.bitCount(bitBoardConfig[WN]);
		int numWB = Long.bitCount(bitBoardConfig[WB]);
		int numWR = Long.bitCount(bitBoardConfig[WR]);
		int numWQ = Long.bitCount(bitBoardConfig[WQ]);
		int numWK = Long.bitCount(bitBoardConfig[WK]);

		int numBP = Long.bitCount(bitBoardConfig[BP]);
		int numBN = Long.bitCount(bitBoardConfig[BN]);
		int numBB = Long.bitCount(bitBoardConfig[BB]);
		int numBR = Long.bitCount(bitBoardConfig[BR]);
		int numBQ = Long.bitCount(bitBoardConfig[BQ]);
		int numBK = Long.bitCount(bitBoardConfig[BK]);

		c = c
				+ (numWP * pieceVals[WP] + numWN * pieceVals[WN] + numWB
						* pieceVals[WB] + numWR * pieceVals[WR] + numWQ
						* pieceVals[WQ] + numWK * pieceVals[WK])
				+ (numBP * pieceVals[BP] + numBN * pieceVals[BN] + numBB
						* pieceVals[BB] + numBR * pieceVals[BR] + numBQ
						* pieceVals[BQ] + numBK * pieceVals[BK]);

		return c;
	}

	public static int tacticalPositionValue(long[] bitBoardConfig,	long blackBoard, long whiteBoard) {
		int c = 0;

		long newBlack = (blackBoard & ~whiteBoard);
		long newWhite = (whiteBoard & ~blackBoard);

		int[] p = ChessBoardWithColumnsAndRows.personality;

		// int centerDefendBonus = personality[0];

		// int ring1DefendBonus = personality[1];

		// int centerControlBonus = personality[2];

		// int ring1ControlBonus = personality[3];

		// int totalLandControl = personality[4];

		// int totalLandDefend = personality[5];

		// int blackBonus = personality[6];

		// int whiteBonus = personality[7];

		c = c
				+ p[0]
				* (p[7] * Long.bitCount(whiteBoard & boardCenter) - p[6]
						* Long.bitCount(blackBoard & boardCenter));

		c = c
				+ p[1]
				* (p[7] * Long.bitCount((whiteBoard & boardShellWhite) - p[6]
						* Long.bitCount((blackBoard & boardShellBlack))));

		c = c
				+ p[2]
				* (p[7] * Long.bitCount(newWhite & boardCenter) - p[6]
						* Long.bitCount(newBlack & boardCenter));

		c = c
				+ p[3]
				* (p[7] * Long.bitCount((newWhite & boardShellWhite) - p[6]
						* Long.bitCount((newBlack & boardShellBlack))));

		c = c
				+ p[4]
				* (p[7] * Long.bitCount((newWhite) - p[6]
						* Long.bitCount(newBlack)));

		c = c
				+ p[5]
				* (p[7] * Long.bitCount((whiteBoard) - p[6]
						* Long.bitCount(blackBoard & boardShellBlack)));

		/*
		if (ChessBoardWithColumnsAndRows.beginning) {
			if (bitBoardConfig[12] != bitBoardOperations.bitConstants[3]) {
				c = c + 100;
			}
			if (bitBoardConfig[13] != bitBoardOperations.bitConstants[4]) {
				c = c + 100;
			}
			if ((bitBoardConfig[8] & bitBoardOperations.bitConstants[28]) != 0) {
				c = c + 100;
			}
		}
*/
		return c;
	}

	public static boolean enemyAttacked(boolean whiteMove,
			long[] bitBoardConfig, long squares) {
		if (!whiteMove) {
			ArrayList<Long[]> whiteMoves = tacticalMoveGen2(bitBoardConfig,
					true);
			for (int i = 0; i < whiteMoves.size(); i++) {
				if ((whiteMoves.get(i)[2] & squares) != 0) {
					return true;
				}
			}
		} else {
			ArrayList<Long[]> blackMoves = tacticalMoveGen2(bitBoardConfig,
					false);
			for (int i = 0; i < blackMoves.size(); i++) {
				if ((blackMoves.get(i)[2] & squares) != 0) {
					return true;
				}
			}
		}
		return false;
	}

	public static long tacticalMapMaker(Node n, boolean whiteMove) {

		long[] bitBoardConfig = n.board;
		int color = 0;
		if (!whiteMove) {
			color = 7;
		}

		int size = n.children.size();
		Set<Long> s = n.children.keySet();
		Iterator<Long> it = s.iterator();

		long attackBoard = 0L;

		for (int i = 0; i < size; i++) {
			Node thisNode = n.children.get(it.next());
			long move = (thisNode.board[color] & ~bitBoardConfig[color]);
			attackBoard = (attackBoard | move);
		}
		return attackBoard;
	}

	public static boolean whiteInCheck(long[] bitBoardConfig) {
		Long[] b = null;
		ArrayList<moveScoreTuple> blackMoves = tacticalMoveGen3(bitBoardConfig,
				false);

		for (int i = 0; i < blackMoves.size(); i++) {
			b = blackMoves.get(i).move;
			if ((b[2] & bitBoardConfig[WK]) != 0) {
				return true;
			}
		}
		return false;

	}

	public static boolean blackInCheck(long[] bitBoardConfig) {
		Long[] b = null;
		ArrayList<moveScoreTuple> whiteMoves = tacticalMoveGen3(bitBoardConfig,
				true);

		for (int i = 0; i < whiteMoves.size(); i++) {
			b = whiteMoves.get(i).move;
			if ((b[2] & bitBoardConfig[BK]) != 0) {
				return true;
			}
		}
		return false;

	}

	public static ArrayList<Long[]> pawnAttackMoves(long[] bitBoardConfig,
			boolean whiteMove) {
		ArrayList<Long[]> possibleMoves = new ArrayList<Long[]>(40);
		int c = 0;
		int color = 0;
		if (!whiteMove) {
			color = 7;
		}

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		int start = -1;
		int end = -1;

		if (whiteMove) {
			long attackLeft = ((bitBoardConfig[WP] << 9) & ~fileH);

			start = Long.numberOfLeadingZeros(attackLeft);
			end = 64 - Long.numberOfTrailingZeros(attackLeft);

			end = Math.min(end, 55);

			for (int i = start; i < end; i++) {
				if ((attackLeft & bitConstants[i]) != 0) {
					Long[] move = {
							(long) WP,
							bitConstants[i + 9],
							bitConstants[i],
							(long) Long
									.numberOfLeadingZeros(bitConstants[i + 9]),
							(long) Long.numberOfLeadingZeros(bitConstants[i]) };
					possibleMoves.add(move);
					c++;
				}
			}

			long attackRight = ((bitBoardConfig[WP] << 7) & ~fileA);

			start = Long.numberOfLeadingZeros(attackRight);
			end = 64 - Long.numberOfTrailingZeros(attackRight);

			end = Math.min(end, 57);

			for (int i = start; i < end; i++) {
				if ((attackRight & bitConstants[i]) != 0) {
					Long[] move = {
							(long) WP,
							bitConstants[i + 7],
							bitConstants[i],
							(long) Long
									.numberOfLeadingZeros(bitConstants[i + 7]),
							(long) Long.numberOfLeadingZeros(bitConstants[i]) };
					possibleMoves.add(move);
					c++;
				}
			}
		} else {
			long attackLeft = ((bitBoardConfig[BP] >> 9) & ~fileA);

			start = Long.numberOfLeadingZeros(attackLeft);
			end = 64 - Long.numberOfTrailingZeros(attackLeft);

			start = Math.max(start, 9);

			for (int i = start; i < end; i++) {
				if ((attackLeft & bitConstants[i]) != 0) {
					Long[] move = {
							(long) BP,
							bitConstants[i - 9],
							bitConstants[i],
							(long) Long
									.numberOfLeadingZeros(bitConstants[i - 9]),
							(long) Long.numberOfLeadingZeros(bitConstants[i]) };
					possibleMoves.add(move);
					c++;
				}
			}

			long attackRight = ((bitBoardConfig[BP] >> 7) & ~fileH);

			start = Long.numberOfLeadingZeros(attackRight);
			end = 64 - Long.numberOfTrailingZeros(attackRight);

			start = Math.max(start, 7);

			for (int i = start; i < end; i++) {
				if ((attackRight & bitConstants[i]) != 0) {
					Long[] move = {
							(long) BP,
							bitConstants[i - 7],
							bitConstants[i],
							(long) Long
									.numberOfLeadingZeros(bitConstants[i - 7]),
							(long) Long.numberOfLeadingZeros(bitConstants[i]) };
					possibleMoves.add(move);
					c++;
				}
			}
		}
		return possibleMoves;
	}

	public static ArrayList<Long[]> pawnStartMoves(long[] bitBoardConfig,
			boolean whiteMove) {
		ArrayList<Long[]> possibleMoves = new ArrayList<Long[]>(40);
		int c = 0;
		int color = 0;
		if (!whiteMove) {
			color = 7;
		}

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		int start = -1;
		int end = -1;

		if (whiteMove) {
			long forwardOne = ((bitBoardConfig[WP] << 8) & ~allPieces);

			start = Long.numberOfLeadingZeros(forwardOne);
			end = 64 - Long.numberOfTrailingZeros(forwardOne);

			end = Math.min(end, 56);

			for (int i = start; i < end; i++) {
				if ((forwardOne & bitConstants[i]) != 0) {
					Long[] move = {
							(long) WP,
							bitConstants[i + 8],
							bitConstants[i],
							(long) Long
									.numberOfLeadingZeros(bitConstants[i + 8]),
							(long) Long.numberOfLeadingZeros(bitConstants[i]) };
					possibleMoves.add(move);
					c++;
				}
			}

			if ((bitBoardConfig[WP] & row2) != 0) {
				long forwardTwo = (((bitBoardConfig[WP] & row2) << 16)
						& ~allPieces & ~(allPieces << 8));

				start = Long.numberOfLeadingZeros(forwardTwo);
				end = 64 - Long.numberOfTrailingZeros(forwardTwo);

				// start = Math.max(start, 0);
				end = Math.min(end, 48);

				for (int i = start; i < end; i++) {
					if ((forwardTwo & bitConstants[i]) != 0) {
						Long[] move = {
								(long) WP,
								bitConstants[i + 16],
								bitConstants[i],
								(long) Long
										.numberOfLeadingZeros(bitConstants[i + 16]),
								(long) Long
										.numberOfLeadingZeros(bitConstants[i]) };
						possibleMoves.add(move);
						c++;
					}
				}
			}
		}

		else {
			long forwardOne = ((bitBoardConfig[BP] >> 8) & ~allPieces);
			start = Long.numberOfLeadingZeros(forwardOne);
			end = 64 - Long.numberOfTrailingZeros(forwardOne);

			start = Math.max(start, 8);
			// end = Math.min(end, 63);

			for (int i = start; i < end; i++) {
				if ((forwardOne & bitConstants[i]) != 0) {
					Long[] move = {
							(long) BP,
							bitConstants[i - 8],
							bitConstants[i],
							(long) Long
									.numberOfLeadingZeros(bitConstants[i - 8]),
							(long) Long.numberOfLeadingZeros(bitConstants[i]) };
					possibleMoves.add(move);
					c++;
				}
			}

			if ((bitBoardConfig[BP] & row7) != 0) {
				long forwardTwo = (((bitBoardConfig[BP] & row7) >> 16)
						& ~allPieces & ~(allPieces >> 8));

				start = Long.numberOfLeadingZeros(forwardTwo);
				end = 64 - Long.numberOfTrailingZeros(forwardTwo);

				start = Math.max(start, 16);
				// end = Math.min(end, 63);

				for (int i = start; i < end; i++) {
					if ((forwardTwo & bitConstants[i]) != 0) {
						Long[] move = {
								(long) BP,
								bitConstants[i - 16],
								bitConstants[i],
								(long) Long
										.numberOfLeadingZeros(bitConstants[i - 16]),
								(long) Long
										.numberOfLeadingZeros(bitConstants[i]) };
						possibleMoves.add(move);
						c++;
					}
				}
			}
		}
		return possibleMoves;
	}

	public static ArrayList<ArrayList<Long[]>> edgeAdjacent(
			long[] bitBoardConfig, boolean whiteMove) {
		ArrayList<ArrayList<Long[]>> possibleMoves = new ArrayList<ArrayList<Long[]>>(
				4);

		ArrayList<Long[]> possibleMoves1 = new ArrayList<Long[]>();
		ArrayList<Long[]> possibleMoves2 = new ArrayList<Long[]>();
		ArrayList<Long[]> possibleMoves3 = new ArrayList<Long[]>();
		ArrayList<Long[]> possibleMoves4 = new ArrayList<Long[]>();

		int c = 0;
		int color = 0;
		if (!whiteMove) {
			color = 7;
		}

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		int start = -1;
		int end = -1;

		int location = 0;
		long temp = 0;
		long firstPiece = 0;

		int num = Long.numberOfLeadingZeros(allPieces);
		firstPiece = bitConstants[num];

		// down right
		int limit = Math.min(8 - (num / 8), 8 - (num % 8));
		for (int i = 1; i < limit; i++) {

			location = num + 9 * i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			Long[] move = { null, firstPiece, temp, (long) num, (long) location };
			possibleMoves1.add(move);
			c++;
			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}

		// down left
		limit = Math.min(8 - (num / 8), 1 + (num % 8));
		for (int i = 1; i < limit; i++) {

			location = num + 7 * i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			location = Long.numberOfLeadingZeros(temp);
			Long[] move = { null, firstPiece, temp, (long) num, (long) location };
			possibleMoves2.add(move);
			c++;
			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}
		// up left
		limit = Math.min(1 + (num / 8), 1 + (num % 8));
		for (int i = 1; i < limit; i++) {

			location = num - 9 * i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			Long[] move = { null, firstPiece, temp, (long) num, (long) location };
			possibleMoves3.add(move);
			c++;
			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}
		// up right
		limit = Math.min(1 + (num / 8), 8 - (num % 8));
		for (int i = 1; i < limit; i++) {

			location = num - 7 * i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			Long[] move = { null, firstPiece, temp, (long) num, (long) location };
			possibleMoves4.add(move);
			c++;
			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}

		possibleMoves.add(possibleMoves1);
		possibleMoves.add(possibleMoves2);
		possibleMoves.add(possibleMoves3);
		possibleMoves.add(possibleMoves4);

		return possibleMoves;

	}

	public static ArrayList<ArrayList<Long[]>> orthAdjacent(
			long[] bitBoardConfig, boolean whiteMove) {
		ArrayList<ArrayList<Long[]>> possibleMoves = new ArrayList<ArrayList<Long[]>>(
				4);

		ArrayList<Long[]> possibleMoves1 = new ArrayList<Long[]>();
		ArrayList<Long[]> possibleMoves2 = new ArrayList<Long[]>();
		ArrayList<Long[]> possibleMoves3 = new ArrayList<Long[]>();
		ArrayList<Long[]> possibleMoves4 = new ArrayList<Long[]>();

		int color = 0;
		if (!whiteMove) {
			color = 7;
		}

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		int start = -1;
		int end = -1;

		int location = 0;
		long temp = 0;
		long firstPiece = 0;

		int num = Long.numberOfLeadingZeros(allPieces);
		firstPiece = bitConstants[num];

		// down
		int limit = (8 - (num / 8));
		for (int i = 1; i < limit; i++) {

			location = num + 8 * i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			Long[] move = { null, firstPiece, temp, (long) num, (long) location };

			possibleMoves1.add(move);

			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}
		// right
		limit = (8 - (num % 8));
		for (int i = 1; i < limit; i++) {
			location = num + i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			Long[] move = { null, firstPiece, temp, (long) num, (long) location };
			possibleMoves2.add(move);

			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}
		// up
		limit = (1 + (num / 8));
		for (int i = 1; i < limit; i++) {

			location = num - 8 * i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			Long[] move = { null, firstPiece, temp, (long) num, (long) location };
			possibleMoves3.add(move);

			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}
		// left
		limit = (1 + (num % 8));
		for (int i = 1; i < limit; i++) {

			location = num - i;

			temp = (bitConstants[location]);

			if ((temp & bitBoardConfig[W + color]) != 0) {
				break;
			}

			Long[] move = { null, firstPiece, temp, (long) num, (long) location };
			possibleMoves4.add(move);

			if ((temp & bitBoardConfig[B - color]) != 0) {
				break;
			}
		}

		possibleMoves.add(possibleMoves1);
		possibleMoves.add(possibleMoves2);
		possibleMoves.add(possibleMoves3);
		possibleMoves.add(possibleMoves4);

		return possibleMoves;

	}

	public static listAndTactical completeMoveGen2(long[] bitBoardConfig,
			boolean whiteMove, boolean wcr, boolean wcl, boolean bcr,
			boolean bcl) {
		// ArrayList<Long[]> possibleMoves = new ArrayList<Long[]>(40);

		// AIv2Tree f= new AIv2Tree();

		ArrayList<moveScoreTuple> possibleMoves = new ArrayList<moveScoreTuple>();

		long tBoard = 0L;

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		if (whiteMove) {
			if (bitBoardConfig[WP] != 0) {
				long pawns = bitBoardConfig[WP];
				int s = 0;
				int count = Long.bitCount(pawns);

				for (int i = 0; i < count; i++) {
					s = Long.numberOfLeadingZeros(pawns);
					ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WP - 1) + s]);
					ArrayList<Long[]> l = list.get(0);
					l1: for (int k = 0; k < l.size(); k++) {
						Long[] arr = l.get(k);
						// System.out.println("forward");
						// bitBoardOperations.printBoardLevel(bitConstants[i]
						// | arr[2]);
						if ((arr[2] & allPieces) == 0) {
							AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
							tuple.move = arr;
							possibleMoves.add(tuple);

						} else {
							break l1;
						}
					}

					l = list.get(1);
					for (int k = 0; k < l.size(); k++) {
						Long[] arr = l.get(k);
						tBoard = tBoard |  arr[2];
						// System.out.println("attack");
						// bitBoardOperations.printBoardLevel(bitConstants[i]
						// | arr[2]);
						if ((arr[2] & bitBoardConfig[B]) != 0) {
							l1: for (int t = 8; t < 14; t++) {
								if ((arr[2] & bitBoardConfig[t]) != 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.takenPiece = t;

									tuple.move = arr;
									possibleMoves.add(tuple);

									break l1;
								}
							}

						}
					}
					pawns = pawns ^ bitConstants[s];
				}
			}

			if (bitBoardConfig[WN] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WN]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WN]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WN - 1) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					tBoard = tBoard |  l[2];
					if ((l[2] & bitBoardConfig[W]) == 0) {
						moveScoreTuple tuple = new moveScoreTuple();
						if ((l[2] & bitBoardConfig[B]) != 0) {
							l1: for (int t = 8; t < 14; t++) {
								if ((l[2] & bitBoardConfig[t]) != 0) {
									tuple.takenPiece = t;

									break l1;
								}
							}
						}

						tuple.move = l;
						possibleMoves.add(tuple);
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable.get(
							ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WN - 1) + e]).get(0);
					for (int i = 0; i < list.size(); i++) {
						Long[] l = list.get(i);
						tBoard = tBoard |  l[2];
						if ((l[2] & bitBoardConfig[W]) == 0) {
							moveScoreTuple tuple = new moveScoreTuple();
							if ((l[2] & bitBoardConfig[B]) != 0) {
								l1: for (int t = 8; t < 14; t++) {
									if ((l[2] & bitBoardConfig[t]) != 0) {
										tuple.takenPiece = t;

										break l1;
									}
								}
							}

							tuple.move = l;
							possibleMoves.add(tuple);
						}
					}
				}

			}

			if (bitBoardConfig[WK] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WK]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WK - 1) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					tBoard = tBoard |  l[2];
					// System.out.println(Arrays.toString(l));
					if ((l[2] & bitBoardConfig[W]) == 0) {
						moveScoreTuple tuple = new moveScoreTuple();
						if ((l[2] & bitBoardConfig[B]) != 0) {
							l1: for (int t = 8; t < 14; t++) {
								if ((l[2] & bitBoardConfig[t]) != 0) {
									tuple.takenPiece = t;

									break l1;
								}
							}
						}

						tuple.move = l;
						possibleMoves.add(tuple);
					}
				}
			}

			if (bitBoardConfig[WB] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WB]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WB]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WB - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					l2: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						tBoard = tBoard |  arr[2];
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								moveScoreTuple tuple = new moveScoreTuple();
								tuple.move = arr;
								possibleMoves.add(tuple);
							} else {
								for (int t = 8; t < 14; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										moveScoreTuple tuple = new moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break l2;
									}
								}
							}
						} else {
							break l2;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WB - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						l2: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							tBoard = tBoard |  arr[2];
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								if ((arr[2] & bitBoardConfig[B]) == 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.move = arr;
									possibleMoves.add(tuple);
								} else {
									for (int t = 8; t < 14; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											moveScoreTuple tuple = new moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break l2;
										}
									}
								}
							} else {
								break l2;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WQ] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WQ]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WQ]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WQ - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					l2: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						tBoard = tBoard | arr[2];
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								moveScoreTuple tuple = new moveScoreTuple();
								tuple.move = arr;
								possibleMoves.add(tuple);
							} else {
								for (int t = 8; t < 14; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										moveScoreTuple tuple = new moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break l2;
									}
								}
							}
						} else {
							break l2;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WQ - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						l2: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							tBoard = tBoard |  arr[2];
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								if ((arr[2] & bitBoardConfig[B]) == 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.move = arr;
									possibleMoves.add(tuple);
								} else {
									for (int t = 8; t < 14; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											moveScoreTuple tuple = new moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break l2;
										}
									}
								}
							} else {
								break l2;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WR] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WR]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WR]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WR - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					l2: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						tBoard = tBoard |  arr[2];
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								moveScoreTuple tuple = new moveScoreTuple();
								tuple.move = arr;
								possibleMoves.add(tuple);
							} else {
								for (int t = 8; t < 14; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										moveScoreTuple tuple = new moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break l2;
									}
								}
							}
						} else {
							break l2;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WR - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						l2: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							tBoard = tBoard |  arr[2];
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								if ((arr[2] & bitBoardConfig[B]) == 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.move = arr;
									possibleMoves.add(tuple);
								} else {
									for (int t = 8; t < 14; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											moveScoreTuple tuple = new moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break l2;
										}
									}
								}
							} else {
								break l2;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WK] == bitConstants[60]) {
				long a = (bitConstants[57] | bitConstants[58] | bitConstants[59]);
				if (wcl == false && ((a & allPieces) == 0)
						&& !bitBoardOperations.whiteInCheck(bitBoardConfig)
						&& (bitBoardConfig[WR] & bitConstants[56]) != 0) {
					if (!bitBoardOperations.enemyAttacked(whiteMove,
							bitBoardConfig, a)) {
						Long[] move = { (long) WK, bitConstants[60],
								bitConstants[58], (long) 60, (long) 58 };
						moveScoreTuple tuple = new moveScoreTuple();
						tuple.move = move;
						possibleMoves.add(tuple);
					}

				}
				long b = (bitConstants[62] | bitConstants[61]);
				if (wcr == false && ((b & allPieces) == 0)
						&& !bitBoardOperations.whiteInCheck(bitBoardConfig)
						&& (bitBoardConfig[WR] & bitConstants[63]) != 0) {
					if (!bitBoardOperations.enemyAttacked(whiteMove,
							bitBoardConfig, b)) {
						Long[] move = { (long) WK, bitConstants[60],
								bitConstants[62], (long) 60, (long) 62 };
						moveScoreTuple tuple = new moveScoreTuple();
						tuple.move = move;
						possibleMoves.add(tuple);
					}
				}
			}

		} else {
			if (bitBoardConfig[BP] != 0) {
				int s = 0;
				// int e = 64 - Long.numberOfTrailingZeros(bitBoardConfig[BP]);

				long pawns = bitBoardConfig[BP];
				int count = Long.bitCount(pawns);

				for (int i = 0; i < count; i++) {
					s = Long.numberOfLeadingZeros(pawns);
					ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BP - 2) + s]);

					ArrayList<Long[]> l = list.get(0);
					l1: for (int k = 0; k < l.size(); k++) {
						Long[] arr = l.get(k);
						// System.out.println("black forward");
						// bitBoardOperations.printBoardLevel(bitConstants[i]
						// | arr[2]);
						if ((arr[2] & allPieces) == 0) {
							moveScoreTuple tuple = new moveScoreTuple();
							tuple.move = arr;
							possibleMoves.add(tuple);
						} else {
							break l1;
						}
					}

					l = list.get(1);
					for (int k = 0; k < l.size(); k++) {
						Long[] arr = l.get(k);
						tBoard = tBoard |  arr[2];
						if ((arr[2] & bitBoardConfig[W]) != 0) {
							l1: for (int t = 1; t < 7; t++) {
								if ((arr[2] & bitBoardConfig[t]) != 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.takenPiece = t;

									tuple.move = arr;
									possibleMoves.add(tuple);

									break l1;
								}
							}

						}
					}
					pawns = pawns ^ bitConstants[s];
				}
				
			}

			if (bitBoardConfig[BN] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BN]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BN]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BN - 2) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					tBoard = tBoard |  l[2];
					if ((l[2] & bitBoardConfig[B]) == 0) {
						moveScoreTuple tuple = new moveScoreTuple();
						if ((l[2] & bitBoardConfig[W]) != 0) {
							l1: for (int t = 1; t < 7; t++) {
								if ((l[2] & bitBoardConfig[t]) != 0) {
									tuple.takenPiece = t;

									break l1;
								}
							}
						}

						tuple.move = l;
						possibleMoves.add(tuple);
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable.get(
							ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BN - 2) + e]).get(0);
					for (int i = 0; i < list.size(); i++) {
						Long[] l = list.get(i);
						tBoard = tBoard |  l[2];
						if ((l[2] & bitBoardConfig[B]) == 0) {
							moveScoreTuple tuple = new moveScoreTuple();
							if ((l[2] & bitBoardConfig[W]) != 0) {
								l1: for (int t = 1; t < 7; t++) {
									if ((l[2] & bitBoardConfig[t]) != 0) {
										tuple.takenPiece = t;

										break l1;
									}
								}
							}

							tuple.move = l;
							possibleMoves.add(tuple);
						}
					}
				}

			}

			if (bitBoardConfig[BK] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BK]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BK - 2) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					tBoard = tBoard |  l[2];
					// System.out.println(Arrays.toString(l));
					if ((l[2] & bitBoardConfig[B]) == 0) {
						moveScoreTuple tuple = new moveScoreTuple();
						if ((l[2] & bitBoardConfig[W]) != 0) {
							l1: for (int t = 1; t < 7; t++) {
								if ((l[2] & bitBoardConfig[t]) != 0) {
									tuple.takenPiece = t;

									break l1;
								}
							}
						}

						tuple.move = l;
						possibleMoves.add(tuple);
					}
				}
			}

			if (bitBoardConfig[BB] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BB]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BB]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BB - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					l2: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						tBoard = tBoard |  arr[2];
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								moveScoreTuple tuple = new moveScoreTuple();
								tuple.move = arr;
								possibleMoves.add(tuple);
							} else {
								for (int t = 1; t < 7; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										moveScoreTuple tuple = new moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break l2;
									}
								}
							}
						} else {
							break l2;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BB - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						l2: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							tBoard = tBoard |  arr[2];
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								if ((arr[2] & bitBoardConfig[W]) == 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.move = arr;
									possibleMoves.add(tuple);
								} else {
									for (int t = 1; t < 7; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											moveScoreTuple tuple = new moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break l2;
										}
									}
								}
							} else {
								break l2;
							}
						}
					}
				}
			}

			if (bitBoardConfig[BQ] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BQ]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BQ]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BQ - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					l2: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						tBoard = tBoard |  arr[2];
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								moveScoreTuple tuple = new moveScoreTuple();
								tuple.move = arr;
								possibleMoves.add(tuple);
							} else {
								for (int t = 1; t < 7; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										moveScoreTuple tuple = new moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break l2;
									}
								}
							}
						} else {
							break l2;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BQ - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						l2: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							tBoard = tBoard |  arr[2];
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								if ((arr[2] & bitBoardConfig[W]) == 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.move = arr;
									possibleMoves.add(tuple);
								} else {
									for (int t = 1; t < 7; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											moveScoreTuple tuple = new moveScoreTuple();
											tuple.move = arr;
											tuple.takenPiece = t;

											possibleMoves.add(tuple);
											break l2;
										}
									}
								}
							} else {
								break l2;
							}
						}
					}
				}
			}

			if (bitBoardConfig[BR] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BR]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BR]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BR - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					l2: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						tBoard = tBoard |  arr[2];
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								moveScoreTuple tuple = new moveScoreTuple();
								tuple.move = arr;
								possibleMoves.add(tuple);
							} else {
								for (int t = 1; t < 7; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										moveScoreTuple tuple = new moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break l2;
									}
								}
							}
						} else {
							break l2;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BR - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						l2: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							tBoard = tBoard |  arr[2];
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								if ((arr[2] & bitBoardConfig[W]) == 0) {
									moveScoreTuple tuple = new moveScoreTuple();
									tuple.move = arr;
									possibleMoves.add(tuple);
								} else {
									for (int t = 1; t < 7; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											moveScoreTuple tuple = new moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break l2;
										}
									}
								}
							} else {
								break l2;
							}
						}
					}
				}
			}

			if (bitBoardConfig[BK] == bitConstants[4]) {
				long a = (bitConstants[1] | bitConstants[2] | bitConstants[3]);
				if (bcl == false && ((a & allPieces) == 0)
						&& !bitBoardOperations.blackInCheck(bitBoardConfig)
						&& (bitBoardConfig[BR] & bitConstants[0]) != 0) {
					if (!bitBoardOperations.enemyAttacked(whiteMove,
							bitBoardConfig, a)) {
						Long[] move = { (long) BK, bitConstants[4],
								bitConstants[2], (long) 4, (long) 2 };
						moveScoreTuple tuple = new moveScoreTuple();
						tuple.move = move;
						possibleMoves.add(tuple);
					}
				}
				long b = (bitConstants[5] | bitConstants[6]);
				if (bcr == false && ((b & allPieces) == 0)
						&& !bitBoardOperations.blackInCheck(bitBoardConfig)
						&& (bitBoardConfig[BR] & bitConstants[7]) != 0) {
					if (!bitBoardOperations.enemyAttacked(whiteMove,
							bitBoardConfig, b)) {
						Long[] move = { (long) BK, bitConstants[4],
								bitConstants[6], (long) 4, (long) 6 };
						moveScoreTuple tuple = new moveScoreTuple();
						tuple.move = move;
						possibleMoves.add(tuple);
					}
				}
			}
		}

		listAndTactical l = new listAndTactical();
		l.list = possibleMoves;
		l.tacticalBoard = tBoard;

		return l;

	}

	public static ArrayList<Long[]> completeMoveGen(long[] bitBoardConfig,
			boolean whiteMove) {
		ArrayList<Long[]> possibleMoves = new ArrayList<Long[]>(40);
		int c = 0;
		int color = 0;
		if (!whiteMove) {
			color = 7;
		}

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		int start = -1;
		int end = -1;

		if (bitBoardConfig[WP] != 0 && whiteMove) {
			long forwardOne = ((bitBoardConfig[WP] << 8) & ~allPieces);

			start = Long.numberOfLeadingZeros(forwardOne);
			end = 64 - Long.numberOfTrailingZeros(forwardOne);

			for (int i = start; i < end; i++) {
				if ((forwardOne & bitConstants[i]) != 0) {
					Long[] move = { bitConstants[i + 8], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}

			if ((bitBoardConfig[WP] & row2) != 0) {
				long forwardTwo = (((bitBoardConfig[WP] & row2) << 16)
						& ~allPieces & ~(allPieces << 8));

				start = Long.numberOfLeadingZeros(forwardTwo);
				end = 64 - Long.numberOfTrailingZeros(forwardTwo);

				for (int i = start; i < end; i++) {
					if ((forwardTwo & bitConstants[i]) != 0) {
						Long[] move = { bitConstants[i + 16], bitConstants[i] };
						possibleMoves.add(move);
						c++;
					}
				}
			}

			long attackLeft = ((bitBoardConfig[WP] << 9) & bitBoardConfig[B] & ~fileH);

			start = Long.numberOfLeadingZeros(attackLeft);
			end = 64 - Long.numberOfTrailingZeros(attackLeft);

			for (int i = start; i < end; i++) {
				if ((attackLeft & bitConstants[i]) != 0) {
					Long[] move = { bitConstants[i + 9], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}

			long attackRight = ((bitBoardConfig[WP] << 7) & bitBoardConfig[B] & ~fileA);

			start = Long.numberOfLeadingZeros(attackRight);
			end = 64 - Long.numberOfTrailingZeros(attackRight);

			for (int i = start; i < end; i++) {
				if ((attackRight & bitConstants[i]) != 0) {
					Long[] move = { bitConstants[i + 7], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}
		}

		if (bitBoardConfig[BP] != 0 && !whiteMove) {
			long forwardOne = ((bitBoardConfig[BP] >> 8) & ~allPieces);
			start = Long.numberOfLeadingZeros(forwardOne);
			end = 64 - Long.numberOfTrailingZeros(forwardOne);

			for (int i = start; i < end; i++) {
				if ((forwardOne & bitConstants[i]) != 0) {
					Long[] move = { bitConstants[i - 8], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}

			if ((bitBoardConfig[BP] & row7) != 0) {
				long forwardTwo = (((bitBoardConfig[BP] & row7) >> 16)
						& ~allPieces & ~(allPieces >> 8));

				start = Long.numberOfLeadingZeros(forwardTwo);
				end = 64 - Long.numberOfTrailingZeros(forwardTwo);

				for (int i = start; i < end; i++) {
					if ((forwardTwo & bitConstants[i]) != 0) {
						Long[] move = { bitConstants[i - 16], bitConstants[i] };
						possibleMoves.add(move);
						c++;
					}
				}
			}

			long attackLeft = ((bitBoardConfig[BP] >> 9) & bitBoardConfig[W] & ~fileA);

			start = Long.numberOfLeadingZeros(attackLeft);
			end = 64 - Long.numberOfTrailingZeros(attackLeft);

			for (int i = start; i < end; i++) {
				if ((attackLeft & bitConstants[i]) != 0) {
					Long[] move = { bitConstants[i - 9], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}

			long attackRight = ((bitBoardConfig[BP] >> 7) & bitBoardConfig[W] & ~fileH);

			start = Long.numberOfLeadingZeros(attackRight);
			end = 64 - Long.numberOfTrailingZeros(attackRight);

			for (int i = start; i < end; i++) {
				if ((attackRight & bitConstants[i]) != 0) {
					Long[] move = { bitConstants[i - 7], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}
		}

		if (bitBoardConfig[WN + color] != 0) {

			int[][] moves = { { 1, 2 }, { -1, 2 }, { 2, 1 }, { -2, 1 },
					{ 2, -1 }, { -2, -1 }, { 1, -2 }, { -1, -2 } };

			int delta = 0;
			int location = 0;
			long temp = 0;
			long firstPiece = bitBoardConfig[WN + color];

			for (int i = 0; i < 8; i++) {

				delta = (8 * moves[i][0] + moves[i][1]);

				if (delta > 0) {
					temp = ((firstPiece >>> delta) & ~bitBoardConfig[W + color]);
				} else {
					int delta2 = Math.abs(delta);
					temp = ((firstPiece << delta2) & ~bitBoardConfig[W + color]);
				}

				if (temp != 0) {
					location = Long.numberOfLeadingZeros(temp);
					int diff = location - delta;
					if ((diff / 8) + moves[i][0] == location / 8) {
						Long[] move = { (long) (WN + color),
								bitConstants[diff], bitConstants[location],
								(long) diff, (long) location };
						possibleMoves.add(move);
						c++;
					}

					if (Long.bitCount(temp) == 2) {
						location = 63 - Long.numberOfTrailingZeros(temp);
						diff = location - delta;
						if ((diff / 8) + moves[i][0] == location / 8) {
							Long[] move2 = { (long) (WN + color),
									bitConstants[diff], bitConstants[location],
									(long) diff, (long) location };
							possibleMoves.add(move2);
							c++;
						}
					}
				}
			}
		}

		if (bitBoardConfig[WK + color] != 0) {

			int[][] moves = { { 1, 1 }, { 0, 1 }, { -1, 1 }, { 1, 0 },
					{ -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };
			int delta = 0;
			long temp = 0;
			long firstPiece = 0;

			int num = 0;

			firstPiece = bitBoardConfig[WK + color];
			num = Long.numberOfLeadingZeros(firstPiece);

			int push = 0;
			int lim = 8;
			if (num % 8 == 7) {
				push = 3;
			}

			if (num % 8 == 0) {
				lim = 5;
			}

			for (int i = push; i < lim; i++) {

				delta = (8 * moves[i][0] + moves[i][1]);

				if (delta > 0) {
					temp = ((firstPiece >>> delta) & ~bitBoardConfig[W + color]);
				} else {
					int delta2 = Math.abs(delta);
					temp = ((firstPiece << delta2) & ~bitBoardConfig[W + color]);
				}

				if (temp != 0) {
					Long[] move = { (long) (WK + color), firstPiece, temp,
							(long) num, (long) (num + delta) };
					possibleMoves.add(move);
					c++;
				}
			}

			/*
			 * if (color == 7){ if (ChessBoardWithColumnsAndRows.blackLeftCastle
			 * == false && (((bitConstants[1] | bitConstants[2] |
			 * bitConstants[3]) & allPieces) == 0) &&
			 * !bitBoardOperations.blackInCheck(bitBoardConfig)) { Long[] move =
			 * {bitConstants[4], bitConstants[2]}; possibleMoves.add(move); c++;
			 * } if (ChessBoardWithColumnsAndRows.blackRightCastle == false &&
			 * (((bitConstants[5] | bitConstants[6]) & allPieces) == 0) &&
			 * !bitBoardOperations.whiteInCheck(bitBoardConfig)){ Long[] move =
			 * {bitConstants[4], bitConstants[6]}; possibleMoves.add(move); c++;
			 * }
			 * 
			 * } else{ if (ChessBoardWithColumnsAndRows.whiteLeftCastle == false
			 * && (((bitConstants[57] | bitConstants[58] | bitConstants[59]) &
			 * allPieces) == 0)){ Long[] move = {bitConstants[60],
			 * bitConstants[58]}; possibleMoves.add(move); c++; } if
			 * (ChessBoardWithColumnsAndRows.whiteRightCastle == false &&
			 * (((bitConstants[62] | bitConstants[61]) & allPieces) == 0)){
			 * Long[] move = {bitConstants[60], bitConstants[62]};
			 * possibleMoves.add(move); c++; } }
			 */

		}

		if (bitBoardConfig[WB + color] != 0) {
			int location = 0;
			long temp = 0;
			long firstPiece = 0;
			int num = 0;
			int count = Long.bitCount(bitBoardConfig[WB + color]);

			for (int j = 0; j < count; j++) {
				if (j == 0) {
					num = Long.numberOfLeadingZeros(bitBoardConfig[WB + color]);
					firstPiece = bitConstants[num];
				}
				if (j == 1) {
					num = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WB
							+ color]);
					firstPiece = bitConstants[num];
				}
				// down right
				int limit = Math.min(8 - (num / 8), 8 - (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num + 9 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					Long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}

				// down left
				limit = Math.min(8 - (num / 8), 1 + (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num + 7 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					location = Long.numberOfLeadingZeros(temp);
					Long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// up left
				limit = Math.min(1 + (num / 8), 1 + (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num - 9 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					Long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// up right
				limit = Math.min(1 + (num / 8), 8 - (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num - 7 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					Long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
			}
		}

		if (bitBoardConfig[WQ + color] != 0) {
			int location = 0;
			long temp = 0;
			int num = Long.numberOfLeadingZeros(bitBoardConfig[WQ + color]);
			long firstPiece = bitConstants[num];

			// down right
			int limit = Math.min(8 - (num / 8), 8 - (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num + 9 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				Long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}

			// down left
			limit = Math.min(8 - (num / 8), 1 + (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num + 7 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				location = Long.numberOfLeadingZeros(temp);
				Long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// up left
			limit = Math.min(1 + (num / 8), 1 + (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num - 9 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				Long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// up right
			limit = Math.min(1 + (num / 8), 8 - (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num - 7 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				Long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}

			// down
			limit = (8 - (num / 8));
			for (int i = 1; i < limit; i++) {

				location = num + 8 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				Long[] move = { firstPiece, temp };

				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// right
			limit = (8 - (num % 8));
			for (int i = 1; i < limit; i++) {
				location = num + i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				Long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// up
			limit = (1 + (num / 8));
			for (int i = 1; i < limit; i++) {

				location = num - 8 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				Long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// left
			limit = (1 + (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num - i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				Long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}

		}

		if (bitBoardConfig[WR + color] != 0) {
			int location = 0;
			long temp = 0;
			long firstPiece = 0;
			int num = 0;
			int count = Long.bitCount(bitBoardConfig[WR + color]);

			for (int j = 0; j < count; j++) {

				if (j == 0) {
					num = Long.numberOfLeadingZeros(bitBoardConfig[WR + color]);
					firstPiece = bitConstants[num];
				}
				if (j == 1) {
					num = 64 - Long.numberOfTrailingZeros(bitBoardConfig[WR
							+ color]) - 1;
					firstPiece = bitConstants[num];
				}

				// down
				int limit = (8 - (num / 8));
				for (int i = 1; i < limit; i++) {

					location = num + 8 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					Long[] move = { firstPiece, temp };

					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// right
				limit = (8 - (num % 8));
				for (int i = 1; i < limit; i++) {
					location = num + i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					Long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// up
				limit = (1 + (num / 8));
				for (int i = 1; i < limit; i++) {

					location = num - 8 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					Long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// left
				limit = (1 + (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num - i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					Long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
			}
		}

		return possibleMoves;
	}

	public static ArrayList<moveScoreTuple> tacticalMoveGen3(
			long[] bitBoardConfig, boolean whiteMove) {

		ArrayList<moveScoreTuple> possibleMoves = new ArrayList<moveScoreTuple>();

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		if (whiteMove) {
			if (bitBoardConfig[WP] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WP]);
				int e = 64 - Long.numberOfTrailingZeros(bitBoardConfig[WP]);
				for (int i = s; i < e; i++) {
					if ((bitConstants[i] & bitBoardConfig[WP]) != 0) {
						ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
								.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
										* (WP - 1) + i]);

						ArrayList<Long[]> l = list.get(1);
						for (int k = 0; k < l.size(); k++) {
							Long[] arr = l.get(k);
							if ((arr[2] & bitBoardConfig[B]) != 0) {
								l1: for (int t = 8; t < 14; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);

										break l1;
									}
								}

							}
						}
					}
				}
			}

			if (bitBoardConfig[WN] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WN]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WN]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WN - 1) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					if ((l[2] & bitBoardConfig[B]) != 0) {
						l1: for (int t = 8; t < 14; t++) {
							if ((l[2] & bitBoardConfig[t]) != 0) {
								AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
								tuple.takenPiece = t;

								tuple.move = l;
								possibleMoves.add(tuple);
								break l1;
							}
						}

					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable.get(
							ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WN - 1) + e]).get(0);
					for (int i = 0; i < list.size(); i++) {
						Long[] l = list.get(i);
						if ((l[2] & bitBoardConfig[B]) != 0) {
							l1: for (int t = 8; t < 14; t++) {
								if ((l[2] & bitBoardConfig[t]) != 0) {
									AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
									tuple.takenPiece = t;

									tuple.move = l;
									possibleMoves.add(tuple);
									break l1;
								}
							}

						}
					}
				}

			}

			if (bitBoardConfig[WK] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WK]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WK - 1) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					if ((l[2] & bitBoardConfig[B]) != 0) {
						l1: for (int t = 8; t < 14; t++) {
							if ((l[2] & bitBoardConfig[t]) != 0) {
								AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
								tuple.takenPiece = t;

								tuple.move = l;
								possibleMoves.add(tuple);
								break l1;
							}
						}

					}
				}
			}

			if (bitBoardConfig[WB] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WB]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WB]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WB - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					loop: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							if ((arr[2] & bitBoardConfig[B]) != 0) {
								l1: for (int t = 8; t < 14; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break loop;
									}
								}
							}
						} else {
							break loop;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WB - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						loop: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								if ((arr[2] & bitBoardConfig[B]) != 0) {
									l1: for (int t = 8; t < 14; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break loop;
										}
									}
								}
							} else {
								break loop;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WQ] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WQ]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WQ]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WQ - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					loop: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							if ((arr[2] & bitBoardConfig[B]) != 0) {
								for (int t = 8; t < 14; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break loop;
									}
								}
							}
						} else {
							break loop;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WQ - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						loop: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								if ((arr[2] & bitBoardConfig[B]) != 0) {
									for (int t = 8; t < 14; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break loop;
										}
									}
								}
							} else {
								break loop;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WR] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WR]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WR]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WR - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					loop: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							if ((arr[2] & bitBoardConfig[B]) != 0) {
								for (int t = 8; t < 14; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break loop;
									}
								}
							}
						} else {
							break loop;
						}

					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WR - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						loop: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								if ((arr[2] & bitBoardConfig[B]) != 0) {
									for (int t = 8; t < 14; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break loop;
										}
									}
								}
							} else {
								break loop;
							}
						}
					}
				}
			}
		} else {
			if (bitBoardConfig[BP] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BP]);
				int e = 64 - Long.numberOfTrailingZeros(bitBoardConfig[BP]);
				for (int i = s; i < e; i++) {
					if ((bitConstants[i] & bitBoardConfig[BP]) != 0) {
						ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
								.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
										* (BP - 2) + i]);

						ArrayList<Long[]> l = list.get(1);
						for (int k = 0; k < l.size(); k++) {
							Long[] arr = l.get(k);
							if ((arr[2] & bitBoardConfig[W]) != 0) {
								l1: for (int t = 1; t < 7; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;
										tuple.move = arr;
										possibleMoves.add(tuple);

										break l1;
									}
								}

							}
						}
					}
				}
			}

			if (bitBoardConfig[BN] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BN]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BN]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BN - 2) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					if ((l[2] & bitBoardConfig[W]) != 0) {
						l1: for (int t = 1; t < 7; t++) {
							if ((l[2] & bitBoardConfig[t]) != 0) {
								AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
								tuple.takenPiece = t;

								tuple.move = l;
								possibleMoves.add(tuple);
								break l1;
							}
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable.get(
							ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BN - 2) + e]).get(0);
					for (int i = 0; i < list.size(); i++) {
						Long[] l = list.get(i);
						if ((l[2] & bitBoardConfig[W]) != 0) {
							l1: for (int t = 1; t < 7; t++) {
								if ((l[2] & bitBoardConfig[t]) != 0) {
									AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
									tuple.takenPiece = t;

									tuple.move = l;
									possibleMoves.add(tuple);
									break l1;
								}
							}
						}
					}
				}

			}

			if (bitBoardConfig[BK] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BK]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BK - 2) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					// System.out.println(Arrays.toString(l));
					if ((l[2] & bitBoardConfig[W]) != 0) {
						l1: for (int t = 1; t < 7; t++) {
							if ((l[2] & bitBoardConfig[t]) != 0) {
								AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
								tuple.takenPiece = t;

								tuple.move = l;
								possibleMoves.add(tuple);
								break l1;
							}
						}

					}
				}
			}

			if (bitBoardConfig[BB] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BB]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BB]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BB - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					loop: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							if ((arr[2] & bitBoardConfig[W]) != 0) {
								for (int t = 1; t < 7; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break loop;
									}
								}

							}
						} else {
							break loop;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BB - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						loop: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								if ((arr[2] & bitBoardConfig[W]) != 0) {
									for (int t = 1; t < 7; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break loop;
										}
									}

								}
							} else {
								break loop;
							}

						}
					}
				}
			}

			if (bitBoardConfig[BQ] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BQ]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BQ]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BQ - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					loop: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							if ((arr[2] & bitBoardConfig[W]) != 0) {
								for (int t = 1; t < 7; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break loop;
									}
								}

							}
						} else {
							break loop;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BQ - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						loop: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								if ((arr[2] & bitBoardConfig[W]) != 0) {
									for (int t = 1; t < 7; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break loop;
										}
									}

								}
							} else {
								break loop;
							}
						}
					}
				}
			}

			if (bitBoardConfig[BR] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BR]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BR]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BR - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					loop: for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							if ((arr[2] & bitBoardConfig[W]) != 0) {
								for (int t = 1; t < 7; t++) {
									if ((arr[2] & bitBoardConfig[t]) != 0) {
										AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
										tuple.takenPiece = t;

										tuple.move = arr;
										possibleMoves.add(tuple);
										break loop;
									}
								}

							}
						} else {
							break loop;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BR - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						loop: for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								if ((arr[2] & bitBoardConfig[W]) != 0) {
									for (int t = 1; t < 7; t++) {
										if ((arr[2] & bitBoardConfig[t]) != 0) {
											AIv2Tree.moveScoreTuple tuple = new AIv2Tree.moveScoreTuple();
											tuple.takenPiece = t;

											tuple.move = arr;
											possibleMoves.add(tuple);
											break loop;
										}
									}

								}
							} else {
								break loop;
							}
						}
					}
				}
			}
		}

		return possibleMoves;

	}

	public static ArrayList<Long[]> tacticalMoveGen2(long[] bitBoardConfig,
			boolean whiteMove) {

		ArrayList<Long[]> possibleMoves = new ArrayList<Long[]>(40);

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		if (whiteMove) {
			if (bitBoardConfig[WP] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WP]);
				int e = 64 - Long.numberOfTrailingZeros(bitBoardConfig[WP]);
				for (int i = s; i < e; i++) {
					if ((bitConstants[i] & bitBoardConfig[WP]) != 0) {
						ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
								.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
										* (WP - 1) + i]);

						ArrayList<Long[]> l = list.get(1);
						for (int k = 0; k < l.size(); k++) {
							if ((l.get(k)[2] & bitBoardConfig[B]) != 0) {
								possibleMoves.add(l.get(k));
							}
						}
					}
				}
			}

			if (bitBoardConfig[WN] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WN]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WN]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WN - 1) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					if ((l[2] & bitBoardConfig[W]) == 0) {
						possibleMoves.add(l);
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable.get(
							ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WN - 1) + e]).get(0);
					for (int i = 0; i < list.size(); i++) {
						Long[] l = list.get(i);
						if ((l[2] & bitBoardConfig[W]) == 0) {
							possibleMoves.add(l);
						}
					}
				}

			}

			if (bitBoardConfig[WK] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WK]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WK - 1) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					// System.out.println(Arrays.toString(l));
					if ((l[2] & bitBoardConfig[W]) == 0) {
						possibleMoves.add(l);
					}
				}
			}

			if (bitBoardConfig[WB] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WB]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WB]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WB - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							possibleMoves.add(arr);
						} else {
							break;
						}

						if ((arr[2] & bitBoardConfig[B]) != 0) {
							break;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WB - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								possibleMoves.add(arr);
							} else {
								break;
							}

							if ((arr[2] & bitBoardConfig[B]) != 0) {
								break;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WQ] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WQ]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WQ]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WQ - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							possibleMoves.add(arr);
						} else {
							break;
						}

						if ((arr[2] & bitBoardConfig[B]) != 0) {
							break;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WQ - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								possibleMoves.add(arr);
							} else {
								break;
							}

							if ((arr[2] & bitBoardConfig[B]) != 0) {
								break;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WR] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WR]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WR]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WR - 1) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[W]) == 0) {
							possibleMoves.add(arr);
						} else {
							break;
						}

						if ((arr[2] & bitBoardConfig[B]) != 0) {
							break;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (WR - 1) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[W]) == 0) {
								possibleMoves.add(arr);
							} else {
								break;
							}

							if ((arr[2] & bitBoardConfig[B]) != 0) {
								break;
							}
						}
					}
				}
			}
		} else {
			if (bitBoardConfig[BP] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BP]);
				int e = 64 - Long.numberOfTrailingZeros(bitBoardConfig[BP]);
				for (int i = s; i < e; i++) {
					if ((bitConstants[i] & bitBoardConfig[BP]) != 0) {
						ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
								.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
										* (BP - 2) + i]);

						ArrayList<Long[]> l = list.get(1);
						for (int k = 0; k < l.size(); k++) {
							if ((l.get(k)[2] & bitBoardConfig[W]) != 0) {
								possibleMoves.add(l.get(k));
							}
						}
					}
				}
			}

			if (bitBoardConfig[BN] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BN]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BN]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BN - 2) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					if ((l[2] & bitBoardConfig[B]) == 0) {
						possibleMoves.add(l);
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable.get(
							ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BN - 2) + e]).get(0);
					for (int i = 0; i < list.size(); i++) {
						Long[] l = list.get(i);
						if ((l[2] & bitBoardConfig[B]) == 0) {
							possibleMoves.add(l);
						}
					}
				}

			}

			if (bitBoardConfig[BK] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BK]);

				ArrayList<Long[]> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BK - 2) + s]).get(0);
				for (int i = 0; i < list.size(); i++) {
					Long[] l = list.get(i);
					// System.out.println(Arrays.toString(l));
					if ((l[2] & bitBoardConfig[B]) == 0) {
						possibleMoves.add(l);
					}
				}
			}

			if (bitBoardConfig[BB] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BB]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BB]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BB - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							possibleMoves.add(arr);
						} else {
							break;
						}

						if ((arr[2] & bitBoardConfig[W]) != 0) {
							break;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BB - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								possibleMoves.add(arr);
							} else {
								break;
							}

							if ((arr[2] & bitBoardConfig[W]) != 0) {
								break;
							}
						}
					}
				}
			}

			if (bitBoardConfig[BQ] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BQ]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BQ]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BQ - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							possibleMoves.add(arr);
						} else {
							break;
						}

						if ((arr[2] & bitBoardConfig[W]) != 0) {
							break;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BQ - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								possibleMoves.add(arr);
							} else {
								break;
							}

							if ((arr[2] & bitBoardConfig[W]) != 0) {
								break;
							}
						}
					}
				}
			}

			if (bitBoardConfig[BR] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BR]);
				int e = 63 - Long.numberOfTrailingZeros(bitBoardConfig[BR]);

				ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
						.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BR - 2) + s]);
				for (int i = 0; i < list.size(); i++) {
					ArrayList<Long[]> l = list.get(i);
					for (int j = 0; j < l.size(); j++) {
						Long[] arr = l.get(j);
						if ((arr[2] & bitBoardConfig[B]) == 0) {
							possibleMoves.add(arr);
						} else {
							break;
						}

						if ((arr[2] & bitBoardConfig[W]) != 0) {
							break;
						}
					}
				}

				if (s != e) {
					list = ChessBoardWithColumnsAndRows.moveTable
							.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
									* (BR - 2) + e]);
					for (int i = 0; i < list.size(); i++) {
						ArrayList<Long[]> l = list.get(i);
						for (int j = 0; j < l.size(); j++) {
							Long[] arr = l.get(j);
							if ((arr[2] & bitBoardConfig[B]) == 0) {
								possibleMoves.add(arr);
							} else {
								break;
							}

							if ((arr[2] & bitBoardConfig[W]) != 0) {
								break;
							}
						}
					}
				}
			}
		}

		return possibleMoves;

	}

	public static ArrayList<long[]> tacticalMoveGen(long[] bitBoardConfig,
			boolean whiteMove) {
		ArrayList<long[]> possibleMoves = new ArrayList<long[]>(40);
		int c = 0;
		int color = 0;
		if (!whiteMove) {
			color = 7;
		}

		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		int start = -1;
		int end = -1;

		if (bitBoardConfig[WP] != 0 && whiteMove) {

			long attackLeft = ((bitBoardConfig[WP] << 9) & bitBoardConfig[B] & ~fileH);

			start = Long.numberOfLeadingZeros(attackLeft);
			end = 64 - Long.numberOfTrailingZeros(attackLeft);

			for (int i = start; i < end; i++) {
				if ((attackLeft & bitConstants[i]) != 0) {
					long[] move = { bitConstants[i + 9], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}

			long attackRight = ((bitBoardConfig[WP] << 7) & bitBoardConfig[B] & ~fileA);

			start = Long.numberOfLeadingZeros(attackRight);
			end = 64 - Long.numberOfTrailingZeros(attackRight);

			for (int i = start; i < end; i++) {
				if ((attackRight & bitConstants[i]) != 0) {
					long[] move = { bitConstants[i + 7], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}
		}

		if (bitBoardConfig[BP] != 0 && !whiteMove) {

			long attackLeft = ((bitBoardConfig[BP] >> 9) & bitBoardConfig[W] & ~fileA);

			start = Long.numberOfLeadingZeros(attackLeft);
			end = 64 - Long.numberOfTrailingZeros(attackLeft);

			for (int i = start; i < end; i++) {
				if ((attackLeft & bitConstants[i]) != 0) {
					long[] move = { bitConstants[i - 9], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}

			long attackRight = ((bitBoardConfig[BP] >> 7) & bitBoardConfig[W] & ~fileH);

			start = Long.numberOfLeadingZeros(attackRight);
			end = 64 - Long.numberOfTrailingZeros(attackRight);

			for (int i = start; i < end; i++) {
				if ((attackRight & bitConstants[i]) != 0) {
					long[] move = { bitConstants[i - 7], bitConstants[i] };
					possibleMoves.add(move);
					c++;
				}
			}
		}

		if (bitBoardConfig[WN + color] != 0) {

			int[][] moves = { { 1, 2 }, { -1, 2 }, { 2, 1 }, { -2, 1 },
					{ 2, -1 }, { -2, -1 }, { 1, -2 }, { -1, -2 } };

			int delta = 0;
			int location = 0;
			long temp = 0;
			long firstPiece = bitBoardConfig[WN + color];

			for (int i = 0; i < 8; i++) {

				delta = (8 * moves[i][0] + moves[i][1]);

				if (delta > 0) {
					temp = ((firstPiece >>> delta) & ~bitBoardConfig[W + color]);
				} else {
					int delta2 = Math.abs(delta);
					temp = ((firstPiece << delta2) & ~bitBoardConfig[W + color]);
				}

				if (temp != 0) {
					location = Long.numberOfLeadingZeros(temp);
					int diff = location - delta;
					if ((diff / 8) + moves[i][0] == location / 8) {
						long[] move = { bitConstants[diff],
								bitConstants[location] };
						possibleMoves.add(move);
						c++;
					}

					if (Long.bitCount(temp) == 2) {
						location = 63 - Long.numberOfTrailingZeros(temp);
						diff = location - delta;
						if ((diff / 8) + moves[i][0] == location / 8) {
							long[] move2 = { bitConstants[diff],
									bitConstants[location] };
							possibleMoves.add(move2);
							c++;
						}
					}
				}
			}
		}

		if (bitBoardConfig[WK + color] != 0) {

			int[][] moves = { { 1, 1 }, { 0, 1 }, { -1, 1 }, { 1, 0 },
					{ -1, 0 }, { -1, -1 }, { 0, -1 }, { 1, -1 } };
			int delta = 0;
			long temp = 0;
			long firstPiece = 0;

			int num = 0;

			firstPiece = bitBoardConfig[WK + color];
			num = Long.numberOfLeadingZeros(firstPiece);

			int push = 0;
			int lim = 8;
			if (num % 8 == 7) {
				push = 3;
			}

			if (num % 8 == 0) {
				lim = 5;
			}

			for (int i = push; i < lim; i++) {

				delta = (8 * moves[i][0] + moves[i][1]);

				if (delta > 0) {
					temp = ((firstPiece >>> delta) & ~bitBoardConfig[W + color]);
				} else {
					delta = Math.abs(delta);
					temp = ((firstPiece << delta) & ~bitBoardConfig[W + color]);
				}

				if (temp != 0) {
					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
				}
			}

			if (color == 7) {
				if (ChessBoardWithColumnsAndRows.blackLeftCastle == false
						&& (((bitConstants[1] | bitConstants[2] | bitConstants[3]) & allPieces) == 0)) {
					long[] move = { bitConstants[4], bitConstants[2] };
					possibleMoves.add(move);
					c++;
				}
				if (ChessBoardWithColumnsAndRows.blackRightCastle == false
						&& (((bitConstants[5] | bitConstants[6]) & allPieces) == 0)) {
					long[] move = { bitConstants[4], bitConstants[6] };
					possibleMoves.add(move);
					c++;
				}

			} else {
				if (ChessBoardWithColumnsAndRows.whiteLeftCastle == false
						&& (((bitConstants[57] | bitConstants[58] | bitConstants[59]) & allPieces) == 0)) {
					long[] move = { bitConstants[60], bitConstants[58] };
					possibleMoves.add(move);
					c++;
				}
				if (ChessBoardWithColumnsAndRows.whiteRightCastle == false
						&& (((bitConstants[62] | bitConstants[61]) & allPieces) == 0)) {
					long[] move = { bitConstants[60], bitConstants[62] };
					possibleMoves.add(move);
					c++;
				}
			}

		}

		if (bitBoardConfig[WB + color] != 0) {
			int location = 0;
			long temp = 0;
			long firstPiece = 0;
			int num = 0;
			int count = Long.bitCount(bitBoardConfig[WB + color]);

			for (int j = 0; j < count; j++) {
				if (j == 0) {
					num = Long.numberOfLeadingZeros(bitBoardConfig[WB + color]);
					firstPiece = bitConstants[num];
				}
				if (j == 1) {
					num = 63 - Long.numberOfTrailingZeros(bitBoardConfig[WB
							+ color]);
					firstPiece = bitConstants[num];
				}
				// down right
				int limit = Math.min(8 - (num / 8), 8 - (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num + 9 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}

				// down left
				limit = Math.min(8 - (num / 8), 1 + (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num + 7 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					location = Long.numberOfLeadingZeros(temp);
					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// up left
				limit = Math.min(1 + (num / 8), 1 + (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num - 9 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// up right
				limit = Math.min(1 + (num / 8), 8 - (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num - 7 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
			}
		}

		if (bitBoardConfig[WQ + color] != 0) {
			int location = 0;
			long temp = 0;
			int num = Long.numberOfLeadingZeros(bitBoardConfig[WQ + color]);
			long firstPiece = bitConstants[num];

			// down right
			int limit = Math.min(8 - (num / 8), 8 - (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num + 9 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}

			// down left
			limit = Math.min(8 - (num / 8), 1 + (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num + 7 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				location = Long.numberOfLeadingZeros(temp);
				long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// up left
			limit = Math.min(1 + (num / 8), 1 + (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num - 9 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// up right
			limit = Math.min(1 + (num / 8), 8 - (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num - 7 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}

			// down
			limit = (8 - (num / 8));
			for (int i = 1; i < limit; i++) {

				location = num + 8 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				long[] move = { firstPiece, temp };

				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// right
			limit = (8 - (num % 8));
			for (int i = 1; i < limit; i++) {
				location = num + i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// up
			limit = (1 + (num / 8));
			for (int i = 1; i < limit; i++) {

				location = num - 8 * i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}
			// left
			limit = (1 + (num % 8));
			for (int i = 1; i < limit; i++) {

				location = num - i;

				temp = (bitConstants[location]);

				if ((temp & bitBoardConfig[W + color]) != 0) {
					break;
				}

				long[] move = { firstPiece, temp };
				possibleMoves.add(move);
				c++;
				if ((temp & bitBoardConfig[B - color]) != 0) {
					break;
				}
			}

		}

		if (bitBoardConfig[WR + color] != 0) {
			int location = 0;
			long temp = 0;
			long firstPiece = 0;
			int num = 0;
			int count = Long.bitCount(bitBoardConfig[WR + color]);

			for (int j = 0; j < count; j++) {

				if (j == 0) {
					num = Long.numberOfLeadingZeros(bitBoardConfig[WR + color]);
					firstPiece = bitConstants[num];
				}
				if (j == 1) {
					num = 64 - Long.numberOfTrailingZeros(bitBoardConfig[WR
							+ color]) - 1;
					firstPiece = bitConstants[num];
				}

				// down
				int limit = (8 - (num / 8));
				for (int i = 1; i < limit; i++) {

					location = num + 8 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					long[] move = { firstPiece, temp };

					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// right
				limit = (8 - (num % 8));
				for (int i = 1; i < limit; i++) {
					location = num + i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// up
				limit = (1 + (num / 8));
				for (int i = 1; i < limit; i++) {

					location = num - 8 * i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
				// left
				limit = (1 + (num % 8));
				for (int i = 1; i < limit; i++) {

					location = num - i;

					temp = (bitConstants[location]);

					if ((temp & bitBoardConfig[W + color]) != 0) {
						break;
					}

					long[] move = { firstPiece, temp };
					possibleMoves.add(move);
					c++;
					if ((temp & bitBoardConfig[B - color]) != 0) {
						break;
					}
				}
			}
		}

		return possibleMoves;
	}

	public static Node promotionCheck(long start, long end, Node n,
			boolean whiteMove, int leading) {
		if (whiteMove) {
			if (leading < 8 && (end & n.board[WP]) != 0) {
				n.board[WP] = n.board[WP] ^ end;
				n.board[WQ] = n.board[WQ] ^ end;
				n.score = n.score + 9000;
			}
		}

		else {
			int trailing = 63 - leading;
			if (trailing < 8 && (end & n.board[BP]) != 0) {
				n.board[BP] = n.board[BP] ^ end;
				n.board[BQ] = n.board[BQ] ^ end;
				n.score = n.score - 9000;
			}
		}

		return n;

	}

	public static Node bitPromotionCheck(long end, Node n, long[] board,
			boolean whiteMove, int leading) {
		if (whiteMove) {
			if (leading < 8 && (end & board[bitBoardOperations.WP]) != 0) {
				// int leading = Long.numberOfLeadingZeros(end);
				// int trailing = Long.numberOfLeadingZeros(start);
				n.key = (n.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64
						* (WQ - 1) + leading])
						^ ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (WP - 1) + leading];

			}
		}

		else {
			int trailing = 63 - leading;
			if (trailing < 8 && (end & board[bitBoardOperations.BP]) != 0) {
				// int leading = Long.numberOfTrailingZeros(end);
				// int trailing = Long.numberOfLeadingZeros(start);
				n.key = (n.key ^ ChessBoardWithColumnsAndRows.pieceRandoms[64
						* (BQ - 2) + leading])
						^ ChessBoardWithColumnsAndRows.pieceRandoms[64
								* (BP - 2) + leading];
			}
		}

		return n;

	}

	public static int differenceCalculator(int[][] board) {
		int difference = 0;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (board[i][j] > 0) {
					difference = difference + pieceValues[board[i][j]];
				}
				if (board[i][j] < 0) {
					difference = difference - pieceValues[(-1) * board[i][j]];
				}
			}
		}
		return difference;
	}

	public static void printBoard(long[] bitBoardConfig) {
		for (int i = 0; i < bitBoardConfig.length; i++) {
			String s = Long.toBinaryString(bitBoardConfig[i]);

			int n = Long.numberOfLeadingZeros(bitBoardConfig[i]);
			char[] zeros = new char[n];

			Arrays.fill(zeros, '0');

			s = new String(zeros) + s;

			System.out.println(pieceNames[i]);
			for (int j = 0; j < s.length(); j++) {
				System.out.print(s.charAt(j));
				if ((j + 1) % 8 == 0) {
					System.out.println();
				}
			}
			System.out.println();
		}
	}

	public static void printBoardLevel(long bitBoard) {
		String s = Long.toBinaryString(bitBoard);
		int n = Long.numberOfLeadingZeros(bitBoard);
		char[] zeros = new char[n];

		Arrays.fill(zeros, '0');

		s = new String(zeros) + s;

		for (int j = 0; j < s.length(); j++) {
			System.out.print(s.charAt(j));
			if ((j + 1) % 8 == 0) {
				System.out.println();
			}
		}
		System.out.println();

	}

	// public static final int W = 0, WP = 1, WN = 2, WB = 3, WR = 4, WQ = 5, WK
	// = 6,
	// B = 7, BP = 8, BN = 9, BB = 10, BR = 11, BQ = 12, BK = 13;

	public static long hasher(long[] pieceRandoms, long[] bitBoardConfig,
			boolean whiteMove) {
		long key = 0;
		int shift = 0;
		for (int i = 0; i < 12; i++) {
			shift = i / 6 + 1;
			for (int j = 0; j < 64; j++) {
				if ((bitConstants[j] & bitBoardConfig[shift + i]) != 0) {
					key = key ^ pieceRandoms[64 * i + j];
				}
			}
		}
		
		key = key & ~bitConstants[0];

		if (whiteMove) {
			key = key | bitConstants[0];
		}

		return key;

	}

	public static ArrayList<Long[]> additionalMovesGen(long[] bitBoardConfig,
			boolean whiteMove) {

		ArrayList<Long[]> possibleMoves = new ArrayList<Long[]>(40);
		long allPieces = (bitBoardConfig[B] | bitBoardConfig[W]);

		if (whiteMove) {
			if (bitBoardConfig[WP] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[WP]);
				int e = 64 - Long.numberOfTrailingZeros(bitBoardConfig[WP]);
				for (int i = s; i < e; i++) {
					if ((bitConstants[i] & bitBoardConfig[WP]) != 0) {
						ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
								.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
										* (WP - 1) + i]);

						ArrayList<Long[]> l = list.get(0);
						for (int k = 0; k < l.size(); k++) {
							if ((l.get(k)[2] & allPieces) == 0) {
								possibleMoves.add(l.get(k));
							} else {
								break;
							}
						}
					}
				}
			}

			if (bitBoardConfig[WK] == bitConstants[60]) {
				if (ChessBoardWithColumnsAndRows.whiteLeftCastle == false
						&& (((bitConstants[57] | bitConstants[58] | bitConstants[59]) & allPieces) == 0)
						&& !bitBoardOperations.whiteInCheck(bitBoardConfig)
						&& (bitBoardConfig[WR] & bitConstants[56]) != 0) {
					Long[] move = { (long) WK, bitConstants[60],
							bitConstants[58] };
					possibleMoves.add(move);
				}
				if (ChessBoardWithColumnsAndRows.whiteRightCastle == false
						&& (((bitConstants[62] | bitConstants[61]) & allPieces) == 0)
						&& !bitBoardOperations.whiteInCheck(bitBoardConfig)
						&& (bitBoardConfig[WR] & bitConstants[63]) != 0) {
					Long[] move = { (long) WK, bitConstants[60],
							bitConstants[62] };
					possibleMoves.add(move);
				}
			}

		} else {
			if (bitBoardConfig[BP] != 0) {
				int s = Long.numberOfLeadingZeros(bitBoardConfig[BP]);
				int e = 64 - Long.numberOfTrailingZeros(bitBoardConfig[BP]);
				for (int i = s; i < e; i++) {
					if ((bitConstants[i] & bitBoardConfig[BP]) != 0) {
						ArrayList<ArrayList<Long[]>> list = ChessBoardWithColumnsAndRows.moveTable
								.get(ChessBoardWithColumnsAndRows.pieceRandoms[64
										* (BP - 2) + i]);

						ArrayList<Long[]> l = list.get(0);
						for (int k = 0; k < l.size(); k++) {
							if ((l.get(k)[2] & allPieces) == 0) {
								possibleMoves.add(l.get(k));
							} else {
								break;
							}
						}
					}
				}
			}

			if (bitBoardConfig[BK] == bitConstants[4]) {
				if (ChessBoardWithColumnsAndRows.blackLeftCastle == false
						&& (((bitConstants[1] | bitConstants[2] | bitConstants[3]) & allPieces) == 0)
						&& !bitBoardOperations.blackInCheck(bitBoardConfig)
						&& (bitBoardConfig[BR] & bitConstants[0]) != 0) {
					Long[] move = { (long) BK, bitConstants[4], bitConstants[2] };
					possibleMoves.add(move);
				}
				if (ChessBoardWithColumnsAndRows.blackRightCastle == false
						&& (((bitConstants[5] | bitConstants[6]) & allPieces) == 0)
						&& !bitBoardOperations.blackInCheck(bitBoardConfig)
						&& (bitBoardConfig[BR] & bitConstants[7]) != 0) {
					Long[] move = { (long) BK, bitConstants[4], bitConstants[6] };
					possibleMoves.add(move);
				}
			}

		}

		return possibleMoves;
	}

	public static void moveTableMaker() {

		ArrayList<Long[]> wk = new ArrayList<Long[]>();
		ArrayList<Long[]> bk = new ArrayList<Long[]>();
		ArrayList<Long[]> wn = new ArrayList<Long[]>();
		ArrayList<Long[]> bn = new ArrayList<Long[]>();

		long[] whiteKingBoard = new long[14];
		long[] blackKingBoard = new long[14];
		long[] whiteKnightBoard = new long[14];
		long[] blackKnightBoard = new long[14];

		for (int i = 0; i < 64; i++) {

			ArrayList<ArrayList<Long[]>> blackKing = new ArrayList<ArrayList<Long[]>>(
					1);
			ArrayList<ArrayList<Long[]>> whiteKing = new ArrayList<ArrayList<Long[]>>(
					1);
			ArrayList<ArrayList<Long[]>> blackKnight = new ArrayList<ArrayList<Long[]>>(
					1);
			ArrayList<ArrayList<Long[]>> whiteKnight = new ArrayList<ArrayList<Long[]>>(
					1);

			whiteKingBoard[WK] = bitConstants[i];
			blackKingBoard[BK] = bitConstants[i];
			whiteKnightBoard[WN] = bitConstants[i];
			blackKnightBoard[BN] = bitConstants[i];

			whiteKingBoard[W] = bitConstants[i];
			blackKingBoard[B] = bitConstants[i];
			whiteKnightBoard[W] = bitConstants[i];
			blackKnightBoard[B] = bitConstants[i];

			wk = completeMoveGen(whiteKingBoard, true);
			bk = completeMoveGen(blackKingBoard, false);
			wn = completeMoveGen(whiteKnightBoard, true);
			bn = completeMoveGen(blackKnightBoard, false);

			// System.out.println("wn len: " + wn.size());

			whiteKing.add(wk);
			blackKing.add(bk);
			whiteKnight.add(wn);
			blackKnight.add(bn);

			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (WK - 1) + i], whiteKing);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (BK - 2) + i], blackKing);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (WN - 1) + i], whiteKnight);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (BN - 2) + i], blackKnight);

		}

		ArrayList<Long[]> wpMove = new ArrayList<Long[]>();
		ArrayList<Long[]> bpMove = new ArrayList<Long[]>();
		ArrayList<Long[]> wpAttack = new ArrayList<Long[]>();
		ArrayList<Long[]> bpAttack = new ArrayList<Long[]>();

		long[] whitePawnBoard = new long[14];
		long[] blackPawnBoard = new long[14];

		for (int i = 0; i < 64; i++) {

			ArrayList<ArrayList<Long[]>> blackPawn = new ArrayList<ArrayList<Long[]>>(
					2);
			ArrayList<ArrayList<Long[]>> whitePawn = new ArrayList<ArrayList<Long[]>>(
					2);

			whitePawnBoard[WP] = bitConstants[i];
			blackPawnBoard[BP] = bitConstants[i];

			whitePawnBoard[W] = bitConstants[i];
			blackPawnBoard[B] = bitConstants[i];

			wpMove = pawnStartMoves(whitePawnBoard, true);
			bpMove = pawnStartMoves(blackPawnBoard, false);
			wpAttack = pawnAttackMoves(whitePawnBoard, true);
			bpAttack = pawnAttackMoves(blackPawnBoard, false);

			// System.out.println("wp move len: " + wpMove.size());
			// System.out.println("wp attack len: " + wpAttack.size());

			whitePawn.add(wpMove);
			blackPawn.add(bpMove);
			whitePawn.add(wpAttack);
			blackPawn.add(bpAttack);

			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (WP - 1) + i], whitePawn);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (BP - 2) + i], blackPawn);
		}

		ArrayList<ArrayList<Long[]>> blackBishop = new ArrayList<ArrayList<Long[]>>(
				4);
		ArrayList<ArrayList<Long[]>> whiteBishop = new ArrayList<ArrayList<Long[]>>(
				4);
		ArrayList<ArrayList<Long[]>> blackRook = new ArrayList<ArrayList<Long[]>>(
				4);
		ArrayList<ArrayList<Long[]>> whiteRook = new ArrayList<ArrayList<Long[]>>(
				4);
		ArrayList<ArrayList<Long[]>> blackQueen = new ArrayList<ArrayList<Long[]>>(
				8);
		ArrayList<ArrayList<Long[]>> whiteQueen = new ArrayList<ArrayList<Long[]>>(
				8);

		long[] whiteBishopBoard = new long[14];
		long[] blackBishopBoard = new long[14];
		long[] whiteRookBoard = new long[14];
		long[] blackRookBoard = new long[14];
		long[] whiteQueenBoard = new long[14];
		long[] blackQueenBoard = new long[14];

		for (int i = 0; i < 64; i++) {
			whiteBishopBoard[WB] = bitConstants[i];
			blackBishopBoard[BB] = bitConstants[i];
			whiteRookBoard[WR] = bitConstants[i];
			blackRookBoard[BR] = bitConstants[i];
			whiteQueenBoard[WQ] = bitConstants[i];
			blackQueenBoard[BQ] = bitConstants[i];

			whiteBishopBoard[W] = bitConstants[i];
			blackBishopBoard[B] = bitConstants[i];
			whiteRookBoard[W] = bitConstants[i];
			blackRookBoard[B] = bitConstants[i];
			whiteQueenBoard[W] = bitConstants[i];
			blackQueenBoard[B] = bitConstants[i];

			whiteBishop = edgeAdjacent(whiteBishopBoard, true);
			blackBishop = edgeAdjacent(blackBishopBoard, false);
			whiteRook = orthAdjacent(whiteRookBoard, true);
			blackRook = orthAdjacent(blackRookBoard, false);

			whiteQueen = edgeAdjacent(whiteQueenBoard, true);
			blackQueen = edgeAdjacent(blackQueenBoard, false);
			whiteQueen.addAll(orthAdjacent(whiteQueenBoard, true));
			blackQueen.addAll(orthAdjacent(blackQueenBoard, false));

			for (int j = 0; j < whiteBishop.size(); j++) {
				for (int k = 0; k < whiteBishop.get(j).size(); k++) {
					whiteBishop.get(j).get(k)[0] = (long) WB;
				}
			}

			for (int j = 0; j < blackBishop.size(); j++) {
				for (int k = 0; k < blackBishop.get(j).size(); k++) {
					blackBishop.get(j).get(k)[0] = (long) BB;
				}
			}

			for (int j = 0; j < whiteRook.size(); j++) {
				for (int k = 0; k < whiteRook.get(j).size(); k++) {
					whiteRook.get(j).get(k)[0] = (long) WR;
				}
			}

			for (int j = 0; j < blackRook.size(); j++) {
				for (int k = 0; k < blackRook.get(j).size(); k++) {
					blackRook.get(j).get(k)[0] = (long) BR;
				}
			}

			for (int j = 0; j < whiteQueen.size(); j++) {
				for (int k = 0; k < whiteQueen.get(j).size(); k++) {
					whiteQueen.get(j).get(k)[0] = (long) WQ;
				}
			}

			for (int j = 0; j < blackQueen.size(); j++) {
				for (int k = 0; k < blackQueen.get(j).size(); k++) {
					blackQueen.get(j).get(k)[0] = (long) BQ;
				}
			}

			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (WB - 1) + i], whiteBishop);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (BB - 2) + i], blackBishop);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (WR - 1) + i], whiteRook);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (BR - 2) + i], blackRook);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (WQ - 1) + i], whiteQueen);
			ChessBoardWithColumnsAndRows.moveTable
					.put(ChessBoardWithColumnsAndRows.pieceRandoms[64
							* (BQ - 2) + i], blackQueen);

		}

		System.out.println("move table size: "
				+ ChessBoardWithColumnsAndRows.moveTable.size());
	}

}
