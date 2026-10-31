# Movie Ticketing System (MTS)

**CSC 1102: Structured and Object-Oriented Programming**  
**Practical Examination Project**  
**Group 8 — Afternoon Class**

---

## 1. Project Overview

The Movie Ticketing System (MTS) is a Java-based desktop application designed to simplify the process of browsing movies, viewing scheduled screenings, selecting seats and purchasing movie tickets.

The system provides an interface through which customers can explore currently showing movies, make bookings and manage their payments. It also provides administrative functionality for managing movies, theatres and scheduled showings.

The application is being developed using Java Swing for its graphical user interface, with object-oriented programming principles guiding the design and implementation of the system.

## 2. Project Objectives

The primary objectives of this project are to:

- Develop a functional movie ticketing application using Java.
- Apply structured and object-oriented programming principles to a practical software development problem.
- Demonstrate the use of classes, objects, inheritance, polymorphism, encapsulation, abstraction and composition.
- Implement a graphical user interface using Java Swing.
- Model relationships between customers, movies, theatres, bookings and payments.
- Practise collaborative software development using Git and GitHub.

## 3. Core Features

The proposed system will include the following functionality:

### Customer Features

- User authentication and login.
- Browse movies currently showing.
- View movie information, including titles, genres, ratings, descriptions and durations.
- View available theatres and scheduled screenings.
- Select preferred movie showings and seats.
- Book movie tickets.
- Make and view payment transactions.

### Administrator Features

- Manage movie information.
- Manage theatre information.
- Schedule and manage movie showings.
- Oversee bookings and payment records.

### Application Features

- Graphical user interface built with Java Swing.
- Movie and screening information management.
- Seat selection and booking management.
- Payment status tracking.
- Separation of application models, business logic and presentation.

*Note: These features describe the intended project scope and may be refined during implementation.*

## 4. Technologies and Tools

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Java Swing | Graphical user interface |
| Git | Version control |
| GitHub | Collaborative development and source code hosting |
| VS Code / IntelliJ IDEA | Development environments |

## 5. Object-Oriented Programming Concepts

The project will demonstrate the following concepts:

| Concept | Application in the Project |
|---|---|
| Encapsulation | Protecting object attributes through access modifiers and controlled access methods. |
| Inheritance | Establishing relationships between related classes and reusing common functionality. |
| Polymorphism | Allowing objects to exhibit different behaviours through shared interfaces or overridden methods. |
| Abstraction | Exposing essential functionality while hiding implementation details. |
| Composition | Modelling relationships between objects, such as bookings, customers and movie showings. |

## 6. Project Structure

The project will follow a modular package structure to separate different application responsibilities.

```text
movie-ticketing-system/
│
├── src/
│   └── main/
│       └── java/
│           │
│           ├── app/
│           │   ├── Main.java
│           │   └── Application.java
│           │
│           ├── models/
│           │   ├── Movie.java
│           │   ├── Customer.java
│           │   ├── Administrator.java
│           │   ├── Theatre.java
│           │   ├── NowShowing.java
│           │   ├── Booking.java
│           │   └── Payment.java
│           │
│           ├── screens/
│           │   ├── LoginScreen.java
│           │   ├── NowShowingScreen.java
│           │   └── PaymentsScreen.java
│           │
│           ├── services/
│           │
│           └── enums/
│
├── docs/
│   ├── README-member-1.md
│   ├── README-member-2.md
│   ├── README-member-3.md
│   └── README-member-4.md
│
├── .gitignore
└── README.md
```

**Package responsibilities:**

- `app`: Application entry point and initialization.
- `models`: Classes representing the entities within the system.
- `screens`: Java Swing graphical interfaces.
- `services`: Business logic and application operations.
- `enums`: Enumerated types used throughout the application.
- `docs`: Project documentation and individual task allocations.

## 7. Development and Collaboration

Development is coordinated through Git and GitHub to enable multiple group members to contribute independently.

We follow a feature-branch workflow:

1. Clone the project repository.
2. Create a feature branch for your assigned task.
3. Implement the required functionality.
4. Commit changes with descriptive commit messages.
5. Push your branch to GitHub.
6. Open a Pull Request against `main`.
7. Have changes reviewed and approved before merging.

### Branching Convention

The `main` branch serves as the primary and stable integration branch.

Feature branches follow this naming convention:

```text
feature/movie-theatre-models
feature/user-models
feature/booking-showings
feature/payment-model
```

**Contribution Guidelines**

- Do not push directly to `main`.
- Keep changes focused on your assigned responsibilities.
- Communicate changes to shared class interfaces with other contributors.
- Write readable, maintainable Java code.
- Ensure your changes compile before submitting a Pull Request.
- Avoid committing generated files, IDE configuration files or sensitive information.

## 8. Group Members

| Name | Registration Number |
|---|---|
| ABIRO FAITH FAUSTINE | 26/U/03467/PSA |
| TUYISHIME JEANPIRRE | 26/E/26540/PSA |
| SSEMAGANDA TRAVIS BENJAMIN | 26/U/03637/PSA |
| NDAWULA PRAISE MARGARET | 26/U/03619/PSA |
| MAJALIWA WILFRED | 26/E/32173/PSA |

## 9. Project Status

<badge>In Development</badge>

The project is currently under development. Initial architecture, class responsibilities and collaborative development workflows are being established.

Subsequent development will focus on implementing the underlying models and business logic, followed by the integration of the graphical user interface.

## 10. Academic Context

This application is developed as part of the practical examination project for:

**CSC 1102 — Structured and Object-Oriented Programming**

**Group:** Group 8 Afternoon Class

The project serves as a practical demonstration of the group's understanding and application of Java programming and object-oriented software design.

---

*Developed by Group 8 — Afternoon Class.*