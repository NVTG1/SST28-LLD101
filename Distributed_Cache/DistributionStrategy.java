package Distributed_Cache;

import java.util.*;

public interface DistributionStrategy {
    CacheNode getNode(String key, List<CacheNode> nodes);
}