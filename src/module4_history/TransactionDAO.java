package module4_history;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import module1_authentication.DBConnection;
import module3_payments.Transaction;

/**
 * Enhanced TransactionDAO - Better error handling and query methods
 */
public class TransactionDAO {

    /**
     * Save transaction with better error handling
     */
    public static boolean saveTransaction(String senderVpa, String receiverVpa,
                                          double amount, String status) {
        if (senderVpa == null || receiverVpa == null || amount <= 0) {
            System.err.println("Invalid transaction parameters");
            return false;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "INSERT INTO transactions(sender_vpa, receiver_vpa, amount, status) " +
                    "VALUES(?,?,?,?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, senderVpa);
            ps.setString(2, receiverVpa);
            ps.setDouble(3, amount);
            ps.setString(4, status);

            int result = ps.executeUpdate();
            return result > 0;

        } catch(Exception e) {
            System.err.println("Error saving transaction: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Get all transactions for a user (sent or received)
     */
    public static ArrayList<Transaction> getTransactions(String vpa) {
        ArrayList<Transaction> list = new ArrayList<>();

        if (vpa == null || vpa.isEmpty()) {
            System.err.println("VPA cannot be null or empty");
            return list;
        }

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM transactions " +
                    "WHERE sender_vpa=? OR receiver_vpa=? " +
                    "ORDER BY transaction_date DESC";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, vpa);
            ps.setString(2, vpa);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Transaction t = new Transaction(
                        rs.getString("sender_vpa"),
                        rs.getString("receiver_vpa"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                list.add(t);
            }

            System.out.println("✓ Loaded " + list.size() + " transactions for " + vpa);

        } catch(Exception e) {
            System.err.println("Error fetching transactions: " + e.getMessage());
            e.printStackTrace();
        }

        return list;
    }

    /**
     * Get sent transactions only
     */
    public static ArrayList<Transaction> getSentTransactions(String vpa) {
        ArrayList<Transaction> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM transactions WHERE sender_vpa=? " +
                    "ORDER BY transaction_date DESC";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, vpa);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Transaction t = new Transaction(
                        rs.getString("sender_vpa"),
                        rs.getString("receiver_vpa"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                list.add(t);
            }

        } catch(Exception e) {
            System.err.println("Error fetching sent transactions: " + e.getMessage());
        }

        return list;
    }

    /**
     * Get received transactions only
     */
    public static ArrayList<Transaction> getReceivedTransactions(String vpa) {
        ArrayList<Transaction> list = new ArrayList<>();

        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT * FROM transactions WHERE receiver_vpa=? " +
                    "ORDER BY transaction_date DESC";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, vpa);

            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                Transaction t = new Transaction(
                        rs.getString("sender_vpa"),
                        rs.getString("receiver_vpa"),
                        rs.getDouble("amount"),
                        rs.getString("status")
                );
                list.add(t);
            }

        } catch(Exception e) {
            System.err.println("Error fetching received transactions: " + e.getMessage());
        }

        return list;
    }

    /**
     * Get total amount transferred
     */
    public static double getTotalSent(String vpa) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT SUM(amount) as total FROM transactions " +
                    "WHERE sender_vpa=? AND status='SUCCESS'";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, vpa);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total") > 0 ? rs.getDouble("total") : 0.0;
            }

        } catch(Exception e) {
            System.err.println("Error calculating total sent: " + e.getMessage());
        }

        return 0.0;
    }

    /**
     * Get total amount received
     */
    public static double getTotalReceived(String vpa) {
        try {
            Connection conn = DBConnection.getConnection();
            String query = "SELECT SUM(amount) as total FROM transactions " +
                    "WHERE receiver_vpa=? AND status='SUCCESS'";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, vpa);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble("total") > 0 ? rs.getDouble("total") : 0.0;
            }

        } catch(Exception e) {
            System.err.println("Error calculating total received: " + e.getMessage());
        }

        return 0.0;
    }
}