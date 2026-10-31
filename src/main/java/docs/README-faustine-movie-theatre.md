# Group 8 — Member 1: Movie and Theatre Models

## Project
**Movie Ticketing System (MTS)**  
Course: CSC 1102 — Structured and Object-Oriented Programming Practical Exam Project  
Group: Group 8 Afternoon Class

## Your responsibility
Implement the two domain model classes:

- `Movie.java`
- `Theatre.java`

Work on the model classes only. The group will build the Swing screens together later.

## 1. `Movie.java`

A `Movie` represents a film that can be scheduled for showing and booked by customers.

Suggested attributes (keep fields `private`):

| Attribute | Suggested Java type | Purpose |
|---|---|---|
| `title` | `String` | Movie title |
| `cost` | `double` | Base ticket cost, if the group agrees this belongs here |
| `rating` | `String` | Age/content rating |
| `releaseDate` | `LocalDate` | Release date |
| `duration` | `Duration` | Runtime |
| `synopsis` | `String` | Short description |
| `genre` | `String` | Movie genre |
| `posterFileName` | `String` | Local poster image filename/path |

**Important:** The group should decide whether ticket price belongs to `Movie` or to `NowShowing`. A movie can have different prices for different showings. The preferred design is to keep the actual `ticketPrice` on `NowShowing`; only retain `cost` on `Movie` if the group explicitly agrees it means something different.

## 2. `Theatre.java`

A `Theatre` represents a cinema auditorium or screen.

Suggested attributes:

| Attribute | Suggested Java type | Purpose |
|---|---|---|
| `name` | `String` | Theatre/auditorium name |
| `location` | `String` | Location or branch |
| `capacity` | `int` | Maximum number of seats |
| `availableSeats` | `List<String>` | Seat identifiers, if seat inventory is managed here |

Avoid storing both a seat list and a separate capacity that can contradict each other unless you validate their consistency.

## Requirements for both classes

- Use `private` fields (encapsulation).
- Provide suitable constructors.
- Provide getters and setters only where modification is appropriate.
- Validate important inputs (for example, nonblank title/name, positive capacity and nonnegative prices).
- Override `toString()` to return useful information.
- Use appropriate Java types: `LocalDate` for dates and `Duration` for runtime.
- Do not put Swing components (`JFrame`, `JPanel`, `JButton`, etc.) in model classes.
- Do not write database or file-persistence code unless the group agrees it is required.

## Suggested workflow

1. Pull the latest `main` branch.
2. Create your branch:
   ```bash
   git checkout main
   git pull origin main
   git checkout -b feature/movie-theatre-models
   ```
3. Implement the two classes.
4. Compile and run a small temporary test (or a simple test in a separate test class).
5. Check your changes:
   ```bash
   git status
   git diff
   ```
6. Commit and push:
   ```bash
   git add .
   git commit -m "Implement Movie and Theatre models"
   git push -u origin feature/movie-theatre-models
   ```
7. Open a Pull Request on GitHub:
   - Base: `main`
   - Compare: `feature/movie-theatre-models`
   - Explain what you implemented and any decisions/questions.

## Definition of done

- [ ] `Movie.java` compiles.
- [ ] `Theatre.java` compiles.
- [ ] Fields are encapsulated.
- [ ] Constructors and required accessors are implemented.
- [ ] Basic validation is present.
- [ ] `toString()` is meaningful.
- [ ] No UI code is mixed into the models.
- [ ] Changes are pushed on your feature branch.
- [ ] A Pull Request is opened.

## Coordinate with the group

Before finalizing attribute names or constructors, agree them with the group. `NowShowing` will use `Movie` and `Theatre`, so communicate any changes to those public interfaces before merging.
