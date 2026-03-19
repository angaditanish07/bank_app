import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Minimal Bank App UI - Lightweight version
 * Perfect for quick integration and learning
 */
public class BankAppUIMinimal extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String currentUser = "Tanish Angadi";
    private double balance = 50000.00;

    public BankAppUIMinimal() {
        setTitle("Bank App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        // Create main container
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(245, 247, 250));

        // Add screens
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createDashboardPanel(), "DASHBOARD");
        mainPanel.add(createTransactionPanel(), "TRANSACTION");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");
        setVisible(true);
    }

    // ============== LOGIN ==============
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(25, 118, 210));

        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        form.setMaximumSize(new Dimension(350, 300));

        JLabel title = new JLabel("Bank App");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel user = new JLabel("Username:");
        JTextField userField = new JTextField("tanish", 15);
        JLabel pass = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(15);

        JButton login = new JButton("Login");
        login.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        form.add(title);
        form.add(Box.createVerticalStrut(20));
        form.add(user);
        form.add(userField);
        form.add(Box.createVerticalStrut(10));
        form.add(pass);
        form.add(passField);
        form.add(Box.createVerticalStrut(15));
        form.add(login);

        GridBagConstraints gbc = new GridBagConstraints();
        panel.add(form, gbc);
        return panel;
    }

    // ============== DASHBOARD ==============
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Header
        JLabel header = new JLabel("Welcome, " + currentUser);
        header.setFont(new Font("Arial", Font.BOLD, 20));
        header.setForeground(new Color(25, 118, 210));

        JPanel topPanel = new JPanel();
        topPanel.setBackground(new Color(245, 247, 250));
        topPanel.add(header);

        // Balance Card
        JPanel balanceCard = new JPanel();
        balanceCard.setLayout(new BoxLayout(balanceCard, BoxLayout.Y_AXIS));
        balanceCard.setBackground(new Color(25, 118, 210));
        balanceCard.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        balanceCard.setMaximumSize(new Dimension(400, 120));

        JLabel balanceTitle = new JLabel("Account Balance");
        balanceTitle.setForeground(Color.WHITE);
        balanceTitle.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel balanceValue = new JLabel("₹ " + String.format("%.2f", balance));
        balanceValue.setForeground(Color.WHITE);
        balanceValue.setFont(new Font("Arial", Font.BOLD, 28));

        balanceCard.add(balanceTitle);
        balanceCard.add(Box.createVerticalStrut(10));
        balanceCard.add(balanceValue);

        // Buttons Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(245, 247, 250));
        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        JButton sendBtn = new JButton("Send Money");
        sendBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        sendBtn.setBackground(new Color(76, 175, 80));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.addActionListener(e -> cardLayout.show(mainPanel, "TRANSACTION"));

        JButton receiveBtn = new JButton("Receive Money");
        receiveBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        receiveBtn.setBackground(new Color(33, 150, 243));
        receiveBtn.setForeground(Color.WHITE);
        receiveBtn.setFocusPainted(false);

        JButton exitBtn = new JButton("Exit");
        exitBtn.setFont(new Font("Arial", Font.PLAIN, 12));
        exitBtn.setBackground(new Color(244, 67, 54));
        exitBtn.setForeground(Color.WHITE);
        exitBtn.setFocusPainted(false);
        exitBtn.addActionListener(e -> cardLayout.show(mainPanel, "LOGIN"));

        buttonPanel.add(sendBtn);
        buttonPanel.add(receiveBtn);
        buttonPanel.add(exitBtn);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(new Color(245, 247, 250));
        centerPanel.add(balanceCard);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(buttonPanel);
        centerPanel.add(Box.createVerticalGlue());

        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        return panel;
    }

    // ============== TRANSACTION ==============
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel title = new JLabel("Send Money");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(new Color(25, 118, 210));

        // Form
        JPanel form = new JPanel();
        form.setLayout(new BoxLayout(form, BoxLayout.Y_AXIS));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        form.add(new JLabel("Recipient Name:"));
        form.add(new JTextField(20));
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("UPI ID:"));
        form.add(new JTextField(20));
        form.add(Box.createVerticalStrut(10));

        form.add(new JLabel("Amount (₹):"));
        form.add(new JTextField(20));
        form.add(Box.createVerticalStrut(20));

        // Buttons
        JButton sendBtn = new JButton("Send");
        sendBtn.setBackground(new Color(76, 175, 80));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Payment sent successfully!");
            cardLayout.show(mainPanel, "DASHBOARD");
        });

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setBackground(new Color(200, 200, 200));
        cancelBtn.setFocusPainted(false);
        cancelBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(sendBtn);
        buttonPanel.add(cancelBtn);

        form.add(buttonPanel);

        panel.add(title, BorderLayout.NORTH);
        panel.add(form, BorderLayout.CENTER);

        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankAppUIMinimal());
    }
}
