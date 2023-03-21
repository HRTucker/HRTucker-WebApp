/**
 * 
 */

var debugPieces = false;
const whiteMoveAudio = new Audio("https://cdn.discordapp.com/attachments/374624617651765248/1080504971079974984/pieceMove01.mp3");
const blackMoveAudio = new Audio("https://cdn.discordapp.com/attachments/374624617651765248/1080504971079974984/pieceMove01.mp3");
 
 window.onload = function() {	 
	var pageName = getPageName();
	 console.log(pageName);
	 console.log("Currently logged in as " + localStorage.getItem("username"));
	if (!(localStorage.getItem("username")) && pageName != "ChessLogin.jsp") {
  		window.location.href = "ChessLogin.jsp";
	}else if(pageName == "ChessSearch.jsp"){
		document.getElementById("join-tab").addEventListener("click",changeTab)
		document.getElementById("create-tab").addEventListener("click",changeTab)
		
	}else if(pageName == "ChessGame.jsp" || pageName == "ChessGame_alt.jsp"){
		if(pageName == "ChessGame.jsp" && localStorage.getItem("colour" == "black")){
			window.location.href = "ChessGame_alt.jsp";
		}else if(pageName == "ChessGame_alt.jsp" && localStorage.getItem("colour" == "white")){
			window.location.href = "ChessGame.jsp";
		}
		
		setBoard();
		setDetails();
		resyncPieces();
		openConnection();
		
		let sessionName = localStorage.getItem("sessionName");
		const timerUpdate = new EventSource(getDomainAddress()+`ChessTimerUpdate?sessionName=${sessionName}`);
	
		timerUpdate.addEventListener("message", event => {
		  const updateData = JSON.parse(event.data);
		  let whiteTimer = convertDigTimer(updateData.wTimer);
		  let blackTimer = convertDigTimer(updateData.bTimer);
		  
		  console.log("Update Event: " + updateData.wTimer + "|" + updateData.bTimer);
		  // update the UI with the new data
		  
		  if(pageName == "ChessGame.jsp"){
		  	  document.getElementById("timer-client").innerHTML = whiteTimer;
		      document.getElementById("timer-opponent").innerHTML = blackTimer;
		  }else if(pageName == "ChessGame_alt.jsp"){
			  document.getElementById("timer-opponent").innerHTML = whiteTimer;
		  	  document.getElementById("timer-client").innerHTML = blackTimer;
		  }
		  
		});
	
		timerUpdate.addEventListener("error", event => {
		  console.error("SSE error", event);
		  timerUpdate.close();
		});
	}
	 
	 /*
	//On Tile Click change to selected style
	var elements = document.getElementsByClassName("chess-tile-black");
	
	for (var i = 0; i < elements.length; i++) {
	    elements[i].addEventListener("click", function() {
	        // When the element is clicked, toggle the "active" class
	        this.classList.toggle("selected");
	    });
	}
	elements = document.getElementsByClassName("chess-tile-white");
	
	for (var i = 0; i < elements.length; i++) {
	    elements[i].addEventListener("click", function() {
	        // When the element is clicked, toggle the "active" class
	        this.classList.toggle("selected");
	    });
	}
	*/
	
	
	
	/*TODO
		- Setup client-side movement checkers that toggle tile allowDrop depending on the piece being moved.
		- Setup var storage for when in-check
		- Setup colour picker with board setup
	*/
	//Setting Pieces
	
	
}

function getDomainAddress(){
	var currentPage = window.location.pathname;
	return currentPage.substring(0, currentPage.lastIndexOf("/") + 1);
}

function getPageName(){
	var currentPage = window.location.pathname;
	return currentPage.substring(currentPage.lastIndexOf("/") + 1);
}

function setDetails(){
	document.getElementById("info-user").innerHTML = localStorage.getItem("username");
	document.getElementById("info-session").innerHTML = localStorage.getItem("sessionName");
	document.getElementById("info-opponent").innerHTML = localStorage.getItem("opponentName");
}

function convertDigTimer(timerSecs){
	let calcSecs = timerSecs;
	let front;
	let end;
	
	end = timerSecs%60;
	calcSecs -= end;
	front = calcSecs/60;
	
	if(front < 10 && end < 10){
		return (`0${front}:0${end}`);
	}else if(front < 10 && end > 10){
		return (`0${front}:${end}`);
	}else if(front > 10 && end < 10){
		return (`${front}:0${end}`);
	}else{
		return (`${front}:${end}`);
	}
}

//Setting Pieces
function setPiece(pColour, pName, x, y){
	var piece = document.createElement("img");
  		piece.id = pColour[0] + pName;
  		piece.setAttribute("class","chess-piece");
 		piece.setAttribute("alt",pColour + pName);
 		
 		//ensures that each user can only drag their pieces.
 		if((getPageName() == "ChessGame.jsp" && pColour == "White")|| debugPieces == true){
	  		piece.setAttribute("draggable","true");
	  		piece.setAttribute("ondragstart","drag(event)");
  		}else if((getPageName() == "ChessGame_alt.jsp" && pColour == "Black")|| debugPieces == true){
	  		piece.setAttribute("draggable","true");
	  		piece.setAttribute("ondragstart","drag(event)");
  		}
  		
  		document.getElementById(`T${x}:${y}`).appendChild(piece);
  		console.log("PieceName: " + pName[pName.length-1]);
  	if(parseInt(pName[pName.length-1]) > 0){
		pName = pName.slice(0, pName.length-1);  
	}else{
		console.log("no number here");
	}
	switch (pName){
		case "Pawn":
  			piece.src = (pColour == "White") ? "https://media.discordapp.net/attachments/374624617651765248/1068584269536034906/WhitePawn.png" : "https://media.discordapp.net/attachments/374624617651765248/1068584209905635359/BlackPawn.png";
			break;
		case "Rook":
			piece.src = (pColour == "White") ? "https://media.discordapp.net/attachments/374624617651765248/1068584270035173429/WhiteRook.png" : "https://media.discordapp.net/attachments/374624617651765248/1068584208412442704/BlackRook.png";
			break;
		case "Knight":
			piece.src = (pColour == "White") ? "https://media.discordapp.net/attachments/374624617651765248/1068584269284392970/WhiteKnight.png" : "https://media.discordapp.net/attachments/374624617651765248/1068584209632997487/BlackKnight.png";
			break;
		case "Bishop":
			piece.src = (pColour == "White") ? "https://media.discordapp.net/attachments/374624617651765248/1068584268718162081/WhiteBishop.png" : "https://media.discordapp.net/attachments/374624617651765248/1068584208936742982/BlackBishop.png";
			break;
		case "Queen":
			piece.src = (pColour == "White") ? "https://media.discordapp.net/attachments/374624617651765248/1068584269783507015/WhiteQueen.png" : "https://media.discordapp.net/attachments/374624617651765248/1068584207959474307/BlackQueen.png";
			break;
		case "King":
			piece.src = (pColour == "White") ? "https://media.discordapp.net/attachments/374624617651765248/1068584269011755008/WhiteKing.png" : "https://media.discordapp.net/attachments/374624617651765248/1068584209268097124/BlackKing.png";
			break;
			
	}
}

function setBoard(){
	//White
	setPiece("White", "Pawn1",1,7);
	setPiece("White", "Pawn2",2,7);
	setPiece("White", "Pawn3",3,7);
	setPiece("White", "Pawn4",4,7);
	setPiece("White", "Pawn5",5,7);
	setPiece("White", "Pawn6",6,7);
	setPiece("White", "Pawn7",7,7);
	setPiece("White", "Pawn8",8,7);
	
	setPiece("White", "Rook1",1,8);
	setPiece("White", "Rook2",8,8);
	
	setPiece("White", "Knight1",2,8);
	setPiece("White", "Knight2",7,8);
	
	setPiece("White", "Bishop1",3,8);
	setPiece("White", "Bishop2",6,8);
	
	setPiece("White", "Queen",4,8);
	setPiece("White", "King",5,8);
	
	//Black
	setPiece("Black", "Pawn1",8,2);
	setPiece("Black", "Pawn2",7,2);
	setPiece("Black", "Pawn3",6,2);
	setPiece("Black", "Pawn4",5,2);
	setPiece("Black", "Pawn5",4,2);
	setPiece("Black", "Pawn6",3,2);
	setPiece("Black", "Pawn7",2,2);
	setPiece("Black", "Pawn8",1,2);
	
	setPiece("Black", "Rook1",8,1);
	setPiece("Black", "Rook2",1,1);
	
	setPiece("Black", "Knight1",7,1);
	setPiece("Black", "Knight2",2,1);
	
	setPiece("Black", "Bishop1",6,1);
	setPiece("Black", "Bishop2",3,1);
	
	setPiece("Black", "Queen",4,1);
	setPiece("Black", "King",5,1);
}

function resetBoard(){
	//white
	document.getElementById("T1:7").appendChild(document.getElementById("WPawn1"));
	document.getElementById("T2:7").appendChild(document.getElementById("WPawn2"));
	document.getElementById("T3:7").appendChild(document.getElementById("WPawn3"));
	document.getElementById("T4:7").appendChild(document.getElementById("WPawn4"));
	document.getElementById("T5:7").appendChild(document.getElementById("WPawn5"));
	document.getElementById("T6:7").appendChild(document.getElementById("WPawn6"));
	document.getElementById("T7:7").appendChild(document.getElementById("WPawn7"));
	document.getElementById("T8:7").appendChild(document.getElementById("WPawn8"));
	
	document.getElementById("T1:8").appendChild(document.getElementById("WRook1"));
	document.getElementById("T8:8").appendChild(document.getElementById("WRook2"));
	
	document.getElementById("T2:8").appendChild(document.getElementById("WKnight1"));
	document.getElementById("T7:8").appendChild(document.getElementById("WKnight2"));
	
	document.getElementById("T3:8").appendChild(document.getElementById("WBishop1"));
	document.getElementById("T6:8").appendChild(document.getElementById("WBishop2"));
	
	document.getElementById("T4:8").appendChild(document.getElementById("WQueen"));
	document.getElementById("T5:8").appendChild(document.getElementById("WKing"));
	
	//Black
	document.getElementById("T8:2").appendChild(document.getElementById("BPawn1"));
	document.getElementById("T7:2").appendChild(document.getElementById("BPawn2"));
	document.getElementById("T6:2").appendChild(document.getElementById("BPawn3"));
	document.getElementById("T5:2").appendChild(document.getElementById("BPawn4"));
	document.getElementById("T4:2").appendChild(document.getElementById("BPawn5"));
	document.getElementById("T3:2").appendChild(document.getElementById("BPawn6"));
	document.getElementById("T2:2").appendChild(document.getElementById("BPawn7"));
	document.getElementById("T1:2").appendChild(document.getElementById("BPawn8"));
	
	document.getElementById("T8:1").appendChild(document.getElementById("BRook1"));
	document.getElementById("T1:1").appendChild(document.getElementById("BRook2"));
	
	document.getElementById("T7:1").appendChild(document.getElementById("BKnight1"));
	document.getElementById("T2:1").appendChild(document.getElementById("BKnight2"));
	
	document.getElementById("T6:1").appendChild(document.getElementById("BBishop1"));
	document.getElementById("T3:1").appendChild(document.getElementById("BBishop2"));
	
	document.getElementById("T4:1").appendChild(document.getElementById("BQueen"));
	document.getElementById("T5:1").appendChild(document.getElementById("BKing"));
	
}

//Check Movement
function sendCommand(com, originPos="_blank", destinationPos="_blank", pieceMoved="_blank", pieceTaken="_blank"){
	return new Promise(function(resolve, reject) {
		var xhr = new XMLHttpRequest();
	  		
	 	 	xhr.open("POST", getDomainAddress() + `ChessServlet`);
	 	 	
	 	 	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    xhr.onload = function() {
		      if (this.status >= 200 && this.status < 300) {
		        resolve(xhr.response);
		      } else {
		        reject({
		          status: this.status,
		          statusText: xhr.statusText
		        });
		      }
		    };
		    xhr.onerror = function() {
		      reject({
		        status: this.status,
		        statusText: xhr.statusText
		      });
		    };
			let sessionName = localStorage.getItem("sessionName");
			let username = localStorage.getItem("username");
		
			console.log(`pieceMoved=${pieceMoved}&pieceTaken=${pieceTaken}&originPos=${originPos}&destinationPos=${destinationPos}&command=${com}&sessionName=${sessionName}&username=${username}`)
	  		xhr.send(`pieceMoved=${pieceMoved}&pieceTaken=${pieceTaken}&originPos=${originPos}&destinationPos=${destinationPos}&command=${com}&sessionName=${sessionName}&username=${username}`);
	  		console.log(com +" Command Sent!");
	});
}

//resyncing board pieces with server-side pieces
function resyncPieces(){
	
	sendCommand("resync")
		.then(function(response) {
			console.log("Resync Sent");
			console.log(JSON.parse(response));
			let serverPieces = JSON.parse(response);

			let pieces = ["WKing", "WQueen", "WKnight1", "WKnight2", "WBishop1", "WBishop2", "WRook1", "WRook2", "WPawn1", "WPawn2", "WPawn3", "WPawn4", "WPawn5", "WPawn6", "WPawn7", "WPawn8",
						"BKing", "BQueen", "BKnight1", "BKnight2", "BBishop1", "BBishop2", "BRook1", "BRook2", "BPawn1", "BPawn2", "BPawn3", "BPawn4", "BPawn5", "BPawn6", "BPawn7", "BPawn8"];
			
			for (var i = 0; i < pieces.length; i++) {
				//console.log("Resync Vars: " + pieces[i] + " | " + serverPieces[pieces[i]]);
				if(serverPieces[pieces[i]] != "taken"){
					document.getElementById(serverPieces[pieces[i]]).append(document.getElementById(pieces[i]));	
				}else if(serverPieces[pieces[i]] == "taken"){
					//var takenPiece = document.getElementById(pieces[i].id);
						//takenPiece.classList.toggle("hide");
						document.getElementById("takenbin").appendChild(document.getElementById(pieces[i]));
				}else{
					console.log("Sync list value unrecognised");
				}
			}
		})
		.catch(function(error) {
			console.log(error);
			console.log("Resync command failed!");
		});
}


//Drag&Drop
	function allowDrop(ev) {
		ev.preventDefault();
		/*
		var element = document.getElementById(ev.target.id)
		console.log("Tile to move to: "+ev.target.id + "-->" + element.children.length + " & " + element.id[0]);
		if(element.id[0] != "T" && element.parentElement.children.length > 0){
			console.log("Can't move there!!!");
		}else if(element.id[0] == "T" && element.children.length > 0){
			console.log("Can't move there!!");
		}else{
			console.log("Can move!");
			ev.preventDefault();	
		}
		*/
	}

	function drag(ev) {
		console.log(ev.target.id + " being dragged");
	    ev.dataTransfer.setData("piece", ev.target.id);
	    ev.dataTransfer.setData("tile", ev.target.parentElement.id);
	    console.log("Grabbed piece parent: "+ev.target.parentElement.id);
	    
	}

	function drop(ev) {
		ev.preventDefault();
		var data = ev.dataTransfer.getData("piece");
		var originTile = ev.dataTransfer.getData("tile");
		var piece = document.getElementById(data);
		
		var destination;
		//Incase the user is attacking, the destination tile will need to be obtain through the parent of the piece that is being attacked.
		if(ev.target.id[0]=="T"){
			var destination = ev.target.id;
		}else{
			var destination = document.getElementById(ev.target.id).parentElement.id;
			var enemyID = ev.target.id;
			console.log("Current Destination: " + document.getElementById(ev.target.id).parentElement.id);
		}
		if(originTile[0] == "T" && destination[0] == "T" ){
			console.log(originTile + "|" + destination);
			var pieceMoved = document.getElementById(originTile).firstChild.id; 
		}else if(originTile[0] == "T" && destination[0] != "T"){
			var pieceMoved = document.getElementById(originTile).firstChild.id; 
			var pieceTaken = document.getElementById(destination).firstChild.id;
		}else{
			console.log("movement positions invalid");
		}
		
		//communication with server board
		sendCommand("move",originTile,destination, pieceMoved, ev.target.id)
			.then(function(response) {//configures how the response should be handled
		      console.log("Response: " + response);
		      var resp = JSON.parse(response);
		      
		      if(resp.canMove && resp.isCastling){
				  console.log("Piece: " + piece.id + " has castled");
				    
				  document.getElementById(resp.castleKingPos).appendChild(document.getElementById(piece.id));
				  document.getElementById(resp.castleRookPos).appendChild(document.getElementById(enemyID));
				  
					
			  }else if(resp.canMove||resp.canAttack){
				  if(resp.canAttack){
						var enemyPiece = document.getElementById(enemyID);
						console.log(enemyID + " has been hidden");
						enemyPiece.classList.toggle("hide");
						document.getElementById("takenbin").appendChild(enemyPiece);
						console.log("Attack made");
					}
					if(getPageName() == "ChessGame.jsp"){
		  	  			whiteMoveAudio.play();
		  			}else if(getPageName() == "ChessGame_alt.jsp"){
			  			blackMoveAudio.play();
		  			}
					
				    console.log("Piece: " + piece.id + " has moved to " + destination);
				    
				    document.getElementById(destination).appendChild(document.getElementById(piece.id));
					console.log(document.getElementById(destination).childElementCount);
					
					if(resp.canPromote == true){
						promotePawn(piece.id)
					}
					
			  }else{
				  console.log("Can't move there!");
			  }
			  
			  if(resp.checkmate == true){
				checkmate(resp.checkColour);
			  }
		      //alert(response);
		    })
		    .catch(function(error) {
		      console.log(error);
		      console.log("move command failed!");
		    });

	}
	
function resetGame(){
	sendCommand("reset")
		.then(function(response) {
			console.log("Game Reset");
		})
		.catch(function(error) {
			console.log(error);
			console.log("Reset command failed!");
		});
		
	pieces = document.getElementsByClassName("chess-piece");
	//console.log(pieces);
	for (var i = 0; i < pieces.length; i++) {
	        //toggle the "active" class
		pieces[i].setAttribute("class","chess-piece");
	}
	resetBoard();
	
}

function startGame(){
	sendCommand("start")
		.then(function(response) {
			console.log("Game Started");
		})
		.catch(function(error) {
			console.log(error);
			console.log("Start command failed!");
		});	
}

function login(){
	//var username = prompt("Please enter a username:");
	var username = document.getElementById("usernameInput").value;
	if(validateUserInput(username)){
		localStorage.setItem("username", username);
		window.location.href = "ChessSearch.jsp"
	}else{
		alert("That is not a valid username!");
	}
}

function validateUserInput(str){
	return /^[a-zA-Z0-9]+$/.test(str);
}

function logout(){
		localStorage.setItem("username", "");
		localStorage.setItem("sessionName", "");
		window.location.href = "ChessLogin.jsp"
}

function drawCheck(){
	var piece_x = prompt("Please enter the x position of the piece you would like to check: ");
	var piece_y = prompt("Please enter the y position of the piece you would like to check: ");
	
	sendCommand("drawCheck", piece_x, piece_y)
				    	.then(function(response) {
							console.log("drawCheck for " + piece_x + "|" + piece_y + " sent");
						})
						.catch(function(error) {
					      console.log(error);
					      console.log("drawCheck command failed!");
		    			});
}

function createSession(){
	localStorage.setItem("sessionName",document.getElementById("createSessionName").value);
	selectedMins = document.querySelector('input[name="timerMins"]:checked').value;
	
	sessionConnect("create", localStorage.getItem("username"), localStorage.getItem("sessionName"), selectedMins)
				    	.then(function(response) {
							console.log("Response: " + response);
		      				var resp = JSON.parse(response);
							if(resp.sessionCreated){
								console.log("Session Created: " + localStorage.getItem("sessionName"));
								//window.location.href = "ChessGame_alt.jsp";
								document.getElementById("sessionInput").style.display = "none";
								document.getElementById("searchMessage").classList.toggle("toggle");
								document.getElementById("searchMessage").innerHTML = "Waiting for another player to join...";
								openConnection();
							}else{
								console.log("Unable to create session: " + localStorage.getItem("sessionName"));
							}
						})
						.catch(function(error) {
					      console.log(error);
					      console.log("Create Session command failed!");
		    			});
}

function joinSession(){
	localStorage.setItem("sessionName",document.getElementById("sessionName").value);
	sessionConnect("join", localStorage.getItem("username"), localStorage.getItem("sessionName"))
				    	.then(function(response) {
							console.log("Response: " + response);
		      				var resp = JSON.parse(response);
							if(resp.sessionCreated){
								localStorage.setItem("opponentName",resp.opponent);
								
								console.log("Session Joined: " + localStorage.getItem("sessionName"));
								//window.location.href = "ChessGame.jsp";
								document.getElementById("sessionInput").style.display = "none";
								document.getElementById("searchMessage").classList.toggle("toggle");
								document.getElementById("searchMessage").textContent = "Waiting for "+resp.opponent+" to start the game...";
								openConnection();
							}else{
								console.log("Unable to join session: " + localStorage.getItem("sessionName"));
							}
						})
						.catch(function(error) {
					      console.log(error);
					      console.log("Join Session command failed!");
		    			});
}

function sessionConnect(com, user, sName, mins=0){
	return new Promise(function(resolve, reject) {
		var xhr = new XMLHttpRequest();
	  		
	 	 	xhr.open("POST", getDomainAddress() + `ChessServlet`);
	 	 	
	 	 	xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		    xhr.onload = function() {
		      if (this.status >= 200 && this.status < 300) {
		        resolve(xhr.response);
		      } else {
		        reject({
		          status: this.status,
		          statusText: xhr.statusText
		        });
		      }
		    };
		    xhr.onerror = function() {
		      reject({
		        status: this.status,
		        statusText: xhr.statusText
		      });
		    };
			
	  		xhr.send(`command=${com}&username=${user}&sessionName=${sName}&minutes=${mins}`);
	  		console.log(com +"Session Command Sent!");
	});
}

function openConnection(){
	var xhr = new XMLHttpRequest();
		
  		xhr.onreadystatechange = function() {
    		if (xhr.readyState == 3) {
      			// Update received
      			console.log("Update Received");
      			processUpdate(xhr.responseText);
      			openConnection();
    		}
  		};
  		
  		let username = localStorage.getItem("username");
  		let sessionName = localStorage.getItem("sessionName");
 	 	xhr.open("GET", getDomainAddress() + `ChessServlet?username=${username}&sessionName=${sessionName}`);
		
  		xhr.send();
  		console.log("Session Connected");
}

function processUpdate(response) {
	var update = JSON.parse(response);
	var com = update.command;
  	console.log("Incoming command: "+com);
  		if(update.command == "joined"){
			document.getElementById("searchMessage").innerHTML = update.opponent + " has joined!";
			localStorage.setItem("opponentName",update.opponent);
			document.getElementById("startBtn").classList.toggle("toggle");
			//document.getElementById("sessionSearching").appendChild("")
		}else if(update.command == "redirect"){
			localStorage.setItem("colour", update.colour);
			window.location.href = (update.colour == "white") ? "ChessGame.jsp" : "ChessGame_alt.jsp";
		}else if(update.command == "resyncMove"){
			document.getElementById(update.tileMoveTo).appendChild(document.getElementById(update.pieceToMove).firstChild);
			
			if(getPageName() == "ChessGame.jsp"){
		  		blackMoveAudio.play();
		  	}else if(getPageName() == "ChessGame_alt.jsp"){
				whiteMoveAudio.play();
		 	}
			
			
		}else if(update.command == "resyncCastling"){
			document.getElementById(update.tileMoveTo_king).appendChild(document.getElementById(update.pieceToMove_king).firstChild);
			document.getElementById(update.tileMoveTo_rook).appendChild(document.getElementById(update.pieceToMove_rook).firstChild);
			
			if(getPageName() == "ChessGame.jsp"){
		  		blackMoveAudio.play();
		  	}else if(getPageName() == "ChessGame_alt.jsp"){
				whiteMoveAudio.play();
		 	}
		 	
		 	
		}else if(update.command == "resyncAttack"){			
			var enemyPiece = document.getElementById(document.getElementById(update.tileMoveTo).firstChild.id);
				enemyPiece.classList.toggle("hide");
			document.getElementById("takenbin").appendChild(enemyPiece);
			document.getElementById(update.tileMoveTo).appendChild(document.getElementById(update.pieceToMove).firstChild);
			
			if(getPageName() == "ChessGame.jsp"){
		  		blackMoveAudio.play();
		  	}else if(getPageName() == "ChessGame_alt.jsp"){
				whiteMoveAudio.play();
		 	}
			
		}else if(update.command == "endGame"){		
			document.getElementById("checkmate-panel").classList.toggle("toggle");
			document.getElementById("winner").innerHTML = update.winner;
			document.getElementById("reason").innerHTML = update.endType;
			
			resyncPieces();
			
			let pieces = document.getElementsByClassName("chess-piece");
			for (var i = 0; i < pieces.length; i++) {
	        	pieces[i].setAttribute("draggable","false");
			}
			
			
		}else if(update.command == "promotePawn"){	
					
			var piece = document.getElementById(update.pieceID);
			
			var newSrc;
			
			//Chooses the correct image for the opponent promotion using the src of existing pieces. 
			//This allows for the images to be replaced in future without having to replace all srcs of every single piece and function.
			switch (update.newType){
				case "QUEEN":
					newSrc = (getPageName == "ChessGame.jsp")? "BQueen" : "WQueen";
					break;
				case "BISHOP":
					newSrc = (getPageName == "ChessGame.jsp")? "BBishop1" : "WBishop1";
					break;
				case "KNIGHT":
					newSrc = (getPageName == "ChessGame.jsp")? "BKnight1" : "WKnight1";
					break;
				case "ROOK":
					newSrc = (getPageName == "ChessGame.jsp")? "BRook1" : "WRook1";
					break;
				default:
					newSrc = (getPageName == "ChessGame.jsp")? "BPawn1" : "WPawn1";
					console.log("!!! Failed to select Pawn Promotion Type !!!")
					break;
			}
			piece.setAttribute("src",document.getElementById(newSrc).getAttribute("src"));
			
		}else if(update.command == "reconnect"){			
			console.log("Reconnecting");
		}else{
			console.log("unknown command sent");
		}
}

function endGame(colour){
	document.getElementById("checkmate-panel").classList.toggle("toggle");
	document.getElementById("checkedColour").innerHTML = colour;
	
	pieces = document.getElementsByClassName("chess-piece");
	for (var i = 0; i < pieces.length; i++) {
	        pieces[i].setAttribute("draggable","false");
	}
}

function promotePawn(pieceID){//TODO - promote Pawn
	console.log("This is the Promotion ID: ");
	console.log(pieceID);
	var piece = document.getElementById(pieceID);
	
	promotionMenu(pieceID);

	function promotionMenu(pID){
		//document.getElementById("board-cover").classList.toggle("activate");
		//document.getElementById("promotion-menu").classList.toggle("show");
		document.getElementById("board-cover").style.visibility = "visible"
		document.getElementById("promotion-menu").style.visibility = "visible"
		
		document.getElementById("QueenP").addEventListener("click", promotionPieceClicked.bind(null, pID));
		document.getElementById("BishopP").addEventListener("click", promotionPieceClicked.bind(null, pID));
		document.getElementById("KnightP").addEventListener("click", promotionPieceClicked.bind(null, pID));
		document.getElementById("RookP").addEventListener("click", promotionPieceClicked.bind(null, pID));
	}
	
	function promotionPieceClicked(id, event){
		console.log(id);
		console.log("--- Promoted to " + event.target.id.substring(0,event.target.id.length-1));
		
		var piece = document.getElementById(id);
		piece.setAttribute("src",document.getElementById(event.target.id).getAttribute("src"));
		
		
		sendCommand("promotePawn", piece.id, event.target.id.substring(0,event.target.id.length-1),piece.parentElement.id[1],piece.parentElement.id[3])
			.then(function(response) {
				console.log("Pawn Promotion sent!");
			})
			.catch(function(error) {
				console.log("Promote Pawn command failed!");
				console.log(error);
			});
			
			document.getElementById("board-cover").style.visibility = "hidden"
			document.getElementById("promotion-menu").style.visibility = "hidden"
		
			document.getElementById("QueenP").removeEventListener("click", promotionPieceClicked);
			document.getElementById("BishopP").removeEventListener("click", promotionPieceClicked);
			document.getElementById("KnightP").removeEventListener("click", promotionPieceClicked);
			document.getElementById("RookP").removeEventListener("click", promotionPieceClicked);
			console.log("Removed Event!!");
			
			
		}
}
	
function changeTab(event){
	let joinTab = document.getElementById("join-tab");
	let createTab = document.getElementById("create-tab");
	
	let joinPage = document.getElementById("join-page");
	let createPage = document.getElementById("create-page");
	
	if(event.target.id == "join-tab"){
		//console.log("changing to join tab");
		joinTab.classList.toggle("unselected");
		joinPage.classList.toggle("unselected");
		
		createTab.classList.toggle("unselected");
		createPage.classList.toggle("unselected");
	}else if(event.target.id == "create-tab"){
		//console.log("changing to create tab");
		createTab.classList.toggle("unselected");
		createPage.classList.toggle("unselected");
		
		joinTab.classList.toggle("unselected");
		joinPage.classList.toggle("unselected");
	}
	
}
/*
function promotionMenu(){
	document.getElementById("board-cover").classList.toggle("activate");
	document.getElementById("promotion-menu").classList.toggle("show");
	
	document.getElementById("QueenP").addEventListener("click", promotionPieceClicked);
	document.getElementById("BishopP").addEventListener("click", promotionPieceClicked);
	document.getElementById("KnightP").addEventListener("click", promotionPieceClicked);
	document.getElementById("RookP").addEventListener("click", promotionPieceClicked);
}

function promotionPieceClicked(event){
	console.log("--- Promoted to " + event.target.id.substring(0,event.target.id.length-1));
		
	document.getElementById("board-cover").classList.toggle("activate");
	document.getElementById("promotion-menu").classList.toggle("show");
	
	document.getElementById("QueenP").removeEventListener("click", promotionPieceClicked);
	document.getElementById("BishopP").removeEventListener("click", promotionPieceClicked);
	document.getElementById("KnightP").removeEventListener("click", promotionPieceClicked);
	document.getElementById("RookP").removeEventListener("click", promotionPieceClicked);
}
	
	sendCommand("canMove",originTile,destination)
			.then(function(response) {//configures how the response should be handled
		      console.log("Response: " + response);
		      var resp = JSON.parse(response);
		      var isAttacking = resp.canAttack;
		      if(resp.canMove||isAttacking){
				    console.log("Piece: " + piece.id + " has moved to " + destination);
				    
				    document.getElementById(destination).appendChild(piece);
				    sendCommand("move", originTile, destination)
				    	.then(function(response) {
							console.log(document.getElementById(destination).childElementCount);
							if(isAttacking){
								console.log(document.getElementById(destination).firstChild.id + " has been hidden");
								document.getElementById(document.getElementById(destination).firstChild.id).classList.toggle("hide");
								
							}
							console.log("Movement sent");
						})
						.catch(function(error) {
					      console.log(error);
					      console.log("Move command failed!");
		    			});
			  }else{
				  console.log("Can't move there!");
			  }
		      //alert(response);
		    })
		    .catch(function(error) {
		      console.log(error);
		      console.log("canMove command failed!");
		    });*/
	
	
	