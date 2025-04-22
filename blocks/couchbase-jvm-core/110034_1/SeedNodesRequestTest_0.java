    @Test
    public void shouldDeduplicateHosts() {
        SeedNodesRequest request = new SeedNodesRequest("127.0.0.1", "127.0.0.1");
        assertEquals(1, request.nodes().size());
    }

