package Chess.Game;

import Chess.Game.ChessTimer;
import java.io.Serializable;
import java.util.Objects;


import Chess.Pieces.*;

public class Player implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public PlayerColour m_colour;
	public Game m_game;
	public ChessTimer timer;
	
	//Piece declaration
	Player player;
	
	Piece King;
	
	Piece Queen;
	
	Piece pawn1;
	Piece pawn2;
	Piece pawn3;
	Piece pawn4;
	Piece pawn5;
	Piece pawn6;
	Piece pawn7;
	Piece pawn8;
	
	Piece Rook1;
	Piece Rook2;
	
	Piece Bishop1;
	Piece Bishop2;
	
	Piece Knight1;
	Piece Knight2;
	
	//Promotion Pieces
	Piece pawn9;
	Piece pawn10;
	
	public boolean isChecked;
	
	Player(Game game, PlayerColour pc, int gameTime){
		
		timer = new ChessTimer(gameTime*60, this);
		
		
		m_game = game;
		m_colour = pc;
		player = this;
		isChecked = false;
		
		//if colour is "white" setup like this
		switch (m_colour) {
			case WHITE:
				King = new King(player,5,8);
				
				Queen = new Queen(player,4,8);
				
				Knight1 = new Knight(player,2,8);
				Knight2 = new Knight(player,7,8);
				
				Bishop1 = new Bishop(player,3,8);
				Bishop2 = new Bishop(player,6,8);
				
				Rook1 = new Rook(player,1,8);
				Rook2 = new Rook(player,8,8);
				
				pawn1 = new Pawn(player,1,7);
				pawn2 = new Pawn(player,2,7);
				pawn3 = new Pawn(player,3,7);
				pawn4 = new Pawn(player,4,7);
				pawn5 = new Pawn(player,5,7);
				pawn6 = new Pawn(player,6,7);
				pawn7 = new Pawn(player,7,7);
				pawn8 = new Pawn(player,8,7);
				
				//test pieces
				//pawn9 = new Pawn(player,6,3);
				//pawn10 = new Pawn(player,8,3);
				break;
			case BLACK:
				King = new King(player,5,1);
				
				Queen = new Queen(player,4,1);
				
				Knight1 = new Knight(player,7,1);
				Knight2 = new Knight(player,2,1);
				
				Bishop1 = new Bishop(player,6,1);
				Bishop2 = new Bishop(player,3,1);
				
				Rook1 = new Rook(player,8,1);
				Rook2 = new Rook(player,1,1);
				
				pawn1 = new Pawn(player,8,2);
				pawn2 = new Pawn(player,7,2);
				pawn3 = new Pawn(player,6,2);
				pawn4 = new Pawn(player,5,2);
				pawn5 = new Pawn(player,4,2);
				pawn6 = new Pawn(player,3,2);
				pawn7 = new Pawn(player,2,2);
				pawn8 = new Pawn(player,1,2);
				
				//test pieces
				//pawn9 = new Pawn(player,6,6);
				//pawn10 = new Pawn(player,8,6);
				break;
		}
	}
	
	public Piece[] getPieces() {
			
			Piece[] pieces = {King, Queen, Knight1, Knight2, Bishop1, Bishop2, Rook1, Rook2, pawn1, pawn2, pawn3, pawn4, pawn5, pawn6, pawn7, pawn8};
			return pieces;
	}

	@Override
	public int hashCode() {
		return Objects.hash(Bishop1, Bishop2, King, Knight1, Knight2, Queen, Rook1, Rook2, m_colour, m_game, pawn1,
				pawn10, pawn2, pawn3, pawn4, pawn5, pawn6, pawn7, pawn8, pawn9, player);
	}

	/*
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		return Objects.equals(Bishop1, other.Bishop1) && Objects.equals(Bishop2, other.Bishop2)
				&& Objects.equals(King, other.King) && Objects.equals(Knight1, other.Knight1)
				&& Objects.equals(Knight2, other.Knight2) && Objects.equals(Queen, other.Queen)
				&& Objects.equals(Rook1, other.Rook1) && Objects.equals(Rook2, other.Rook2)
				&& m_colour == other.m_colour && Objects.equals(m_game, other.m_game)
				&& Objects.equals(pawn1, other.pawn1) && Objects.equals(pawn10, other.pawn10)
				&& Objects.equals(pawn2, other.pawn2) && Objects.equals(pawn3, other.pawn3)
				&& Objects.equals(pawn4, other.pawn4) && Objects.equals(pawn5, other.pawn5)
				&& Objects.equals(pawn6, other.pawn6) && Objects.equals(pawn7, other.pawn7)
				&& Objects.equals(pawn8, other.pawn8) && Objects.equals(pawn9, other.pawn9)
				&& Objects.equals(player, other.player);
	}*/
}
