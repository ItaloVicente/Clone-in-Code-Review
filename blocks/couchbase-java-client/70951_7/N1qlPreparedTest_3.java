
    @Test
    public void testLongRunningPreparedQuery() {
        int fetzhSz = 10000;
        N1qlQuery query = N1qlQuery.simple("select * from `"+ ctx.bucketName() +"` limit " + fetzhSz, N1qlParams.build().adhoc(false));
        N1qlQueryResult result = ctx.bucket().query(query);
        System.out.println("Elapsed time:" + result.info().elapsedTime());
        assertEquals("Did not fetch all results", result.allRows().size(), fetzhSz);
    }
}
