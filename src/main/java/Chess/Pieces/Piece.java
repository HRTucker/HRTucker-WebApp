package Chess.Pieces;

import java.io.Serializable;
import java.util.Objects;


import Chess.Game.*;

public abstract class Piece implements Serializable{
	
	
	/*	TODO
	 *	Add checking communication
	 *	if in check bool
	 *	can stop check/is checking king
	 * 	Setup is being attacked states for tiles surrounding the king
	*/
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int m_x; //x position
	public int m_y; //y position
	
	protected PieceType m_type; //Which piece it is. This will help with function checking later on.
	
	protected Player m_player; //Which player it is assigned to.
	
	protected int[][] checkBoard = new int[8][8];//valid movement will be labelled with 1's and 0's
	
	public boolean hasMoved;//For PAWN & KING & ROOK to account for the double spaced first move for PAWN and castling for KING & ROOK.
	
	public boolean hasBeenChecked;
	
	public boolean canCastleLeft;//For tracking... castling on the left
	
	public boolean canCastleRight;//For tracking... castling on the right
	
	public boolean isChecking;
	
	public boolean isTaken;
	
	public boolean isPinned;
	
	public boolean isDefended;
	public Piece defender;
	
	public boolean isAttackable;
	public Piece attacker;
	
	
	Piece(Player p, int x, int y){
		m_x = x;
		m_y = y;
		m_player = p;
		m_type = PieceType.DEFAULT;
		hasMoved = false;
		isChecking = false;
		isTaken = false;
		isPinned = false;		
		isDefended = false;
		canCastleLeft = false;
		canCastleRight = false;
		
	}
	
	//used only for tile pieces to fill out pieceBoard array
	Piece(){
		
	}
	
	
	public void drawChecker() {
		this.checkMoveable();
		int ynum = 1;
		for (int[] i : this.checkBoard) {
			for (int x : i) {
				System.out.print(" 0" + x);
			}
			System.out.print(" " + ynum++ + "\n\n");
		}
		System.out.println(" 1  2  3  4  5  6  7  8 ");
	}
	
	
	public void changePos(int x, int y) {
		if(hasMoved == false) {
			hasMoved = true;
		}
		m_x = x;
		m_y = y;
		this.checkMoveable();
	}
	
	//Will iterate over @boardCheck array to see if the piece can move
	public boolean canMove(int x, int y) {
		System.out.println("Trying to move to Position " + x + ":" + y);
		//this.checkMoveable();
		this.getPlayer().m_game.gameBoard.checkAll();
		int n = this.checkBoard[y-1][x-1];
		System.out.println("Checkboard number: " + n);
		
		if(this.m_player.m_colour != this.m_player.m_game.currentTurnColour) {
			System.out.println(this.m_player.m_colour + "can't move! It is currently " + this.m_player.m_game.currentTurnColour + "'s turn!");
			return false;
		}else if(this.isPinned == true) {
			System.out.println(this.getType() + " can't move! Currently pinned!");
			return false;
		}else if (n == 0) {
			System.out.println("Checkboard: Can't move!");
			return false;
		}else if(n == 1) {
			System.out.println("Checkboard: Can move!");
			return true;
		}else if(n == 2) {
			System.out.println("Checkboard: Can attack!");
			return false;
		}else if(n == 3) {
			System.out.println("Checkboard: Can Castle Left!");
			return true;
		}else if(n == 4) {
			System.out.println("Checkboard: Can Castle Right!");
			return true;
		}else if(n == 5) {
			System.out.println("Checkboard: Can't move over your own piece");
			return true;
		}else {
			System.out.println("Checkboard: move unknown!");
			return false;
		}
	}
	
	//Will check if a tile has an attackable piece on it/
	public boolean canAttack(int x, int y) {
		System.out.println("Trying to Attack Position " + x + ":" + y);
		this.checkMoveable();
		int n = this.checkBoard[y-1][x-1];
		System.out.println("Checkboard number: " + n);
		
		if(this.m_player.m_colour != this.m_player.m_game.currentTurnColour) {
			System.out.println(this.m_player.m_colour + "can't move! It is currently " + this.m_player.m_game.currentTurnColour + "'s turn!");
			return false;
		}else if (n == 2){	
			System.out.println("Checkboard: Can attack!");
			return true;
		}else {
			System.out.println("Checkboard: attack unknown!");
			return false;
		}
	}
	
	public abstract void checkMoveable();
	
	//Getters & Setters
	public PieceType getType(){
		return m_type;
	}
	
	public Player getPlayer() {
		return m_player;
	}


	@Override
	public int hashCode() {
		return Objects.hash(m_player, m_type, m_x, m_y);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Piece other = (Piece) obj;
		return Objects.equals(m_player, other.m_player) && m_type == other.m_type && m_x == other.m_x
				&& m_y == other.m_y;
	}

	public boolean isTileAttacked(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}
	
	public int getCheckBoardNum(int x , int y) {
		return this.checkBoard[y][x];
	}

	public boolean canEscape() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isTileDefendable(int x, int y) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canBlockAttacker() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean canTakeAttacker() {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
	
}
