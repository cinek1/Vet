# Vet
### Rest Api to for the vet
#### Spring project for java course 

## Endpoints

Application supports following endpoints:

| Name                       | Method  | Description                        |        
|:---------------------------|:--------|:----------------------------------:|
| /clients                   | GET     | get list of clients                |
| /clients/{id}              | GET     | get one Client with pets           |             
| /clients/{id}/withPets=true| GET     | get client with list of pets       |           
| /clients                   | POST    | add new client                     |    
| /clients/{id}              | DELETE  | delete client                      |
| /pets                      | GET     | get list of pets                   |
| /pets                      | POST    | add new pet                        |
| /{id}?ownerId=id           | PATCH   | set pet owner                      |
| /pets/{id}?dateOfDeath=date| PATCH   | set date of death                  |
| /visits                    | GET     | get list of visits                 |
| /visits/client/{id}        | GET     | get list of client visits          |
| /visits/pet/{id}           | GET     | get list of pets visits            |
| /visits/day?date=date      | GET     | get list of day visits             | 
| /visits                    | POST    | add new visit                      |
| /visits/{id}               | DELETE  | delete visit                       |
| /visits/{id}               | PATCH   | set status of visit                |
| /visits/{id}               | PUT     | change visit descripe              |


Each visit must have good date and hour. Two visits cannot take place at the same hour and cannot overlap.  

    
#### You can display raport from build and tests on the Github https://github.com/cinek1/Vet/runs/757516428?check_suite_focus=true

#### All data is saved in the postgreSQL database. 

#### Tests was wrote in JUnit jupiter and used Mockito to mock. 

#### App uses logback to logs.  
