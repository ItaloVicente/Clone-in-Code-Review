        assertEquals(3, parsed.unresolvedHosts().size());
        assertEquals("foo", parsed.unresolvedHosts().get(0).getHostName());
        assertEquals(0, parsed.unresolvedHosts().get(0).getPort());
        assertEquals("bar", parsed.unresolvedHosts().get(1).getHostName());
        assertEquals(5678, parsed.unresolvedHosts().get(1).getPort());
        assertEquals("baz", parsed.unresolvedHosts().get(2).getHostName());
        assertEquals(0, parsed.unresolvedHosts().get(2).getPort());
