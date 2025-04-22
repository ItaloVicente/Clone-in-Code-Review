    @Override
    public Observable<DocumentFragment<Lookup>> retrieveIn(String docId, String... paths) {
        if (paths == null || paths.length == 0) {
            throw new IllegalArgumentException("RetrieveIn should have at least one path");
        }
        AsyncLookupInBuilder builder = lookupIn(docId);
        for (String path : paths) {
            builder.get(path);
        }
        return builder.doLookup();
    }

    @Override
    public Observable<DocumentFragment<Lookup>> retrieveIn(String docId, Collection<String> paths) {
        if (paths == null || paths.size() == 0) {
            throw new IllegalArgumentException("RetrieveIn should have at least one path");
        }
        AsyncLookupInBuilder builder = lookupIn(docId);
        for (String path : paths) {
            builder.get(path);
        }
        return builder.doLookup();
    }

