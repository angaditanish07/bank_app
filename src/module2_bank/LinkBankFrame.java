package module2_bank;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Enhanced LinkBankFrame - Minimal improvements to existing code
 */
public class LinkBankFrame extends JFrame {

    JTextField accField;
    JTextField bankField;
    JTextField balanceField;
    JTextField emailField;
    JLabel errorLabel;
    JButton linkButton;

    public LinkBankFrame() {
        setTitle("Link Bank Account");
        setSize(450, 380);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Main Panel with gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 118, 210),
                        getWidth(), getHeight(), new Color(13, 71, 161));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        headerPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

        JLabel title = new JLabel("Link Bank Account");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("Connect your bank to UPI");
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(200, 220, 240));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(3));
        headerPanel.add(subtitle);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(20, 30, 20, 30));

        // Account Number
        addFormField(formPanel, "Account Number:", accField = new JTextField(),
                "12-digit account number");

        // Bank Name
        addFormField(formPanel, "Bank Name:", bankField = new JTextField(),
                "e.g., HDFC Bank, ICICI Bank");

        // Balance
        addFormField(formPanel, "Initial Balance:", balanceField = new JTextField(),
                "Amount in rupees");

        // Email
        addFormField(formPanel, "Email:", emailField = new JTextField(),
                "your.email@example.com");

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        errorLabel.setForeground(new Color(244, 67, 54));
        formPanel.add(errorLabel);
        formPanel.add(Box.createVerticalStrut(10));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        linkButton = createButton("Link Account", new Color(76, 175, 80), 140);
        linkButton.addActionListener(e -> handleLink());

        JButton cancelBtn = createButton("Cancel", new Color(200, 200, 200), 140);
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(linkButton);
        buttonPanel.add(cancelBtn);
        formPanel.add(buttonPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
        accField.requestFocus();
    }

    private void addFormField(JPanel panel, String label, JTextField field, String tooltip) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(50, 50, 50));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 8, 8, 8)));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        field.setToolTipText(tooltip);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(12));
    }

    private void handleLink() {
        errorLabel.setText("");

        String acc = accField.getText().trim();
        String bank = bankField.getText().trim();
        String balStr = balanceField.getText().trim();
        String email = emailField.getText().trim();

        // Validate
        if (acc.isEmpty() || bank.isEmpty() || balStr.isEmpty() || email.isEmpty()) {
            errorLabel.setText("❌ All fields are required");
            return;
        }

        if (acc.length() < 10 || acc.length() > 18) {
            errorLabel.setText("❌ Invalid account number");
            return;
        }

        double balance;
        try {
            balance = Double.parseDouble(balStr);
            if (balance < 0) {
                errorLabel.setText("❌ Balance cannot be negative");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("❌ Invalid balance amount");
            return;
        }

        if (!email.contains("@")) {
            errorLabel.setText("❌ Invalid email format");
            return;
        }

        // Link account
        try {
            BankAccount account = new BankAccount(acc, bank, balance, email);
            if (AccountDAO.linkBankAccount(account)) {
                JOptionPane.showMessageDialog(this,
                        "✓ Bank account linked successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                errorLabel.setText("❌ Account number might already be linked");
            }
        } catch (Exception ex) {
            errorLabel.setText("❌ Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        accField.setText("");
        bankField.setText("");
        balanceField.setText("");
        emailField.setText("");
    }

    private JButton createButton(String text, Color bgColor, int width) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(width, 38));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(
                        Math.max(bgColor.getRed() - 20, 0),
                        Math.max(bgColor.getGreen() - 20, 0),
                        Math.max(bgColor.getBlue() - 20, 0)));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(bgColor);
            }
        });

        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LinkBankFrame());
    }
}