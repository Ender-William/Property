package com.kkpip2022.property.entity;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class CategoryResponse {
    /* 处理的数据格式如下 -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~
    {
        "data": [
            {
                "id": 1,
                "categoryName": "教科书",
                "sonItem": 30,
                "sonTotality": 10000,
                "categoryState": 0
            },
            {
                "id": 2,
                "categoryName": "工具书",
                "sonItem": 12,
                "sonTotality": 10000,
                "categoryState": 0
            }
        ],
        "error": 0
    }
    -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-*/

    private List data;
    private int error;

    public List getData() {
        return data;
    }
    public void setData(List data) {
        this.data = data;
    }

    public int getError() {
        return error;
    }
    public void setError(int error) {
        this.error = error;
    }

}
