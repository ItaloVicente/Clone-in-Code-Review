        DocumentFragment<Lookup> results = ctx.bucket()
                .lookupIn(key)
                .get("boolean")
                .get("sub")
                .exists("string")
                .get("no")
                .exists("no")
                .get("sub[1]")
                .exists("sub[1]")
                .doLookup();

        assertNotNull(results);
        assertEquals(7, results.size());

        assertEquals(true, results.exists(0));
        assertEquals(true, results.exists(1));
        assertEquals(true, results.exists(2));
        assertEquals(false, results.exists(3));
        assertEquals(false, results.exists(4));
        assertEquals(false, results.exists(5));
        assertEquals(false, results.exists(6));

        assertTrue(results.content(0) instanceof Boolean);
        assertTrue(results.content(1) instanceof JsonObject);
        assertTrue(results.content(2) instanceof Boolean);
        assertEquals(null, results.content(3));
        assertEquals(false, results.content(4));
        verifyException(results).content(5);
        assertThat("expected subdocument exception when getting content for #5",
                caughtException(), instanceOf(SubDocumentException.class));
        verifyException(results).content(6);
        assertThat("expected subdocument exception when getting content for #6",
                caughtException(), instanceOf(SubDocumentException.class));
