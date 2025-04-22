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
