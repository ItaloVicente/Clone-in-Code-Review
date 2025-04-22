    @Test
    public void shouldIncludeDocs() {
        ViewQuery query = ViewQuery.from("design", "view").includeDocs();
        assertTrue(query.isIncludeDocs());
        assertEquals(JsonDocument.class, query.includeDocsTarget());

        query = ViewQuery.from("design", "view").includeDocs(JsonDocument.class);
        assertTrue(query.isIncludeDocs());
        assertEquals(JsonDocument.class, query.includeDocsTarget());

        query = ViewQuery.from("design", "view");
        assertFalse(query.isIncludeDocs());
        assertNull(query.includeDocsTarget());

        query = ViewQuery.from("design", "view").includeDocs(false, RawJsonDocument.class);
        assertFalse(query.isIncludeDocs());
        assertEquals(RawJsonDocument.class, query.includeDocsTarget());
    }

