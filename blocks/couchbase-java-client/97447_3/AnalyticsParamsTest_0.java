    @Test
    public void shouldConfigurePrettyFalseByDefault() {
        AnalyticsParams params = AnalyticsParams.build();
        JsonObject result = JsonObject.create();
        params.injectParams(result);
        assertFalse(result.getBoolean("pretty"));
    }

    @Test
    public void shouldAllowToConfigurePretty() {
        AnalyticsParams params = AnalyticsParams.build().pretty(true);
        JsonObject result = JsonObject.create();
        params.injectParams(result);
        assertTrue(result.getBoolean("pretty"));
    }

