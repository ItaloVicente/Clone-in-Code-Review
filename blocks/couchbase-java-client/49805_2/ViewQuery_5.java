    public ViewQuery includeDocs() {
        return includeDocs(true, JsonDocument.class);
    }

    public ViewQuery includeDocs(Class<? extends Document<?>> target) {
        return includeDocs(true, target);
    }

    public ViewQuery includeDocs(boolean includeDocs) {
        return includeDocs(includeDocs, JsonDocument.class);
    }

    public ViewQuery includeDocs(boolean includeDocs, Class<? extends Document<?>> target) {
        this.includeDocs = includeDocs;
        this.includeDocsTarget = target;
        return this;
    }

