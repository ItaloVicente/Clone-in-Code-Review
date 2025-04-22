        try {
            bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v"))).toBlocking().single();
            assertTrue(false);
        } catch(CASMismatchException ex) {
            assertTrue(true);
        }
