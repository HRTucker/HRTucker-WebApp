package Chess.Pieces;

import Chess.Game.*;

public class Rook extends Piece {

	public Rook(Player p, int x, int y) {
		super(p,x,y);
		m_type = PieceType.ROOK;
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
		
		for (int y = origin_y - 1; y >= 0; y--) {//going up
			if(pBoard[y][origin_x].getType() == PieceType.DEFAULT) {
				//System.out.println(pBoard[y][origin_x].getType().toString());
				this.checkBoard[y][origin_x] = 1;
			}else if(pBoard[y][origin_x].getType() != PieceType.DEFAULT && pBoard[y][origin_x].getPlayer().m_colour != pColour) {
				//System.out.println(pBoard[y][origin_x].getType().toString());
				this.checkBoard[y][origin_x] = 2;
				pBoard[y][origin_x].isAttackable = true;
				pBoard[y][origin_x].attacker = this;
				break;
			}else if(pBoard[y][origin_x].getType() != PieceType.DEFAULT && pBoard[y][origin_x].getPlayer().m_colour == pColour) {
				pBoard[y][origin_x].isDefended = true;
				pBoard[y][origin_x].defender = this;
				break;
			}else {
				break;
			}
		}
		for (int y = origin_y + 1; y <= 7; y++) {//going down
			if(pBoard[y][origin_x].getType() == PieceType.DEFAULT) {
				//System.out.println(pBoard[y][origin_x].getType().toString());
				this.checkBoard[y][origin_x] = 1;
			}else if(pBoard[y][origin_x].getType() != PieceType.DEFAULT && pBoard[y][origin_x].getPlayer().m_colour != pColour) {
				//System.out.println(pBoard[y][origin_x].getType().toString());
				this.checkBoard[y][origin_x] = 2;
				pBoard[y][origin_x].isAttackable = true;
				pBoard[y][origin_x].attacker = this;
				break;
			}else if(pBoard[y][origin_x].getType() != PieceType.DEFAULT && pBoard[y][origin_x].getPlayer().m_colour == pColour) {
				pBoard[y][origin_x].isDefended = true;
				pBoard[y][origin_x].defender = this;
			}else {
				break;
			}
		}
		for (int x = origin_x - 1; x >= 0; x--) {//going left
			if(pBoard[origin_y][x].getType() == PieceType.DEFAULT) {
				//System.out.println(pBoard[origin_y][x].getType().toString());
				this.checkBoard[origin_y][x] = 1;
			}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour != pColour) {
				//System.out.println(pBoard[origin_y][x].getType().toString());
				this.checkBoard[origin_y][x] = 2;
				pBoard[origin_y][x].isAttackable = true;
				pBoard[origin_y][x].attacker = this;
				break;
			}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour == pColour) {
				pBoard[origin_y][x].isDefended = true;
				pBoard[origin_y][x].defender = this;
				break;
			}else {
				break;
			}
		}
		for (int x = origin_x + 1; x <= 7; x++) {//going right
			if(pBoard[origin_y][x].getType() == PieceType.DEFAULT) {
				//System.out.println(pBoard[origin_y][x].getType().toString());
				this.checkBoard[origin_y][x] = 1;
			}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour != pColour) {
				//System.out.println(pBoard[origin_y][x].getType().toString());
				this.checkBoard[origin_y][x] = 2;
				pBoard[origin_y][x].isAttackable = true;
				pBoard[origin_y][x].attacker = this;
				break;
			}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour == pColour) {
				pBoard[origin_y][x].isDefended = true;
				pBoard[origin_y][x].defender = this;
				break;
			}else {
				break;
			}
		}
		
		
	}

}
