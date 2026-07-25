package ui;

import dao.UserDAO;
import javax.swing.*;
import java.awt.*;

public class LoginForm extends JFrame {

    JTextField txtUser;
    JPasswordField txtPass;

    public LoginForm() {
        setTitle("THE MALL OF LAHORE - Login");
        setSize(500, 400); // Increased height slightly so scaled image fits with text fields
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel imgLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/image/login.png"));
            Image img = icon.getImage().getScaledInstance(600, 350, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) { System.out.println("Image not found"); }
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("THE MALL OF LAHORE", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JPanel form = new JPanel(new GridLayout(3,2,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,50,10,50));

        txtUser = new JTextField();
        txtPass = new JPasswordField();

        JButton btnLogin = new JButton("LOGIN");

        form.add(new JLabel("Username"));
        form.add(txtUser);
        form.add(new JLabel("Password"));
        form.add(txtPass);
        form.add(new JLabel(""));
        form.add(btnLogin);

        add(title, BorderLayout.NORTH);
        add(imgLabel, BorderLayout.CENTER);
        add(form, BorderLayout.SOUTH);

        btnLogin.addActionListener(e -> login());

        setVisible(true);
    }

    private void login() {
        if(new UserDAO().validateUser(txtUser.getText(), new String(txtPass.getPassword()))) {
            new Dashboard();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,"Invalid login");
        }
    }

    public static void main(String[] args) {
        new LoginForm();
    }
}