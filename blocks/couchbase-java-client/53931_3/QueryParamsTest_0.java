
    @Test
    public void shouldInjectMaxParallelism() throws Exception {
        QueryParams source = QueryParams.build().maxParallelism(5);

        JsonObject expected = JsonObject.create().put("max_parallelism", "5");
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }
