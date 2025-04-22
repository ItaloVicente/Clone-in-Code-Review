    @Test
    public void shouldInsertAndGetWithSpatial() {
        List<View> views = Arrays.asList(
            SpatialView.create("geo", "function (doc) {if(doc.type == \"city\") { emit([doc.lon, doc.lat], null);}}")
        );

        DesignDocument designDocument = DesignDocument.create("withSpatial", views);
        manager.upsertDesignDocument(designDocument);

        DesignDocument found = manager.getDesignDocument("withSpatial");
        assertEquals(1, found.views().size());
        View view = found.views().get(0);
        assertTrue(view instanceof SpatialView);
        assertEquals("geo", view.name());
        assertNotNull(view.map());
        assertNull(view.reduce());
        assertFalse(view.hasReduce());
    }

