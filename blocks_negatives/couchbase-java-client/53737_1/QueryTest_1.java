    @Test
    public void shouldProduceAndExecutePlan() {
        String preparedName = "testPreparedNamed";

        Statement statement = select(x("*")).from(i(bucketName())).where(x("item").eq(x("$1")));
        PrepareStatement prepareStatement = PrepareStatement.prepare(statement, preparedName);
        PreparedPayload payload = bucket().prepare(prepareStatement);
        assertNotNull(bucket().get("test2"));

        assertNotNull(payload);
        assertNotNull(payload.originalStatement());
        assertNotNull(payload.preparedName());
        assertEquals(statement.toString(), payload.originalStatement().toString());
        assertEquals(preparedName, payload.preparedName());

        PreparedQuery preparedQuery = Query.prepared(payload,
                JsonArray.from(123),
                QueryParams.build().withContextId("TEST").consistency(CONSISTENCY));
        QueryResult response = bucket().query(preparedQuery, 2, TimeUnit.MINUTES);
        assertTrue(response.errors().toString(), response.finalSuccess());
        List<QueryRow> rows = response.allRows();
        assertEquals("TEST", response.clientContextId());
