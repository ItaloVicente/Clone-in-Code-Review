        assertEquals(expected, query.n1ql());
    }

    @Test
    public void queryParamsShouldBeInjectedInQuery() {
        QueryParams fullParams = QueryParams.build()
                .consistency(ScanConsistency.REQUEST_PLUS)
                .scanWait(12, TimeUnit.SECONDS)
                .serverSideTimeout(20, TimeUnit.SECONDS)
                .withContextId("test");

        JsonObject expected = JsonObject.create()
                .put("statement", "SELECT * FROM default")
                .put("scan_consistency", "request_plus")
                .put("scan_wait", "12s")
                .put("timeout", "20s")
                .put("client_context_id", "test");

        SimpleQuery query1 = new SimpleQuery(select(x("*")).from("default"), fullParams);
        assertEquals(expected, query1.n1ql());

        ParametrizedQuery query2 = new ParametrizedQuery(select(x("*")).from("default"), JsonObject.empty(), fullParams);
        assertEquals(expected, query2.n1ql());

        JsonObject fakePlan = JsonObject.create().put("fake", "select *");
        expected.removeKey("statement").put("prepared", fakePlan);
        PreparedQuery query3 = new PreparedQuery(new QueryPlan(fakePlan), JsonArray.empty(), fullParams);
        assertEquals(expected, query3.n1ql());
