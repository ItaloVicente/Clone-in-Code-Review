    @Override
    public <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType) {
        return getIn(id, path, fragmentType, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> getIn(String id, String path, Class<T> fragmentType, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.getIn(id, path, fragmentType).singleOrDefault(null)
                , timeout, timeUnit);
    }

    @Override
    public boolean existsIn(String id, String path) {
        return existsIn(id, path, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public boolean existsIn(String id, String path, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.existsIn(id, path).singleOrDefault(null)
                , timeout, timeUnit);
    }

    @Override
    public <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo) {
        return upsertIn(fragment, createParents, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> upsertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.upsertIn(fragment, createParents, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public List<DocumentFragment<Object>> lookupIn(String id, LookupSpec... lookupSpecs) {
        return lookupIn(id, kvTimeout, TIMEOUT_UNIT, lookupSpecs);
    }

    @Override
    public List<DocumentFragment<Object>> lookupIn(String id, long timeout, TimeUnit timeUnit, LookupSpec... lookupSpecs) {
        return Blocking.blockForSingle(asyncBucket.lookupIn(id, lookupSpecs).toList(), timeout, timeUnit);
    }



