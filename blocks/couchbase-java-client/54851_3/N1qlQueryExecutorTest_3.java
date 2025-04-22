
        PreparedPayload plan = cache.values().iterator().next();
        PreparedN1qlQuery planQuery = new PreparedN1qlQuery(plan, N1qlParams.build());
        JsonObject n1qlPlanQuery = planQuery.n1ql();
        assertEquals("server", plan.payload());
        assertEquals("server", n1qlPlanQuery.getString("prepared"));
        assertEquals("encodedPlan", n1qlPlanQuery.getString("encoded_plan"));
        assertFalse(n1qlPlanQuery.containsKey("statement"));
    }

    @Test
    public void testExtractionOfPayloadFromPrepareResponse() {
        LRUCache<String, PreparedPayload> cache = new LRUCache<String, PreparedPayload>(3);
        CouchbaseCore mockFacade = mock(CouchbaseCore.class);
        N1qlQueryExecutor executor = new N1qlQueryExecutor(mockFacade, "default", "", cache);

        JsonObject prepareResponse = JsonObject.create()
                .put("encoded_plan", "encoded123")
                .put("name", "UUID")
                .put("prepared", "SomeLongVersionOfThePlan")
                .put("text", "SELECT original FROM bucket");
        PrepareStatement prepared = PrepareStatement.prepare("SELECT something");
        PreparedPayload extracted = executor.extractPreparedPayloadFromResponse(prepared, prepareResponse);

        assertEquals("SELECT something", extracted.originalStatement().toString());
        assertEquals("UUID", extracted.payload());
        assertEquals("UUID", extracted.preparedName());
        assertEquals("encoded123", extracted.encodedPlan());

