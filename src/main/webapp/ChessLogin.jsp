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
				<div class="dialogue">
					<h1>Welcome to Tucker Chess!</h1>
					<label for="usernameInput">Enter your username: </label>
					<input type="text" id="usernameInput"  placeholder="Aa" />
					<input class="buttonStyle" type="button" value="LOGIN" onclick="login()">
				</div>
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
</div>
<div id="takenbin"></div>
</body>
</html>