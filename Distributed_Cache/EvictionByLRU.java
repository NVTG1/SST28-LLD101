package Distributed_Cache;

import java.util.*;

public class EvictionByLRU implements EvictionStrategy{
    private LinkedHashSet<String> order = new LinkedHashSet<>();

    @Override
    public void onGet(String key) {
        order.remove(key);
        order.add(key);
    }

    @Override
    public void onPut(String key) {
        order.remove(key);
        order.add(key);
    }

    @Override
    public String evict() {
        String oldest = order.iterator().next();
        order.remove(oldest);
        return oldest;
    }
}