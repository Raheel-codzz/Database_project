package ui;

import util.DBConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.*;

public class ProductForm extends JFrame {

    JTextField txtId, txtName, txtPrice, txtShop; // txtId kept in code but removed from UI
    JTable table;

    public ProductForm() {
        setTitle("Product Management");
        setSize(950, 550);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        ImageIcon icon = new ImageIcon("src/resources/images/product.png");
        JLabel imgLabel = new JLabel(icon);
        imgLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel title = new JLabel("Product Management", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));

        JPanel form = new JPanel(new GridLayout(2,4,10,10));
        form.setBorder(BorderFactory.createEmptyBorder(10,15,10,15));

        txtId = new JTextField();
        txtName = new JTextField();
        txtPrice = new JTextField();
        txtShop = new JTextField();

        JButton btnAdd = new JButton("ADD");
        JButton btnUpdate = new JButton("UPDATE");
        JButton btnDelete = new JButton("DELETE");

        form.add(new JLabel("Product Name"));
        form.add(new JLabel("Price"));
        form.add(new JLabel("Shop ID"));
        form.add(new JLabel("Actions"));

        form.add(txtName);
        form.add(txtPrice);
        form.add(txtShop);

        JPanel bp = new JPanel(new GridLayout(1,3,5,5));
        bp.add(btnAdd); bp.add(btnUpdate); bp.add(btnDelete);
        form.add(bp);

        table = new JTable();
        loadData();

        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        btnAdd.addActionListener(e -> addProduct());
        btnUpdate.addActionListener(e -> updateProduct());
        btnDelete.addActionListener(e -> deleteProduct());

        add(title, BorderLayout.NORTH);
        add(form, BorderLayout.SOUTH);
        add(new JScrollPane(table), BorderLayout.CENTER);
        add(imgLabel, BorderLayout.EAST);

        setVisible(true);
    }

    private void addProduct() {
        execute("INSERT INTO product(product_name,price,shop_id) VALUES (?,?,?)",
                txtName.getText(), txtPrice.getText(), txtShop.getText());
    }

    private void updateProduct() {
        execute("UPDATE product SET product_name=?,price=?,shop_id=? WHERE product_id=?",
                txtName.getText(), txtPrice.getText(), txtShop.getText(), txtId.getText());
    }

    private void deleteProduct() {
        execute("DELETE FROM product WHERE product_id=?", txtId.getText());
    }

    private void fillForm() {
        int r = table.getSelectedRow();
        if(r>=0){
            txtId.setText(table.getValueAt(r,0).toString());
            txtName.setText(table.getValueAt(r,1).toString());
            txtPrice.setText(table.getValueAt(r,2).toString());
            txtShop.setText(table.getValueAt(r,3).toString());
        }
    }

    private void loadData() {
        DefaultTableModel m = new DefaultTableModel(
                new String[]{"ID","Name","Price","Shop"},0);

        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM product")){
            while(rs.next())
                m.addRow(new Object[]{rs.getInt(1),rs.getString(2),
                        rs.getDouble(3),rs.getInt(4)});
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