    @Test
    public void shouldConfigurePrettyFalseByDefault() {
        AnalyticsParams params = AnalyticsParams.build();
        JsonObject result = JsonObject.create();
        params.injectParams(result);
        assertFalse(result.getBoolean("pretty"));
    }

