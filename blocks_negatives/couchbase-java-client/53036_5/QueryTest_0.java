        try {
            QueryResult response = bucket().query(preparedQuery);
            fail("Expected NamedPreparedStatementException, got: " + response.allRows().toString() + ", errors: "
                + response.errors().toString());
        } catch (NamedPreparedStatementException e) {
        }
