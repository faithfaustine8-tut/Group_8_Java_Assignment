package models;

public class Customer {

    // Private fields (encapsulation)
    private String customerId;
    private String name;
    private String email;
    private String password;

    
    public Customer(String customerId, String name, String email, String password) {
        this.customerId = requireNonBlank(customerId, "Customer ID");
        this.name = requireNonBlank(name, "Name");
        this.email = requireValidEmail(email);
        this.password = requireNonBlank(password, "Password");
    }

    // ---------- Getters ----------

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

        // ---------- Setters (with validation) ----------

    public void setName(String name) {
        this.name = requireNonBlank(name, "Name");
    }

    public void setEmail(String email) {
        this.email = requireValidEmail(email);
    }

    public void setPassword(String password) {
        this.password = requireNonBlank(password, "Password");
    }

    
    public boolean checkPassword(String attempt) {
        return password.equals(attempt);
    }

    
    public String getRole() {
        return "Customer";
    }

    @Override
    public String toString() {
        // The password is NOT included on purpose.
        return getRole() + "{id=" + customerId
                + ", name=" + name
                + ", email=" + email + "}";
    }

    // ---------- Validation helpers ----------

    private static String requireNonBlank(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be blank.");
        }
        return value.trim();
    }

    private static String requireValidEmail(String email) {
        String cleaned = requireNonBlank(email, "Email");
        // something@something.something, with no spaces and a single @
        if (!cleaned.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Email format is not valid: " + cleaned);
        }
        return cleaned;
    }
}