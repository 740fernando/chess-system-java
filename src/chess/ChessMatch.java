package chess;

import boardgame.Board;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * Classe "Partida de Xadrez", responsável por conter as regras do jogo
 * @author fsouviei
 *
 */
public class ChessMatch {
	
	private Board board;
	
	/**
	 * Tamanho do tabuleiro
	 */
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	/**
	 * Retorna uma matriz de peças da partida de xadrez
	 * @return 
	 */
	public ChessPiece[][] getPieces(){
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for(int i = 0 ; i<board.getRows();i++) {
			for(int y = 0 ; y<board.getColumns();y++) {
				mat[i][y]=(ChessPiece) board.piece(i,y);
			}
		}
		return mat;
	}
	/**
	 * Responsável por inicial a partida de xadrez
	 */
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
		board.placePiece(new King(board, Color.BLACK), new Position(0,4));
		board.placePiece(new King(board, Color.BLACK), new Position(7,4));
	}

}
