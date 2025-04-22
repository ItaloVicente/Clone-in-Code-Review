    @Test
    public void shouldIncrementFromCounter() throws Exception {
        LongDocument doc1 = bucket().counter("incr-key", 10, 0, 0).toBlocking().single();
        assertEquals(0L, (long) doc1.content());

        LongDocument doc2 = bucket().counter("incr-key", 10, 0, 0).toBlocking().single();
        assertEquals(10L, (long) doc2.content());

        LongDocument doc3 = bucket().counter("incr-key", 10, 0, 0).toBlocking().single();
        assertEquals(20L, (long) doc3.content());

        assertTrue(doc1.cas() != doc2.cas());
        assertTrue(doc2.cas() != doc1.cas());
    }

    @Test
    public void shouldDecrementFromCounter() throws Exception {
        LongDocument doc1 = bucket().counter("decr-key", -10, 100, 0).toBlocking().single();
        assertEquals(100L, (long) doc1.content());

        LongDocument doc2 = bucket().counter("decr-key", -10, 0, 0).toBlocking().single();
        assertEquals(90L, (long) doc2.content());

        LongDocument doc3 = bucket().counter("decr-key", -10, 0, 0).toBlocking().single();
        assertEquals(80L, (long) doc3.content());

        assertTrue(doc1.cas() != doc2.cas());
        assertTrue(doc2.cas() != doc1.cas());
    }

