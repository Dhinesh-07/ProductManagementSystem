# Product Management System (Spring Boot)

## Overview
The Product Management System is an API built using the Spring Boot framework, designed to manage various aspects of product inventory, orders, and user authentication.

## Features
- **User Authentication**: Implemented endpoints for user login and registration with authentication and authorization features.
- **Role and Permission Management**: Created a master(admin) for managing roles and permissions to control user access.
- **Product Category Management**: Developed CRUD API for managing product categories including title and status.
- **Product Brand Management**: Implemented CRUD API for managing product brands with category ID, brand ID, brand name, and status.
- **Product Management**: Created CRUD API for managing products including category ID, brand ID, product ID, product name, description, stock, MRP, and sale price.
- **Order Management**: Developed CRUD API for managing orders including order ID, user ID, date, and total amount.
- **Order Details Management**: Implemented CRUD API for managing order details including order ID, order details ID, product name, quantity, and price.
- **Stock Management**: Developed CRUD API for managing stock including product name and stock quantity.

## Technologies Used
- **Spring Boot**: Framework for building web applications with Java.
- **MySQL**: Relational database management system used for data storage.
- **Swagger**: Integrated for API documentation and testing purposes.
- **Postman**: Created a Postman collection containing all the API endpoints for easy testing and usage.

## Project Directory Structure

.
- **Application**
    - pom.xml
    - src
        - main
            - java
                - com
                    - product
                        - BoxApplication.java
                        - CorsOriginConfig.java
                        - ServletInitializer.java
                        - SwaggerConfig.java
            - resources
                - application.properties
- **Controller**
    - pom.xml
    - src
        - main
            - java
                - com
                    - product
                        - BrandController.java
                        - CategoryController.java
                        - OrderController.java
                        - OrderDetailsController.java
                        - ProductController.java
                        - StockController.java
                        - UserController.java
- **Model**
    - pom.xml
    - src
        - main
            - java
                - com
                    - product
                        - Brand.java
                        - Category.java
                        - OrderDetails.java
                        - OrderEntity.java
                        - Product.java
                        - Stock.java
                        - UserDetails.java
- pom.xml
- README.md
- **Repository**
    - pom.xml
    - src
        - main
            - java
                - com
                    - product
                        - BrandRepository.java
                        - CategoryRepository.java
                        - OrderDetailsRepository.java
                        - OrderRepository.java
                        - ProductRepository.java
                        - StockRepository.java
                        - UserRepository.java
- **Service**
    - pom.xml
    - src
        - main
            - java
                - com
                    - product
                        - BrandService.java
                        - CategoryService.java
                        - OrderDetailsService.java
                        - OrderService.java
                        - ProductService.java
                        - ServiceException.java
                        - StockService.java
                        - UserService.java


## Setup Instructions
1. **Clone Repository**: 
   ```bash
   git clone https://github.com/Dhinesh-07/ProductManagementSystem.git`
2. **Java Version**: Ensure Java version 17 is installed and configured properly. You can verify your Java version by running: `java -version`
3. **Maven Version**: Ensure Maven version 3.9.3 is installed and configured properly. You can verify your Maven version by running: `mvn -version`
4. **Clean and Install Dependencies**: Run the following command to clean the project and install dependencies:
   ```bash
   mvn clean install
5. **Run the Application**: After successfully building the project, you can run the Spring Boot application using the following command:
   ```bash
   mvn spring-boot:run

Access API Documentation: http://localhost:8080/swagger-ui/index.html to access Swagger UI for API documentation and testing.

Contributors
    Dhineshkumar
