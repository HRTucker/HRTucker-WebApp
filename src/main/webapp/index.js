function printTime() {
	    var d = new Date();
	    var hours = d.getHours();
	    var mins = d.getMinutes();
	    var secs = d.getSeconds();
	    let time = document.getElementById("time");
	    //time.innerText = "Current Time: " + hours+":"+mins+":"+secs;
	    time.innerText = `Current Time: ${(hours < 10) ? "0"+hours : hours}:${(mins < 10) ? "0"+mins : mins }:${(secs < 10) ? "0"+secs : secs }`
	}
onload = function(){
	printTime();
	setInterval(printTime, 1000);
	
	
	console.log(magic(1, 2, 3, 4, 5, 6));
}

function magic(...nums) {
  let sum = 0;
  nums.filter(n => n % 2 == 0).map(el => sum+= el);
  return sum;
}
