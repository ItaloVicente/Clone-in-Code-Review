    public void shouldSplitNoKeys() {
        String query = "stale=false&endKey=test";
        Tuple2<String, String> split = handler.extractKeysFromQueryString(query, 4); //sure to trigger splitting

        assertNotNull(split);
        assertNotNull(split.value1());
        assertNotNull(split.value2());
        assertEquals(query, split.value1());
        assertEquals(0, split.value2().length());
