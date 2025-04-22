    @Test
    public void shouldInjectEmptyConsistencyLevel() {
        SearchQuery p = new SearchQuery(null, null)
                .scanConsistency(ScanConsistency.NOT_BOUNDED);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
                .put("ctl",
                        JsonObject.create().put("consistency",
                                JsonObject.create()
                                .put("level", "")));
        assertEquals(expected, result);
    }

    @Test
    public void shouldInjectAtPlusConsistencyLevelWithVectors() {
        MutationToken token1 = new MutationToken(1, 1234, 1000, "bucket1");
        MutationToken token2 = new MutationToken(2, 1235, 2000, "bucket1");
        JsonDocument doc1 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token1);
        JsonDocument doc2 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token2);

        SearchQuery p = new SearchQuery("foo", null)
                .consistentWith(doc1, doc2);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expectedVector = JsonObject.create().put("foo", JsonObject.create()
            .put("1/1234", 1000L).put("2/1235", 2000L));
        JsonObject expected = JsonObject.create()
                .put("ctl",
                        JsonObject.create().put("consistency",
                                JsonObject.create()
                                .put("level", "at_plus")
                                .put("vectors", expectedVector)));
        assertEquals(expected, result);
    }

    @Test
    public void shouldReplaceConsistencyWithNotBounded() {
        MutationToken token1 = new MutationToken(1, 1234, 1000, "bucket1");
        MutationToken token2 = new MutationToken(2, 1235, 2000, "bucket1");
        JsonDocument doc1 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token1);
        JsonDocument doc2 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token2);

        SearchQuery p = new SearchQuery(null, null)
                .consistentWith(doc1, doc2)
                .scanConsistency(ScanConsistency.NOT_BOUNDED);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
                .put("ctl",
                        JsonObject.create().put("consistency",
                                JsonObject.create()
                                .put("level", "")));
        assertEquals(expected, result);
    }

    @Test
    public void shouldReplaceConsistencyWithAtPlus() {
        MutationToken token1 = new MutationToken(1, 1234, 1000, "bucket1");
        MutationToken token2 = new MutationToken(2, 1235, 2000, "bucket1");
        JsonDocument doc1 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token1);
        JsonDocument doc2 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token2);

        SearchQuery p = new SearchQuery("foo", null)
                .scanConsistency(ScanConsistency.NOT_BOUNDED)
                .consistentWith(doc1, doc2);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expectedVector = JsonObject.create().put("foo", JsonObject.create()
            .put("1/1234", 1000L).put("2/1235", 2000L));
        JsonObject expected = JsonObject.create()
                .put("ctl",
                        JsonObject.create().put("consistency",
                                JsonObject.create()
                                .put("level", "at_plus")
                                .put("vectors", expectedVector)));
        assertEquals(expected, result);
    }

    @Test
    public void shouldReplaceOnConsistentWithOnSameVbucketLargerSeqno() {
        MutationToken token1 = new MutationToken(1, 1234, 1000, "bucket1");
        MutationToken token2 = new MutationToken(1, 123, 1001, "bucket1");
        JsonDocument doc1 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token1);
        JsonDocument doc2 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token2);

        SearchQuery p = new SearchQuery("foo", null)
                .scanConsistency(ScanConsistency.NOT_BOUNDED)
                .consistentWith(doc1, doc2);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expectedVector = JsonObject.create().put("foo", JsonObject.create().put("1/123", 1001L));
        JsonObject expected = JsonObject.create()
                .put("ctl",
                        JsonObject.create().put("consistency",
                                JsonObject.create()
                                .put("level", "at_plus")
                                .put("vectors", expectedVector)));
        assertEquals(expected, result);
    }

    @Test
    public void shouldNotReplaceOnConsistentWithOnSameVbucketLesserSeqno() {
        MutationToken token1 = new MutationToken(1, 1234, 1000, "bucket1");
        MutationToken token2 = new MutationToken(1, 1235, 8, "bucket1");
        JsonDocument doc1 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token1);
        JsonDocument doc2 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token2);

        SearchQuery p = new SearchQuery("foo", null)
                .scanConsistency(ScanConsistency.NOT_BOUNDED)
                .consistentWith(doc1, doc2);
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expectedVector = JsonObject.create().put("foo", JsonObject.create().put("1/1234", 1000L));
        JsonObject expected = JsonObject.create()
                .put("ctl",
                        JsonObject.create().put("consistency",
                                JsonObject.create()
                                .put("level", "at_plus")
                                .put("vectors", expectedVector)));
        assertEquals(expected, result);
    }

