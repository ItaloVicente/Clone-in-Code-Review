    @Test
    public void shouldQueryViewWithIterator() {
        ViewResult result = bucket().query(ViewQuery.from("users", "by_name").stale(Stale.FALSE));
        assertNull(result.debug());
        assertNull(result.error());
        assertTrue(result.success());
        assertEquals(result.totalRows(), STORED_DOCS);

        int foundRows = 0;
        for (ViewRow row : result) {
            assertNull(row.value());
            assertNotNull(row.id());
            assertNotNull(row.key());
            foundRows++;
        }
        assertEquals(STORED_DOCS, foundRows);
    }

