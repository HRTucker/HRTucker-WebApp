package Chess.Pieces;
import Chess.Game.*;

public class Pawn extends Piece {
	
	public Pawn(Player p, int x, int y) {
		super(p,x,y);
		m_type = PieceType.PAWN;
		m_player.m_game.gameBoard.setPiece(x, y, this);
		hasMoved = false;
		isPinned = false;
		
	}
	
	//This will check for valid places for the pawn to move to
	@Override
	public void checkMoveable() {
		//System.out.println("TEST:" + this.getPlayer().m_colour.toString() + " " + this.getType().toString() + " has been checked");
		PlayerColour pColour = m_player.m_colour;
		
		Piece[][] pBoard = m_player.m_game.gameBoard.getPieceBoard();
		
		//resets the checking board to allow for new data to be passed
		for (int i = 0; i < this.checkBoard.length; i++){
	           for (int x = 0; x < this.checkBoard[i].length; x++) {
	                this.checkBoard[i][x] = 0;
	           }
		}
		
		//checks next moveable space and stores it in the checkboard. 01 represents free spaces and 02 represents attackable spaces
		if(!(this.isPinned == true)) {
			if(pColour == PlayerColour.BLACK && !(m_y-1 <= 0)) {//Black Piece
				if(pBoard[m_y][m_x-1].getType() == PieceType.DEFAULT && m_y != 8) {//Check single space move
					this.checkBoard[m_y][m_x-1] = 1;
				}
				if(hasMoved == false) {
					if(pBoard[m_y][m_x-1].getType() == PieceType.DEFAULT && pBoard[m_y+1][m_x-1].getType() == PieceType.DEFAULT) {//Check double space move && has moved bool.
						this.checkBoard[m_y+1][m_x-1] = 1;
					}
				}
				//move to attack(unique to pawn since it can only attack diagonally)
				if(m_x != 1 && m_x != 8) {
					if(pBoard[m_y][m_x-2].getPlayer() != null) {
						if(pBoard[m_y][m_x-2].getPlayer().m_colour != pColour && m_x != 1) {//left attack
							this.checkBoard[m_y][m_x-2] = 2;
							pBoard[m_y][m_x-2].isAttackable = true;
							pBoard[m_y][m_x-2].attacker = this;
							//System.out.println("a");
						}else if(pBoard[m_y][m_x-2].getPlayer().m_colour == pColour && m_x != 1) {
							pBoard[m_y][m_x-2].isDefended = true;
							pBoard[m_y][m_x-2].defender = this;
						}
					}
					if(pBoard[m_y][m_x].getPlayer() != null ) {
						if(pBoard[m_y][m_x].getPlayer().m_colour != pColour && m_x != 8) {//right attack
							this.checkBoard[m_y][m_x] = 2;
							pBoard[m_y][m_x].isAttackable = true;
							pBoard[m_y][m_x].attacker = this;
							//System.out.println("a");
						}else if(pBoard[m_y][m_x].getPlayer().m_colour == pColour && m_x != 8){
							pBoard[m_y][m_x].isDefended = true;
							pBoard[m_y][m_x].defender = this;
						}
					}
				}else if (m_x != 1){
					if(pBoard[m_y][m_x-2].getPlayer() != null) {
						if(pBoard[m_y][m_x-2].getPlayer().m_colour != pColour && m_x != 1) {//left attack if piece is on the far right side of the board
							this.checkBoard[m_y][m_x-2] = 2;
							pBoard[m_y][m_x-2].isAttackable = true;
							pBoard[m_y][m_x-2].attacker = this;
							//System.out.println("b");
						}else if(pBoard[m_y][m_x-2].getPlayer().m_colour == pColour && m_x != 1) {
							pBoard[m_y][m_x-2].isDefended = true;
							pBoard[m_y][m_x-2].defender = this;
						}
					}
				}else if(m_x != 8) {
					if(pBoard[m_y][m_x].getPlayer() != null) {
						if(pBoard[m_y][m_x].getPlayer().m_colour != pColour && m_x != 8) {//right attack if the piece is on the far left side of the board
							this.checkBoard[m_y][m_x] = 2;
							pBoard[m_y][m_x].isAttackable = true;
							pBoard[m_y][m_x].attacker = this;
							//System.out.println("c");
						}else if(pBoard[m_y][m_x].getPlayer().m_colour == pColour && m_x != 8) {
							pBoard[m_y][m_x].isDefended = true;
							pBoard[m_y][m_x].defender = this;
						}
					}
				}
			}else if (pColour == PlayerColour.WHITE &&!(m_y-1 <= 0)){//White Piece
				
					if(pBoard[m_y-2][m_x-1].getType() == PieceType.DEFAULT && m_y != 1) {//Check single space move
						this.checkBoard[m_y-2][m_x-1] = 1;
					}
					if(hasMoved == false) {
						if(pBoard[m_y-2][m_x-1].getType() == PieceType.DEFAULT && pBoard[m_y-3][m_x-1].getType() == PieceType.DEFAULT) {//Check double space move && has moved bool.
							this.checkBoard[m_y-3][m_x-1] = 1;
						}
					}
				
				//move to attack(unique to pawn since it can only attack diagonally)
				if(m_x != 1 && m_x != 8) {
					if(pBoard[m_y-2][m_x-2].getPlayer() != null) {
						if(pBoard[m_y-2][m_x-2].getPlayer().m_colour != pColour && m_x != 1) {//left attack
							this.checkBoard[m_y-2][m_x-2] = 2;
							pBoard[m_y-2][m_x-2].isAttackable = true;
							pBoard[m_y-2][m_x-2].attacker = this;
							//System.out.println("a");
						}else if(pBoard[m_y-2][m_x-2].getPlayer().m_colour == pColour && m_x != 1) {
							pBoard[m_y-2][m_x-2].isDefended = true;
							pBoard[m_y-2][m_x-2].defender = this;
						}
					}
					if(pBoard[m_y-2][m_x].getPlayer() != null) {
						if(pBoard[m_y-2][m_x].getPlayer().m_colour != pColour && m_x != 8) {//right attack
							this.checkBoard[m_y-2][m_x] = 2;
							pBoard[m_y-2][m_x].isAttackable = true;
							pBoard[m_y-2][m_x].attacker = this;
							//System.out.println("a");
						}else if(pBoard[m_y-2][m_x].getPlayer().m_colour == pColour && m_x != 8) {
							pBoard[m_y-2][m_x].isDefended = true;
							pBoard[m_y-2][m_x].defender = this;
						}
					}
				}else if (m_x != 1){
					if(pBoard[m_y-2][m_x-2].getPlayer() != null) {
						if(pBoard[m_y-2][m_x-2].getPlayer().m_colour != pColour && m_x != 1) {//left attack if piece is on the far right side of the board
							this.checkBoard[m_y-2][m_x-2] = 2;
							pBoard[m_y-2][m_x-2].isAttackable = true;
							pBoard[m_y-2][m_x-2].attacker = this;
							//System.out.println("b");
						}else if(pBoard[m_y-2][m_x-2].getPlayer().m_colour == pColour && m_x != 1) {
							pBoard[m_y-2][m_x-2].isDefended = true;
							pBoard[m_y-2][m_x-2].defender = this;
						}
					}
				}else if(m_x != 8) {
					if(pBoard[m_y-2][m_x].getPlayer() != null) {
						if(pBoard[m_y-2][m_x].getPlayer().m_colour != pColour && m_x != 8) {//right attack if the piece is on the far left side of the board
							this.checkBoard[m_y-2][m_x] = 2;
							pBoard[m_y-2][m_x].isAttackable = true;
							pBoard[m_y-2][m_x].attacker = this;
							//System.out.println("c");
						}else if(pBoard[m_y-2][m_x].getPlayer().m_colour == pColour && m_x != 8) {
							pBoard[m_y-2][m_x].isDefended = true;
							pBoard[m_y-2][m_x].defender = this;
						}
					}
				}
					
			}
			
		}
	}
	
	

}
