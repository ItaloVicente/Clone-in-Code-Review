    @Test
    public void shouldInjectReadonly() throws Exception {
        N1qlParams source = N1qlParams.build().readonly(true);

        JsonObject expected = JsonObject.create().put("readonly", true);
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldInjectScapCap() throws Exception {
        N1qlParams source = N1qlParams.build().scanCap(5);

        JsonObject expected = JsonObject.create().put("scan_cap", 5);
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldInjectPipelineBatch() throws Exception {
        N1qlParams source = N1qlParams.build().pipelineBatch(99);

        JsonObject expected = JsonObject.create().put("pipeline_batch", 99);
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldInjectPipelineCap() throws Exception {
        N1qlParams source = N1qlParams.build().pipelineCap(-1);

        JsonObject expected = JsonObject.create().put("pipeline_cap", -1);
        JsonObject actual = JsonObject.empty();
        source.injectParams(actual);

        assertEquals(expected, actual);
    }

