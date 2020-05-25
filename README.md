# Vet
### Rest Api to for the vet
#### Spring project for java course 

Done: 

* User can get List of Clients    
Method: GET ``` /clients ```
* User can get one Client  
Method: GET ```  /clients/{id} ``` or get ``` clients\{mail} ```
* User can add Client      
Method: POST ```  /clients ``` Client in JSON body

* User can get List of Pets 
Method: GET ```  /pets ```
* User can add pet         
Method: POST ```  /pets ``` Pet in JSON body 

#### All data is saved in the postgreSQL database. 

### To do:
* Visits table, controller and service
* Delete data
* Connect pets (pet can don't have client (owner) with clients
* Tests 
* Logs
* Deploy on heroku 
