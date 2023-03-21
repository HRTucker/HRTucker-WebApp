
window.onload = function(){
	if(sessionStorage.getItem("clientID") == null){
		var username = prompt("Enter your username:");
		sessionStorage.setItem("clientID", username);
		document.getElementById("username").textContent = username;
	}else{
		var username = sessionStorage.getItem("clientID");
		document.getElementById("username").textContent = username;
		document.getElementById("id").textContent = "identifier";
	}
	
	function sendMessage(msg) {
	  
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", "http://192.168.4.28:8080/BasicWebApp/ChatRoomServlet");
	  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	  let id = sessionStorage.getItem("clientID");
	  xhr.send(`message=${msg}&clientID=${id}&isJoining=0`);
	  console.log("MessageSent");
	  
	  var p = document.createElement("p");
	  p.id = "localMsg";
	  p.innerText = `(YOU)${id}: ${msg}`;
      //var node = document.createTextNode(`${msg} :${id}(YOU)`);
      //p.appendChild(node);
	  document.getElementById("ChatBox").appendChild(p);
	  
	  ChatStart();
	}
	
	function joinMessage(){
	  var xhr = new XMLHttpRequest();
	  xhr.open("POST", "http://192.168.4.28:8080/BasicWebApp/ChatRoomServlet");
	  xhr.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
	  let id = sessionStorage.getItem("clientID");
	  //console.log(`message="has join the chat!"&clientID=${id}&isJoining="true"`);
	  xhr.send(`message=has join the chat!&clientID=${id}&isJoining=1`);
	  //console.log("MessageSent");
	  ChatStart()
	}
		
	function ChatStart() {
		var xhr = new XMLHttpRequest();
		
  		xhr.onreadystatechange = function() {
    		if (xhr.readyState == 3) {
      			// Update received
      			processUpdate(xhr.responseText);
    		}
  		};
  		
  		let id = sessionStorage.getItem("clientID");
 	 	xhr.open("GET", `http://192.168.4.28:8080/BasicWebApp/ChatRoomServlet?clientID=${id}`);
		
  		xhr.send();
  		console.log("Chat Connected");
	}
	
	function processUpdate(update) {
  		console.log(update);
  		var p = document.createElement("p");
  		p.id = "incomingMsg";
  		//p.style.color = '#6600FF';
     	var node = document.createTextNode(update);
        p.appendChild(node);
	    document.getElementById("ChatBox").appendChild(p);
	    ChatStart()
	}
	
	ChatStart();
	
	joinMessage();
	
	document.getElementById('sendBtn').addEventListener('click', function() {
	  
	  let message = document.getElementById("messageInput").value;
	  document.getElementById("messageInput").value = "";
	  
	  sendMessage(message);
		
	});
	
	document.getElementById('messageInput').addEventListener("keydown", function(event){
		if(event.key === "Enter"){
			let message = document.getElementById("messageInput").value;
	 		document.getElementById("messageInput").value = "";
	  		sendMessage(message);
		}
	});
	
}