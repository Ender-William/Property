package com.kkpip2022.property.entity;

public class LendResponseDetail {
    /* 处理的数据格式如下 -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~
    "data": {
        "sn": "1",
        "itemName": "T1",
        "cateid": 1,
        "quantity": 2000
    }
    -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-*/

    private String sn;
    private String itemName;
    private int cateid;
    private int quantity;

    public String getSn() {
        return sn;
    }
    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getCateid() {
        return cateid;
    }
    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

}
