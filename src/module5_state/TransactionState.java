package module5_state;

import java.util.Date;

/**
 * Enhanced TransactionState - Interface with more capabilities
 */
public interface TransactionState {
    
    /**
     * Handle the transaction in current state
     */
    void handleTransaction();
    
    /**
     * Get state name for logging
     */
    String getStateName();
    
    /**
     * Check if transition to next state is allowed
     */
    boolean canTransition(TransactionState nextState);
    
    /**
     * Get state timestamp
     */
    Date getStateTimestamp();
}
