
# Ticketing Service

A Spring boot app that implements a ticketting hold/reservation system for a venue.


## ASSUMPTIONS

- No login required, a user uses his/her email address to identify.
- The Seat data is loaded up once, and modified while the server is running. It does not attempt to save to disk. This means that once the server is shutdown, there is no way to restore any data.
- There are 260 seats (26 rows, one for each letter and 10 seats per row). They are all initialized to the 'available' status.
- Seats can be held for default of 60 seconds before the customer's hold expires. This can be configured at startup with the `--expire.seconds=<seconds>` flag. (See Configuration section for details.)
- Seats are held by the best seats first, you cannot pick your seats. Row A is the best row, Z is the back row. Seats are held from the lowest row number to the highest row number.

    
## SYSTEM REQUIREMENTS

1. Java 8 JRE
2. git (to clone this)
3. maven
4. Browser to run the UI 

    
## HOW TO BUILD/TEST

1. Clone the repo onto a git installed machine with this command `https://github.com/Med116/samsclub.git`. To quickly run it, run the command `mvn spring-boot:run` from the root of the git repo. This is where the `pom.xml` file is located.

2.  To build with the tests : `cd` into the repo, and issue the command `mvn clean install`. This will install a runnable jar file into the `target` directory

3. To run after the build :`cd` into the target directory, and issue the command `java -jar ticketapp-0.0.1-SNAPSHOT.jar` . This runs the spring boot server. See the CONFIGURATION section below to configure the hold expiration in secoonds. 

4. Open up a web browser to [localhost:8080](http://localhost:8080 "localhost:8080").

5. To test the app, stop the server (Hit Ctrl-C to stop), and then run `mvn test` in the root directory (where the .pom file resides)
 
 
## CONFIGURATION
 
 You can configure the expiration of the seatHold object via the application.properties, or the command line. To configure this when running, run the server with the command (from the target directory) `java -jar ticketapp-0.0.1-SNAPSHOT.jar --expire.seconds=10` to make expiration happen in 10 seconds. You can alternatively open up the application.properties file and change the value
 there, and then re-build the app.