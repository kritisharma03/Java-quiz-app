package quizApplication;

import java.net.URL;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Login extends JFrame implements ActionListener {

    JButton back, next;
    JTextField name, rollno;

    public Login(){
        getContentPane().setBackground(Color.black);
        setLayout(null);

        // ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/login.jpeg"));
        URL url = ClassLoader.getSystemResource("icons/login.jpeg");
        ImageIcon i1;
        if (url != null) {
            i1 = new ImageIcon(url);
        } else {
            System.err.println("Resource icons/login.jpeg not found on classpath — trying src/icons/login.jpeg");
            java.io.File f = new java.io.File("src/icons/login.jpeg");
            if (f.exists()) {
                i1 = new ImageIcon(f.getAbsolutePath());
            } else {
                // final fallback: empty icon (prevents NPE)
                i1 = new ImageIcon();
            }
        }

        JLabel image = new JLabel(i1);
        image.setBounds(0,0,600,600);
        add(image);

        JLabel heading = new JLabel("Testing Minds");
        heading.setBounds(750,100,500,100);
        heading.setFont(new Font("Viner Hand ITC", Font.BOLD,40));
        heading.setForeground(Color.cyan);
        add(heading);

        JLabel heading2 = new JLabel("Enter details here");
        heading2.setBounds(810,180,200,50);
        heading2.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        heading2.setForeground(Color.cyan);
        add(heading2);

        JLabel u_name = new JLabel("Name:");
        u_name.setBounds(710,250,70,50);
        u_name.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        u_name.setForeground(Color.cyan);
        add(u_name);

        name = new JTextField();
        name.setBounds(850,250,250,30);
        name.setFont(new Font("Times New Roman", Font.BOLD,22));
        add(name);

        JLabel u_rollno = new JLabel("Roll no:");
        u_rollno.setBounds(710,300,70,50);
        u_rollno.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        u_rollno.setForeground(Color.cyan);
        add(u_rollno);

        rollno = new JTextField();
        rollno.setBounds(850,300,250,30);
        rollno.setFont(new Font("Times New Roman", Font.BOLD,22));
        add(rollno);

        next = new JButton("Next");
        next.setBounds(950, 430, 120,25);
        next.setBackground(Color.white);
        next.setForeground(Color.black);
        next.setFocusPainted(false);
        next.addActionListener(this);
        add(next);

        back = new JButton("Back");
        back.setBounds(720, 430, 120,25);
        back.setBackground(Color.white);
        back.setForeground(Color.black);
        back.setFocusPainted(false);
        back.addActionListener(this);
        add(back);

        setSize(1200,600);
        setLocation(200,100);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == back){
            setVisible(false);
        }
        else if (ae.getSource() == next){
            String name = this.name.getText().trim();
            String rollText = rollno.getText().trim(); // Get text from JTextField

            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Name cannot be empty!", "Input Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop execution
            }

            if (rollText.isEmpty() || !rollText.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Please enter a valid numeric Roll No.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;  // Stop execution if invalid
            }

            setVisible(false);
            new Next(name,rollText);
        }
    }
    public static void main(String[] args){

        new Login();
    }
}
