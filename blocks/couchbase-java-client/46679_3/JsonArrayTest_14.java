
    @Test
    public void shouldSupportSerialization() throws Exception {
        JsonArray original = JsonArray.from("a", "b", JsonObject.create().put("foo", "bar"));

        byte[] serialized = SerializationHelper.serializeToBytes(original);
        assertNotNull(serialized);

        JsonArray deserialized = SerializationHelper.deserializeFromBytes(serialized, JsonArray.class);
        assertEquals(original, deserialized);
    }
