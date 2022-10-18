package com.kkpip2022.property.entity;

public class LendResponse {
    /* 处理的数据格式如下 -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~
    {
        "data": {
            "sn": "1",
            "itemName": "T1",
            "cateid": 1,
            "quantity": 2000
        },
        "error": 0
    }
    -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-*/

    private dataBean data;
    private int error;

    public static class dataBean{
        private String sn;
        private String itemName;
        private int cateid;
        private String quantity;

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

        public String getQuantity() {
            return quantity;
        }
        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }
    }

    public dataBean getData() {
        return data;
    }
    public void setData(dataBean data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }
    public void setError(int error) {
        this.error = error;
    }

}
