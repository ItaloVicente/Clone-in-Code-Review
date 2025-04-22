
    @Test
    public void shouldInjectMutationTokenOnAtPlus() throws Exception {
        MutationToken token = new MutationToken(1, 2345, 567);
        N1qlParams source = N1qlParams.build()
            .consistency(ScanConsistency.AT_PLUS)
            .scanTokens(token);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject expected = JsonObject.create()
            .put("scan_consistency", "at_plus")
            .put("scan_vector", JsonObject.create().put("1",
                JsonObject.create().put("value", 567L).put("guard", "2345")));
        assertEquals(expected, actual);
    }

    @Test
    public void shouldInjectMutationTokensOnAtPlus() throws Exception {
        MutationToken token1 = new MutationToken(1, 2345, 567);
        MutationToken token2 = new MutationToken(5, 2, 3);
        N1qlParams source = N1qlParams.build()
            .consistency(ScanConsistency.AT_PLUS)
            .scanTokens(token1, token2);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject expected = JsonObject.create()
            .put("scan_consistency", "at_plus")
            .put("scan_vector", JsonObject.create()
                .put("1", JsonObject.create().put("value", 567L).put("guard", "2345"))
                .put("5", JsonObject.create().put("value", 3L).put("guard", "2"))
            );
        assertEquals(expected, actual);
    }

    @Test
    public void shouldIgnoreTokenIfNotAtPlus() throws Exception {
        MutationToken token = new MutationToken(1, 2345, 567);
        N1qlParams source = N1qlParams.build()
            .consistency(ScanConsistency.REQUEST_PLUS)
            .scanTokens(token);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        JsonObject expected = JsonObject.create().put("scan_consistency", "request_plus");
        assertEquals(expected, actual);
    }

    @Test(expected =  IllegalArgumentException.class)
    public void shouldFailIfNoTokensOnAtPlus() throws Exception {
        N1qlParams source = N1qlParams.build()
            .consistency(ScanConsistency.AT_PLUS);

        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);
    }
