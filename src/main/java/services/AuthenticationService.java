package services;

import javax.swing.*;
import java.awt.*;

public class AuthenticationService {

    private AuthenticationService() {

    }

    public static int loginAuth(
            JTextField usernameField, JPasswordField passwordField,
            JLabel usernameLabel, JLabel passwordLabel) {
        if (usernameField.getText().isEmpty()) {
            usernameLabel.setText("Username cannot be empty!");
            usernameLabel.setForeground(Color.RED);
            return -1;
        } else if (passwordField.getPassword().length < 8) {
            passwordLabel.setForeground(Color.RED);
            passwordLabel.setText("Password must be longer than 8 characters!");
            return -1;
        }
        return 1;
    }
}