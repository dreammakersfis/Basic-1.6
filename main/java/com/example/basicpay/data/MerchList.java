package com.example.basicpay.data;


import java.io.Serializable;

public class MerchList implements Serializable {

    private String[] recip = new String[10];
    private String[] recipId = new String[10];
    private String[] recipAddress = new String[10];
    private int recipSel;

    public void setRecipId(String recipId, int i) {
        this.recipId[i] = recipId;
    }

    public String getRecip(int id) {
        recip[0] = "ABCD";
        recip[1] = "EFGH";
        return recip[id];
    }
    public void setRecip(String recip, int i) {
        this.recip[i] = recip;
    }

    public String getRecipId(int i) {
        return recipId[i];
    }

    public void setRecipId(int id, String recipId) {
        this.recipId[id] = recipId;
    }

    public int getSelectedRecipId() {
        return recipSel;
    }

    public void setSelectedRecipId(int i) {
        this.recipSel = i;
    }

    public String getRecipAddress(int id) {
        recipAddress[0] = "First Street";
        recipAddress[1] = "Second Street";
        return recipAddress[id];
    }

    public void setRecipAddress(int id, String recipAddress) {
        this.recipAddress[id] = recipAddress;
    }
}
