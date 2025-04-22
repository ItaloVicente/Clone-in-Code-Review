    @Test
    public void shouldDisableMetrics() {
        N1qlQuery query = N1qlQuery.simple(
            select("*").fromCurrentBucket().limit(1),
            N1qlParams.build().enableMetrics(false)
        );

        N1qlQueryResult result = ctx.bucket().query(query);
        assertNull(result.info());
    }

