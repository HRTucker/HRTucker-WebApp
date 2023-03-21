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
				<div id="sessionInput">
					<h1>Welcome to Tucker Chess!</h1>
					<div class="tab-element">
						<div class="tab-row">
							<input id="join-tab" class="tab-button" type="button" value="JOIN">
							<input id="create-tab" class="tab-button unselected" type="button" value="CREATE">
						</div>
						<div id="join-page" class="tab-content">
							<label for="sessionName">Enter a session name: </label>
							<input type="text" id="sessionName"  placeholder="Aa" />
							<br>
							<input class="buttonStyle" type="button" value="JOIN" onclick="joinSession()">
							<p id="serverCom"></p>
						</div>
						<div id="create-page" class="tab-content unselected">
							<label for="createSessionName">Enter a session name: </label>
							<input type="text" id="createSessionName"  placeholder="Aa" />
							<form>
								<input type="radio" name="timerMins" value="2" />2 mins<br/>
								<input type="radio" name="timerMins" value="10" />10 mins<br/>
								<input type="radio" name="timerMins" value="20" />20 mins<br/>
								<input type="radio" name="timerMins" value="30" />30 mins<br/>
							</form>
							<br>
							<input class="buttonStyle" type="button" value="CREATE" onclick="createSession()">
							<p id="serverCom"></p>
						</div>
					</div>
				</div>
				<div id="sessionSearching">
					<h3 id="searchMessage">^_^</h3>
					<input id="startBtn" class="buttonStyle" type="button" value="Start" onclick="startGame()">
				</div>
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
	<div id="interface-section">
		<input class="buttonStyle" type="button" value="Logout" onclick="logout()">
	</div>
</div>
<div id="takenbin"></div>
</body>
</html>