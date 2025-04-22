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
                            return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(), null,
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
                    return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(), newValue,
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
            case EXISTS:
                return new CASMismatchException("CAS provided in subdoc mutation didn't match the CAS of stored document " + id);
            case TOO_BIG:
                return new RequestTooBigException();
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
    public Observable<MultiLookupResult> lookupIn(final String id, final LookupSpec... lookupSpecs) {
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
        }).flatMap(new Func1<MultiLookupResponse, Observable<MultiLookupResult>>() {
            @Override
            public Observable<MultiLookupResult> call(final MultiLookupResponse multiLookupResponse) {
                return Observable.from(multiLookupResponse.responses())
                        .map(new Func1<com.couchbase.client.core.message.kv.subdoc.multi.MultiResult<Lookup>, LookupResult>() {
                            @Override
                            public LookupResult call(com.couchbase.client.core.message.kv.subdoc.multi.MultiResult<Lookup> lookupResult) {
                                String path = lookupResult.path();
                                boolean isExist = lookupResult.operation() == Lookup.EXIST;
                                boolean success = lookupResult.status().isSuccess();

                                try {
                                    if (isExist) {
                                        return LookupResult.createExistResult(path, lookupResult.status());
                                    } else if (success) {
                                        try {
                                            Object content = subdocumentTranscoder.decode(lookupResult.value(), Object.class);
                                            return LookupResult.createGetResult(path, lookupResult.status(), content);
                                        } catch (TranscodingException e) {
                                            LOGGER.error("Couldn't decode multi-lookup " + lookupResult.operation() + " for " + id + "/" + path, e);
                                            return LookupResult.createFatal(path, lookupResult.operation(), e);
                                        }
                                    } else {
                                        return LookupResult.createGetResult(path, lookupResult.status(), null);
                                    }
                                } finally {
                                    if (lookupResult.value() != null) {
                                        lookupResult.value().release();
                                    }
                                }
                            }
                        }).toList()
                        .map(new Func1<List<LookupResult>, MultiLookupResult>() {
                            @Override
                            public MultiLookupResult call(List<LookupResult> lookupResults) {
                                return new MultiLookupResult(id, lookupSpecs, lookupResults);
                            }
                        });
            }
        });
    }


    private static void releaseAll(List<ByteBuf> byteBufs) {
        for (ByteBuf byteBuf : byteBufs) {
            if (byteBuf != null && byteBuf.refCnt() > 0) {
                byteBuf.release();
            }
        }
    }
