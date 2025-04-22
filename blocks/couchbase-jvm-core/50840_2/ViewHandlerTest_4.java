    public void shouldProduceValidUrlIfShortKeysAndNoOtherQueryParam() {
        String urlEncodedKeys = "%5B%221%22%2C%222%22%2C%223%22%5D";
        String keys = "[\"1\",\"2\",\"3\"]";
        String query = "";
        ViewQueryRequest request = new ViewQueryRequest("design", "view", true, query, keys, "bucket", "password");
        channel.writeOutbound(request);
        DefaultFullHttpRequest outbound = (DefaultFullHttpRequest) channel.readOutbound();
        String failMsg = outbound.getUri();
        assertEquals(HttpMethod.GET, outbound.getMethod());
        assertTrue(failMsg, outbound.getUri().endsWith("?keys=" + urlEncodedKeys));
        String content = outbound.content().toString(CharsetUtil.UTF_8);
        assertTrue(content.isEmpty());
