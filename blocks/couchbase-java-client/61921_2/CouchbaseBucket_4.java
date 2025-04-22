    @Override
    public DocumentFragment<Lookup> retrieveIn(String docId, String... paths) {
        return Blocking.blockForSingle(asyncBucket.retrieveIn(docId, paths), kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public DocumentFragment<Lookup> retrieveIn(String docId, Collection<String> paths) {
        return retrieveIn(docId, paths, kvTimeout, TIMEOUT_UNIT);
    }
    @Override
    public DocumentFragment<Lookup> retrieveIn(String docId, Collection<String> paths, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.retrieveIn(docId, paths), timeout, timeUnit);
    }

