    public Observable<DocumentFragment<Mutation>> execute(final PersistTo persistTo, final ReplicateTo replicateTo) {
        Observable<DocumentFragment<Mutation>> mutationResult = execute();

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return mutationResult;
        }

        return mutationResult.flatMap(new Func1<DocumentFragment<Mutation>, Observable<DocumentFragment<Mutation>>>() {
            @Override
            public Observable<DocumentFragment<Mutation>> call(final DocumentFragment<Mutation> fragment) {
                return Observe.call(core, bucketName, docId, fragment.cas(), false, fragment.mutationToken(),
                    persistTo.value(), replicateTo.value(),
                    environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, DocumentFragment<Mutation>>() {
                        @Override
                        public DocumentFragment<Mutation> call(Boolean aBoolean) {
                            return fragment;
                        }
                    }).onErrorResumeNext(new Func1<Throwable, Observable<DocumentFragment<Mutation>>>() {
                        @Override
                        public Observable<DocumentFragment<Mutation>> call(Throwable throwable) {
                            return Observable.error(new DurabilityException(
                                "Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    });
            }
        });


    }

    public Observable<DocumentFragment<Mutation>> execute(PersistTo persistTo) {
        return execute(persistTo, ReplicateTo.NONE);
    }

    public Observable<DocumentFragment<Mutation>> execute(ReplicateTo replicateTo) {
        return execute(PersistTo.NONE, replicateTo);
    }


