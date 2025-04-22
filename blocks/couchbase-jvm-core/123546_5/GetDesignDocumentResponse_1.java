
    @Override
    public GetDesignDocumentResponse touch() {
        content.touch();
        return this;
    }

    @Override
    public GetDesignDocumentResponse touch(Object hint) {
        content.touch(hint);
        return this;
    }
