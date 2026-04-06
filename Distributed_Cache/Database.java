package Distributed_Cache;

import java.util.*;

public class Database {
    private Map<String, String> db = new HashMap<>();

    public String get(String key) {
        System.out.println("Fetching from DB: " + key);
        return db.get(key);
    }

    public void put(String key, String value) {
        db.put(key, value);
    }
}