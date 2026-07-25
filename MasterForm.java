package ui;

import util.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class MasterForm extends JFrame {
    private JTable table;
    private String tableName;
    private String[] columns;

    public MasterForm(String title, String tableName, String[] columns) {
        this.tableName = tableName;
        this.columns = columns;
        setTitle(title);
        setSize(800, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lbl = new JLabel(title.toUpperCase(), SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lbl, BorderLayout.NORTH);

        table = new JTable();
        add(new JScrollPane(table), BorderLayout.CENTER);

        loadData(columns);

        JButton btnAdd = new JButton("Add New Entry");
        btnAdd.addActionListener(e -> {
            JPanel panel = new JPanel(new GridLayout(columns.length, 2));
            JTextField[] fields = new JTextField[columns.length];
            for (int i = 0; i < columns.length; i++) {
                panel.add(new JLabel(columns[i]));
                fields[i] = new JTextField();
                panel.add(fields[i]);
            }

            int result = JOptionPane.showConfirmDialog(null, panel, "Enter " + tableName + " Details", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try (Connection con = DBConnection.getConnection()) {
                    StringBuilder sql = new StringBuilder("INSERT INTO " + tableName + " (");
                    StringBuilder values = new StringBuilder("VALUES (");
                    for (int i = 0; i < columns.length; i++) {
                        sql.append(columns[i]).append(i == columns.length - 1 ? "" : ",");
                        values.append("?").append(i == columns.length - 1 ? "" : ",");
                    }
                    sql.append(") ").append(values).append(")");

                    PreparedStatement pstmt = con.prepareStatement(sql.toString());
                    for (int i = 0; i < fields.length; i++) {
                        pstmt.setString(i + 1, fields[i].getText());
                    }
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(this, "Data inserted successfully!");
                    loadData(columns);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
                }
            }
        });
        add(btnAdd, BorderLayout.SOUTH);

        setVisible(true);
    }

    private void loadData(String[] cols) {
        DefaultTableModel model = new DefaultTableModel(cols, 0);
        try (Connection con = DBConnection.getConnection();
             ResultSet rs = con.createStatement().executeQuery("SELECT * FROM " + tableName)) {
            int columnCount = cols.length;
            while (rs.next()) {
                Object[] row = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = rs.getObject(cols[i]);
                }
                model.addRow(row);
            }
        } catch (Exception e) {
            System.out.println("Error loading " + tableName + ": " + e.getMessage());
        }
        table.setModel(model);
    }
}