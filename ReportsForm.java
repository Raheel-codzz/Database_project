package ui;

import util.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ReportsForm extends JFrame {

    public ReportsForm() {
        setTitle("THE MALL OF LAHORE Reports");
        setSize(850, 450);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel imgLabel = new JLabel();
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource("/image/report.png"));
            // Scaled for sidebar placement
            Image img = icon.getImage().getScaledInstance(250, 350, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(img));
        } catch (Exception e) { System.out.println("Report image not found"); }
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);
        imgLabel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        JTable table = new JTable();
        DefaultTableModel model = new DefaultTableModel(new String[]{"Shop", "Sales"}, 0);

        try (Connection con = DBConnection.getConnection()) {
            ResultSet rs = con.createStatement()
                    .executeQuery("SELECT * FROM view_shop_sales");

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getString("shop_name"),
                        rs.getDouble("total_sales")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        table.setModel(model);

        add(new JLabel("SALES SUMMARY REPORT", SwingConstants.CENTER), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(imgLabel, BorderLayout.EAST); // Image on the right side

        setVisible(true);
    }
}