    @Override
    public <V> Observable<V> mapGet(final String docId, final String key, Class<V> valueType) {
        final Func1<DocumentFragment<Lookup>, V> mapResult = new Func1<DocumentFragment<Lookup>, V>() {
            @Override
            public V call(DocumentFragment<Lookup> documentFragment) {
                ResponseStatus status = documentFragment.status(0);
                if (status == ResponseStatus.SUCCESS) {
                    return (V) documentFragment.content(0);
                } else {
                    throw new CouchbaseException(status.toString());
                }
            }
        };
        return lookupIn(docId).get(key)
                .execute()
                .map(mapResult);
    }

    private Func1<DocumentFragment<Mutation>, Boolean> getMapResultFnForSubdocMutationToBoolean() {
        return new Func1<DocumentFragment<Mutation>, Boolean>() {
            @Override
            public Boolean call(DocumentFragment<Mutation> documentFragment) {
                ResponseStatus status = documentFragment.status(0);
                if (status == ResponseStatus.SUCCESS) {
                    return true;
                } else {
                    throw new CouchbaseException(status.toString());
                }
            }
        };
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
        return mapSubdocAdd(docId, key, value, mutationOptionBuilder)
                .map(getMapResultFnForSubdocMutationToBoolean());
    }


    private <V> Observable<DocumentFragment<Mutation>> mapSubdocAdd(final String docId,
                                                                    final String key,
                                                                    final V value,
                                                                    final MutationOptionBuilder mutationOptionBuilder) {

        final Func1<JsonDocument, DocumentFragment<Mutation>> mapFullDocResultToSubdoc = new
                Func1<JsonDocument, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(JsonDocument document) {
                        List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                        list.add(SubdocOperationResult.createResult(key, Mutation.DICT_UPSERT, ResponseStatus.SUCCESS, value));
                        return new DocumentFragment<Mutation>(document.id(), document.cas(), document.mutationToken(), list);
                    }
                };

        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryAddIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return mapSubdocAdd(docId, key, value, mutationOptionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };

        return mutateIn(docId).upsert(key, value, false)
                .withCas(mutationOptionBuilder.getCAS())
                .withDurability(mutationOptionBuilder.getPersistTo(), mutationOptionBuilder.getReplicateTo())
                .withExpiry(mutationOptionBuilder.getExpiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonDocument.create(docId, mutationOptionBuilder.getExpiry(),
                                    JsonObject.create().put(key, value)),
                                    mutationOptionBuilder.getPersistTo(),
                                    mutationOptionBuilder.getReplicateTo())
                                    .map(mapFullDocResultToSubdoc)
                                    .onErrorResumeNext(retryAddIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.DICT_UPSERT, status, null));
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

        return mutateIn(docId).remove(key)
                .withCas(mutationOptionBuilder.getCAS())
                .withDurability(mutationOptionBuilder.getPersistTo(), mutationOptionBuilder.getReplicateTo())
                .withExpiry(mutationOptionBuilder.getExpiry())
                .execute()
                .map(getMapResultFnForSubdocMutationToBoolean())
                .onErrorResumeNext(handleSubdocException);
    }

    @Override
    public Observable<Integer> mapSize(final String docId) {
        return get(docId, JsonDocument.class)
                .toList()
                .map(new Func1<List<JsonDocument>, Integer>() {
                    @Override
                    public Integer call(List<JsonDocument> documents) {
                        if (documents.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        } else {
                            return documents.get(0).content().size();
                        }
                    }
                });
    }

    @Override
    public <E> Observable<E> listGet(String docId, int index, Class<E> elementType) {
        final Func1<DocumentFragment<Lookup>, E> mapResult = new
                Func1<DocumentFragment<Lookup>, E>() {
                    @Override
                    public E call(DocumentFragment<Lookup> documentFragment) {
                        ResponseStatus status = documentFragment.status(0);
                        if (status == ResponseStatus.SUCCESS) {
                            return (E) documentFragment.content(0);
                        } else {
                            throw new CouchbaseException(status.toString());
                        }
                    }
                };

        return lookupIn(docId).get("[" + index + "]")
                .execute()
                .map(mapResult);
    }

    @Override
    public <E> Observable<Boolean> listPush(String docId, E element) {
        return listPush(docId, element, MutationOptionBuilder.build());
    }


    @Override
    public <E> Observable<Boolean> listPush(final String docId, final E element, final MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocPushLast(docId, element, mutationOptionBuilder)
                .map(getMapResultFnForSubdocMutationToBoolean());
    }


    private <E> Observable<DocumentFragment<Mutation>> listSubdocPushLast(final String docId,
                                                                          final E element,
                                                                          final MutationOptionBuilder optionBuilder) {
        final Func1<JsonArrayDocument, DocumentFragment<Mutation>> mapFullDocResulttoSubdoc = new
                Func1<JsonArrayDocument, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(JsonArrayDocument document) {
                        List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                        list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_PUSH_LAST, ResponseStatus.SUCCESS, element));
                        return new DocumentFragment<Mutation>(document.id(), document.cas(), document.mutationToken(), list);
                    }
                };
        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return listSubdocPushLast(docId, element, optionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };
        return mutateIn(docId).arrayAppend("", element, false)
                .withCas(optionBuilder.getCAS())
                .withDurability(optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                .withExpiry(optionBuilder.getExpiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, optionBuilder.getExpiry(),
                                    JsonArray.create().add(element)),
                                    optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                                    .map(mapFullDocResulttoSubdoc)
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_PUSH_LAST, status, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0, null, list));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public Observable<Boolean> listRemove(String docId, int index) {
        return listRemove(docId, index, MutationOptionBuilder.build());
    }

    @Override
    public Observable<Boolean> listRemove(final String docId, final int index, final MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocRemove(docId, index, mutationOptionBuilder)
                .map(getMapResultFnForSubdocMutationToBoolean());
    }

    private Observable<DocumentFragment<Mutation>> listSubdocRemove(final String docId,
                                                                    final int index,
                                                                    final MutationOptionBuilder mutationOptionBuilder) {
        return mutateIn(docId).remove("[" + index + "]")
                .withCas(mutationOptionBuilder.getCAS())
                .withExpiry(mutationOptionBuilder.getExpiry())
                .withDurability(mutationOptionBuilder.getPersistTo(), mutationOptionBuilder.getReplicateTo())
                .execute();
    }

    @Override
    public <E> Observable<Boolean> listSet(String docId, int index, E element) {
        return listSet(docId, index, element, MutationOptionBuilder.build());
    }

    @Override
    public <E> Observable<Boolean> listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocInsert(docId, index, element, mutationOptionBuilder)
                .map(getMapResultFnForSubdocMutationToBoolean());
    }

    private <E> Observable<DocumentFragment<Mutation>> listSubdocInsert(final String docId,
                                                                        final int index,
                                                                        final E element,
                                                                        final MutationOptionBuilder optionBuilder) {
        final Func1<JsonArrayDocument, DocumentFragment<Mutation>> mapFullDocResultToSubdoc = new
                Func1<JsonArrayDocument, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(JsonArrayDocument document) {
                        List<SubdocOperationResult<Mutation>> list;
                        list = new ArrayList<SubdocOperationResult<Mutation>>();
                        list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_INSERT, ResponseStatus.SUCCESS, element));
                        return new DocumentFragment<Mutation>(document.id(), document.cas(), document.mutationToken(), list);
                    }
                };

        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return listSubdocInsert(docId, index, element, optionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };
        return mutateIn(docId).arrayInsert("[" + index + "]", element)
                .withCas(optionBuilder.getCAS())
                .withDurability(optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                .withExpiry(optionBuilder.getExpiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, optionBuilder.getExpiry(),
                                    JsonArray.create().add(element)),
                                    optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                                    .map(mapFullDocResultToSubdoc)
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_INSERT, status, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0, null, list));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public <E> Observable<Boolean> listShift(String docId, E element) {
        return listShift(docId, element, MutationOptionBuilder.build());
    }

    @Override
    public <E> Observable<Boolean> listShift(final String docId, final E element, final MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocPushFirst(docId, element, mutationOptionBuilder)
                .map(getMapResultFnForSubdocMutationToBoolean());
    }


    private <E> Observable<DocumentFragment<Mutation>> listSubdocPushFirst(final String docId,
                                                                           final E element,
                                                                           final MutationOptionBuilder optionBuilder) {

        final Func1<JsonArrayDocument, DocumentFragment<Mutation>> mapFullDocResultToSubDoc = new
                Func1<JsonArrayDocument, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(JsonArrayDocument document) {
                        List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                        list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_PUSH_FIRST, ResponseStatus.SUCCESS, element));
                        return new DocumentFragment<Mutation>(document.id(), document.cas(), document.mutationToken(), list);
                    }
                };
        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        return listSubdocPushFirst(docId, element, optionBuilder);
                    }
                };

        return mutateIn(docId).arrayPrepend("", element, false)
                .withCas(optionBuilder.getCAS())
                .withDurability(optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                .withExpiry(optionBuilder.getExpiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, optionBuilder.getExpiry(),
                                    JsonArray.create().add(element)),
                                    optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                                    .map(mapFullDocResultToSubDoc)
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list;
                                list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_PUSH_FIRST, status, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0, null, list));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public Observable<Integer> listSize(String docId) {
        return get(docId, JsonArrayDocument.class)
                .toList()
                .map(new Func1<List<JsonArrayDocument>, Integer>() {
                    @Override
                    public Integer call(List<JsonArrayDocument> documents) {
                        if (documents.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        }
                        return documents.get(0).content().size();
                    }
                });
    }

    @Override
    public <E> Observable<Boolean> setAdd(String docId, E element) {
        return setAdd(docId, element, MutationOptionBuilder.build());
    }

    @Override
    public <E> Observable<Boolean> setAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        final Func1<DocumentFragment<Mutation>, Boolean> mapResult = new Func1<DocumentFragment<Mutation>, Boolean>() {
            @Override
            public Boolean call(DocumentFragment<Mutation> documentFragment) {
                ResponseStatus status = documentFragment.status(0);
                if (status == ResponseStatus.SUCCESS) {
                    return true;
                } else {
                    if (status == ResponseStatus.SUBDOC_PATH_EXISTS) {
                        return false;
                    } else {
                        throw new CouchbaseException(status.toString());
                    }
                }
            }
        };
        return queueSubdocAddUnique(docId, element, mutationOptionBuilder)
                .map(mapResult);
    }

    private <E> Observable<DocumentFragment<Mutation>> queueSubdocAddUnique(final String docId,
                                                                            final E element,
                                                                            final MutationOptionBuilder optionBuilder) {
        final Func1<JsonArrayDocument, DocumentFragment<Mutation>> mapFullDocResultToSubdoc = new
                Func1<JsonArrayDocument, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(JsonArrayDocument document) {
                        List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                        list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_ADD_UNIQUE, ResponseStatus.SUCCESS, element));
                        return new DocumentFragment<Mutation>(document.id(), document.cas(), document.mutationToken(), list);
                    }
                };

        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return queueSubdocAddUnique(docId, element, optionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };

        return mutateIn(docId).arrayAddUnique("", element, false)
                .withCas(optionBuilder.getCAS())
                .withDurability(optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                .withExpiry(optionBuilder.getExpiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, optionBuilder.getExpiry(),
                                    JsonArray.create().add(element)),
                                    optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                                    .map(mapFullDocResultToSubdoc)
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_ADD_UNIQUE, status, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0, null, list));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public <E> Observable<Boolean> setExists(final String docId, final E element) {
        return get(docId, JsonArrayDocument.class)
                .toList()
                .map(new Func1<List<JsonArrayDocument>, Boolean>() {
                    @Override
                    public Boolean call(List<JsonArrayDocument> documents) {
                        if (documents.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        }
                        JsonArrayDocument document = documents.get(0);
                        JsonArray jsonArray = document.content();
                        Iterator<Object> iterator = jsonArray.iterator();
                        while (iterator.hasNext()) {
                            Object next = iterator.next();
                            if (next == null && element == null) {
                                return true;
                            } else if (next != null && next.equals(element)) {
                                return true;
                            }
                        }
                        return false;
                    }
                });
    }

    @Override
    public <E> Observable<E> setRemove(String docId, E element) {
        return setRemove(docId, element, MutationOptionBuilder.build());
    }

    @Override
    public <E> Observable<E> setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return setSubdocRemove(docId, element, mutationOptionBuilder);
    }

    private <E> Observable<E> setSubdocRemove(final String docId,
                                              final E element,
                                              final MutationOptionBuilder mutationOptionBuilder) {
        return get(docId, JsonArrayDocument.class)
                .toList()
                .flatMap(new Func1<List<JsonArrayDocument>, Observable<E>>() {
                    @Override
                    public Observable<E> call(List<JsonArrayDocument> documents) {
                        if (documents.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        }
                        JsonArrayDocument jsonArrayDocument = documents.get(0);
                        Iterator iterator = jsonArrayDocument.content().iterator();
                        int ii = 0, index = -1;
                        while (iterator.hasNext()) {
                            Object next = iterator.next();
                            if (next == null && element == null) {
                                index = ii;
                                break;
                            } else if (next != null && next.equals(element)) {
                                index = ii;
                                break;
                            }
                            ii++;
                        }
                        if (index == -1) {
                            return Observable.just(element);
                        }
                        Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> handleCASMismatch = new
                                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                                    @Override
                                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                                        if (throwable instanceof CASMismatchException) {
                                            return setSubdocRemove(docId, element, mutationOptionBuilder).
                                                    map(new Func1<E, DocumentFragment<Mutation>>() {
                                                        @Override
                                                        public DocumentFragment<Mutation> call(E element) {
                                                            List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                                                            list.add(SubdocOperationResult.createResult(null, Mutation.DELETE, ResponseStatus.SUCCESS, element));
                                                            return new DocumentFragment<Mutation>(null, 0, null, list);
                                                        }
                                                    });
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                };
                        return mutateIn(docId).remove("[" + index + "]")
                                .withCas(jsonArrayDocument.cas())
                                .withExpiry(mutationOptionBuilder.getExpiry())
                                .withDurability(mutationOptionBuilder.getPersistTo(), mutationOptionBuilder.getReplicateTo())
                                .execute()
                                .onErrorResumeNext(handleCASMismatch)
                                .map(new Func1<DocumentFragment<Mutation>, E>() {
                                    @Override
                                    public E call(DocumentFragment<Mutation> documentFragment) {
                                        ResponseStatus status = documentFragment.status(0);
                                        if (status == ResponseStatus.SUCCESS) {
                                            return element;
                                        } else {
                                            if (status == ResponseStatus.SUBDOC_PATH_NOT_FOUND ||
                                                    status == ResponseStatus.SUBDOC_PATH_INVALID) {
                                                return element;
                                            }
                                            throw new CouchbaseException(status.toString());
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<Integer> setSize(String docId) {
        return get(docId, JsonArrayDocument.class)
                .toList()
                .map(new Func1<List<JsonArrayDocument>, Integer>() {
                    @Override
                    public Integer call(List<JsonArrayDocument> documents) {
                        if (documents.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        }
                        return documents.get(0).content().size();
                    }
                });
    }

    @Override
    public <E> Observable<Boolean> queueAdd(String docId, E element) {
        return queueAdd(docId, element, MutationOptionBuilder.build());
    }

    @Override
    public <E> Observable<Boolean> queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return queueSubdocAddLast(docId, element, mutationOptionBuilder)
                .map(getMapResultFnForSubdocMutationToBoolean());
    }

    private <E> Observable<DocumentFragment<Mutation>> queueSubdocAddLast(final String docId,
                                                                          final E element,
                                                                          final MutationOptionBuilder optionBuilder) {
        final Func1<JsonArrayDocument, DocumentFragment<Mutation>> mapFullDocResultToSubdoc = new
                Func1<JsonArrayDocument, DocumentFragment<Mutation>>() {
                    @Override
                    public DocumentFragment<Mutation> call(JsonArrayDocument document) {
                        List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                        list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_PUSH_LAST, ResponseStatus.SUCCESS, element));
                        return new DocumentFragment<Mutation>(document.id(), document.cas(), document.mutationToken(), list);
                    }
                };

        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return queueSubdocAddLast(docId, element, optionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };

        return mutateIn(docId).arrayPrepend("", element, false)
                .withCas(optionBuilder.getCAS())
                .withDurability(optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                .withExpiry(optionBuilder.getExpiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, optionBuilder.getExpiry(),
                                    JsonArray.create().add(element)),
                                    optionBuilder.getPersistTo(), optionBuilder.getReplicateTo())
                                    .map(mapFullDocResultToSubdoc)
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                                list.add(SubdocOperationResult.createResult(null, Mutation.ARRAY_PUSH_LAST, status, null));
                                return Observable.just(new DocumentFragment<Mutation>(null, 0, null, list));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public <E> Observable<E> queueRemove(String docId, Class<E> elementType) {
        return queueRemove(docId, elementType, MutationOptionBuilder.build());
    }

    @Override
    public <E> Observable<E> queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder) {
        return queueSubdocRemove(docId, mutationOptionBuilder, elementType);
    }

    private <E> Observable<E> queueSubdocRemove(final String docId, final MutationOptionBuilder mutationOptionBuilder, final Class<E> elementType) {
        return get(docId, JsonArrayDocument.class)
                .toList()
                .flatMap(new Func1<List<JsonArrayDocument>, Observable<E>>() {
                    @Override
                    public Observable<E> call(List<JsonArrayDocument> jsonArrayDocuments) {
                        if (jsonArrayDocuments.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        }
                        JsonArrayDocument jsonArrayDocument = jsonArrayDocuments.get(0);
                        int size = jsonArrayDocument.content().size();
                        final Object val;
                        if (size > 0) {
                            val = jsonArrayDocument.content().get(size - 1);
                        } else {
                            return Observable.just(null);
                        }
                        if (mutationOptionBuilder.getCAS() != 0 && jsonArrayDocument.cas() != mutationOptionBuilder.getCAS()) {
                            throw new CASMismatchException();
                        }
                        Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> handleCASMismatch = new
                                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                                    @Override
                                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                                        if (throwable instanceof CASMismatchException) {
                                            return queueSubdocRemove(docId, mutationOptionBuilder, elementType)
                                                    .map(new Func1<E, DocumentFragment<Mutation>>() {
                                                        @Override
                                                        public DocumentFragment<Mutation> call(E element) {
                                                            List<SubdocOperationResult<Mutation>> list = new ArrayList<SubdocOperationResult<Mutation>>();
                                                            list.add(SubdocOperationResult.createResult(null, Mutation.DELETE, ResponseStatus.SUCCESS, element));
                                                            return new DocumentFragment<Mutation>(null, 0, null, list);
                                                        }
                                                    });
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                };
                        return mutateIn(docId).remove("[" + -1 + "]")
                                .withCas(jsonArrayDocument.cas())
                                .withExpiry(mutationOptionBuilder.getExpiry())
                                .withDurability(mutationOptionBuilder.getPersistTo(), mutationOptionBuilder.getReplicateTo())
                                .execute()
                                .onErrorResumeNext(handleCASMismatch)
                                .map(new Func1<DocumentFragment<Mutation>, E>() {
                                    @Override
                                    public E call(DocumentFragment<Mutation> documentFragment) {
                                        ResponseStatus status = documentFragment.status(0);
                                        if (status == ResponseStatus.SUCCESS) {
                                            if (documentFragment.content(0) != null) {
                                                return (E) documentFragment.content(0);
                                            } else {
                                                return (E) val;
                                            }
                                        } else {
                                            throw new CouchbaseException(status.toString());
                                        }
                                    }
                                });
                    }
                });
    }

    @Override
    public Observable<Integer> queueSize(String docId) {
        return get(docId, JsonArrayDocument.class)
                .toList()
                .map(new Func1<List<JsonArrayDocument>, Integer>() {
                    @Override
                    public Integer call(List<JsonArrayDocument> documents) {
                        if (documents.size() == 0) {
                            throw new DocumentDoesNotExistException();
                        }
                        return documents.get(0).content().size();
                    }
                });
    }

