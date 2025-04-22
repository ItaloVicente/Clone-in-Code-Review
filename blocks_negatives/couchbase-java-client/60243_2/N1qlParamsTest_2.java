
    @Test(expected = IllegalArgumentException.class)
    public void shouldFailIfTokenDoesNotMatchBucket() throws Exception {
        Bucket mockBucket = mock(Bucket.class);
        when(mockBucket.name()).thenReturn("travel-sample");

        JsonDocument doc = JsonDocument.create("doc", 0, JsonObject.empty(), 0,
            new MutationToken(1, 2345, 567, "bucket"));
        N1qlParams source = N1qlParams.build()
            .consistentWith(mockBucket, doc);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);
    }
