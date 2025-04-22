
        assertEquals(true, results.exists(0));
        assertEquals(true, results.exists(1));
        assertEquals(true, results.exists(2));
        assertEquals(false, results.exists(3));

        assertTrue(results.content(0) instanceof Boolean);
        assertTrue(results.content(1) instanceof JsonObject);
        assertTrue(results.content(2) instanceof Boolean);
        verifyException(results).content(3);
        assertThat("expected subdocument exception when getting content for #3",
                caughtException(), instanceOf(SubDocumentException.class));
