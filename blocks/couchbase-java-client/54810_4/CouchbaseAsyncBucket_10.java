    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, PersistTo persistTo) {
        return counter(id, delta, persistTo, ReplicateTo.NONE);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, ReplicateTo replicateTo) {
        return counter(id, delta, PersistTo.NONE, replicateTo);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, PersistTo persistTo) {
        return counter(id, delta, initial, persistTo, ReplicateTo.NONE);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, ReplicateTo replicateTo) {
        return counter(id, delta, initial, PersistTo.NONE, replicateTo);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, PersistTo persistTo) {
        return counter(id, delta, initial, expiry, persistTo, ReplicateTo.NONE);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, ReplicateTo replicateTo) {
        return counter(id, delta, initial, expiry, PersistTo.NONE, replicateTo);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, initial, 0, persistTo, replicateTo);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, PersistTo persistTo, ReplicateTo replicateTo) {
        return counter(id, delta, 0, COUNTER_NOT_EXISTS_EXPIRY, persistTo, replicateTo);
    }

    @Override
    public Observable<JsonLongDocument> counter(String id, long delta, long initial, int expiry, final PersistTo persistTo, final ReplicateTo replicateTo) {

        Observable<JsonLongDocument> counterResult = counter(id, delta, initial, expiry);

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return counterResult;
        }

        return counterResult.flatMap(new Func1<JsonLongDocument, Observable<JsonLongDocument>>() {
            @Override
            public Observable<JsonLongDocument> call(final JsonLongDocument doc) {
                return Observe
                    .call(core, bucket, doc.id(), doc.cas(), false, doc.mutationToken(),
                        persistTo.value(), replicateTo.value(),
                        environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, JsonLongDocument>() {
                        @Override
                        public JsonLongDocument call(Boolean aBoolean) {
                            return doc;
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<? extends JsonLongDocument>>() {
                        @Override
                        public Observable<? extends JsonLongDocument> call(Throwable throwable) {
                            return Observable.error(new DurabilityException(
                                "Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    });
            }
        });
    }

    @Override
    public <D extends Document<?>> Observable<D> append(D document, PersistTo persistTo) {
        return append(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> Observable<D> append(D document, ReplicateTo replicateTo) {
        return append(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> Observable<D> append(D document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        Observable<D> appendResult = append(document);

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return appendResult;
        }

        return appendResult.flatMap(new Func1<D, Observable<D>>() {
            @Override
            public Observable<D> call(final D doc) {
                return Observe
                    .call(core, bucket, doc.id(), doc.cas(), false, doc.mutationToken(), persistTo.value(), replicateTo.value(),
                        environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, D>() {
                        @Override
                        public D call(Boolean aBoolean) {
                            return doc;
                        }
                    }).onErrorResumeNext(new Func1<Throwable, Observable<? extends D>>() {
                        @Override
                        public Observable<? extends D> call(Throwable throwable) {
                            return Observable.error(new DurabilityException(
                                "Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    });
            }
        });
    }

    @Override
    public <D extends Document<?>> Observable<D> prepend(D document, PersistTo persistTo) {
        return prepend(document, persistTo, ReplicateTo.NONE);
    }

    @Override
    public <D extends Document<?>> Observable<D> prepend(D document, ReplicateTo replicateTo) {
        return prepend(document, PersistTo.NONE, replicateTo);
    }

    @Override
    public <D extends Document<?>> Observable<D> prepend(D document, final PersistTo persistTo, final ReplicateTo replicateTo) {
        Observable<D> prependResult = prepend(document);

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return prependResult;
        }

        return prependResult.flatMap(new Func1<D, Observable<D>>() {
            @Override
            public Observable<D> call(final D doc) {
                return Observe
                    .call(core, bucket, doc.id(), doc.cas(), false, doc.mutationToken(), persistTo.value(), replicateTo.value(),
                        environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, D>() {
                        @Override
                        public D call(Boolean aBoolean) {
                            return doc;
                        }
                    }).onErrorResumeNext(new Func1<Throwable, Observable<? extends D>>() {
                        @Override
                        public Observable<? extends D> call(Throwable throwable) {
                            return Observable.error(new DurabilityException(
                                "Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    });
            }
        });
    }

