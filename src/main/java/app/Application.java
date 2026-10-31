package app;

import enums.Screens;
import screens.LoginScreen;
import screens.NowShowingScreen;

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
            registerScreen(Screens.NOW_SHOWING, new NowShowingScreen(this));

            JFrame frame = new JFrame("Multiplex Naalya");
            frame.setContentPane(mainPanel);
            frame.setSize(1000, 600);
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
