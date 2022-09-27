package com.kkpip2022.property.util;

import java.util.Objects;

public class StringUtils {

    //判断字符串是否为空
    public static boolean isEmpty(String str){
        if(str == null || str.length()<=0){
            return true;
        }else{
            return false;
        }
    }

    // 两次密码一致性判断
    public static boolean CheckPasswdSame(String tar_passwd, String passwd) {
        if (Objects.equals(passwd, tar_passwd)) {
            return true;
        } else {
            return false;
        }
    }

}
