    @Test
    public void testRemoveArrayElementWithIndexOutOfBounds() {
        final String path = "array[4]";
        DocumentFragment<Mutation> result = ctx.bucket()
                .mutateIn(key)
                .remove(path)
                .doMutate();

        assertNotNull(result);
        verifyException(result, PathNotFoundException.class).content(path);
        verifyException(result, PathNotFoundException.class).content(0);
        assertFalse(result.exists(0));
        assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, result.status(0));
