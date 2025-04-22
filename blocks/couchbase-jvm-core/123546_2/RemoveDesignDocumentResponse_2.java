
    @Override
    public RemoveDesignDocumentResponse touch() {
        content.touch();
        return this;
    }

    @Override
    public RemoveDesignDocumentResponse touch(Object hint) {
        content.touch(hint);
        return this;
    }
