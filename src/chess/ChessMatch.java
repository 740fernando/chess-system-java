package chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import boardgame.Board;
import boardgame.Piece;
import boardgame.Position;
import chess.pieces.King;
import chess.pieces.Rook;

/**
 * Classe "Partida de Xadrez", responsï¿½vel por conter as regras do jogo
 * 
 * @author fsouviei
 *
 */
public class ChessMatch {

	private int turno;
	private Color jogadorAtual;
	private Board board;
	private boolean check; // uma propriedade boolean por padrão inicia com false

	private List<Piece> pecasNoTabuleiro = new ArrayList<>();
	private List<Piece> pecasCapturadas = new ArrayList<>();

	/**
	 * Tamanho do tabuleiro
	 */
	public ChessMatch() {
		board = new Board(8, 8);
		turno = 1;
		jogadorAtual = Color.WHITE;
		initialSetup();
	}

	public Color getJogadorAtual() {
		return jogadorAtual;
	}

	public void setJogadorAtual(Color jogadorAtual) {
		this.jogadorAtual = jogadorAtual;
	}

	public int getTurno() {
		return turno;
	}

	public void setTurno(int turno) {
		this.turno = turno;
	}

	/**
	 * Retorna uma matriz de peï¿½as da partida de xadrez
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

	/**
	 * Responsavel por imprimir as posicoes possiveis a partir de uma posicao de
	 * origem
	 * 
	 * @param sourcePosition
	 * @return
	 */
	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validarPosicaoOrigem(position);
		return board.piece(position).possibleMoves();
	}

	/**
	 * Responsavel por mover a peï¿½a da pos origem para destino retorna uma posicao
	 * capturada, se for o caso
	 */
	public ChessPiece executarMovimentoXadrez(ChessPosition sourcePosition, ChessPosition targetPosition) {
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validarPosicaoOrigem(source);
		validarPosicaoDestion(source, target);
		Piece pecaCapturada = makeMove(source, target);
		proximoTurno();
		return (ChessPiece) pecaCapturada;
	}

	private void validarPosicaoDestion(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("A peca escolhida nao pode mover para posicao destino");
		}
	}

	private Piece makeMove(Position source, Position target) {
		Piece p = board.removePiece(source);
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(p, target);

		if (capturedPiece != null) {
			pecasNoTabuleiro.remove(capturedPiece);
			pecasCapturadas.add(capturedPiece);
		}
		return capturedPiece;
	}

	/**
	 * Desfaz o movimento da peca. Necassario caso o user se coloque em check.
	 * 
	 * @param source
	 * @param target
	 * @param capturedPiece
	 */
	private void desfazMovimento(Position source, Position target, Piece capturedPiece) {
		Piece p = board.removePiece(target);
		board.placePiece(p, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);
			pecasCapturadas.remove(capturedPiece);
			pecasNoTabuleiro.add(capturedPiece);
		}
	}

	private void proximoTurno() {
		turno++;
		jogadorAtual = (jogadorAtual == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private void validarPosicaoOrigem(Position source) {
		if (!board.thereIsPiece(source)) {
			throw new ChessException("Nao ha peca na posicao de origem ");
		}
		if (jogadorAtual != ((ChessPiece) board.piece(source)).getColor()) {
			throw new ChessException("A peca escolhida nao eh sua ");
		}
		if (!board.piece(source).isThereAnyPossibleMove()) {
			throw new ChessException("Nao existe movimentos possiveis para peca escolhida ");
		}
	}

	/**
	 * Retorna o oponente de uma cor. Ex: Se o turno foi branco, o oponente é preto.
	 * @param color
	 * @return
	 */
	private Color oponente(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}
	/**
	 * Responsável por localizar o Rei de uma determinada cor
	 * @param color
	 * @return ChessPiece
	 */
	private ChessPiece king(Color color) {
		List<Piece> list = pecasNoTabuleiro.stream().filter(x -> ((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p : list) {
			if( p instanceof King) { 
				return (ChessPiece)p;
			}
		}
		throw new IllegalStateException("Não existe o rei da cor "+color+" no tabuleiro!");
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {
		board.placePiece(piece, new ChessPosition(column, row).toPosition());
		pecasNoTabuleiro.add(piece);
		pecasCapturadas.add(piece);
	}

	/**
	 * Responsï¿½vel por inicial a partida de xadrez
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
