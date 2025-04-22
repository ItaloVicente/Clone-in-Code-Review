        return upsertDocument(createDocument);
    }

    public AsyncMutateInBuilder upsertDocument(boolean upsertDocument) {
        if (this.insertDocument && upsertDocument) {
            throw new IllegalArgumentException("Cannot set both upsertDocument and insertDocument to true");
