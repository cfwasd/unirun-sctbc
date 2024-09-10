package com.example.unirun.utlis;

import com.example.unirun.config.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * @author wzh
 * sign生成工具
 */
public class SignUtil {

    private static final String APPKEY = AppConfig.APP_KEY; // Replace with your actual app key
    private static final String APPSECRET = AppConfig.APP_SECRET; // Replace with your actual app secret

    public static String getSign(Map<String, String> query, Map<String, String> body) throws NoSuchAlgorithmException, JsonProcessingException {
        StringBuilder signStrBuilder = new StringBuilder();
        Gson objectMapper = new Gson();

        // Sort and append query parameters
        if (query != null) {
            List<Map.Entry<String, String>> sortedQueryList = new ArrayList<>(query.entrySet());
            sortedQueryList.sort(Map.Entry.comparingByKey());
            for (Map.Entry<String, String> entry : sortedQueryList) {
                signStrBuilder.append(entry.getKey()).append(entry.getValue());
            }
        }

        // Append app key and secret
        signStrBuilder.append(APPKEY).append(APPSECRET);

        // Append body if not null
        if (body != null) {
            signStrBuilder.append(objectMapper.toJson(body)); // Requires Gson library for JSON conversion
        }

        boolean replaced = false;

        // Remove specific characters
        String[] charsToRemove = {" ", "~", "!", "(", ")", "'"};
        String str = signStrBuilder.toString();
        for (String ch : charsToRemove) {
            str = str.replace(ch, "");
            replaced = true;
        }
        signStrBuilder = new StringBuilder(str);

        // Encode if any replacements were made
        String signStr = signStrBuilder.toString();
//        replaced = Arrays.stream(charsToRemove).anyMatch(signStr::contains);
        if (replaced) {
            signStr = URLEncoder.encode(str, StandardCharsets.UTF_8);
        }

        String md5Hash = generateMD5(signStr);


        if (replaced) {
            md5Hash += "encodeutf8";
        }
        signStrBuilder = new StringBuilder(md5Hash);


        return signStrBuilder.toString();
    }


    public static String generateMD5(String signStr) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(signStr.getBytes("UTF-8"));
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString().toUpperCase();
        } catch (NoSuchAlgorithmException | java.io.UnsupportedEncodingException e) {
            throw new RuntimeException("Failed to generate MD5", e);
        }
    }


}
