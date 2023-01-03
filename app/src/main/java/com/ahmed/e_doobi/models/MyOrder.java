package com.ahmed.e_doobi.models;

import java.io.Serializable;

public class MyOrder implements Serializable {
    private String id;
    private String clothType;
    private String clothQuantity;

    public MyOrder() {
    }

    public MyOrder(String id, String clothType, String clothQuantity) {
        this.id = id;
        this.clothType = clothType;
        this.clothQuantity = clothQuantity;
    }

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

    public String getClothQuantity() {
        return clothQuantity;
    }

    public void setClothQuantity(String clothQuantity) {
        this.clothQuantity = clothQuantity;
    }
}
