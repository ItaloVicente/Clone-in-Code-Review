    @Test
    public void shouldStoreAndLoadRawJsonDocument() {
        String id = "jsonRaw";
        String content = "{\"foo\": 1234}";

        bucket().insert(RawJsonDocument.create(id, content));

        RawJsonDocument foundRaw = bucket().get(id, RawJsonDocument.class);
        assertEquals(content, foundRaw.content());

        JsonDocument foundParsed = bucket().get(id);
        assertEquals(1234, (int) foundParsed.content().getInt("foo"));
    }

