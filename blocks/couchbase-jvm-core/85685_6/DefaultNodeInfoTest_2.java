
    @Test
    public void shouldExposeRawHostnameFromConstruction() {
        assertEquals(
            "localhost",
            new DefaultNodeInfo(null, "localhost:8091", new HashMap<String, Integer>()).rawHostname()
        );

        assertEquals(
            "127.0.0.1",
            new DefaultNodeInfo(null, "127.0.0.1:8091", new HashMap<String, Integer>()).rawHostname()
        );
    }
