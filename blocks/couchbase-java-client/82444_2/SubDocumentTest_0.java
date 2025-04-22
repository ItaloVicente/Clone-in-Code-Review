
    @Test
    public void shouldSingleLookupGetCountOnArray() {
        ctx.ignoreIfClusterUnder(new Version(5, 0, 0));

        DocumentFragment<Lookup> result = ctx.bucket()
            .lookupIn(key)
            .getCount("array")
            .execute();

        assertEquals(3L, result.content("array"));
    }

    @Test
    public void shouldSingleLookupGetCountOnObject() {
        ctx.ignoreIfClusterUnder(new Version(5, 0, 0));

        DocumentFragment<Lookup> result = ctx.bucket()
            .lookupIn(key)
            .getCount("sub")
            .execute();

        assertEquals(1L, result.content("sub"));
    }

    @Test
    public void shouldMultiLookupGetCount() {
        ctx.ignoreIfClusterUnder(new Version(5, 0, 0));

        DocumentFragment<Lookup> result = ctx.bucket()
            .lookupIn(key)
            .getCount("array")
            .getCount("sub")
            .execute();

        assertEquals(3L, result.content("array"));
        assertEquals(1L, result.content("sub"));
    }

    @Test
    public void shouldFailSingleLookupGetCountOnPathError() {
        ctx.ignoreIfClusterUnder(new Version(5, 0, 0));

        DocumentFragment<Lookup> result = ctx.bucket()
            .lookupIn(key)
            .getCount("does_not_exist")
            .execute();

        assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, result.status("does_not_exist"));
        assertNull(result.content("does_not_exist"));
    }

    @Test(expected = PathMismatchException.class)
    public void shouldFailSingleLookupGetCountOnPrimitiveError() {
        ctx.ignoreIfClusterUnder(new Version(5, 0, 0));
        ctx.bucket().lookupIn(key).getCount("boolean").execute();
    }
