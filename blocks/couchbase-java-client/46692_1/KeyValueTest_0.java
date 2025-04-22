    @Test(expected = TemporaryLockFailureException.class)
    public void shouldFailDoubleLocking() throws Exception {
        String key = "doubleLockFail";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")));
        assertNotNull(upsert);
        assertEquals(key, upsert.id());

        JsonDocument locked = bucket().getAndLock(key, 15);
        assertEquals("v", locked.content().getString("k"));

        bucket().getAndLock(key, 5);
    }

