	private static final String configInEnvelope =
		"{" +
		"    \"authType\": \"sasl\", " +
		"    \"basicStats\": {" +
		"        \"diskUsed\": 0, " +
		"        \"hitRatio\": 0, " +
		"        \"itemCount\": 10001, " +
		"        \"memUsed\": 27007687, " +
		"        \"opsPerSec\": 0, " +
		"        \"quotaPercentUsed\": 10.061147436499596" +
		"    }, " +
		"    \"bucketType\": \"memcached\", " +
		"    \"flushCacheUri\": \"/pools/default/buckets/default/controller/doFlush\", " +
		"    \"name\": \"default\", " +
		"    \"nodeLocator\": \"ketama\", " +
		"    \"nodes\": [" +
		"        {" +
		"            \"clusterCompatibility\": 1, " +
		"            \"clusterMembership\": \"active\", " +
		"            \"hostname\": \"127.0.0.1:8091\", " +
		"            \"mcdMemoryAllocated\": 2985, " +
		"            \"mcdMemoryReserved\": 2985, " +
		"            \"memoryFree\": 285800000, " +
		"            \"memoryTotal\": 3913584000.0, " +
		"            \"os\": \"i386-apple-darwin9.8.0\", " +
		"            \"ports\": {" +
		"                \"direct\": 11210, " +
		"                \"proxy\": 11211" +
		"            }, " +
		"            \"replication\": 1.0, " +
		"            \"status\": \"unhealthy\", " +
		"            \"uptime\": \"4204\", " +
		"            \"version\": \"1.6.5\"" +
		"        }" +
		"    ], " +
		"    \"proxyPort\": 0, " +
		"    \"quota\": {" +
		"        \"ram\": 268435456, " +
		"        \"rawRAM\": 268435456" +
		"    }, " +
		"    \"replicaNumber\": 0, " +
		"    \"saslPassword\": \"\", " +
		"    \"stats\": {" +
		"        \"uri\": \"/pools/default/buckets/default/stats\"" +
		"    }, " +
		"    \"streamingUri\": \"/pools/default/bucketsStreaming/default\", " +
		"    \"uri\": \"/pools/default/buckets/default\"" +
		"}";


    public void testGetPrimary() {
/*
        MemcachedNode node1 = createMock(MemcachedNode.class);
        MemcachedNode node2 = createMock(MemcachedNode.class);
        MemcachedNode node3 = createMock(MemcachedNode.class);
        InetSocketAddress address1 = new InetSocketAddress("127.0.0.1", 11211);
        InetSocketAddress address2 = new InetSocketAddress("127.0.0.1", 11210);
        InetSocketAddress address3 = new InetSocketAddress("127.0.0.1", 11211);

        expect(node1.getSocketAddress()).andReturn(address1);
        expect(node2.getSocketAddress()).andReturn(address2);
        expect(node3.getSocketAddress()).andReturn(address3);

        replay(node1, node2, node3);

        ConfigFactory factory = new DefaultConfigFactory();
        Config config = factory.createConfigFromString(configInEnvelope);
