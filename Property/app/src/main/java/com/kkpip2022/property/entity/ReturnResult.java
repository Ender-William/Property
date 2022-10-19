package com.kkpip2022.property.entity;

public class ReturnResult {

    private dataBean data;
    private int error;

    public static class dataBean{
        private int code;

        public int getCode() {
            return code;
        }
        public void setCode(int code) {
            this.code = code;
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
