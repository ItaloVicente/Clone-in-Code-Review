    @Test
    public void shouldHaveRequestId() {
        QueryResult result = bucket().query(Query.simple("SELECT * FROM `" + bucketName() + "` LIMIT 3",
                QueryParams.build().withContextId(null)));
        assertNotNull(result);
        assertTrue(result.parseSuccess());
        assertTrue(result.finalSuccess());
        assertNotNull(result.info());
        assertNotNull(result.allRows());
        assertNotNull(result.errors());
        assertFalse(result.allRows().isEmpty());
        assertTrue(result.errors().isEmpty());

        assertEquals("", result.clientContextId());
        assertNotNull(result.requestId());
        assertTrue(result.requestId().length() > 0);
    }

    @Test
    public void shouldHaveRequestIdAndContextId() {
        Query query = Query.simple("SELECT * FROM `" + bucketName() + "` LIMIT 3",
                QueryParams.build().withContextId("TEST"));
        System.out.println(bucket().get("test1"));
        System.out.println(query.n1ql());
        QueryResult result = bucket().query(query);
        assertNotNull(result);
        assertTrue(result.parseSuccess());
        assertTrue(result.finalSuccess());
        assertNotNull(result.info());
        assertNotNull(result.allRows());
        assertNotNull(result.errors());
        System.out.println("AllRows " + result.allRows().size());
        assertFalse(result.allRows().isEmpty());
        assertTrue(result.errors().isEmpty());

        assertEquals("TEST", result.clientContextId());
        assertNotNull(result.requestId());
        assertTrue(result.requestId().length() > 0);
    }

    @Test
    public void shouldHaveRequestIdAndTruncatedContextId() {
        String contextIdMoreThan64Bytes = "123456789012345678901234567890123456789012345678901234567890☃BCD";
        String contextIdTruncatedExpected = new String(Arrays.copyOf(contextIdMoreThan64Bytes.getBytes(), 64));
        System.out.println("contextId expected to be truncated to " + contextIdTruncatedExpected);

        Query query = Query.simple("SELECT * FROM `" + bucketName() + "` LIMIT 3",
                QueryParams.build().withContextId(contextIdMoreThan64Bytes));
        QueryResult result = bucket().query(query);
        JsonObject params = JsonObject.create();
        query.params().injectParams(params);

        assertEquals(contextIdMoreThan64Bytes, params.getString("client_context_id"));
        assertNotNull(result);
        assertTrue(result.parseSuccess());
        assertTrue(result.finalSuccess());
        assertNotNull(result.info());
        assertNotNull(result.allRows());
        assertNotNull(result.errors());
        assertFalse(result.allRows().isEmpty());
        assertTrue(result.errors().isEmpty());

        assertEquals(contextIdTruncatedExpected, result.clientContextId());
        assertNotNull(result.requestId());
        assertTrue(result.requestId().length() > 0);
    }

