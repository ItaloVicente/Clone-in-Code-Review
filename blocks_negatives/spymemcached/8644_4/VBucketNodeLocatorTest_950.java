    public void testGetPrimary() {
        MemcachedNode node1 = createMock(MemcachedNode.class);
        MemcachedNode node2 = createMock(MemcachedNode.class);
        MemcachedNode node3 = createMock(MemcachedNode.class);
        InetSocketAddress address1 = new InetSocketAddress("127.0.0.1", 11211);
        InetSocketAddress address2 = new InetSocketAddress("127.0.0.1", 11210);
        InetSocketAddress address3 = new InetSocketAddress("127.0.0.1", 11212);
