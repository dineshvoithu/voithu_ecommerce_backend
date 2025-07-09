# ⚙️ E-Commerce Backend (Spring Boot)

This is the **backend** for our full-stack e-commerce web application. It is built using Spring Boot and secured with JWT authentication. The backend provides RESTful APIs for handling user roles, product management, cart operations, and orders.

## 💻 Technologies Used

- Java  
- Spring Boot  
- Spring Security (JWT)  
- Hibernate / JPA  
- MySQL  
- Maven  

## 🔑 Features

- JWT-based Authentication  
- Role-based Access Control: Customer / Seller / Admin  
- Product APIs: Add, Edit, Delete, View  
- Cart APIs: Add to cart, Update quantity, Delete  
- Order APIs: Place Order, View Order History  
- Seller: Can manage only their own products  
- Admin: Can manage all users and all orders  
- Passwords encrypted with BCrypt  
- Follows layered architecture (Controller → Service → Repository)

## 🔧 Getting Started

1. Clone this repo:
   ```bash
   git clone https://github.com/your-username/backend-repo.git
   cd backend-repo

src/
├── config/          # Security config (JWT, filters, etc.)
├── controller/      # API controllers
├── dto/             # Request/response DTOs
├── model/           # Entity classes
├── repository/      # JPA repositories
├── service/         # Business logic
└── EcommerceApplication.java

