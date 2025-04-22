        List<View> views = Arrays.asList(
            DefaultView.create("v1", "function(d,m){}"),
            DefaultView.create("v2", "function(d,m){}", "_count")
        );

        DesignDocument designDocument = DesignDocument.create("pub2", views);
        manager.upsertDesignDocument(designDocument, true).toBlocking().single();

        DesignDocument found = manager.getDesignDocument("pub2").toBlocking().singleOrDefault(null);
        assertNull(found);
