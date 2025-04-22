
    @Test
    public void shouldPersistAndLoadWithNullValues() {
        final String key = "testPersistAndLoadWithNullValues";

        JsonObject obj = JsonObject.create()
            .put("name", "test")
            .put("age", 21)
            .put("role", (Object) null)
            .put("hobby", JsonValue.NULL)
            .putNull("superior");

        JsonDocument doc = JsonDocument.create(key, obj);
        bucket().upsert(doc);

        JsonDocument stored = bucket().get(key);
        assertThat(stored, notNullValue());
        assertThat(stored.content(), notNullValue());
        assertThat(stored.content().get("name"), instanceOf(String.class));
        assertThat(stored.content().get("age"), instanceOf(Number.class));
        assertThat(stored.content().containsKey("role"), is(true));
        assertThat(stored.content().containsKey("superior"), is(true));
        assertThat(stored.content().containsKey("hobby"), is(true));
        assertThat(stored.content().get("hobby"), nullValue());
        assertThat(stored.content().get("role"), nullValue());
        assertThat(stored.content().get("superior"), nullValue());
    }
