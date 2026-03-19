package module1_authentication;

import javax.swing.*;

public class DashboardFrame extends JFrame {

    JButton sendMoneyBtn = new JButton("Send Money");
    JButton historyBtn = new JButton("Transaction History");

    User currentUser;

    public DashboardFrame(User user) {

        currentUser = user;

        setTitle("UPI Dashboard - " + user.getName());

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(sendMoneyBtn);
        add(historyBtn);

        sendMoneyBtn.addActionListener(e -> openSendMoney());

        setSize(300,200);
        setVisible(true);
    }

    private void openSendMoney() {

        new module3_payments.SendMoneyFrame(currentUser);

    }
}