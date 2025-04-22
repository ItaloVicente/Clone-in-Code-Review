        this.upsertDocument = createDocument;
    }

    @Override
    public boolean upsertDocument() { return this.upsertDocument; }

    public void upsertDocument(boolean upsertDocument) {
        if (this.insertDocument && upsertDocument) {
            throw new IllegalArgumentException("Invalid to set upsertDocument to true along with insertDocument");
        }
        this.upsertDocument = upsertDocument; }
