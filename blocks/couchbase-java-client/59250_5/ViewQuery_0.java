    public ViewQuery includeDocsOrdered() {
        return includeDocsOrdered(true, JsonDocument.class);
    }

    public ViewQuery includeDocsOrdered(Class<? extends Document<?>> target) {
        return includeDocsOrdered(true, target);
    }

    public ViewQuery includeDocsOrdered(boolean includeDocs) {
        return includeDocsOrdered(includeDocs, JsonDocument.class);
    }

    public ViewQuery includeDocsOrdered(boolean includeDocs, Class<? extends Document<?>> target) {
        this.includeDocs = includeDocs;
        this.retainOrder = includeDocs; //deactivate if includeDocs is deactivated
        this.includeDocsTarget = target;
        return this;
    }

