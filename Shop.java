package model;

public class Shop {
    private int shopId;
    private String shopName;
    private int floorNo;
    private int ownerId;

    public Shop() {}

    public Shop(int shopId, String shopName, int floorNo, int ownerId) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.floorNo = floorNo;
        this.ownerId = ownerId;
    }

    public int getShopId() { return shopId; }
    public void setShopId(int shopId) { this.shopId = shopId; }
    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
    public int getFloorNo() { return floorNo; }
    public void setFloorNo(int floorNo) { this.floorNo = floorNo; }
    public int getOwnerId() { return ownerId; }
    public void setOwnerId(int ownerId) { this.ownerId = ownerId; }
}
