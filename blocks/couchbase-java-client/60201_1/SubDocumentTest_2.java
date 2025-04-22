        DocumentFragment<Lookup> resultSub = ctx.bucket().lookupIn(key).exists("sub").doLookup();
        DocumentFragment<Lookup> resultInt = ctx.bucket().lookupIn(key).exists("int").doLookup();
        DocumentFragment<Lookup> resultString = ctx.bucket().lookupIn(key).exists("string").doLookup();
        DocumentFragment<Lookup> resultArray = ctx.bucket().lookupIn(key).exists("array").doLookup();
        DocumentFragment<Lookup> resultBoolean = ctx.bucket().lookupIn(key).exists("boolean").doLookup();
        DocumentFragment<Lookup> resultBadPath = ctx.bucket().lookupIn(key).exists("somePathBlaBla").doLookup();

        assertTrue(resultSub.exists("sub"));
        assertTrue(resultInt.exists("int"));
        assertTrue(resultString.exists("string"));
        assertTrue(resultArray.exists("array"));
        assertTrue(resultBoolean.exists("boolean"));
        assertFalse(resultBadPath.exists("somePathBlaBla"));

        assertEquals(Boolean.TRUE, resultSub.content("sub"));
        assertEquals(Boolean.TRUE, resultInt.content("int"));
        assertEquals(Boolean.TRUE, resultString.content("string"));
        assertEquals(Boolean.TRUE, resultArray.content("array"));
        assertEquals(Boolean.TRUE, resultBoolean.content("boolean"));
        try {
            resultBadPath.content("somePathBlaBla");
            fail("expected exception when retrieving content for a bad exists");
        } catch (PathNotFoundException e) {
        }
