package models;


 // A customer who buys tickets at the counter. Customers do not log in,
 
 
public class Customer extends User {

    private String phone;

    public Customer(String customerId, String name, String phone) {
        super(customerId, name);
        this.phone = requireValidPhone(phone);
    }

    /** Booking and other classes can use this name for the customer's ID. */
    public String getCustomerId() {
        return getUserId();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = requireValidPhone(phone);
    }

    @Override
    public String getRole() {
        return "Customer";
    }

    @Override
    public String toString() {
        return super.toString() + " [phone=" + phone + "]";
    }

    private static String requireValidPhone(String phone) {
        String cleaned = requireNonBlank(phone, "Phone").replaceAll("[\\s-]", "");
        // optional leading +, then 9 to 15 digits
        if (!cleaned.matches("^\\+?[0-9]{9,15}$")) {
            throw new IllegalArgumentException("Phone number is not valid: " + phone);
        }
        return cleaned;
    }
}