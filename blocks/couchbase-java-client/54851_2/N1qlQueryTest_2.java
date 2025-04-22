    @Test
    public void preparedShouldHaveBothNameAndEncodedPlanButNotStatementInN1ql() {
        PreparedPayload plan = new PreparedPayload(select("*"), "planName", "somePlan1234");
        PreparedN1qlQuery preparedN1qlQuery = new PreparedN1qlQuery(plan, N1qlParams.build());

        assertEquals("prepared", preparedN1qlQuery.statementType());
        assertEquals("planName", preparedN1qlQuery.statementValue());
        JsonObject n1ql = preparedN1qlQuery.n1ql();
        assertEquals("somePlan1234", n1ql.getString("encoded_plan"));
        assertEquals("planName", n1ql.getString("prepared"));
        assertFalse(n1ql.containsKey("statement"));
    }

