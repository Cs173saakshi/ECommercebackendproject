# E-Commerce Backend System


## Project Overview

This is a backend system for an e-commerce platform built using Java and Spring Boot. It manages users, products, carts, orders, payments, and inventory through RESTful APIs.


## Features

- User registration and login  

- Role-based access (Admin, Customer)  

- Product management (add, update, delete, view)  

- Shopping cart and order management  

- Simulated payment and stock update  


## Technologies Used

- Java 21+  

- Spring Boot 3+  

- MySQL  

- Spring Data JPA / Hibernate  

- Maven  

- JUnit 5 and Mockito (for testing)  


## Main API Endpoints

**User:** `/api/users/register`, `/api/users/login`, `/api/auth/register`, `/api/auth/login`  

**Products:** `/api/products`, `/api/products/{id}`  

**Cart:** `/api/cart/add/{productId}`, `/api/cart`  

**Orders:** `/api/orders/checkout`, `/api/orders`  


## How to Run

1. Clone the project to your system  

2. Open it in Eclipse  

3. Configure `application.properties` with your MySQL username and password  

4. Run the main Spring Boot application  

5. Test the APIs using Postman  


## Git Repository

git clone https://github.com/Cs173saakshi/ECommercebackendproject.git
