    @Test
    public void shouldUseOnErrorCallbackWhenQueryingMissingDesignDocumentAndOnErrorSetToStop() {
        ViewQuery queryTemplate = ViewQuery.from("test", "dummy");
        queryTemplate.stale(Stale.FALSE).onError(OnError.STOP);
        boolean errorRaised = false;
        try {
            bucket().query(queryTemplate);
        } catch (ViewQueryException ex) {
            assertEquals("not_found", ex.getError());
        }
        assertTrue("Should raise ViewQueryException when View is missing", errorRaised);
    }

    @Test
    public void shouldReturnErrorInResultObjectWhenQueryingMissingDesignDocumentAndOnErrorSetToContinue() {
        ViewQuery queryTemplate = ViewQuery.from("test", "dummy");
        queryTemplate.stale(Stale.FALSE).onError(OnError.CONTINUE);
        ViewResult result = bucket().query(queryTemplate);
        assertFalse(result.success());
        assertEquals("not_found", result.error().getString("error"));
    }
