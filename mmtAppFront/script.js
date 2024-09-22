async function findCheapestRoute() {
    const startCity = document.getElementById("start-journey").value;
    const endCity = document.getElementById("end-journey").value;
    const apiUrl = `http://localhost:8080/routes/${startCity}/${endCity}/cheapest`;

    try {
        const response = await fetch(apiUrl);
        if (response.ok) {
            const data = await response.json();
            if (data.length === 1) {
                // Show a popup if no routes are available
                alert(`No route available between ${startCity} and ${endCity}.`);
                location.reload();
                return; // Exit the function
            }
            
            console.log(data);
            const routeDetails = document.getElementById("route-details");
            routeDetails.innerHTML = "";
            routeDetails.style.display = "block";
            for (let i = 0; i < data.length - 1; i++) {
                
                const route = data[i];
                
                const routeInfo = document.createElement("div");
                routeInfo.classList.add("route-info"); // Add this line to apply new styles
                routeInfo.innerHTML = `
                    <p>Route: ${i+1}</p>
                    <p>Time: ${route.TimeInHrs} hrs</p>
                    <p>Source City: ${route.SourceCity}</p>
                    <p>Destination City: ${route.destinationCity}</p>
                    <p>Mode of Transport: ${route.ModeOfTransport}</p>
                    <p>Fare: $${route.Fare}</p>
                `;
                routeDetails.appendChild(routeInfo);
            }

            const routeInfo = document.createElement("div");
            const route = data[data.length-1];
            routeInfo.innerHTML = `
               
                <p>Total Time: ${route.TotalTime} hrs</p>
                <p>Tota Fare: ${route.TotalFare}</p>
            
        
            `;
            routeDetails.appendChild(routeInfo);
        } else {
            // Handle the case when the response is not okay (e.g., show an error message).
            alert(`No route available between ${startCity} and ${endCity}.`);
            location.reload(); // Refresh the page after alert
            return;
        }
    } catch (error) {
        // Handle any network errors or other exceptions.
        console.error("Error:", error);
        alert("A network error occurred. Please check your connection.");
        location.reload();
        return;
    }
}

document.getElementById("cheapest-route").addEventListener("click", findCheapestRoute);



async function findFastestRoute() {
    const startCity = document.getElementById("start-journey").value;
    const endCity = document.getElementById("end-journey").value;
    const apiUrl = `http://localhost:8080/routes/${startCity}/${endCity}/fastest`;

    try {
        const response = await fetch(apiUrl);
        console.log(response);
        if (response.ok) {
            const data = await response.json();
            console.log(data);
            const routeDetails = document.getElementById("route-details");
            routeDetails.innerHTML = "";
            if (data.length === 1) {
                // Show a popup if no routes are available
                alert(`No route available between ${startCity} and ${endCity}.`);
                location.reload();
                return; // Exit the function
            }

            routeDetails.style.display = "block";
            for (let i = 0; i < data.length - 1; i++) {
                
                const route = data[i];
                
                const routeInfo = document.createElement("div");
                routeInfo.classList.add("route-info"); // Add this line to apply new styles
                routeInfo.innerHTML = `
                    <p>Route: ${i+1}</p>
                    <p>Time: ${route.TimeInHrs} hrs</p>
                    <p>Source City: ${route.SourceCity}</p>
                    <p>Destination City: ${route.destinationCity}</p>
                    <p>Mode of Transport: ${route.ModeOfTransport}</p>
                    <p>Fare: $${route.Fare}</p>
                `;
                routeDetails.appendChild(routeInfo);
                
            }

            const routeInfo = document.createElement("div");
            const route = data[data.length-1];
            routeInfo.innerHTML = `
               
                <p>Total Time: ${route.TotalTime} hrs</p>
                <p>Tota Fare: ${route.TotalFare}</p>
            
        
            `;
            routeDetails.appendChild(routeInfo);

           


        } else {
            // Handle the case when the response is not okay (e.g., show an error message).
            alert(`No route available between ${startCity} and ${endCity}.`);
            location.reload(); // Refresh the page after alert
            return;
        }
    } catch (error) {
        // Handle any network errors or other exceptions.
        console.error("Error:", error);
        alert("A network error occurred. Please check your connection.");
        location.reload();
        return;
    }
}

document.getElementById("cheapest-route").addEventListener("click", findCheapestRoute);

document.getElementById("fastest-route").addEventListener("click", findFastestRoute);

