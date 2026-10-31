# Group 8 — Member 4: Payment Model

## Project
**Movie Ticketing System (MTS)**  
Course: CSC 1102 — Structured and Object-Oriented Programming Practical Exam Project  
Group: Group 8 Afternoon Class

## Your responsibility
Implement the payment model.

- Current file: `models/Payments.java`
- Recommended class/file name: `Payment.java`

The group should agree on the rename before you make it, so imports and references remain consistent.

A `Payment` represents an attempt to pay for a `Booking`. It should not itself create bookings or display a Swing payment screen.

## Suggested attributes

| Attribute | Suggested Java type | Purpose |
|---|---|---|
| `paymentId` | `String` | Unique payment identifier |
| `booking` | `Booking` | Booking being paid for |
| `amount` | `double` or `BigDecimal` | Amount paid |
| `paymentDate` | `LocalDateTime` | Date/time of payment |
| `status` | `PaymentStatus` | Payment outcome |
| `paymentMethod` | `String` or enum | Method selected |

For a small assignment, `double` may be acceptable if that is what the group uses consistently. `BigDecimal` is more appropriate for precise monetary calculations in real payment systems. Agree on one approach with the group and use it consistently.

## Payment status

Propose an enum in a separate file, for example:

```java
public enum PaymentStatus {
    PENDING,
    SUCCESSFUL,
    FAILED
}
```

Coordinate with the group before adding the enum and agree where the `enums` package/folder will live.

## Suggested behaviour

- Validate that the amount is positive or nonnegative according to the agreed rules.
- Associate the payment with a valid Booking.
- Provide a method to mark the payment successful.
- Provide a method to mark the payment failed.
- Avoid allowing a completed payment to be changed casually without an agreed rule.
- Provide a useful receipt summary or `toString()`.

This assignment does not require you to connect to a real bank, mobile money provider or payment gateway. Use simulated payment outcomes unless the lecturer specifies otherwise.

## Requirements

- Use `private` fields.
- Provide suitable constructors and accessors.
- Use the agreed `PaymentStatus` enum if adopted.
- Do not put Swing components in the Payment class.
- Do not store real card details, PINs or mobile money credentials.
- Coordinate with Member 3 because `Payment` references `Booking`.

## Suggested workflow

1. Pull the latest `main` branch.
2. Create your branch:
   ```bash
   git checkout main
   git pull origin main
   git checkout -b feature/payment-model
   ```
3. Agree on the Booking interface and money type with the group.
4. Implement the Payment class and, if approved, `PaymentStatus.java`.
5. Compile and test using a dummy Booking and simulated payment result.
6. Review your changes:
   ```bash
   git status
   git diff
   ```
7. Commit and push:
   ```bash
   git add .
   git commit -m "Implement Payment model"
   git push -u origin feature/payment-model
   ```
8. Open a Pull Request on GitHub:
   - Base: `main`
   - Compare: `feature/payment-model`
   - Explain the payment states and how Payment relates to Booking.

## Definition of done

- [ ] `Payment.java` compiles.
- [ ] The group has agreed on the class/file rename.
- [ ] Payment references a Booking.
- [ ] Payment status is represented consistently.
- [ ] Basic validation is present.
- [ ] No real payment credentials or sensitive test data are committed.
- [ ] Changes are pushed on your feature branch.
- [ ] A Pull Request is opened.

## Coordinate with the group

Do not invent a separate Booking structure. Confirm the constructor and getters with Member 3. Confirm the money representation and payment status with the group so the Payment class integrates cleanly.
