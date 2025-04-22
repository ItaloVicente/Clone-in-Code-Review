    @Test
    public void shouldAllowToConfigurePriority() {
        AnalyticsParams params = AnalyticsParams.build();
        assertEquals(0, params.priority());

        params = AnalyticsParams.build().priority(true);
        assertEquals(-1, params.priority());

        params = AnalyticsParams.build().priority(false);
        assertEquals(0, params.priority());
    }

