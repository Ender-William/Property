package com.kkpip2022.property.entity;

public class LendGetFather {
        /* 处理的数据格式如下：
        {
            "data": {
                "categoryName": "1111"
            },
            "error": 0
        }
    */

    private dataBean data;
    private int error;

    public static class dataBean{
        private String categoryName;

        public String getCategoryName() {
            return categoryName;
        }
        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
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
