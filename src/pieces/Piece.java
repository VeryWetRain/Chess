package pieces;

/**
 * Represents a generic piece on the chess board.
 * @author Bryan Law
 * @author Ryan Kim
 *
*/
public abstract class Piece {
	protected int xPos;
	protected int yPos;
	protected String color;

	/**
	 * Moves a chess piece to the given position.
	 *
	 * @param boardObj 	Object refering to the chessboard.
	 * @param i			Translated x position for destination.
	 * @param j			Translated y position for destination.
	 * @return			true if move is successful, otherwise false
	 */
	public abstract boolean move(Board boardObj, int i, int j);

	/**
	 * Moves and promotes a pawn piece if it has reached the other side.
	 *
	 * <p>This move function is only intended to be used for the pawn piece and will return false otherwise.</p>
	 *
	 * @param boardObj	Object refering to the chessboard.
	 * @param i			Translated x position for destination.
	 * @param j			Translated y position for destination.
	 * @param s			Piece to promote to.
	 * @return			true if successful(and is pawn piece), otherwise false
	 */
	public boolean move(Board boardObj, int i, int j, String s) {return false;}

	/**
	 * Determines if the current piece puts the King piece in check.
	 *
	 * @param boardObj	Object refering to the chessboard.
	 * @param x			Translated x position of the King.
	 * @param y			Translated y positions of the King.
	 * @return			true if the King is in check, otherwise false.
	 */
	public abstract boolean check(Board boardObj, int x, int y);

	/**
	 * Get method for the Piece's color.
	 *
	 * @return color of Piece
	 */
	public String getColor() {
		return color;
	}
}
