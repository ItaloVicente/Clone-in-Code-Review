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
                ByteBuf buf;
                try {
                    buf = JSON_OBJECT_TRANSCODER.objectToByteBuf(fragment.fragment());
                } catch (Exception e) {
                    return Observable.error(new TranscodingException("Couldn't encode subdoc fragment "
                            + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"", e));
                }
                SubDictUpsertRequest request = new SubDictUpsertRequest(fragment.id(), fragment.path(), buf, bucket,
                        fragment.expiry(), fragment.cas());
                request.createIntermediaryPath(createParents);
                return core.send(request);
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

        return subdocObserveMutation(mutation, persistTo, replicateTo);
    }

    @Override
    public <T> Observable<DocumentFragment<T>> insertIn(final DocumentFragment<T> fragment, final boolean createParents,
            PersistTo persistTo, ReplicateTo replicateTo) {
        Observable<DocumentFragment<T>> mutation = Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        ByteBuf buf;
                        try {
                            buf = JSON_OBJECT_TRANSCODER.objectToByteBuf(fragment.fragment());
                        } catch (Exception e) {
                            return Observable.error(new TranscodingException("Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"", e));
                        }
                        SubDictAddRequest request = new SubDictAddRequest(fragment.id(), fragment.path(), buf, bucket,
                                fragment.expiry(), fragment.cas());
                        request.createIntermediaryPath(createParents);
                        return core.send(request);
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
                    case SUBDOC_PATH_EXISTS:
                        throw new PathExistsException(fragment.id(), fragment.path());
                    default:
                        throw commonSubdocErrors(response.status(), fragment);
                }
            }
        });

        return subdocObserveMutation(mutation, persistTo, replicateTo);
    }

    @Override
    public <T> Observable<DocumentFragment<T>> replaceIn(final DocumentFragment<T> fragment, PersistTo persistTo,
            ReplicateTo replicateTo) {
        Observable<DocumentFragment<T>> mutation = Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        ByteBuf buf;
                        try {
                            buf = JSON_OBJECT_TRANSCODER.objectToByteBuf(fragment.fragment());
                        } catch (Exception e) {
                            return Observable.error(new TranscodingException("Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"", e));
                        }
                        SubReplaceRequest request = new SubReplaceRequest(fragment.id(), fragment.path(), buf, bucket,
                                fragment.expiry(), fragment.cas());
                        return core.send(request);
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
                    case SUBDOC_PATH_NOT_FOUND:
                        throw new PathNotFoundException("Path to be replaced " + fragment.path() + " not found in " + fragment.id());
                    case SUBDOC_PATH_MISMATCH:
                        throw new PathMismatchException("Path " + fragment.path() + " ends in a scalar value in "
                            + fragment.id() + ", expected dictionary");
                    default:
                        throw commonSubdocErrors(response.status(), fragment);
                }
            }
        });

        return subdocObserveMutation(mutation, persistTo, replicateTo);
    }

    @Override
    public <T> Observable<DocumentFragment<T>> extendIn(final DocumentFragment<T> fragment, final ExtendDirection direction,
            final boolean createParents, PersistTo persistTo, ReplicateTo replicateTo) {
        Observable<DocumentFragment<T>> mutation = Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        ByteBuf buf;
                        try {
                            buf = JSON_OBJECT_TRANSCODER.objectToByteBuf(fragment.fragment());
                        } catch (Exception e) {
                            return Observable.error(new TranscodingException("Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"", e));
                        }
                        SubArrayRequest.ArrayOperation op;
                        switch (direction) {
                            case FRONT:
                                op = SubArrayRequest.ArrayOperation.PUSH_FIRST;
                                break;
                            case BACK:
                            default:
                                op = SubArrayRequest.ArrayOperation.PUSH_LAST;
                                break;
                        }

                        SubArrayRequest request = new SubArrayRequest(fragment.id(), fragment.path(), op,
                                buf, bucket, fragment.expiry(), fragment.cas());
                        request.createIntermediaryPath(createParents);
                        return core.send(request);
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

                throw commonSubdocErrors(response.status(), fragment);
            }
        });

        return subdocObserveMutation(mutation, persistTo, replicateTo);
    }

    @Override
    public <T> Observable<DocumentFragment<T>> arrayInsertIn(final DocumentFragment<T> fragment, PersistTo persistTo,
            ReplicateTo replicateTo) {
        Observable<DocumentFragment<T>> mutation = Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        ByteBuf buf;
                        try {
                            buf = JSON_OBJECT_TRANSCODER.objectToByteBuf(fragment.fragment());
                        } catch (Exception e) {
                            return Observable.error(new TranscodingException("Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"", e));
                        }
                        SubArrayRequest request = new SubArrayRequest(fragment.id(), fragment.path(),
                                SubArrayRequest.ArrayOperation.INSERT,
                                buf, bucket, fragment.expiry(), fragment.cas());
                        return core.send(request);
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

                switch (response.status()) {
                    case SUBDOC_PATH_MISMATCH:
                        throw new PathMismatchException("The last component of path " + fragment.path()
                                + " in " + fragment.id() + " was expected to be an array element");
                    default:
                        throw commonSubdocErrors(response.status(), fragment);
                }
            }
        });

        return subdocObserveMutation(mutation, persistTo, replicateTo);
    }

    @Override
    public <T> Observable<DocumentFragment<T>> addUniqueIn(final DocumentFragment<T> fragment, final boolean createParents,
            PersistTo persistTo, ReplicateTo replicateTo) {
        Observable<DocumentFragment<T>> mutation = Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        ByteBuf buf;
                        try {
                            buf = JSON_OBJECT_TRANSCODER.objectToByteBuf(fragment.fragment());
                        } catch (Exception e) {
                            return Observable.error(new TranscodingException("Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"", e));
                        }
                        SubArrayRequest request = new SubArrayRequest(fragment.id(), fragment.path(),
                                SubArrayRequest.ArrayOperation.ADD_UNIQUE,
                                buf, bucket, fragment.expiry(), fragment.cas());
                        request.createIntermediaryPath(createParents);
                        return core.send(request);
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

                switch (response.status()) {
                    case SUBDOC_PATH_EXISTS:
                        throw new PathExistsException("The unique value already exist in array " + fragment.path()
                                + " in document " + fragment.id());
                    case SUBDOC_VALUE_CANTINSERT:
                        throw new CannotInsertValueException("The unique value provided is not a JSON primitive");
                    case SUBDOC_PATH_MISMATCH:
                        throw new PathMismatchException("The array at " + fragment.path()
                                + " contains non-primitive JSON elements in document " + fragment.id());
                    default:
                        throw commonSubdocErrors(response.status(), fragment);
                }
            }
        });

        return subdocObserveMutation(mutation, persistTo, replicateTo);
    }

    @Override
    public <T> Observable<DocumentFragment<T>> removeIn(final DocumentFragment<T> fragment, PersistTo persistTo,
            ReplicateTo replicateTo) {
        Observable<DocumentFragment<T>> mutation = Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        SubDeleteRequest request = new SubDeleteRequest(fragment.id(), fragment.path(), bucket,
                                fragment.expiry(), fragment.cas());
                        return core.send(request);
                    }
                }).map(new Func1<SimpleSubdocResponse, DocumentFragment<T>>() {
            @Override
            public DocumentFragment<T> call(SimpleSubdocResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return new DocumentFragment<T>(fragment.id(), fragment.path(), null, fragment.expiry(),
                            response.cas(), response.mutationToken());
                }

                throw commonSubdocErrors(response.status(), fragment);
            }
        });

        return subdocObserveMutation(mutation, persistTo, replicateTo);
    }

    @Override
    public Observable<DocumentFragment<Long>> counterIn(final DocumentFragment<Long> fragment, final boolean createParents,
            PersistTo persistTo, ReplicateTo replicateTo) {
        if (fragment.fragment() == null || fragment.fragment() == 0L) {
            return Observable.error(new ZeroDeltaException());
        }

        Observable<DocumentFragment<Long>> mutation = Observable.defer(
                new Func0<Observable<SimpleSubdocResponse>>() {
                    @Override
                    public Observable<SimpleSubdocResponse> call() {
                        long delta = fragment.fragment();
                        SubCounterRequest request = new SubCounterRequest(fragment.id(), fragment.path(),
                                delta, bucket, fragment.expiry(), fragment.cas());
                        request.createIntermediaryPath(createParents);
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

                switch (response.status()) {
                    default:
                        throw commonSubdocErrors(response.status(), fragment);
                }
            }
        }).map(new Func1<SimpleSubdocResponse, DocumentFragment<Long>>() {
            @Override
            public DocumentFragment<Long> call(SimpleSubdocResponse response) {
                try {
                    Long newValue = Long.parseLong(response.content().toString(CharsetUtil.UTF_8));
                    return new DocumentFragment<Long>(fragment.id(), fragment.path(), newValue, fragment.expiry(),
                            response.cas(), response.mutationToken());
                } catch (NumberFormatException e) {
                    throw new TranscodingException("Couldn't parse counter response into a long", e);
                } finally {
                    if (response.content() != null) {
                        response.content().release();
                    }
                }
            }
        });

        return subdocObserveMutation(mutation, persistTo, replicateTo);
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
            case SUBDOC_VALUE_CANTINSERT: //this shouldn't happen outside of add-unique, since we use JSON serializer
                return new CannotInsertValueException("Provided subdocument fragment is not valid JSON");
            default:
                return new CouchbaseException(status.toString());
        }
    }

    private <T> Observable<DocumentFragment<T>> subdocObserveMutation(Observable<DocumentFragment<T>> mutation,
            final PersistTo persistTo, final ReplicateTo replicateTo) {
        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return mutation;
        }

        return mutation.flatMap(new Func1<DocumentFragment<T>, Observable<DocumentFragment<T>>>() {
            @Override
            public Observable<DocumentFragment<T>> call(final DocumentFragment<T> frag) {
                return Observe
                    .call(core, bucket, frag.id(), frag.cas(), false, frag.mutationToken(), persistTo.value(), replicateTo.value(),
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


