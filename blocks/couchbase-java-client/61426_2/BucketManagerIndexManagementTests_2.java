    @Test
    public void testDropNamedPrimaryIndex() {
        BucketManager mgr = indexedBucket.bucketManager();
        mgr.createNamedPrimaryIndex("def_primary", true, false);
        assertEquals(1, mgr.listIndexes().size());

        mgr.dropNamedPrimaryIndex("def_primary", false);
        assertEquals(0, mgr.listIndexes().size());
    }

    @Test
    public void testDropPrimaryIndexWithNameFailsIfNameNotProvided() {
        BucketManager mgr = indexedBucket.bucketManager();
        mgr.createNamedPrimaryIndex("def_primary", true, false);
        assertEquals(1, mgr.listIndexes().size());

        boolean dropped = mgr.dropPrimaryIndex(true);
        assertEquals(false, dropped);
        assertEquals(1, mgr.listIndexes().size());

        try {
            mgr.dropPrimaryIndex(false);
            fail("Expected IndexDoesNotExistException");
        } catch (IndexDoesNotExistException e) {
        }
    }

    @Test(expected = IndexDoesNotExistException.class)
    public void testDropNamedPrimaryIndexThatDoesntExistFails() {
        indexedBucket.bucketManager().dropNamedPrimaryIndex("invalidPrimaryIndex", false);
    }

    @Test
    public void testDropIgnoreNamedPrimaryIndexThatDoesntExistSucceeds() {
        boolean dropped = indexedBucket.bucketManager().dropNamedPrimaryIndex("invalidPrimaryIndex", true);
        assertFalse(dropped);
    }

