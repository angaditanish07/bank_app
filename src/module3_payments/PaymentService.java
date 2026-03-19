package module3_payments;

import module1_authentication.User;
import module1_authentication.UserDAO;
import module4_history.TransactionDAO;

public class PaymentService {

    public boolean transfer(String senderVpa, String receiverVpa, double amount) {

        try {

            UserDAO userDAO = new UserDAO();

            User sender = userDAO.getUserByVpa(senderVpa);
            User receiver = userDAO.getUserByVpa(receiverVpa);

            if(sender == null || receiver == null) {

                return false;

            }

            TransactionDAO txnDAO = new TransactionDAO();

            txnDAO.saveTransaction(senderVpa, receiverVpa, amount, "SUCCESS");

            return true;

        } catch(Exception e) {

            e.printStackTrace();

        }

        return false;
    }
}