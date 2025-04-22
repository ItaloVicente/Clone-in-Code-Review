
    public AsyncLookupInBuilder get(String path, SubdocOptionsBuilder optionsBuilder) {
        if (path == null) {
            throw new IllegalArgumentException("Path is mandatory for subdoc get");
        }
        if (optionsBuilder.createParents()) {
            throw new IllegalArgumentException("Options createParents are not supported for lookup");
        }
        this.specs.add(new LookupSpec(Lookup.GET, path, optionsBuilder));
        return this;
    }


    public AsyncLookupInBuilder get(Iterable<String> paths, SubdocOptionsBuilder optionsBuilder) {
        if (paths == null) {
            throw new IllegalArgumentException("Path is mandatory for subdoc get");
        }
        if (optionsBuilder.createParents()) {
            throw new IllegalArgumentException("Options createParents are not supported for lookup");
        }
        for (String path : paths) {
            if (StringUtil.isNullOrEmpty(path)) {
                throw new IllegalArgumentException("Path is mandatory for subdoc get");
            }
            this.specs.add(new LookupSpec(Lookup.GET, path, optionsBuilder));
        }
        return this;
    }

