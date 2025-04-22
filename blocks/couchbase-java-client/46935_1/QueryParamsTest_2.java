
    @Test
    public void shouldSupportSerialization() throws Exception {
        QueryParams source = QueryParams
            .build()
            .serverSideTimeout(1, TimeUnit.DAYS)
            .consistency(ScanConsistency.NOT_BOUNDED);

        byte[] serialized = SerializationHelper.serializeToBytes(source);
        assertNotNull(serialized);

        QueryParams deserialized = SerializationHelper.deserializeFromBytes(serialized, QueryParams.class);
        assertEquals(source, deserialized);
    }
