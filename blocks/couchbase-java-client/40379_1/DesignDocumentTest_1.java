        List<View> views = Arrays.asList(
            DefaultView.create("v1", "function(d,m){}"),
            DefaultView.create("v2", "function(d,m){}", "_count")
        );

        DesignDocument designDocument = DesignDocument.create("remove1", views);
        manager.upsertDesignDocument(designDocument).toBlocking().single();
