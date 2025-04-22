        assertTrue(request.nodes().contains(NetworkAddress.localhost()));
    }

    @Test
    public void shouldDeduplicateHosts() {
        SeedNodesRequest request = new SeedNodesRequest("127.0.0.1", "localhost");
        assertEquals(1, request.nodes().size());
    }

    @Test
    public void shouldProceedIfOnlyPartialFailure() {
        String invalidIp = "999.999.999.999";
        SeedNodesRequest request = new SeedNodesRequest("127.0.0.1", invalidIp);
        assertEquals(1, request.nodes().size());

        request = new SeedNodesRequest("127.0.0.1", "");
        assertEquals(1, request.nodes().size());
