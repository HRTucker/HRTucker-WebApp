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
		<div id="board-section">
			<div class="side-tag-col">
				<%
				int count = 8;
				for(int y = 0; y < 8; y++){
					out.println("<div class=\"side-tag\"><p>" + count-- + "</p></div>");
				}
				%>
			</div>
			<div class="chess-board-container">
				<audio id="whiteMoveAudio" src="https://cdn.discordapp.com/attachments/374624617651765248/1080504971079974984/pieceMove01.mp3"></audio>
				<audio id="blackMoveAudio" src="https://cdn.discordapp.com/attachments/374624617651765248/1080504971079974984/pieceMove01.mp3"></audio>
				
				<div id="checkmate-panel">
					<h3><span id="winner">COLOUR</span> Wins by <span id="reason">reason</span> !</h3> 
				</div>
				
				<div id="board-cover"></div>
				<div id="promotion-menu">
			    	<div class="picker-socket">
			    		<img id="QueenP" class="chess-piece" alt="BlackQueen" src="https://media.discordapp.net/attachments/374624617651765248/1068584269783507015/WhiteQueen.png" draggable="false">
			    	</div>
			    	<div class="picker-socket">
			    		<img id="BishopP" class="chess-piece" alt="BlackBishop1" src="https://media.discordapp.net/attachments/374624617651765248/1068584268718162081/WhiteBishop.png" draggable="false">
			    	</div>
			    	<div class="picker-socket">
			    		<img id="KnightP" class="chess-piece" alt="BlackKnight1" src="https://media.discordapp.net/attachments/374624617651765248/1068584269284392970/WhiteKnight.png" draggable="false">
			    	</div>
			    	<div class="picker-socket">
			    		<img id="RookP" class="chess-piece" alt="BlackRook1" src="https://media.discordapp.net/attachments/374624617651765248/1068584270035173429/WhiteRook.png" draggable="false">
			    	</div>
				</div>
				
				<%
				
				int xCount = 1;
				int yCount = 1;
				
				for(int y = 0; y < 8; y++){
					out.println("<div class=\"chess-tile-row\">");
					if(y % 2 == 0){
						xCount = 1;
						for(int x = 0; x < 8; x++){
							if(x % 2 == 0){
								out.println("<div class=\"chess-tile-white\" id=\"T" + xCount++ + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}else{
								out.println("<div class=\"chess-tile-black\" id=\"T" + xCount++ + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}
						}
						yCount++;
					}else{
						xCount = 1;
						for(int x = 0; x < 8; x++){
							if(x % 2 == 0){
								out.println("<div class=\"chess-tile-black\" id=\"T" + xCount++ + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}else{
								out.println("<div class=\"chess-tile-white\" id=\"T" + xCount++ + ":" + yCount + "\" draggable=\"false\" ondrop=\"drop(event)\" ondragover=\"allowDrop(event)\">" + "</div>");
							}
							
						}
						yCount++;
					}
					out.println("</div>");
				}
				%>
			</div>
			<div class="bottom-tag-row">
				<%
				char[] botChars = {'A','B','C','D','E','F','G','H'};
				int bot_count = 0;
				for(int y = 0; y < 8; y++){
					out.println("<div class=\"bottom-tag\"><p>" + botChars[bot_count++] + "</p></div>");
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
		<button class="buttonStyle" onclick="promotePawn('WPawn5')">Promote</button>
		
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