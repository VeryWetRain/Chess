package pieces;

/**
 *
 * @author Bryan Law
 * @author Ryan Kim
 * Queen Piece
 */
public class Queen extends Piece{
	
	public Queen(int x, int y, String color) {
		this.xPos = x;
		this.yPos = y;
		this.color = color;
	}
	
	public boolean move(Board boardObj, int x, int y){
		Piece[][] board = boardObj.getBoard();
		if(Math.abs(xPos-x)==Math.abs(yPos-y) || Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0 || Math.abs(yPos-y)!=0 && Math.abs(xPos-x)==0) {
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
			}
			//Checking for collisions moving up and down
			else if(Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0) { 
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
		if(Math.abs(xPos-x)==Math.abs(yPos-y) || Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0 || Math.abs(yPos-y)!=0 && Math.abs(xPos-x)==0) {
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
			}
			//Checking for collisions moving up and down
			else if(Math.abs(xPos-x)!=0 && Math.abs(yPos-y)==0) { 
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
			if(board[x][y]==null || !board[x][y].color.equals(color))
			return true;
		}
		return false;
	}
	
	public String toString() {
		return color.equals("white")?"wQ":"bQ";
	}
}
