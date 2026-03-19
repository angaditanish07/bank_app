package module1_authentication;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

/**
 * Enhanced Registration Frame with Form Validation
 * Features: Email validation, VPA format check, password strength, professional UI
 */
public class RegisterFrame extends JFrame {

    private JTextField nameField;
    private JTextField mobileField;
    private JTextField emailField;
    private JTextField vpaField;
    private JPasswordField pinField;
    private JPasswordField confirmPinField;
    private JLabel validationLabel;
    private JButton registerBtn;
    private JButton loginBtn;
    private UserDAO userDAO;

    // Regex patterns for validation
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern VPA_PATTERN =
            Pattern.compile("^[a-zA-Z0-9._]+@upi$");
    private static final Pattern MOBILE_PATTERN =
            Pattern.compile("^[6-9]\\d{9}$");

    public RegisterFrame() {
        userDAO = new UserDAO();
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Create UPI Account");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 750);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel with gradient
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                GradientPaint gp = new GradientPaint(0, 0, new Color(156, 39, 176),
                        getWidth(), getHeight(), new Color(103, 58, 183));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(30, 20, 20, 20));
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Create Account");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Join UPI Payment System");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(220, 180, 240));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createVerticalStrut(5));
        headerPanel.add(subtitleLabel);

        // Form Panel (Scrollable)
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Full Name
        addFormField(formPanel, "Full Name", nameField = new JTextField(),
                "Enter your full name");

        // Mobile
        addFormField(formPanel, "Mobile Number", mobileField = new JTextField(),
                "10-digit mobile number");

        // Email
        addFormField(formPanel, "Email Address", emailField = new JTextField(),
                "your.email@example.com");

        // VPA
        addFormField(formPanel, "Create VPA", vpaField = new JTextField(),
                "username@upi (unique)");

        // PIN
        JLabel pinLabel = new JLabel("Create PIN (4 digits)");
        pinLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        pinLabel.setForeground(new Color(50, 50, 50));

        pinField = new JPasswordField();
        pinField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        pinField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        pinField.setBackground(new Color(250, 250, 250));
        pinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        pinField.setToolTipText("Must be 4 digits");

        formPanel.add(pinLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(pinField);
        formPanel.add(Box.createVerticalStrut(15));

        // Confirm PIN
        JLabel confirmLabel = new JLabel("Confirm PIN");
        confirmLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        confirmLabel.setForeground(new Color(50, 50, 50));

        confirmPinField = new JPasswordField();
        confirmPinField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        confirmPinField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        confirmPinField.setBackground(new Color(250, 250, 250));
        confirmPinField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        formPanel.add(confirmLabel);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(confirmPinField);
        formPanel.add(Box.createVerticalStrut(15));

        // Validation Label
        validationLabel = new JLabel("");
        validationLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        validationLabel.setForeground(new Color(244, 67, 54));
        formPanel.add(validationLabel);
        formPanel.add(Box.createVerticalStrut(15));

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        registerBtn = createButton("Register", new Color(76, 175, 80), 150);
        registerBtn.addActionListener(e -> handleRegistration());

        loginBtn = createButton("Back to Login", new Color(33, 150, 243), 150);
        loginBtn.addActionListener(e -> backToLogin());

        buttonPanel.add(registerBtn);
        buttonPanel.add(loginBtn);
        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalGlue());

        // Scrollable wrapper
        JScrollPane scrollPane = new JScrollPane(formPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Add panels
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);

        nameField.requestFocus();
    }

    private void addFormField(JPanel panel, String labelText, JTextField field, String tooltip) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(new Color(50, 50, 50));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        field.setBackground(new Color(250, 250, 250));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        field.setToolTipText(tooltip);

        panel.add(label);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    private void handleRegistration() {
        validationLabel.setText("");

        // Get inputs
        String name = nameField.getText().trim();
        String mobile = mobileField.getText().trim();
        String email = emailField.getText().trim();
        String vpa = vpaField.getText().trim();
        String pin = new String(pinField.getPassword()).trim();
        String confirmPin = new String(confirmPinField.getPassword()).trim();

        // Validate
        String error = validateInputs(name, mobile, email, vpa, pin, confirmPin);
        if (error != null) {
            validationLabel.setText("❌ " + error);
            return;
        }

        // Create user
        try {
            User user = new User(0, name, mobile, email, vpa, pin);

            if (userDAO.registerUser(user)) {
                JOptionPane.showMessageDialog(this,
                        "✓ Account created successfully!",
                        "Registration Success",
                        JOptionPane.INFORMATION_MESSAGE);

                new LoginFrame();
                dispose();
            } else {
                validationLabel.setText("❌ Registration failed. VPA might already exist.");
            }
        } catch (Exception ex) {
            validationLabel.setText("❌ Error: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private String validateInputs(String name, String mobile, String email,
                                  String vpa, String pin, String confirmPin) {
        if (name.isEmpty()) return "Name cannot be empty";
        if (name.length() < 3) return "Name must be at least 3 characters";

        if (mobile.isEmpty()) return "Mobile number is required";
        if (!MOBILE_PATTERN.matcher(mobile).matches())
            return "Invalid mobile format (10 digits starting with 6-9)";

        if (email.isEmpty()) return "Email is required";
        if (!EMAIL_PATTERN.matcher(email).matches())
            return "Invalid email format";

        if (vpa.isEmpty()) return "VPA is required";
        if (!VPA_PATTERN.matcher(vpa).matches())
            return "VPA format: username@upi";

        if (pin.isEmpty()) return "PIN is required";
        if (pin.length() != 4 || !pin.matches("\\d+"))
            return "PIN must be exactly 4 digits";

        if (!pin.equals(confirmPin))
            return "PINs do not match";

        return null; // No errors
    }

    private void backToLogin() {
        new LoginFrame();
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
        SwingUtilities.invokeLater(() -> new RegisterFrame());
    }
}