package com.kkpip2022.property.entity;

public class UserInfoEntity {
    /* 处理的数据格式如下 -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~
        {
            "data": {
                "stuID": 1111,
                "passwd": "admin",
                "stuname": "admin",
                "email": "fuwa165@126.com",
                "authority": "admin",
                "state": 0
            },
            "error": 0
        }
     -~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-~-*/

    private dataBean data;
    private int error;

    public static class dataBean {
        private int stuID;
        private String passwd;
        private String stuname;
        private String email;
        private String authority;
        private int state;

        public int getStuID() {
            return stuID;
        }
        public void setStuID(int stuID) {
            this.stuID = stuID;
        }

        public String getPasswd() {
            return passwd;
        }
        public void setPasswd(String passwd) {
            this.passwd = passwd;
        }

        public String getStuname() {
            return stuname;
        }
        public void setStuname(String stuname) {
            this.stuname = stuname;
        }

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getAuthority() {
            return authority;
        }
        public void setAuthority(String authority) {
            this.authority = authority;
        }

        public int getState() {
            return state;
        }
        public void setState(int state) {
            this.state = state;
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
