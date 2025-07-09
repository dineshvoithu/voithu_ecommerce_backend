🌐 Backend API
Make sure the backend server is running on http://localhost:8080. Update axiosInstance.js if needed.


---

## 🟨 **Backend Repo: `README.md`**

```markdown
# 🛠️ E-Commerce Backend (Spring Boot)

This is the **backend** of the e-commerce web app. It is built using Spring Boot, Spring Security, and MySQL, with JWT-based authentication and RESTful APIs.

## 💡 Tech Stack

- Spring Boot
- Spring Security (JWT)
- MySQL Database
- Hibernate/JPA
- Maven

## 🎯 Features

- User Authentication (JWT)
- Role-based Access Control (Customer, Seller, Admin)
- CRUD for Products
- Cart & Order Management
- Seller can manage own products
- Admin dashboard APIs (manage users & orders)

## 🧑‍💻 Setup Instructions

1. Clone the repo:
   ```bash
   git clone https://github.com/your-username/backend-repo.git
   cd backend-repo
Update MySQL credentials in application.properties:

properties
Copy
Edit
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
spring.datasource.url=jdbc:mysql://localhost:3306/ecommerce_db
Run the app:

bash
Copy
Edit
./mvnw spring-boot:run
📁 Key Endpoints
/api/auth/register — Register users

/api/auth/login — Login and get JWT

/api/products — Products CRUD

/api/cart — Add to cart, view cart

/api/orders — Place orders

/api/admin — Admin actions

📌 Notes
Roles are: CUSTOMER, SELLER, ADMIN

Passwords are encrypted

Use Postman for API testing

✅ Database
MySQL schema includes tables like:

users

products

cart_items

orders

order_items
