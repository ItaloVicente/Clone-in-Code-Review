        return this.upsertDocument;
    }

    public SubMultiMutationDocOptionsBuilder upsertDocument(boolean upsertDocument) {
        if (this.insertDocument && upsertDocument) {
            throw new IllegalArgumentException("Invalid to set upsertDocument to true along with insertDocument");
        }
        this.upsertDocument = upsertDocument;
        return this;
    }

    public boolean upsertDocument() {
        return this.upsertDocument;
