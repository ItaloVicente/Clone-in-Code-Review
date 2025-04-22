    @Test
    public void shouldHaveLessViewsWhenUpsertingWithOnlyNewViews() {
        List<View> views = Collections.singletonList(DefaultView.create("v1", "function(d,m){}"));
        DesignDocument designDocument = DesignDocument.create("upsert3", views);
        manager.upsertDesignDocument(designDocument);

        DesignDocument found = manager.getDesignDocument("upsert3");
        assertNotNull(found);
        assertEquals("upsert3", found.name());
        assertEquals(1, found.views().size());
        assertEquals("v1", found.views().get(0).name());
        assertNull(found.views().get(0).reduce());

        views = Collections.singletonList(DefaultView.create("v2", "function(d,m){}", "_count"));
        designDocument = DesignDocument.create("upsert3", views);
        manager.upsertDesignDocument(designDocument);

        found = manager.getDesignDocument("upsert3");
        assertNotNull(found);
        assertEquals("upsert3", found.name());
        assertEquals(1, found.views().size());
        assertEquals("v2", found.views().get(0).name());
        assertEquals("_count", found.views().get(0).reduce());
    }

    @Test
    public void shouldUseLatestDefinitionWhenAddingViewNameTwice() {
        List<View> views = new ArrayList<View>();
        views.add(DefaultView.create("v1", "function(d,m){}"));
        DesignDocument designDocument = DesignDocument.create("upsert4", views);
        designDocument.views().add(DefaultView.create("v1", "function(d,m){emit(null,null);}", "_count"));
        manager.upsertDesignDocument(designDocument);

        DesignDocument found = manager.getDesignDocument("upsert4");
        assertNotNull(found);
        assertEquals("upsert4", found.name());
        assertEquals(1, found.views().size());
        assertEquals("v1", found.views().get(0).name());
        assertEquals("function(d,m){emit(null,null);}", found.views().get(0).map());
        assertEquals("_count", found.views().get(0).reduce());

        found.views().add(DefaultView.create("v1", "function(d,m){emit(d.type, null);}", null));
        manager.upsertDesignDocument(found);

        found = manager.getDesignDocument("upsert4");
        assertNotNull(found);
        assertEquals("upsert4", found.name());
        assertEquals(1, found.views().size());
        assertEquals("v1", found.views().get(0).name());
        assertEquals("function(d,m){emit(d.type, null);}", found.views().get(0).map());
        assertNull(found.views().get(0).reduce());
    }

