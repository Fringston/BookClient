# BookClient


## Description
This project is frontend version of [Book_Pipeline](https://github.com/Samer-Ismael/Book_Pipeline), it will be using aws to connect with the backend end project that is hosted on beanstalk. This project lets the user create a list of authors and books, the user role will use crate and read to add an print out a list of authors and books. The admin user
will have full crud for both users and authors & books. The project has many features, this project use Spring security for authentication and authorization, you will need to authenticate yourself to be able to add authors and books.

## Table of Contents (Optional)

- [Installation](#installation)
- [Functionality](#functionality)
- [Usage](#usage)
- [Dependencies](#dependencies)
- [Credits](#credits)

## Functionality
### User functionality
- **All users can:**
  - Add books & authors
  - change password
  - delete their account
  - print out data from database
 
### Admin functionality
- **Admins users can:** 
  - Do everything a regular user can do.
  - Full CRUD for books & author
 
 ## Installation

- Download and install IntelliJ IDEA or your preferred IDE [Here](https://www.jetbrains.com/idea/download/?section=windows).
- clone this repository to your local machine.

 ## Usage
+ Clone this repository to your local machine.
+ Open project in IntelliJ IDEA or your preferred IDE.
+ Run the application in the main class.

 ## Dependencies:

- [jackson-core](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core)
- [jackson-annotations](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-annotations)
- [httpclient5](https://mvnrepository.com/artifact/org.apache.httpcomponents.client5/httpclient5)
- [jackson-databind](https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind)

## Credits

### Collaborators in this project:
* [Clara Brorson](https://github.com/clarabrorson)
* [Fredrik Rinstad](https://github.com/Fringston)
* [Jafar Hussein](https://github.com/Jafar-Hussein)

 ## Badges
![badmath](https://img.shields.io/badge/JWT-000000?style=for-the-badge&logo=JSON%20web%20tokens&logoColor=white)
![badmath](https://img.shields.io/badge/apache_maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)
![badmath](https://img.shields.io/badge/Spring_Boot-F2F4F9?style=for-the-badge&logo=spring-boot)
![badmath](https://img.shields.io/badge/IntelliJ_IDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)
