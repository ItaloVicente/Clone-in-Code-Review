        assertEquals(Index.PRIMARY_NAME, indexes.get(0).name());
        assertTrue(indexes.get(0).isPrimary());
    }

    @Test
    public void testCreatePrimaryIndexWithCustomName() {
        indexedBucket.bucketManager().createNamedPrimaryIndex("def_primary", false, false);
        List<IndexInfo> indexes = indexedBucket.bucketManager().listIndexes();

        assertEquals(1, indexes.size());
        assertEquals("def_primary", indexes.get(0).name());
        assertTrue(indexes.get(0).isPrimary());
