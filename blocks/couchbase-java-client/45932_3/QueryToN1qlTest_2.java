    @Test
    public void shouldNotDoublePrefixAStringPreparedStatement() {
        String alreadyPrepare = "PREPARE SELECT *";
        assertEquals(alreadyPrepare, PrepareStatement.prepare(alreadyPrepare).toString());
    }

