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
