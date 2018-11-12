package main;

/**
 * The pieces object.
 */
public class Pieces {
	
	private Piece[] pieces = new Piece[Constants.TOTAL_PIECES.getInt()];
	private String rook = Constants.ROOK.getString();
	private String knight = Constants.KNIGHT.getString();
	private String bishop = Constants.BISHOP.getString();
	private String queen = Constants.QUEEN.getString();
	private String king = Constants.KING.getString();
	private String pawn = Constants.PAWN.getString();
	private String black = Constants.BLACK.getString();
	private String white = Constants.WHITE.getString();
	
	/**
	 * The constructor.
	 */
	public Pieces() {
		createPieces();
	}
	
	/**
	 * Creates the pieces.
	 */	
	public void createPieces() {
		final int CREATE_ROOK = 0;
		final int CREATE_KNIGHT = 1;
		final int CREATE_BISHOP = 2;
		final int CREATE_QUEEN = 3;
		final int CREATE_KING = 4;
		final int CREATE_PAWN = 5;
		final int TOTAL_TYPES = 6;
		final int TOTAL_PAWNS = 16;
		
		int index = 0;
		
		for(int i = 0; i < TOTAL_TYPES; i++) {
			switch(i) {
				case CREATE_ROOK:
					pieces[index] = createPiece(black, rook, 0, 0);
					index++;
					
					pieces[index] = createPiece(black, rook, 0, 7);
					index++;
					
					pieces[index] = createPiece(white, rook, 7, 0);
					index++;
					
					pieces[index] = createPiece(white, rook, 7, 7);
					index++;
					
					break;
				case CREATE_KNIGHT:
					pieces[index] = createPiece(black, knight, 0, 1);
					index++;
					
					pieces[index] = createPiece(black, knight, 0, 6);
					index++;
					
					pieces[index] = createPiece(white, knight, 7, 1);
					index++;
					
					pieces[index] = createPiece(white, knight, 7, 6);
					index++;
					
					break;
				case CREATE_BISHOP:
					pieces[index] = createPiece(black, bishop, 0, 2);
					index++;
					
					pieces[index] = createPiece(black, bishop, 0, 5);
					index++;
					
					pieces[index] = createPiece(white, bishop, 7, 2);
					index++;
					
					pieces[index] = createPiece(white, bishop, 7, 5);
					index++;
					
					break;
				case CREATE_QUEEN:
					pieces[index] = createPiece(black, queen, 0, 3);
					index++;
					
					pieces[index] = createPiece(white, queen, 7, 3);
					index++;
					
					break;
				case CREATE_KING:
					pieces[index] = createPiece(black, king, 0, 4);
					index++;
					
					pieces[index] = createPiece(white, king, 7, 4);
					index++;
					
					break;
				case CREATE_PAWN:
					int pawnIndex = 0;
					
					for(int j = 0; j < TOTAL_PAWNS; j++) {
						if(j >= 0 && j < 8) {
							pieces[index] = createPiece(black, pawn, 1, j);
							index++;
						} else {
							pieces[index] = createPiece(white, pawn, 6, pawnIndex++);
							index++;
						}
					}
					
					break;
			}
		}
		
	}
	
	/**
	 * Creates piece.
	 * 
	 * @param color The piece color.
	 * @param type The piece type.
	 * @param x The piece x position.
	 * @param y The piece y position.
	 * 
	 * @return Piece
	 */
	public Piece createPiece(String color, String type, int x, int y) {
		Piece piece = null;
		
		int position[] = new int[2];
		position[0] = x;
		position[1] = y;
		
		if(type == rook) {
			piece = new Rook(color, type, position);
		} else if(type == knight) {
			piece = new Knight(color, type, position);
		} else if(type == bishop) {
			piece = new Bishop(color, type, position);
		} else if(type == queen) {
			piece = new Queen(color, type, position);
		} else if(type == king) {
			piece = new King(color, type, position);
		} else if(type == pawn) {
			piece = new Pawn(color, type, position);
		}
		
		return piece;
	}	
	
	public Piece[] getPieces() { return pieces; }
	
	/**
	 * Sets the pieces.
	 * 
	 * @param index The pieces array index.
	 * @param piece The piece for the array index.
	 */
	public void setPieces(int index, Piece piece) {
		pieces[index] = piece;
	}
	
}
