package service;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class OrderService {

    public void placeOrder(int customerId, int productId, int qty, double price) {
        Connection con = null;

        try {
            con = DBConnection.getConnection();
            con.setAutoCommit(false);

            PreparedStatement pstOrder = con.prepareStatement(
                    "INSERT INTO orders(customer_id, order_date, total_amount) VALUES (?, CURDATE(), ?)",
                    PreparedStatement.RETURN_GENERATED_KEYS
            );

            pstOrder.setInt(1, customerId);
            pstOrder.setDouble(2, qty * price);
            pstOrder.executeUpdate();

            var rs = pstOrder.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            PreparedStatement pstItem = con.prepareStatement(
                    "INSERT INTO order_item(order_id, product_id, quantity, price) VALUES (?,?,?,?)"
            );
            pstItem.setInt(1, orderId);
            pstItem.setInt(2, productId);
            pstItem.setInt(3, qty);
            pstItem.setDouble(4, price);
            pstItem.executeUpdate();

            PreparedStatement pstInv = con.prepareStatement(
                    "UPDATE inventory SET quantity = quantity - ? WHERE product_id = ?"
            );
            pstInv.setInt(1, qty);
            pstInv.setInt(2, productId);
            pstInv.executeUpdate();

            con.commit();
        }
        catch (Exception e) {
            try { con.rollback(); } catch (Exception ignored) {}
            throw new RuntimeException("Order failed");
        }
    }
}
