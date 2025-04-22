package com.couchbase.client.java.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    private final int maxCapacity;

    public LRUCache(final int maxCapacity) {
        super(maxCapacity + 1, 1.0f, true);
        this.maxCapacity = maxCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return super.size() > maxCapacity;
    }

}
