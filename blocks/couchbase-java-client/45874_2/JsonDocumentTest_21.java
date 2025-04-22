    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAllowLongerDocumentID() {
        String id = "thisIsCertainlyATooLongDocumentIdToStoreInCouchbaseWhoWouldUseItLikeThisAnyway?"
            + "thisIsCertainlyATooLongDocumentIdToStoreInCouchbaseWhoWouldUseItLikeThisAnyway?"
            + "thisIsCertainlyATooLongDocumentIdToStoreInCouchbaseWhoWouldUseItLikeThisAnyway?"
            + "thisIsCertainlyATooLongDocumentIdToStoreInCouchbaseWhoWouldUseItLikeThisAnyway?";
        JsonDocument.create(id);
    }
