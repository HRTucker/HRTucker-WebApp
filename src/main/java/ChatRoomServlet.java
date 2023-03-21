

import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.AsyncContext;
import javax.servlet.AsyncEvent;
import javax.servlet.AsyncListener;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet implementation class ChatRoomServlet
 */
@WebServlet(urlPatterns = {"/ChatRoomServlet"}, asyncSupported = true)
public class ChatRoomServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatRoomServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
		//response.setContentType("text/plain");
	    response.setHeader("Cache-Control", "private");
	    response.setHeader("Pragma", "no-cache");
	    response.setHeader("Connection", "keep-alive");
		
	    String id = request.getParameter("clientID");
		 // Keep the request open until there is data to send
	    //AsyncContext asyncContext = request.startAsync();
	    if (ChatService.clientIdMap.containsKey(id)) {
	    	ChatService.updateAsyncContext(ChatService.clientIdMap.get(id),request);
			System.out.println("Chat Reconnected:" + id);
	    }else {
		    ChatService.addAsyncContext(request);
			System.out.println("Chat Connected:" + id);
	    }
	    

	    
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		//response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
		response.setContentType("text/plain");
	    response.setHeader("Cache-Control", "private");
	    response.setHeader("Pragma", "no-cache");
			
	    
	    String id = request.getParameter("clientID");
	    int isJoining = Integer.parseInt(request.getParameter("isJoining"));
	    System.out.println(isJoining);

	    if(isJoining == 1) {
	    	String message = (id + " has joined the chat!");
		    ChatService.sendUpdate(message, request);
	    }else {
		    System.out.println(id+"has sent a message!");
			
		    String message = (id + ": " + request.getParameter("message"));
		    ChatService.sendUpdate(message, request);
	    }
	}

}

/*
//Iterate over the values using a for-each loop
for (Integer value : map.values()) {
System.out.println(value);
}
//Iterate over the entries using a for-each loop
for (Map.Entry<String, Integer> entry : map.entrySet()) {
System.out.println(entry.getKey() + ": " + entry.getValue());
}
*/

class ChatService {
	  private static List<AsyncContext> contexts = new ArrayList<>();
	  public static HashMap<String,AsyncContext> clientIdMap = new HashMap<>();
	  
	  public static void addAsyncContext(HttpServletRequest req) throws IOException {
		  
		 AsyncContext asyncContext = req.startAsync();
		 asyncContext.setTimeout(0);
		 //ServletOutputStream out = r.getOutputStream();
		String clientID = req.getParameter("clientID");
	    contexts.add(asyncContext);
	    clientIdMap.put(clientID, asyncContext);
	    System.out.println("(SETUP)Map Updated:" + clientIdMap);
	  }
	  
	  public static void updateAsyncContext(AsyncContext oldContext, HttpServletRequest req) throws IOException {
			 //ServletOutputStream out = r.getOutputStream();
		  
		  	AsyncContext asyncContext = req.startAsync();
			 asyncContext.setTimeout(0);
			String clientID = req.getParameter("clientID");
			contexts.remove(oldContext);
		    contexts.add(asyncContext);
		    clientIdMap.put(clientID, asyncContext);
		    System.out.println("AsyncContext updated for " + clientID + "\n changed from: " + oldContext + "\n to: " + asyncContext);
		    System.out.println("(UPDATE)Map Updated:" + clientIdMap);
		  }
	  
	  public static void removeAsyncContext(AsyncContext context, HttpServletRequest req) throws IOException {
			String clientID = req.getParameter("clientID");
		    contexts.remove(context);
		    clientIdMap.remove(clientID, context);
		  }
	  
	  public static void sendUpdate(String message, HttpServletRequest req) throws IOException {
		  System.out.println(clientIdMap);
	    for (Map.Entry<String, AsyncContext> context : clientIdMap.entrySet()) {
	    	 System.out.println("This is the current client ID:" + req.getParameter("clientID") + "|" +clientIdMap.get(req.getParameter("clientID")));
	    	 
		      try {
		    	  if(clientIdMap.get(req.getParameter("clientID")) != context.getValue()) {
			    	  ServletResponse response = context.getValue().getResponse();
			          response.setContentType("text/plain");
			          ServletOutputStream out = response.getOutputStream();
			          out.print(message);
			          out.flush();
			          out.close();
			          
			          context.getValue().complete();
			          
			          System.out.println(context.getKey() + "updated");
		      	}else {
		      		System.out.println("Passing on sender");
		    		context.getValue().complete();
		    	}
		      } catch (IOException e) {
		        // Error sending update
		    	  System.out.println("There was an error sending Chat Update!");
		      } catch (IllegalStateException e) {
		    	  System.out.println("User: " + context.getKey() + " is unreachable or has an illegal State!");
		    	  System.out.println("Removing context!");
		    	  System.out.println("Error Message from illegalState:" + e.getLocalizedMessage());
		    	  
		    	  clientIdMap.remove(context.getKey());
		      }catch(ConcurrentModificationException e){
		    	  System.out.println("Error Message from ConcurrentModification"+e.getLocalizedMessage());
		      }
	    		
	    }
	    contexts.clear();
	  }
	}
