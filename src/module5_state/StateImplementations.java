package module5_state;

import java.util.Date;

/**
 * InitiatedState - Transaction just started
 */
class InitiatedState implements TransactionState {
    private Date timestamp = new Date();

    @Override
    public void handleTransaction() {
        System.out.println("✓ Transaction Initiated at " + timestamp);
    }

    @Override
    public String getStateName() {
        return "INITIATED";
    }

    @Override
    public boolean canTransition(TransactionState nextState) {
        // Can only move to PENDING
        return nextState instanceof PendingState;
    }

    @Override
    public Date getStateTimestamp() {
        return timestamp;
    }
}

/**
 * PendingState - Transaction waiting for processing
 */
class PendingState implements TransactionState {
    private Date timestamp = new Date();

    @Override
    public void handleTransaction() {
        System.out.println("⏳ Transaction Pending - Processing at " + timestamp);
    }

    @Override
    public String getStateName() {
        return "PENDING";
    }

    @Override
    public boolean canTransition(TransactionState nextState) {
        // Can move to SUCCESS or FAILED
        return (nextState instanceof SuccessState) ||
                (nextState instanceof FailedState);
    }

    @Override
    public Date getStateTimestamp() {
        return timestamp;
    }
}

/**
 * SuccessState - Transaction completed successfully
 */
class SuccessState implements TransactionState {
    private Date timestamp = new Date();

    @Override
    public void handleTransaction() {
        System.out.println("✓✓ Transaction Successful at " + timestamp);
    }

    @Override
    public String getStateName() {
        return "SUCCESS";
    }

    @Override
    public boolean canTransition(TransactionState nextState) {
        // Terminal state - cannot transition
        return false;
    }

    @Override
    public Date getStateTimestamp() {
        return timestamp;
    }
}

/**
 * FailedState - Transaction failed
 */
class FailedState implements TransactionState {
    private Date timestamp = new Date();
    private String reason = "Unknown reason";

    public FailedState() {
        this.reason = "Unknown reason";
    }

    public FailedState(String reason) {
        this.reason = reason;
    }

    @Override
    public void handleTransaction() {
        System.out.println("✗ Transaction Failed: " + reason + " at " + timestamp);
    }

    @Override
    public String getStateName() {
        return "FAILED";
    }

    @Override
    public boolean canTransition(TransactionState nextState) {
        // Can retry - go back to INITIATED
        return nextState instanceof InitiatedState;
    }

    @Override
    public Date getStateTimestamp() {
        return timestamp;
    }

    public String getFailureReason() {
        return reason;
    }
}