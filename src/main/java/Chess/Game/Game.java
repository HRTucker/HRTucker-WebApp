package Chess.Game;

import java.util.Scanner;
import java.io.*;

import org.json.*;
import org.json.JSONObject;

import Chess.Pieces.Piece;
import Chess.Game.*;

import SessionService.SessionService;

public class Game implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Board gameBoard = new Board(this);
	
	public Player playerW;
	public Player playerB;
	
	public SessionService SS;
	
	public PlayerColour currentTurnColour;
	
	public boolean gameEndStatus = false;
	public boolean checkmateStatus = false;
	public String checkColour;
	
	public Game(int gameTimer, SessionService Session){
		System.out.println("Chess game has begun!");
		playerW = new Player(this, PlayerColour.WHITE, gameTimer);
		playerB = new Player(this, PlayerColour.BLACK, gameTimer);
		
		this.SS = Session;
		
		gameBoard.checkAll();
		
		currentTurnColour = PlayerColour.WHITE;
		
		playerW.timer.start();
		/*
		while(true){
			
			Scanner scan = new Scanner(System.in);
			System.out.println("What would you like to do?");
			String a = scan.nextLine();
			
			int x = 0;
			int y = 0;
			
			int tx = 0;
			int ty = 0;
			
			switch(a) {
				case "draw" :
					System.out.println("Drawing board!");
					gameBoard.draw();
					break;
				case "checkType":
					System.out.println("Please choose x position of the piece:");
					x = scan.nextInt();
					System.out.println("Please choose y position of the piece:");
					y = scan.nextInt();
					System.out.println("That piece is a: " + gameBoard.getPieceType(x, y));
					break;
				case "move":
					System.out.println("Please choose x position of the piece you would like to move:");
					x = scan.nextInt();
					System.out.println("Please choose y position of the piece you would like to move:");
					y = scan.nextInt();
					System.out.println("Please choose x position to move to:");
					tx = scan.nextInt();
					System.out.println("Please choose y position to move to:");
					ty = scan.nextInt();
					gameBoard.movePiece(x, y, tx, ty);
					break;
				case "drawCheck":
					System.out.println("Please choose x position of the piece you would like to check:");
					x = scan.nextInt();
					System.out.println("Please choose y position of the piece you would like to check:");
					y = scan.nextInt();
					gameBoard.pieceBoard[y-1][x-1].drawChecker();
					break;
				case "check":
					break;
					
				default:
					System.out.println("Invalid Option");
					break;
			}
		}*/
		
	}
	
	
	public void endTurn() {
		System.out.println(currentTurnColour + "'s turn has ended.");
		if(currentTurnColour == PlayerColour.WHITE) {
			playerW.timer.stop();
			playerB.timer.start();
		}else if(currentTurnColour == PlayerColour.BLACK) {
			playerB.timer.stop();
			playerW.timer.start();
		}
		
		currentTurnColour = (currentTurnColour == PlayerColour.WHITE) ? PlayerColour.BLACK : PlayerColour.WHITE;
		gameBoard.checkAll();
		System.out.println(currentTurnColour + "'s turn next.");		
		
		if(gameBoard.isKingChecked(currentTurnColour)) {
			System.out.println(currentTurnColour + "'s king is in check!");
			if(currentTurnColour == this.playerW.m_colour) {
				gameBoard.checkAll();
				this.playerW.King.hasBeenChecked = true;
				this.checkColour = "WHITE";
				this.checkmateStatus = this.gameBoard.isCheckmate(this.playerW.m_colour);
			}else if(currentTurnColour == this.playerB.m_colour){
				gameBoard.checkAll();
				this.playerB.King.hasBeenChecked = true;
				this.checkColour = "BLACK";
				this.checkmateStatus = this.gameBoard.isCheckmate(this.playerB.m_colour);
			}
			
			if(this.checkmateStatus == true && this.checkColour == "BLACK") {
				this.endGame(playerW, "checkmate");
			}else if(checkmateStatus == true && checkColour == "WHITE") {
				this.endGame(playerB, "checkmate");
			}
		}
	}
	
	public int move(int x, int y, int tx, int ty) {
		return gameBoard.movePiece(x, y, tx, ty);
	}
	
	public boolean checkCanMove(int x, int y, int tx, int ty) {
		return gameBoard.pieceBoard[y-1][x-1].canMove(tx, ty);
	}
	
	public boolean checkCanAttack(int x, int y, int tx, int ty) {
		return gameBoard.pieceBoard[y-1][x-1].canAttack(tx, ty);
	}
	
	public void drawCheck(int x, int y) {
		gameBoard.checkAll();
		Piece p = gameBoard.pieceBoard[y-1][x-1];
		System.out.println("CheckBoard for " + p.getPlayer().m_colour.toString() + p.getType().toString());
		gameBoard.pieceBoard[y-1][x-1].drawChecker();
	}
	
	public void draw() {
		gameBoard.draw();
	}
	
	public void endGame(Player winnerPlayer, String endType) {
		JSONObject sender = new JSONObject();
		
		
		if(endType == "checkmate") {
			this.playerW.timer.stop();
			this.playerB.timer.stop();
		}
		
		this.gameEndStatus = true;
		
		sender.put("command", "endGame");
		sender.put("endType", endType);
		sender.put("winner", winnerPlayer.m_colour.toString());
		
		SS.sendUpdate(sender.toString());
		
	}
	
}

	
