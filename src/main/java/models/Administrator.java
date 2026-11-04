package models;


public class Administrator extends Customer {

    private String administratorId;

    public Administrator(String customerId, String name, String email,
                         String password, String administratorId) {
        super(customerId, name, email, password); // reuse Customer's validation
        if (administratorId == null || administratorId.trim().isEmpty()) {
            throw new IllegalArgumentException("Administrator ID must not be blank.");
        }
        this.administratorId = administratorId.trim();
    }

    public String getAdministratorId() {
        return administratorId;
    }

    @Override
    public String getRole() {
        return "Administrator";
    }

    @Override
    public String toString() {
        // Reuses Customer's toString (which already hides the password)
        return super.toString() + " [administratorId=" + administratorId + "]";
    }
}