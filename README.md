# computeDensityOfZone

developed using JRE 10.0.2

Unit Testing with JUnit 5.3.1

API using RestEasy 3.6.1 (+Tomcat 9.0)

# To run

use Maven to get dependencies

./computeDensityOfZone (TsvFilePath)
to execute the program

Api tested with Tomcat 9.0 server with json data being sent.

URIs :
- http://localhost:8080/api/poi/getBiggestAreas
- http://localhost:8080/api/poi/getNumber?lon=-7&lat=6.5

The api uses a absolute link for the .tsv file so you might want to change it