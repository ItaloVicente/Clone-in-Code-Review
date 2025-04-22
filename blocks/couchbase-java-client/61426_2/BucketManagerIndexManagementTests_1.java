    @Test
    public void testCreatePrimaryIndexesWithDifferentNames() {
        boolean namedAttempt1 = indexedBucket.bucketManager().createNamedPrimaryIndex("def_primary", false, false);
        boolean namedAttempt2 = indexedBucket.bucketManager().createNamedPrimaryIndex("def_primary2", false, false);
        boolean unamedAttempt = indexedBucket.bucketManager().createPrimaryIndex(false, false);

        assertTrue(namedAttempt1);
        assertTrue(namedAttempt2);
        assertTrue(unamedAttempt);

        assertEquals(3, indexedBucket.bucketManager().listIndexes().size());
    }

