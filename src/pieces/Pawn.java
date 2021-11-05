package pieces;
/**
 *
 * @author Bryan Law
 * @author Ryan Kim
 * Pawn Piece
 */
public class Pawn extends Piece{
	private boolean moved;
	private boolean double_stepped;
	private boolean promote;
	
	public Pawn(int x, int y, String color) {
		this.xPos = x;
		this.yPos = y;
		this.color = color;
		this.moved = false;
		this.double_stepped = false;
		this.promote = false;
	}
	
	public boolean move(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		boolean move = false;
		if(color.equals("white")) {
			// first move can move up 2
			if(!moved && xPos-x==2 && yPos-y==0) { 
				for(int i=xPos-1; i>=x; i--) {
					if(board[i][yPos]!=null) return false;
				}
				double_stepped = true;
				boardObj.double_stepped_white = true;
				move = true;
			// can move up 1 space if it is empty
			}else if(xPos-x==1 && yPos-y==0 && board[x][y]==null) {
				move = true;
				double_stepped = false;
				boardObj.double_stepped_white = false;
			// can move up 1 left 1 if there is a piece to capture
			}else if(xPos-x==1 && yPos-y==1 && board[x][y]!=null) {
				move = true;
				double_stepped = false;
				boardObj.double_stepped_white = false;
			// can move up 1 right 1 if there is a piece to capture
			}else if(xPos-x==1 && yPos-y==-1 && board[x][y]!=null) {
				move = true;
				double_stepped = false;
				boardObj.double_stepped_white = false;
			// en passant: can move up 1 right 1 to capture a pawn that has double-stepped to the right of it
			} else if(xPos-x==1 && yPos-y==-1 && // up 1 right 1
					  board[x][y] == null && // empty space
					  xPos==3 && // fifth rank for white
					  board[xPos][yPos+1] instanceof Pawn && // capturing piece must be pawn
					  boardObj.double_stepped_black // immediate last move was double-step
					  ) {
				// check if captured pawn's last move was a double step
				if(((Pawn) board[xPos][yPos+1]).double_stepped) {
					move = true;
					board[xPos][yPos+1] = null;
					double_stepped = false;
					boardObj.double_stepped_white = false;
				}
			// en passant: can move up 1 left 1 to capture a pawn that has double-stepped to the right of it
			} else if(xPos-x==1 && yPos-y==1 && // up 1 left 1
					board[x][y] == null && // empty space
					xPos==3 && // fifth rank for white
					board[xPos][yPos-1] instanceof Pawn && // capturing piece must be pawn
					boardObj.double_stepped_black // immediate last move was double-step
					) {
				// check if captured pawn's last move was a double step
				if (((Pawn) board[xPos][yPos - 1]).double_stepped) {
					move = true;
					board[xPos][yPos - 1] = null;
					double_stepped = false;
					boardObj.double_stepped_white = false;
				}
			}

			// promote if move is valid and destination is on the 8th rank
			if(move && x==0) {promote = true;}

		}else if(color.equals("black")) {
			// first move can move down 2
			if(!moved && xPos-x==-2 && yPos-y==0) { 
				for(int i=xPos+1; i<=x; i++) {
					if(board[i][yPos]!=null) return false;
				}
				double_stepped = true;
				boardObj.double_stepped_black = true;
				move = true;
			// can move down 1 space if it is empty
			}else if(xPos-x==-1 && yPos-y==0 && board[x][y]==null) {
				move = true;
				double_stepped = false;
				boardObj.double_stepped_black = false;
			// can move down 1 left 1 if there is a piece to capture
			}else if(xPos-x==-1 && yPos-y==1 && board[x][y]!=null) {
				move = true;
				double_stepped = false;
				boardObj.double_stepped_black = false;
			// can move down 1 right 1 if there is a piece to capture
			}else if(xPos-x==-1 && yPos-y==-1 && board[x][y]!=null) {
				move = true;
				double_stepped = false;
				boardObj.double_stepped_black = false;
			// en passant: can move down 1 left 1 to capture a pawn that has double-stepped to the right of it
			} else if(xPos-x==-1 && yPos-y==1 && // down 1 left 1
					board[x][y] == null && // empty space
					xPos==4 && // fifth rank for black
					board[xPos][yPos-1] instanceof Pawn && // capturing piece must be pawn
					boardObj.double_stepped_white // immediate last move was double-step
					) {
			// check if captured pawn's last move was a double step
				if(((Pawn) board[xPos][yPos-1]).double_stepped) {
					move = true;
					board[xPos][yPos-1] = null;
					double_stepped = false;
					boardObj.double_stepped_black = false;
				}
			// en passant: can move down 1 right 1 to capture a pawn that has double-stepped to the right of it
			} else if(xPos-x==-1 && yPos-y==-1 && // down 1 right 1
					board[x][y] == null && // empty space
					xPos==4 && // fifth rank for black
					board[xPos][yPos+1] instanceof Pawn && // capturing piece must be pawn
					boardObj.double_stepped_white // immediate last move was double-step
					) {
			// check if captured pawn's last move was a double step
				if (((Pawn) board[xPos][yPos + 1]).double_stepped) {
					move = true;
					board[xPos][yPos + 1] = null;
					double_stepped = false;
					boardObj.double_stepped_black = false;
				}
			}

			// promote if move is valid and destination is on the 8th rank
			if(move && x==7) {promote = true;}
		}
		if(move) {
			board[xPos][yPos] = null;
			if(promote) {
				board[x][y] = new Queen(x, y, this.color);
			} else {
				board[x][y] = this;
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
				return false;
			}
			xPos = x;
			yPos = y;
			moved = true;
			return true;
		}
		return false;
	}

	private static Piece getPiece(int x, int y, String type, String color) {
		switch(type) {
			case "B":
				return new Bishop(x, y, color);
			case "R":
				return new Rook(x, y, color);
			case "N":
				return new Knight(x, y, color);
			default:
				return new Queen(x, y, color);
		}
	}

	@Override
	public boolean move(Board boardObj, int x, int y, String type) {
		Piece[][] board = boardObj.getBoard();
		if(color.equals("white") && x==0 || color.equals("black") && x==7) {
			if (move(boardObj, x, y)) {
				Piece promoted = getPiece(x, y, type, color);
				board[x][y] = promoted;
				return true;
			}
		}

		return false;
	}

	public boolean check(Board boardObj, int x, int y) {
		Piece[][] board = boardObj.getBoard();
		if(color.equals("white")) {
			// first move can move up 2
			if(!moved && xPos-x==2 && yPos-y==0) { 
				for(int i=xPos-1; i>=x; i--) {
					if(board[i][yPos]!=null) return false;
				}
				return true;
			// can move up 1 space if it is empty
			}else if(xPos-x==1 && yPos-y==0 && board[x][y]==null) {
				return true;
			// can move up 1 left 1 if there is a piece to capture
			}else if(xPos-x==1 && yPos-y==1 && board[x][y]!=null && !board[x][y].color.equals(color)) {
				return true;
			// can move up 1 right 1 if there is a piece to capture
			}else if(xPos-x==1 && yPos-y==-1 && board[x][y]!=null && !board[x][y].color.equals(color)) {
				return true;
			}
		}else {
			if(!moved && xPos-x==-2 && yPos-y==0) { 
				for(int i=xPos+1; i<=x; i++) {
					if(board[i][yPos]!=null) return false;
				}
				return true;
			// can move down 1 space if it is empty
			}else if(xPos-x==-1 && yPos-y==0 && board[x][y]==null) {
				return true;
			// can move down 1 left 1 if there is a piece to capture
			}else if(xPos-x==-1 && yPos-y==1 && board[x][y]!=null && !board[x][y].color.equals(color)) {
				return true;
			// can move down 1 right 1 if there is a piece to capture
			}else if(xPos-x==-1 && yPos-y==-1 && board[x][y]!=null && !board[x][y].color.equals(color)) {
				return true;
			}
		}
		return false;
	}
	
	public String toString() {
		return color.equals("white")?"wp":"bp";
	}

}
