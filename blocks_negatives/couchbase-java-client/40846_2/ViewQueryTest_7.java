        assertFalse(query.isWithDocuments());
        assertTrue(query.toString().isEmpty());
    }

    @Test
    public void shouldSetIncludeDocs() {
        ViewQuery query = ViewQuery.from("design", "view").withDocuments();
        assertTrue(query.isWithDocuments());
