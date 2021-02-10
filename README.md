# time-tracker-POC 
The goal of this project was to create a web application for a development company to track time spent on projects.

## How to get started :
* Make sure you have JAVA_HOME and MAVEN_HOME as system variables (check this tutorial if not : https://mkyong.com/maven/how-to-install-maven-in-windows/)  
* Pull the project   
* Go into the project and use npm install   
* Then, use the command ./mvnw the first time you build the project   
* The other times, use ./mvnw -P-webpack, it makes it faster and should auto reload the project   
  
* Alternative : npm start in powershell, and launch on eclipse your project.   

## Chosen technic stack
### Description
As we wanted to use Angular and Springboot, we chose JHipster for this project.
It is a technology that uses both Angular and Springboot in order to generate a very solid base of code.

### Advantages
Jhipster allows us to not start a project from scratch. It helps us create a project with a front already working, two types of users already done (Admin and User), an easy way to create and modify accounts, an easily accessible API, and even more. Moreover, they have a tool called JDL-studio in which we can easily add new types of data, like Project or WorkUnit by drawing a UML diagram.

### Drawbacks
Because it creates a lot of things, we start with a very heavy project with a lot of files. Unfortunately, partly because of our lack of experience, it is then very hard to navigate in this project to modify it. Even when we want to add little things, like a Manager role, many files need to be modified if we want everything to work properly. Something even harder to do is to modify the base User, because if we change anything in the file it will break the whole project. So, because of those problems, we have lost a lot of time and did not manage to complete the project.

## Project files description
### Front end

### Back end

### Tests


## Users stories completed
* Login/password connection
* Different users roles with different authorizations : user, manager, admin.
* A user can enter the time spent on projects.
* A manager can create projects.
* A manager can create users.
* An admin can activate/desactivate an account.
* An admin can change a user status to manager or admin.
* A not logged in user can't make API calls.

## API


