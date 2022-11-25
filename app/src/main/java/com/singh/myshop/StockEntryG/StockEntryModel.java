package com.singh.myshop.StockEntryG;

public class StockEntryModel {

    String itemName,itemQty,itemRate,itemDesc,date;

    public StockEntryModel(String itemName, String itemQty, String itemRate,String ItemDesc,String date) {
        this.itemName = itemName;
        this.itemQty = itemQty;
        this.itemRate = itemRate;
        this.itemDesc = ItemDesc;
        this.date = date;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String name) {
        this.itemName = itemName;
    }

    public String getItemOty() {
        return itemQty;
    }

    public void setItemOty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}

