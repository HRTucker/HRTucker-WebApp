package Chess.Pieces;

import Chess.Game.Player;
import Chess.Game.PlayerColour;

public class Knight extends Piece {

	public Knight(Player p, int x, int y) {
		super(p,x,y);
		m_type = PieceType.KNIGHT;
		m_player.m_game.gameBoard.setPiece(x, y, this);
		hasMoved = false;
	}

	@Override
	public void checkMoveable() {
		
		PlayerColour pColour = m_player.m_colour;//Own players colour;
		
		Piece[][] pBoard = m_player.m_game.gameBoard.getPieceBoard();
		
		int origin_x = m_x - 1;
		int origin_y = m_y - 1;
		
		//resets the checking board to allow for new data to be passed
		for (int i = 0; i < this.checkBoard.length; i++){
			for (int x = 0; x < this.checkBoard[i].length; x++) {
				this.checkBoard[i][x] = 0;
			}
		}
		
		
		//up moves
		if(!(origin_y - 2 < 0 || origin_x + 1 > 7)) {//up right y-2/x+1
			int y = origin_y - 2;
			int x = origin_x + 1;
			if(pBoard[y][x].getType() == PieceType.DEFAULT) {
				//System.out.println();
				this.checkBoard[y][x] = 1;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
				//System.out.println();
				this.checkBoard[y][x] = 2;
				pBoard[y][x].isAttackable = true;
				pBoard[y][x].attacker = this;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
				pBoard[y][x].isDefended = true;
				pBoard[y][x].defender = this;
				
			}
		}
		if(!(origin_y - 2 < 0 || origin_x - 1 < 0)) {//up left y-2/x-1
			int y = origin_y - 2;
			int x = origin_x - 1;
			if(pBoard[y][x].getType() == PieceType.DEFAULT) {
				//System.out.println();
				this.checkBoard[y][x] = 1;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
				//System.out.println();
				this.checkBoard[y][x] = 2;
				pBoard[y][x].isAttackable = true;
				pBoard[y][x].attacker = this;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
				pBoard[y][x].isDefended = true;
				pBoard[y][x].defender = this;
			}
		}
		//down moves
		if(!(origin_y + 2 > 7 || origin_x + 1 > 7)) {//down right y+2/x+1
			int y = origin_y + 2;
			int x = origin_x + 1;
			if(pBoard[y][x].getType() == PieceType.DEFAULT) {
				//System.out.println();
				this.checkBoard[y][x] = 1;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
				//System.out.println();
				this.checkBoard[y][x] = 2;
				pBoard[y][x].isAttackable = true;
				pBoard[y][x].attacker = this;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
				pBoard[y][x].isDefended = true;
				pBoard[y][x].defender = this;
			}
		}
		if(!(origin_y + 2 > 7 || origin_x - 1 < 0)) {//down left y+2/x-1
			int y = origin_y + 2;
			int x = origin_x - 1;
			if(pBoard[y][x].getType() == PieceType.DEFAULT) {
				//System.out.println();
				this.checkBoard[y][x] = 1;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
				//System.out.println();
				this.checkBoard[y][x] = 2;
				pBoard[y][x].isAttackable = true;
				pBoard[y][x].attacker = this;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
				pBoard[y][x].isDefended = true;
				pBoard[y][x].defender = this;
			}
		}
		//right moves
		if(!(origin_y - 1 < 0 || origin_x + 2 > 7)) {//right up y-1/x+2
			int y = origin_y - 1;
			int x = origin_x + 2;
			if(pBoard[y][x].getType() == PieceType.DEFAULT) {
				//System.out.println();
				this.checkBoard[y][x] = 1;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
				//System.out.println();
				this.checkBoard[y][x] = 2;
				pBoard[y][x].isAttackable = true;
				pBoard[y][x].attacker = this;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
				pBoard[y][x].isDefended = true;
				pBoard[y][x].defender = this;
			}
		}
		if(!(origin_y + 1 > 7 || origin_x + 2 > 7)) {//right down y+1/x+2
			int y = origin_y + 1;
			int x = origin_x + 2;
			if(pBoard[y][x].getType() == PieceType.DEFAULT) {
				//System.out.println();
				this.checkBoard[y][x] = 1;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
				//System.out.println();
				this.checkBoard[y][x] = 2;
				pBoard[y][x].isAttackable = true;
				pBoard[y][x].attacker = this;
			}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
				pBoard[y][x].isDefended = true;
				pBoard[y][x].defender = this;
			}
		}
		//left moves
				if(!(origin_y - 1 < 0 || origin_x - 2 < 0)) {//left up y-1/x-2
					int y = origin_y - 1;
					int x = origin_x - 2;
					if(pBoard[y][x].getType() == PieceType.DEFAULT) {
						//System.out.println();
						this.checkBoard[y][x] = 1;
					}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
						//System.out.println();
						this.checkBoard[y][x] = 2;
						pBoard[y][x].isAttackable = true;
						pBoard[y][x].attacker = this;
					}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
						pBoard[y][x].isDefended = true;
						pBoard[y][x].defender = this;
					}
				}
				if(!(origin_y + 1 > 7 || origin_x - 2 < 0)) {//left down y+1/x-2
					int y = origin_y + 1;
					int x = origin_x - 2;
					if(pBoard[y][x].getType() == PieceType.DEFAULT) {
						//System.out.println();
						this.checkBoard[y][x] = 1;
					}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour) {
						//System.out.println();
						this.checkBoard[y][x] = 2;
						pBoard[y][x].isAttackable = true;
						pBoard[y][x].attacker = this;
					}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
						pBoard[y][x].isDefended = true;
						pBoard[y][x].defender = this;
					}
				}
	}

}
