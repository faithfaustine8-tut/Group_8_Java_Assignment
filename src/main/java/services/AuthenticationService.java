package services;

import data.UserDatabase;
import models.LoginAccount;

public class AuthenticationService {

    /**
     * Checks whether the supplied login details
     * match an account in the user database.
     */
    public boolean authenticate(String username, String password) {

        if (username == null || password == null) {
            return false;
        }

        // Check every account in the user database
        for (LoginAccount account : UserDatabase.getUsers()) {

            if (account.getUsername().equalsIgnoreCase(username.trim())
                    && account.getPassword().equals(password)) {

                return true;
            }
        }

        // No matching account was found
        return false;
    }
}

