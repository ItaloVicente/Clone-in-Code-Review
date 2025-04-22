
    @Test
    public void shouldGracefullyHandleEmptyPartitions() throws Exception {
        String raw = Resources.read("config_with_no_partitions.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);

        assertEquals(DefaultCouchbaseBucketConfig.PARTITION_NOT_EXISTENT, config.nodeIndexForMaster(24, false));
        assertEquals(DefaultCouchbaseBucketConfig.PARTITION_NOT_EXISTENT, config.nodeIndexForReplica(24, 1, false));
    }
