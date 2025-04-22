    @Override
    public <V> Observable<V> mapGet(final String docId, final String key, Class<V> valueType) {
        final Func1<DocumentFragment<Lookup>, V> mapResult = new Func1<DocumentFragment<Lookup>, V>() {
            @Override
            public V call(DocumentFragment<Lookup> documentFragment) {
                ResponseStatus status = documentFragment.status(0);
                if (status == ResponseStatus.SUCCESS) {
                    return (V) documentFragment.content(0);
                } else {
                    if (status == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                        throw new NoSuchElementException();
                    } else {
                        throw new CouchbaseException(status.toString());
                    }
                }
            }
        };
        return Observable.defer(new Func0<Observable<V>>() {
            @Override
            public Observable<V> call() {
                return lookupIn(docId).get(key)
                        .execute()
                        .map(mapResult);
            }
        });
    }

    @Override
    public <V> Observable<Boolean> mapAdd(String docId, String key, V value) {
        return mapAdd(docId, key, value, MutationOptionBuilder.build());
    }

    @Override
    public <V> Observable<Boolean> mapAdd(final String docId,
                                          final String key,
                                          final V value,
                                          final MutationOptionBuilder mutationOptionBuilder) {
        final Func1<DocumentFragment<Mutation>, Boolean> mapResult = new Func1<DocumentFragment<Mutation>, Boolean>() {
            @Override
            public Boolean call(DocumentFragment<Mutation> documentFragment) {
                ResponseStatus status = documentFragment.status(0);
                if (status == ResponseStatus.SUCCESS) {
                    return true;
                } else {
                    if (status == ResponseStatus.TOO_BIG) {
                        throw new IllegalStateException("Map full");
                    } else {
                        throw new CouchbaseException(status.toString());
                    }
                }
            }
        };
        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return subdocAdd(docId, key, value, mutationOptionBuilder.getCAS(), mutationOptionBuilder.getPersistTo(),
                        mutationOptionBuilder.getReplicateTo(), mutationOptionBuilder.getExpiry())
                        .map(mapResult);
            }
        });
    }

    private <V> Observable<JsonDocument> createDocument(String docId, String key, V value) {
        return upsert(JsonDocument.create(docId, JsonObject.create().put(key, value)));
    }

    private <V> Observable<DocumentFragment<Mutation>> subdocAdd(final String docId,
                                                                 final String key,
                                                                 final V value,
                                                                 final long cas,
                                                                 final PersistTo persistTo,
                                                                 final ReplicateTo replicateTo,
                                                                 final int expiry) {

        final Func1<JsonDocument, DocumentFragment<Mutation>> mapFullDocResultToSubdoc = new
                Func1<JsonDocument, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(JsonDocument document) {
                        List<SubdocOperationResult<Mutation>> list;
                        list = new ArrayList<SubdocOperationResult<Mutation>>();
                        list.add(SubdocOperationResult.createResult(key, Mutation.DICT_UPSERT,
                                ResponseStatus.SUCCESS, value));
                        return new DocumentFragment<Mutation>(document.id(), document.cas(),
                                document.mutationToken(),
                                list);
                    }
                };

        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryAddIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return subdocAdd(docId, key, value, cas, persistTo, replicateTo,
                                    expiry);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };

        return mutateIn(docId).upsert(key, value, false)
                .withCas(cas)
                .withDurability(persistTo, replicateTo)
                .withExpiry(expiry)
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return createDocument(docId, key, value)
                                    .map(mapFullDocResultToSubdoc)
                                    .onErrorResumeNext(retryAddIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list;
                                list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.DICT_UPSERT, status, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0, null, list));
                            } else if (throwable instanceof RequestTooBigException) {
                                List<SubdocOperationResult<Mutation>> list;
                                list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.DICT_UPSERT, ResponseStatus.TOO_BIG, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0, null, list));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public Observable<Boolean> mapRemove(String docId, String key) {
        return mapRemove(docId, key, MutationOptionBuilder.build());
    }

    @Override
    public Observable<Boolean> mapRemove(final String docId,
                                         final String key,
                                         final MutationOptionBuilder mutationOptionBuilder) {
        final Func1<DocumentFragment<Mutation>, Boolean> mapResult = new Func1<DocumentFragment<Mutation>, Boolean>() {
            @Override
            public Boolean call(DocumentFragment<Mutation> documentFragment) {
                ResponseStatus status = documentFragment.status(0);
                if (status == ResponseStatus.SUCCESS) {
                    return true;
                } else {
                    return false;
                }
            }
        };

        final Func1<Throwable, Observable<? extends Boolean>> handleSubdocException = new
                Func1<Throwable, Observable<? extends Boolean>>() {
                    @Override
                    public Observable<? extends Boolean> call(Throwable throwable) {
                        ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                        if (status == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                            return Observable.just(true);
                        } else {
                            throw new CouchbaseException(status.toString());
                        }
                    }
                };

        return Observable.defer(new Func0<Observable<Boolean>>() {
            @Override
            public Observable<Boolean> call() {
                return mutateIn(docId).remove(key)
                        .withCas(mutationOptionBuilder.getCAS())
                        .withDurability(mutationOptionBuilder.getPersistTo(), mutationOptionBuilder.getReplicateTo())
                        .withExpiry(mutationOptionBuilder.getExpiry())
                        .execute()
                        .map(mapResult)
                        .onErrorResumeNext(handleSubdocException);
            }
        });
    }

    @Override
    public Observable<Integer> mapSize(final String docId) {
        return Observable.defer(new Func0<Observable<Integer>>() {
            @Override
            public Observable<Integer> call() {
                return get(docId, JsonDocument.class)
                        .map(new Func1<JsonDocument, Integer>() {
                            @Override
                            public Integer call(JsonDocument document) {
                                return document.content().size();
                            }
                        });
            }
        });
    }

