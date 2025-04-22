
    @Test
    public void shouldWorkWithPrettyFalse() {
        ctx.ignoreIfClusterUnder(Version.parseVersion("4.5.1"));
        N1qlQuery query = N1qlQuery.simple(
            select("*").fromCurrentBucket().limit(1),
            N1qlParams.build().pretty(false)
        );

        N1qlQueryResult result = ctx.bucket().query(query);
        assertEquals(1, result.allRows().size());
        assertTrue(result.parseSuccess());
        assertTrue(result.finalSuccess());
        assertNotNull(result.info());
        assertNotNull(result.allRows());
        assertNotNull(result.errors());
    }
