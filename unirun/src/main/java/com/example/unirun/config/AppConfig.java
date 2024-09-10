package com.example.unirun.config;

/**
 * @author wzh
 * 配置类
 */
public final class AppConfig {

    private AppConfig() {
        // 防止实例化
    }

    public static final String APP_KEY = "389885588s0648fa";
    public static final String APP_SECRET = "56E39A1658455588885690425C0FD16055A21676";
    public static final String HOST = "https://run-lb.tanmasports.com/v1";

    // Query related configurations
    public static final String APP_VERSION = "1.8.3";
    public static final String BRAND = "iPhone";
    public static final String DEVICE_TOKEN_DEFAULT = ""; // Assuming a default empty value
    public static final String DEVICE_TYPE = "2";
    public static final String MOBILE_TYPE = "iPhone 15 Pro Max";
    public static final String SYS_VERSION = "10";
    public static final String TOKEN = "1243489ade4c457702e7c9c7fe2698a0";

    // Headers
    public static final String CONTENT_TYPE = "application/json; charset=UTF-8";
    public static final String USER_AGENT = "okhttp/3.12.0";
}

