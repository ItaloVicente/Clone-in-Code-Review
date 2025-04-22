
    @Override
    public UpsertDesignDocumentResponse touch() {
        content.touch();
        return this;
    }

    @Override
    public UpsertDesignDocumentResponse touch(Object hint) {
        content.touch(hint);
        return this;
    }
