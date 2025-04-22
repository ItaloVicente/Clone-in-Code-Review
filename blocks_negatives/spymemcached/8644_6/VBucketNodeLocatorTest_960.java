        verify(node1, node2, node3);
    }
    public void testGetAlternative() {
        MemcachedNodeMockImpl node1 = new MemcachedNodeMockImpl();
        MemcachedNodeMockImpl node2 = new MemcachedNodeMockImpl();
        MemcachedNodeMockImpl node3 = new MemcachedNodeMockImpl();
        node1.setSocketAddress(new InetSocketAddress("127.0.0.1", 11211));
        node2.setSocketAddress(new InetSocketAddress("127.0.0.1", 11210));
        node3.setSocketAddress(new InetSocketAddress("127.0.0.1", 11212));
        ConfigFactory configFactory = new DefaultConfigFactory();
        Config config = configFactory.create(configInEnvelope);
        VBucketNodeLocator locator = new VBucketNodeLocator(Arrays.asList((MemcachedNode)node1, node2, node3), config);
        MemcachedNode primary = locator.getPrimary("k1");
        MemcachedNode alternative = locator.getAlternative("k1", Arrays.asList(primary));
        alternative.getSocketAddress();
    }
