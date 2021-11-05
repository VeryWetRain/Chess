package pieces;

/**
 *
 * @author Bryan Law
 * @author Ryan Kim
 * Rook Piece
 */
public class Rook extends Piece{
	private boolean moved;
	
	public Rook(int x, int y, String color) {
		this.xPos = x;
		this.yPos = y;
		this.color = color;
		moved = false;
	}
	
	public boolean move(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		if(Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0 || Math.abs(yPos-y)!=0 && Math.abs(xPos-x)==0) {
			//Checking for collisions moving up and down
			if(Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0) { 
				if(xPos-x<0) { //moving down
					for(int i=xPos+1; i<x; i++) {
						if(board[i][yPos]!=null) return false;
					}
				}else if(xPos-x>0) { //moving up
					for(int i=xPos-1; i>x; i--) {
						if(board[i][yPos]!=null) return false;
					}
				}
			}
			//Checking for collisions moving left and right
			else if(Math.abs(yPos-y)!=0 && Math.abs(xPos-x)==0) {
				if(yPos-y<0) {
					for(int i=yPos+1; i<y; i++) { //moving right
						if(board[xPos][i]!=null) return false;
					}
				}else if(yPos-y>0) {
					for(int i=yPos-1; i>y; i--) { //moving left
						if(board[xPos][i]!=null) return false;
					}
				}
			}
			//Swap positions on the board
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
			
			moved = true;
			return true;
		}
		return false;
	}

	public boolean check(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		if(Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0 || Math.abs(yPos-y)!=0 && Math.abs(xPos-x)==0) {
			//Checking for collisions moving up and down
			if(Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0) { 
				if(xPos-x<0) { //moving down
					for(int i=xPos+1; i<x; i++) {
						if(board[i][yPos]!=null) return false;
					}
				}else if(xPos-x>0) { //moving up
					for(int i=xPos-1; i>x; i--) {
						if(board[i][yPos]!=null) return false;
					}
				}
				if(board[x][y]==null || !board[x][y].color.equals(color))
				return true;
			}
			//Checking for collisions moving left and right
			else if(Math.abs(yPos-y)!=0 && Math.abs(xPos-x)==0) {
				if(yPos-y<0) {
					for(int i=yPos+1; i<y; i++) { //moving right
						if(board[xPos][i]!=null) return false;
					}
				}else if(yPos-y>0) {
					for(int i=yPos-1; i>y; i--) { //moving left
						if(board[xPos][i]!=null) return false;
					}
				}
				if(board[x][y]==null || !board[x][y].color.equals(color))
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return color.equals("white")?"wR":"bR";
	}
	
	public boolean getMoved() {
		return moved;
	}
}
