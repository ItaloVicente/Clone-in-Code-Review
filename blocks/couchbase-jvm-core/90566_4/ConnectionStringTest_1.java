        assertEquals(3, parsed.allHosts().size());
        assertEquals("foo", parsed.allHosts().get(0).getHostName());
        assertEquals(0, parsed.allHosts().get(0).getPort());
        assertEquals("bar", parsed.allHosts().get(1).getHostName());
        assertEquals(5678, parsed.allHosts().get(1).getPort());
        assertEquals("baz", parsed.allHosts().get(2).getHostName());
        assertEquals(0, parsed.allHosts().get(2).getPort());
