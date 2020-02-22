package com.example;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by Birkhoff
 */
public class TicTacToeTest {
	@Test
	public void simpleNoWinnerBoard() throws Exception {
		assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("O...X.X.."));
		assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("........."));
		assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("o..x..o.x"));
		assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("o..x.xx.o"));
		assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("ox.....xo"));
		assertEquals(Evaluation.NoWinner, TicTacToe.evaluateBoard("ooxx....."));
	}

	@Test
	public void testInvalidInput() throws Exception {
		assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("............adsssssssssssssssssssssssssssssss"));
		assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("."));
		assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard(""));
		assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard("                                                     "));
		assertEquals(Evaluation.InvalidInput, TicTacToe.evaluateBoard(null));
	}

	@Test
	public void testXwins() throws Exception {
		assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("iOioaoXxX"));
		assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("Xoqxo0x.p"));
		assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("oxapXoAxt"));
		assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("XOmOxoupX"));
		assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("oOxiXoXac"));
		assertEquals(Evaluation.Xwins, TicTacToe.evaluateBoard("XoxOXoxOX"));
	}

	@Test
	public void testOwins() throws Exception {
		assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("OOOxx.. x"));
		assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("xoxqOyxoa"));
		assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("OXXiobaxO"));
		assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("OxxaoijxO"));
		assertEquals(Evaluation.Owins, TicTacToe.evaluateBoard("axoCObOxx"));
	}

	@Test
	public void testUnreachableState() throws Exception {
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("OOOOOOOOO"));
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("XXXXXXXXX"));
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("XoXoXXXXX"));
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("OOOOOOXOX"));
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("OXXOXXXOX"));
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("XXXOOO..."));
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("OOO...XXX"));
		assertEquals(Evaluation.UnreachableState, TicTacToe.evaluateBoard("Xo.xOqXop"));
	}
}
