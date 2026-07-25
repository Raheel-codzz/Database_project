package dao;

import model.Shop;
import util.DBConnection;
import java.sql.*;
import java.util.*;

public class ShopDAO {

    public void addShop(Shop s){
        execute("INSERT INTO shop(shop_name,floor_no,owner_id) VALUES (?,?,?)",
                s.getShopName(), s.getFloorNo(), s.getOwnerId());
    }

    public void updateShop(Shop s){
        execute("UPDATE shop SET shop_name=?, floor_no=?, owner_id=? WHERE shop_id=?",
                s.getShopName(), s.getFloorNo(), s.getOwnerId(), s.getShopId());
    }

    public void deleteShop(int id){
        execute("DELETE FROM shop WHERE shop_id=?", id);
    }

    public List<Shop> getAllShops(){
        List<Shop> list = new ArrayList<>();
        try (Connection c = DBConnection.getConnection();
             ResultSet rs = c.createStatement().executeQuery("SELECT * FROM shop")) {
            while(rs.next()){
                list.add(new Shop(rs.getInt(1),rs.getString(2),
                        rs.getInt(3),rs.getInt(4)));
            }
        } catch(Exception ignored){}
        return list;
    }

    private void execute(String sql, Object... data){
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql)){
            for(int i=0;i<data.length;i++) p.setObject(i+1,data[i]);
            p.executeUpdate();
        } catch(Exception ignored){}
    }
}
