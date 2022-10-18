package com.kkpip2022.property.entity;

import java.util.List;

public class CateListResponse {
    /* 处理的数据格式如下 -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~
    {
        "data": [
            {
                "itemName": "T1",
                "quantity": 2000
            },
            {
                "itemName": "T10",
                "quantity": 2000
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
