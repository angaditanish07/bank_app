package module1_authentication;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Enhanced Login Frame with Professional UI
 * Features: Form validation, better error handling, modern design
 */
public class LoginFrame extends JFrame {

    private JTextField vpaField;
    private JPasswordField pinField;
    private JButton loginBtn;
    private JButton registerBtn;
    private JLabel errorLabel;
    private AuthenticationService auth;

    public LoginFrame() {
        auth = new AuthenticationService();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("UPI Login - Secure Access");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main container with gradient background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 118, 210),
                        getWidth(), getHeight(), new Color(13, 71, 161));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(30, 20, 20, 20));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("UPI Login");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Secure Payment Access");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(200, 220, 240));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        // VPA Field
        JLabel vpaLabel = new JLabel("Virtual Payment Address (VPA)");
        vpaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        vpaLabel.setForeground(new Color(50, 50, 50));

        vpaField = new JTextField();
        vpaField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        vpaField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        vpaField.setBackground(new Color(250, 250, 250));
        vpaField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        vpaField.setToolTipText("e.g., username@upi");

        // PIN Field
        JLabel pinLabel = new JLabel("UPI PIN");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pinLabel.setForeground(new Color(50, 50, 50));

        pinField = new JPasswordField();
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pinField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        pinField.setBackground(new Color(250, 250, 250));
        pinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pinField.setToolTipText("Enter your 4-digit PIN");

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        errorLabel.setForeground(new Color(244, 67, 54));

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        loginBtn = createButton("Login", new Color(76, 175, 80), 150);
        loginBtn.addActionListener(e -> handleLogin());

        registerBtn = createButton("Register", new Color(33, 150, 243), 150);
        registerBtn.addActionListener(e -> openRegister());

        // Add components to form
        formPanel.add(vpaLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(vpaField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(pinLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(pinField);
        formPanel.add(Box.createVerticalStrut(15));

        formPanel.add(errorLabel);
        formPanel.add(Box.createVerticalStrut(15));

        buttonPanel.add(loginBtn);
        buttonPanel.add(registerBtn);
        formPanel.add(buttonPanel);

        // Add panels to main
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        // Set focus to VPA field
        vpaField.requestFocus();

        // Allow Enter key to trigger login
        pinField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
    }

    private void handleLogin() {
        // Clear previous error
        errorLabel.setText("");

        // Get input
        String vpa = vpaField.getText().trim();
        String pin = new String(pinField.getPassword()).trim();

        // Validate input
        if (vpa.isEmpty() || pin.isEmpty()) {
            errorLabel.setText("❌ Please enter both VPA and PIN");
            return;
        }

        if (pin.length() != 4 || !pin.matches("\\d+")) {
            errorLabel.setText("❌ PIN must be 4 digits");
            return;
        }

        // Authenticate
        try {
            User user = auth.login(vpa, pin);

            if (user != null) {
                JOptionPane.showMessageDialog(this,
                        "✓ Welcome " + user.getName(),
                        "Login Successful",
                        JOptionPane.INFORMATION_MESSAGE);

                new DashboardFrame(user);
                dispose();
            } else {
                errorLabel.setText("❌ Invalid VPA or PIN");
                pinField.setText("");
                pinField.requestFocus();
            }
        } catch (Exception ex) {
            errorLabel.setText("❌ Login error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void openRegister() {
        new RegisterFrame();
        dispose();
    }

    private JButton createButton(String text, Color bgColor, int width) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        btn.setPreferredSize(new Dimension(width, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(
                        Math.max(bgColor.getRed() - 20, 0),
                        Math.max(bgColor.getGreen() - 20, 0),
                        Math.max(bgColor.getBlue() - 20, 0)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame());
    }
}