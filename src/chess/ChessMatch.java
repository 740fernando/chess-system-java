package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * Classe "Partida de Xadrez", responsável por conter as regras do jogo
 * 
 * @author fsouviei
 *
 */
public class ChessMatch {

	private Board board;

	/**
	 * Tamanho do tabuleiro
	 */
	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
	}

	/**
	 * Retorna uma matriz de peças da partida de xadrez
	 * 
	 * @return
	 */
	public ChessPiece[][] getPieces() {
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int y = 0; y < board.getColumns(); y++) {
				mat[i][y] = (ChessPiece) board.piece(i, y);
			}
		}
		return mat;
	}
	
	/** Responsavel por mover a peça da pos origem para destino
	 *  retorna uma posicao capturada, se for o caso
	 */
	public ChessPiece executarMovimentoXadrez(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target  = targetPosition.toPosition();
		validarPosicaoOrigem(source);
		Piece pecaCapturada = makeMove(source, target);
		return (ChessPiece)pecaCapturada;
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);
		return capturedPiece;
	}
	
	private void validarPosicaoOrigem(Position source) {
		if(!board.thereIsPiece(source)) {
			throw new ChessException("Não há peça na posicao de origem ");
		}
		
	}
	



	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
	}

	/**
	 * Responsável por inicial a partida de xadrez
	 */
	private void initialSetup() {
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
		placeNewPiece('c', 2, new Rook(board, Color.WHITE));
		placeNewPiece('d', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 2, new Rook(board, Color.WHITE));
		placeNewPiece('e', 1, new Rook(board, Color.WHITE));
		placeNewPiece('d', 1, new King(board, Color.WHITE));

		placeNewPiece('c', 7, new Rook(board, Color.BLACK));
		placeNewPiece('c', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 7, new Rook(board, Color.BLACK));
		placeNewPiece('e', 8, new Rook(board, Color.BLACK));
		placeNewPiece('d', 8, new King(board, Color.BLACK));
	}

	
}
