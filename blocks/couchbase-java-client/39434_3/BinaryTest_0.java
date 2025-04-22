    @Test
    public void shouldUnlock() throws Exception {
        String key = "unlock";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")))
            .toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());

        JsonDocument locked = bucket().getAndLock(key, 15).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, locked.status());
        assertEquals("v", locked.content().getString("k"));

        upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")))
            .toBlocking().single();
        assertEquals(ResponseStatus.EXISTS, upsert.status());

        boolean unlocked = bucket().unlock(key, locked.cas()).toBlocking().single();
        assertTrue(unlocked);

        upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")))
            .toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());
    }

    @Test
    public void shouldTouch() throws Exception {
        String key = "touch";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v"), 3))
            .toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());

        Thread.sleep(2000);

        Boolean touched = bucket().touch(key, 3).toBlocking().single();
        assertTrue(touched);

        Thread.sleep(2000);

        JsonDocument loaded = bucket().get(key).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, loaded.status());
        assertEquals("v", loaded.content().getString("k"));
    }

