
# Ticketing Service
    A Spring boot app that makes *these assumptions* :
    - The data is loaded up once, and modified while the server is running. It does not attempt to save to disk.
    - There are 260 seats (26 rows, one for each letter and 10 seats per row). They are all initialized to the 'available' status.
    - Seats can be held for default of 60 seconds before the customer's hold expires. This can be configured at startup with the `-expire_seconds <seconds>` flag.
    
## APP REQUIREMENTS

1. Java 8 JRE
2. git
3. maven
4. Browser to run the UI 
    
## HOW TO BUILD

1. Clone the repo onto a git installed machine with this command `https://github.com/Med116/samsclub.git`

2. `cd` into the repo, and issue the command `mvn clean install`. This will install a runnable jar file into the `target` directory

3. `cd` into the target directory, and issue the command `java -jar ticketapp-0.0.1-SNAPSHOT.jar` . This runs the spring boot server. 

4. open up a web browser to [localhost:8080](http://localhost:8080 "localhost:8080").