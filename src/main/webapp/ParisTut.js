/**
 * 
 */
 let aBox
 let cBox

function priceCalc(adults, children){
    return adults*12 + children*5;
}

function receipt(){
    let adultTickets = aBox.value;
    let childrenTickets = cBox.value;
    let r = "Your Tickets:";
    if(!(adultTickets == null || childrenTickets == null)){
        if (adultTickets < 0 || childrenTickets < 0){
            alert("Invalid Ticket Amount! Please only use positive numbers.");
        }else{
            let totalPrice = priceCalc(adultTickets, childrenTickets);

            for(let x = adultTickets; x > 0; x--){
                r += ("\nAdult-Ticket#"+x);
            }
            for(let x = childrenTickets; x > 0; x--){
                r +=("\nChild-Ticket#"+x);
            }

            r += ("\nTotal price: \xA3" + totalPrice);
            alert(r);
        }
    }else{
        alert("Please enter a value for both child and adult tickets!");
    }
}

function hello(){
    alert("Hello There!");
}

window.onload = function() {
    aBox = document.getElementById("adultTickets");
    cBox = document.getElementById("childTickets");
    console.log(aBox.value);

    let buyButton = document.getElementById("buyButton");

    buyButton.onclick = function(){
        console.log("Clicked! " + aBox.value);
        //hello();
        receipt();
    }
}