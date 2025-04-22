    public void testExistsInUnknownPathReturnContentFalse() {
        DocumentFragment<Lookup> result = ctx.bucket().lookupIn(key).exists("badPath").doLookup();

        assertNotNull(result);
        assertEquals(false, result.content(0));
        assertEquals(false, result.content("badPath"));
    }

    @Test(expected = PathMismatchException.class)
    public void testExistOnMismatchPathThrowsException() {
        ctx.bucket().lookupIn(key).exists("sub[1]").doLookup();
