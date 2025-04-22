
    @Test
    public void shouldRetryPrepareIfPlanNameNotFound() {
        Statement st = select("*").from(i("beer-sample")).limit(10);
        PreparedPayload nonExistingPayload = new PreparedPayload(st, "nonExistingName");

        QueryResult response = bucket().query(nonExistingPayload);
        assertTrue(response.errors().toString(), response.finalSuccess());
        List<QueryRow> rows = response.allRows();
        assertEquals(10, rows.size());
    }
