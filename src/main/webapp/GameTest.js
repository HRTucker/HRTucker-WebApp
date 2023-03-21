/**
 * 
 */
 window.onload = function() {
	 //TODO text saving(temp)
	 //loginOutput
	 var local_loginOutput = localStorage.getItem("loginOutput");
	 if(local_loginOutput){
		 document.getElementById("login-output").innerText = local_loginOutput;
	 }
	 //PlayMessage
	 var local_pm = localStorage.getItem("PlayMessage");
	 if(local_pm){
		 document.getElementById("PlayMessage").innerText = local_pm;
	 }
	 
 	
 	function makeRequest(url, data) {
		 console.log(document.getElementById('name-input').value);
		 console.log(document.getElementById('age-input').value);
	  return new Promise(function(resolve, reject) {
	    var xhr = new XMLHttpRequest();
	    xhr.open('POST', url);
	    //xhr.setRequestHeader("Content-Type", "application/json");
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
	    console.log(JSON.stringify(data));
	    //let toSend = 'name=${data.name}&age=${data.age}';
	    xhr.send(`name=${data.name}&age=${data.age}`);
	  });
	}

	function chessComm(url, opt) {
		  return new Promise(function(resolve, reject) {
		    var xhr = new XMLHttpRequest();
		    xhr.open('POST', url);
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
		    
		    xhr.send(`option=${opt}`);
		  });
	}


	//sets up an object that can be read on the back-end, all variables will travel with it
	document.getElementById('submit-button').addEventListener('click', function() {
	  
	  var inputData = {
	    name: document.getElementById('name-input').value,
	    age: document.getElementById('age-input').value
	  };
	  
	  let formOut = document.getElementById("login-output");
		
	  makeRequest('http://192.168.4.28:8080/BasicWebApp/TestServlet', inputData)
	    .then(function(response) {//configures how the response should be handled
	      localStorage.setItem("loginOutput",response);
	      formOut.innerText = response;
	      console.log(response);
	      //alert(response);
	    })
	    .catch(function(error) {
	      console.log(error);
	      alert("it failed!");
	    });
	});

	document.getElementById('playBtn').addEventListener('click', function() {
	  
	  let messageBox = document.getElementById("PlayMessage");
		
	  chessComm('http://192.168.4.28:8080/BasicWebApp/GameServlet', "start")
	    .then(function(response) {//configures how the response should be handled
	      localStorage.setItem("PlayMessage",response);
	      messageBox.innerText = response;
	      console.log(response);
	      //alert(response);
	    })
	    .catch(function(error) {
	      console.log(error);
	      alert("it failed!");
	    });
	});

}