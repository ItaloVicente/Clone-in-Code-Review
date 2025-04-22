    @Test
    public void shouldAlwaysStartAsFree() {
        Endpoint endpoint = new DummyEndpoint(hostname, environment);
        assertTrue(endpoint.isFree());
    }

