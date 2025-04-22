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
