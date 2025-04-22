
    @Test
    public void shouldHandleIPv6() {
        Map<String, Integer> ports = new HashMap<String, Integer>();
        DefaultNodeInfo info = new DefaultNodeInfo(null, "[fd63:6f75:6368:2068:c490:b5ff:fe86:9cf7]:8091", ports);

        assertEquals(1, info.services().size());
        assertEquals("fd63:6f75:6368:2068:c490:b5ff:fe86:9cf7", info.hostname().address());
        assertEquals(8091, (long) info.services().get(ServiceType.CONFIG));
    }
