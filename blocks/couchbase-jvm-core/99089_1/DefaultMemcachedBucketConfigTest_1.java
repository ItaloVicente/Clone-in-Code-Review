
    @Test
    public void shouldOnlyTakeNodesArrayIntoAccount() {
        MemcachedBucketConfig config = readConfig("memcached_during_rebalance.json");

        List<String> mustContain = Arrays.asList(
            "10.0.0.1",
            "10.0.0.2",
            "10.0.0.3"
        );
        List<String> mustNotContain = Collections.singletonList("10.0.0.4");

        Collection<NodeInfo> actualRingNodes = config.ketamaNodes().values();
        for (NodeInfo nodeInfo : actualRingNodes) {
            String actual = nodeInfo.hostname().nameOrAddress();
            assertTrue(mustContain.contains(actual));
            assertFalse(mustNotContain.contains(actual));
        }
    }

    private static MemcachedBucketConfig readConfig(final String path) {
        return (MemcachedBucketConfig) BucketConfigParser.parse(
            Resources.read(path, DefaultMemcachedBucketConfigTest.class),
            ENV,
            null
        );
    }

