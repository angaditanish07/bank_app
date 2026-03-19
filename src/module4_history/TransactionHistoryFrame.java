package module4_history;

import module1_authentication.User;
import module3_payments.Transaction;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Enhanced TransactionHistoryFrame - Professional UI with filtering
 */
public class TransactionHistoryFrame extends JFrame {

    JTable table;
    JLabel countLabel;
    JTextField filterField;
    User currentUser;
    ArrayList<Transaction> allTransactions;
    DefaultTableModel model;

    public TransactionHistoryFrame(User user) {
        this.currentUser = user;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Transaction History - " + currentUser.getVpa());
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);

        // Main Panel
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 247, 250));

        // Top Navigation Bar
        JPanel topNav = createTopNavigation();
        mainPanel.add(topNav, BorderLayout.NORTH);

        // Filter Panel
        JPanel filterPanel = createFilterPanel();
        mainPanel.add(filterPanel, BorderLayout.SOUTH);

        // Table Panel
        JPanel tablePanel = createTablePanel();
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createTopNavigation() {
        JPanel nav = new JPanel(new BorderLayout());
        nav.setBackground(Color.WHITE);
        nav.setBorder(new CompoundBorder(
                new MatteBorder(0, 0, 1, 0, new Color(220, 220, 220)),
                new EmptyBorder(15, 20, 15, 20)));

        JLabel titleLabel = new JLabel("📜 Transaction History");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(255, 152, 0));

        countLabel = new JLabel("Total: 0");
        countLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        countLabel.setForeground(new Color(100, 100, 100));

        nav.add(titleLabel, BorderLayout.WEST);
        nav.add(countLabel, BorderLayout.EAST);

        return nav;
    }

    private JPanel createFilterPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(10, 20, 10, 20));

        JLabel searchLabel = new JLabel("🔍 Search:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));

        filterField = new JTextField(20);
        filterField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        filterField.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1),
                new EmptyBorder(6, 8, 6, 8)));
        filterField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                filterTransactions(filterField.getText());
            }
        });

        JButton refreshBtn = new JButton("🔄 Refresh");
        refreshBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        refreshBtn.setFocusPainted(false);
        refreshBtn.addActionListener(e -> refreshData());

        JButton closeBtn = new JButton("Close");
        closeBtn.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        closeBtn.setFocusPainted(false);
        closeBtn.addActionListener(e -> dispose());

        panel.add(searchLabel);
        panel.add(filterField);
        panel.add(refreshBtn);
        panel.add(closeBtn);

        return panel;
    }

    private JPanel createTablePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(245, 247, 250));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Load transactions
        allTransactions = TransactionDAO.getTransactions(currentUser.getVpa());
        updateCountLabel();

        // Create table model
        String[] columns = {"Type", "Peer VPA", "Amount", "Status", "Date"};
        model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        populateTable(allTransactions);

        // Create table with styling
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        table.setRowHeight(28);
        table.setBackground(Color.WHITE);
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(200, 230, 255));

        // Column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(60);  // Type
        columnModel.getColumn(1).setPreferredWidth(150); // Peer VPA
        columnModel.getColumn(2).setPreferredWidth(100); // Amount
        columnModel.getColumn(3).setPreferredWidth(80);  // Status
        columnModel.getColumn(4).setPreferredWidth(120); // Date

        // Style header
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(25, 118, 210));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setPreferredSize(new Dimension(header.getWidth(), 30));

        // Scroll pane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new LineBorder(new Color(200, 200, 200), 1));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void populateTable(ArrayList<Transaction> transactions) {
        model.setRowCount(0);

        for (Transaction t : transactions) {
            String type = t.getSenderEmail().equals(currentUser.getVpa()) ? "Sent" : "Received";

            String peerVpa = t.getSenderEmail().equals(currentUser.getVpa()) ?
                    t.getReceiverEmail() : t.getSenderEmail();

            Object[] row = {
                    type,
                    peerVpa,
                    "₹ " + String.format("%.2f", t.getAmount()),
                    t.getStatus(),
                    formatDate(t.getDate())
            };

            model.addRow(row);
        }

        updateCountLabel();
    }

    private void filterTransactions(String query) {
        if (query.isEmpty()) {
            populateTable(allTransactions);
            return;
        }

        ArrayList<Transaction> filtered = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        for (Transaction t : allTransactions) {
            String peerVpa = t.getSenderEmail().equals(currentUser.getVpa()) ?
                    t.getReceiverEmail() : t.getSenderEmail();

            if (peerVpa.toLowerCase().contains(lowerQuery) ||
                    String.format("%.2f", t.getAmount()).contains(lowerQuery) ||
                    t.getStatus().toLowerCase().contains(lowerQuery)) {
                filtered.add(t);
            }
        }

        populateTable(filtered);
    }

    private void refreshData() {
        allTransactions = TransactionDAO.getTransactions(currentUser.getVpa());
        populateTable(allTransactions);
        filterField.setText("");
        JOptionPane.showMessageDialog(this, "✓ Data refreshed", "Info",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateCountLabel() {
        countLabel.setText("Total: " + model.getRowCount());
    }

    private String formatDate(java.util.Date date) {
        if (date == null) return "N/A";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        User demoUser = new User(1, "Tanish Angadi", "9876543210",
                "tanish@example.com", "tanish@upi", "1234");
        SwingUtilities.invokeLater(() -> new TransactionHistoryFrame(demoUser));
    }
}