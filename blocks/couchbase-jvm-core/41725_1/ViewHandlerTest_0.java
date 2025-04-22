    @Test
    public void shouldEncodeLongViewQueryRequestWithPOST() {
        String keys = Resources.read("key_many.json", this.getClass());
        String query = "keys=" + keys;
        ViewQueryRequest request = new ViewQueryRequest("design", "view", true, query , "bucket", "password");
        channel.writeOutbound(request);
        HttpRequest outbound = (HttpRequest) channel.readOutbound();
        assertEquals(HttpMethod.POST, outbound.getMethod());
    }

