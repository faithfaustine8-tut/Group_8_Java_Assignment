package services;

import java.util.ArrayList;
import models.LoginAccount;

public class UserDatabase {

    private static final ArrayList<LoginAccount> users = new ArrayList<>();

    static {
        users.add(new LoginAccount("faustine", "pass123"));
        users.add(new LoginAccount("praise", "auth456"));
        users.add(new LoginAccount("travis", "movies789"));
        users.add(new LoginAccount("jampiere", "timer000"));
    }

    public static ArrayList<LoginAccount> getUsers() {
        return users;
    }
}
