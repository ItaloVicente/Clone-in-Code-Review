    @Test
    public void shouldImportFromString() {
        String input = "{\n" +
            "  \"default\": {\n" +
            "    \"1\": [1, \"1234\"]\n" +
            "  },\n" +
            "  \"beer-sample\": {\n" +
            "    \"25\": [10, \"5678\"]\n" +
            "  }\n" +
            "}";

        MutationState state = MutationState.stringImport(input);
        int count = 0;
        for (MutationToken token : state) {
            count++;
            if (token.bucket().equals("default")) {
                assertEquals(1, token.vbucketID());
                assertEquals(1, token.sequenceNumber());
                assertEquals(1234, token.vbucketUUID());
            } else if (token.bucket().equals("beer-sample")) {
                assertEquals(25, token.vbucketID());
                assertEquals(10, token.sequenceNumber());
                assertEquals(5678, token.vbucketUUID());
            } else {
                assertTrue("Found not expected bucket name when importing MutationState", false);
            }
        }
        assertEquals(2, count);
    }

    @Test
    public void shouldImportFromJsonObject() {
        MutationToken token = new MutationToken(1, 1234, 5678, "bucket1");
        JsonDocument document = JsonDocument.create("id", 0, JsonObject.empty(), 0, token);

        MutationState state = MutationState.from(document);

        MutationToken token2 = new MutationToken(2, 8888, 9999, "bucket2");
        JsonDocument document2 = JsonDocument.create("id", 0, JsonObject.empty(), 0, token2);

        state.add(document2);

        JsonObject exported = state.export();
        assertEquals(state, MutationState.jsonImport(exported));
    }

