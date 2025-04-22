    @Test
    public void testN1QLQueryToString() {
        String queryStr = "SELECT * FROM default";
        N1qlQuery query = N1qlQuery.simple(queryStr);
        assertEquals(queryStr, query.toString());

        ParameterizedN1qlQuery parameterizedN1qlQuery = N1qlQuery.parameterized(select(x("*")).from("default").where(x("name").eq("$name")),
                JsonObject.create().put("name", "foo"));
        assertEquals(queryStr + " WHERE name = $name", parameterizedN1qlQuery.toString());
    }
