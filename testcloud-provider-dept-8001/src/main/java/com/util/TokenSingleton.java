package com.util;

import java.util.HashMap;
import java.util.Map;

public class TokenSingleton {

    private Map<String,Object> map = new HashMap<>();

    private static TokenSingleton ourInstance = new TokenSingleton();

    public static TokenSingleton getInstance() {
        return ourInstance;
    }

    private TokenSingleton() {
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
