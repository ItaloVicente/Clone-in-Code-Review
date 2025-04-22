        assertEquals(2, parsed.allHosts().size());
        assertEquals("foo", parsed.allHosts().get(0).getHostName());
        assertEquals(1234, parsed.allHosts().get(0).getPort());
        assertEquals("bar", parsed.allHosts().get(1).getHostName());
        assertEquals(5678, parsed.allHosts().get(1).getPort());
        assertEquals(null, parsed.username());
