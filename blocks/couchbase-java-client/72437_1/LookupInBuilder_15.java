    public LookupInBuilder get(String path, SubdocOptionsBuilder optionsBuilder) {
        this.async.get(path, optionsBuilder);
        return this;
    }

    public LookupInBuilder get(Iterable<String> paths, SubdocOptionsBuilder optionsBuilder) {
        this.async.get(paths, optionsBuilder);
        return this;
    }

