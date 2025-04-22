    @Test
    public void testGetInUnknownPathReturnsContentNull() {
        DocumentFragment<Lookup> result = ctx.bucket().lookupIn(key).get("badPath").doLookup();

        assertNotNull(result);
        assertEquals(null, result.content(0));
        assertEquals(null, result.content("badPath"));
    }

    @Test(expected = PathMismatchException.class)
    public void testGetInPathMismatchThrowsException() {
        ctx.bucket().lookupIn(key).get("sub[1]").doLookup();
