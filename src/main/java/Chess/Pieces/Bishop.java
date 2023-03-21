package Chess.Pieces;

import Chess.Game.*;

public class Bishop extends Piece {

	public Bishop(Player p, int x, int y) {
		super(p,x,y);
		m_type = PieceType.BISHOP;
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
		
		for(int n = 1; n <= 7; n++) {//up right diagonal movement -y +x
			if(!((origin_x == 7 || origin_y == 0) || (origin_x + n > 7 || origin_y - n < 0))) {
				if(pBoard[origin_y - n][origin_x + n].getType() == PieceType.DEFAULT) {
					//System.out.println((origin_y - n) + ":" + (origin_x + n) + "= 1" );
					this.checkBoard[origin_y - n][origin_x + n] = 1;
				}else if(pBoard[origin_y - n][origin_x + n].getType() != PieceType.DEFAULT && pBoard[origin_y - n][origin_x + n].getPlayer().m_colour != pColour) {
					//System.out.println((origin_y - n) + ":" + (origin_x + n) + "= 2" );
					this.checkBoard[origin_y - n][origin_x + n] = 2;
					pBoard[origin_y - n][origin_x + n].isAttackable = true;
					pBoard[origin_y - n][origin_x + n].attacker = this;
					break;
				}else if(pBoard[origin_y - n][origin_x + n].getType() != PieceType.DEFAULT && pBoard[origin_y - n][origin_x + n].getPlayer().m_colour == pColour) {
					pBoard[origin_y - n][origin_x + n].isDefended = true;
					pBoard[origin_y - n][origin_x + n].defender = this;
					break;
				}else {
					break;
				}
			}else {
				break;
			}
		}
		for(int n = 1; n <= 7; n++) {//up left diagonal movement -y -x
			if(!((origin_x == 0 || origin_y == 0) || (origin_x - n < 0 || origin_y - n < 0))) {
				if(pBoard[origin_y - n][origin_x - n].getType() == PieceType.DEFAULT) {
					//System.out.println((origin_y - n) + ":" + (origin_x - n) + "= 1" );
					this.checkBoard[origin_y - n][origin_x - n] = 1;
				}else if(pBoard[origin_y - n][origin_x - n].getType() != PieceType.DEFAULT && pBoard[origin_y - n][origin_x - n].getPlayer().m_colour != pColour) {
					//System.out.println((origin_y - n) + ":" + (origin_x - n) + "= 2" );
					this.checkBoard[origin_y - n][origin_x - n] = 2;
					pBoard[origin_y - n][origin_x - n].isAttackable = true;
					pBoard[origin_y - n][origin_x - n].attacker = this;
					break;
				}else if(pBoard[origin_y - n][origin_x - n].getType() != PieceType.DEFAULT && pBoard[origin_y - n][origin_x - n].getPlayer().m_colour == pColour) {
					pBoard[origin_y - n][origin_x - n].isDefended = true;
					pBoard[origin_y - n][origin_x - n].defender = this;
					break;
				}else {
					break;
				}
			}else {
				break;
			}
		}
		for(int n = 1; n <= 7; n++) {//down right diagonal movement +y +x
			if(!((origin_x == 7 || origin_y == 7) || (origin_x + n > 7 || origin_y + n > 7))) {
				if(pBoard[origin_y + n][origin_x + n].getType() == PieceType.DEFAULT) {
					//System.out.println((origin_y + n) + ":" + (origin_x + n) + "= 1" + " n:" + n);
					this.checkBoard[origin_y + n][origin_x + n] = 1;
				}else if(pBoard[origin_y + n][origin_x + n].getType() != PieceType.DEFAULT && pBoard[origin_y + n][origin_x + n].getPlayer().m_colour != pColour) {
					//System.out.println((origin_y + n) + ":" + (origin_x + n) + "= 2" );
					this.checkBoard[origin_y + n][origin_x + n] = 2;
					pBoard[origin_y + n][origin_x + n].isAttackable = true;
					pBoard[origin_y + n][origin_x + n].attacker = this;
					break;
				}else if(pBoard[origin_y + n][origin_x + n].getType() != PieceType.DEFAULT && pBoard[origin_y + n][origin_x + n].getPlayer().m_colour == pColour) {
					pBoard[origin_y + n][origin_x + n].isDefended = true;
					pBoard[origin_y + n][origin_x + n].defender = this;
					break;
				}else {
					break;
				}
			}else {
				break;
			}
		}
		for(int n = 1; n <= 7; n++) {//down left diagonal movement +y -x
			if(!((origin_x == 0 || origin_y == 7) || (origin_x - n < 0 || origin_y + n > 7))) {
				if(pBoard[origin_y + n][origin_x - n].getType() == PieceType.DEFAULT) {
					//System.out.println((origin_y + n) + ":" + (origin_x - n) + "= 1" );
					this.checkBoard[origin_y + n][origin_x - n] = 1;
				}else if(pBoard[origin_y + n][origin_x - n].getType() != PieceType.DEFAULT && pBoard[origin_y + n][origin_x - n].getPlayer().m_colour != pColour) {
					//System.out.println((origin_y + n) + ":" + (origin_x - n) + "= 2" );
					this.checkBoard[origin_y + n][origin_x - n] = 2;
					pBoard[origin_y + n][origin_x - n].isAttackable = true;
					pBoard[origin_y + n][origin_x - n].attacker = this;
					break;
				}else if(pBoard[origin_y + n][origin_x - n].getType() != PieceType.DEFAULT && pBoard[origin_y + n][origin_x - n].getPlayer().m_colour == pColour) {
					pBoard[origin_y + n][origin_x - n].isDefended = true;
					pBoard[origin_y + n][origin_x - n].defender = this;
					break;
				}else {
					break;
				}
			}else {
				break;
			}
		}
	}

}
