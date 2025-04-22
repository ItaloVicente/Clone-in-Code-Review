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
                    case NOT_EXISTS:
                        throw new DocumentDoesNotExistException("Document not found for subdoc API: " + id);
                    case SUBDOC_PATH_NOT_FOUND:
                        return false;
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                        throw new TemporaryFailureException();
                    case OUT_OF_MEMORY:
                        throw new CouchbaseOutOfMemoryException();
                    case SUBDOC_DOC_NOT_JSON:
                    default:
                        throw new CouchbaseException(response.status().toString());
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

                switch(response.status()) {
                    case NOT_EXISTS:
                        throw new DocumentDoesNotExistException("Document not found for subdoc API: " + id);
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                        throw new TemporaryFailureException();
                    case OUT_OF_MEMORY:
                        throw new CouchbaseOutOfMemoryException();
                    case SUBDOC_DELTA_RANGE:
                    default:
                        throw new CouchbaseException(response.status().toString());
                }
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
                    case NOT_EXISTS:
                        throw new DocumentDoesNotExistException("Document not found for subdoc API: " + id);
                    case TEMPORARY_FAILURE:
                    case SERVER_BUSY:
                        throw new TemporaryFailureException();
                    case OUT_OF_MEMORY:
                        throw new CouchbaseOutOfMemoryException();
                    case SUBDOC_DOC_NOT_JSON:
                    default:
                        throw new CouchbaseException(response.status().toString());
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


