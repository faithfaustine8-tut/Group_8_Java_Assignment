package models;

import java.time.LocalDateTime;
import enums.PaymentStatus;

public class Payment {

    // Attributes
    private String paymentId;
    private Booking booking;
    private double amount;
    private LocalDateTime paymentDate;
    private PaymentStatus status;
    private String paymentMethod;

    // Constructor
    public Payment(String paymentId, Booking booking,
                   double amount, String paymentMethod) {

        if (paymentId == null || paymentId.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment ID cannot be empty");
        }

        if (booking == null) {
            throw new IllegalArgumentException("Booking cannot be null");
        }

        if (amount <= 0 || Double.isNaN(amount)
                || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Amount must be positive");
        }

        if (paymentMethod == null || paymentMethod.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment method cannot be empty");
        }

        this.paymentId = paymentId;
        this.booking = booking;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = LocalDateTime.now();
        this.status = PaymentStatus.PENDING;
    }

    // Getters
    public String getPaymentId() {
        return paymentId;
    }

    public Booking getBooking() {
        return booking;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    // Mark payment as successful
    public void markSuccessful() {

        if (status != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                "Only pending payments can be completed."
            );
        }

        status = PaymentStatus.SUCCESSFUL;
    }

    // Mark payment as failed
    public void markFailed() {

        if (status != PaymentStatus.PENDING) {
            throw new IllegalStateException(
                "Only pending payments can be completed."
            );
        }

        status = PaymentStatus.FAILED;
    }

    // Check whether payment is completed
    public boolean isCompleted() {
        return status == PaymentStatus.SUCCESSFUL
                || status == PaymentStatus.FAILED;
    }

    // Display payment receipt
    @Override
    public String toString() {

        return "----- PAYMENT RECEIPT -----\n"
                + "Payment ID: " + paymentId + "\n"
                + "Booking: " + booking + "\n"
                + "Amount: UGX " + String.format("%.2f", amount) + "\n"
                + "Payment Method: " + paymentMethod + "\n"
                + "Payment Date: " + paymentDate + "\n"
                + "Payment Status: " + status + "\n"
                + "---------------------------";
    }
}