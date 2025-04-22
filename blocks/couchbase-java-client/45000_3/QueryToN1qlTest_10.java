        assertEquals(expected.toString(), query.toN1QL());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldRefuseToPrepareQueryPlan() {
        QueryPlan fakePlan = new QueryPlan(JsonObject.empty());
        PrepareStatement.prepare(fakePlan);
    }

    @Test
    public void shouldNotWrapAPrepareStatement() {
        PrepareStatement statement = PrepareStatement.prepare(select("*"));
        assertEquals(statement, PrepareStatement.prepare(statement));
    }

    @Test
    public void shouldPrependStatementWithPrepare() {
        Statement toPrepare = select("*").from("default");
        PrepareStatement prepare = PrepareStatement.prepare(toPrepare);

        assertEquals("PREPARE SELECT * FROM default", prepare.toString());
