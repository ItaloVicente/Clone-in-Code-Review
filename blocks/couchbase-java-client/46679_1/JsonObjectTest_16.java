
    @Test
    public void shouldSupportSerialization() throws Exception {
        JsonObject original = JsonObject
           .create()
           .put("a", "b")
           .put("list", JsonArray.from(1, 2, 3));

        byte[] serialized = SerializationHelper.serializeToBytes(original);
        assertNotNull(serialized);

        JsonObject deserialized = SerializationHelper.deserializeFromBytes(serialized, JsonObject.class);
        assertEquals(original, deserialized);
    }

