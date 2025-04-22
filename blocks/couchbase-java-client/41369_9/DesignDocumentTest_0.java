    @Test
    public void shouldRaiseErrorWhenQueryingMissingDesignDocument() {
        ViewQuery viewQuery = ViewQuery.from("test", "dummy");
        boolean errorRaised = false;
        try {
            bucket().query(viewQuery);
        } catch (ViewQueryException ex) {
            errorRaised = true;
            assertEquals(1, ex.errors().size());
            assertEquals("not_found", ex.errors().get(0).error());
        }
        assertTrue("Should raise ViewQueryException when View is missing", errorRaised);
    }
