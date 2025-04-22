
    @Test
    public void shouldSupportSerialization() throws Exception {
        JsonDocument original = JsonDocument.create("Ķěƴ", JsonObject.create().put("foo", "bar"));

        byte[] serialized = SerializationHelper.serializeToBytes(original);
        assertNotNull(serialized);

        JsonDocument deserialized = SerializationHelper.deserializeFromBytes(serialized, JsonDocument.class);
        assertEquals(original, deserialized);
    }
