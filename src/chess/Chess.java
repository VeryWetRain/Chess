package chess;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import pieces.*;

/**
*
* @author Bryan Law
* @author Ryan Kim
*/
public class Chess {
	/**
	* Main driver method to launch the chess game from
	*/
	public static void main(String[] args) throws FileNotFoundException {
		//8 by 8 double array of pieces (the chess board)
		Board board = new Board();
//		Piece[][] board = b.board;
		File testfile = new File("foolsmatewhitewins.txt");
		Scanner scn = new Scanner(testfile);
		
		//boolean to keep track of who's turn it is
		boolean white = true, invalid=false, drawRequest = false, move_ret;
		String input = "";
		int x1,y1,x2,y2;
		String to_promote;
		
		//testing out input for now
		while(!input.equals("resign")) {
			printBoard(board.getBoard());	
			System.out.println("");
			do{
				if(invalid)System.out.println("Illegal move, try again");
				if(board.getCheck()) System.out.println("Check");
				if(white) System.out.println("White's move: ");
				else System.out.println("Black's move: ");
				input = scn.nextLine();	
				if(drawRequest && input.equals("draw")){
					System.out.println("draw");
					return;
				}else if(input.equals("resign")) {
					if(white)System.out.println("Black wins");
					else System.out.println("White wins");
					return;
				}else if(input.substring(input.lastIndexOf(" ")).equals(" draw?")){
					drawRequest = true;
				}
				x1 = 8-(input.charAt(1)-48);
				y1 = input.charAt(0)-97;
				x2 = 8-(input.charAt(4)-48);
				y2 = input.charAt(3)-97;
				
				invalid = true;
			}//check if selected piece is null, player is moving their colored piece, and if move is valid
			while(board.getPiece(x1,y1) == null 
					|| white && !board.getPiece(x1,y1).getColor().equals("white") //moving your colored piece
					|| !white && !board.getPiece(x1,y1).getColor().equals("black")
					|| board.getPiece(x2, y2)!=null && white && board.getPiece(x2, y2).getColor().equals("white") //moving to ally piece
					|| board.getPiece(x2, y2)!=null && !white && board.getPiece(x2, y2).getColor().equals("black")
					|| x1 == x2 && y1 == y2 //moving to same spot
					|| ((input.length() == 7) ? !board.getPiece(x1,y1).move(board, x2, y2, input.substring(6)) : !board.getPiece(x1,y1).move(board, x2, y2))); 
			board.check(!white); //check if new move results in a check
			invalid = false;
			white = !white; 
			if(board.checkMate(board, white)) {
				System.out.println("Checkmate");
				break;
			}
		}
		if(!white)
		System.out.println("White wins");
		else System.out.println("Black wins");
		scn.close(); 
	}
	
	/**
	* Prints out the current state of the chess board in ascii format
	* @param  board The chess board to be printed 
	*/
	public static void printBoard(Piece[][] board) {
//		Piece[][] board = b.board;
		for(int i=0; i<8; i++) {
			for(int j=0; j<8;j++) {
				if(board[i][j]==null) {
					if(i%2==0 && j%2==0 || i%2==1 && j%2==1) System.out.print("  ");
					else System.out.print("##");
				}else {
					System.out.print(board[i][j]);
				}
				System.out.print(" ");
			}
			System.out.println(8-i);
		}
		System.out.println(" a  b  c  d  e  f  g  h");
	}

}
