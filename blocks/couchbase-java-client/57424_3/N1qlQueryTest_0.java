
    @Test
    public void testPreparedSumWorks() {
        ctx.ignoreIfClusterUnder(new Version(4, 1, 0));

        String statement = "SELECT sum(c1) FROM `" + ctx.bucketName() + "`";
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
        String statement = "SELECT * FROM `" + ctx.bucketName() + "` WHERE item = $1";
        N1qlQuery query = N1qlQuery.parameterized(statement, JsonArray.from("value"), N1qlParams.build().adhoc(false));
        N1qlQuery query2 = N1qlQuery.parameterized(statement, JsonArray.from(123), N1qlParams.build().adhoc(false));

        ctx.bucket().invalidateQueryCache();
        N1qlQueryResult response = ctx.bucket().query(query);
        N1qlQueryResult secondResponse = ctx.bucket().query(query2);

        assertTrue(response.finalSuccess());
        assertTrue(secondResponse.finalSuccess());
        assertEquals(1, ctx.bucket().invalidateQueryCache());

        assertEquals(1, response.allRows().size());
        assertEquals(1, secondResponse.allRows().size());
    }

    @Test
    public void testPreparedWithNamedPlaceholdersExecute() {
        String statement = "SELECT * FROM `" + ctx.bucketName() + "` WHERE item = $item";
        N1qlQuery query = N1qlQuery
                .parameterized(statement, JsonObject.create().put("item", "value"), N1qlParams.build().adhoc(false));
        N1qlQuery query2 = N1qlQuery.parameterized(statement, JsonObject.create().put("item", 123), N1qlParams.build().adhoc(false));

        ctx.bucket().invalidateQueryCache();
        N1qlQueryResult response = ctx.bucket().query(query);
        N1qlQueryResult secondResponse = ctx.bucket().query(query2);

        assertTrue(response.finalSuccess());
        assertTrue(secondResponse.finalSuccess());
        assertEquals(1, ctx.bucket().invalidateQueryCache());

        assertEquals(1, response.allRows().size());
        assertEquals(1, secondResponse.allRows().size());
    }
