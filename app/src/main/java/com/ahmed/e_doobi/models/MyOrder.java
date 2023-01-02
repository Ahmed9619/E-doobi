package com.ahmed.e_doobi.models;

public class MyOrder {
    private String id;
    private String clothType;
    private String clothCount;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClothType() {
        return clothType;
    }

    public void setClothType(String clothType) {
        this.clothType = clothType;
    }

    public String getClothCount() {
        return clothCount;
    }

    public void setClothCount(String clothCount) {
        this.clothCount = clothCount;
    }
}
