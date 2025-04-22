
    @Test
    public void shouldLoadConfigWithMDS() throws Exception {
        String raw = Resources.read("mds_config_index_out_of_bound_bug.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);
    }
