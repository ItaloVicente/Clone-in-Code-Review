
    @Test
    public void testWatchIndexesWithPrimaryTrueAddsPrimaryToWatchList() {
        BucketManager mgr = indexedBucket.bucketManager();
        mgr.createPrimaryIndex(true, false);

        final List<IndexInfo> indexInfos = mgr.watchIndexes(Collections.<String>emptyList(), true, 3, TimeUnit.SECONDS);
        assertEquals("Expected primary index to be added to watch list", 1, indexInfos.size());
        assertEquals(Index.PRIMARY_NAME, indexInfos.get(0).name());
    }
