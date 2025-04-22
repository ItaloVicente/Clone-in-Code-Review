    @Test
    public void shouldDisableMetrics() {
        N1qlQuery query = N1qlQuery.simple(
            select("*").fromCurrentBucket().limit(1),
            N1qlParams.build().disableMetrics(true)
        );

        N1qlQueryResult result = ctx.bucket().query(query);
        assertEquals(N1qlMetrics.EMPTY_METRICS, result.info());
    }

