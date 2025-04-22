
    @Test
    public void shouldListDesignDocuments() {
        ListDesignDocumentsRequest req = new ListDesignDocumentsRequest(bucket(), password());
        ListDesignDocumentResponse response = cluster().<ListDesignDocumentResponse>send(req).toBlocking().single();

        assertTrue(response.status().isSuccess());
        assertNotNull(response.content());
    }
