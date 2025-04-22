    @Test
    public void shouldQueryWithIncludeDocs() {
        SpatialViewResult result = bucket().query(
            SpatialViewQuery.from("cities", "by_location").stale(Stale.FALSE).includeDocs()
        );
        List<SpatialViewRow> allRows = result.allRows();
        assertEquals(FIXTURES.length, allRows.size());

        for (SpatialViewRow row : allRows) {
            assertNotNull(row.document().content());
        }
    }

