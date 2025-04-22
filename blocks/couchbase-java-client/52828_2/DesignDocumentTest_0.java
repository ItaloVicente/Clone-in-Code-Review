    @Test
    public void shouldCreateAndLoadDesignDocumentWithOptions() {
        List<View> views = Arrays.asList(DefaultView.create("vOpts", "function(d,m){}", "_count"));
        Map<DesignDocument.Option, Long> options = new HashMap<DesignDocument.Option, Long>();
        options.put(DesignDocument.Option.UPDATE_MIN_CHANGES, 100L);
        options.put(DesignDocument.Option.REPLICA_UPDATE_MIN_CHANGES, 5000L);

        DesignDocument designDocument = DesignDocument.create("upsertWithOpts", views, options);
        manager.upsertDesignDocument(designDocument);

        DesignDocument loaded = manager.getDesignDocument("upsertWithOpts");
        assertEquals((Long) 100L, loaded.options().get(DesignDocument.Option.UPDATE_MIN_CHANGES));
        assertEquals((Long) 5000L, loaded.options().get(DesignDocument.Option.REPLICA_UPDATE_MIN_CHANGES));
    }

