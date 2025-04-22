    @Test
    public void shouldLoadConfigWithIPv6() throws Exception {
        String raw = Resources.read("memcached_with_ipv6.json", getClass());
        InjectableValues inject = new InjectableValues.Std()
                .addValue("env", environment);
        MemcachedBucketConfig config = JSON_MAPPER.readerFor(MemcachedBucketConfig.class).with(inject).readValue(raw);

        assertEquals(2, config.nodes().size());
        for (Map.Entry<Long, NodeInfo> node : config.ketamaNodes().entrySet()) {
            String hostname = node.getValue().hostname().address();
            assertTrue(hostname.equals("fd63:6f75:6368:2068:1471:75ff:fe25:a8be")
                || hostname.equals("fd63:6f75:6368:2068:c490:b5ff:fe86:9cf7"));
            assertTrue(node.getValue().services().containsKey(ServiceType.BINARY));
        }
    }

