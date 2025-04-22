    @Test
    public void shouldOutputSmallKeysInToString() {
        JsonArray keysArray = JsonArray.from("foo", 3, true);
        ViewQuery query = ViewQuery.from("design", "view").keys(keysArray);
        assertEquals("", query.toQueryString());
        assertEquals("ViewQuery(design/view){params=\"\", keys=\"[\"foo\",3,true]\"}", query.toString());
    }

    @Test
    public void shouldTruncateLargeKeysInToString() {
        StringBuilder largeString = new StringBuilder(142);
        for (int i = 0; i < 140; i++) {
            largeString.append('a');
        }
        largeString.append("bc");
        JsonArray keysArray = JsonArray.from(largeString.toString());
        ViewQuery query = ViewQuery.from("design", "view").keys(keysArray);
        assertEquals("", query.toQueryString());
        assertEquals("ViewQuery(design/view){params=\"\", keys=\"[\"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa" +
                "aaaaaaaaaaaaaaaaaaaaaaaaaaa...\"(146 chars total)}", query.toString());
    }

    @Test
    public void shouldOutputDesignDocViewDevAndIncludeDocsInToString() {
        ViewQuery query = ViewQuery.from("a", "b").includeDocs().development();
        assertEquals("", query.toQueryString());
        assertEquals("ViewQuery(a/b){params=\"\", dev, includeDocs}", query.toString());
    }

