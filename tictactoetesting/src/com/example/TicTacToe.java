package com.example;
public class TicTacToe {
	public static Evaluation evaluateBoard(String boardState) {

		if (boardState == null || boardState.length() != 9)
			return Evaluation.InvalidInput;
		
		int numX = 0, numO = 0, i;
		for (i = 0; i < 9; i++) {
			switch (boardState.charAt(i)) {
				case 'x':
				case 'X':
					numX++;
					break;
				case 'o':
				case 'O':
					numO++;
					break;
			}
		}
		if (numX != numO && numX - numO != 1)
			return Evaluation.UnreachableState;
		
		int row, col;
		boolean xWins = false, oWins = false;
		boardState = boardState.toLowerCase();
		String rowStr;
		for (row = 0; row < 3; row++) { // Test for horizontal wins
			rowStr = boardState.substring(row * 3, row * 3 + 3);
			if (rowStr.equalsIgnoreCase("xxx"))
				xWins = true;
			if (rowStr.equalsIgnoreCase("ooo"))
				oWins = true;
		}

		for (col = 0; col < 3; col++) { // Test for vertical wins
			if (boardState.charAt(col) == boardState.charAt(col + 3) && boardState.charAt(col) == boardState.charAt(col + 6)) {
				switch (boardState.charAt(col)) {
					case 'x':
						xWins = true;
						break;
					case 'o':
						oWins = true;
						break;
				}
			}
		}


		if (boardState.charAt(0) == boardState.charAt(4) && boardState.charAt(4) == boardState.charAt(8)) { // Test for diagonal wins
			switch (boardState.charAt(0)) {
				case 'x':
					xWins = true;
					break;
				case 'o':
					oWins = true;
					break;
				default:
					break;
			}
		}
		else if (boardState.charAt(2) == boardState.charAt(4) && boardState.charAt(4) == boardState.charAt(6)) {
			switch (boardState.charAt(2)) {
				case 'x':
					xWins = true;
					break;
				case 'o':
					oWins = true;
					break;
				default:
					break;
			}
		}

		if (xWins && oWins) return Evaluation.UnreachableState;
		else if (xWins) return Evaluation.Xwins;
		else if (oWins) return Evaluation.Owins;
		return Evaluation.NoWinner;
	}
}
