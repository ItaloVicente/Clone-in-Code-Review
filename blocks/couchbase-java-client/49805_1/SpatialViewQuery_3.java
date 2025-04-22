    public SpatialViewQuery includeDocs() {
        return includeDocs(true, JsonDocument.class);
    }

    public SpatialViewQuery includeDocs(Class<? extends Document<?>> target) {
        return includeDocs(true, target);
    }

    public SpatialViewQuery includeDocs(boolean includeDocs) {
        return includeDocs(includeDocs, JsonDocument.class);
    }

    public SpatialViewQuery includeDocs(boolean includeDocs, Class<? extends Document<?>> target) {
        this.includeDocs = includeDocs;
        this.includeDocsTarget = target;
        return this;
    }

