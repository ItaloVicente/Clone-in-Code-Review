    @Test
    public void shouldGetAndTouch() throws Exception {
        String key = "get-and-touch";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v"), 3))
            .toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());

        Thread.sleep(2000);

        JsonDocument touched = bucket().getAndTouch(key, 3).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, touched.status());
        assertEquals("v", touched.content().getString("k"));

        Thread.sleep(2000);

        touched = bucket().getAndTouch(key, 3).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, touched.status());
        assertEquals("v", touched.content().getString("k"));
    }

    @Test
    public void shouldGetAndLock() throws Exception {
        String key = "get-and-lock";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")))
            .toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());

        JsonDocument locked = bucket().getAndLock(key, 2).toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, locked.status());
        assertEquals("v", locked.content().getString("k"));

        upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")))
            .toBlocking().single();
        assertEquals(ResponseStatus.EXISTS, upsert.status());

        Thread.sleep(3000);

        upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")))
            .toBlocking().single();
        assertEquals(ResponseStatus.SUCCESS, upsert.status());
    }

