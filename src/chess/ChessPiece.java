package chess;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;

public abstract class ChessPiece extends Piece {

	private Color color;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	/**
	 * Retorna uma posicao no formato do xadrez
	 * @return
	 */
	public ChessPosition getChessPosition() {
	     return ChessPosition.fromPosition(position);
	}
	/**
	 * Verifica se existe uma peca adversaria na matriz do tabuleiro
	 * 
	 * @param position
	 * @return
	 */
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p != null && p.getColor() != color; 
	}
}
