        assertEquals(IndexType.GSI, third.type());
    }

    @Test
    public void testListIndexesIgnoresNonGsi() {
        List<IndexInfo> indexes = indexedBucket.bucketManager().listIndexes();
        assertEquals(indexes.toString(), 0, indexes.size());

        indexedBucket.query(N1qlQuery.simple("CREATE PRIMARY INDEX ON `" + indexedBucketName + "`"));
        indexedBucket.query(N1qlQuery.simple("CREATE INDEX `ambiguousIndex` ON `" + indexedBucketName + "` (tata, toto)"));
        indexedBucket.query(N1qlQuery.simple("CREATE INDEX `ambiguousIndex` ON `" + indexedBucketName + "` (tata) USING VIEW"));
        indexes = indexedBucket.bucketManager().listIndexes();

        assertEquals(indexes.toString(), 2, indexes.size());
        IndexInfo first = indexes.get(0);
        IndexInfo second = indexes.get(1);

        N1qlQueryResult countResult = indexedBucket.query(N1qlQuery
                .simple("SELECT COUNT(*) AS cnt FROM system:indexes WHERE keyspace_id = '" + indexedBucketName + "'"));
        List<N1qlQueryRow> rows = countResult.allRows();
        Long count = rows.get(0).value().getLong("cnt");
        assertEquals("" + rows, (Long) 3L, count);

        assertTrue(first.isPrimary());
        assertEquals(IndexInfo.PRIMARY_DEFAULT_NAME, first.name());
        assertEquals("gsi", first.rawType());
        assertNotNull(first.type());
        assertEquals(IndexType.GSI, first.type());
        assertEquals("online", first.state());
        assertEquals(JsonArray.empty(), first.indexKey());

        assertEquals("ambiguousIndex", second.name());
        assertEquals(JsonArray.create().add("`tata`").add("`toto`"), second.indexKey());
        assertEquals("online", second.state());
        assertFalse(second.isPrimary());
        assertEquals(IndexType.GSI, second.type());

