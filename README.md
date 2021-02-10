# time-tracker-POC 
The goal of this project was to create a web application for a development company to track time spent on projects.

## How to get started :
* Make sure you have JAVA_HOME and MAVEN_HOME as system variables (check this tutorial if not : https://mkyong.com/maven/how-to-install-maven-in-windows/)  
* Pull the project   
* Go into the project and use npm install   
* Then, use the command ./mvnw the first time you build the project   
* The other times, use ./mvnw -P-webpack, it makes it faster and should auto reload the project   
  
* Alternative : npm start in powershell, and launch on eclipse your project.   

## Chosen technologies
### Description
As we wanted to use Angular and Springboot, we chose JHipster for this project.
It is a technology that uses both Angular and Springboot in order to generate a very solid base of code.

### Advantages

### Drawbacks


## Project files description
### Front end
The front end of the application is made with Angular so the code is separated in HTML files, router files and SCSS files mostly.
This is a complex tree structure but quite clear to understand. Each page is divided into smaller components.
There are too many files to describe them all. For example let's look at the home page structure:
* a navbar: *1 .html file, 2 .ts files and 1 .scss file*.
  * drop menus.
  * connection access.
* a footer: *1 .html file, 1 .ts file*.
* a center main component where different informations can be displayed depending on the menu item you selected: *1 .html file, 3 .ts files and 1 .scss file*.

### Back end

### Tests


## Users stories completed
* Login/password connection.
* Different users roles with different authorizations : user, manager, admin.
* A user can enter the time spent on projects.
* A manager can create projects.
* A manager can create users.
* An admin can activate/desactivate an account.
* An admin can change a user status to manager or admin.
* A not logged in user can't make API calls.

## API
Our API documentation is accessible on our application.
* Log in as *admin* (admin/admin).
* Go to *administration* in the navbar.
* Then click on *API*, here you go.

