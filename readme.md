# pollapp-example

## A basic poll app example built using [SpringMVC](https://spring.io/) [AngularJs](https://angularjs.org/) , [H2 Database](http://www.h2database.com) and [MySql](http://www.mysql.com/). 
 
 
 **How to run**:
  1. Download repository
  2. Execute mvn tomcat7:run
  3. Load localhost:8080/PollApp on your favorite browser
  
  If you don't want to use preset polls and users add -Dbuild.preset.skip=true argument
  
  If using mysql as database add -Pmysql-db as argument. Default user and password will be filtered from profile 
  configuration in pom.xml or can be overridden in application.properties
  
  Selenium tests will be run when executing mvn:verify with google chrome browser as default, which will require -Ddriver.binary.loc to be set.
  e.g.: mvn verify -Ddriver.binary.loc=<path to driver> -DskipUTs=true
  
