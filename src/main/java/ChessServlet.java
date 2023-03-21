import Chess.Game.*;
import Chess.Pieces.*;
import SessionService.SessionService;

import org.json.*;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ChessServlet
 */
@WebServlet(urlPatterns = {"/ChessServlet"}, asyncSupported = true)
public class ChessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public String test = "string";
	
    public ChessServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setHeader("Access-Control-Allow-Origin", "LOCALHOST");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
	    response.setHeader("Cache-Control", "private");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Connection", "keep-alive");
		
	    //String command = request.getParameter("command");
	    String username = request.getParameter("username");
	    String sessionName = request.getParameter("sessionName");
	    if(SessionService.sessionList.containsKey(sessionName)) {
	    	SessionService SS = SessionService.sessionList.get(sessionName);
	    	if (SS.clientIdMap.containsKey(username)) {
		    	SS.updateAsyncContext(username, SS.clientIdMap.get(username),request);
				System.out.println("User: " + username + " has Reconnected to session: " + sessionName);
		    }else {
		    	SS.addAsyncContext(username, request);
				System.out.println("User: " + username + " has Connected to session: " + sessionName);
		    }
	    }else {
	    	System.out.println("User: " + username + " was unable to connect to session: " + sessionName);
	    }
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "LOCALHOST");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		//response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
		response.setContentType("text/plain");
	    response.setHeader("Cache-Control", "private");
	    response.setHeader("Pragma", "no-cache");
			
	    String command = request.getParameter("command");
	    
	    System.out.println(command);
	    
	    
	    if(command.equals("resync")) {
	    	SessionService SS = SessionService.sessionList.get(request.getParameter("sessionName"));
	    	System.out.println(SS.getJPieces().toString());
	    	response.getWriter().println(SS.getJPieces().toString());
	    	
	    }else if(command.equals("start")) {
	    	System.out.println(request.getParameter("sessionName"));
	    	
	    	SessionService SS = SessionService.sessionList.get(request.getParameter("sessionName"));
	    	
	    	SS.startGame(SS.timerMinutes);
	    	
	    }else if(command.equals("create")) {
	    	
	    	String username = request.getParameter("username");
	    	String sessionName = request.getParameter("sessionName");
	    	
	    	SessionService.createSession(username, sessionName, request, response);
	    	
	    }else if(command.equals("join")) {
	    	String username = request.getParameter("username");
	    	String sessionName = request.getParameter("sessionName");
	    	
	    	SessionService.joinSession(username, sessionName, request, response);
	    	
	    }else if(command.equals("move")&& request.getParameter("originPos") != "") {
	    	JSONObject sender = new JSONObject();
	    	JSONObject syncSend = new JSONObject();
	    	SessionService SS = SessionService.sessionList.get(request.getParameter("sessionName"));
	    	
    		String orig = request.getParameter("originPos");
    		String dest = request.getParameter("destinationPos");
    		
    		System.out.println(orig + " " + dest);
    		
    		int x = Integer.parseInt(Character.toString(orig.charAt(1)));
    		int y = Integer.parseInt(Character.toString(orig.charAt(3)));
    		int tx = Integer.parseInt(Character.toString(dest.charAt(1)));
    		int ty = Integer.parseInt(Character.toString(dest.charAt(3)));
    		
    		response.setContentType("application/json");
    		int moveStatus = SS.chessGame.move(x, y, tx, ty);
    		
    		if(moveStatus==0) {
    			sender.put("canMove", false);
    			sender.put("canAttack", false);
    			response.getWriter().println(sender.toString());
    			System.out.println("Can't move! SENT");    		
    		}else if(moveStatus==1 || moveStatus == 5){//moving
    			sender.put("canMove", true);
    			sender.put("canAttack", false);
    			
    			if(moveStatus==5) { 
    				sender.put("canPromote", true);
    			}else {
    				sender.put("canPromote", false);
    			}
    			
    			sender.put("checkmate", SS.chessGame.checkmateStatus);
    			sender.put("checkColour", SS.chessGame.checkColour);
    			System.out.println("Can move! SENT");
    			
    			syncSend.put("command", "resyncMove");
    			syncSend.put("pieceToMove", orig);
    			syncSend.put("tileMoveTo", dest);
    			syncSend.put("checkmate", SS.chessGame.checkmateStatus);
    			syncSend.put("checkColour", SS.chessGame.checkColour);
    			
    			SS.sessionPiecePositions.remove(request.getParameter("pieceMoved"));
    			SS.sessionPiecePositions.put(request.getParameter("pieceMoved"),request.getParameter("destinationPos"));
    			
    			response.getWriter().println(sender.toString());
    			SS.sendUpdate(syncSend.toString(),request);
    		}else if(moveStatus==2 || moveStatus == 6){//attacking
    			sender.put("canMove", false);
    			sender.put("canAttack", true);
    			
    			if(moveStatus==6) { 
    				sender.put("canPromote", true);
    			}else {
    				sender.put("canPromote", false);
    			}
    			
    			sender.put("checkmate", SS.chessGame.checkmateStatus);
    			sender.put("checkColour", SS.chessGame.checkColour);
    			System.out.println("Can Attack! SENT");  
    			
    			syncSend.put("command", "resyncAttack");
    			syncSend.put("pieceToMove", orig);
    			syncSend.put("tileMoveTo", dest);
    			syncSend.put("checkmate", SS.chessGame.checkmateStatus);
    			syncSend.put("checkColour", SS.chessGame.checkColour);
    			
    			//System.out.println("pieceMoved: " + request.getParameter("pieceTaken"));
    			SS.sessionPiecePositions.remove(request.getParameter("pieceMoved"));
    			SS.sessionPiecePositions.remove(request.getParameter("pieceTaken"));
    			System.out.println("Sync list length: " + SS.sessionPiecePositions.size());
    			SS.sessionPiecePositions.putIfAbsent(request.getParameter("pieceMoved"),request.getParameter("destinationPos"));
    			SS.sessionPiecePositions.putIfAbsent(request.getParameter("pieceTaken"),"taken");
    			System.out.println("Sync list length: " + SS.sessionPiecePositions.size());
    			response.getWriter().println(sender.toString());
    			SS.sendUpdate(syncSend.toString(),request);
    		}else if(moveStatus==3){//castling left
    			int ckx = x-2;
    			int crx = x-1;
    			sender.put("canMove", true);
    			sender.put("canAttack", false);
    			sender.put("isCastling", true);
    			sender.put("castleKingPos", "T"+ckx+":"+y);
    			sender.put("castleRookPos", "T"+crx+":"+ty);
    			System.out.println("Can move! SENT");
    			
    			syncSend.put("command", "resyncCastling");
    			syncSend.put("pieceToMove_king", "T"+x+":"+y);
    			syncSend.put("tileMoveTo_king", "T"+ckx+":"+y);
    			syncSend.put("pieceToMove_rook", "T"+tx+":"+ty);
    			syncSend.put("tileMoveTo_rook", "T"+crx+":"+ty);
    			
    			SS.sessionPiecePositions.remove(request.getParameter("pieceMoved"));
    			SS.sessionPiecePositions.remove(request.getParameter("pieceTaken"));
    			SS.sessionPiecePositions.put(request.getParameter("pieceMoved"),"T"+ckx+":"+y);
    			SS.sessionPiecePositions.put(request.getParameter("pieceTaken"),"T"+crx+":"+ty);
    			
    			response.getWriter().println(sender.toString());
    			SS.sendUpdate(syncSend.toString(),request);
    		}else if(moveStatus==4){//Castling right
    			int ckx = x+2;
    			int crx = x+1;
    			sender.put("canMove", true);
    			sender.put("canAttack", false);
    			sender.put("isCastling", true);
    			sender.put("castleKingPos", "T"+ckx+":"+y);
    			sender.put("castleRookPos", "T"+crx+":"+ty);
    			System.out.println("Can move! SENT");
    			
    			syncSend.put("command", "resyncCastling");
    			syncSend.put("pieceToMove_king", "T"+x+":"+y);
    			syncSend.put("tileMoveTo_king", "T"+ckx+":"+y);
    			syncSend.put("pieceToMove_rook", "T"+tx+":"+ty);
    			syncSend.put("tileMoveTo_rook", "T"+crx+":"+ty);
    			
    			SS.sessionPiecePositions.remove(request.getParameter("pieceMoved"));
    			SS.sessionPiecePositions.remove(request.getParameter("pieceTaken"));
    			SS.sessionPiecePositions.put(request.getParameter("pieceMoved"),"T"+ckx+":"+y);
    			SS.sessionPiecePositions.put(request.getParameter("pieceTaken"),"T"+crx+":"+ty);
    			
    			response.getWriter().println(sender.toString());
    			SS.sendUpdate(syncSend.toString(),request);
    		}
    		
    		/*
    		if(moveStatus == 1 || moveStatus == 2) {
    			sender = new JSONObject();
    			sender.put("command", "checkmate");
    	    	if(SS.chessGame.gameBoard.isCheckmate(SS.chessGame.playerB.m_colour)) {
    	    		SS.checkmate = true;
    	    		sender.put("checkColour","BLACK");
    	    		System.out.println("Sending CHECKMATE FOR BLACK");
    	    		SS.sendUpdate(sender.toString());
    	    	}else if(SS.chessGame.gameBoard.isCheckmate(SS.chessGame.playerW.m_colour)){
    	    		SS.checkmate = true;
    	    		sender.put("checkColour","WHITE");
    	    		System.out.println("Sending CHECKMATE FOR WHITE");
    	    		SS.sendUpdate(sender.toString());
    	    	}
    	    }
    	    */
    		
	    }else if(command.equals("reset")) {
	    	SessionService SS = SessionService.sessionList.get(request.getParameter("sessionName"));
	    	System.out.println("Game reset!");
	    	SS.chessGame = new Game(SS.timerMinutes, SS);
	    }else if(command.equals("drawCheck")) {
	    	int x = Integer.parseInt(request.getParameter("originPos"));
	    	int y = Integer.parseInt(request.getParameter("destinationPos"));
	    	
	    	SessionService SS = SessionService.sessionList.get(request.getParameter("sessionName"));
	    	
	    	SS.chessGame.draw();
	    	SS.chessGame.drawCheck(x, y);
	    }else if(command.equals("promotePawn")) {
	    	String piece = request.getParameter("originPos");
	    	PieceType newType = PieceType.valueOf(request.getParameter("destinationPos").toUpperCase());
	    	int x = Integer.parseInt(request.getParameter("pieceMoved"));//Naming is incorrect but is required for other areas of the code.
	    	int y = Integer.parseInt(request.getParameter("pieceTaken"));//Naming is incorrect but is required for other areas of the code.
	    	JSONObject syncJSON = new JSONObject();
	    	
	    	SessionService SS = SessionService.sessionList.get(request.getParameter("sessionName"));
	    	
	    	System.out.println(piece + " promoted to " + newType);
	    	System.out.println(piece + "|" + newType + "|" +  x + "|" + y);
	    	
	    	SS.chessGame.gameBoard.promotePiece(newType, x, y);
	    	
	    	syncJSON.put("command", "promotePawn");
	    	syncJSON.put("pieceID", piece);
	    	syncJSON.put("newType", newType.toString());
	    	
	    	SS.sendUpdate(syncJSON.toString(), request);
	    }else {
	    	System.out.println("Invalid Command: " + command);
	    }
		
	}

}

