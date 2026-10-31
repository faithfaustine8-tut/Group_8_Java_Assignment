package screens;

import app.Application;
import enums.Screens;

import javax.swing.*;
import java.awt.*;

public class LoginScreen extends JPanel {

    private final Application app;

    // UI components
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JButton loginButton;
    private final JLabel backgroundLabel;

    public LoginScreen(Application app) {
        this.app = app;

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");

        backgroundLabel = createBackgroundLoginImage(
                new ImageIcon("assets/ticket-box.jpg")
        );

        setUpLayout();
        setUpComponents();
        setUpActions();
    }

    private void setUpLayout() {
        setLayout(new GridLayout(1, 2));
    }

    private void setUpComponents() {
        Color midnight = new Color(16, 24, 40);
        Color panel = new Color(23, 33, 52);
        Color accent = new Color(226, 55, 75);
        Color muted = new Color(166, 178, 197);

        JPanel authContainer = new JPanel(new GridBagLayout());
        authContainer.setBackground(midnight);

        JPanel form = new JPanel();
        form.setOpaque(false);
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBorder(BorderFactory.createEmptyBorder(42, 48, 42, 48));
        form.setPreferredSize(new Dimension(420, 520));

        JLabel brand = new JLabel("MULTIPLEX  NAALYA");
        brand.setFont(new Font("SansSerif", Font.BOLD, 13));
        brand.setForeground(new Color(244, 190, 83));
        brand.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel heading = new JLabel("Welcome back");
        heading.setFont(new Font("SansSerif", Font.BOLD, 30));
        heading.setForeground(Color.WHITE);
        heading.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel intro = new JLabel("Ready to keep the cinema running?");
        intro.setFont(new Font("SansSerif", Font.PLAIN, 14));
        intro.setForeground(muted);
        intro.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel usernameLabel = new JLabel("USERNAME");
        JLabel passwordLabel = new JLabel("PASSWORD");
        for (JLabel label : new JLabel[]{usernameLabel, passwordLabel}) {
            label.setFont(new Font("SansSerif", Font.BOLD, 11));
            label.setForeground(muted);
            label.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        for (JTextField field : new JTextField[]{usernameField, passwordField}) {
            field.setFont(new Font("SansSerif", Font.PLAIN, 15));
            field.setForeground(Color.WHITE);
            field.setCaretColor(Color.WHITE);
            field.setBackground(panel);
            field.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(55, 68, 91)),
                    BorderFactory.createEmptyBorder(12, 14, 12, 14)
            ));
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
            field.setAlignmentX(Component.LEFT_ALIGNMENT);
        }

        loginButton.setText("SIGN IN");
        loginButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(accent);
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        loginButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        loginButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        loginButton.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel accentRule = new JPanel();
        accentRule.setBackground(accent);
        accentRule.setMaximumSize(new Dimension(42, 3));
        accentRule.setAlignmentX(Component.LEFT_ALIGNMENT);

        form.add(brand);
        form.add(Box.createVerticalStrut(34));
        form.add(accentRule);
        form.add(Box.createVerticalStrut(18));
        form.add(heading);
        form.add(Box.createVerticalStrut(8));
        form.add(intro);
        form.add(Box.createVerticalStrut(34));
        form.add(usernameLabel);
        form.add(Box.createVerticalStrut(9));
        form.add(usernameField);
        form.add(Box.createVerticalStrut(20));
        form.add(passwordLabel);
        form.add(Box.createVerticalStrut(9));
        form.add(passwordField);
        form.add(Box.createVerticalStrut(28));
        form.add(loginButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(24, 24, 24, 24);
        authContainer.add(form, gbc);

        add(backgroundLabel);
        add(authContainer);
    }

    private void setUpActions() {
        loginButton.addActionListener(event -> {
            // Authentication will be handled here through a service.
            app.showScreen(Screens.NOW_SHOWING);
        });
    }

    private JLabel createBackgroundLoginImage(ImageIcon loginImage) {
        return new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Image image = loginImage.getImage();

                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }
}