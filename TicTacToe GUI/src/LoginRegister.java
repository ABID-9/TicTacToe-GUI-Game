
import javax.swing.*;
import java.awt.*;
import java.io.*;

public class LoginRegister extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private final File file = new File("users.txt");

    Color background = new Color(13, 17, 35);
    Color panelColor = new Color(25, 32, 58);
    Color cyan = new Color(0, 220, 255);
    Color white = Color.WHITE;

    LoginRegister() {

        setTitle("Tic Tac Toe | Login");
        setSize(500, 580);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(background);
        mainPanel.setLayout(null);

        // Decorative X and O
        JLabel xLabel = new JLabel("X");
        xLabel.setBounds(55, 35, 80, 80);
        xLabel.setFont(new Font("Arial", Font.BOLD, 65));
        xLabel.setForeground(cyan);
        mainPanel.add(xLabel);

        JLabel oLabel = new JLabel("O");
        oLabel.setBounds(365, 35, 80, 80);
        oLabel.setFont(new Font("Arial", Font.BOLD, 65));
        oLabel.setForeground(new Color(170, 100, 255));
        mainPanel.add(oLabel);

        // Title
        JLabel title = new JLabel("TIC TAC TOE");
        title.setBounds(0, 100, 500, 45);
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setForeground(white);
        mainPanel.add(title);

        JLabel subtitle = new JLabel("WELCOME BACK, PLAYER!");
        subtitle.setBounds(0, 145, 500, 25);
        subtitle.setHorizontalAlignment(JLabel.CENTER);
        subtitle.setFont(new Font("Arial", Font.PLAIN, 13));
        subtitle.setForeground(cyan);
        mainPanel.add(subtitle);

        // Login Card
        JPanel card = new JPanel();
        card.setBounds(65, 195, 370, 315);
        card.setBackground(panelColor);
        card.setLayout(null);

        JLabel loginTitle = new JLabel("LOGIN / REGISTER");
        loginTitle.setBounds(0, 20, 370, 35);
        loginTitle.setHorizontalAlignment(JLabel.CENTER);
        loginTitle.setFont(new Font("Arial", Font.BOLD, 21));
        loginTitle.setForeground(white);
        card.add(loginTitle);

        // Username
        JLabel userLabel = new JLabel("USERNAME");
        userLabel.setBounds(35, 75, 300, 20);
        userLabel.setFont(new Font("Arial", Font.BOLD, 12));
        userLabel.setForeground(cyan);
        card.add(userLabel);

        usernameField = new JTextField();
        usernameField.setBounds(35, 100, 300, 40);
        styleField(usernameField);
        card.add(usernameField);

        // Password
        JLabel passLabel = new JLabel("PASSWORD");
        passLabel.setBounds(35, 155, 300, 20);
        passLabel.setFont(new Font("Arial", Font.BOLD, 12));
        passLabel.setForeground(cyan);
        card.add(passLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(35, 180, 300, 40);
        styleField(passwordField);
        card.add(passwordField);

        // Login Button
        JButton loginBtn = new JButton("LOGIN");
        loginBtn.setBounds(35, 245, 140, 42);
        styleButton(loginBtn, cyan, background);
        card.add(loginBtn);

        // Register Button
        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setBounds(195, 245, 140, 42);
        styleButton(registerBtn, new Color(145, 90, 255), white);
        card.add(registerBtn);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        mainPanel.add(card);

        JLabel footer = new JLabel("© TIC TAC TOE GAME");
        footer.setBounds(0, 520, 500, 25);
        footer.setHorizontalAlignment(JLabel.CENTER);
        footer.setFont(new Font("Arial", Font.PLAIN, 11));
        footer.setForeground(Color.GRAY);
        mainPanel.add(footer);

        add(mainPanel);

        setVisible(true);
    }

    // Input Field Design
    private void styleField(JTextField field) {

        field.setBackground(new Color(40, 48, 75));
        field.setForeground(white);
        field.setCaretColor(cyan);
        field.setFont(new Font("Arial", Font.PLAIN, 15));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(65, 80, 120), 1),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
    }

    // Button Design
    private void styleButton(JButton button, Color bg, Color fg) {

        button.setBackground(bg);
        button.setForeground(fg);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    // Register
    public void register() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        if (username.contains(",")) {
            JOptionPane.showMessageDialog(this, "Username cannot contain comma!");
            return;
        }

        try {

            if (file.exists()) {

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {

                    String[] data = line.split(",", 2);

                    if (data.length == 2 && data[0].equals(username)) {
                        br.close();
                        JOptionPane.showMessageDialog(this,
                                "Username already exists!");
                        return;
                    }
                }

                br.close();
            }

            FileWriter fw = new FileWriter(file, true);
            fw.write(username + "," + password + "\n");
            fw.close();

            JOptionPane.showMessageDialog(this,
                    "Registration Successful!");

            passwordField.setText("");

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this,
                    "Registration Failed!");
        }
    }

    // Login
    public void login() {

        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields!");
            return;
        }

        try {

            if (!file.exists()) {
                JOptionPane.showMessageDialog(this,
                        "No account found! Please Register first.");
                return;
            }

            BufferedReader br = new BufferedReader(new FileReader(file));

            String line;
            boolean found = false;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",", 2);

                if (data.length == 2 &&
                    data[0].equals(username) &&
                    data[1].equals(password)) {

                    found = true;
                    break;
                }
            }

            br.close();

            if (found) {

                JOptionPane.showMessageDialog(this,
                        "Login Successful! Welcome " + username);

                dispose();

                SwingUtilities.invokeLater(() -> new TicTacToe());

            } else {

                JOptionPane.showMessageDialog(this,
                        "Invalid Username or Password!");
            }

        } catch (IOException e) {

            JOptionPane.showMessageDialog(this,
                    "Login Error!");
        }
    }
}
