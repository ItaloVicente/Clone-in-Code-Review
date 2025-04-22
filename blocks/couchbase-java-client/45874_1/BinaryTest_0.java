    @Test(expected = CASMismatchException.class)
    public void shouldFailUnlockWithInvalidCAS() throws Exception {
        String key = "unlockfail";

        JsonDocument upsert = bucket().upsert(JsonDocument.create(key, JsonObject.empty().put("k", "v")));
        assertNotNull(upsert);
        assertEquals(key, upsert.id());

        JsonDocument locked = bucket().getAndLock(key, 15);
        assertEquals("v", locked.content().getString("k"));

        bucket().unlock(key, locked.cas()+1);
    }

    @Test(expected = DocumentDoesNotExistException.class)
    public void shouldFailUnlockWhenDocDoesNotExist() throws Exception {
        bucket().unlock("thisDocDoesNotExist", 1234);
    }

