    @Test
    public void shouldSupportSerialization() throws Exception {
        SpatialViewQuery query = SpatialViewQuery.from("design", "view")
            .debug()
            .development();

        byte[] serialized = SerializationHelper.serializeToBytes(query);
        assertNotNull(serialized);

        SpatialViewQuery deserialized = SerializationHelper.deserializeFromBytes(serialized, SpatialViewQuery.class);
        assertEquals(query, deserialized);
    }
