
    @Test
    public void testPreparedSumWorks() {
        ctx.ignoreIfClusterUnder(new Version(4,1,0));

        String statement = "SELECT sum(c1) FROM default";
        N1qlQuery query = N1qlQuery.simple(statement, N1qlParams.build().adhoc(false));

        ctx.bucket().invalidateQueryCache();
        N1qlQueryResult response = ctx.bucket().query(query);
        N1qlQueryResult secondResponse = ctx.bucket().query(query);

        assertTrue(response.finalSuccess());
        assertTrue(secondResponse.finalSuccess());
        assertEquals(1, ctx.bucket().invalidateQueryCache());
    }

    @Test
    public void testPreparedWithPositionalPlaceholdersExecute() {
        String statement = "SELECT * FROM `beer-sample` WHERE abv = $1";
        N1qlQuery query = N1qlQuery.parameterized(statement, JsonArray.from(1), N1qlParams.build().adhoc(false));
        N1qlQuery query2 = N1qlQuery.parameterized(statement, JsonArray.from(9.9), N1qlParams.build().adhoc(false));

        ctx.bucket().invalidateQueryCache();
        N1qlQueryResult response = ctx.bucket().query(query);
        N1qlQueryResult secondResponse = ctx.bucket().query(query2);

        assertTrue(response.finalSuccess());
        assertTrue(secondResponse.finalSuccess());
        assertEquals(1, ctx.bucket().invalidateQueryCache());

        assertFalse(response.allRows().isEmpty());
        assertEquals(4, secondResponse.allRows().size());
    }

    @Test
    public void testPreparedWithNamedPlaceholdersExecute() {
        String statement = "SELECT * FROM `beer-sample` WHERE abv = $abv";
        N1qlQuery query = N1qlQuery.parameterized(statement, JsonObject.create().put("abv", 1), N1qlParams.build().adhoc(false));
        N1qlQuery query2 = N1qlQuery.parameterized(statement, JsonObject.create().put("abv", 9.9), N1qlParams.build().adhoc(false));

        ctx.bucket().invalidateQueryCache();
        N1qlQueryResult response = ctx.bucket().query(query);
        N1qlQueryResult secondResponse = ctx.bucket().query(query2);

        assertTrue(response.finalSuccess());
        assertTrue(secondResponse.finalSuccess());
        assertEquals(1, ctx.bucket().invalidateQueryCache());

        assertFalse(response.allRows().isEmpty());
        assertEquals(4, secondResponse.allRows().size());
    }
