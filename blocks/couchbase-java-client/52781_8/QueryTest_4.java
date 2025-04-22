
    @Test
    public void shouldFailToExecuteUnknownNamedPreparedStatement() {
        SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMddHHmmss");
        String preparedName = "testPreparedNamed" + sdf.format(new Date());

        Statement statement = select(x("*")).from(i(bucketName())).where(x("item").eq(x("$1")));
        PrepareStatement prepareStatement = PrepareStatement.prepare(statement, preparedName);
        PreparedPayload payload = new PreparedPayload(prepareStatement, preparedName);
        PreparedQuery preparedQuery = Query.prepared(payload,
                JsonArray.from(123),
                QueryParams.build().withContextId("TEST").consistency(CONSISTENCY));
        try {
            QueryResult response = bucket().query(preparedQuery);
            fail("Expected NamedPreparedStatementException, got: " + response.allRows().toString() + ", errors: "
                + response.errors().toString());
        } catch (NamedPreparedStatementException e) {
        }
    }
