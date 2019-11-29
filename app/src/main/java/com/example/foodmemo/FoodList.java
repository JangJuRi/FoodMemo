package com.example.foodmemo;

public class FoodList {
    private int id;
    private String name;
    private int type;
    private String typeT;
    private int score;
    private int region;
    private String regionT;
    private String phone;
    private String address;
    private String memo;
    private byte[] pic;

    public int getId() {return id;}
    public String getName() {return name;}
    public int getType() {return type;}
    public int getScore() {return score;}
    public int getRegion() {return region;}
    public String getPhone() {return phone;}
    public String getAddress() {return address;}
    public String getMemo() {return memo;}
    public byte[] getPic() {return pic;}
    public String getTypeT() { return typeT; }
    public String getRegionT() { return regionT; }

    public FoodList(int id, String name, int type, String typeT, int score, int region, String regionT, String phone, String address, String memo, byte[] pic) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.typeT = typeT;
        this.score = score;
        this.region = region;
        this.regionT = regionT;
        this.phone = phone;
        this.address = address;
        this.memo = memo;
        this.pic = pic;
    }
}