        setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
        cache.get("fs1");
        assertFalse(cache.memoizedSuppliers.containsKey("fs2"));

        setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
        cache.get("fs1");
        cache.get("fs2");
        assertFalse(cache.memoizedSuppliers.containsKey("fs3"));

        cache.get("fs1");
        cache.get("fs2");
        cache.get("fs3");
        setupCacheToTestOrder(config, "fs1", "fs2", "fs3");
        assertFalse(cache.memoizedSuppliers.containsKey("fs1"));
    }

    @Test
    public void removeEldestEntryTest() {
