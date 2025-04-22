
    @Test
    public void testLongRunningPreparedQuery() {
        int fetchSz = 20000;
        N1qlQuery query = N1qlQuery.simple("select * from `"+ ctx.bucketName() +"` limit " + fetchSz, N1qlParams.build().adhoc(false));
        N1qlQueryResult result = ctx.bucket().query(query);
        System.out.println("Elapsed time:" + result.info().elapsedTime());
        assertEquals("Result size incorrect", fetchSz, result.allRows().size());
    }
}
