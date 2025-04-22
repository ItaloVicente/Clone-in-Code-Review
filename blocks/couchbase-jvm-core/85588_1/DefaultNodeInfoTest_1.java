
    @Test
    public void shouldHandleIPv6() {
        Map<String, Integer> ports = new HashMap<String, Integer>();
        DefaultNodeInfo info = new DefaultNodeInfo(null, "[::1]:8091", ports);
        System.out.println(info);
    }
