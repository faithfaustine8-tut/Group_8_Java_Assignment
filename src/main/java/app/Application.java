package app;

import enums.Screens;
import screens.DashboardScreen;
import screens.LoginScreen;

import javax.swing.*;
import java.awt.CardLayout;
import java.util.EnumMap;
import java.util.Map;

public class Application {

    private final Map<Screens, JPanel> screens = new EnumMap<>(Screens.class);
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public void launch() {
        SwingUtilities.invokeLater(() -> {
            cardLayout = new CardLayout();
            mainPanel = new JPanel(cardLayout);

            registerScreen(Screens.LOGIN_SCREEN, new LoginScreen(this));
            registerScreen(Screens.DASHBOARD_SCREEN, new DashboardScreen(this));

            JFrame frame = new JFrame("Multiplex Naalya");
            frame.setContentPane(mainPanel);
            frame.setSize(1280, 820);
            frame.setMinimumSize(new java.awt.Dimension(900, 640));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            showScreen(Screens.LOGIN_SCREEN);
            frame.setVisible(true);
        });
    }

    public void registerScreen(Screens screen, JPanel panel) {
        if (screens.containsKey(screen)) {
            throw new IllegalArgumentException("Screen already registered: " + screen);
        }

        screens.put(screen, panel);
        mainPanel.add(panel, screen.name());
    }

    public void showScreen(Screens screen) {
        if (!screens.containsKey(screen)) {
            throw new IllegalArgumentException("Screen is not registered: " + screen);
        }

        cardLayout.show(mainPanel, screen.name());
    }
}
