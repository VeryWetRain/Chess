package pieces;

/**
 *
 * @author Bryan Law
 * @author Ryan Kim
 */

public class Board implements Cloneable{
	private Piece[][] board;
	public boolean double_stepped_white;
	public boolean double_stepped_black;
	private int wKingX, wKingY;
	private int bKingX, bKingY;
	private boolean check = false;
	private boolean checkMate = false;

	/**
	 * Constructor for chess board
	 *
	 * @return 8x8 array of Pieces in initial chess formation
	 */
	public Board() {
		board = new Piece[8][8];
		double_stepped_white = false;
		double_stepped_black = false;
		bKingX = 0; 
		bKingY = 4;
		wKingX = 7;
		wKingY = 4;
		
		//Initialize the chess board with initial placements of the pieces
		for(int i=0; i<8; i++) {
			for(int j=0; j<8;j++) {
				if(i==0) {
					//initialize the black pieces in first row
					if(j==2 || j== 5) board[i][j] = new Bishop(i,j,"black");
					if(j==0 || j== 7) board[i][j] = new Rook(i,j,"black");
					if(j==1 || j== 6) board[i][j] = new Knight(i,j,"black");
					if(j==3) board[i][j] = new Queen(i,j,"black");
					if(j==4) board[i][j] = new King(i,j,"black");
				}
				else if(i==1) {
					//all black pawns
					board[i][j] = new Pawn(i,j,"black");
				}
				else if(i==6) {
					//all white pawns
					board[i][j] = new Pawn(i,j,"white");
				}
				else if(i==7) {
					//initialize the white pieces in the last row
					if(j==2 || j== 5) board[i][j] = new Bishop(i,j,"white");
					if(j==0 || j== 7) board[i][j] = new Rook(i,j,"white");
					if(j==1 || j== 6) board[i][j] = new Knight(i,j,"white");
					if(j==3) board[i][j] = new Queen(i,j, "white");
					if(j==4) board[i][j] = new King(i,j,"white");
				}
				//Black and white empty spaces are treated as null
				else board[i][j] = null;
			}
		}
	}

	/**
	 * Determines if the board is in a check state.
	 *
	 * @param white Boolean for whose turn it is.
	 */
	public void check(boolean white) {
		for(int i=0;i<8; i++) {
			for(int j=0;j<8;j++) {
				if(white && board[i][j]!=null && !(board[i][j] instanceof King) && board[i][j].getColor().equals("black")) {
					if(board[i][j].check(this, wKingX, wKingY)) {
						check = true;
						return;
					}
				}else if(!white && board[i][j]!=null && !(board[i][j] instanceof King) && board[i][j].getColor().equals("white")) {
					if(board[i][j].check(this, bKingX, bKingY)) {
						check = true; 
						return;
					}
				}
			}
		}
		check = false;
	}

	/**
	 * Determines if the given board is in checkmate.
	 *
	 * @param boardObj Object refering to the chessboard.
	 * @param white Boolean for whose turn it is.
	 * @return true if in checkmate, otherwise false
	 */
	public boolean checkMate(Board boardObj, boolean white) {
		Piece[][] board = boardObj.getBoard();
		if(!boardObj.getCheck()) return false;
		for(int i=0; i<8; i++) {
			for(int j=0; j<8; j++) {
				if(white && board[i][j]!=null && board[i][j].getColor().equals("white")) {
					for(int x=0;x<8;x++) {
						for(int y=0; y<8; y++) {
							if(board[i][j].check(boardObj, x, y)) {
								Piece temp = board[x][y];
								board[x][y] = board[i][j];
								board[i][j] = null;
								if(board[x][y] instanceof King) {
									this.setWKing(x, y);
								}
								boardObj.check(white);
								board[i][j] = board[x][y];
								board[x][y] = temp;
								if(board[x][y] instanceof King) {
									this.setWKing(i, j);
								}
								if(!boardObj.getCheck()) {
									check = true;
									checkMate = false;
									return false;
								}
							}
						}
					}
				}else if(!white && board[i][j]!=null && board[i][j].getColor().equals("black")) {
					for(int x=0;x<8;x++) {
						for(int y=0; y<8; y++) {
							if(board[i][j].check(boardObj, x, y)) {
								Piece temp = board[x][y];
								board[x][y] = board[i][j];
								board[i][j] = null;
								if(board[x][y] instanceof King) {
									this.setBKing(x, y);
								}
								boardObj.check(white);
								board[i][j] = board[x][y];
								board[x][y] = temp;
								if(board[x][y] instanceof King) {
									this.setBKing(i, j);
								}
								if(!boardObj.getCheck()) { 
									check = true;
									checkMate = false;
									return false;
								}
							}
						}
					}
				}
			}
		}
		checkMate = true;
		return true;
	}
	
	public Piece getPiece(int x, int y) {
		return board[x][y];
	}
	
	public Piece[][] getBoard() {
		return board;
	}
	
	public void setWKing(int x, int y) {
		wKingX= x;
		wKingY= y;
	}
	
	public void setBKing(int x, int y) {
		bKingX= x;
		bKingY= y;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckmate() {
		return checkMate;
	}
	
	public void setCheck(boolean b) {
		check = b;
	}
	
}
