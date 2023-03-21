<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>TuckerChess</title>
	<script type="text/javascript" src="ChessGame.js">
	</script>
	  
	<link rel="stylesheet" type="text/css" href="ChessGame.css">
</head>
<body>
<div id="main">
	<div id="game-section">
		<div id="baard-section">
			<div class="side-tag-col">
				<%
				int count = 1;
				for(int y = 0; y < 8; y++){
					out.println("<div class=\"side-tag\"><p>" + count++ + "</p></div>");
				}
				%>
			</div>
			<div class="chess-board-container">
				<div id="checkmate-panel">
					<h3><span id="checkedColour">COLOUR</span> has been CHECKMATED!</h3>
				</div>
				<%
					
				int xCount = 8;
				int yCount = 8;
					
				for(int y = 0; y < 8; y++){
					out.println("<div class=\"chess-tile-row\">");
					if(y % 2 == 0){
						xCount = 8;
						for(int x = 0; x < 8; x++){
							if(x % 2 == 0){
								out.println("<div class=\"chess-tile-white\" id=\"T" + xCount-- + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}else{
								out.println("<div class=\"chess-tile-black\" id=\"T" + xCount-- + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}
						}
						yCount--;
					}else{
						xCount = 8;
						for(int x = 0; x < 8; x++){
							if(x % 2 == 0){
								out.println("<div class=\"chess-tile-black\" id=\"T" + xCount-- + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}else{
								out.println("<div class=\"chess-tile-white\" id=\"T" + xCount-- + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}
								
						}
						yCount--;
					}
					out.println("</div>");
				}
				%>
				</div>
			<div class="bottom-tag-row">
				<%
				char[] botChars = {'A','B','C','D','E','F','G','H'};
				int bot_count = 7;
				for(int y = 0; y < 8; y++){
					out.println("<div class=\"bottom-tag\"><p>" + botChars[bot_count--] + "</p></div>");
				}
				%>
			</div>
		</div>
	</div>
	<div id="interface-section">
		<table id="clientInfo">
			<tr>
			</tr>
			<tr>
				<td>Session Name:</td>
				<td id="info-session"></td>
			</tr>
			<tr>
				
			</tr>
		</table>

		<input class="buttonStyle" type="button" value="Logout" onclick="logout()">
		<input class="buttonStyle" type="button" value="Reset Board" onclick="resetGame()">
		
		<button class="buttonStyle" onclick="drawCheck()">CheckBoard</button>
		
		<input class="buttonStyle" type="button" value="Test Check" onclick="checkmate()">
		<div id="turnTimer-section">
			<div id="turnTimer-section-opponent">
				<div class="timer" id="timer-opponent">00:00</div>
				<div class="timerName">
					<p id="info-opponent"></p>
				</div>
			</div>
			<div id="turnTimer-section-client">
				<div class="timer" id="timer-client">00:00</div>
				<div class="timerName">
					<p id="info-user"></p>
				</div>
			</div>
		</div>
		<div id="chatbox">
		
		</div>
	</div>
</div>
<div id="takenbin"></div>
</body>
</html>