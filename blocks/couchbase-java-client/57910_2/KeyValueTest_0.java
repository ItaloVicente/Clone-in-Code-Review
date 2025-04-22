    @Test
    public void shouldStoreAndGetKeyWithUtf16Char() {
        String key = "utf16\uD834\uDD1E"; //a nice little musical key
        JsonDocument toStore = JsonDocument.create(key, JsonObject.create().put("UTF8", true).put("hasUTF16", true));

        JsonDocument inserted = bucket().upsert(toStore);
        JsonDocument retrieved = bucket().get(key);

        assertNotNull(inserted);
        assertNotNull(retrieved);
        assertEquals(key, inserted.id());
        assertEquals(key, retrieved.id());
        assertEquals(inserted.content(), retrieved.content());
    }

