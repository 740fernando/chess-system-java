package boardgame;

public abstract class Piece {

	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		position=null;
	}

	protected Board getBoard() {
		return board;
	}
	
	public abstract boolean[][] possibleMoves();
	
	
	/**
	 * Responsavel por movimentar a peca
	 * HooK methods -> Método concreto que utiliza um metodo abstrado 
	 * 
	 * @param position
	 * @return
	 */
	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getColumn()];
	}
	/**
	 * Responsavel por verificar os movimentos possiveis da peca
	 * @return
	 */
	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for(int i = 0; i<mat.length; i++) {
			for(int y=0; y<mat.length;y++) {
				if(mat[i][y]) {
					return true;
				}
			}
		}
		return false;
	}
}
