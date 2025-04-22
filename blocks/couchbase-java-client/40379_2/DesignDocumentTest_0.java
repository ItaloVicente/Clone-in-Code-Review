        List<View> views = Arrays.asList(DefaultView.create("v1", "function(d,m){}"));
        DesignDocument designDocument1 = DesignDocument.create("doc1", views);
        DesignDocument designDocument2 = DesignDocument.create("doc3", views);
        DesignDocument designDocument3 = DesignDocument.create("doc2", views);

        manager.upsertDesignDocument(designDocument1).toBlocking().single();
        manager.upsertDesignDocument(designDocument2).toBlocking().single();
        manager.upsertDesignDocument(designDocument3).toBlocking().single();

        List<DesignDocument> docs = manager.getDesignDocuments().toList().toBlocking().single();
        assertTrue(docs.size() >= 3);
        int found = 0;
        for (DesignDocument doc : docs) {
            if (doc.name().equals("doc1") || doc.name().equals("doc2") || doc.name().equals("doc3")) {
                found++;
            }
        }
        assertEquals(3, found);
