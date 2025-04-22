        try {
            bucket().upsert(JsonDocument.create(id, JsonObject.empty().put("k", "v")))
                .toBlocking().single();
            assertTrue(false);
        } catch(CASMismatchException ex) {
            assertTrue(true);
        }
