package Chess.Game;

import java.io.Serializable;

import Chess.Pieces.*;


public class Board implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String[][] drawingBoard;
	protected Piece[][] pieceBoard;
	
	protected Piece dPiece = new Tile();
	
	public Game m_game;
	
	Game tempGame;
	
	Board tempBoard;
	
	
	Board(Game game){
		
		m_game = game;
		
		//Board Setup
		drawingBoard = new String[8][8];		
		for (int i = 0; i < drawingBoard.length; i++){
	           for (int x = 0; x < drawingBoard[i].length; x++) {
	                drawingBoard[i][x] = "00";
	           }
	    }
		pieceBoard = new Piece[8][8];
		for (int i = 0; i < pieceBoard.length; i++){
	           for (int x = 0; x < pieceBoard[i].length; x++) {
	                pieceBoard[i][x] = dPiece;
	           }
	    }
	}
	
	//draws board
	public void draw() {
		//System.out.flush();
		int ynum = 1;
		for (String[] i : drawingBoard) {
			for (String x : i) {
				System.out.print(" " + x);
			}
			System.out.print(" " + ynum++ + "\n\n");
		}
		System.out.println(" 1  2  3  4  5  6  7  8 ");
	}
	
	//Tests board drawing by filling it with 5's
	public void fives() {
		for (int i = 0; i < drawingBoard.length; i++){
	           for (int x = 0; x < drawingBoard[i].length; x++) {
	        	   drawingBoard[i][x] = "55";
	           }     
	       }
	}
	
	//Changes desired grid location to specified value(Only used fort server-side board)
	public void changeTile(int x, int y, String c) {
		drawingBoard[y-1][x-1] = c;
		System.out.println("Position " + x + " | " + y + " is now : " + c);
	}
	
	//Retrieves desired position and Piece
	//stores position in the drawn board
	//then stores the piece in the Piece board for reference.
	public void setPiece(int x, int y, Piece p) {
		//String firstChar = p.m_type.toString();
		
		drawingBoard[y-1][x-1] = p.getPlayer().m_colour.toString().substring(0,1) + p.getType().toString().substring(0,1);	
		
		pieceBoard[y-1][x-1] = p;
	}
	
	//Returns PieceType as a string
	public String getPieceType(int x , int y) {
		return pieceBoard[y-1][x-1].getType().toString();
	}
	
	public Piece[][] getPieceBoard(){
		return pieceBoard;
	}
	
	//takes in XY of the piece to move and XY of the where they would like to move(tx/ty)
	//Swaps them around in the pieceBoard and their signatures in the gameBoard(DrawnBoard)
	public int movePiece(int origin_x, int origin_y, int dest_x, int dest_y) {
		Piece origin_piece = pieceBoard[origin_y-1][origin_x-1];//to move
		Piece dest_piece = pieceBoard[dest_y-1][dest_x-1];//to replace
		Piece default_tile = dPiece;//tile to replace the piece that has just moved
		System.out.println(origin_piece.getType());
		if(origin_piece.getPlayer() == null) {
			return 0;
		}else if(origin_piece.getPlayer().m_colour != m_game.currentTurnColour) {
			System.out.println(origin_piece.getPlayer().m_colour + "can't move! It is currently " + this.m_game.currentTurnColour + "'s turn!");
			return 0;
		}else {
			if(origin_piece.canMove(dest_x,dest_y)==true && origin_piece.hasBeenChecked != true && origin_piece.getType() == PieceType.KING && dest_piece.getType() == PieceType.ROOK && origin_piece.getPlayer().m_colour == dest_piece.getPlayer().m_colour) {
				System.out.println("Movement: Attempting Castling");
				if(origin_piece.getCheckBoardNum(dest_x-1, dest_y-1) == 3) {//for castling left
					pieceBoard[origin_y-1][origin_x-1] = default_tile;
					pieceBoard[dest_y-1][dest_x-1] = default_tile;
					pieceBoard[origin_y-1][origin_x-2] = dest_piece;
					pieceBoard[origin_y-1][origin_x-3] = origin_piece;
					
					drawingBoard[origin_y-1][origin_x-1] = "00";
					drawingBoard[dest_y-1][dest_x-1] = "00";
					drawingBoard[origin_y-1][origin_x-2] = dest_piece.getPlayer().m_colour.toString().substring(0,1) + dest_piece.getType().toString().substring(0,1);
					drawingBoard[origin_y-1][origin_x-3] = origin_piece.getPlayer().m_colour.toString().substring(0,1) + origin_piece.getType().toString().substring(0,1);
					
					dest_piece.changePos(origin_x-1, origin_y);
					origin_piece.changePos(origin_x-2, origin_y);
					
					System.out.println("Movement: Can castle!");
					System.out.println("Player " + origin_piece.getPlayer().m_colour.toString() + " castled their " + origin_piece.getType().toString());
					this.m_game.endTurn();
					return 3;
					
				}else if (origin_piece.getCheckBoardNum(dest_x-1, dest_y-1) == 4) {//For Castling Right
					pieceBoard[origin_y-1][origin_x-1] = default_tile;
					pieceBoard[dest_y-1][dest_x-1] = default_tile;
					pieceBoard[origin_y-1][origin_x] = dest_piece;
					pieceBoard[origin_y-1][origin_x+1] = origin_piece;
					
					drawingBoard[origin_y-1][origin_x-1] = "00";
					drawingBoard[dest_y-1][dest_x-1] = "00";
					drawingBoard[origin_y-1][origin_x] = dest_piece.getPlayer().m_colour.toString().substring(0,1) + dest_piece.getType().toString().substring(0,1);
					drawingBoard[origin_y-1][origin_x+1] = origin_piece.getPlayer().m_colour.toString().substring(0,1) + origin_piece.getType().toString().substring(0,1);

					dest_piece.changePos(origin_x+1, origin_y);
					origin_piece.changePos(origin_x+2, origin_y);
					
					System.out.println("Movement: Can castle!");
					System.out.println("Player " + origin_piece.getPlayer().m_colour.toString() + " castled their " + origin_piece.getType().toString());
					this.m_game.endTurn();
					return 4;
				}else {
					System.out.println("!!!SOMETHING WRONG WITH CASTLING");
					return 0;
				}
				
				
			}else if(origin_piece.canMove(dest_x,dest_y)==true) {
				boolean tempHasMoved = origin_piece.hasMoved;//stores the current hasMoved to ensure that piece like pawns, rooks and kings can still use their unique moves when a move failed.
				boolean temp_origin_hasBeenChecked = origin_piece.getPlayer().King.hasBeenChecked;
				
				System.out.println("Movement: Attempting Move");
				pieceBoard[origin_y-1][origin_x-1] = dest_piece;
				pieceBoard[dest_y-1][dest_x-1] = origin_piece;
				
				drawingBoard[origin_y-1][origin_x-1] = "00";
				drawingBoard[dest_y-1][dest_x-1] = origin_piece.getPlayer().m_colour.toString().substring(0,1) + origin_piece.getType().toString().substring(0,1);
				origin_piece.changePos(dest_x, dest_y);
			
				this.checkAll();//finally check to make sure the move doesn't self check
				
				if(this.isKingChecked(m_game.currentTurnColour) ) {
					System.out.println("That move causes a self check!");
					pieceBoard[origin_y-1][origin_x-1] = origin_piece;
					pieceBoard[dest_y-1][dest_x-1] = dest_piece;
					origin_piece.changePos(origin_x, origin_y);
					
					origin_piece.hasMoved = tempHasMoved;//ensures the hasMoved isn't accidentally changed.
					origin_piece.getPlayer().King.hasBeenChecked = temp_origin_hasBeenChecked;
					
					return 0;
				}else if(origin_piece.getType() == PieceType.PAWN && ((origin_piece.getPlayer().m_colour == PlayerColour.WHITE && origin_piece.m_y == 1)||(origin_piece.getPlayer().m_colour == PlayerColour.BLACK && origin_piece.m_y == 8))) {
					if(origin_piece.getPlayer().m_colour == PlayerColour.WHITE && origin_piece.m_y == 1) {
						return 5;
					}else if(origin_piece.getPlayer().m_colour == PlayerColour.BLACK && origin_piece.m_y == 8) {
						return 5;
					}else {
						System.out.println("!!!SOMETHING WRONG WITH PAWN PROMOTION");
						return 0;
					}
				}else {
					System.out.println("Movement: Can move!");
					System.out.println("Player " + origin_piece.getPlayer().m_colour.toString() + " moved their " + origin_piece.getType().toString() + " to " + dest_x + " : " + dest_y );
					this.m_game.endTurn();
					return 1;
				}
			}else if(origin_piece.canAttack(dest_x,dest_y)==true) {
				boolean tempHasMoved = origin_piece.hasMoved;
				boolean temp_origin_hasBeenChecked = origin_piece.getPlayer().King.hasBeenChecked;
				boolean temp_dest_hasBeenChecked = dest_piece.getPlayer().King.hasBeenChecked;
				
				System.out.println("Movement: Attempting Attack");
				pieceBoard[origin_y-1][origin_x-1] = default_tile;
				pieceBoard[dest_y-1][dest_x-1] = origin_piece;
				
				drawingBoard[origin_y-1][origin_x-1] = "00";
				drawingBoard[dest_y-1][dest_x-1] = origin_piece.getPlayer().m_colour.toString().substring(0,1) + origin_piece.getType().toString().substring(0,1);
				origin_piece.changePos(dest_x, dest_y);
				
				this.checkAll();//finally check to make sure the move doesn't self check
				
				if(this.isKingChecked(m_game.currentTurnColour)) {
					System.out.println("That move causes a self check!");
					pieceBoard[origin_y-1][origin_x-1] = origin_piece;
					pieceBoard[dest_y-1][dest_x-1] = dest_piece;
					origin_piece.changePos(origin_x, origin_y);
					
					origin_piece.hasMoved = tempHasMoved;
					origin_piece.getPlayer().King.hasBeenChecked = temp_origin_hasBeenChecked;
					dest_piece.getPlayer().King.hasBeenChecked = temp_dest_hasBeenChecked;
					
					return 0;
				}else if(origin_piece.getType() == PieceType.PAWN && ((origin_piece.getPlayer().m_colour == PlayerColour.WHITE && origin_piece.m_y == 1)||(origin_piece.getPlayer().m_colour == PlayerColour.BLACK && origin_piece.m_y == 8))) {
					if(origin_piece.getPlayer().m_colour == PlayerColour.WHITE && origin_piece.m_y == 1) {
						dest_piece.isTaken = true;
						return 6;
					}else if(origin_piece.getPlayer().m_colour == PlayerColour.BLACK && origin_piece.m_y == 8) {
						dest_piece.isTaken = true;
						return 6;
					}else {
						System.out.println("!!!SOMETHING WRONG WITH PAWN PROMOTION");
						return 0;
					}
				}else {
					System.out.println("Movement: Can attack!");
					System.out.println("Player " + origin_piece.getPlayer().m_colour.toString() + " moved their " + origin_piece.getType().toString() + " to " + dest_x + " : " + dest_y + " taking Player " + dest_piece.getPlayer().m_colour.toString() + "'s " + dest_piece.getType().toString());
					dest_piece.isTaken = true;
					this.m_game.endTurn();
					return 2;
				}
			}else {
				System.out.println("Can't move there!");
				return 0;
			}
		}
	}
	
	public void checkAll() {
		/*for(Piece p : this.m_game.playerW.getPieces()) {
			p.isPinned = false;
			p.checkMoveable();
		}
		for(Piece p : this.m_game.playerB.getPieces()) {
			p.isPinned = false;
			p.checkMoveable();
		}*/
		
		for(Piece[] i: pieceBoard) {
			for (Piece p : i) {
				if (p.getPlayer() != null) {
					p.isPinned = false;
					p.defender = null;
					p.isDefended = false;
					p.attacker = null;
					p.isAttackable = false;
					p.canCastleLeft = false;
					p.canCastleRight = false;
				}
			}
		}
		for(Piece[] i: pieceBoard) {
			for (Piece p : i) {
				if (p.getPlayer() != null) {
					p.checkMoveable();
				}
			}
		}
	}
	
	public void checkBlack() {
			for(Piece p : this.m_game.playerB.getPieces()) {
				p.isPinned = false;
				p.checkMoveable();
			}
	}
	public void checkWhite() {
			for(Piece p : this.m_game.playerW.getPieces()) {
				p.isPinned = false;
				p.checkMoveable();
			}
	}
	
	public boolean isKingChecked(PlayerColour colour) {
		if(colour == PlayerColour.WHITE && m_game.playerW.King.isAttackable) {
			/*
			this.checkAll();
			if(m_game.playerB.King.canEscape() == false && m_game.playerB.King.canBlockAttacker() == false && m_game.playerB.King.canTakeAttacker() == false) {
				System.out.println("CHECKMATE! BLACK WINS!");
			}
			*/
			//System.out.println(" --- White is able to move out of check: " + m_game.playerW.King.canEscape());
			//System.out.println(" --- White is able to block the check: " + m_game.playerW.King.canBlockAttacker());
			//System.out.println(" --- White is able to take the checking piece: " + m_game.playerW.King.canTakeAttacker());
			return true;
		}else if(colour == PlayerColour.BLACK && m_game.playerB.King.isAttackable) {
			/*
			this.checkAll();
			if(m_game.playerB.King.canEscape() == false && m_game.playerB.King.canBlockAttacker() == false && m_game.playerB.King.canTakeAttacker() == false) {
				System.out.println("CHECKMATE! WHITE WINS!");
			}
			*/
			//System.out.println(" --- Black is able to move out of check: " + m_game.playerB.King.canEscape());
			//System.out.println(" --- Black is able to block the check: " + m_game.playerB.King.canBlockAttacker());
			//System.out.println(" --- Black is able to take the checking piece: " + m_game.playerB.King.canTakeAttacker());
			return true;
		}else {
			System.out.println("Unknown King Colour or neither side is in check");
			return false;
		}
	}
	
	public boolean isCheckmate(PlayerColour colour) {
		if(isKingChecked(colour)) {
			this.checkAll();
			if(colour == PlayerColour.BLACK) {
				if(m_game.playerB.King.canEscape() == false && m_game.playerB.King.canBlockAttacker() == false && m_game.playerB.King.canTakeAttacker() == false) {
					System.out.println("CHECKMATE! WHITE WINS!");
					
					return true;
				}else {
					System.out.println("Not checkmate!");
					return false;
				}
			}else if(colour == PlayerColour.WHITE) {
				if(m_game.playerW.King.canEscape() == false && m_game.playerW.King.canBlockAttacker() == false && m_game.playerW.King.canTakeAttacker() == false) {
					System.out.println("CHECKMATE! BLACK WINS!");
					
					return true;
				}else {
					System.out.println("Not checkmate!");
					return false;
				}
			}else {
				System.out.println("Unknown King");
				return false;
			}
		}
		return false;
	}
	
	public boolean moveCausesCheck(int origin_x, int origin_y, int dest_x, int dest_y) {
		System.out.println("??? Checking for Self Check statements ???");
		Piece origin_piece = pieceBoard[origin_y-1][origin_x-1];//to move
		Piece dest_piece = pieceBoard[dest_y-1][dest_x-1];//to replace
		Piece default_tile = dPiece;//tile to replace the piece that has just moved
		
		boolean tempHasMoved = origin_piece.hasMoved;
		boolean temp_origin_hasBeenChecked = origin_piece.getPlayer().King.hasBeenChecked;
		boolean temp_dest_hasBeenChecked = dest_piece.getPlayer().King.hasBeenChecked;
		
		pieceBoard[origin_y-1][origin_x-1] = default_tile;
		pieceBoard[dest_y-1][dest_x-1] = origin_piece;
		
		drawingBoard[origin_y-1][origin_x-1] = "00";
		drawingBoard[dest_y-1][dest_x-1] = origin_piece.getPlayer().m_colour.toString().substring(0,1) + origin_piece.getType().toString().substring(0,1);
		origin_piece.changePos(dest_x, dest_y);
		
		this.checkAll();//finally check to make sure the move doesn't self check
		
		if(this.isKingChecked(m_game.currentTurnColour)) {
			System.out.println("- moveCausesCheck() : Move causes a self check");
			pieceBoard[origin_y-1][origin_x-1] = origin_piece;
			pieceBoard[dest_y-1][dest_x-1] = dest_piece;
			origin_piece.changePos(origin_x, origin_y);
			
			origin_piece.hasMoved = tempHasMoved;
			origin_piece.getPlayer().King.hasBeenChecked = temp_origin_hasBeenChecked;
			dest_piece.getPlayer().King.hasBeenChecked = temp_dest_hasBeenChecked;
			
			System.out.println("??? END of Self Check statements ???");
			
			return true;
		}else {
			System.out.println("- moveCausesCheck() : Move doesn't cause check");
			pieceBoard[origin_y-1][origin_x-1] = origin_piece;
			pieceBoard[dest_y-1][dest_x-1] = dest_piece;
			origin_piece.changePos(origin_x, origin_y);
			origin_piece.hasMoved = tempHasMoved;
			System.out.println("??? END of Self Check statements ???");
			return false;
		}
	}
	
public void promotePiece(PieceType newType, int x, int y) {
	Player player = this.pieceBoard[y-1][x-1].getPlayer();
	
	if(newType == PieceType.QUEEN) {
		this.pieceBoard[y-1][x-1] = new Queen(player, x, y);
		this.m_game.endTurn();
	}else if(newType == PieceType.BISHOP) {
		this.pieceBoard[y-1][x-1] = new Bishop(player, x, y);
		this.m_game.endTurn();
	}else if(newType == PieceType.KNIGHT) {
		this.pieceBoard[y-1][x-1] = new Knight(player, x, y);
		this.m_game.endTurn();
	}else if(newType == PieceType.ROOK) {
		this.pieceBoard[y-1][x-1] = new Rook(player, x, y);
		this.m_game.endTurn();
	}else {
		System.out.println("ERROR: Couldn't promote pawn!");
	}
}
	
	
	
	
	/*
	public void tempMove(int x, int y, int tx, int ty) {
		Piece ptm = pieceBoard[y-1][x-1];//to move
		Piece ptr = pieceBoard[ty-1][tx-1];//to replace
		Piece tile = dPiece;//tile to replace the piece that has just moved
		
		if(ptm.getPlayer().m_colour != m_game.currentTurnColour) {
			System.out.println(ptm.getPlayer().m_colour + "can't move! It is currently " + this.m_game.currentTurnColour + "'s turn!");
		}else {
			if(ptm.canMove(tx,ty)) {
				pieceBoard[y-1][x-1] = ptr;
				pieceBoard[ty-1][tx-1] = ptm;
				
				drawingBoard[y-1][x-1] = "00";
				drawingBoard[ty-1][tx-1] = ptm.getPlayer().m_colour.toString().substring(0,1) + ptm.getType().toString().substring(0,1);
				ptm.changePos(tx, ty);
			
				System.out.println("Player " + ptm.getPlayer().m_colour.toString() + " moved their " + ptm.getType().toString() + " to " + tx + " : " + ty );
			}else if(ptm.canAttack(tx,ty)) {
				pieceBoard[y-1][x-1] = tile;
				pieceBoard[ty-1][tx-1] = ptm;
				
				drawingBoard[y-1][x-1] = "00";
				drawingBoard[ty-1][tx-1] = ptm.getPlayer().m_colour.toString().substring(0,1) + ptm.getType().toString().substring(0,1);
				ptm.changePos(tx, ty);
			
				System.out.println("Player " + ptm.getPlayer().m_colour.toString() + " moved their " + ptm.getType().toString() + " to " + tx + " : " + ty + " taking Player " + ptr.getPlayer().m_colour.toString() + "'s " + ptr.getType().toString());
				ptr.isTaken = true;
			}else {
				System.out.println("Can't move there!");
			}
		}
	}
	
	public boolean moveCausesSelfCheck(int origin_x, int origin_y, int dest_x, int dest_y) {
		System.out.println("!!Checking TempBoard!!");
		tempBoard = m_game.gameBoard;
		tempGame = Game.copy(m_game);
		tempGame.gameBoard.tempMove(origin_x, origin_y, dest_x, dest_y);
		if(isKingChecked(tempGame.gameBoard.pieceBoard[dest_y-1][dest_x-1].getPlayer().m_colour)) {
			System.out.println("The move causes a self-check");
			return true;
		}else {
			return false;
		}
	}
	*/
}
