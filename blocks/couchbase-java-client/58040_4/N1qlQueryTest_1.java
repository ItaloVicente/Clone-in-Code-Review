
    @Test
    public void shouldSelectFromCurrentBucket() {
        N1qlQuery query = N1qlQuery.simple(
          select("*").fromCurrentBucket().limit(3),
          WITH_CONSISTENCY
        );

        N1qlQueryResult result = ctx.bucket().query(query);
        assertTrue(result.allRows().size() > 0);
        assertTrue(result.errors().isEmpty());
        assertTrue(result.finalSuccess());
    }
