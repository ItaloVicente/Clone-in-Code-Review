    public AsyncLookupInBuilder exists(String path, SubdocOptionsBuilder optionsBuilder) {
        if (path == null) {
            throw new IllegalArgumentException("Path is mandatory for subdoc exists");
        }
        if (optionsBuilder.createParents()) {
            throw new IllegalArgumentException("Options createParents are not supported for lookup");
        }
        this.specs.add(new LookupSpec(Lookup.EXIST, path, optionsBuilder));
        return this;
    }

    public AsyncLookupInBuilder exists(Iterable<String> paths, SubdocOptionsBuilder optionsBuilder) {
        if (paths == null) {
            throw new IllegalArgumentException("Path is mandatory for subdoc exists");
        }
        if (optionsBuilder.createParents()) {
            throw new IllegalArgumentException("Options createParents are not supported for lookup");
        }
        for (String path : paths) {
            if (StringUtil.isNullOrEmpty(path)) {
                throw new IllegalArgumentException("Path is mandatory for subdoc exists");
            }
            this.specs.add(new LookupSpec(Lookup.EXIST, path, optionsBuilder));
        }
        return this;
    }

