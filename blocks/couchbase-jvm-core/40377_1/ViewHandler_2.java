    private CouchbaseResponse handleUpsertDesignDocumentResponse(final UpsertDesignDocumentRequest request) {
        ResponseStatus status = statusFromCode(responseHeader.getStatus().code());
        return new UpsertDesignDocumentResponse(status, responseContent.copy(), request);
    }

    private CouchbaseResponse handleRemoveDesignDocumentResponse(final RemoveDesignDocumentRequest request) {
        ResponseStatus status = statusFromCode(responseHeader.getStatus().code());
        return new RemoveDesignDocumentResponse(status, responseContent.copy(), request);
    }

