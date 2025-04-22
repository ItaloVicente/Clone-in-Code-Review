    public LookupInBuilder getCount(String... paths) {
        this.async.getCount(paths);
        return this;
    }

    public LookupInBuilder getCount(String path, SubdocOptionsBuilder optionsBuilder) {
        this.async.getCount(path, optionsBuilder);
        return this;
    }

    public LookupInBuilder getCount(Iterable<String> paths, SubdocOptionsBuilder optionsBuilder) {
        this.async.getCount(paths, optionsBuilder);
        return this;
    }

