package ui;

import javax.swing.*;
import java.awt.*;

public class Dashboard extends JFrame {

    public Dashboard() {
        setTitle("THE MALL OF LAHORE - Management System");
        setSize(1250, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(245, 245, 245));
        JLabel header = new JLabel("THE MALL OF LAHORE MANAGEMENT SYSTEM", SwingConstants.CENTER);
        header.setFont(new Font("Segoe UI", Font.BOLD, 28));
        header.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        headerPanel.add(header);

        // Sidebar Menu - Wahi 10 buttons bina kisi change ke
        JPanel menu = new JPanel(new GridLayout(10, 1, 10, 10));
        menu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menu.setPreferredSize(new Dimension(280, 0));

        JButton btnShop = new JButton("Shop Management");
        JButton btnProduct = new JButton("Product Management");
        JButton btnOwner = new JButton("Owner Management");
        JButton btnOrder = new JButton("Order Management");
        JButton btnEmp = new JButton("Employee Management");
        JButton btnSup = new JButton("Supplier Management");
        JButton btnMaint = new JButton("Mall Maintenance");
        JButton btnReport = new JButton("Reports & Analytics");
        JButton btnFeedback = new JButton("Customer Feedback");
        JButton btnLogout = new JButton("Logout System");

        menu.add(btnShop); menu.add(btnProduct); menu.add(btnOwner);
        menu.add(btnOrder); menu.add(btnEmp); menu.add(btnSup);
        menu.add(btnMaint); menu.add(btnReport); menu.add(btnFeedback); menu.add(btnLogout);

        // Central Image Section (LOGO FIX)
        JPanel imageContainer = new JPanel(new GridBagLayout()); // Central alignment ke liye
        JLabel imgLabel = new JLabel();

        try {
            // Check karein ke logo.png "src/image/" folder mein maujood hai
            java.net.URL imgURL = getClass().getResource("/image/logo.png");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image img = icon.getImage().getScaledInstance(600, 500, Image.SCALE_SMOOTH);
                imgLabel.setIcon(new ImageIcon(img));
            } else {
                imgLabel.setText("IMAGE NOT FOUND in src/image/logo.png");
            }
        } catch (Exception e) {
            imgLabel.setText("Logo Loading Error");
        }
        imageContainer.add(imgLabel);

        add(headerPanel, BorderLayout.NORTH);
        add(menu, BorderLayout.WEST);
        add(imageContainer, BorderLayout.CENTER);

        // Action Listeners (Same logic as provided)
        btnShop.addActionListener(e -> new MasterForm("Shop Management", "shop", new String[]{"shop_id", "shop_name", "floor_no", "owner_id"}));
        btnProduct.addActionListener(e -> new MasterForm("Product Management", "product", new String[]{"product_id", "product_name", "price", "shop_id"}));
        btnOwner.addActionListener(e -> new MasterForm("Owner Management", "shop_owner", new String[]{"owner_id", "owner_name", "email", "phone"}));
        btnOrder.addActionListener(e -> new MasterForm("Order Management", "orders", new String[]{"order_id", "customer_id", "order_date", "total_amount"}));
        btnEmp.addActionListener(e -> new MasterForm("Employee Management", "employee", new String[]{"emp_id", "emp_name", "designation", "salary"}));
        btnSup.addActionListener(e -> new MasterForm("Supplier Management", "supplier", new String[]{"sup_id", "sup_name", "contact"}));
        btnMaint.addActionListener(e -> new MasterForm("Mall Maintenance", "maintenance", new String[]{"m_id", "shop_id", "amount", "m_date"}));

        // Reporting Logic
        btnReport.addActionListener(e -> new MasterForm("Sales Summary", "view_shop_sales", new String[]{"shop_name", "total_sales"}));
        btnFeedback.addActionListener(e -> new MasterForm("Customer Feedback", "feedback", new String[]{"f_id", "customer_id", "comments", "rating"}));

        btnLogout.addActionListener(e -> { dispose(); new LoginForm(); });

        setVisible(true);
    }
}