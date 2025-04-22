    @InterfaceStability.Experimental
    public AsyncMutateInBuilder insertDocument(boolean insertDocument) {
        if (this.createDocument && insertDocument) {
            throw new IllegalArgumentException("Cannot set both createDocument and insertDocument to true");
        }
        this.insertDocument = insertDocument;
        return this;
    }

