        QueryResult response = bucket().query(preparedQuery);

        assertTrue(response.errors().toString(), response.finalSuccess());
        List<QueryRow> rows = response.allRows();
        assertEquals("TEST", response.clientContextId());
        assertEquals(1, rows.size());
        assertTrue(rows.get(0).value().toString().contains("123"));
