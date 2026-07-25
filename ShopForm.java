package ui;

import util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ShopForm extends JFrame {

    JTextField txtId, txtName, txtFloor, txtOwner;
    JTable table;

    public ShopForm() {
        setTitle("Shop Management");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/resources/images/shop.png");
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("Shop Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JPanel form = new JPanel(new GridLayout(2,4,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        txtId = new JTextField();
        txtName = new JTextField();
        txtFloor = new JTextField();
        txtOwner = new JTextField();

        JButton btnAdd = new JButton("ADD");
        JButton btnUpdate = new JButton("UPDATE");
        JButton btnDelete = new JButton("DELETE");

        form.add(new JLabel("Shop Name"));
        form.add(new JLabel("Floor"));
        form.add(new JLabel("Owner ID"));
        form.add(new JLabel("Actions"));

        form.add(txtName);
        form.add(txtFloor);
        form.add(txtOwner);

        JPanel bp = new JPanel(new GridLayout(1,3,5,5));
        bp.add(btnAdd); bp.add(btnUpdate); bp.add(btnDelete);
        form.add(bp);

        table = new JTable();
        loadData();

        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> addShop());
        btnUpdate.addActionListener(e -> updateShop());
        btnDelete.addActionListener(e -> deleteShop());

        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.SOUTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(imgLabel, BorderLayout.EAST);

        setVisible(true);
    }

    private void addShop() {
        execute("INSERT INTO shop(shop_name,floor_no,owner_id) VALUES (?,?,?)",
                txtName.getText(), txtFloor.getText(), txtOwner.getText());
    }

    private void updateShop() {
        execute("UPDATE shop SET shop_name=?,floor_no=?,owner_id=? WHERE shop_id=?",
                txtName.getText(), txtFloor.getText(), txtOwner.getText(), txtId.getText());
    }

    private void deleteShop() {
        execute("DELETE FROM shop WHERE shop_id=?", txtId.getText());
    }

    private void fillForm() {
        int r = table.getSelectedRow();
        if(r>=0){
            txtId.setText(table.getValueAt(r,0).toString());
            txtName.setText(table.getValueAt(r,1).toString());
            txtFloor.setText(table.getValueAt(r,2).toString());
            txtOwner.setText(table.getValueAt(r,3).toString());
        }
    }

    private void loadData() {
        DefaultTableModel m = new DefaultTableModel(
                new String[]{"ID","Name","Floor","Owner"},0);

        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM shop")){
            while(rs.next())
                m.addRow(new Object[]{rs.getInt(1),rs.getString(2),
                        rs.getInt(3),rs.getInt(4)});
        } catch(Exception ignored){}

        table.setModel(m);
    }

    private void execute(String sql, Object... data){
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)){
            for(int i=0;i<data.length;i++) p.setObject(i+1,data[i]);
            p.executeUpdate();
            loadData();
        } catch(Exception e){
            JOptionPane.showMessageDialog(this,"Database error");
        }
    }
}