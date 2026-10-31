# Group 8 — Member 2: Customer and Administrator Models

## Project
**Movie Ticketing System (MTS)**  
Course: CSC 1102 — Structured and Object-Oriented Programming Practical Exam Project  
Group: Group 8 Afternoon Class

## Your responsibility
Implement:

- `Customer.java`
- `Administrator.java`

These classes represent the people who use or administer the system. Do not build the Swing login screen as part of this task.

## Recommended design

The group should agree on a shared user design before you code.

A simple option is:

- `Customer` contains common account details and customer-specific behaviour.
- `Administrator` extends `Customer` only if the group agrees that an administrator can reasonably be represented as a specialized user/customer.
- If the distinction feels awkward, propose a separate abstract `User` superclass instead. Do not introduce it without telling the group first.

### Suggested `Customer` attributes

| Attribute | Suggested Java type | Purpose |
|---|---|---|
| `customerId` | `String` | Unique customer identifier |
| `name` | `String` | Customer's full name |
| `email` | `String` | Login/contact email |
| `password` | `String` | Password value for the assignment's in-memory model |

**Security note:** Do not store real account passwords in GitHub or test data. For a real application, passwords must be securely hashed; plaintext password storage is not suitable for production. For this assignment, follow the lecturer's scope and use dummy data only.

### Suggested `Administrator` attributes/behaviour

If `Administrator extends Customer`, add only administrator-specific information, such as:

- `administratorId`, if it is distinct from the inherited ID.
- Methods to manage movies and showings, once the group has agreed on the service/API design.

Do not add methods that directly manipulate Swing screens. Administration actions should eventually call business/service classes.

## Requirements

- Use `private` fields.
- Provide suitable constructors.
- Add getters and setters where appropriate.
- Validate basic input (for example, nonblank name and a plausible email format).
- Override `toString()` without printing passwords.
- Demonstrate inheritance and method overriding if the group adopts the inheritance design.
- Use `@Override` when overriding superclass methods.
- Do not put UI code in these classes.

## Suggested workflow

1. Pull the latest `main` branch.
2. Create your branch:
   ```bash
   git checkout main
   git pull origin main
   git checkout -b feature/user-models
   ```
3. Implement the agreed classes.
4. Compile and test with dummy data.
5. Review your changes:
   ```bash
   git status
   git diff
   ```
6. Commit and push:
   ```bash
   git add .
   git commit -m "Implement Customer and Administrator models"
   git push -u origin feature/user-models
   ```
7. Open a Pull Request on GitHub:
   - Base: `main`
   - Compare: `feature/user-models`
   - Describe the inheritance/composition decision you used.

## Definition of done

- [ ] `Customer.java` compiles.
- [ ] `Administrator.java` compiles.
- [ ] The agreed inheritance design is implemented consistently.
- [ ] Fields are encapsulated.
- [ ] Constructors and required accessors are implemented.
- [ ] Basic validation is present.
- [ ] `toString()` does not expose passwords.
- [ ] Changes are pushed on your feature branch.
- [ ] A Pull Request is opened.

## Coordinate with the group

`Booking` will refer to a `Customer`, so agree on the customer constructor and ID field with the member implementing Booking. Confirm the inheritance decision with the group before coding against it.
