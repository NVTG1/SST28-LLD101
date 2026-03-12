package com.example.metrics;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * INTENTION: Global metrics registry (should be a Singleton).
 *
 * CURRENT STATE (BROKEN ON PURPOSE):
 * - Constructor is public -> anyone can create instances.
 * - getInstance() is lazy but NOT thread-safe -> can create multiple instances.
 * - Reflection can call the constructor to create more instances.
 * - Serialization can create a new instance when deserialized.
 *
 * TODO (student):
 *  1) Make it a proper lazy, thread-safe singleton (private ctor)
 *  2) Block reflection-based multiple construction
 *  3) Preserve singleton on serialization (readResolve)
 */

// Because class is implementing Serializable, deserialization creates a new object
public class MetricsRegistry implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Made volatile for system write to RAM everytime
    private static volatile MetricsRegistry INSTANCE; // BROKEN: not volatile, not thread-safe
    private final Map<String, Long> counters = new HashMap<>();

    // BROKEN: should be private and should prevent second construction
    // Anyone can create an instance
    // Made the constructor from public -> private
    private MetricsRegistry() {
        // intentionally empty

        // To block the reflection attack
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() instead");
        }
    }

    // BROKEN: racy lazy init; two threads can create two instances
    // Not thread-safe
    // Lazy Loading to make thread-safe
    public static MetricsRegistry getInstance() {
        if (INSTANCE == null) {
            synchronized (MetricsRegistry.class){
                if(INSTANCE == null){
                    INSTANCE = new MetricsRegistry();
                }
            }
        }
        return INSTANCE;
    }

    public synchronized void setCount(String key, long value) {
        counters.put(key, value);
    }

    public synchronized void increment(String key) {
        counters.put(key, getCount(key) + 1);
    }

    public synchronized long getCount(String key) {
        return counters.getOrDefault(key, 0L);
    }

    // To preserve singleton during deserialization so that no new object is created
    public synchronized Map<String, Long> getAll() {
        return Collections.unmodifiableMap(new HashMap<>(counters));
    }

    // TODO: implement readResolve() to preserve singleton on deserialization
}