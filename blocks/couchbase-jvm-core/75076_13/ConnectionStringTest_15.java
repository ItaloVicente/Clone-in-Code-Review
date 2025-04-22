    @Test
    public void shouldParseUsername() {
        ConnectionString parsed = ConnectionString.create("couchbase://user@localhost?foo=bar");
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals("user", parsed.username());
        assertEquals(InetSocketAddress.createUnresolved("localhost", 0),parsed.hosts().get(0));
        assertEquals(1, parsed.params().size());
        assertEquals("bar", parsed.params().get("foo"));

        parsed = ConnectionString.create("couchbase://user123@host1,host2?foo=bar&setting=true");
        assertEquals(ConnectionString.Scheme.COUCHBASE, parsed.scheme());
        assertEquals("user123", parsed.username());
        assertEquals(InetSocketAddress.createUnresolved("host1", 0),parsed.hosts().get(0));
        assertEquals(InetSocketAddress.createUnresolved("host2", 0),parsed.hosts().get(1));
        assertEquals(2, parsed.params().size());
        assertEquals("bar", parsed.params().get("foo"));
        assertEquals("true", parsed.params().get("setting"));
    }

