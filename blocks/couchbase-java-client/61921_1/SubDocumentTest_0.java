    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveInEmptyPathFails() {
        ctx.bucket().retrieveIn(key);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveInNullPathFails() {
        ctx.bucket().retrieveIn(key, (String[]) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveInNullKeyFails() {
        ctx.bucket().retrieveIn(null, "sub");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRetrieveInEmptyKeyFails() {
        ctx.bucket().retrieveIn("", "sub");
    }

    @Test
    public void testRetrieveIn() {
        DocumentFragment<Lookup> results = ctx.bucket()
                .retrieveIn(key, "boolean", "sub", "string", "no", "sub[1]");

        assertNotNull(results);
        assertEquals(5, results.size());

        assertEquals(true, results.exists(0));
        assertEquals(true, results.exists(1));
        assertEquals(true, results.exists(2));
        assertEquals(true, results.exists(3)); //content is null, pathNotFound
        assertEquals(false, results.exists(4)); //content throws, other error

        assertTrue(results.content(0) instanceof Boolean);
        assertTrue(results.content(1) instanceof JsonObject);
        assertTrue(results.content(2) instanceof String);
        assertNull(results.content(3));
        verifyException(results).content(4);
        assertThat("expected subdocument exception when getting content for #4",
                caughtException(), instanceOf(SubDocumentException.class));
    }

