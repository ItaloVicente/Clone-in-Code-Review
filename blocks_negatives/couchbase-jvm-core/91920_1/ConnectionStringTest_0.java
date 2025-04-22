        assertEquals(3, parsed.hosts().size());
        assertEquals("foo", parsed.hosts().get(0).getHostName());
        assertEquals(0, parsed.hosts().get(0).getPort());
        assertEquals("bar", parsed.hosts().get(1).getHostName());
        assertEquals(5678, parsed.hosts().get(1).getPort());
        assertEquals("baz", parsed.hosts().get(2).getHostName());
        assertEquals(0, parsed.hosts().get(2).getPort());
