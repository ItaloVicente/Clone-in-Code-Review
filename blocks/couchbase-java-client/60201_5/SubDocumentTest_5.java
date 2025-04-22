    public void testExistsInOnUnknownDocumentThrowsException() {
        ctx.bucket().lookupIn("blabla").exists("array").doLookup();
    }

    @Test
    public void testExistsInUnknownPathReturnContentFalse() {
        DocumentFragment<Lookup> result = ctx.bucket().lookupIn(key).exists("badPath").doLookup();

        assertNotNull(result);
        assertEquals(false, result.content(0));
        assertEquals(false, result.content("badPath"));
