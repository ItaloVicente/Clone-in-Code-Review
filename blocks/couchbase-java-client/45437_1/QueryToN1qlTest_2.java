    @Test
    public void rawSimpleQueryShouldJustProduceStatementAsIs() {
        SimpleQuery query = Query.raw("Here goes anything even not \"JSON\"");

        assertEquals("{\"statement\":\"Here goes anything even not \\\"JSON\\\"\"}", query.n1ql().toString());
    }

