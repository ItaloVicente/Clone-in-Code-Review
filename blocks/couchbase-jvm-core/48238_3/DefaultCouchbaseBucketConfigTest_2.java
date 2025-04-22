
    @Test
    public void shouldFallbackToNodeHostnameIfNotInNodesExt() throws Exception {
        String raw = Resources.read("nodes_ext_without_hostname.json", getClass());
        CouchbaseBucketConfig config = JSON_MAPPER.readValue(raw, CouchbaseBucketConfig.class);

        InetAddress expected = InetAddress.getByName("1.2.3.4");
        assertEquals(1, config.nodes().size());
        assertEquals(expected, config.nodes().get(0).hostname());

    }
