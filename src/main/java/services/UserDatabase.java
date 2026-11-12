package services;

import java.util.ArrayList;
import models.LoginAccount;

public class UserDatabase {

    private static final ArrayList<LoginAccount> users = new ArrayList<>();

    static {
        users.add(new LoginAccount("faustine", "pass123", "Abiro Faith Faustine"));
        users.add(new LoginAccount("praise", "auth456", "Ndawula Praise Margaret"));
        users.add(new LoginAccount("travis", "movies789", "Ssemaganda Travis Benjamin"));
        users.add(new LoginAccount("j.p", "timer000", "Tuyishime Jeanpierre"));
        users.add(new LoginAccount("wilfred", "pass123", "Majaliwa Wilfred"));
    }

    public static ArrayList<LoginAccount> getUsers() {
        return users;
    }
}
