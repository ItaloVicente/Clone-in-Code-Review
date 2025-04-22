        assertNotEquals(doc1.cas(), doc2.cas());
        assertNotEquals(doc1.cas(), doc3.cas());
        assertNotEquals(doc2.cas(), doc3.cas());

        JsonLongDocument doc4 = bucket().get("incr-key", JsonLongDocument.class);
        assertEquals(20L, (long) doc4.content());
