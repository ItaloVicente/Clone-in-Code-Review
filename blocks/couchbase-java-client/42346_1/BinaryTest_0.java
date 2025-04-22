
        JsonDocument removed = bucket().remove(doc);
        assertEquals(doc.id(), removed.id());
        assertNull(removed.content());
        assertEquals(0, removed.expiry());
        assertTrue(removed.cas() != 0);

        assertNull(bucket().get("upsert"));
