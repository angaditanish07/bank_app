package module5_state;

import java.util.ArrayList;
import java.util.List;

/**
 * Enhanced TransactionContext - Better state management with logging
 */
public class TransactionContext {
    
    private TransactionState currentState;
    private List<String> stateHistory;
    private String transactionId;
    private double amount;
    
    public TransactionContext(String transactionId, double amount) {
        this.transactionId = transactionId;
        this.amount = amount;
        this.stateHistory = new ArrayList<>();
        this.currentState = new InitiatedState();
        logStateChange("INITIATED");
    }
    
    /**
     * Set new state with validation
     */
    public boolean setState(TransactionState newState) {
        // Validate transition
        if (!currentState.canTransition(newState)) {
            System.err.println("✗ Cannot transition from " + 
                currentState.getStateName() + " to " + newState.getStateName());
            return false;
        }
        
        // Log transition
        String oldState = currentState.getStateName();
        this.currentState = newState;
        String newStateStr = newState.getStateName();
        
        System.out.println("→ State transition: " + oldState + " → " + newStateStr);
        logStateChange(newStateStr);
        
        return true;
    }
    
    /**
     * Process transaction in current state
     */
    public void process() {
        currentState.handleTransaction();
    }
    
    /**
     * Get current state name
     */
    public String getCurrentState() {
        return currentState.getStateName();
    }
    
    /**
     * Get state history
     */
    public List<String> getStateHistory() {
        return new ArrayList<>(stateHistory);
    }
    
    /**
     * Get transaction info
     */
    public void printTransactionInfo() {
        System.out.println("\n═══════════════════════════════════════");
        System.out.println("Transaction ID: " + transactionId);
        System.out.println("Amount: ₹" + String.format("%.2f", amount));
        System.out.println("Current State: " + currentState.getStateName());
        System.out.println("State History: " + stateHistory);
        System.out.println("═══════════════════════════════════════\n");
    }
    
    /**
     * Log state change
     */
    private void logStateChange(String state) {
        stateHistory.add(state + " (" + new java.text.SimpleDateFormat("HH:mm:ss")
            .format(new java.util.Date()) + ")");
    }
    
    /**
     * Demo/Test method
     */
    public static void main(String[] args) {
        System.out.println("╔═══════════════════════════════════════╗");
        System.out.println("║  Transaction State Machine Demo       ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        // Create transaction context
        TransactionContext context = new TransactionContext("TXN001", 5000.00);
        
        // Process initial state
        context.process();
        
        // Transition to PENDING
        System.out.println();
        context.setState(new PendingState());
        context.process();
        
        // Transition to SUCCESS
        System.out.println();
        context.setState(new SuccessState());
        context.process();
        
        // Print transaction info
        context.printTransactionInfo();
        
        // Demo: Failed transaction with retry
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║  Failed Transaction with Retry        ║");
        System.out.println("╚═══════════════════════════════════════╝\n");
        
        TransactionContext failedTx = new TransactionContext("TXN002", 10000.00);
        failedTx.process();
        
        System.out.println();
        failedTx.setState(new PendingState());
        failedTx.process();
        
        System.out.println();
        failedTx.setState(new FailedState("Insufficient balance"));
        failedTx.process();
        
        // Retry: back to INITIATED
        System.out.println();
        System.out.println("🔄 Retrying transaction...");
        failedTx.setState(new InitiatedState());
        failedTx.process();
        
        failedTx.printTransactionInfo();
    }
}
