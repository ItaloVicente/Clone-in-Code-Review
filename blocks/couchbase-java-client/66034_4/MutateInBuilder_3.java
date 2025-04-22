    public DocumentFragment<Mutation> execute(PersistTo persistTo, ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBuilder.execute(persistTo, replicateTo), timeout, timeUnit);
    }

    public DocumentFragment<Mutation> execute(PersistTo persistTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBuilder.execute(persistTo), timeout, timeUnit);
    }

    public DocumentFragment<Mutation> execute(ReplicateTo replicateTo, long timeout, TimeUnit timeUnit) {
        return Blocking.blockForSingle(asyncBuilder.execute(replicateTo), timeout, timeUnit);
    }

