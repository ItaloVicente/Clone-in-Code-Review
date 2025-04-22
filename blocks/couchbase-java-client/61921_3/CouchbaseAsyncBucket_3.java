    @Override
    public Observable<DocumentFragment<Lookup>> retrieveIn(String docId, String... paths) {
        if (paths == null || paths.length == 0) {
            throw new IllegalArgumentException("RetrieveIn vararg should have at least one path");
        }
        AsyncLookupInBuilder builder = lookupIn(docId);
        for (String path : paths) {
            builder.get(path);
        }
        return builder.doLookup();
    }

    @Override
    public Observable<DocumentFragment<Lookup>> retrieveIn(String docId, List<String> paths) {
        if (paths == null || paths.isEmpty()) {
            throw new IllegalArgumentException("RetrieveIn list should have at least one path");
        }

        AsyncLookupInBuilder builder = lookupIn(docId);
        for (String path : paths) {
            builder.get(path);
        }
        return builder.doLookup();
    }

