package com.example.foodmemo;

public class FoodList {
    private int id;
    private String name;
    private int type;
    private int score;
    private int region;
    private String phone;
    private String address;
    private String memo;
    private String pic;

    public int getId() {return id;}
    public String getName() {return name;}
    public int getType() {return type;}
    public int getScore() {return score;}
    public int getRegion() {return region;}
    public String getPhone() {return phone;}
    public String getAddress() {return address;}
    public String getMemo() {return memo;}
    public String getPic() {return pic;}

    public FoodList(int id, String name, int type, int score, int region, String phone, String address, String memo, String pic) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.score = score;
        this.region = region;
        this.phone = phone;
        this.address = address;
        this.memo = memo;
        this.pic = pic;
    }
}