package services;

import models.LoginAccount;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JLabel;

public class AuthenticationService {

    /**
     * Checks whether the supplied login details
     * match an account in the user database.
     */
    public boolean authenticate(String username, String password) {
        return authenticateAccount(username, password) != null;
    }

    public LoginAccount authenticateAccount(String username, String password) {
        if (username == null || password == null) {
            return null;
        }

        // Check every account in the user database
        for (LoginAccount account : UserDatabase.getUsers()) {

            if (account.getUsername().equalsIgnoreCase(username.trim())
                    && account.getPassword().equals(password)) {

                return account;
            }
        }

        // No matching account was found
        return null;
    }

    /**
     * Handles login validation and authentication.
     *
     * Returns 1 for success and 0 for failure.
     */
    public static int loginAuth(
            JTextField usernameField,
            JPasswordField passwordField,
            JLabel usernameLabel,
            JLabel passwordLabel) {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        // Reset previous validation messages
        usernameLabel.setText("");
        passwordLabel.setText("");

        // Check empty fields
        if (username.isEmpty()) {
            usernameLabel.setText("Username is required");
            usernameLabel.setForeground(java.awt.Color.RED);
            return 0;
        }

        if (password.isEmpty()) {
            passwordLabel.setText("Password is required");
            passwordLabel.setForeground(java.awt.Color.RED);
            return 0;
        }

        // Authenticate user
        AuthenticationService auth = new AuthenticationService();

        if (auth.authenticate(username, password)) {
            return 1;
        }

        usernameLabel.setText("Invalid username or password");
        usernameLabel.setForeground(java.awt.Color.RED);
        return 0;
    }
}

