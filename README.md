# ⚙️ Voithu E-Commerce Backend (Spring Boot)

This is the **backend** for our full-stack e-commerce web application. It is built using **Spring Boot** and secured with **JWT authentication**. The backend provides RESTful APIs for handling user roles, product management, cart operations, and orders.

## 💻 Technologies Used

- Java  
- Spring Boot  
- Spring Security (JWT)  
- Hibernate / JPA  
- MySQL  
- Maven  

## 🔑 Features

- JWT-based authentication and authorization  
- Role-based access (Customer, Seller, Admin)  
- Product management APIs  
- Cart and order management  
- Admin can manage all users and orders  
- Seller can manage their own products only  
- Encrypted passwords with BCrypt  
- Layered architecture (Controller → Service → Repository)

## 🔧 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/backend-repo.git
cd backend-repo
```

### 2. Configure MySQL Database

Update `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.jpa.hibernate.ddl-auto=update
```

### 3. Run the Application

```bash
./mvnw spring-boot:run
```

Or use IntelliJ/Eclipse to run the main class: `EcommerceApplication.java`

## 📁 Project Structure

```
src/
├── config/          # Security configuration (JWT, filters)
├── controller/      # API Controllers
├── dto/             # Request/response DTOs
├── model/           # Entity classes
├── repository/      # JPA Repositories
├── service/         # Business logic
└── EcommerceApplication.java
```

## 📂 API Endpoints Overview

| Method | Endpoint                | Description                        |
|--------|-------------------------|------------------------------------|
| POST   | /api/auth/register      | Register new user                  |
| POST   | /api/auth/login         | Login and get JWT token            |
| GET    | /api/products           | View all products                  |
| GET    | /api/products/{id}      | View product by ID                 |
| POST   | /api/products           | Add new product (Seller only)      |
| PUT    | /api/products/{id}      | Update product                     |
| DELETE | /api/products/{id}      | Delete product                     |
| POST   | /api/cart               | Add to cart                        |
| GET    | /api/cart               | View cart                          |
| DELETE | /api/cart/{itemId}      | Remove item from cart              |
| POST   | /api/orders             | Place order                        |
| GET    | /api/orders             | View order history                 |
| GET    | /api/admin/users        | Admin: View all users              |
| DELETE | /api/admin/users/{id}   | Admin: Delete user                 |
| GET    | /api/admin/orders       | Admin: View all orders             |

## 🔒 Roles

- **CUSTOMER** – Browse, add to cart, place orders  
- **SELLER** – Add/Edit/Delete own products  
- **ADMIN** – Full access to users and orders

## ✅ Notes

- Backend runs on: `http://localhost:8080`
- Use Postman or Swagger for testing
- JWT token must be passed in the `Authorization` header:
  ```
  Authorization: Bearer <your_token_here>
  ```

## 📌 Future Improvements

- Swagger API documentation  
- Payment Gateway integration  
- Email confirmation & reset password  
- Deployment to cloud (Render / AWS)

