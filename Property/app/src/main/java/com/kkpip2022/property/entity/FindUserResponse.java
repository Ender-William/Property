package com.kkpip2022.property.entity;

public class FindUserResponse {
    /* 处理的数据格式如下：
        {
            "data": {
                "stuID": 1111
            },
            "error": 0
        }
    */

    private dataBean data;
    private int error;

    public static class dataBean{
        private int stuID;

        public int getStuID() {
            return stuID;
        }
        public void setStuID(int stuID) {
            this.stuID = stuID;
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
