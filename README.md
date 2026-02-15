#  Surplus Food Management System

A multi-role backend platform designed to manage surplus food donations efficiently and securely.  
The system connects food donors, organizations, and administrators through secure REST APIs, enabling real-time tracking and coordination of food distribution.

Built with **Java & Spring Boot**, focusing on clean architecture, scalability, and secure API design.

---

##  Features

- Role-based access control (Admin / Donor / Receiver /Driver)
- Secure authentication using JWT
- RESTful API architecture
- Food listing and request management
- Delivery tracking and status updates
- Real-time notifications using Server-Sent Events (SSE)
- Dockerized deployment for consistent environments

---

##  Architecture

The application follows a layered architecture to ensure maintainability and scalability:

- **Controller Layer** — Handles HTTP requests and API endpoints
- **Service Layer** — Business logic and workflow processing
- **Repository Layer** — Database interaction using Spring Data JPA
- **Security Layer** — JWT authentication and authorization

This structure allows easy scaling and feature extension while maintaining clean separation of concerns.

---

##  Tech Stack

**Backend**
- Java
- Spring Boot
- Spring Security
- Spring Data JPA (Hibernate)

**Database**
- PostgreSQL

**Real-Time Communication**
- Server-Sent Events (SSE)

**DevOps**
- Docker
- Docker Compose

---

## ⚙️ Getting Started

### Run Locally

```bash
# Clone repository
git clone https://github.com/adhil5108/Surplus-food-mangement-system.git

# Navigate into project
cd Surplus-food-mangement-system

# Run using Docker
docker-compose up --build
