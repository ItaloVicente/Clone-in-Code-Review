    @Test
    public void shouldPersistAsyncOnDefaultTimeout() {
        String key ="persist-to-master-json-doc";

        JsonDocument result = bucket().async().insert(JsonDocument.create(key, JsonObject.empty().put("k", "v")), PersistTo.MASTER).toBlocking().single();
        assertEquals(key, result.id());
        assertTrue(result.cas() != 0);

        result = bucket().async().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")), PersistTo.MASTER).toBlocking().single();
        assertEquals(key, result.id());
        assertTrue(result.cas() != 0);

        result = bucket().async().replace(JsonDocument.create(key, JsonObject.empty().put("k", "v")), PersistTo.MASTER).toBlocking().single();
        assertEquals(key, result.id());
        assertTrue(result.cas() != 0);

        result = bucket().async().remove(key, PersistTo.MASTER).toBlocking().single();
        assertEquals(key, result.id());
        assertTrue(result.cas() != 0);

        key = "persist-to-master-counter-doc";

        JsonLongDocument cresult = bucket().async().counter(key, 1, 1, PersistTo.MASTER).toBlocking().single();
        assertEquals(1, (long) cresult.content());
        assertTrue(cresult.cas() != 0);

        key = "persist-to-master-string-doc";

        bucket().insert(StringDocument.create(key, "init"));

        StringDocument aresult = bucket().async().append(StringDocument.create(key, "foo"), PersistTo.MASTER).toBlocking().single();
        assertTrue(aresult.cas() != 0);

        aresult = bucket().async().prepend(StringDocument.create(key, "foo"), PersistTo.MASTER).toBlocking().single();
        assertTrue(aresult.cas() != 0);
    }

