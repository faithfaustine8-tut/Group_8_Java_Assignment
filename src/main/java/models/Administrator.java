package models;


 //Movie, NowShowing and Theatre classes are available.
 
public class Administrator extends User {

    private String email;
    private String password; // dummy data only in this assignment

    public Administrator(String adminId, String name, String email, String password) {
        super(adminId, name);
        this.email = requireValidEmail(email);
        this.password = requireNonBlank(password, "Password");
    }

    public String getAdminId() {
        return getUserId();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = requireValidEmail(email);
    }

    public void setPassword(String password) {
        this.password = requireNonBlank(password, "Password");
    }

    // No getPassword(): other classes use checkPassword() instead.
    public boolean checkPassword(String attempt) {
        return password.equals(attempt);
    }

    @Override
    public String getRole() {
        return "Administrator";
    }

    @Override
    public String toString() {
        // password intentionally not included
        return super.toString() + " [email=" + email + "]";
    }

    private static String requireValidEmail(String email) {
        String cleaned = requireNonBlank(email, "Email");
        if (!cleaned.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw new IllegalArgumentException("Email format is not valid: " + cleaned);
        }
        return cleaned;
    }
}