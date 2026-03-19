package module3_payments;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import module1_authentication.User;

/**
 * Enhanced SendMoneyFrame - Minimal improvements with validation
 */
public class SendMoneyFrame extends JFrame {

    JTextField receiverField;
    JTextField amountField;
    JLabel errorLabel;
    JButton sendBtn;
    JButton cancelBtn;
    User sender;

    public SendMoneyFrame(User user) {
        this.sender = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Send Money - " + sender.getVpa());
        setSize(450, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main Panel with gradient
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(76, 175, 80),
                        getWidth(), getHeight(), new Color(56, 142, 60));
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

        JLabel title = new JLabel("Send Money via UPI");
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitle = new JLabel("From: " + sender.getVpa());
        subtitle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitle.setForeground(new Color(220, 255, 220));
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        headerPanel.add(title);
        headerPanel.add(Box.createVerticalStrut(3));
        headerPanel.add(subtitle);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new EmptyBorder(25, 30, 25, 30));

        // Receiver VPA
        addFormField(formPanel, "Receiver VPA:", receiverField = new JTextField(),
                "e.g., username@upi");

        // Amount
        addFormField(formPanel, "Amount (₹):", amountField = new JTextField(),
                "Enter amount in rupees");

        // Error Label
        errorLabel = new JLabel("");
        errorLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        errorLabel.setForeground(new Color(244, 67, 54));
        formPanel.add(errorLabel);
        formPanel.add(Box.createVerticalStrut(15));

        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        sendBtn = createButton("Send Money", new Color(76, 175, 80), 140);
        sendBtn.addActionListener(e -> handleSendMoney());

        cancelBtn = createButton("Cancel", new Color(200, 200, 200), 140);
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(sendBtn);
        buttonPanel.add(cancelBtn);
        formPanel.add(buttonPanel);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(formPanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
        receiverField.requestFocus();
    }

    private void addFormField(JPanel panel, String label, JTextField field, String tooltip) {
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lbl.setForeground(new Color(50, 50, 50));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(10, 10, 10, 10)));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 38));
        field.setToolTipText(tooltip);

        panel.add(lbl);
        panel.add(Box.createVerticalStrut(5));
        panel.add(field);
        panel.add(Box.createVerticalStrut(15));
    }

    private void handleSendMoney() {
        errorLabel.setText("");

        String receiver = receiverField.getText().trim();
        String amountStr = amountField.getText().trim();

        // Validate
        if (receiver.isEmpty() || amountStr.isEmpty()) {
            errorLabel.setText("❌ Please enter receiver VPA and amount");
            return;
        }

        if (!receiver.matches("^[a-zA-Z0-9._]+@upi$")) {
            errorLabel.setText("❌ Invalid VPA format (e.g., username@upi)");
            return;
        }

        if (receiver.equals(sender.getVpa())) {
            errorLabel.setText("❌ Cannot send money to yourself");
            return;
        }

        double amount;
        try {
            amount = Double.parseDouble(amountStr);
            if (amount <= 0) {
                errorLabel.setText("❌ Amount must be greater than 0");
                return;
            }
            if (amount > 100000) {
                errorLabel.setText("❌ Amount cannot exceed ₹1,00,000");
                return;
            }
        } catch (NumberFormatException e) {
            errorLabel.setText("❌ Invalid amount format");
            return;
        }

        // Send money
        try {
            PaymentService service = new PaymentService();
            boolean success = service.transfer(sender.getVpa(), receiver, amount);

            if (success) {
                JOptionPane.showMessageDialog(this,
                        String.format("✓ Payment of ₹%.2f sent to %s", amount, receiver),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                clearFields();
            } else {
                errorLabel.setText("❌ Receiver not found or payment failed");
            }
        } catch (Exception ex) {
            errorLabel.setText("❌ Error: " + ex.getMessage());
        }
    }

    private void clearFields() {
        receiverField.setText("");
        amountField.setText("");
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
        User demoUser = new User(1, "Tanish Angadi", "9876543210",
                "tanish@example.com", "tanish@upi", "1234");
        SwingUtilities.invokeLater(() -> new SendMoneyFrame(demoUser));
    }
}