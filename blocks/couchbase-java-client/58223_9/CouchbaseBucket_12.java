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
    public <T> DocumentFragment<T> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo) {
        return insertIn(fragment, createParents, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> insertIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.insertIn(fragment, createParents, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> DocumentFragment<T> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo,
            ReplicateTo replicateTo) {
        return replaceIn(fragment, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> replaceIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo,
            long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.replaceIn(fragment, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> DocumentFragment<T> extendIn(DocumentFragment<T> fragment, ExtendDirection direction,
            boolean createParents, PersistTo persistTo, ReplicateTo replicateTo) {
        return extendIn(fragment, direction, createParents, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> extendIn(DocumentFragment<T> fragment, ExtendDirection direction,
            boolean createParents, PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.extendIn(fragment, direction, createParents, persistTo, replicateTo),
                timeout, timeUnit);
    }

    @Override
    public <T> DocumentFragment<T> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo,
            ReplicateTo replicateTo) {
        return arrayInsertIn(fragment, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> arrayInsertIn(DocumentFragment<T> fragment, PersistTo persistTo,
            ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.arrayInsertIn(fragment, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> DocumentFragment<T> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo) {
        return addUniqueIn(fragment, createParents, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> addUniqueIn(DocumentFragment<T> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.addUniqueIn(fragment, createParents, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public <T> DocumentFragment<T> removeIn(DocumentFragment<T> fragment, PersistTo persistTo,
            ReplicateTo replicateTo) {
        return removeIn(fragment, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public <T> DocumentFragment<T> removeIn(DocumentFragment<T> fragment, PersistTo persistTo, ReplicateTo replicateTo,
            long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.removeIn(fragment, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public DocumentFragment<Long> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo) {
        return counterIn(fragment, createParents, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT);
    }

    @Override
    public DocumentFragment<Long> counterIn(DocumentFragment<Long> fragment, boolean createParents, PersistTo persistTo,
            ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBucket.counterIn(fragment, createParents, persistTo, replicateTo), timeout, timeUnit);
    }

    @Override
    public MultiLookupResult lookupIn(String id, LookupSpec... lookupSpecs) {
        return lookupIn(id, kvTimeout, TIMEOUT_UNIT, lookupSpecs);
    }

    @Override
    public MultiLookupResult lookupIn(String id, long timeout, TimeUnit timeUnit, LookupSpec... lookupSpecs) {
        return Blocking.blockForSingle(asyncBucket.lookupIn(id, lookupSpecs), timeout, timeUnit);
    }

    @Override
    public MultiMutationResult mutateIn(JsonDocument doc, PersistTo persistTo, ReplicateTo replicateTo,
            MutationSpec... mutationSpecs) {
        return mutateIn(doc, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT, mutationSpecs);
    }

    @Override
    public MultiMutationResult mutateIn(JsonDocument doc, PersistTo persistTo, ReplicateTo replicateTo, long timeout,
            TimeUnit timeUnit, MutationSpec... mutationSpecs) {
        return Blocking.blockForSingle(asyncBucket.mutateIn(doc, persistTo, replicateTo, mutationSpecs), timeout, timeUnit);
    }

    @Override
    public MultiMutationResult mutateIn(String docId, PersistTo persistTo, ReplicateTo replicateTo,
            MutationSpec... mutationSpecs) {
        return mutateIn(docId, persistTo, replicateTo, kvTimeout, TIMEOUT_UNIT, mutationSpecs);
    }

    @Override
    public MultiMutationResult mutateIn(String docId, PersistTo persistTo, ReplicateTo replicateTo, long timeout,
            TimeUnit timeUnit, MutationSpec... mutationSpecs) {
        return Blocking.blockForSingle(asyncBucket.mutateIn(docId, persistTo, replicateTo, mutationSpecs), timeout, timeUnit);
    }



