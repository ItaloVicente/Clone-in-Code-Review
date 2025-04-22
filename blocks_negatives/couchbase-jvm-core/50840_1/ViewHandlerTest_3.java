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
