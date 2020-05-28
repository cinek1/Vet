# Vet
### Rest Api to for the vet
#### Spring project for java course 

Done: 

* User can get List of Clients    
Method: GET ``` /clients ```
* User can get one Client  
Method: GET ```  /clients/{id}
* User can get one Client with pets 
Method: GET ```  /clients/{id}/withPets=true
* User can add Client      
Method: POST ```  /clients ``` Client in JSON body
* User can delete Client      
Method: Delete ```  /clients/{id} ``` 

* User can get List of Pets 
Method: GET ```  /pets ```
* User can add pet         
Method: POST ```  /pets ``` Pet in JSON body 
* User can assign pet to owner
Method: PATCH ```  /pets/{id}?ownerId= ```
* User can set date of death the pet
Method: PATCH ```  /pets/death/{id}?dateOfDeath= ```

* User can get list of all visits 
Method: GET ``` /visits ```
* User can get list of visits client
Method: GET ``` /visits/client/{id} ```
* User can get list of visits pet
Method: GET ``` /visits/pet/{id} ```
* User can get list of visits in one day 
Method: GET ``` /visits/day?date= ```
* User can add visit
Method: POST ``` /visits```
* User can delete visit
Method: DELETE``` /visits/{id}```
* User can set status of visit
Method: PATCH``` /visits/{id}```
* User can change decripe
Method: PUT``` /visits/{id}```




#### All data is saved in the postgreSQL database. 

#### Tests was wrote in JUnit jupiter and used Mockito to mock. 

### To do:
* Logs
* Deploy on heroku 
