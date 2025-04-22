        String path1 = "sub[1]";
        String path2 = "badPath";
        String path3 = "sub";
        DocumentFragment<Lookup> results = ctx.bucket()
                .lookupIn(key)
                .exists(path1)
                .exists(path2)
                .exists(path3)
                .doLookup();

        assertNotNull(results);
        assertEquals(3, results.size());

        assertFalse(results.exists(0));
        assertEquals(ResponseStatus.SUBDOC_PATH_MISMATCH, results.status(0));
        verifyException(results, PathMismatchException.class).content(0);
        assertFalse(results.exists(path1));
        assertEquals(ResponseStatus.SUBDOC_PATH_MISMATCH, results.status(path1));
        verifyException(results, PathMismatchException.class).content(path1);

        assertFalse(results.exists(1));
        assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, results.status(1));
        verifyException(results, PathNotFoundException.class).content(1);
        assertFalse(results.exists(path2));
        assertEquals(ResponseStatus.SUBDOC_PATH_NOT_FOUND, results.status(path2));
        verifyException(results, PathNotFoundException.class).content(path2);

        assertTrue(results.exists(2));
        assertEquals(true, results.content(2));
        assertEquals(ResponseStatus.SUCCESS, results.status(2));
        assertTrue(results.exists(path3));
        assertEquals(true, results.content(path3));
        assertEquals(ResponseStatus.SUCCESS, results.status(path3));
