package Distributed_Cache;

import java.util.*;

public class DistributedCache {
    private List<CacheNode> nodes;
    private DistributionStrategy strategy;
    private Database database;

    public DistributedCache(List<CacheNode> nodes,
                            DistributionStrategy strategy,
                            Database database) {
        this.nodes = nodes;
        this.strategy = strategy;
        this.database = database;
    }

    public String get(String key) {
        CacheNode node = strategy.getNode(key, nodes);

        String value = node.get(key);
        if (value != null) return value;

        value = database.get(key);
        if (value != null) {
            node.put(key, value);
        }

        return value;
    }

    public void put(String key, String value) {
        CacheNode node = strategy.getNode(key, nodes);

        node.put(key, value);

        database.put(key, value);
    }
}