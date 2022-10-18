package com.kkpip2022.property.api;

public class ApiConfig {
    // public static final String BASE_URL = "http://192.168.3.83:6500";
    public static final String LOGIN = "/api/login?";  // stuID={$value}&passwd={$value}&token={$value}
    public static final String FIND_USR = "/api/finduser?"; // stuID={$value}
    public static final String REGISTER = "/api/register?"; //
    public static final String GET_DETAIL_USERINFO = "/api/userinfo?";
    public static final String GET_USER_EMAIL = "/api/getalluseremail?";
    public static final String HOME_CATEDETAIL = "/api/getcargoinfo?";
    public static final String GET_CATE_SON_ITEM = "/api/getsonitemlist?";
    public static final String SEARCH_SNCODE = "/api/searchsn?";
    public static final String GET_FATHER_NAME = "/api/getcatename?";
    public static final String TAKE_OUT = "/api/takeout?";
}
