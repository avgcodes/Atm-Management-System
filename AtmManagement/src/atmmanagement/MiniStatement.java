package atmmanagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class MiniStatement extends JFrame implements ActionListener {

    JButton b1;
    JLabel l1;
    String pin;

    MiniStatement(String pin) {

        super("Mini Statement");
        this.pin = pin;

        getContentPane().setBackground(Color.WHITE);
        setSize(400, 600);
        setLayout(null);
        setLocation(20, 20);

        JLabel l2 = new JLabel("Indian Bank");
        l2.setBounds(150, 20, 200, 20);
        add(l2);

        l1 = new JLabel();
        l1.setBounds(20, 140, 400, 200);
        add(l1);

        JLabel l3 = new JLabel();
        l3.setBounds(20, 80, 300, 200);
        add(l3);

        JLabel l4 = new JLabel();
        l4.setBounds(20, 400, 300, 20);
        add(l4);

        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("Select * from login where pin = '" + pin + "'");
            while (rs.next()) {
                l3.setText("Card Number:     " + rs.getString("Cardno").substring(0, 4) + "XXXXXXXX" + rs.getString("Cardno").substring(12));
            }
        } catch (Exception e) {

        }
        int balance = 0;
        try {
            Conn c = new Conn();
            ResultSet rs = c.s.executeQuery("Select * from bank where pin = '" + pin + "'");
            while (rs.next()) {
                l1.setText(l1.getText() + "<html>" + rs.getString("date") + "&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("mode") + "&nbsp;&nbsp;&nbsp;&nbsp;" + rs.getString("amount") + "<br><br><html>");
                if (rs.getString("mode").equals("Deposit")) {
                    balance += Integer.parseInt(rs.getString("amount"));
                } else {
                    balance -= Integer.parseInt(rs.getString("amount"));
                }
            }
            l4.setText("Your Current Account Balance is Rs " + balance);

        } catch (Exception e) {
            e.printStackTrace();
        }
        
        
        b1 = new JButton("Back");
        b1.setBounds(20, 500, 100, 25);
        add(b1, "South");

        b1.addActionListener(this);

    }

    public void actionPerformed(ActionEvent ae) {
        setVisible(false);
    }

    public static void main(String[] args) {
        new MiniStatement("").setVisible(true);

    }
}
