package quizApplication;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.net.URL;

public class Score extends JFrame implements ActionListener {
    Score(String name,String rollno, int score, int totalques){
        getContentPane().setBackground(new Color(244, 242, 242));
        setLayout(null);
        setSize(700,450);
        setLocation(450,180);

        // ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/finish.jpeg"));
        URL url = ClassLoader.getSystemResource("icons/finish.jpeg");
        ImageIcon i1;
        if (url != null) {
            i1 = new ImageIcon(url);
        } else {
            System.err.println("Score: resource icons/finish.jpeg not found on classpath, trying src/icons/finish.jpeg");
            java.io.File f = new java.io.File("src/icons/finish.jpeg");
            if (f.exists()) {
                i1 = new ImageIcon(f.getAbsolutePath());
            } else {
                System.err.println("Score: fallback image src/icons/finish.jpeg also not found, using empty icon.");
                i1 = new ImageIcon(); // avoid NPE, just no image
            }
        }

        Image i2 = i1.getImage().getScaledInstance(300,200,Image.SCALE_DEFAULT);
        ImageIcon i = new ImageIcon(i2);
        JLabel image = new JLabel(i);
        image.setBounds(0,0,300,200);
        add(image);

        JLabel heading = new JLabel("Congratulations !");
        heading.setBounds(400,40,600,40);
        heading.setFont(new Font("Tahoma", Font.BOLD,20));
        heading.setForeground(new Color(21, 20, 20));
        add(heading);

        JLabel message = new JLabel("Quiz completed successfully");
        message.setBounds(350,100,600,40);
        message.setFont(new Font("Tahoma", Font.BOLD,20));
        message.setForeground(new Color(11, 11, 11));
        add(message);

        JLabel scoremsg = new JLabel("Your Score is "+ score );
        scoremsg.setBounds(160,250,600,60);
        scoremsg.setFont(new Font("Viner Hand ITC", Font.BOLD,48));
        scoremsg.setForeground(new Color(145, 5, 9));
        add(scoremsg);

        JButton end = new JButton("Finish");
        end.setBounds(280, 350, 150,25);
        end.setBackground(Color.black);
        end.setForeground(Color.white);
        end.setFocusPainted(false);
        end.addActionListener(this);
        add(end);

//          Save to Database
        try {
            DBConnection.saveScore(name, rollno, score, totalques);
        } catch (Exception e) {
           e.printStackTrace();
        }

        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        setVisible(false);
    }
    public static void main(String[] args) {
        new Score("","",0,0);


    }
}
