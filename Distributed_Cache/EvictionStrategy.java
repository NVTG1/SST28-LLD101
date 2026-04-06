package Distributed_Cache;

public interface EvictionStrategy{
     void onGet(String key);
    void onPut(String key);
    String evict();
}