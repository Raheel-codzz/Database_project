package service;

import dao.ShopDAO;
import model.Shop;
import java.util.List;

public class ShopService {

    private ShopDAO dao = new ShopDAO();

    public void addShop(Shop s){ dao.addShop(s); }
    public void updateShop(Shop s){ dao.updateShop(s); }
    public void deleteShop(int id){ dao.deleteShop(id); }
    public List<Shop> getAllShops(){ return dao.getAllShops(); }
}
