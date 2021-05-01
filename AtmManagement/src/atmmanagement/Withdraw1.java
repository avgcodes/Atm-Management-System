package atmmanagement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.util.Date;

public class Withdraw1 extends JFrame implements ActionListener{
    JTextField t1, t2;
    JButton b1, b2, b3;
    JLabel l1, l2, l3, l4;
    String pin;
    
    Withdraw1(String pin){
        setTitle("WITHDRAWAL");
        this.pin = pin;        
        
        ImageIcon i1 =  new ImageIcon(ClassLoader.getSystemResource("atmmanagement/icons/atm.jpg"));
        Image i2 = i1.getImage().getScaledInstance(1000, 1180, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel l11 = new JLabel(i3);
        l11.setBounds(0, 0, 960, 1080);
        add(l11);
        
        l1 = new JLabel("MAXIMUM DAILY WITHDRAWAL IS Rs.10,000");
        l1.setFont(new Font("System", Font.BOLD, 16));
        l1.setForeground(Color.WHITE);
        
        l2 = new JLabel("PLEASE ENTER YOUR AMOUNT");
        l2.setFont(new Font("System", Font.BOLD, 16));
        l2.setForeground(Color.WHITE);
        
        t1 = new JTextField();
        t1.setFont(new Font("Raleway", Font.BOLD, 22));
        
        t2 = new JTextField();
        t2.setFont(new Font("Raleway", Font.BOLD, 25));
        
        b1 = new JButton("WITHDRAW");
        b2 = new JButton("BACK");
        
        setLayout(null);
        
        l1.setBounds(190, 350, 800, 60);
        l11.add(l1);
        
        l2.setBounds(190, 400, 800, 60);
        l11.add(l2);
        
        t1.setBounds(190, 450, 330, 30);
        l11.add(t1);
        
        b1.setBounds(390, 588, 150, 35);
        l11.add(b1);
        
        b2.setBounds(390, 633, 150, 35);
        l11.add(b2);
        
        b1.addActionListener(this);
        b2.addActionListener(this);
        
        setSize(960, 1080);
        setLocation(500, 0);
        setUndecorated(true);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        try{
     
            if(ae.getSource()==b1){
                String amount = t1.getText();
                Date date = new Date();
                if(t1.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Please enter the amount you want to withdraw");
                }else{
                    Conn c1 = new Conn();
                    ResultSet rs = c1.s.executeQuery("select * from bank where pin = '"+pin+"'");
                    int balance = 0;
                    while(rs.next()){
                        if(rs.getString("mode").equals("Deposit")){
                            balance += Integer.parseInt(rs.getString("amount"));
                        }else{
                            balance -= Integer.parseInt(rs.getString("amount"));
                        }
                    }
                    if(balance < Integer.parseInt(amount)){
                        JOptionPane.showMessageDialog(null, "Insufficient Balance");
                        return;
                    }
                    String q1 = "insert into bank values('"+pin+"', '"+date+"', 'Withdraw1', '"+amount+"')";
                    c1.s.executeUpdate(q1);
                    JOptionPane.showMessageDialog(null, "Rs. "+amount+" Debited Successfully");
                    setVisible(false);
                    new Transactions(pin).setVisible(true);
                }
            }
            else if(ae.getSource()==b2){
                setVisible(false);
                new Transactions(pin).setVisible(true);
                }
            }catch(Exception e){
            e.printStackTrace();
        }        
    }   
    
    public static void main(String[] args){
        new Withdraw1("").setVisible(true);
    }    
}


