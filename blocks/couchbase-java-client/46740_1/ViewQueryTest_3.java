    @Test
    public void shouldSupportSerialization() throws Exception {
        ViewQuery query = ViewQuery.from("design", "view")
            .descending()
            .debug()
            .development()
            .group()
            .reduce(false)
            .startKey(JsonArray.from("foo", true));

        byte[] serialized = SerializationHelper.serializeToBytes(query);
        assertNotNull(serialized);

        ViewQuery deserialized = SerializationHelper.deserializeFromBytes(serialized, ViewQuery.class);
        assertEquals(query, deserialized);
    }

