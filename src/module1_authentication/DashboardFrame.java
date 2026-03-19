package module1_authentication;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Enhanced Dashboard Frame with User Profile & Account Overview
 * Features: Account balance display, quick actions, logout functionality
 */
public class DashboardFrame extends JFrame {

    private User currentUser;
    private JLabel balanceLabel;
    private JLabel userInfoLabel;
    private JButton sendMoneyBtn;
    private JButton receiveMoneyBtn;
    private JButton historyBtn;
    private JButton logoutBtn;

    public DashboardFrame(User user) {
        this.currentUser = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("UPI Dashboard - " + currentUser.getName());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 650);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Top Navigation Bar
        JPanel topNav = createTopNavigation();
        mainPanel.add(topNav, BorderLayout.NORTH);

        // Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(new Color(245, 247, 250));
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Profile Card
        JPanel profileCard = createProfileCard();
        contentPanel.add(profileCard);
        contentPanel.add(Box.createVerticalStrut(20));

        // Balance Card
        JPanel balanceCard = createBalanceCard();
        contentPanel.add(balanceCard);
        contentPanel.add(Box.createVerticalStrut(20));

        // Quick Actions
        JPanel actionsPanel = createQuickActionsPanel();
        contentPanel.add(actionsPanel);
        contentPanel.add(Box.createVerticalGlue());

        // Wrap in scroll pane
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTopNavigation() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Color.WHITE);
        nav.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 20, 15, 20)));

        JLabel titleLabel = new JLabel("UPI Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        titleLabel.setForeground(new Color(25, 118, 210));

        logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        logoutBtn.setBackground(new Color(244, 67, 54));
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.addActionListener(e -> logout());

        nav.add(titleLabel, BorderLayout.WEST);
        nav.add(logoutBtn, BorderLayout.EAST);

        return nav;
    }

    private JPanel createProfileCard() {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(15, 0));
        card.setBackground(new Color(25, 118, 210));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));

        // User Icon (simulated with initial)
        JLabel iconLabel = new JLabel(String.valueOf(currentUser.getName().charAt(0)).toUpperCase());
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        iconLabel.setForeground(Color.WHITE);
        iconLabel.setOpaque(true);
        iconLabel.setBackground(new Color(13, 71, 161));
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);
        iconLabel.setVerticalAlignment(SwingConstants.CENTER);
        iconLabel.setPreferredSize(new Dimension(60, 60));
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        // User Info
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel nameLabel = new JLabel("Name: " + currentUser.getName());
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nameLabel.setForeground(Color.WHITE);

        JLabel vpaLabel = new JLabel("VPA: " + currentUser.getVpa());
        vpaLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        vpaLabel.setForeground(new Color(200, 220, 240));

        JLabel mobileLabel = new JLabel("Mobile: " + currentUser.getMobile());
        mobileLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        mobileLabel.setForeground(new Color(200, 220, 240));

        infoPanel.add(nameLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(vpaLabel);
        infoPanel.add(Box.createVerticalStrut(3));
        infoPanel.add(mobileLabel);

        card.add(iconLabel, BorderLayout.WEST);
        card.add(infoPanel, BorderLayout.CENTER);

        return card;
    }

    private JPanel createBalanceCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(new Color(76, 175, 80));
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel titleLabel = new JLabel("Account Balance");
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(200, 255, 200));

        balanceLabel = new JLabel("₹ 50,000.00");
        balanceLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        balanceLabel.setForeground(Color.WHITE);

        JLabel noteLabel = new JLabel("Available Balance");
        noteLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        noteLabel.setForeground(new Color(200, 255, 200));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(8));
        card.add(balanceLabel);
        card.add(Box.createVerticalStrut(5));
        card.add(noteLabel);

        return card;
    }

    private JPanel createQuickActionsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(245, 247, 250));

        // Send Money
        sendMoneyBtn = createActionCard("💰 Send Money",
                "Transfer to another VPA", new Color(33, 150, 243));
        sendMoneyBtn.addActionListener(e -> openSendMoney());

        // Receive Money
        receiveMoneyBtn = createActionCard("📥 Receive Money",
                "Share your VPA", new Color(156, 39, 176));
        receiveMoneyBtn.addActionListener(e -> openReceiveMoney());

        // Transaction History
        historyBtn = createActionCard("📜 Transaction History",
                "View past transactions", new Color(255, 152, 0));
        historyBtn.addActionListener(e -> openHistory());

        // Settings
        JButton settingsBtn = createActionCard("⚙️ Settings",
                "Manage account", new Color(158, 158, 158));
        settingsBtn.addActionListener(e -> openSettings());

        panel.add(sendMoneyBtn);
        panel.add(receiveMoneyBtn);
        panel.add(historyBtn);
        panel.add(settingsBtn);

        return panel;
    }

    private JButton createActionCard(String title, String subtitle, Color bgColor) {
        JButton btn = new JButton(
                String.format("<html><div style='text-align:center'>" +
                        "<div style='font-size:14px; font-weight:bold;'>%s</div>" +
                        "<div style='font-size:11px; margin-top:5px;'>%s</div>" +
                        "</div></html>", title, subtitle));

        btn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bgColor);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(20, 15, 20, 15));
        btn.setOpaque(true);
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

    private void openSendMoney() {
        // TODO: Implement SendMoneyFrame
        JOptionPane.showMessageDialog(this,
                "Opening Send Money...\n(Implement module3_payments.SendMoneyFrame)",
                "Send Money",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openReceiveMoney() {
        JOptionPane.showMessageDialog(this,
                "Your VPA: " + currentUser.getVpa() + "\n\nShare this to receive payments",
                "Receive Money",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openHistory() {
        // TODO: Implement HistoryFrame
        JOptionPane.showMessageDialog(this,
                "Opening Transaction History...\n(Implement module4_history.HistoryFrame)",
                "History",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void openSettings() {
        JOptionPane.showMessageDialog(this,
                "Account Settings Coming Soon!",
                "Settings",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void logout() {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to logout?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            new LoginFrame();
            dispose();
        }
    }

    public static void main(String[] args) {
        // Demo with sample user
        User demoUser = new User(1, "Tanish Angadi", "9876543210",
                "tanish@example.com", "tanish@upi", "1234");
        SwingUtilities.invokeLater(() -> new DashboardFrame(demoUser));
    }
}