
    @Test
    public void shouldInjectMutationTokenOnAtPlus() throws Exception {
        MutationToken token = new MutationToken(1, 2345, 567, "bucket");
        N1qlParams source = N1qlParams.build()
            .consistentWith(token);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject bucket = JsonObject.create().put("1",
            JsonObject.create().put("value", 567L).put("guard", "2345"));
        JsonObject expected = JsonObject.create()
            .put("scan_consistency", "at_plus")
            .put("scan_vector", JsonObject.create().put("bucket", bucket));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldInjectMutationTokensOnAtPlus() throws Exception {
        MutationToken token1 = new MutationToken(1, 2345, 567, "bucket1");
        MutationToken token2 = new MutationToken(5, 2, 3, "bucket1");
        MutationToken token3 = new MutationToken(8, 1, 4, "bucket2");
        N1qlParams source = N1qlParams.build()
            .consistentWith(token1, token2, token3);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject bucket1 = JsonObject.create()
            .put("1", JsonObject.create().put("value", 567L).put("guard", "2345"))
            .put("5", JsonObject.create().put("value", 3L).put("guard", "2"));

        JsonObject bucket2 = JsonObject.create()
            .put("8", JsonObject.create().put("value", 4L).put("guard", "1"));

        JsonObject expected = JsonObject.create()
            .put("scan_consistency", "at_plus")
            .put("scan_vector", JsonObject.create().put("bucket1", bucket1).put("bucket2", bucket2));
        assertEquals(expected, actual);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldFailIfConsistentWithAndConsistency() throws Exception {
        N1qlParams source = N1qlParams.build()
            .consistency(ScanConsistency.REQUEST_PLUS)
            .consistentWith(new MutationToken(1, 2345, 567, "bucket"));

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);
    }
