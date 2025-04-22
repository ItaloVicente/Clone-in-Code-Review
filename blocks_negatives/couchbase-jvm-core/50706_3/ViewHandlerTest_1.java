    public void shouldSplitOnlyKeys() {
        String query = "keys=[1,2,3]";
        Tuple2<String, String> split = handler.extractKeysFromQueryString(query, 4); //sure to trigger splitting

        assertNotNull(split);
        assertNotNull(split.value1());
        assertNotNull(split.value2());
        assertEquals(0, split.value1().length());
        assertEquals("{\"keys\":[1,2,3]}", split.value2());
