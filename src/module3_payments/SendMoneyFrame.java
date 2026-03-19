package module3_payments;

import javax.swing.*;
import module1_authentication.User;

public class SendMoneyFrame extends JFrame {

    JTextField receiverField = new JTextField();
    JTextField amountField = new JTextField();
    JButton sendBtn = new JButton("Send");

    User sender;

    public SendMoneyFrame(User user) {

        sender = user;

        setTitle("Send Money");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(new JLabel("Receiver VPA"));
        add(receiverField);

        add(new JLabel("Amount"));
        add(amountField);

        add(sendBtn);

        // Button click event
        sendBtn.addActionListener(e -> sendMoney());

        setSize(300,250);
        setVisible(true);
    }

    private void sendMoney() {

        try {

            String receiver = receiverField.getText();
            double amount = Double.parseDouble(amountField.getText());

            PaymentService service = new PaymentService();

            boolean success = service.transfer(sender.getVpa(), receiver, amount);

            if(success) {

                JOptionPane.showMessageDialog(this,"Payment Successful");

            } else {

                JOptionPane.showMessageDialog(this,"Payment Failed");

            }

        } catch(Exception e) {

            JOptionPane.showMessageDialog(this,"Invalid Amount");

        }
    }
}