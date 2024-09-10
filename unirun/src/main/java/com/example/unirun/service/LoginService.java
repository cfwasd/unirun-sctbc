package com.example.unirun.service;

import cn.hutool.http.HttpRequest;
import com.example.unirun.config.AppConfig;
import com.example.unirun.utlis.Md5Utils;
import com.example.unirun.utlis.SignUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author wzh
 */
@Service
public class LoginService {
    String loginUrl = AppConfig.HOST+"/auth/login/password";

    public static LinkedHashMap<String,String> loginData(String phone, String password){
        LinkedHashMap<String,String> loginData = new LinkedHashMap<>();
        loginData.put("appVersion", AppConfig.APP_VERSION);
        loginData.put("brand", AppConfig.BRAND);
        loginData.put("deviceToken",AppConfig.DEVICE_TOKEN_DEFAULT);
        loginData.put("deviceType",AppConfig.DEVICE_TYPE);
        loginData.put("mobileType",AppConfig.MOBILE_TYPE);
        loginData.put("password", Md5Utils.getMd5(password));
        loginData.put("sysVersion",AppConfig.SYS_VERSION);
        loginData.put("userPhone",phone);
        return loginData;
    }

    public static LinkedHashMap<String,String> loginHeaders(String sign) throws Exception{

        LinkedHashMap<String,String> loginHeaders = new LinkedHashMap<>();
        loginHeaders.put("appKey",AppConfig.APP_KEY);
        loginHeaders.put("sign",sign);
        loginHeaders.put("Content-Type",AppConfig.CONTENT_TYPE);
        loginHeaders.put("User-Agent",AppConfig.USER_AGENT);
        return loginHeaders;
    }
    public Map login(String phone, String password){
        ObjectMapper mapper = new ObjectMapper();
        try {
            Map body = loginData(phone, password);

            String sign = SignUtil.getSign(null,body);

            Map<String,String> headers = loginHeaders(sign);

            String result = HttpRequest.post(loginUrl).addHeaders(headers).body(mapper.writeValueAsString(body)).execute().body();
            Map map = mapper.readValue(result,Map.class);
            return (Map) map.get("response");
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
