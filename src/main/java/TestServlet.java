
import Chess.*;

import java.io.IOException;


import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Chess.Game.Game;

import javax.servlet.*;
import javax.servlet.http.*;

//import org.codehaus.jackson.annotate.JsonAutoDetect;
//import org.codehaus.jackson.annotate.JsonIgnoreProperties;
//import org.codehaus.jackson.annotate.JsonProperty;
/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/TestServlet") 
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public TestServlet() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setHeader("Access-Control-Allow-Origin", "*");
		resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		resp.setHeader("Access-Control-Allow-Headers", "Content-Type");
		doPost(req,resp);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		
		System.out.println("request received");
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		/*
		String body = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
		ObjectMapper mapper = new ObjectMapper();
		MyObject obj = mapper.readValue(body, MyObject.class);
		*/
		
	    // Process the request data and generate a response
	    String message = "Hello, " + name + "! You are " + age + "years old!";
	    System.out.println(name);
	    response.setContentType("text/plain");
	    response.getWriter().println(message);
	}

}
