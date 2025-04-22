
    @Test
    public void shouldInjectMutationTokenOnAtPlus() throws Exception {
        Bucket mockBucket = mock(Bucket.class);
        when(mockBucket.name()).thenReturn("travel-sample");

        JsonDocument doc = JsonDocument.create("doc", 0, JsonObject.empty(), 0,
            new MutationToken(1, 2345, 567, "bucket"));
        N1qlParams source = N1qlParams.build()
            .consistentWith(mockBucket, doc);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject bucket = JsonObject.create().put("1",
            JsonArray.create().add(567L).add("2345"));
        JsonObject expected = JsonObject.create()
            .put("scan_consistency", "at_plus")
            .put("scan_vectors", JsonObject.create().put("travel-sample", bucket));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldInjectMutationTokensOnAtPlus() throws Exception {
        Bucket mockBucket1 = mock(Bucket.class);
        when(mockBucket1.name()).thenReturn("bucket1");
        Bucket mockBucket2 = mock(Bucket.class);
        when(mockBucket2.name()).thenReturn("bucket2");

        JsonDocument doc1 = JsonDocument.create("doc1", 0, JsonObject.empty(), 0,
            new MutationToken(1, 2345, 567, "bucket1"));

        JsonDocument doc2 = JsonDocument.create("doc2", 0, JsonObject.empty(), 0,
            new MutationToken(5, 2, 3, "bucket1"));
        JsonDocument doc3 = JsonDocument.create("doc3", 0, JsonObject.empty(), 0,
            new MutationToken(8, 1, 4, "bucket2"));

        N1qlParams source = N1qlParams.build()
            .consistentWith(mockBucket1, doc1, doc2)
            .consistentWith(mockBucket2, doc3);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject bucket1 = JsonObject.create()
            .put("1", JsonArray.from(567L, "2345"))
            .put("5", JsonArray.from(3L, "2"));

        JsonObject bucket2 = JsonObject.create()
            .put("8", JsonArray.from(4L, "1"));

        JsonObject expected = JsonObject.create()
            .put("scan_consistency", "at_plus")
            .put("scan_vectors", JsonObject.create().put("bucket1", bucket1).put("bucket2", bucket2));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldOnlyUseHighestSeqnoToken() throws Exception {
        Bucket mockBucket = mock(Bucket.class);
        when(mockBucket.name()).thenReturn("travel-sample");

        JsonDocument doc1 = JsonDocument.create("doc", 0, JsonObject.empty(), 0,
            new MutationToken(1, 2345, 567, "bucket"));
        JsonDocument doc2 = JsonDocument.create("doc", 0, JsonObject.empty(), 0,
            new MutationToken(1, 2345, 600, "bucket"));

        N1qlParams source = N1qlParams.build()
            .consistentWith(mockBucket, doc1)
            .consistentWith(mockBucket, doc2);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject bucket = JsonObject.create().put("1",
            JsonArray.create().add(600L).add("2345"));
        JsonObject expected = JsonObject.create()
            .put("scan_consistency", "at_plus")
            .put("scan_vectors", JsonObject.create().put("travel-sample", bucket));
        assertEquals(expected, actual);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldFailIfConsistentWithAndConsistency() throws Exception {
        Bucket mockBucket = mock(Bucket.class);
        when(mockBucket.name()).thenReturn("bucket");

        N1qlParams source = N1qlParams.build()
            .consistency(ScanConsistency.REQUEST_PLUS)
            .consistentWith(mockBucket, JsonDocument.create("doc2", 0, JsonObject.empty(), 0,
                new MutationToken(1, 2345, 567, "bucket")));

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);
    }
