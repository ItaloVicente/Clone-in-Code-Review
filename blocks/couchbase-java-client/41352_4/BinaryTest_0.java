    @Test
    public void shouldUpsertAndGet() {
        JsonObject content = JsonObject.empty().put("hello", "world");
        final JsonDocument doc = JsonDocument.create("upsert", content);

        bucket().upsert(doc);
        JsonDocument response = bucket().get("upsert");
        assertEquals(content.getString("hello"), response.content().getString("hello"));
    }
