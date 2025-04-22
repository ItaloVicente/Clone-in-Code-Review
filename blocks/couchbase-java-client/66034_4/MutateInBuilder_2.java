    public DocumentFragment<Mutation> execute(PersistTo persistTo, ReplicateTo replicateTo) {
        return execute(persistTo, replicateTo, defaultTimeout, defaultTimeUnit);
    }

    public DocumentFragment<Mutation> execute(PersistTo persistTo) {
        return execute(persistTo, defaultTimeout, defaultTimeUnit);
    }

    public DocumentFragment<Mutation> execute(ReplicateTo replicateTo) {
        return execute(replicateTo, defaultTimeout, defaultTimeUnit);
    }


