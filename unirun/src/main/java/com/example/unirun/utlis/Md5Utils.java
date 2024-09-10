package com.example.unirun.utlis;

import cn.hutool.crypto.SecureUtil;


/**
 * @author wzh
 * Md5加密
 */
public class Md5Utils {
    public static String getMd5(String str) {
        return SecureUtil.md5(str);
    }
}
