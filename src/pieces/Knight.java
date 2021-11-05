package pieces;

/**
 *
 * @author Bryan Law
 * @author Ryan Kim
 * Knight Piece
 */
public class Knight extends Piece{
	
	public Knight(int x, int y, String color) {
		this.xPos = x;
		this.yPos = y;
		this.color = color;
	}

	public boolean move(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		if (Math.abs(xPos-x)==2 && Math.abs(yPos-y)==1 || Math.abs(xPos-x)==1 && Math.abs(yPos-y)==2){
			board[xPos][yPos] = null;
			board[x][y] = this;
			
			//Checks if this new move would result in a check for the king
			if(color.equals("white")) {
				boardObj.check(true);
			}else {
				boardObj.check(false);
			}
			//reverts swapped positions on the board if it would result in a check
			if(boardObj.getCheck()) {
				board[xPos][yPos] = this;
				board[x][y] = null;
				return false;
			}
					
			xPos = x;
			yPos = y;
			
			return true;
		}
		return false;
	}

	public boolean check(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		if(Math.abs(xPos-x)==2 && Math.abs(yPos-y)==1 || Math.abs(xPos-x)==1 && Math.abs(yPos-y)==2)
				return (board[x][y]==null || !board[x][y].color.equals(color));
		return false;
	}
	
	public String getColor() {
		return color;
	}
	
	public String toString() {
		return color.equals("white")?"wN":"bN";
	}
}
