    @Test
    public void shouldCreateAndLoadDesignDocumentWithOptions() {
        List<View> views = Arrays.asList(DefaultView.create("vOpts", "function(d,m){}", "_count"));
        Map<DesignDocument.Option, Object> options = new HashMap<DesignDocument.Option, Object>();
        options.put(DesignDocument.Option.UPDATE_MIN_CHANGES, 100);
        options.put(DesignDocument.Option.REPLICA_UPDATE_MIN_CHANGES, 5000);

        DesignDocument designDocument = DesignDocument.create("upsertWithOpts", views, options);
        manager.upsertDesignDocument(designDocument);

        DesignDocument loaded = manager.getDesignDocument("upsertWithOpts");
        assertEquals(100, loaded.options().get(DesignDocument.Option.UPDATE_MIN_CHANGES));
        assertEquals(5000, loaded.options().get(DesignDocument.Option.REPLICA_UPDATE_MIN_CHANGES));
    }

