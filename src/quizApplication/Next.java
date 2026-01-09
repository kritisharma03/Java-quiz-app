package quizApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Next extends JFrame implements ActionListener {
    String name, rollno;
    JButton back,start;
    Next(String name, String rollNo){
        this.name = name;
        this.rollno =rollNo;

        getContentPane().setBackground(new Color(33, 33, 41));
        setLayout(null);

        JLabel heading = new JLabel("Hey "+ name+ " ! Welcome to the Testing Minds");
        heading.setBounds(250,40,900,40);
        heading.setFont(new Font("Tahoma", Font.BOLD,35));
        heading.setForeground(new Color(101, 178, 216));
        add(heading);

        JLabel rules = new JLabel();
        rules.setBounds(100,10,1000,500);
        rules.setFont(new Font(Font.DIALOG_INPUT, Font.PLAIN,18));
        rules.setForeground(Color.WHITE);
        rules.setText(
                "<html>"+
                "1. Points: All questions are worth 1 point." + "<br>"+
                "2. Penalty: There are no deductions for wrong answers."+ "<br>"+
                "3. No external help: Participants cannot use search engines, reference materials, or contact other individuals during the quiz." + "<br>"+
                "4. Time limit: Each question will have a specified time limit of 15s for answering.when time is up, the scree automatically loads the next question "+ "<br>"+
                "5. Answer locking: Once the participant moves to next question, the previous one can not be changed"+ "<br>"+
                "6. Answer format: Specified format for answer submission is multiple choice (MCQs)"+ "<br>"+
                "7. Participants can't minimize/maximize or close the quiz window, once the quiz has started"+"<br>"+
                "<html>"
        );
        add(rules);

        back = new JButton("Back");
        back.setBounds(200, 450, 120,25);
        back.setBackground(Color.white);
        back.setForeground(Color.black);
        back.setFocusPainted(false);
        back.addActionListener(this);
        add(back);

        start = new JButton("Start");
        start.setBounds(800, 450, 120,25);
        start.setBackground(Color.white);
        start.setForeground(Color.black);
        start.setFocusPainted(false);
        start.addActionListener(this);
        add(start);

        setSize(1200,600);
        setLocation(200,100);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent ae){
        if(ae.getSource() == back){
            setVisible(false);
            new Login();
        }
        else if (ae.getSource() == start){
            setVisible(false);
            System.out.println("Next: launching Quiz with name='" + name + "', rollno='" + rollno + "'");
            new Quiz(name,rollno);

        }
    }
    public static void main(String[] args) {
        new Next("User", "");
    }
}
