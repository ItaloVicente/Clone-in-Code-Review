
        JsonLongDocument doc4 = bucket().get("decr-key", JsonLongDocument.class);
        assertEquals(80L, (long) doc4.content());
    }

    @Test
    public void shouldIncrAndDecrAfterInitialUpsert() throws Exception {
        String id = "incrdecr-key";
        JsonLongDocument doc1 = bucket().upsert(JsonLongDocument.create(id, 30L));
        assertEquals(30L, (long) doc1.content());

        JsonLongDocument doc2 = bucket().get(id, JsonLongDocument.class);
        assertEquals(30L, (long) doc2.content());
        assertEquals(doc1.cas(), doc2.cas());

        JsonLongDocument doc3 = bucket().counter(id, 10);
        assertEquals(40L, (long) doc3.content());
        assertTrue(doc3.cas() != doc2.cas());

        JsonLongDocument doc4 = bucket().get(id, JsonLongDocument.class);
        assertEquals(40L, (long) doc4.content());
        assertEquals(doc4.cas(), doc3.cas());

        JsonLongDocument doc5 = bucket().counter(id, -20);
        assertEquals(20L, (long) doc5.content());
        assertTrue(doc5.cas() != doc4.cas());

        JsonLongDocument doc6 = bucket().get(id, JsonLongDocument.class);
        assertEquals(20L, (long) doc6.content());
        assertEquals(doc6.cas(), doc5.cas());
    }

    @Test
    public void shouldHaveCounterInitialZero() throws Exception {
        JsonLongDocument doc1 = bucket().counter("defincr-key", 10);
        assertEquals(0, (long) doc1.content());

        JsonLongDocument doc2 = bucket().get("defincr-key", JsonLongDocument.class);
        assertEquals(0, (long) doc2.content());

        JsonLongDocument doc3 = bucket().counter("defdecr-key", -10);
        assertEquals(0, (long) doc3.content());

        JsonLongDocument doc4 = bucket().get("defdecr-key", JsonLongDocument.class);
        assertEquals(0, (long) doc4.content());
