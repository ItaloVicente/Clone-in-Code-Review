    @Test
    public void shouldEncodeLongViewQueryRequestWithPOST() {
        String keys = Resources.read("key_many.json", this.getClass());
        String query = "stale=false&keys=" + keys + "&endKey=test";
        ViewQueryRequest request = new ViewQueryRequest("design", "view", true, query , "bucket", "password");
        channel.writeOutbound(request);
        DefaultFullHttpRequest outbound = (DefaultFullHttpRequest) channel.readOutbound();
        assertEquals(HttpMethod.POST, outbound.getMethod());
        assertTrue(outbound.getUri().endsWith("?stale=false&endKey=test"));
        String content = outbound.content().toString(CharsetUtil.UTF_8);
        assertTrue(content.startsWith("{\"keys\":["));
        assertTrue(content.endsWith("]}"));
    }

    @Test
    public void shouldSplitOnlyKeys() {
        String query = "keys=[1,2,3]";
        Tuple2<String, String> split = handler.extractKeysFromQueryString(query, 4); //sure to trigger splitting

        assertNotNull(split);
        assertNotNull(split.value1());
        assertNotNull(split.value2());
        assertEquals(0, split.value1().length());
        assertEquals("{\"keys\":[1,2,3]}", split.value2());
    }

    @Test
    public void shouldSplitNoKeys() {
        String query = "stale=false&endKey=test";
        Tuple2<String, String> split = handler.extractKeysFromQueryString(query, 4); //sure to trigger splitting

        assertNotNull(split);
        assertNotNull(split.value1());
        assertNotNull(split.value2());
        assertEquals(query, split.value1());
        assertEquals(0, split.value2().length());
    }

    @Test
    public void shouldSplitAndReconstructParameters() {
        String query = "stale=false&endKey=test&keys=[1,2,3]";
        Tuple2<String, String> split = handler.extractKeysFromQueryString(query, 4); //sure to trigger splitting
        assertNotNull(split);
        assertNotNull(split.value1());
        assertNotNull(split.value2());
        assertEquals("stale=false&endKey=test", split.value1());
        assertEquals("{\"keys\":[1,2,3]}", split.value2());


        query = "keys=[1,2,3]&stale=false&endKey=test";
        split = handler.extractKeysFromQueryString(query, 4); //sure to trigger splitting
        assertNotNull(split);
        assertNotNull(split.value1());
        assertNotNull(split.value2());
        assertEquals("stale=false&endKey=test", split.value1());
        assertEquals("{\"keys\":[1,2,3]}", split.value2());

        query = "stale=false&keys=[1,2,3]&endKey=test";
        split = handler.extractKeysFromQueryString(query, 4); //sure to trigger splitting
        assertNotNull(split);
        assertNotNull(split.value1());
        assertNotNull(split.value2());
        assertEquals("stale=false&endKey=test", split.value1());
        assertEquals("{\"keys\":[1,2,3]}", split.value2());
    }

    @Test
    public void shouldNotSplitIfThresholdNotMet() {
        String query = "stale=false&keys=[1,2,3]&endKey=test";
        Tuple2<String, String> split = handler.extractKeysFromQueryString(query, query.length() + 1);
        assertNotNull(split);
        assertNotNull(split.value1());
        assertNull(split.value2());
        assertEquals(query, split.value1());
    }

