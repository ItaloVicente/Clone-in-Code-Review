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
