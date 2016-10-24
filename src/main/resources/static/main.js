			function getAvailableSeatsCount(){
				
				jQuery.post("/seats/available/", function(json){
					jQuery(".tix_count").text(json.availableNum);
				});
				
			}
			
			function getEmail(){
				
				var email = jQuery(".email input").val();
				if(email !== undefined){
					return email;
				}else{
					alert("You must enter in an email address");
					return false;
				}
			}
			
			function reserveSeats(){
				var holdId = jQuery(".action_reserve span").text();
				var data = {seatHoldId: holdId, email:getEmail};
				jQuery.post("/seats/reserve/", data, function(json){
					console.log(json);
					jQuery("#reserve_confirmation").text("SUCCESS: YOUR CODE: " + json.code + " SEATS : " + json.seats.length  );
					updateSeatsChart();			
				});	
			
			}
			
			function updateSeatsChart(){
			
				var root = jQuery("#seats_chart");
				jQuery.post("/seats/all/", function(json){
					
					console.log("ALL SEATS: ", json);
					
					
					root.html("<pre>" + JSON.stringify(json, null, "\t")  + "</pre>");
					
					
					json.map(function(seat){
							
							var seatIndex = seat.seatIndex;
							if(seat.held === true){
								jQuery("." + seatIndex ).addClass("seat_held");
							}else{
								jQuery("." + seatIndex ).removeClass("seat_held");
							}
							
							if(seat.reservationCode != null){
								jQuery("." + seatIndex ).addClass("seat_reserved");
							}else{
								jQuery("." + seatIndex ).removeClass("seat_reserved");
							}
						
					});
					
					// flash yellow to let know its been updated
					setTimeout(function(){
						root.find("pre").addClass("yellow");
						setTimeout(function(){
							root.find("pre").removeClass("yellow");
						}, 500);	
					}, 500);
				});
				
			
			}
			
			
			function holdSeats(){
				console.log("IN HOLD SEATS");
				var email = getEmail();
				console.log("EMAIL: " , email);
				
				if(email){
					
					var holdCount = jQuery(".action_hold input").val();
					
					if(holdCount !== undefined){
						
						jQuery.post("/seats/find-and-hold/",{email: email,numSeats: holdCount },  function(json){
							console.log("JSON: " , json);
							var holdId = json['holdId'];
							if(parseInt(holdId)){
								
								json.seatsHeld.map(function(seat){
									var seatIndex = seat.seatIndex;
									console.log("index:" + seatIndex);
									jQuery("." + seatIndex).addClass("seat_held");
									
									
								});
								jQuery(".action_reserve span").text(json.holdId);
								
							}
							getAvailableSeatsCount();
							updateSeatsChart();
							
						});
						
					}else{
						alert("Please enter # of tickets you would like to hold");
					}
							
				}
			}
			
			
			function updateSeatsLayout(){
				
				var alphabet = "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z";
				var alphaArr= alphabet.split(",");
				var root = jQuery("#seats_layout");
				for(var a= 0; a < alphaArr.length; a++ ){
					
					for(var i = 1; i <= 10; i++){
						var seatIndex =  alphaArr[a]+ i;
						var seatDiv = jQuery("<div class=\"seat " + seatIndex + "\"></div>");
						seatDiv.append(jQuery("<span>" + seatIndex + "</span>"));
						root.append(seatDiv);
						
					
					}
				}
				
			}
			
			jQuery(document).ready(function(){
				getAvailableSeatsCount();
				updateSeatsChart();
				updateSeatsLayout();
				jQuery(document).on("click", ".action_hold button", holdSeats);
				jQuery(document).on("click", ".action_reserve button", reserveSeats);
				
			});