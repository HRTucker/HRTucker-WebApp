package SessionService;
import java.io.IOException;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import Chess.Game.Game;
import Chess.Game.PlayerColour;

public class SessionService {
	 public static HashMap<String,SessionService> sessionList = new HashMap<String,SessionService>();
		
	  public HashMap<String,AsyncContext> clientIdMap = new HashMap<String,AsyncContext>();
	  
	  public HashMap<String,String> sessionPiecePositions;
	  public JSONObject pieceSync;
	  
	  public boolean searching;
	  
	  public Game chessGame;
	  
	  public String host;
	  public String user;
	  
	  public int timerMinutes;
	  
	  public boolean checkmate = false;
	  
	  public SessionService(String user1){
		  this.searching = true;
		  this.host = user1;
		  
		  
		  //Timer timer = new Timer();
		  
		  //this.chessGame = new Game();
		  //pieceSync = new JSONObject();
		  sessionPiecePositions = new HashMap<String,String>();
		  
		  sessionPiecePositions.put("WKing", "T5:8");
		  
		  sessionPiecePositions.put("WQueen", "T4:8");
		  
		  sessionPiecePositions.put("WKnight1", "T2:8");
		  sessionPiecePositions.put("WKnight2", "T7:8");
		  
		  sessionPiecePositions.put("WBishop1", "T3:8");
		  sessionPiecePositions.put("WBishop2", "T6:8");
		  
		  sessionPiecePositions.put("WRook1", "T1:8");
		  sessionPiecePositions.put("WRook2", "T8:8");
		  
		  sessionPiecePositions.put("WPawn1", "T1:7");
		  sessionPiecePositions.put("WPawn2", "T2:7");
		  sessionPiecePositions.put("WPawn3", "T3:7");
		  sessionPiecePositions.put("WPawn4", "T4:7");
		  sessionPiecePositions.put("WPawn5", "T5:7");
		  sessionPiecePositions.put("WPawn6", "T6:7");
		  sessionPiecePositions.put("WPawn7", "T7:7");
		  sessionPiecePositions.put("WPawn8", "T8:7");
		  
		  sessionPiecePositions.put("BKing", "T5:1");
		  
		  sessionPiecePositions.put("BQueen", "T4:1");
		  
		  sessionPiecePositions.put("BKnight1", "T7:1");
		  sessionPiecePositions.put("BKnight2", "T2:1");
		  
		  sessionPiecePositions.put("BBishop1", "T6:1");
		  sessionPiecePositions.put("BBishop2", "T3:1");
		  
		  sessionPiecePositions.put("BRook1", "T8:1");
		  sessionPiecePositions.put("BRook2", "T1:1");
		  
		  sessionPiecePositions.put("BPawn1", "T8:2");
		  sessionPiecePositions.put("BPawn2", "T7:2");
		  sessionPiecePositions.put("BPawn3", "T6:2");
		  sessionPiecePositions.put("BPawn4", "T5:2");
		  sessionPiecePositions.put("BPawn5", "T4:2");
		  sessionPiecePositions.put("BPawn6", "T3:2");
		  sessionPiecePositions.put("BPawn7", "T2:2");
		  sessionPiecePositions.put("BPawn8", "T1:2");
		  
		  /*TimerTask checkmateTimer = new TimerTask() {
			@Override
			public void run() {
				if(!checkmate) {
					checkCheckmate();
				}else {
					timer.cancel();
				}
				
			}
			  
		  };
		  timer.scheduleAtFixedRate(checkmateTimer, 0, 1000);*/
		  
	  }
	  
	  
	  public void startGame(int gameTimer) {
		  chessGame = new Game(gameTimer, this);

		  JSONObject senderW = new JSONObject();
		  senderW.put("command", "redirect");
		  senderW.put("colour", "white");
		  
		  JSONObject senderB = new JSONObject();
		  senderB.put("command", "redirect");
		  senderB.put("colour", "black");
		  
		  this.sendUpdate(host, senderW.toString());
		  this.sendUpdate(user, senderB.toString());
	  }
	  
	  public static void createSession(String user, String sessionName, HttpServletRequest req, HttpServletResponse resp) throws IOException{
		  JSONObject sender = new JSONObject();
		  resp.setContentType("application/json");
		  
		  if(!sessionList.containsKey(sessionName)) {
			  SessionService SS = new SessionService(user);
			  //SS.addAsyncContext(user, req);
			  SS.timerMinutes = Integer.parseInt(req.getParameter("minutes"));
			  sessionList.put(sessionName, SS);
			  
			  sender.put("sessionCreated",true);
			  
			  System.out.println("User: " + user + " created session: " + sessionName);
		  }else {
			  System.out.println("A session already exists with name: " + sessionName);
			  sender.put("sessionCreated",false);
		  }
		  resp.getWriter().println(sender.toString());
	  }
	  
	  public static void joinSession(String user2, String sessionName, HttpServletRequest req, HttpServletResponse resp) throws IOException {
		  
		  JSONObject sender = new JSONObject();
		  resp.setContentType("application/json");
		  
		  if(sessionList.containsKey(sessionName)) {
			  SessionService SS = sessionList.get(sessionName);
			  SS.user = user2;
			  
			  sender.put("sessionCreated",true);
			  sender.put("opponent", SS.host);
			  
			  System.out.println("User: " + user2 + " joined session: " + sessionName);
			  JSONObject sessionCom = new JSONObject();
			  sessionCom.put("command", "joined");
			  sessionCom.put("opponent", user2);
			  SS.sendUpdate(sessionCom.toString(), req);
			  
		  }else {
			  System.out.println("No session exists with name: " + sessionName);
		  }
		  resp.getWriter().println(sender.toString());
	  }
	  
	  public void addAsyncContext(String user, HttpServletRequest req) throws IOException {
		  
		 AsyncContext asyncContext = req.startAsync();
		 asyncContext.setTimeout(0);
		 this.clientIdMap.put(user, asyncContext);
	    System.out.println("(SETUP)Map Updated:" + clientIdMap);
	  }
	  
	  public void updateAsyncContext(String user, AsyncContext oldContext, HttpServletRequest req) throws IOException {
			 //ServletOutputStream out = r.getOutputStream();
		  
		  	AsyncContext asyncContext = req.startAsync();
			asyncContext.setTimeout(0);
		    this.clientIdMap.put(user, asyncContext);
		    System.out.println("AsyncContext updated for " + user + "\n changed from: " + oldContext + "\n to: " + asyncContext);
		    System.out.println("(UPDATE)Map Updated:" + clientIdMap);
		  }
	  
	  public void removeAsyncContext(AsyncContext context, HttpServletRequest req) throws IOException {
			String clientID = req.getParameter("clientID");
		    clientIdMap.remove(clientID, context);
		  }
	  
	  //for sending to opposing client
	  public void sendUpdate(String jMessage, HttpServletRequest req) throws IOException {
		  System.out.println(this.clientIdMap);
	    for (Map.Entry<String, AsyncContext> context : this.clientIdMap.entrySet()) {
	    	 System.out.println("This is the current client ID:" + req.getParameter("username") + "|" +this.clientIdMap.get(req.getParameter("username")));
	    	 
		      try {
		    	  if(clientIdMap.get(req.getParameter("username")) != context.getValue()) {
			    	  ServletResponse response = context.getValue().getResponse();
			    	  response.setContentType("application/json");
			          ServletOutputStream out = response.getOutputStream();
			          out.print(jMessage);
			          out.flush();
			          out.close();
			          
			          context.getValue().complete();
			          
			          System.out.println(context.getKey() + " updated");
		      	}else {
		      		JSONObject reconnecter = new JSONObject();
		      		reconnecter.put("command", "reconnect");
		      		ServletResponse response = context.getValue().getResponse();
			    	  response.setContentType("application/json");
			          ServletOutputStream out = response.getOutputStream();
			          out.print(reconnecter.toString());
			          out.flush();
			          out.close();
		      		System.out.println("Passing on sender");
		    		context.getValue().complete();
		    	}
		      } catch (IOException e) {
		        // Error sending update
		    	  System.out.println("There was an error sending board Update! " + e.getLocalizedMessage());
		      } catch (IllegalStateException e) {
		    	  System.out.println("User: " + context.getKey() + " is unreachable or has an illegal State!");
		    	  System.out.println("Removing context!");
		    	  System.out.println("Error Message from illegalState:" + e.getLocalizedMessage());
		    	  
		    	  clientIdMap.remove(context.getKey());
		      }catch(ConcurrentModificationException e){
		    	  System.out.println("Error Message from ConcurrentModification"+e.getLocalizedMessage());
		      }
	    
	    }
	    }
	    //for sending to both clients
	    public void sendUpdate(String jMessage) {
			  System.out.println(this.clientIdMap);
		    for (Map.Entry<String, AsyncContext> context : this.clientIdMap.entrySet()) {		    	 
			      try {
				    	  ServletResponse response = context.getValue().getResponse();
				    	  response.setContentType("application/json");
				          ServletOutputStream out = response.getOutputStream();
				          out.print(jMessage);
				          out.flush();
				          out.close();
				          
				          context.getValue().complete();
				          
				          System.out.println(context.getKey() + "updated");
			      } catch (IOException e) {
			        // Error sending update
			    	  System.out.println("There was an error sending board Update! " + e.getLocalizedMessage());
			      } catch (IllegalStateException e) {
			    	  System.out.println("User: " + context.getKey() + " is unreachable or has an illegal State!");
			    	  //System.out.println("Removing context!");
			    	  System.out.println("Error Message from illegalState:" + e.getLocalizedMessage());
			    	  
			    	  //clientIdMap.remove(context.getKey());
			      }catch(ConcurrentModificationException e){
			    	  System.out.println("Error Message from ConcurrentModification"+e.getLocalizedMessage());
			      }
			      }
		    		
		    }
		  //for sending to one specific player
		    public void sendUpdate(String player,String jMessage) {
				  System.out.println(this.clientIdMap);	    	 
				      try {
				    	  AsyncContext context = this.clientIdMap.get(player);
				    	  ServletResponse response = context.getResponse();
					    	  response.setContentType("application/json");
					          ServletOutputStream out = response.getOutputStream();
					          out.print(jMessage);
					          out.flush();
					          out.close();
					          
					          context.complete();
					          
					          System.out.println(player + "updated");
				      } catch (IOException e) {
				        // Error sending update
				    	  System.out.println("There was an error sending board Update! " + e.getLocalizedMessage());
				      } catch (IllegalStateException e) {
				    	  System.out.println("User: " + player + " is unreachable or has an illegal State!");
				    	  System.out.println("Removing context!");
				    	  System.out.println("Error Message from illegalState:" + e.getLocalizedMessage());
				    	  
				    	  clientIdMap.remove(player);
				      }catch(ConcurrentModificationException e){
				    	  System.out.println("Error Message from ConcurrentModification"+e.getLocalizedMessage());
				      }
			    		
			    
		    }
		    
		    public JSONObject getJPieces() {
		    	JSONObject sender = new JSONObject();
		    	System.out.println(this.sessionPiecePositions.entrySet());
		    	 for (Map.Entry<String, String> piece : this.sessionPiecePositions.entrySet()) {
		    		 sender.put(piece.getKey(), piece.getValue());
		    	 }
		    	return sender;
		    }
		    
		    public void checkCheckmate() {
		    	PlayerColour pColour = this.chessGame.currentTurnColour;
		    }
	}

