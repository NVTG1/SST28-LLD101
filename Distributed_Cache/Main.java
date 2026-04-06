package Distributed_Cache;

import java.util.*;

public class Main {
    public static void main(String[] args) {

        Database db = new Database();

        List<CacheNode> nodes = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            nodes.add(new CacheNode(2, new EvictionByLRU()));
        }

        DistributedCache cache =
                new DistributedCache(nodes, new ModuloDistribution(), db);

        cache.put("1", "Mathematics");
        cache.put("2", "Science");
        cache.put("3", "Commerce");
        cache.put("4", "Economics");

        System.out.println(cache.get("2"));
        System.out.println(cache.get("5")); 
    }
}