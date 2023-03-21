package Chess.Pieces;

import Chess.Game.*;

public class King extends Piece {

	public King(Player p, int x, int y) {
		super(p,x,y);
		m_type = PieceType.KING;
		m_player.m_game.gameBoard.setPiece(x, y, this);
		hasMoved = false;
		canCastleLeft = false;
		canCastleRight = false;
		hasBeenChecked = false;
		
	}

	@Override
	//TODO fix problem with pawns not being recognised : maybe fixed
	public boolean isTileAttacked(int x, int y) {//Unique to King, this checks for potential attackers at desired destination and denies move if there are attackers.
		//System.out.println("Tiles attacking around the king are being checked.");
		//Piece[] enemyPieces = (this.m_player.equals(this.m_player.m_game.playerW)) ? this.m_player.m_game.playerB.getPieces() : this.m_player.m_game.playerW.getPieces();
		boolean isAttacked = false;
		
		Piece[][] pieces = this.getPlayer().m_game.gameBoard.getPieceBoard();
		for(Piece[] row: pieces) {
			for(Piece p: row) {
				if(p.getType() != null && p.getPlayer() != null) {
					if (p.isTaken != true && p.m_player.m_colour != this.m_player.m_colour) {
						int o_x = p.m_x - 1;
						int o_y = p.m_y - 1;
						//System.out.println(p.getType().toString() + " has been checked at " + (x+1) +"|" + (y+1));
						if(p.getType() == PieceType.PAWN) {
							if(p.getPlayer().m_colour == PlayerColour.WHITE) {
								if((o_x+1 == x && o_y-1 == y)||(o_x-1 == x && o_y-1 == y)) {
									System.out.println(p.getType().toString() + " is attacking " + (x+1) + "|" + (y+1) );
									isAttacked = true;
									break;
								}
							}else if(p.getPlayer().m_colour == PlayerColour.BLACK) {
								if((o_x+1 == x && o_y+1 == y)||(o_x-1 == x && o_y+1 == y)){
									System.out.println(p.getType().toString() + " is attacking " + (x+1) + "|" + (y+1) );
									isAttacked = true;
									break;
								}
							}
							
						}else if(p.checkBoard[y][x] == 1){
							System.out.println(p.getType().toString() + " is attacking " + (x+1) + "|" + (y+1) );
							isAttacked = true;
							break;
						}
					}
				}
			}
		}
		return isAttacked;
	}

	@Override
	public boolean isTileDefendable(int x, int y) {//Unique to King, this checks for potential attackers at desired destination and denies move if there are attackers.
		//System.out.println("Tiles attacking around the king are being checked.");
		Piece[] friendlyPieces = (this.m_player.equals(this.m_player.m_game.playerB)) ? this.m_player.m_game.playerB.getPieces() : this.m_player.m_game.playerW.getPieces();
		boolean isDefendable = false;
		for(Piece p: friendlyPieces) {
			if (p.isTaken != true && p.getType() != PieceType.KING) {
				//System.out.println(p.getType().toString() + " has been checked at " + (x+1) +"|" + (y+1));
				if(p.checkBoard[y][x] == 1){
					System.out.println(p.getType().toString() + " is defending " + (x+1) + "|" + (y+1) );
					isDefendable = true;
					break;
				}
			}
		}
		return isDefendable;
	}
	
	@Override
	public boolean canEscape() {
		Piece[][] pBoard = m_player.m_game.gameBoard.getPieceBoard();
		for (int y = 0; y < 8; y++) {
			for(int x = 0; x < 8; x++) {
				if(this.checkBoard[y][x] == 1) {
					if(!this.isTileAttacked(x, y)) {
						System.out.println("King can escape position by moving to " +(x+1)+":"+(y+1)+" !");
						return true;
					}
				}else if(this.checkBoard[y][x] == 2) {
					if(pBoard[y][x].isDefended == false) {
						System.out.println("King can escape position by attacking "+(x+1)+":"+(y+1)+" !");
						return true;
					}
				}
			}
		}
		System.out.println("King can't escape position!");
		return false;
	}
	
	@Override
	public boolean canBlockAttacker(){
		if(!this.isAttackable) {
			System.out.println("--- canBlockAttacker has been activated without an attacker present");
			return false;
		}
		int ax = this.attacker.m_x - 1;
		int ay = this.attacker.m_y - 1;
		
		int ox = this.m_x - 1;
		int oy = this.m_y - 1;
		
		Piece[][] pBoard = m_player.m_game.gameBoard.getPieceBoard();
		
		if(attacker.getType() == PieceType.KNIGHT || attacker.getType() == PieceType.PAWN || attacker.getType() == PieceType.KING ) {
			return false;
		}else if(ay == oy && ax > ox) {//attacking from right side(rook style)
			for (int x = ax - 1; x >= 0; x--) {//going left from attacker
				if(pBoard[ay][x].getType() == PieceType.DEFAULT) {
					//System.out.println(pBoard[origin_y][x].getType().toString());
					if(this.isTileDefendable(x, ay)) {
						return true;
					}
				}else {
					break;
				}
			}
			
		}else if(ay == oy && ax < ox) {//attacking from left side(rook style)
			for (int x = ax + 1; x <= 7; x++) {//going right from attacker
				if(pBoard[ay][x].getType() == PieceType.DEFAULT) {
					//System.out.println(pBoard[origin_y][x].getType().toString());
					if(this.isTileDefendable(x, ay)) {
						return true;
					}
				}else {
					break;
				}
			}
		}else if(ay > oy && ax == ox) {//attacking from below(rook style)
			for (int y = ay - 1; y >= 0; y--) {//going up from attacker
				if(pBoard[y][ax].getType() == PieceType.DEFAULT) {
					//System.out.println(pBoard[y][origin_x].getType().toString());
					if(this.isTileDefendable(ax, y)) {
						return true;
					}
				}else {
					break;
				}
			}
		}else if(ay < oy && ax == ox) {//attacking from above side(rook style)
			for (int y = ay + 1; y <= 7; y++) {//going down from attacker
				if(pBoard[y][ax].getType() == PieceType.DEFAULT) {
					//System.out.println(pBoard[y][origin_x].getType().toString());
					if(this.isTileDefendable(ax, y)) {
						return true;
					}
				}else {
					break;
				}
			}
		}else if(ay < oy && ax < ox) {//attacking from top-left diagonal (bishop style)
			for(int n = 1; n <= 7; n++) {//down right diagonal movement +y +x from attacker
				if(!((ax == 7 || ay == 7) || (ax + n > 7 || ay + n > 7))) {
					if(pBoard[ay + n][ax + n].getType() == PieceType.DEFAULT) {
						//System.out.println((origin_y + n) + ":" + (origin_x + n) + "= 1" + " n:" + n);
						if(this.isTileDefendable(ax + n, ay + n)) {
							return true;
						}
					}else {
						break;
					}
				}else {
					break;
				}
			}
		}else if(ay < oy && ax > ox) {//attacking from top-right diagonal (bishop style)
			for(int n = 1; n <= 7; n++) {//down left diagonal movement +y -x from attacker
				if(!((ax == 0 || ay == 7) || (ax - n < 0 || ay + n > 7))) {
					if(pBoard[ay + n][ax - n].getType() == PieceType.DEFAULT) {
						//System.out.println((origin_y + n) + ":" + (origin_x - n) + "= 1" );
						if(this.isTileDefendable(ax - n, ay + n)) {
							return true;
						}
					}else {
						break;
					}
				}else {
					break;
				}
			}
		}else if(ay > oy && ax < ox) {//attacking from bottom-left diagonal (bishop style)
			for(int n = 1; n <= 7; n++) {//up right diagonal movement -y +x from attacker
				if(!((ax == 7 || ay == 0) || (ax + n > 7 || ay - n < 0))) {
					if(pBoard[ay - n][ax + n].getType() == PieceType.DEFAULT) {
						//System.out.println((origin_y - n) + ":" + (origin_x + n) + "= 1" );
						if(this.isTileDefendable(ax + n, ay - n)) {
							return true;
						}
					}else {
						break;
					}
				}else {
					break;
				}
			}
		}else if(ay > oy && ax > ox) {//attacking from bottom-right diagonal (bishop style)
			for(int n = 1; n <= 7; n++) {//up left diagonal movement -y -x from attacker
				if(!((ax == 0 || ay == 0) || (ax - n < 0 || ay - n < 0))) {
					if(pBoard[ay - n][ax - n].getType() == PieceType.DEFAULT) {
						//System.out.println((origin_y - n) + ":" + (origin_x - n) + "= 1" );
						this.checkBoard[ay - n][ax - n] = 1;
						if(this.isTileDefendable(ax - n, ay - n)) {
							return true;
						}
					}else {
						break;
					}
				}else {
					break;
				}
			}
		}else {
			System.out.println("!!! Unable to find attacker Direction !!!");
			return false;
		}
		System.out.println("!!! Something failed within the canBlockAttacker function !!!");
		return false;
	}
	
	@Override
	public boolean canTakeAttacker() {
		Board gBoard = m_player.m_game.gameBoard;
		
		Piece checkingPiece = this.attacker;
		
		if(checkingPiece.isAttackable) {//TODO: Fixed potential problem with Promoted Pieces
			Piece defendingPiece = this.attacker.attacker;
			if(defendingPiece.isAttackable) {
				if(gBoard.moveCausesCheck(checkingPiece.m_x,checkingPiece.m_y, defendingPiece.m_x, defendingPiece.m_y)) {
					return false;
				}else {
					return true;
				}
			}else {
				return true;
			}
		}else {
			return false;
		}
	}
	
	public boolean canCastle() {
		
		return true;
	}
	
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
		
		//King Movement
		//vertical moves
		if(!(origin_y - 1 < 0)) {//up y-1/x
			int y = origin_y - 1;
			int x = origin_x;
			
				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					
					this.checkBoard[y][x] = 1;

					
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		if(!(origin_y + 1 > 7)) {//down y+1/x
			int y = origin_y + 1;
			int x = origin_x;
	
				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					this.checkBoard[y][x] = 1;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		
		//Horizontal Moves
		if(!(origin_x + 1  > 7)) {//right y/x+1
			int y = origin_y;
			int x = origin_x + 1;
	
				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					this.checkBoard[y][x] = 1;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		if(!(origin_x - 1  < 0)) {//left y/x-1
			int y = origin_y;
			int x = origin_x - 1;
	
				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					this.checkBoard[y][x] = 1;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		
		//Bottom Left to Top Right Diagonal Moves
		if(!(origin_y - 1 < 0 || origin_x + 1 > 7)) {//Top right y-1/x+1
			int y = origin_y - 1;
			int x = origin_x + 1;

				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					this.checkBoard[y][x] = 1;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		if(!(origin_y + 1 > 7 || origin_x - 1 < 0)) {//Bottom Left y+1/x-1
			int y = origin_y + 1;
			int x = origin_x - 1;

				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					this.checkBoard[y][x] = 1;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		
		//Bottom Right to Top Left Diagonal Moves
		if(!(origin_y + 1 > 7 || origin_x + 1 > 7)) {//Top right y+1/x+1
			int y = origin_y + 1;
			int x = origin_x + 1;
	
				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					this.checkBoard[y][x] = 1;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		if(!(origin_y - 1 < 0 || origin_x - 1 < 0)) {//Bottom Left y-1/x-1
			int y = origin_y - 1;
			int x = origin_x - 1;

				if(pBoard[y][x].getType() == PieceType.DEFAULT) {
					//System.out.println();
					this.checkBoard[y][x] = 1;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour != pColour && pBoard[y][x].isDefended == false) {
					//System.out.println();
					this.checkBoard[y][x] = 2;
					pBoard[y][x].isAttackable = true;
					pBoard[y][x].attacker = this;
				}else if(pBoard[y][x].getType() != PieceType.DEFAULT && pBoard[y][x].getPlayer().m_colour == pColour) {
					//System.out.println();
					pBoard[y][x].isDefended = true;
					pBoard[y][x].defender = this;
				}
			
		}
		
		//Checking for Castle
		if(this.hasBeenChecked == false && this.hasMoved == false) {
			for (int x = origin_x - 1; x >= 0; x--) {//going left
				if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour == pColour && pBoard[origin_y][x].getType() == PieceType.ROOK ) {
					if(pBoard[origin_y][x].hasMoved==false) {
						this.checkBoard[origin_y][x] = 3;
						this.canCastleLeft = true;
					}
					
				}else if(pBoard[origin_y][x].getType() == PieceType.DEFAULT) {
					if(isTileAttacked(x,origin_y)) {
						this.canCastleLeft = false;
						break;
					}else {
						continue;
					}
					
				}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour != pColour) {
					//System.out.println(pBoard[origin_y][x].getType().toString());
					this.canCastleLeft = false;
					break;
				}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour == pColour) {
					//System.out.println(pBoard[origin_y][x].getType().toString());
					this.canCastleLeft = false;
					break;
				}else {
					break;
				}
			}
			for (int x = origin_x + 1; x <= 7; x++) {//going right
				if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour == pColour && pBoard[origin_y][x].getType() == PieceType.ROOK ) {
					if(pBoard[origin_y][x].hasMoved==false) {
						this.checkBoard[origin_y][x] = 4;
						this.canCastleRight = true;
					}
					
				}else if(pBoard[origin_y][x].getType() == PieceType.DEFAULT) {
					if(isTileAttacked(x,origin_y)) {
						this.canCastleRight = false;
						break;
					}else {
						continue;
					}
					
				}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour != pColour) {
					//System.out.println(pBoard[origin_y][x].getType().toString());
					canCastleRight = false;
					break;
				}else if(pBoard[origin_y][x].getType() != PieceType.DEFAULT && pBoard[origin_y][x].getPlayer().m_colour == pColour) {
					//System.out.println(pBoard[origin_y][x].getType().toString());
					canCastleRight = false;
					break;
				}else {
					break;
				}
			}
		}
				
	}

}
