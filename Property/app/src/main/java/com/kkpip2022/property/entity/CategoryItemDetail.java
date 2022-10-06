package com.kkpip2022.property.entity;

public class CategoryItemDetail {
    /* 处理的数据格式如下 -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~
        {
            "id": 2,
            "categoryName": "工具书",
            "sonItem": 12,
            "sonTotality": 10000,
            "categoryState": 0
        }
    -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-*/

    private int cateid;
    private String categoryName;
    private int sonItem;
    private int sonTotality;
    private int categoryState;

    public int getCateid() {
        return cateid;
    }
    public void setCateid(int cateid) {
        this.cateid = cateid;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public int getSonItem() {
        return sonItem;
    }
    public void setSonItem(int sonItem) {
        this.sonItem = sonItem;
    }

    public int getSonTotality() {
        return sonTotality;
    }
    public void setSonTotality(int sonTotality) {
        this.sonTotality = sonTotality;
    }

    public int getCategoryState() {
        return categoryState;
    }
    public void setCategoryState(int categoryState) {
        this.categoryState = categoryState;
    }
}
