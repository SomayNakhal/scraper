# Scraper 1.0

 * The grocery site url is to be provided on the command line to give flexibility.
 * Json output is sent to STDOUT.
 * Error messages are sent to STDERR.
 * To run the tests use:
```
mvn test
```
  * To run the application use:
```
mvn exec:java -Dexec.args="http://hiring-tests.s3-website-eu-west-1.amazonaws.com/2015_Developer_Scrape/5_products.html"
```
 * Uses:
	- Java 1.8
	- Maven 3.3.9
	- HtmlUnit 2.19
	- Jackson 2.7
	- JUnit 4.11
	- JMockit 1.9.5
	- JsonAssert 1.4