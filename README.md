# ⚙️ E-Commerce Backend (Spring Boot + JWT + MySQL)

This is the **backend** for a full-stack e-commerce web application developed as an MCA final-year project. It is built using **Spring Boot**, secured with **JWT**, and connects to a **MySQL** database. The backend provides RESTful APIs for user authentication, product management, cart functionality, and order processing.

---

## 💻 Technologies Used

- Java 17  
- Spring Boot  
- Spring Security (JWT)  
- Hibernate / JPA  
- MySQL  
- Maven  

---

## ✅ Key Features

- 🔐 JWT-based Login/Register (Customer & Seller)
- 👤 Role-based Access (Customer, Seller, Admin)
- 🛍️ Product Management (Add, Edit, Delete by Seller)
- 🛒 Cart Operations (Add, View, Remove items)
- 📦 Order Placement and Order History (Customer)
- 🧑‍💼 Admin Panel: Manage Users and Orders
- BCrypt password hashing
- RESTful API structure following MVC design

---

## 🚀 Getting Started

### Prerequisites

- Java 17+  
- Maven  
- MySQL 8.x  
- IDE (IntelliJ / Eclipse / VS Code)

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/ecommerce-backend.git
cd ecommerce-backend
```

### 2. Configure MySQL Database

```sql
CREATE DATABASE ecommerce_db;
```

In `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
spring.datasource.username=root
spring.datasource.password=your_mysql_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. Run the Application

```bash
mvn spring-boot:run
```

Backend server: `http://localhost:8080`  
Swagger (if enabled): `http://localhost:8080/swagger-ui/`

---

## 🗃️ Database Schema

### users

```sql
CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  email VARCHAR(100) UNIQUE,
  password VARCHAR(255),
  role ENUM('CUSTOMER', 'SELLER', 'ADMIN'),
  address TEXT,
  phone VARCHAR(20)
);
```

### products

```sql
CREATE TABLE products (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100),
  description TEXT,
  price DOUBLE,
  image_url VARCHAR(255),
  category VARCHAR(50),
  seller_id BIGINT,
  FOREIGN KEY (seller_id) REFERENCES users(id)
);
```

### cart_items

```sql
CREATE TABLE cart_items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  product_id BIGINT,
  quantity INT,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);
```

### orders

```sql
CREATE TABLE orders (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  user_id BIGINT,
  total_price DOUBLE,
  payment_method VARCHAR(50),
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (user_id) REFERENCES users(id)
);
```

### order_items

```sql
CREATE TABLE order_items (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  order_id BIGINT,
  product_id BIGINT,
  quantity INT,
  price DOUBLE,
  FOREIGN KEY (order_id) REFERENCES orders(id),
  FOREIGN KEY (product_id) REFERENCES products(id)
);
```

---

## 🔐 Authentication & Roles

- JWT Token-based authentication  
- Passwords hashed with BCrypt  
- Roles:  
  - CUSTOMER: Browse and order products  
  - SELLER: Manage own products  
  - ADMIN: Full control over users and orders  

---

## 🧪 Sample API Endpoints

| Method | Endpoint                 | Description                        |
|--------|--------------------------|------------------------------------|
| POST   | `/api/auth/register`     | User registration                  |
| POST   | `/api/auth/login`        | Login and get JWT token            |
| GET    | `/api/products`          | View all products                  |
| POST   | `/api/seller/products`   | Add new product (Seller only)      |
| GET    | `/api/cart`              | View cart items                    |
| POST   | `/api/cart/add`          | Add item to cart                   |
| POST   | `/api/orders`            | Place order                        |
| GET    | `/api/orders`            | View order history                 |

---

## 📁 Folder Structure

```
src/
├── config/                # JWT Security Config
├── controller/            # REST API Controllers
├── dto/                   # Request/Response DTOs
├── entity/                # JPA Entities
├── repository/            # Spring Data Repositories
├── service/               # Business Logic
└── resources/
    └── application.properties
```

---

## 📌 Project Info

- 🎓 MCA Final Year Project  
- 🔧 Tech Stack: React (Frontend), Spring Boot (Backend), MySQL (DB)  
- 🧠 Developed by: Dinesh  
- 🔐 Auth: JWT & Role-Based Access  
- 📅 Year: 2025

---

## 📫 Contact

**Dinesh**  
📧 Email: dineshvoithu@gmail.com  
🔗 LinkedIn: [linkedin.com/in/dineshvoithu]  
🌐 GitHub: [github.com/dineshvoithu](https://github.com/dineshvoithu)
