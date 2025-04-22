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

    @Override
    public <V> Observable<Boolean> mapAdd(String docId, String key, V value) {
        return mapAdd(docId, key, value, MutationOptionBuilder.builder());
    }

    @Override
    public <V> Observable<Boolean> mapAdd(final String docId,
                                          final String key,
                                          final V value,
                                          final MutationOptionBuilder mutationOptionBuilder) {
        return mapSubdocAdd(docId, key, value, mutationOptionBuilder)
                .map(ResultMappingUtils.getMapResultFnForSubdocMutationToBoolean());
    }


    private <V> Observable<DocumentFragment<Mutation>> mapSubdocAdd(final String docId,
                                                                    final String key,
                                                                    final V value,
                                                                    final MutationOptionBuilder mutationOptionBuilder) {
        final Mutation mutationOperation = Mutation.DICT_UPSERT;
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
                .withCas(mutationOptionBuilder.cas())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .withExpiry(mutationOptionBuilder.expiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonDocument.create(docId, mutationOptionBuilder.expiry(),
                                    JsonObject.create().put(key, value)),
                                    mutationOptionBuilder.persistTo(),
                                    mutationOptionBuilder.replicateTo())
                                    .map(ResultMappingUtils.getMapFullDocResultToSubDocFn(mutationOperation))
                                    .onErrorResumeNext(retryAddIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                return Observable.just(ResultMappingUtils.convertToSubDocumentResult(status, mutationOperation, value));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public Observable<Boolean> mapRemove(String docId, String key) {
        return mapRemove(docId, key, MutationOptionBuilder.builder());
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
                .withCas(mutationOptionBuilder.cas())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .withExpiry(mutationOptionBuilder.expiry())
                .execute()
                .map(ResultMappingUtils.getMapResultFnForSubdocMutationToBoolean())
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
        return listPush(docId, element, MutationOptionBuilder.builder());
    }


    @Override
    public <E> Observable<Boolean> listPush(final String docId, final E element, final MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocPushLast(docId, element, mutationOptionBuilder)
                .map(ResultMappingUtils.getMapResultFnForSubdocMutationToBoolean());
    }


    private <E> Observable<DocumentFragment<Mutation>> listSubdocPushLast(final String docId,
                                                                          final E element,
                                                                          final MutationOptionBuilder mutationOptionBuilder) {
        final Mutation mutationOperation = Mutation.ARRAY_PUSH_LAST;
        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return listSubdocPushLast(docId, element, mutationOptionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };
        return mutateIn(docId).arrayAppend("", element, false)
                .withCas(mutationOptionBuilder.cas())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .withExpiry(mutationOptionBuilder.expiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, mutationOptionBuilder.expiry(),
                                    JsonArray.create().add(element)),
                                    mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                                    .map(ResultMappingUtils.getMapFullArrayDocResultToSubDocFn(mutationOperation))
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                return Observable.just(ResultMappingUtils.convertToSubDocumentResult(status, mutationOperation, element));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public Observable<Boolean> listRemove(String docId, int index) {
        return listRemove(docId, index, MutationOptionBuilder.builder());
    }

    @Override
    public Observable<Boolean> listRemove(final String docId, final int index, final MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocRemove(docId, index, mutationOptionBuilder)
                .map(ResultMappingUtils.getMapResultFnForSubdocMutationToBoolean());
    }

    private Observable<DocumentFragment<Mutation>> listSubdocRemove(final String docId,
                                                                    final int index,
                                                                    final MutationOptionBuilder mutationOptionBuilder) {
        return mutateIn(docId).remove("[" + index + "]")
                .withCas(mutationOptionBuilder.cas())
                .withExpiry(mutationOptionBuilder.expiry())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .execute();
    }

    @Override
    public <E> Observable<Boolean> listSet(String docId, int index, E element) {
        return listSet(docId, index, element, MutationOptionBuilder.builder());
    }

    @Override
    public <E> Observable<Boolean> listSet(String docId, int index, E element, MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocInsert(docId, index, element, mutationOptionBuilder)
                .map(ResultMappingUtils.getMapResultFnForSubdocMutationToBoolean());
    }

    private <E> Observable<DocumentFragment<Mutation>> listSubdocInsert(final String docId,
                                                                        final int index,
                                                                        final E element,
                                                                        final MutationOptionBuilder mutationOptionBuilder) {
        final Mutation mutationOperation = Mutation.ARRAY_INSERT;
        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return listSubdocInsert(docId, index, element, mutationOptionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };
        return mutateIn(docId).arrayInsert("[" + index + "]", element)
                .withCas(mutationOptionBuilder.cas())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .withExpiry(mutationOptionBuilder.expiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, mutationOptionBuilder.expiry(),
                                    JsonArray.create().add(element)),
                                    mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                                    .map(ResultMappingUtils.getMapFullArrayDocResultToSubDocFn(mutationOperation))
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                return Observable.just(ResultMappingUtils.convertToSubDocumentResult(status, mutationOperation, element));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public <E> Observable<Boolean> listShift(String docId, E element) {
        return listShift(docId, element, MutationOptionBuilder.builder());
    }

    @Override
    public <E> Observable<Boolean> listShift(final String docId, final E element, final MutationOptionBuilder mutationOptionBuilder) {
        return listSubdocPushFirst(docId, element, mutationOptionBuilder)
                .map(ResultMappingUtils.getMapResultFnForSubdocMutationToBoolean());
    }


    private <E> Observable<DocumentFragment<Mutation>> listSubdocPushFirst(final String docId,
                                                                           final E element,
                                                                           final MutationOptionBuilder mutationOptionBuilder) {


        final Mutation mutationOperation = Mutation.ARRAY_PUSH_FIRST;
        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        return listSubdocPushFirst(docId, element, mutationOptionBuilder);
                    }
                };

        return mutateIn(docId).arrayPrepend("", element, false)
                .withCas(mutationOptionBuilder.cas())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .withExpiry(mutationOptionBuilder.expiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, mutationOptionBuilder.expiry(),
                                    JsonArray.create().add(element)),
                                    mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                                    .map(ResultMappingUtils.getMapFullArrayDocResultToSubDocFn(mutationOperation))
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                return Observable.just(ResultMappingUtils.convertToSubDocumentResult(status, mutationOperation, element));
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
                        } else {
                            return documents.get(0).content().size();
                        }
                    }
                });
    }

    @Override
    public <E> Observable<Boolean> setAdd(String docId, E element) {
        return setAdd(docId, element, MutationOptionBuilder.builder());
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
        return setSubdocAddUnique(docId, element, mutationOptionBuilder)
                .map(mapResult);
    }

    private <E> Observable<DocumentFragment<Mutation>> setSubdocAddUnique(final String docId,
                                                                            final E element,
                                                                            final MutationOptionBuilder mutationOptionBuilder) {
        final Mutation mutationOperation = Mutation.ARRAY_ADD_UNIQUE;
        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return setSubdocAddUnique(docId, element, mutationOptionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };

        return mutateIn(docId).arrayAddUnique("", element, false)
                .withCas(mutationOptionBuilder.cas())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .withExpiry(mutationOptionBuilder.expiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, mutationOptionBuilder.expiry(),
                                    JsonArray.create().add(element)),
                                    mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                                    .map(ResultMappingUtils.getMapFullArrayDocResultToSubDocFn(mutationOperation))
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                return Observable.just(ResultMappingUtils.convertToSubDocumentResult(status, mutationOperation, element));
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
        return setRemove(docId, element, MutationOptionBuilder.builder());
    }

    @Override
    public <E> Observable<E> setRemove(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return setSubdocRemove(docId, element, mutationOptionBuilder, MAX_CAS_RETRIES_DATASTRUCTURES);
    }

    private <E> Observable<E> setSubdocRemove(final String docId,
                                              final E element,
                                              final MutationOptionBuilder mutationOptionBuilder,
                                              final int retryCount) {
        final Mutation mutationOperation = Mutation.DELETE;
        if (retryCount <= 0) return Observable.error(new CASMismatchException());
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
                                            return setSubdocRemove(docId, element, mutationOptionBuilder, retryCount-1).
                                                    map(new Func1<E, DocumentFragment<Mutation>>() {
                                                        @Override
                                                        public DocumentFragment<Mutation> call(E element) {
                                                           return ResultMappingUtils.convertToSubDocumentResult(ResponseStatus.SUCCESS, mutationOperation, element);
                                                        }
                                                    });
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                };
                        return mutateIn(docId).remove("[" + index + "]")
                                .withCas(jsonArrayDocument.cas())
                                .withExpiry(mutationOptionBuilder.expiry())
                                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
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
        return queueAdd(docId, element, MutationOptionBuilder.builder());
    }

    @Override
    public <E> Observable<Boolean> queueAdd(String docId, E element, MutationOptionBuilder mutationOptionBuilder) {
        return queueSubdocAddFirst(docId, element, mutationOptionBuilder)
                .map(ResultMappingUtils.getMapResultFnForSubdocMutationToBoolean());
    }

    private <E> Observable<DocumentFragment<Mutation>> queueSubdocAddFirst(final String docId,
                                                                          final E element,
                                                                          final MutationOptionBuilder mutationOptionBuilder) {
        final Mutation mutationOperation = Mutation.ARRAY_PUSH_FIRST;
        final Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> retryIfDocExists = new
                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentAlreadyExistsException) {
                            return queueSubdocAddFirst(docId, element, mutationOptionBuilder);
                        } else {
                            return Observable.error(throwable);
                        }
                    }
                };

        return mutateIn(docId).arrayPrepend("", element, false)
                .withCas(mutationOptionBuilder.cas())
                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                .withExpiry(mutationOptionBuilder.expiry())
                .execute()
                .onErrorResumeNext(new Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                    @Override
                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                        if (throwable instanceof DocumentDoesNotExistException) {
                            return insert(JsonArrayDocument.create(docId, mutationOptionBuilder.expiry(),
                                    JsonArray.create().add(element)),
                                    mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
                                    .map(ResultMappingUtils.getMapFullArrayDocResultToSubDocFn(mutationOperation))
                                    .onErrorResumeNext(retryIfDocExists);
                        } else {
                            if (throwable instanceof MultiMutationException) {
                                ResponseStatus status = ((MultiMutationException) throwable).firstFailureStatus();
                                return Observable.just(ResultMappingUtils.convertToSubDocumentResult(status,mutationOperation, element));
                            } else {
                                return Observable.error(throwable);
                            }
                        }
                    }
                });
    }

    @Override
    public <E> Observable<E> queueRemove(String docId, Class<E> elementType) {
        return queueRemove(docId, elementType, MutationOptionBuilder.builder());
    }

    @Override
    public <E> Observable<E> queueRemove(String docId, Class<E> elementType, MutationOptionBuilder mutationOptionBuilder) {
        return queueSubdocRemove(docId, mutationOptionBuilder, elementType, MAX_CAS_RETRIES_DATASTRUCTURES);
    }

    private <E> Observable<E> queueSubdocRemove(final String docId,
                                                final MutationOptionBuilder mutationOptionBuilder,
                                                final Class<E> elementType,
                                                final int retryCount) {
        if (retryCount <= 0) return Observable.error(new CASMismatchException());
        final Mutation mutationOperation = Mutation.DELETE;
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
                        if (mutationOptionBuilder.cas() != 0 && jsonArrayDocument.cas() != mutationOptionBuilder.cas()) {
                            throw new CASMismatchException();
                        }
                        Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>> handleCASMismatch = new
                                Func1<Throwable, Observable<? extends DocumentFragment<Mutation>>>() {
                                    @Override
                                    public Observable<? extends DocumentFragment<Mutation>> call(Throwable throwable) {
                                        if (throwable instanceof CASMismatchException) {
                                            return queueSubdocRemove(docId, mutationOptionBuilder, elementType, retryCount-1)
                                                    .map(new Func1<E, DocumentFragment<Mutation>>() {
                                                        @Override
                                                        public DocumentFragment<Mutation> call(E element) {
                                                            return ResultMappingUtils.convertToSubDocumentResult(ResponseStatus.SUCCESS, mutationOperation, element);
                                                        }
                                                    });
                                        } else {
                                            return Observable.error(throwable);
                                        }
                                    }
                                };
                        return mutateIn(docId).remove("[" + -1 + "]")
                                .withCas(jsonArrayDocument.cas())
                                .withExpiry(mutationOptionBuilder.expiry())
                                .withDurability(mutationOptionBuilder.persistTo(), mutationOptionBuilder.replicateTo())
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

