        assertEquals(1, parsed.allHosts().size());
        assertFalse(parsed.allHosts().get(0).isUnresolved());
        assertEquals("localhost", parsed.allHosts().get(0).getHostName());
        assertEquals("127.0.0.1", parsed.allHosts().get(0).getAddress().getHostAddress());
        assertEquals(0, parsed.allHosts().get(0).getPort());
        assertEquals(null, parsed.username());
