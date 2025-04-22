    public LookupInBuilder exists(String path, SubdocOptionsBuilder optionsBuilder) {
        this.async.exists(path, optionsBuilder);
        return this;
    }


    public LookupInBuilder exists(Iterable<String> paths, SubdocOptionsBuilder optionsBuilder) {
        this.async.exists(paths, optionsBuilder);
        return this;
    }

