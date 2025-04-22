
    @Test
    public void shouldCacheBucketReference() {
        Cluster cluster = CouchbaseCluster.create(TestProperties.seedNode());
        Bucket bucket1 = cluster.openBucket(TestProperties.bucket(), TestProperties.password());
        Bucket bucket2 = cluster.openBucket(TestProperties.bucket(), TestProperties.password());

        assertEquals(bucket1.hashCode(), bucket2.hashCode());

        assertFalse(bucket1.isClosed());
        assertFalse(bucket2.isClosed());
        bucket1.close();
        assertTrue(bucket1.isClosed());
        assertTrue(bucket2.isClosed());

        Bucket bucket3 = cluster.openBucket(TestProperties.bucket(), TestProperties.password());

        assertNotEquals(bucket1.hashCode(), bucket3.hashCode());

        assertFalse(bucket3.isClosed());
    }
