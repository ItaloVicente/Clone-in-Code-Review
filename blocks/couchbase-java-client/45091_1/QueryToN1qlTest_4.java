        assertEquals(expected, query.n1ql());
    }

    @Test
    public void queryParamsShouldBeInjectedInQuery() {
        QueryParams fullParams = QueryParams.build()
                .consistencyAtPlus(Collections.singletonMap("5", 12345))
                .scanWait(12, TimeUnit.SECONDS)
                .addCredential("bucket", "pass")
                .addAdminCredential("Admin", "pass")
                .serverSideTimeout(20, TimeUnit.SECONDS)
                .withContextId("test");

        JsonObject expectedVector = JsonObject.create().put("5", 12345);
        JsonObject expectedCred1 = JsonObject.create().put("user", "local:bucket").put("pass", "pass");
        JsonObject expectedCred2 = JsonObject.create().put("user", "admin:Admin").put("pass", "pass");
        JsonObject expected = JsonObject.create()
                .put("statement", "SELECT * FROM default")
                .put("scan_consistency", "at_plus")
                .put("scan_vector", expectedVector)
                .put("scan_wait", "12s")
                .put("creds", JsonArray.create().add(expectedCred1).add(expectedCred2))
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
