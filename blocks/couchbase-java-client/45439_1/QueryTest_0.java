
        QueryResult indexResult = bucket().query(Query.simple("CREATE PRIMARY INDEX ON default"));
        assertEquals(0, indexResult.allRows().size());
        assertNotNull(indexResult.info());
        List<JsonObject> indexErrors = indexResult.errors();
        Assume.assumeTrue(indexResult.finalSuccess()
                || (indexErrors.size() == 1
                    && "Primary index already exists".equals(indexErrors.get(0).getString("msg"))
        ));
