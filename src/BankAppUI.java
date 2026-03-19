import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BankAppUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    private String currentUser = "Tanish Angadi";
    private double accountBalance = 50000.00;

    public BankAppUI() {
        setTitle("Bank App - UPI Payments System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setResizable(true);
        setUndecorated(false);

        // Set modern look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Main container with card layout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        mainPanel.setBackground(new Color(245, 247, 250));

        // Add different screens
        mainPanel.add(createLoginPanel(), "LOGIN");
        mainPanel.add(createDashboardPanel(), "DASHBOARD");
        mainPanel.add(createTransactionPanel(), "TRANSACTION");
        mainPanel.add(createHistoryPanel(), "HISTORY");

        add(mainPanel);
        cardLayout.show(mainPanel, "LOGIN");

        setVisible(true);
    }

    // ============== LOGIN PANEL ==============
    private JPanel createLoginPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Gradient background
                GradientPaint gp = new GradientPaint(0, 0, new Color(25, 118, 210),
                        getWidth(), getHeight(), new Color(13, 71, 161));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panel.setLayout(new GridBagLayout());

        JPanel loginBox = new JPanel();
        loginBox.setLayout(new BoxLayout(loginBox, BoxLayout.Y_AXIS));
        loginBox.setBackground(Color.WHITE);
        loginBox.setBorder(new RoundedBorder(15, Color.WHITE));
        loginBox.setPreferredSize(new Dimension(350, 400));

        // Title
        JLabel titleLabel = new JLabel("Bank App");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(25, 118, 210));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Secure UPI Payments");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(100, 100, 100));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form fields
        JLabel userLabel = new JLabel("Username");
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(new Color(50, 50, 50));

        JTextField userField = new JTextField(20);
        userField.setText("tanish");
        userField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        userField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 8, 8, 8)));
        userField.setBackground(new Color(250, 250, 250));

        JLabel passLabel = new JLabel("Password");
        passLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        passLabel.setForeground(new Color(50, 50, 50));

        JPasswordField passField = new JPasswordField(20);
        passField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 8, 8, 8)));
        passField.setBackground(new Color(250, 250, 250));

        // Login button
        JButton loginBtn = createPrimaryButton("Login", 250);
        loginBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        JButton registerBtn = createSecondaryButton("Register", 250);

        // Spacing
        loginBox.add(Box.createVerticalStrut(20));
        loginBox.add(titleLabel);
        loginBox.add(Box.createVerticalStrut(5));
        loginBox.add(subtitleLabel);
        loginBox.add(Box.createVerticalStrut(30));
        loginBox.add(userLabel);
        loginBox.add(Box.createVerticalStrut(5));
        loginBox.add(userField);
        loginBox.add(Box.createVerticalStrut(15));
        loginBox.add(passLabel);
        loginBox.add(Box.createVerticalStrut(5));
        loginBox.add(passField);
        loginBox.add(Box.createVerticalStrut(20));
        loginBox.add(loginBtn);
        loginBox.add(Box.createVerticalStrut(10));
        loginBox.add(registerBtn);
        loginBox.add(Box.createVerticalStrut(20));

        GridBagConstraints gbc = new GridBagConstraints();
        panel.add(loginBox, gbc);

        return panel;
    }

    // ============== DASHBOARD PANEL ==============
    private JPanel createDashboardPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Navigation Bar
        JPanel topNav = createTopNavigation("Dashboard");
        panel.add(topNav, BorderLayout.NORTH);

        // Main content
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new GridLayout(2, 2, 15, 15));
        contentPanel.setBackground(new Color(245, 247, 250));

        // Account Card
        JPanel accountCard = createCardPanel(
                "Account Balance",
                "₹ " + String.format("%.2f", accountBalance),
                new Color(25, 118, 210)
        );
        contentPanel.add(accountCard);

        // Quick Actions
        JPanel quickActionsCard = new JPanel();
        quickActionsCard.setLayout(new BoxLayout(quickActionsCard, BoxLayout.Y_AXIS));
        quickActionsCard.setBackground(Color.WHITE);
        quickActionsCard.setBorder(new RoundedBorder(10, Color.WHITE));

        JLabel quickLabel = new JLabel("Quick Actions");
        quickLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        quickLabel.setForeground(new Color(50, 50, 50));

        JButton sendBtn = createActionButton("Send Money", new Color(76, 175, 80));
        sendBtn.addActionListener(e -> cardLayout.show(mainPanel, "TRANSACTION"));

        JButton receiveBtn = createActionButton("Receive Money", new Color(33, 150, 243));
        JButton historyBtn = createActionButton("View History", new Color(255, 152, 0));
        historyBtn.addActionListener(e -> cardLayout.show(mainPanel, "HISTORY"));

        quickActionsCard.add(Box.createVerticalStrut(15));
        quickActionsCard.add(quickLabel);
        quickActionsCard.add(Box.createVerticalStrut(10));
        quickActionsCard.add(sendBtn);
        quickActionsCard.add(Box.createVerticalStrut(8));
        quickActionsCard.add(receiveBtn);
        quickActionsCard.add(Box.createVerticalStrut(8));
        quickActionsCard.add(historyBtn);
        quickActionsCard.add(Box.createVerticalStrut(15));

        contentPanel.add(quickActionsCard);

        // Recent Transactions
        JPanel recentCard = createCardPanel(
                "Recent Transactions",
                "5 transactions\nTotal: ₹ 15,000",
                new Color(156, 39, 176)
        );
        contentPanel.add(recentCard);

        // Account Info
        JPanel infoCard = createCardPanel(
                "Account Type",
                "Savings Account\nActive since 2024",
                new Color(233, 30, 99)
        );
        contentPanel.add(infoCard);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // ============== TRANSACTION PANEL ==============
    private JPanel createTransactionPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Navigation
        JPanel topNav = createTopNavigation("Send Money");
        panel.add(topNav, BorderLayout.NORTH);

        // Form Panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setBorder(new CompoundBorder(
                new RoundedBorder(10, Color.WHITE),
                new EmptyBorder(20, 20, 20, 20)));

        // Recipient
        addFormField(formPanel, "Recipient Name", "Enter name");
        addFormField(formPanel, "UPI ID / Account Number", "recipient@upi");
        addFormField(formPanel, "Amount (₹)", "Enter amount");
        addFormField(formPanel, "Description", "Payment description");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(Color.WHITE);

        JButton sendBtn = createPrimaryButton("Send Money", 150);
        sendBtn.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Payment sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainPanel, "DASHBOARD");
        });

        JButton cancelBtn = createSecondaryButton("Cancel", 150);
        cancelBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        buttonPanel.add(sendBtn);
        buttonPanel.add(cancelBtn);

        formPanel.add(buttonPanel);

        panel.add(new JScrollPane(formPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.CENTER);

        return panel;
    }

    // ============== HISTORY PANEL ==============
    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(15, 15));
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Top Navigation
        JPanel topNav = createTopNavigation("Transaction History");
        panel.add(topNav, BorderLayout.NORTH);

        // Transaction list
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBackground(new Color(245, 247, 250));

        String[][] transactions = {
                {"Rahul Sharma", "₹ 5,000", "Sent", "Today 2:30 PM"},
                {"Priya Verma", "₹ 2,500", "Received", "Yesterday 5:15 PM"},
                {"Amit Kumar", "₹ 10,000", "Sent", "Mar 15, 2:45 PM"},
                {"Neha Singh", "₹ 1,200", "Sent", "Mar 14, 10:20 AM"},
                {"Divya Patel", "₹ 7,500", "Received", "Mar 13, 3:00 PM"}
        };

        for (String[] trans : transactions) {
            listPanel.add(createTransactionItem(trans[0], trans[1], trans[2], trans[3]));
            listPanel.add(Box.createVerticalStrut(8));
        }

        listPanel.add(Box.createVerticalGlue());

        JButton backBtn = createSecondaryButton("Back", 120);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "DASHBOARD"));

        JPanel backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        backPanel.setBackground(new Color(245, 247, 250));
        backPanel.add(backBtn);

        panel.add(new JScrollPane(listPanel), BorderLayout.CENTER);
        panel.add(backPanel, BorderLayout.SOUTH);

        return panel;
    }

    // ============== HELPER METHODS ==============

    private JPanel createTopNavigation(String title) {
        JPanel nav = new JPanel();
        nav.setLayout(new BorderLayout());
        nav.setBackground(Color.WHITE);
        nav.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 0, 15, 0)));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 118, 210));

        JLabel userLabel = new JLabel("👤 " + currentUser);
        userLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        userLabel.setForeground(new Color(100, 100, 100));

        nav.add(titleLabel, BorderLayout.WEST);
        nav.add(userLabel, BorderLayout.EAST);

        return nav;
    }

    private JPanel createCardPanel(String title, String content, Color accentColor) {
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw gradient background
                GradientPaint gp = new GradientPaint(0, 0, accentColor, 0, getHeight(),
                        new Color(Math.max(accentColor.getRed() - 30, 0),
                                Math.max(accentColor.getGreen() - 30, 0),
                                Math.max(accentColor.getBlue() - 30, 0)));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
            }
        };
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(new EmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(255, 255, 255, 200));

        JLabel contentLabel = new JLabel("<html>" + content.replace("\n", "<br>") + "</html>");
        contentLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        contentLabel.setForeground(Color.WHITE);

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(10));
        card.add(contentLabel);

        return card;
    }

    private JButton createPrimaryButton(String text, int width) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(25, 118, 210));
        btn.setFocusPainted(false);
        btn.setBorder(new RoundedBorder(8, new Color(25, 118, 210)));
        btn.setPreferredSize(new Dimension(width, 40));
        btn.setMaximumSize(new Dimension(width, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(21, 101, 192));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(25, 118, 210));
            }
        });

        return btn;
    }

    private JButton createSecondaryButton(String text, int width) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(new Color(100, 100, 100));
        btn.setBackground(new Color(240, 240, 240));
        btn.setFocusPainted(false);
        btn.setBorder(new RoundedBorder(8, new Color(200, 200, 200)));
        btn.setPreferredSize(new Dimension(width, 40));
        btn.setMaximumSize(new Dimension(width, 40));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(new Color(240, 240, 240));
            }
        });

        return btn;
    }

    private JButton createActionButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(color);
        btn.setFocusPainted(false);
        btn.setBorder(new RoundedBorder(6, color));
        btn.setMaximumSize(new Dimension(250, 35));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(
                        Math.max(color.getRed() - 20, 0),
                        Math.max(color.getGreen() - 20, 0),
                        Math.max(color.getBlue() - 20, 0)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(color);
            }
        });

        return btn;
    }

    private void addFormField(JPanel panel, String label, String placeholder) {
        JLabel lblField = new JLabel(label);
        lblField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblField.setForeground(new Color(50, 50, 50));

        JTextField textField = new JTextField(placeholder);
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        textField.setForeground(new Color(150, 150, 150));
        textField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(8, 10, 8, 10)));
        textField.setBackground(new Color(250, 250, 250));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        panel.add(lblField);
        panel.add(Box.createVerticalStrut(5));
        panel.add(textField);
        panel.add(Box.createVerticalStrut(15));
    }

    private JPanel createTransactionItem(String name, String amount, String type, String time) {
        JPanel item = new JPanel();
        item.setLayout(new BorderLayout(15, 0));
        item.setBackground(Color.WHITE);
        item.setBorder(new CompoundBorder(
                new RoundedBorder(8, Color.WHITE),
                new EmptyBorder(12, 15, 12, 15)));

        JLabel nameLabel = new JLabel(name);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        nameLabel.setForeground(new Color(50, 50, 50));

        JLabel timeLabel = new JLabel(time);
        timeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        timeLabel.setForeground(new Color(150, 150, 150));

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.add(nameLabel);
        leftPanel.add(timeLabel);

        Color typeColor = type.equals("Sent") ? new Color(244, 67, 54) : new Color(76, 175, 80);
        JLabel amountLabel = new JLabel(amount);
        amountLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        amountLabel.setForeground(typeColor);

        JLabel typeLabel = new JLabel(type);
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        typeLabel.setForeground(typeColor);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(amountLabel);
        rightPanel.add(typeLabel);
        rightPanel.add(Box.createVerticalGlue());

        item.add(leftPanel, BorderLayout.WEST);
        item.add(rightPanel, BorderLayout.EAST);

        return item;
    }

    // ============== CUSTOM BORDER ==============
    static class RoundedBorder extends AbstractBorder {
        private int radius;
        private Color color;

        RoundedBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(color);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius, radius, radius, radius);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BankAppUI());
    }
}
