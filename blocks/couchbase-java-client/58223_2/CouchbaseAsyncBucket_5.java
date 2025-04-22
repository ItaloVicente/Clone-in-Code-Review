    @Override
    public <T> Observable<DocumentFragment<T>> getIn(final String id, final String path, final Class<T> fragmentType) {
        return Observable.defer(new Func0<Observable<SimpleSubdocResponse>>() {
            @Override
            public Observable<SimpleSubdocResponse> call() {
                SubGetRequest request = new SubGetRequest(id, path, bucket);
                return core.send(request);
            }
        }).filter(new Func1<SimpleSubdocResponse, Boolean>() {
            @Override
            public Boolean call(SimpleSubdocResponse response) {
                if (response.status().isSuccess()) {
                    return true;
                }

                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                switch(response.status()) {
                    case SUBDOC_PATH_NOT_FOUND:
                        return false;
                    default:
                        throw commonSubdocErrors(response.status(), id, path);
                }
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<T>>() {
            @Override
            public DocumentFragment<T> call(SimpleSubdocResponse response) {
                try {
                    T content = (T) JSON_OBJECT_TRANSCODER.byteBufJsonValueToObject(response.content());
                    return new DocumentFragment<T>(id, path, content, 0, response.cas(), response.mutationToken());
                } catch (Exception e) {
                    throw new TranscodingException("Couldn't decode subget fragment for " + id + "/" + path, e);
                } finally {
                    if (response.content() != null) {
                        response.content().release();
                    }
                }
            }
        });
    }

    @Override
    public Observable<Boolean> existsIn(final String id, final String path) {
        return Observable.defer(new Func0<Observable<SimpleSubdocResponse>>() {
            @Override
            public Observable<SimpleSubdocResponse> call() {
                SubExistRequest request = new SubExistRequest(id, path, bucket);
                return core.send(request);
            }
        }).map(new Func1<SimpleSubdocResponse, Boolean>() {
            @Override
            public Boolean call(SimpleSubdocResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return true;
                } else if (response.status() == ResponseStatus.SUBDOC_PATH_NOT_FOUND) {
                    return false;
                }

                throw commonSubdocErrors(response.status(), id, path);
            }
        });
    }

    @Override
    public <T> Observable<DocumentFragment<T>> upsertIn(final DocumentFragment<T> fragment, final boolean createParents,
            final PersistTo persistTo, final ReplicateTo replicateTo) {
        Observable<DocumentFragment<T>> mutation = Observable.defer(new Func0<Observable<SimpleSubdocResponse>>() {
            @Override
            public Observable<SimpleSubdocResponse> call() {
                try {
                    ByteBuf buf = JSON_OBJECT_TRANSCODER.objectToByteBuf(fragment.fragment());
                    SubDictUpsertRequest request = new SubDictUpsertRequest(fragment.id(), fragment.path(), buf, bucket,
                            fragment.expiry(), fragment.cas());
                    request.createIntermediaryPath(createParents);
                    return core.send(request);
                } catch (Exception e) {
                    return Observable.error(new TranscodingException("Couldn't encode subdoc fragment "
                            + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"", e));
                }
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<T>>() {
            @Override
            public DocumentFragment<T> call(SimpleSubdocResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return new DocumentFragment<T>(fragment.id(), fragment.path(), fragment.fragment(), fragment.expiry(),
                            response.cas(), response.mutationToken());
                }

                switch(response.status()) {
                    case SUBDOC_PATH_INVALID:
                        throw new PathInvalidException("Path " + fragment.path() + " ends in an array index in "
                                + fragment.id() + ", expected dictionary");
                    case SUBDOC_PATH_MISMATCH:
                        throw new PathMismatchException("Path " + fragment.path() + " ends in a scalar value in "
                            + fragment.id() + ", expected dictionary");
                    default:
                        throw commonSubdocErrors(response.status(), fragment);
                }
            }
        });

        return subdocObserveMutation(mutation, false, persistTo, replicateTo);
    }

    private <T> CouchbaseException commonSubdocErrors(ResponseStatus status, DocumentFragment<T> fragment) {
        return commonSubdocErrors(status, fragment.id(), fragment.path());
    }

    private CouchbaseException commonSubdocErrors(ResponseStatus status, String id, String path) {
        switch (status) {
            case NOT_EXISTS:
                return new DocumentDoesNotExistException("Document not found for subdoc API: " + id);
            case TEMPORARY_FAILURE:
            case SERVER_BUSY:
                return  new TemporaryFailureException();
            case OUT_OF_MEMORY:
                return new CouchbaseOutOfMemoryException();
            case SUBDOC_PATH_NOT_FOUND:
                return new PathNotFoundException(id, path);
            case SUBDOC_PATH_EXISTS:
                return new PathExistsException(id, path);
            case SUBDOC_DOC_NOT_JSON:
                return new DocumentNotJsonException(id);
            case SUBDOC_DOC_TOO_DEEP:
                return new DocumentTooDeepException(id);
            case SUBDOC_DELTA_RANGE:
                return new DeltaTooBigException();
            case SUBDOC_NUM_RANGE:
                return new NumberTooBigException();
            case SUBDOC_VALUE_TOO_DEEP:
                return new ValueTooDeepException(id, path);
            case SUBDOC_PATH_TOO_BIG:
                return new PathTooDeepException(path);
            case SUBDOC_PATH_INVALID:
                return new PathInvalidException(id, path);
            case SUBDOC_PATH_MISMATCH:
                return new PathMismatchException(id, path);
            case SUBDOC_VALUE_CANTINSERT:
            default:
                return new CouchbaseException(status.toString());
        }
    }

    private <T> Observable<DocumentFragment<T>> subdocObserveMutation(Observable<DocumentFragment<T>> mutation,
            final boolean isRemove, final PersistTo persistTo, final ReplicateTo replicateTo) {
        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return mutation;
        }

        return mutation.flatMap(new Func1<DocumentFragment<T>, Observable<DocumentFragment<T>>>() {
            @Override
            public Observable<DocumentFragment<T>> call(final DocumentFragment<T> frag) {
                return Observe
                    .call(core, bucket, frag.id(), frag.cas(), isRemove, frag.mutationToken(), persistTo.value(), replicateTo.value(),
                        environment.observeIntervalDelay(), environment.retryStrategy())
                    .map(new Func1<Boolean, DocumentFragment<T>>() {
                        @Override
                        public DocumentFragment<T> call(Boolean aBoolean) {
                            return frag;
                        }
                    })
                    .onErrorResumeNext(new Func1<Throwable, Observable<DocumentFragment<T>>>() {
                        @Override
                        public Observable<DocumentFragment<T>> call(Throwable throwable) {
                            return Observable.error(new DurabilityException(
                                "Durability requirement failed: " + throwable.getMessage(),
                                throwable));
                        }
                    });
            }
        });
    }

    @Override
    public Observable<DocumentFragment<Object>> lookupIn(final String id, final LookupSpec... lookupSpecs) {
        if (lookupSpecs == null) {
            throw new NullPointerException("At least one LookupCommand is necessary for lookupIn");
        }
        if (lookupSpecs.length == 0) {
            throw new IllegalArgumentException("At least one LookupCommand is necessary for lookupIn");
        }

        return Observable.defer(new Func0<Observable<MultiLookupResponse>>() {
            @Override
            public Observable<MultiLookupResponse> call() {
                return core.send(new SubMultiLookupRequest(id, bucket, lookupSpecs));
            }
        }).filter(new Func1<MultiLookupResponse, Boolean>() {
            @Override
            public Boolean call(MultiLookupResponse response) {
                if (response.status().isSuccess() || response.status() == ResponseStatus.SUBDOC_MULTI_PATH_FAILURE) {
                    return true;
                }

                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                switch(response.status()) {
                    default:
                        throw commonSubdocErrors(response.status(), id, "MULTI-LOOKUP");
                }
            }
        }).flatMap(new Func1<MultiLookupResponse, Observable<DocumentFragment<Object>>>() {
            @Override
            public Observable<DocumentFragment<Object>> call(final MultiLookupResponse multiLookupResponse) {
                return Observable.from(multiLookupResponse.responses())
                        .map(new Func1<LookupResult, DocumentFragment<Object>>() {
                            @Override
                            public DocumentFragment<Object> call(LookupResult lookupResult) {
                                String path = lookupResult.path();
                                boolean isExist = lookupResult.operation() == Lookup.EXIST;
                                boolean success = lookupResult.status().isSuccess();

                                try {
                                    Object content;
                                    if (isExist) {
                                        content = success;
                                    } else if (success) {
                                        content = JSON_OBJECT_TRANSCODER.byteBufJsonValueToObject(lookupResult.value());
                                    } else {
                                        content = null;//FIXME how to transpose the error?
                                    }
                                    return new DocumentFragment<Object>(id, path, content, 0, 0L, null);
                                } catch (Exception e) {
                                    throw new TranscodingException("Couldn't decode multi-lookup " +
                                            lookupResult.operation() + " for " + id + "/" + path, e);
                                } finally {
                                    if (lookupResult.value() != null) {
                                        lookupResult.value().release();
                                    }
                                }
                            }
                        });
            }
        });
    }


