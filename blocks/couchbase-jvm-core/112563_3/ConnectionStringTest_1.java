        assertEquals(2, parsed.hosts().size());
        assertEquals("foo", parsed.hosts().get(0).hostname());
        assertEquals(1234, parsed.hosts().get(0).port());
        assertEquals("bar", parsed.hosts().get(1).hostname());
        assertEquals(5678, parsed.hosts().get(1).port());
        assertNull(parsed.username());
