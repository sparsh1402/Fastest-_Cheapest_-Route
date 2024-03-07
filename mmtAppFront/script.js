async function findCheapestRoute() {
    const startCity = document.getElementById("start-journey").value;
    const endCity = document.getElementById("end-journey").value;
    const apiUrl = `http://localhost:8080/routes/${startCity}/${endCity}/cheapest`;

    try {
        const response = await fetch(apiUrl);
        if (response.ok) {
            const data = await response.json();
            console.log(data);
            const routeDetails = document.getElementById("route-details");
            routeDetails.innerHTML = "";

            // data.forEach(route => {
            //     const routeInfo = document.createElement("div");
            //     routeInfo.innerHTML = `
            //         <p>Time: ${route.TimeInHrs} hrs</p>
            //         <p>Source City: ${route.SourceCity}</p>
            //         <p>Destination City: ${route.destinationCity}</p>
            //         <p>Mode of Transport: ${route.ModeOfTransport}</p>
            //         <p>Fare: $${route.Fare}</p>
            //     `;
            //     routeDetails.appendChild(routeInfo);
            // });

            for (let i = 0; i < data.length - 1; i++) {
                
                const route = data[i];
                
                const routeInfo = document.createElement("div");
                
                routeInfo.innerHTML = `
                    <p>Route: ${i+1} <p>
                    <p>Time: ${route.TimeInHrs} hrs</p>
                    <p>Source City: ${route.SourceCity}</p>
                    <p>Destination City: ${route.destinationCity}</p>
                    <p>Mode of Transport: ${route.ModeOfTransport}</p>
                    <p>Fare: $${route.Fare}</p>
                    <hr>
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
        }
    } catch (error) {
        // Handle any network errors or other exceptions.
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
            for (let i = 0; i < data.length - 1; i++) {
                
                const route = data[i];
                
                const routeInfo = document.createElement("div");
                
                routeInfo.innerHTML = `
                    <p>Route: ${i+1} <p>
                    <p>Time: ${route.TimeInHrs} hrs</p>
                    <p>Source City: ${route.SourceCity}</p>
                    <p>Destination City: ${route.destinationCity}</p>
                    <p>Mode of Transport: ${route.ModeOfTransport}</p>
                    <p>Fare: $${route.Fare}</p>
                    <hr>
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
        }
    } catch (error) {
        // Handle any network errors or other exceptions.
    }
}

document.getElementById("cheapest-route").addEventListener("click", findCheapestRoute);

document.getElementById("fastest-route").addEventListener("click", findFastestRoute);

