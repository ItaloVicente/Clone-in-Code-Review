        assertEquals(3, parsed.hosts().size());
        assertEquals("foo", parsed.hosts().get(0).hostname());
        assertEquals(0, parsed.hosts().get(0).port());
        assertEquals("bar", parsed.hosts().get(1).hostname());
        assertEquals(5678, parsed.hosts().get(1).port());
        assertEquals("baz", parsed.hosts().get(2).hostname());
        assertEquals(0, parsed.hosts().get(2).port());
        assertNull(parsed.username());
