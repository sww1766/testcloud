package com.service;

import java.util.HashMap;
import java.util.Map;

public class DataSource {
    private static Map<String, Map<String, String>> data = new HashMap<>();

    static {
        Map<String, String> data1 = new HashMap<>();
        data1.put("password", "api_123");
        data1.put("role", "admin");
        data1.put("permission", "api");

        data.put("api", data1);
    }

    public static Map<String, Map<String, String>> getData() {
        return data;
    }
}
