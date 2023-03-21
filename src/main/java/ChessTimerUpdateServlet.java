
import Chess.Game.*;
import Chess.Pieces.*;
import SessionService.SessionService;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Servlet implementation class ChessTimerUpdate
 */
@WebServlet("/ChessTimerUpdate")
public class ChessTimerUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChessTimerUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/event-stream; charset=utf-8");
	    response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Connection", "keep-alive");
	    
	    PrintWriter out = response.getWriter();
	    
	    String sessionName = request.getParameter("sessionName");
	    System.out.println("###Session Name: " + sessionName);
	    
	    SessionService SS = SessionService.sessionList.get(sessionName);
	    

	    while (!SS.chessGame.gameEndStatus) {
	    	
	      //generate the update data
	      JSONObject updateData = new JSONObject();//parse JSONObject to text and then back on client-side.
	      updateData.put("wTimer",SS.chessGame.playerW.timer.getSecondsRemaining());
	      updateData.put("bTimer",SS.chessGame.playerB.timer.getSecondsRemaining());
	      
	      
	      
	      //send the update to the client
	      out.write("data: " + updateData.toString() + "\n\n");
	      //out.write("blackTimer: " + "20:10" + "\n\n");

	      //flush the writer to ensure that the data is sent immediately
	      out.flush();

	      if(SS.chessGame.playerW.timer.getSecondsRemaining() == 0) {
	    	  SS.chessGame.endGame(SS.chessGame.playerB, "time");
	      }else if(SS.chessGame.playerB.timer.getSecondsRemaining() == 0) {
	    	  SS.chessGame.endGame(SS.chessGame.playerW, "time");
	      }
	      //wait for some time before sending the next update
	      try {
	        Thread.sleep(1000);
	      } catch (InterruptedException e) {
	        e.printStackTrace();
	      }
	    }
	   
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
