package module1_authentication;
import javax.swing.*;

public class LoginFrame extends JFrame {

    JTextField vpaField = new JTextField();
    JPasswordField pinField = new JPasswordField();

    JButton loginBtn = new JButton("Login");

    public LoginFrame() {

        setTitle("UPI Login");

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(new JLabel("VPA"));
        add(vpaField);

        add(new JLabel("PIN"));
        add(pinField);

        add(loginBtn);

        loginBtn.addActionListener(e -> login());

        setSize(300,200);
        setVisible(true);
    }

    private void login() {

    String vpa = vpaField.getText();
    String pin = new String(pinField.getPassword());

    AuthenticationService auth = new AuthenticationService();

    User user = auth.login(vpa, pin);

    if(user != null) {

        JOptionPane.showMessageDialog(this,"Welcome " + user.getName());

        new DashboardFrame(user);   // open dashboard
        dispose();                  // close login screen

    } else {

        JOptionPane.showMessageDialog(this,"Invalid Login");

    }
}
public static void main(String[] args) {

    new LoginFrame();

}
}