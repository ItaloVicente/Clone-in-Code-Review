    @Test
    public void shouldLocateActive() {
        InetAddress node = helper.activeNodeForId("foobar");
        assertTrue(helper.nodes().contains(node));
    }

