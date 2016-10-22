
# Ticketing Service

A Spring boot app that implements a ticketting hold/reservation system for a venue.


## ASSUMPTIONS

- No login required, a user uses his/her email address to identify.
- The Seat data is loaded up once, and modified while the server is running. It does not attempt to save to disk. This means that once the server is shutdown, there is no way to restore any data.
- There are 260 seats (26 rows, one for each letter and 10 seats per row). They are all initialized to the 'available' status.
- Seats can be held for default of 60 seconds before the customer's hold expires. This can be configured at startup with the `-expire_seconds <seconds>` flag.

    
## APP REQUIREMENTS

1. Java 8 JRE
2. git (to clone this)
3. maven
4. Browser to run the UI 

    
## HOW TO BUILD/TEST

1. Clone the repo onto a git installed machine with this command `https://github.com/Med116/samsclub.git`

2. `cd` into the repo, and issue the command `mvn clean install`. This will install a runnable jar file into the `target` directory

3. `cd` into the target directory, and issue the command `java -jar ticketapp-0.0.1-SNAPSHOT.jar` . This runs the spring boot server. 

4. Open up a web browser to [localhost:8080](http://localhost:8080 "localhost:8080").

5. To test the app, stop the server (Hit Ctrl-C to stop), and then run `mvv test` in the root directory (where the .pom file resides)
 