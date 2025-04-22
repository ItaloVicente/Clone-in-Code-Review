    public void shouldNotSplitIfThresholdNotMet() {
        String query = "stale=false&keys=[1,2,3]&endKey=test";
        Tuple2<String, String> split = handler.extractKeysFromQueryString(query, query.length() + 1);
        assertNotNull(split);
        assertNotNull(split.value1());
        assertNull(split.value2());
        assertEquals(query, split.value1());
