package Distributed_Cache;

import java.util.*;

public class CacheNode {
    private int capacity;
    private Map<String, String> map;
    private EvictionStrategy evictionStrategy;

    public CacheNode(int capacity, EvictionStrategy evictionStrategy) {
        this.capacity = capacity;
        this.map = new HashMap<>();
        this.evictionStrategy = evictionStrategy;
    }

    public String get(String key) {
        if (!map.containsKey(key)) return null;

        evictionStrategy.onGet(key);
        return map.get(key);
    }

    public void put(String key, String value) {

        if (map.containsKey(key)) {
            map.put(key, value);
            evictionStrategy.onPut(key);
            return;
        }

        if (map.size() >= capacity) {
            String evictKey = evictionStrategy.evict();
            map.remove(evictKey);
        }

        map.put(key, value);
        evictionStrategy.onPut(key);
    }
}