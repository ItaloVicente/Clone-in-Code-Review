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
                    T content = subdocumentTranscoder.decodeWithMessage(response.content(), fragmentType,
                            "Couldn't decode subget fragment for " + id + "/" + path);
                    return DocumentFragment.create(id, path, 0, content, response.cas(), response.mutationToken());
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
                    buf = subdocumentTranscoder.encodeWithMessage(fragment.fragment(),
                            "Couldn't encode subdoc fragment " + fragment.id() + "/" + fragment.path() +
                            " \"" + fragment.fragment() + "\"");
                } catch (TranscodingException e) {
                    return Observable.error(e);
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
                    return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(),
                            fragment.fragment(), response.cas(), response.mutationToken());
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
                            buf = subdocumentTranscoder.encodeWithMessage(fragment.fragment(),
                                    "Couldn't encode subdoc fragment " + fragment.id() + "/" + fragment.path() +
                                            " \"" + fragment.fragment() + "\"");
                        } catch (TranscodingException e) {
                            return Observable.error(e);
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
                            return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(),
                                    fragment.fragment(), response.cas(), response.mutationToken());
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
                            buf = subdocumentTranscoder.encodeWithMessage(fragment.fragment(), "Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"");
                        } catch (TranscodingException e) {
                            return Observable.error(e);
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
                            return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(),
                                    fragment.fragment(), response.cas(), response.mutationToken());
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
                            buf = subdocumentTranscoder.encodeWithMessage(fragment.fragment(), "Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"");
                        } catch (TranscodingException e) {
                            return Observable.error(e);
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
                            return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(),
                                    fragment.fragment(), response.cas(), response.mutationToken());
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
                            buf = subdocumentTranscoder.encodeWithMessage(fragment.fragment(), "Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"");
                        } catch (TranscodingException e) {
                            return Observable.error(e);
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
                            return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(),
                                    fragment.fragment(), response.cas(), response.mutationToken());
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
                            buf = subdocumentTranscoder.encodeWithMessage(fragment.fragment(), "Couldn't encode subdoc fragment "
                                    + fragment.id() + "/" + fragment.path() + " \"" + fragment.fragment() + "\"");
                        } catch (TranscodingException e) {
                            return Observable.error(e);
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
                            return DocumentFragment.create(fragment.id(), fragment.path(), fragment.expiry(),
                                    fragment.fragment(), response.cas(), response.mutationToken());
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
                        .map(new Func1<com.couchbase.client.core.message.kv.subdoc.multi.LookupResult, LookupResult>() {
                            @Override
                            public LookupResult call(com.couchbase.client.core.message.kv.subdoc.multi.LookupResult lookupResult) {
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

    @Override
    public Observable<MultiMutationResult> mutateIn(final JsonDocument doc, PersistTo persistTo, ReplicateTo replicateTo,
            final MutationSpec... mutationSpecs) {
        return mutateIn(doc.id(), doc.cas(), doc.expiry(), persistTo, replicateTo, mutationSpecs);
    }

    @Override
    public Observable<MultiMutationResult> mutateIn(String docId, PersistTo persistTo, ReplicateTo replicateTo,
            final MutationSpec... mutationSpecs) {
        return mutateIn(docId, 0L, 0, persistTo, replicateTo, mutationSpecs);
    }

    protected Observable<MultiMutationResult> mutateIn(final String docId, final long cas, final int expiry,
            final PersistTo persistTo, final ReplicateTo replicateTo, final MutationSpec... mutationSpecs) {
        if (mutationSpecs == null) {
            throw new NullPointerException("At least one MutationSpec is necessary for mutateIn");
        }
        if (mutationSpecs.length == 0) {
            throw new IllegalArgumentException("At least one MutationSpec is necessary for mutateIn");
        }

        Observable<MultiMutationResult> mutations = Observable.defer(new Func0<Observable<MutationCommand>>() {
            @Override
            public Observable<MutationCommand> call() {
                List<ByteBuf> bufList = new ArrayList<ByteBuf>(mutationSpecs.length);
                final List<MutationCommand> commands = new ArrayList<MutationCommand>(mutationSpecs.length);

                for (int i = 0; i < mutationSpecs.length; i++) {
                    MutationSpec spec = mutationSpecs[i];
                    if (spec.type() == Mutation.DELETE) {
                        commands.add(new MutationCommand(Mutation.DELETE, spec.path()));
                    } else {
                        try {
                            ByteBuf buf = subdocumentTranscoder.encodeWithMessage(spec.fragment(), "Couldn't encode MutationSpec #" +
                                    i + " (" + spec.type() + " on " + spec.path() + ") in " + docId);
                            bufList.add(buf);
                            commands.add(new MutationCommand(spec.type(), spec.path(), buf, spec.createParents()));
                        } catch (TranscodingException e) {
                            releaseAll(bufList);
                            return Observable.error(e);
                        }
                    }
                }
                return Observable.from(commands);
            }
        }).toList()
        .flatMap(new Func1<List<MutationCommand>, Observable<MultiMutationResponse>>(){
            @Override
            public Observable<MultiMutationResponse> call(List<MutationCommand> mutationCommands) {
                return core.send(new SubMultiMutationRequest(docId, bucket, expiry, cas, mutationCommands));
            }
        }).flatMap(new Func1<MultiMutationResponse, Observable<MultiMutationResult>>() {
            @Override
            public Observable<MultiMutationResult> call(MultiMutationResponse response) {
                if (response.content() != null && response.content().refCnt() > 0) {
                    response.content().release();
                }

                if (response.status().isSuccess()) {
                    return Observable.just(
                            new MultiMutationResult(docId, response.cas(), response.mutationToken()));
                }

                switch(response.status()) {
                    case SUBDOC_MULTI_PATH_FAILURE:
                        int index = response.firstErrorIndex();
                        ResponseStatus errorStatus = response.firstErrorStatus();
                        String errorPath = mutationSpecs[index].path();
                        CouchbaseException errorException = commonSubdocErrors(errorStatus, docId, errorPath);

                        return Observable.error(new MultiMutationException(index, errorStatus,
                                Arrays.asList(mutationSpecs), errorException));
                    default:
                        return Observable.error(commonSubdocErrors(response.status(), docId, "MULTI-MUTATION"));
                }
            }
        });

        if (persistTo == PersistTo.NONE && replicateTo == ReplicateTo.NONE) {
            return mutations;
        }

        return mutations.flatMap(new Func1<MultiMutationResult, Observable<MultiMutationResult>>() {
            @Override
            public Observable<MultiMutationResult> call(final MultiMutationResult result) {
                return Observe
                        .call(core, bucket, result.id(), result.cas(), false, result.mutationToken(),
                                persistTo.value(), replicateTo.value(),
                                environment.observeIntervalDelay(), environment.retryStrategy())
                        .map(new Func1<Boolean, MultiMutationResult>() {
                            @Override
                            public MultiMutationResult call(Boolean aBoolean) {
                                return result;
                            }
                        })
                        .onErrorResumeNext(new Func1<Throwable, Observable<MultiMutationResult>>() {
                            @Override
                            public Observable<MultiMutationResult> call(Throwable throwable) {
                                return Observable.error(new DurabilityException(
                                        "Durability requirement failed: " + throwable.getMessage(),
                                        throwable));
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

