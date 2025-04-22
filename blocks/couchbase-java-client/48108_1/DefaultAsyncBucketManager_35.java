    public Observable<DesignDocument> getDesignDocument(final String name, final boolean development) {
        return Buffers.wrapColdWithAutoRelease(Observable.defer(new Func0<Observable<GetDesignDocumentResponse>>() {
            @Override
            public Observable<GetDesignDocumentResponse> call() {
                return core.send(new GetDesignDocumentRequest(name, development, bucket, password));
            }
        }))
        .filter(new Func1<GetDesignDocumentResponse, Boolean>() {
            @Override
            public Boolean call(GetDesignDocumentResponse response) {
                boolean success = response.status().isSuccess();
                if (!success) {
                    if (response.content() != null && response.content().refCnt() > 0) {
                        response.content().release();
