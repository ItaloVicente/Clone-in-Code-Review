    @Test
    public void shouldAcceptSingleIPv6WithoutPort() {
        ConnectionString parsed = ConnectionString.create("couchbase://[::1]");
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals(1, parsed.hosts().size());
        assertEquals(new InetSocketAddress("::1", 0), parsed.hosts().get(0));
        assertTrue(parsed.params().isEmpty());

        parsed = ConnectionString.create("couchbase://[::1/128]");
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals(1, parsed.hosts().size());
        assertEquals(new InetSocketAddress("::1/128", 0), parsed.hosts().get(0));
        assertTrue(parsed.params().isEmpty());
    }

    @Test
    public void shouldAcceptMultipleIPv6WithoutPort() {
        ConnectionString parsed = ConnectionString.create("couchbase://[::1], [::1]");
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals(2, parsed.hosts().size());
        assertEquals(new InetSocketAddress("::1", 0), parsed.hosts().get(0));
        assertEquals(new InetSocketAddress("::1", 0), parsed.hosts().get(1));
        assertTrue(parsed.params().isEmpty());

        parsed = ConnectionString.create("couchbase://[::1/128], [::1/128],[::1/128]");
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals(3, parsed.hosts().size());
        assertEquals(new InetSocketAddress("::1/128", 0), parsed.hosts().get(0));
        assertEquals(new InetSocketAddress("::1/128", 0), parsed.hosts().get(1));
        assertEquals(new InetSocketAddress("::1/128", 0), parsed.hosts().get(2));
        assertTrue(parsed.params().isEmpty());
    }

    @Test
    public void shouldAcceptSingleIPv6WithPort() {
        ConnectionString parsed = ConnectionString.create("couchbases://[::1]:8091, [::1]:11210");
        assertEquals(ConnectionString.Scheme.COUCHBASES, parsed.scheme());
        assertEquals(2, parsed.hosts().size());
        assertEquals(new InetSocketAddress("::1", 8091), parsed.hosts().get(0));
        assertEquals(new InetSocketAddress("::1", 11210), parsed.hosts().get(1));
        assertTrue(parsed.params().isEmpty());

        parsed = ConnectionString.create("couchbase://[::1/128]:1234, [::1/128]:11210,[::1/128]:1");
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals(3, parsed.hosts().size());
        assertEquals(new InetSocketAddress("::1/128", 1234), parsed.hosts().get(0));
        assertEquals(new InetSocketAddress("::1/128", 11210), parsed.hosts().get(1));
        assertEquals(new InetSocketAddress("::1/128", 1), parsed.hosts().get(2));
        assertTrue(parsed.params().isEmpty());
    }

