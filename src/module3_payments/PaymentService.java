package module3_payments;

import module1_authentication.User;
import module1_authentication.UserDAO;
import module2_bank.AccountDAO;
import module4_history.TransactionDAO;

/**
 * Enhanced PaymentService - Minimal improvements with validation
 */
public class PaymentService {

    private UserDAO userDAO;
    private TransactionDAO txnDAO;

    public PaymentService() {
        this.userDAO = new UserDAO();
        this.txnDAO = new TransactionDAO();
    }

    /**
     * Transfer money from sender to receiver with validation
     */
    public boolean transfer(String senderVpa, String receiverVpa, double amount) {
        try {
            // Validate inputs
            if (senderVpa == null || receiverVpa == null || amount <= 0) {
                logTransaction(senderVpa, receiverVpa, amount, "FAILED - Invalid input");
                return false;
            }

            // Check if sender and receiver exist
            User sender = userDAO.getUserByVpa(senderVpa);
            User receiver = userDAO.getUserByVpa(receiverVpa);

            if (sender == null || receiver == null) {
                logTransaction(senderVpa, receiverVpa, amount, "FAILED - User not found");
                return false;
            }

            // Prevent self-transfer
            if (senderVpa.equals(receiverVpa)) {
                logTransaction(senderVpa, receiverVpa, amount, "FAILED - Self transfer");
                return false;
            }

            // Check amount limit (₹1,00,000 max)
            if (amount > 100000) {
                logTransaction(senderVpa, receiverVpa, amount, "FAILED - Amount exceeds limit");
                return false;
            }

            // Save transaction as SUCCESS
            txnDAO.saveTransaction(senderVpa, receiverVpa, amount, "SUCCESS");

            System.out.println("✓ Transaction successful: " + senderVpa + " → " +
                    receiverVpa + " : ₹" + amount);

            return true;

        } catch (Exception e) {
            System.err.println("✗ Transaction error: " + e.getMessage());
            logTransaction(senderVpa, receiverVpa, amount, "FAILED - " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Log failed transactions for debugging
     */
    private void logTransaction(String senderVpa, String receiverVpa, double amount, String status) {
        try {
            txnDAO.saveTransaction(senderVpa, receiverVpa, amount, status);
        } catch (Exception e) {
            System.err.println("Could not log transaction: " + e.getMessage());
        }
    }
}