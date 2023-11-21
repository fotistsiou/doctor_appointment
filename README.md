My project is an application for appointments between doctors and patients. The technologies and packages used in the application are as follows: 
- Spring Boot (Spring Web, Spring Data JPA, Spring Security, Lombok, I/O Validation, Thymeleaf, MySQL Drivers)    
- Bootstrap 
- CSS 

The users of the application are doctors and patients and have the following features: 
- User as Doctor: 
    * Register/Login/Logout 
    * Create new appointment 
    * View/Delete his appointment 
    * View/Edit his account details
- User as Patient: 
    * Register/Login/Logout 
    * Booking appointment 
    * View/Cancel appointment 
    * View/Edit his account details 

The entities of the application are three: 
- User 
- Role 
- Appointment 

Four tables are created from these entities: 
- The three tables are one for each entity. 
- The fourth table is created by the many to many relationship between user and role. 

For the above entities have been additionally created:
- From a repository for each entity, which implements the JpaRepository interface.
     * In the case of the role repository we have an additional custom method for finding roles by name.
     * In the case of the user repository we have an additional custom method for finding the user by email and another one for updating the user with a custom query.
- From a service for each entity where, with dependency injection of the respective repositories, it implements the business logic, i.e. the methods for crud operations.
     * In the case of the user, there is another service, which implements the UserDetailsService interface of Spring Security.
- From a controller for the user and appointment entities, where it manages all http requests for communication between the backend and the frontend.
- Two config files, one for the web security of the application urls and one for the creation of seed data during the first loading of the application.

For the frontend part of the application has been used:
- Thymeleaf for creating templates.
- Bootstrap and CSS for formatting the templates.
- Images for a more pleasant presentation of the forms. 

All of the above can be found in the project's resources where there is also a file with the base schema and some data samples.
