        JsonArray expectedKeys = JsonArray.from("`toto`", "`tata`");
        assertEquals(expectedKeys, indexes.get(0).indexKey());
        assertEquals("", indexes.get(0).condition());
    }

    @Test
    public void testCreateIndexWithWhereClause() {
        final String index = "secondaryConditionalIndex";
        List<Object> fields = Arrays.asList("toto", x("tata"));
        indexedBucket.bucketManager().createN1qlIndex(index, fields,
                x("toto").isNotMissing()
                .and(x("tata").isNotNull()),
                false, false);
        List<IndexInfo> indexes = indexedBucket.bucketManager().listN1qlIndexes();

        assertEquals(1, indexes.size());
        JsonArray expectedKeys = JsonArray.from("`toto`", "`tata`");
        assertEquals(expectedKeys, indexes.get(0).indexKey());
        assertEquals("((`toto` is not missing) and (`tata` is not null))", indexes.get(0).condition());
