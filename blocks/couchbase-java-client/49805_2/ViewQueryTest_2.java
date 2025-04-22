    @Test
    public void shouldLoadDocumentsWithIncludeDocs() {
        ViewResult result = bucket().query(
            ViewQuery.from("users", "by_name").limit(10).stale(Stale.FALSE).includeDocs()
        );
        assertNull(result.debug());
        assertNull(result.error());
        assertTrue(result.success());
        assertEquals(result.totalRows(), STORED_DOCS);

        int count = 0;
        Iterator<ViewRow> rows = result.rows();
        while(rows.hasNext()) {
            count++;
            ViewRow row = rows.next();

            assertNotNull(row);
            JsonDocument doc = row.document();
            assertTrue(doc.id().startsWith("user-"));
            assertTrue(doc.cas() != 0);
            assertTrue(doc.expiry() == 0);

            assertTrue(doc.content().getString("name").startsWith("Mr. Foo Bar"));
            assertTrue(doc.content().getString("type").equals("user"));
        }

        assertEquals(10, count);
    }

    @Test
    public void shouldIncludeDocsWithCustomTarget() {
        ViewResult result = bucket().query(
            ViewQuery.from("users", "by_name").limit(20).stale(Stale.FALSE).includeDocs(RawJsonDocument.class)
        );
        assertNull(result.debug());
        assertNull(result.error());
        assertTrue(result.success());
        assertEquals(result.totalRows(), STORED_DOCS);

        int count = 0;
        Iterator<ViewRow> rows = result.rows();
        while(rows.hasNext()) {
            count++;
            ViewRow row = rows.next();

            assertNotNull(row);
            RawJsonDocument doc = row.document(RawJsonDocument.class);
            assertNotNull(doc.content());
            assertFalse(doc.content().isEmpty());
        }

        assertEquals(20, count);
    }

    @Test(expected = ClassCastException.class)
    public void shouldFailWhenWrongCustomTargetOnIncludeDocs() {
        ViewResult result = bucket().query(
            ViewQuery.from("users", "by_name").limit(20).stale(Stale.FALSE).includeDocs(RawJsonDocument.class)
        );
        assertNull(result.debug());
        assertNull(result.error());
        assertTrue(result.success());
        assertEquals(result.totalRows(), STORED_DOCS);

        Iterator<ViewRow> rows = result.rows();
        while(rows.hasNext()) {
            ViewRow row = rows.next();

            assertNotNull(row);
            row.document();
        }
    }

