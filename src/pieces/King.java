package pieces;

/**
 *
 * @author Bryan Law
 * @author Ryan Kim
 * King Piece
 */
public class King extends Piece{
	private boolean moved;
	
	public King(int x, int y, String color) {
		this.xPos = x;
		this.yPos = y;
		this.color = color;
		moved = false;
	}
	
	public boolean move(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		
		//check if king's new spot would be adjacent to the opponent king
		if(x+1<8 && y+1<8 && board[x+1][y+1]!=this && board[x+1][y+1] instanceof King //bottom right
				|| x+1<8 &&board[x+1][y]!=this && board[x+1][y] instanceof King //bottom
				|| x+1<8 && y-1>-1 && board[x+1][y-1]!=this && board[x+1][y-1] instanceof King //bottom left
				|| y-1>-1 && board[x][y-1]!=this && board[x][y-1] instanceof King //left
				|| x-1>-1 && y-1>-1 && board[x-1][y-1]!=this && board[x-1][y-1] instanceof King //top left
				|| x-1>-1 && board[x-1][y]!=this && board[x-1][y] instanceof King //top
				|| x-1>-1 && y+1<8 && board[x-1][y+1]!=this && board[x-1][y+1] instanceof King //top right
				|| y+1<8 && board[x][y+1]!=this && board[x][y+1] instanceof King) { //right
			return false;
		}
		
		//castling
		boolean castle = false;
		if(!boardObj.getCheck() && !moved && color.equals("white") && x==7 && y==6
				||!moved && color.equals("white") && x==7 && y==2
				||!moved && color.equals("black") && x==0 && y==6
				||!moved && color.equals("black") && x==0 && y==2) {
			castle = true;
		}
		if(castle) {
			if(y==6) {
				if(board[x][7]!=null && board[x][7] instanceof Rook) {
					if(!((Rook) board[x][7]).getMoved()){
						for(int i=yPos+1; i<=6;i++) {
							if(board[xPos][i]!=null) return false;
							board[xPos][i] = this;
							board[xPos][yPos] = null;
							if(color.equals("white")) {
								boardObj.setWKing(xPos, i);
								boardObj.check(true);
							}else {
								boardObj.setBKing(xPos, i);
								boardObj.check(false);
							}
							if(boardObj.getCheck()) {
								if(color.equals("white")) {
									boardObj.setWKing(xPos, yPos);
								}else {
									boardObj.setBKing(xPos, yPos);
								}
								board[xPos][i] = null;
								board[xPos][yPos] = this;
								return false;
							}
						}
						board[x][6] = this;
						board[x][5] = new Rook(x,5,color);
						board[x][7] = null;
					}
				}else 
					return false;
			}else if(y==2){
				if(board[x][0]!=null && board[x][0] instanceof Rook) {
					if(!((Rook) board[x][0]).getMoved()){
						for(int i=yPos-1; i>=2;i--) {
							if(board[xPos][i]!=null) return false;
							board[xPos][i] = this;
							board[xPos][yPos] = null;
							if(color.equals("white")) {
								boardObj.setWKing(xPos, i);
								boardObj.check(true);
							}else {
								boardObj.setBKing(xPos, i);
								boardObj.check(false);
							}
							if(boardObj.getCheck()) {
								if(color.equals("white")) {
									boardObj.setWKing(xPos, yPos);
								}else {
									boardObj.setBKing(xPos, yPos);
								}
								board[xPos][i] = null;
								board[xPos][yPos] = this;
								return false;
							}
						}
						board[x][2] = this;
						board[x][3] = new Rook(x,3,color);
						board[x][0] = null;
					}
				}else
					return false;
			}
			if(color.equals("white")) {
				boardObj.setWKing(x, y);
			}else {
				boardObj.setBKing(x, y);
			}
			board[xPos][yPos] = null;
			xPos = x;
			yPos = y;
			return true;
		}
		
		//if movement is not the same square and only one space around then valid standard move
		if(Math.abs(xPos-x)<=1 && Math.abs(yPos-y)<=1) {
			board[xPos][yPos] = null;
			board[x][y] = this;
			if(color.equals("white")) {
				boardObj.setWKing(x, y);
			}else {
				boardObj.setBKing(x, y);
			}
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
				if(color.equals("white")) {
					boardObj.setWKing(xPos, yPos);
				}else {
					boardObj.setBKing(xPos, yPos);
				}
				return false;
			}
			
			xPos = x;
			yPos = y;
			moved = true;
			return true;
		}
		return false;
	}

	//Checks if the king can move to a new location to avoid checkmate
	public boolean check(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		if(Math.abs(xPos-x)<=1 && Math.abs(yPos-y)<=1) {
			if(board[x][y]==null || !board[x][y].color.equals(color)) 
				return true;
		}
		return false;
	}
	
	public String toString() {
		return color.equals("white")?"wK":"bK";
	}
	
	public boolean getMoved() {
		return moved;
	}
}
