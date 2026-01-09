package quizApplication;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.awt.event.*;
import java.net.URL;

public class Quiz extends JFrame implements ActionListener {
    private JLabel qno, question;
    private JRadioButton[] options;
    private ButtonGroup optionGroup;
    private JButton nextButton, submit;

    private List<Question> questions;
    private int currentQuestionIndex = 0;
    private int score = 0;

    public static int timer = 15;
    public int totalques = 0;

    String name, rollno;

    Quiz(String name, String rollno) {
        this.name = name;
        this.rollno = rollno;

        System.out.println("Quiz: constructor called for user=" + this.name + " rollno=" + this.rollno);

        // set frame size,color and layout
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(0, 0, screensize.width, screensize.height);

        getContentPane().setBackground(new Color(205, 205, 244));
        setLayout(null);

        // add image to frame
        // ImageIcon i1 = new
        // ImageIcon(ClassLoader.getSystemResource("icons/quiz.jpg"));
        // JLabel image = new JLabel(i1);
        // image.setBounds(0,0,screensize.width,350);
        // add(image);
        // add image to frame (safe loading)
        java.net.URL quizUrl = ClassLoader.getSystemResource("icons/quiz.jpg");
        ImageIcon i1;
        if (quizUrl != null) {
            i1 = new ImageIcon(quizUrl);
        } else {
            System.err.println("Quiz: resource icons/quiz.jpg not found on classpath, trying src/icons/quiz.jpg");
            java.io.File f = new java.io.File("src/icons/quiz.jpg");
            if (f.exists()) {
                i1 = new ImageIcon(f.getAbsolutePath());
            } else {
                System.err.println("Quiz: fallback image src/icons/quiz.jpg also not found, using empty icon.");
                i1 = new ImageIcon(); // avoid NPE; just no banner image
            }
        }
        JLabel image = new JLabel(i1);
        image.setBounds(0, 0, screensize.width, 350);
        add(image);

        // add label for question no.
        qno = new JLabel();
        qno.setBounds(55, 380, 60, 80);
        qno.setFont(new Font("Tahoma", Font.BOLD, 22));
        add(qno);

        // add label for question
        question = new JLabel();
        question.setBounds(135, 380, 1250, 80);
        question.setFont(new Font("Tahoma", Font.BOLD, 20));
        add(question);

        ImagePanel panel = new ImagePanel("icons/java.jpeg");
        panel.setBounds(0, 0, getWidth(), getHeight());
        panel.setOpaque(false); // Make panel transparent
        add(panel);

        // Initialize Radio Buttons for Answer Options
        options = new JRadioButton[4];
        optionGroup = new ButtonGroup();

        for (int i = 0; i < 4; i++) {
            options[i] = new JRadioButton();
            options[i].setBounds(130, 440 + (i * 40), 1250, 50); // Spacing between options
            options[i].setFont(new Font("Tahoma", Font.PLAIN, 18));
            options[i].setBackground(new Color(205, 205, 244)); // Match background
            optionGroup.add(options[i]); // Add to ButtonGroup
            add(options[i]);
        }
        // Next Button
        nextButton = new JButton("Next");
        nextButton.setBounds(175, 650, 150, 40);
        nextButton.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        nextButton.setBackground(Color.BLACK);
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.addActionListener(this);
        add(nextButton);

        // Submit Button
        submit = new JButton("Submit");
        submit.setBounds(375, 650, 150, 40);
        submit.setFont(new Font(Font.MONOSPACED, Font.BOLD, 18));
        submit.setBackground(Color.BLACK);
        submit.setForeground(Color.WHITE);
        submit.setFocusPainted(false);
        submit.addActionListener(this);
        submit.setEnabled(false);
        add(submit);

        // Initialize Questions
        initializeQuestions();
        Collections.shuffle(questions); // Shuffle questions for randomness
        loadQuestion(); // Load the first question

        // Disable resizing
        // setUndecorated(true);

        // Prevent minimizing and maximize (keeps close button)
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            // @Override
            public void windowClosing(WindowEvent e) {
                int confirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                        "Exit Quiz", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        setVisible(true);
    }

    public void paint(Graphics g) {
        super.paint(g);

        String time = "Time Left- " + timer + " seconds";
        g.setColor(new Color(204, 4, 4));
        g.setFont(new Font("Tahoma", Font.BOLD, 28));
        if (timer > 0) {
            g.drawString(time, 1140, 460);
        } else {
            g.drawString("Times up!", 1210, 460);
            moveToNextQuestion();
        }
        timer--;
        try {
            Thread.sleep(1000);
            repaint();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // questions from QuestionBank
    private void initializeQuestions() {
        questions = QuestionBank.getQuestions();

    }
    // class ImagePanel extends JPanel {
    // private Image image;

    // public ImagePanel(String imagePath) {
    // image = new ImageIcon(ClassLoader.getSystemResource(imagePath)).getImage();
    // }

    // @Override
    // protected void paintComponent(Graphics g) {
    // super.paintComponent(g);
    // int x = getWidth() - 400; // Adjust X position
    // int y = getHeight() - 400; // Adjust Y position
    // g.drawImage(image, x, y, 300, 300, this); // Draw image at bottom-right
    // }
    // }
    class ImagePanel extends JPanel {
        private Image image;

        public ImagePanel(String imagePath) {
            // Try to load from classpath first
            URL url = ClassLoader.getSystemResource(imagePath);
            ImageIcon icon = null;

            if (url != null) {
                icon = new ImageIcon(url);
            } else {
                System.err.println("Quiz: resource " + imagePath + " not found on classpath, trying src/" + imagePath);
                java.io.File f = new java.io.File("src/" + imagePath);
                if (f.exists()) {
                    icon = new ImageIcon(f.getAbsolutePath());
                } else {
                    System.err.println("Quiz: fallback image src/" + imagePath + " also not found, using empty image.");
                    icon = new ImageIcon(); // empty icon to avoid NPE
                }
            }

            this.image = icon.getImage();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int x = getWidth() - 400; // Adjust X position
            int y = getHeight() - 400; // Adjust Y position
            g.drawImage(image, x, y, 300, 300, this);
        }
    }

    private void loadQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question q = questions.get(currentQuestionIndex);

            qno.setText((currentQuestionIndex + 1) + "."); // Update question number
            question.setText(q.getQuestionText());

            // Shuffle and display options
            List<String> shuffledOptions = new ArrayList<>(q.getOptions());
            Collections.shuffle(shuffledOptions);

            // Clear selection before displaying new options
            optionGroup.clearSelection();

            for (int i = 0; i < 4; i++) {
                options[i].setText(shuffledOptions.get(i));
                options[i].setActionCommand(shuffledOptions.get(i)); // Set action command for answer checking
                options[i].setSelected(false); // Deselect radio button
            }

            // Enable Submit Button Only for Last Question
            if (currentQuestionIndex == questions.size() - 1) {
                nextButton.setEnabled(false); // Disable Next button
                submit.setEnabled(true); // Enable Submit button
            } else {
                nextButton.setEnabled(true); // Enable Next button
                submit.setEnabled(false); // Keep Submit button disabled
            }
            // Reset and Start Timer
            timer = 15;
            totalques++;
        }
    }

    // @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) { // Handle Next Button
            for (JRadioButton option : options) {
                if (option.isSelected()) {
                    String selectedAnswer = option.getActionCommand();
                    if (selectedAnswer.equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                        score++; // Increase score if correct
                    }
                    break;
                }
            }
            currentQuestionIndex++;
            loadQuestion();
        } else if (e.getSource() == submit) { // Handle Submit Button
            for (JRadioButton option : options) {
                if (option.isSelected()) {
                    String selectedAnswer = option.getActionCommand();
                    if (selectedAnswer.equals(questions.get(currentQuestionIndex).getCorrectAnswer())) {
                        score++;
                    }
                    break;
                }
            }
            // Show Final Score
            setVisible(false);
            new Score(name, rollno, score, totalques);
        }
    }

    private void moveToNextQuestion() {
        currentQuestionIndex++;
        loadQuestion();
    }

    public static void main(String[] args) {

        new Quiz("", "");

    }
}