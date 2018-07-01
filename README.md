# Tabcorp coding challenge

# Design / Architecture
I have chosen Spring Boot to create this application. The reason for choosing Spring Boot is its convention over configuration approach and ability to create standalone applications. Project structure was generated using Spring Initializr.

# Tech-Stack Used to build application:

1. Spring boot embedded Tomcat Server
2. In-Memory h2 Database (but can be used with any jdbc compliant database)
3. Spring Rest
4. Jackson : Java to Json mapping 
5. Unit Testing Persistence : JPA provided by Hibernate 
6. JUnit : Unit Test Framework 
7. Mockito : Mocking framework 
8. Maven : Build integration
9. Postman : testing Rest Services.

# Security Considerations / Assumptions:
1. Full OAuth based authentication/authorization was not implemented as it was out of scope for this excercise.	
2. REST services are secured using access token, which is hard coded for this application. But in a real world application it will be generated using OAuth etc.
3. Security services are provided by non-invasive http interceptors.

# Application Assumptions
Regarding (**Total number of bets sold per hour**) rest endpoint, requirement was not clear. So I have assumed that I will pass fromDate and toDate as request parameters to limit the number of records return in the response.

# How to run this application
* Clone the git repo using following command

```git clone https://github.com/jverma1/tabcorp.git```
	
   This will create a folder tabcorp in your current working directory.
* Execute command:

``` cd tabcorp ```
* Compile code using following command

``` mvn clean install ```
* Run the application using following command

``` java -jar ./target/bettingApp-0.0.1-SNAPSHOT.jar```
* Now application is started.

# Using application
* Create some bets using any Rest client (I have used Postman)
* endpoint URL: http://localhost:8080/bets/create 
	* http method= POST
	* add following two http headers:
 		* key=Authorization value=abc123
 		* key=Content-Type  value=application/json
	* Paste the following content to the body section then click send
```	
[
{
	"DateTime" : "2018-07-01 15:56",
	"BetType": "TRIFECTA",
	"PropNumber": 10101,
	"CustomerID" : 1080,
	"Investment": 100.00
},
{
	"DateTime" : "2018-07-01 12:56",
	"BetType": "WIN",
	"PropNumber": 10102,
	"CustomerID" : 1081,
	"Investment": 500.50
},
{
	"DateTime" : "2018-07-01 15:56",
	"BetType": "PLACE",
	"PropNumber": 10103,
	"CustomerID" : 1082,
	"Investment": 100.00
},
{
	"DateTime" : "2018-07-01 14:56",
	"BetType": "WIN",
	"PropNumber": 10104,
	"CustomerID" : 1081,
	"Investment": 200.50
},
{
	"DateTime" : "2018-07-01 13:56",
	"BetType": "QUADDIE",
	"PropNumber": 10105,
	"CustomerID" : 1083,
	"Investment": 100.00
},
{
	"DateTime" : "2018-07-01 16:56",
	"BetType": "DOUBLE",
	"PropNumber": 10106,
	"CustomerID" : 1084,
	"Investment": 500.50
}
]
```

* Run the following endpoint to get **Total investment per bet type** 
* endpoint URL: http://localhost:8080/bets/getInvestmentPerBetType
	* http method=GET
	* add following http header:
 	* key=Authorization value=abc123

click send and you should get the following results
```
[
    {
        "betType": "PLACE",
        "totalInvestment": 100
    },
    {
        "betType": "TRIFECTA",
        "totalInvestment": 100
    },
    {
        "betType": "DOUBLE",
        "totalInvestment": 500.5
    },
    {
        "betType": "QUADDIE",
        "totalInvestment": 100
    },
    {
        "betType": "WIN",
        "totalInvestment": 701
    }
]
```
* Run the following endpoint to get **Total investment per CustomerID** 
* endpoint URL: http://localhost:8080/bets/getInvestmentPerCustomerID
	* http method=GET
	* add following http header:
 	* key=Authorization value=abc123
click send and you should get the following results

```
[
    {
        "totalInvestment": 701,
        "customerID": 1081
    },
    {
        "totalInvestment": 100,
        "customerID": 1082
    },
    {
        "totalInvestment": 100,
        "customerID": 1083
    },
    {
        "totalInvestment": 500.5,
        "customerID": 1084
    },
    {
        "totalInvestment": 100,
        "customerID": 1080
    }
]
```
* Run the following endpoint to get **Total bets sold per bet type** 
* endpoint URL: http://localhost:8080/bets/getTotalBetsSoldPerBetType
	* http method=GET
	* add following http header:
 	* key=Authorization value=abc123
click send and you should get the following results

```
[
    {
        "betType": "PLACE",
        "betCount": 1
    },
    {
        "betType": "TRIFECTA",
        "betCount": 1
    },
    {
        "betType": "DOUBLE",
        "betCount": 1
    },
    {
        "betType": "QUADDIE",
        "betCount": 1
    },
    {
        "betType": "WIN",
        "betCount": 2
    }
]
```
* Run the following endpoint to get **Total number of bets sold per hour** 
* endpoint URL: http://localhost:8080/bets/getTotalNumberOfBetsSoldPerHour?fromDate=2018-07-01&toDate=2018-07-02
	* http method=GET
	* add following http header:
 	* key=Authorization value=abc123

click send and you should get the following results (the format of json key is "yyyy-MM-dd-HH", value is count). That way same hour of different date will be aggregated in a seperate key. 

```
 {
    "2018-07-01-12": 1,
    "2018-07-01-14": 1,
    "2018-07-01-13": 1,
    "2018-07-01-16": 1,
    "2018-07-01-15": 2
}
```

# Testing
JUnit coverage is provided for controller class(BettingController.java) and interceptor class(AuthenticationInterceptor.java) only. There is no JUnit coverage for repository class(BetRepository.java) methods as repository is just an Inteface and implementation is provided by JPA.


## Author
* **Jyoti Verma**
