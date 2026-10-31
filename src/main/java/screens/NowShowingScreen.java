package screens;

import app.Application;
import enums.Screens;

import javax.swing.*;

public class NowShowingScreen extends JPanel {

    private final Application app;

    public NowShowingScreen(Application app) {
        this.app = app;
        add(new JTextField("Now Showing"));

        JButton logoutButton = new JButton("Logout");
        add(logoutButton);
        logoutButton.addActionListener(e -> {
            this.app.showScreen(Screens.LOGIN_SCREEN);
        });
    }
}