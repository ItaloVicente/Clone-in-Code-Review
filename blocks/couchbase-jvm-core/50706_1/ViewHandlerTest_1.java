        ViewQueryRequest request = new ViewQueryRequest("design", "view", true, query, keys, "bucket", "password");
        channel.writeOutbound(request);
        DefaultFullHttpRequest outbound = (DefaultFullHttpRequest) channel.readOutbound();
        assertEquals(HttpMethod.GET, outbound.getMethod());
        assertTrue(outbound.getUri().contains("keys="));
        assertTrue(outbound.getUri().contains("?stale=false&endKey=test&keys="));
        assertFalse(outbound.getUri().endsWith("?stale=false&endKey=test&keys="));
        String content = outbound.content().toString(CharsetUtil.UTF_8);
        assertTrue(content.isEmpty());
