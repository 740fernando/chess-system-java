package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.Color;

/**
 * Respons�vel por exibir a matriz de pecas da partida
 * 
 * @author fsouviei
 *
 */
public class UI {

	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		try {
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/**
	 * Recebe input do programa principal e faz a leitura da posicao
	 * 
	 * @param sc
	 * @return
	 */
	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char column = s.charAt(0);
			int row = Integer.parseInt(s.substring(1));
			return new ChessPosition(column, row);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Erro lendo a posicao de xadrez. Valores v�lidos do a1 at� o h8");
		}
	}

	public static void imprimirPartida(ChessMatch chessMatch, List<ChessPiece> capturadas) {
		printBoard(chessMatch.getPieces());
		System.out.println();
		imprimirPecasCapturadas(capturadas);
		System.out.println("Turno : " + chessMatch.getTurno());
		if(!chessMatch.getCheckMate()) {
			System.out.println("Esperando jogador : " + chessMatch.getJogadorAtual());
			testImprimirCheck(chessMatch);
		}else {
			System.out.println("CHECKMATE!");
			System.out.println("Vencedor : "+ chessMatch.getJogadorAtual());
		}
	}

	private static void testImprimirCheck(ChessMatch chessMatch) {
		if(chessMatch.getCheck()) {
			System.out.println("CHECK !");
		}
	}

	/**
	 * Exibe o tabuleiro com as pecas
	 * 
	 * @param pieces
	 */
	public static void printBoard(ChessPiece[][] pieces) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");

	}

	/**
	 * Exibe o tabuleiro com as peças e os movimentos possiveis.
	 * 
	 * @param pieces
	 * @param possibleMoves
	 */
	public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
		for (int i = 0; i < pieces.length; i++) {
			System.out.print((8 - i) + " ");
			for (int j = 0; j < pieces.length; j++) {
				printPiece(pieces[i][j], possibleMoves[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");

	}

	/**
	 * Exibe a peca
	 * 
	 * @param piece
	 * @param background - colorir o fundo
	 */
	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		} else {
			configuraCorDaPeca(piece);
		}
		System.out.print(" ");
	}

	private static void configuraCorDaPeca(ChessPiece piece) {
		if (piece.getColor() == Color.WHITE) {
			System.out.print(ANSI_WHITE + piece + ANSI_RESET);
		} else {
			System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
		}
	}

	private static void imprimirPecasCapturadas(List<ChessPiece> capturadas) {
		List<ChessPiece> white = capturadas.stream().filter(x -> x.getColor() == Color.WHITE)
				.collect(Collectors.toList());
		List<ChessPiece> black = capturadas.stream().filter(x -> x.getColor() == Color.BLACK)
				.collect(Collectors.toList());

		System.out.println("Pecas capturadas : ");
		System.out.print("Brancas : ");

		System.out.print(ANSI_WHITE);
		System.out.println(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);

		System.out.print("Pretas : ");
		System.out.print(ANSI_YELLOW);
		System.out.println(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);
	}

}
