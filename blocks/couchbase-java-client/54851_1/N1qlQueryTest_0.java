    @Test
    public void testNotAdhocPopulatesCache() {
        Statement statement = select(x("*")).from(i(bucketName())).limit(10);
        N1qlQuery query = N1qlQuery.simple(statement, N1qlParams.build().adhoc(false));
        bucket().invalidateQueryCache();
        N1qlQueryResult response = bucket().query(query);
        N1qlQueryResult responseFromCache = bucket().query(query);

        assertTrue(response.finalSuccess());
        assertTrue(responseFromCache.finalSuccess());
        assertEquals(1, bucket().invalidateQueryCache());
    }

    @Test
    public void testAdhocDoesntPopulateCache() {
        Statement statement = select(x("*")).from(i(bucketName())).limit(10);
        N1qlQuery query = N1qlQuery.simple(statement, N1qlParams.build().adhoc(true));

        bucket().invalidateQueryCache();
        N1qlQueryResult response = bucket().query(query);
        N1qlQueryResult secondResponse = bucket().query(query);

        assertTrue(response.finalSuccess());
        assertTrue(secondResponse.finalSuccess());
        assertEquals(0, bucket().invalidateQueryCache());
    }
