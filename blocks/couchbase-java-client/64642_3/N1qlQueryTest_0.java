    @Test
    public void shouldUseRawParams() {
        N1qlQuery query = N1qlQuery.simple(
            select("*").fromCurrentBucket().limit(1),
            N1qlParams.build()
                .rawParam("metrics", false)
                .rawParam("client_context_id", "somecustomID")
        );

        N1qlQueryResult result = ctx.bucket().query(query);
        assertEquals(N1qlMetrics.EMPTY_METRICS, result.info());
        assertEquals("somecustomID", result.clientContextId());
    }

