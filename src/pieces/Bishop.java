package pieces;

/**
 *
 * @author Bryan Law
 * @author Ryan Kim
 * Bishop Piece
 */
public class Bishop extends Piece{
	
	public Bishop(int x, int y, String color) {
		this.xPos = x;
		this.yPos = y;
		this.color = color;
	}
	
	public boolean move(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		if(Math.abs(xPos-x)==Math.abs(yPos-y)) {
			if(xPos-x>0 && yPos-y<0) { //moving to up and right diagonally
				int j = yPos+1;
				for(int i=xPos-1; i>x; i--) {
					if(board[i][j]!=null) return false;
					j++;
				}
			}else if(xPos-x>0 && yPos-y>0) {//moving to up and left diagonally
				int j = yPos-1;
				for (int i=xPos-1; i>x; i--) {
					if(board[i][j]!=null) return false;
					j--;
				}
			}else if(xPos-x<0 && yPos-y<0) {//moving to down and right diagonally
				int j = yPos+1;
				for (int i=xPos+1; i<x; i++) {
					if(board[i][j]!=null) return false;
					j++;
				}
			}else if(xPos-x<0 && yPos-y>0) {//moving to down and left diagonally
				int j = yPos-1;
				for (int i=xPos+1; i<x; i++) {
					if(board[i][j]!=null) return false;
					j--;
				}
			}
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
		if(Math.abs(xPos-x)==Math.abs(yPos-y)) {
			if(xPos-x>0 && yPos-y<0) { //moving to up and right diagonally
				int j = yPos+1;
				for(int i=xPos-1; i>x; i--) {
					if(board[i][j]!=null) return false;
					j++;
				}
			}else if(xPos-x>0 && yPos-y>0) {//moving to up and left diagonally
				int j = yPos-1;
				for (int i=xPos-1; i>x; i--) {
					if(board[i][j]!=null) return false;
					j--;
				}
			}else if(xPos-x<0 && yPos-y<0) {//moving to down and right diagonally
				int j = yPos+1;
				for (int i=xPos+1; i<x; i++) {
					if(board[i][j]!=null) return false;
					j++;
				}
			}else if(xPos-x<0 && yPos-y>0) {//moving to down and left diagonally
				int j = yPos-1;
				for (int i=xPos+1; i<x; i++) {
					if(board[i][j]!=null) return false;
					j--;
				}
			}
			if(board[x][y]==null || !board[x][y].color.equals(color))
			return true;
		}
		return false;
	}
	
	public String toString() {
		return color.equals("white")?"wB":"bB";
	}
}
