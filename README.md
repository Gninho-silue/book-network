# 📚 Book Social Network

> A full-stack social platform for book lovers — share, borrow, review, and discover books with a community of readers.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.3-brightgreen?logo=springboot)
![Angular](https://img.shields.io/badge/Angular-19-red?logo=angular)
![Java](https://img.shields.io/badge/Java-21-orange?logo=openjdk)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-15-blue?logo=postgresql)
![Docker](https://img.shields.io/badge/Docker-Compose-2496ED?logo=docker)
![JWT](https://img.shields.io/badge/Auth-JWT-yellow?logo=jsonwebtokens)
![License](https://img.shields.io/badge/License-MIT-purple)

---

## 📋 Table of Contents

- [Overview](#-overview)
- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Architecture](#-architecture)
- [Project Structure](#-project-structure)
- [Getting Started](#-getting-started)
  - [Prerequisites](#prerequisites)
  - [Run with Docker (Recommended)](#run-with-docker-recommended)
  - [Run Manually](#run-manually)
- [API Documentation](#-api-documentation)
- [Environment Variables](#-environment-variables)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)

---

## 🌟 Overview

**Book Social Network** is a full-stack web application that enables users to manage their personal book collections and interact with a community of readers. Users can register, share their books, borrow books from others, leave reviews, and manage the entire borrowing lifecycle — all within a secure, authenticated platform.

This project demonstrates a complete **production-ready architecture** combining a RESTful Spring Boot backend with a reactive Angular frontend, secured by JWT authentication, containerized with Docker, and documented with OpenAPI/Swagger.

---

## ✅ Features

### 🔐 Authentication & Security
- User **Registration** with email validation (token-based account activation)
- Secure **Login** with JWT (JSON Web Token)
- **Email activation** flow with a 6-digit OTP code sent via SMTP (MailDev in dev)
- Spring Security with role-based access control
- Stateless session management

### 📖 Book Management
- **Add / Edit / Delete** your own books
- Upload a **book cover picture**
- Toggle **shareable** status (make a book available for borrowing)
- Toggle **archived** status (hide a book from the catalogue)
- Browse all **publicly available books** (paginated)

### 🔄 Borrowing System
| Action | Who | Description |
|--------|-----|-------------|
| Borrow a book | Borrower | Request to borrow a shareable book |
| Return a book | Borrower | Mark a borrowed book as returned + leave a review |
| Approve return | Owner | Confirm the physical book has been received |

### ⭐ Feedbacks & Reviews
- Leave a **star rating** (0–5) and a **comment** when returning a book
- View all **community reviews** for any book on the Book Details page
- Feedbacks are paginated for scalability

### 📄 Book Details
- Dedicated **detail page** per book with full synopsis, author, ISBN
- Star rating display (full / half / empty stars)
- Paginated list of reader reviews

---

## 🛠️ Tech Stack

### Backend
| Technology | Version | Role |
|------------|---------|------|
| **Java** | 21 | Core language |
| **Spring Boot** | 3.4.3 | Application framework |
| **Spring Security** | 6.x | Authentication & authorization |
| **Spring Data JPA** | 3.x | ORM / database access |
| **PostgreSQL** | 15 | Relational database |
| **JWT (jjwt)** | 0.12.3 | Stateless token authentication |
| **Thymeleaf** | 3.1 | Email template rendering |
| **SpringDoc OpenAPI** | 2.8.6 | API documentation (Swagger UI) |
| **Lombok** | Latest | Boilerplate reduction |
| **Maven** | 3.9.6 | Build tool |

### Frontend
| Technology | Version | Role |
|------------|---------|------|
| **Angular** | 19 | SPA framework |
| **Angular SSR** | 19 | Server-side rendering support |
| **TypeScript** | 5.7 | Language |
| **Bootstrap** | 5.3 | UI component library |
| **Font Awesome** | 6.7 | Icon library |
| **RxJS** | 7.8 | Reactive programming |
| **ng-openapi-gen** | 0.52 | Auto-generated API services from OpenAPI spec |

### Infrastructure & DevOps
| Technology | Role |
|------------|------|
| **Docker** | Containerization |
| **Docker Compose** | Multi-service orchestration |
| **MailDev** | Local SMTP server for email testing |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        Docker Compose                           │
│                                                                 │
│  ┌──────────────┐    ┌─────────────────┐    ┌───────────────┐  │
│  │   Angular    │───▶│  Spring Boot    │───▶│  PostgreSQL   │  │
│  │  (port 4200) │    │   (port 8088)   │    │  (port 5432)  │  │
│  │              │    │                 │    └───────────────┘  │
│  │  book-       │    │  /api/v1/auth   │                       │
│  │  network-ui  │    │  /api/v1/books  │    ┌───────────────┐  │
│  │              │    │  /api/v1/       │───▶│    MailDev    │  │
│  └──────────────┘    │  feedbacks      │    │  (port 1080)  │  │
│                      └─────────────────┘    └───────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

### Backend Package Structure
```
com.example.book_network/
├── auth/           # Registration, login, account activation
├── book/           # Book CRUD, shareable/archived status, cover upload
├── feedback/       # Reviews and star ratings
├── history/        # Book transaction history (borrow/return lifecycle)
├── user/           # User entity and repository
├── role/           # Role entity (USER, ADMIN)
├── security/       # JWT filter, JWT service, security config
├── config/         # OpenAPI config, async config, beans
├── email/          # Email service with Thymeleaf templates
├── file/           # File storage service (book cover uploads)
├── handler/        # Global exception handler
├── exception/      # Custom exceptions
└── common/         # Shared DTOs (PageResponse)
```

---

## 📁 Project Structure

```
book_network/
│
├── 📁 book-network/              # Spring Boot Backend
│   ├── src/main/java/            # Java source code
│   ├── src/main/resources/
│   │   ├── application.yml       # Main config (activates 'dev' profile)
│   │   ├── application-dev.yml   # Dev config (DB, mail, JWT, ports)
│   │   └── templates/
│   │       └── activate_account.html  # Thymeleaf email template
│   ├── Dockerfile                # Multi-stage build (Maven + JRE 21)
│   └── pom.xml
│
├── 📁 book-network-ui/           # Angular Frontend
│   ├── src/app/
│   │   ├── modules/book/         # Main book module
│   │   │   ├── pages/
│   │   │   │   ├── book-list/          # Public catalogue
│   │   │   │   ├── my-books/           # Owner's books management
│   │   │   │   ├── manage-book/        # Add / Edit book form
│   │   │   │   ├── borrowed-book-list/ # Borrower: my borrowed books
│   │   │   │   ├── returned-book-list/ # Owner: validate returned books
│   │   │   │   └── book-details/       # Book detail + reviews
│   │   │   └── components/
│   │   │       ├── book-card/          # Reusable book card
│   │   │       ├── rating/             # Star rating component
│   │   │       └── menu/               # Navigation bar
│   │   ├── pages/                # Authentication pages
│   │   │   ├── login/
│   │   │   ├── register/
│   │   │   └── activate-account/
│   │   └── services/             # Auto-generated API services (OpenAPI)
│   └── package.json
│
├── 📁 uploads/                   # Book cover images storage
├── docker-compose.yml            # Full stack orchestration
└── README.md
```

---

## 🚀 Getting Started

### Prerequisites

Make sure the following are installed on your machine:

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) (v20+)
- [Node.js](https://nodejs.org/) (v18+) — for running the frontend locally
- [Java 21](https://adoptium.net/) — only if running the backend without Docker

---

### Run with Docker (Recommended)

This will start **PostgreSQL**, **MailDev**, and the **Spring Boot API** together.

**1. Clone the repository**
```bash
git clone https://github.com/Gninho-silue/book-network.git
cd book-network
```

**2. Start the backend stack**
```bash
docker-compose up -d --build
```

Services started:
| Service | URL |
|---------|-----|
| Spring Boot API | http://localhost:8088/api/v1 |
| Swagger UI | http://localhost:8088/api/v1/swagger-ui/index.html |
| MailDev (inbox) | http://localhost:1080 |
| PostgreSQL | localhost:5432 |

**3. Start the Angular frontend**
```bash
cd book-network-ui
npm install
npm start
```

**4. Open the application**

Navigate to **http://localhost:4200**

---

### Run Manually

#### Backend

```bash
cd book-network

# Start PostgreSQL and MailDev only
docker-compose up -d postgres mail-dev

# Run the Spring Boot app
./mvnw spring-boot:run
```

#### Frontend

```bash
cd book-network-ui
npm install
npm start
```

---

## 📖 API Documentation

Once the backend is running, the full interactive API documentation is available at:

> **http://localhost:8088/api/v1/swagger-ui/index.html**

### Main Endpoints

#### 🔐 Authentication — `/api/v1/auth`
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/register` | Register a new user |
| `POST` | `/authenticate` | Login and receive JWT token |
| `GET` | `/activate-account?token=xxx` | Activate account with OTP |

#### 📚 Books — `/api/v1/books`
| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/` | Get all displayable books (paginated) |
| `POST` | `/` | Create a new book |
| `GET` | `/{book-id}` | Get book by ID |
| `GET` | `/owner` | Get current user's books |
| `GET` | `/borrowed` | Get books borrowed by current user |
| `GET` | `/returned` | Get books returned to current user (owner view) |
| `PATCH` | `/shareable/{book-id}` | Toggle shareable status |
| `PATCH` | `/archived/{book-id}` | Toggle archived status |
| `POST` | `/borrow/{book-id}` | Borrow a book |
| `PATCH` | `/borrow/return/{book-id}` | Return a borrowed book |
| `PATCH` | `/borrow/return/approve/{book-id}` | Approve a book return (owner only) |
| `POST` | `/cover/{book-id}` | Upload book cover image |

#### ⭐ Feedbacks — `/api/v1/feedbacks`
| Method | Endpoint | Description |
|--------|----------|-------------|
| `POST` | `/` | Submit a review for a book |
| `GET` | `/book/{book-id}` | Get all reviews for a book (paginated) |

---

## ⚙️ Environment Variables

The following environment variables can be overridden (e.g., in `docker-compose.yml` or a `.env` file):

| Variable | Default | Description |
|----------|---------|-------------|
| `SPRING_DATASOURCE_URL` | `jdbc:postgresql://localhost:5432/book_social_network` | PostgreSQL JDBC URL |
| `SPRING_DATASOURCE_USERNAME` | `username` | Database user |
| `SPRING_DATASOURCE_PASSWORD` | `password` | Database password |
| `SPRING_MAIL_HOST` | `localhost` | SMTP server host |
| `SPRING_PROFILES_ACTIVE` | `dev` | Active Spring profile |

> ⚠️ **Security note**: In production, replace JWT secret key, database credentials, and mail credentials with strong secrets using environment variables or a secrets manager.

---

## 🔒 Security Design

- **JWT** tokens are stateless, signed with HMAC-SHA256, and expire after 24h (configurable)
- **Spring Security filter chain** intercepts every request and validates the token
- **Account activation** is mandatory — an invalid or expired token blocks login
- **Business rules** are enforced at the service layer (e.g., you cannot borrow your own book, cannot borrow an already-borrowed book)
- **File uploads** are stored on the server filesystem under `/uploads`, referenced by path in the database

---

## 🗄️ Data Model (Simplified)

```
User ──────────┐
  │            │ (owner)
  │ (borrower) ▼
  └──────── BookTransactionHistory ──────── Book
                                             │
                                           Feedback
```

- A `User` can own many `Book`s
- A `Book` can have many `Feedback`s
- A `BookTransactionHistory` records each borrow event with `returned` and `returnedApproved` flags
- `Role` is linked to `User` for authorization

---

## 🤝 Contributing

Contributions are welcome! Here's how to get started:

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/your-feature-name`
3. **Commit** your changes: `git commit -m 'feat: add your feature'`
4. **Push** to the branch: `git push origin feature/your-feature-name`
5. **Open** a Pull Request

Please follow [Conventional Commits](https://www.conventionalcommits.org/) for commit messages.

---

## 📄 License

This project is licensed under the **MIT License** — see the [LICENSE](LICENSE) file for details.

---

<div align="center">

Built with ❤️ using **Spring Boot** + **Angular** + **Docker**

⭐ If you found this project useful, give it a star!

</div>
