
    public ViewQuery withDocuments() {
        return withDocuments(true);
    }

    public ViewQuery withDocuments(boolean withDocs) {
        this.withDocs = withDocs;
        return this;
    }

