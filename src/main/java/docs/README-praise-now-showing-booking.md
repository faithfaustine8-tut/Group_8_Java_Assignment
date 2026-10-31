# Group 8 — Member 3: Now Showing and Booking Models

## Project
**Movie Ticketing System (MTS)**  
Course: CSC 1102 — Structured and Object-Oriented Programming Practical Exam Project  
Group: Group 8 Afternoon Class

## Your responsibility
Implement:

- `NowShowing.java`
- `Booking.java` (a new class the current project structure does not yet contain)

These classes connect the movie, theatre and customer models. They are important because a movie itself is not the same thing as a scheduled showing, and a booking is not the same thing as a payment.

## 1. `NowShowing.java`

A `NowShowing` represents a particular scheduled screening of a movie in a theatre.

Suggested attributes:

| Attribute | Suggested Java type | Purpose |
|---|---|---|
| `movie` | `Movie` | Movie being screened |
| `theatre` | `Theatre` | Auditorium hosting the screening |
| `showingTime` | `LocalDateTime` | Date and time of the screening |
| `ticketPrice` | `double` | Price for this particular showing |

Possible behaviour:
- Check whether the showing is in the future.
- Return useful display information.
- Validate that the ticket price is not negative.

Do not store a duplicate copy of every Movie or Theatre field. Keep references to the corresponding objects.

## 2. `Booking.java`

A `Booking` represents a customer's reservation for a specific showing.

Suggested attributes:

| Attribute | Suggested Java type | Purpose |
|---|---|---|
| `bookingId` | `String` | Booking identifier |
| `customer` | `Customer` | Customer making the booking |
| `showing` | `NowShowing` | Selected scheduled showing |
| `selectedSeats` | `List<String>` | Seat identifiers selected |
| `totalAmount` | `double` | Total booking cost |
| `status` | `BookingStatus` or `String` | Current booking state |

Prefer an enum for status if the group agrees to create one (for example, `PENDING`, `CONFIRMED`, `CANCELLED`). Coordinate before introducing a shared enum.

Possible behaviour:
- Calculate the total from the number of seats and the showing's ticket price.
- Reject an empty seat selection.
- Avoid duplicate seat identifiers within one booking.
- Provide a useful `toString()`.

**Important:** Preventing two different bookings from reserving the same seat requires shared booking/showing state or a service that checks existing bookings. Do not assume a single Booking object can enforce that by itself. Discuss this with the group.

## Requirements

- Use `private` fields.
- Provide suitable constructors and accessors.
- Validate important inputs.
- Use object references (`Customer`, `NowShowing`, `Movie`, `Theatre`) rather than copying all their fields.
- Use a collection for selected seats.
- Keep Swing code out of these classes.
- Coordinate public constructor and method signatures with Members 1, 2 and 4.

## Suggested workflow

1. Pull the latest `main` branch.
2. Create your branch:
   ```bash
   git checkout main
   git pull origin main
   git checkout -b feature/booking-showings
   ```
3. Confirm the agreed `Movie`, `Theatre` and `Customer` interfaces with the group.
4. Implement `NowShowing` and `Booking`.
5. Compile and test using temporary sample objects.
6. Review your changes:
   ```bash
   git status
   git diff
   ```
7. Commit and push:
   ```bash
   git add .
   git commit -m "Implement NowShowing and Booking models"
   git push -u origin feature/booking-showings
   ```
8. Open a Pull Request on GitHub:
   - Base: `main`
   - Compare: `feature/booking-showings`
   - Explain how a booking relates to a customer and showing.

## Definition of done

- [ ] `NowShowing.java` compiles.
- [ ] `Booking.java` compiles.
- [ ] The agreed relationships are represented using object references.
- [ ] Booking total is calculated consistently.
- [ ] Empty and duplicate seat selections are handled.
- [ ] Constructors and required accessors are implemented.
- [ ] Changes are pushed on your feature branch.
- [ ] A Pull Request is opened.

## Coordinate with the group

Your work depends on the Movie, Theatre and Customer interfaces. Agree on their names and constructors early. Member 4's Payment class will reference Booking, so share your final Booking interface with them before either class is finalized.
