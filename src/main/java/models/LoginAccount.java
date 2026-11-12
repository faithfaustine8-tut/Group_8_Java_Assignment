package models;

public class LoginAccount {

    private final String username;
    private final String password;
    private final String fullName;

    public LoginAccount(String username, String password) {
        this(username, password, username);
    }

    public LoginAccount(String username, String password, String fullName) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }
}
