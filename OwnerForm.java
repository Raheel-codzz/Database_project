package ui;

import util.DBConnection;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class OwnerForm extends JFrame {

    JTextField txtId, txtName, txtEmail, txtPhone;
    JTable table;

    public OwnerForm() {
        setTitle("Owner Management");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Owner Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JPanel form = new JPanel(new GridLayout(2,4,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        txtId = new JTextField();
        txtName = new JTextField();
        txtEmail = new JTextField();
        txtPhone = new JTextField();

        JButton btnAdd = new JButton("ADD");
        JButton btnUpdate = new JButton("UPDATE");
        JButton btnDelete = new JButton("DELETE");

        form.add(new JLabel("Owner Name"));
        form.add(new JLabel("Email"));
        form.add(new JLabel("Phone"));
        form.add(new JLabel("Actions"));

        form.add(txtName);
        form.add(txtEmail);
        form.add(txtPhone);

        JPanel bp = new JPanel(new GridLayout(1,3,5,5));
        bp.add(btnAdd); bp.add(btnUpdate); bp.add(btnDelete);
        form.add(bp);

        table = new JTable();
        loadData();

        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> addOwner());
        btnUpdate.addActionListener(e -> updateOwner());
        btnDelete.addActionListener(e -> deleteOwner());

        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.SOUTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        setVisible(true);
    }

    private void addOwner() {
        execute("INSERT INTO shop_owner(owner_name, email, phone) VALUES (?,?,?)",
                txtName.getText(), txtEmail.getText(), txtPhone.getText());
    }

    private void updateOwner() {
        execute("UPDATE shop_owner SET owner_name=?, email=?, phone=? WHERE owner_id=?",
                txtName.getText(), txtEmail.getText(), txtPhone.getText(), txtId.getText());
    }

    private void deleteOwner() {
        execute("DELETE FROM shop_owner WHERE owner_id=?", txtId.getText());
    }

    private void fillForm() {
        int r = table.getSelectedRow();
        if(r >= 0){
            txtId.setText(table.getValueAt(r,0).toString());
            txtName.setText(table.getValueAt(r,1).toString());
            txtEmail.setText(table.getValueAt(r,2).toString());
            txtPhone.setText(table.getValueAt(r,3).toString());
        }
    }

    private void loadData() {
        DefaultTableModel m = new DefaultTableModel(
                new String[]{"ID","Name","Email","Phone"},0);

        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM shop_owner")){
            while(rs.next())
                m.addRow(new Object[]{rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4)});
        } catch(Exception ignored){}

        table.setModel(m);
    }

    private void execute(String sql, Object... data){
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)){
            for(int i=0; i<data.length; i++) p.setObject(i+1, data[i]);
            p.executeUpdate();
            loadData();
        } catch(Exception e){
            JOptionPane.showMessageDialog(this, "Database error");
        }
    }
}