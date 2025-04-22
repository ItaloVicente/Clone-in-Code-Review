    public void shouldDoNothingOnEmptyKeys() {
        String keys = "";
        String query = "stale=false&endKey=test";
        ViewQueryRequest request = new ViewQueryRequest("design", "view", true, query, keys, "bucket", "password");
        channel.writeOutbound(request);
        DefaultFullHttpRequest outbound = (DefaultFullHttpRequest) channel.readOutbound();
        assertEquals(HttpMethod.GET, outbound.getMethod());
        assertFalse(outbound.getUri().contains("keys="));
        assertTrue(outbound.getUri().endsWith("?stale=false&endKey=test"));
        String content = outbound.content().toString(CharsetUtil.UTF_8);
        assertTrue(content.isEmpty());
