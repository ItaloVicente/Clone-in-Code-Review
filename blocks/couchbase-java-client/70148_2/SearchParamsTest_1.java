    @Test
    public void shouldInjectSort() {
        SearchQuery p = new SearchQuery(null, null)
            .sort("hello", "world", "-_score");
        JsonObject result = JsonObject.empty();
        p.injectParams(result);

        JsonObject expected = JsonObject.create()
            .put("sort", JsonArray.from("hello", "world", "-_score"));
        assertEquals(expected, result);
    }

