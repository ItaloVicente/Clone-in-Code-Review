    @Override
    public DocumentFragment<Lookup> retrieveIn(String docId, String... paths) {
        return retrieveIn(docId, kvTimeout, TIMEOUT_UNIT, paths);
    }

    @Override
    public DocumentFragment<Lookup> retrieveIn(String docId, long timeout, TimeUnit timeUnit, String... paths) {
        return Blocking.blockForSingle(asyncBucket.retrieveIn(docId, paths), timeout, timeUnit);
    }

