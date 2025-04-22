    public AsyncLookupInBuilder getCount(String... paths) {
        if (paths == null || paths.length == 0) {
            throw new IllegalArgumentException("Path is mandatory for subdoc get count");
        }
        for (String path : paths) {
            if (StringUtil.isNullOrEmpty(path)) {
                throw new IllegalArgumentException("Path is mandatory for subdoc get count");
            }
            this.specs.add(new LookupSpec(Lookup.GET_COUNT, path));
        }
        return this;
    }

    public AsyncLookupInBuilder getCount(String path, SubdocOptionsBuilder optionsBuilder) {
        if (path == null) {
            throw new IllegalArgumentException("Path is mandatory for subdoc get count");
        }
        if (optionsBuilder.createParents()) {
            throw new IllegalArgumentException("Options createParents are not supported for lookup");
        }
        this.specs.add(new LookupSpec(Lookup.GET_COUNT, path, optionsBuilder));
        return this;
    }


    public AsyncLookupInBuilder getCount(Iterable<String> paths, SubdocOptionsBuilder optionsBuilder) {
        if (paths == null) {
            throw new IllegalArgumentException("Path is mandatory for subdoc get count");
        }
        if (optionsBuilder.createParents()) {
            throw new IllegalArgumentException("Options createParents are not supported for lookup");
        }
        for (String path : paths) {
            if (StringUtil.isNullOrEmpty(path)) {
                throw new IllegalArgumentException("Path is mandatory for subdoc get count");
            }
            this.specs.add(new LookupSpec(Lookup.GET_COUNT, path, optionsBuilder));
        }
        return this;
    }

